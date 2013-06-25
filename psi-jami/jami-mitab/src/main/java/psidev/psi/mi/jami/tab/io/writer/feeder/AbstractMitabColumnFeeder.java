package psidev.psi.mi.jami.tab.io.writer.feeder;

import psidev.psi.mi.jami.binary.BinaryInteraction;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.tab.utils.MitabUtils;
import psidev.psi.mi.jami.utils.ParameterUtils;
import psidev.psi.mi.jami.utils.RangeUtils;

import java.io.IOException;
import java.io.Writer;
import java.util.Date;
import java.util.Iterator;

/**
 * Abstract Mitab 2.5 column feeder
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>20/06/13</pre>
 */

public abstract class AbstractMitabColumnFeeder<T extends BinaryInteraction, P extends Participant> implements MitabColumnFeeder<T, P> {

    private Writer writer;

    public AbstractMitabColumnFeeder(Writer writer){
        if (writer == null){
            throw  new IllegalArgumentException("The Mitab column feeder needs a Writer.");
        }
        this.writer = writer;
    }

    public void writeUniqueIdentifier(P participant) throws IOException {
        if (participant == null){
            writer.write(MitabUtils.EMPTY_COLUMN);
        }
        else {
            Interactor interactor = participant.getInteractor();
            // write first identifier
            if (!interactor.getIdentifiers().isEmpty()){
                writeIdentifier(interactor.getIdentifiers().iterator().next());
            }
            else{
                writer.write(MitabUtils.EMPTY_COLUMN);
            }
        }
    }

    public void writeAlternativeIdentifiers(P participant) throws IOException {
        if (participant == null){
            writer.write(MitabUtils.EMPTY_COLUMN);
        }
        else {
            Interactor interactor = participant.getInteractor();
            // write other identifiers
            if (interactor.getIdentifiers().size() > 1){
                Iterator<Xref> identifierIterator = interactor.getIdentifiers().iterator();
                // skip first identifier
                identifierIterator.next();

                while (identifierIterator.hasNext()){
                    // write alternative identifier
                    writeIdentifier(identifierIterator.next());
                    // write field separator
                    if (identifierIterator.hasNext()){
                        writer.write(MitabUtils.FIELD_SEPARATOR);
                    }
                }
            }
            else{
                writer.write(MitabUtils.EMPTY_COLUMN);
            }
        }
    }

    public void writeAliases(P participant) throws IOException {
        if (participant == null){
            writer.write(MitabUtils.EMPTY_COLUMN);
        }
        else {
            Interactor interactor = participant.getInteractor();

            // write shortlabel first
            writer.write(CvTerm.PSI_MI);
            writer.write(":");
            escapeAndWriteString(interactor.getShortName());
            writer.write("(");
            writer.write(MitabUtils.DISPLAY_SHORT);
            writer.write(")");

            // write fullName then
            if (interactor.getFullName() != null){
                writer.write("|");
                writer.write(CvTerm.PSI_MI);
                writer.write(":");
                escapeAndWriteString(interactor.getFullName());
                writer.write("(");
                writer.write(MitabUtils.DISPLAY_LONG);
                writer.write(")");
            }

            // write aliases
            if (!interactor.getAliases().isEmpty()){
                Iterator<Alias> aliasIterator = interactor.getAliases().iterator();

                while (aliasIterator.hasNext()){
                    writer.write(MitabUtils.FIELD_SEPARATOR);

                    writeAlias(participant, aliasIterator.next());
                }
            }
            // write participant aliases
            if (!participant.getAliases().isEmpty()){
                Iterator<Alias> aliasIterator = participant.getAliases().iterator();

                while (aliasIterator.hasNext()){
                    writer.write(MitabUtils.FIELD_SEPARATOR);

                    writeAlias(participant, aliasIterator.next());
                }
            }
        }
    }

    public void writeInteractorOrganism(P participant) throws IOException {
        if (participant != null){
            Interactor interactor = participant.getInteractor();

            writeOrganism(interactor.getOrganism());
        }
        else{
            writer.write(MitabUtils.EMPTY_COLUMN);
        }
    }

    public void writeInteractionType(T interaction) throws IOException {
        writeCvTerm(interaction.getInteractionType());
    }

    public void writeConfidence(Confidence conf) throws IOException {
        if (conf != null){
            // write confidence type first
            if (conf.getType().getFullName() != null){
                escapeAndWriteString(conf.getType().getFullName());
            }
            else{
                escapeAndWriteString(conf.getType().getShortName());
            }

            // write confidence value
            writer.write(MitabUtils.XREF_SEPARATOR);
            escapeAndWriteString(conf.getValue());
        }
    }

    public void writeParameter(Parameter parameter) throws IOException {
        if (parameter != null){
            // first parameter type
            escapeAndWriteString(parameter.getType().getShortName());
            getWriter().write(MitabUtils.XREF_SEPARATOR);
            // then parameter value
            escapeAndWriteString(ParameterUtils.getParameterValueAsString(parameter));
            // then write unit
            if (parameter.getUnit() != null){
                getWriter().write("(");
                escapeAndWriteString(parameter.getUnit().getShortName());
                getWriter().write(")");
            }
        }
    }

    public void writeOrganism(Organism organism) throws IOException {
        if (organism != null){

            writer.write(MitabUtils.TAXID);
            writer.write(MitabUtils.XREF_SEPARATOR);
            writer.write(Integer.toString(organism.getTaxId()));

            // write common name if provided
            if (organism.getCommonName() != null){
                writer.write("(");
                escapeAndWriteString(organism.getCommonName());
                writer.write(")");

                // write scientific name if provided
                if (organism.getScientificName() != null){
                    writer.write(MitabUtils.FIELD_SEPARATOR);
                    writer.write(MitabUtils.TAXID);
                    writer.write(MitabUtils.XREF_SEPARATOR);
                    writer.write(Integer.toString(organism.getTaxId()));
                    writer.write("(");
                    escapeAndWriteString(organism.getScientificName());
                    writer.write(")");
                }
            }
            // write scientific name if provided
            else if (organism.getScientificName() != null){
                writer.write("(");
                escapeAndWriteString(organism.getScientificName());
                writer.write(")");
            }
        }
        else {
            writer.write(MitabUtils.EMPTY_COLUMN);
        }
    }

    public void writeCvTerm(CvTerm cv) throws IOException {
        if (cv != null){
            // write MI xref first
            if (cv.getMIIdentifier() != null){
                writer.write(CvTerm.PSI_MI);
                writer.write(MitabUtils.XREF_SEPARATOR);
                writer.write("\"");
                writer.write(cv.getMIIdentifier());
                writer.write("\"");

                // write cv name
                writer.write("(");
                writeCvTermName(cv);
                writer.write(")");
            }
            // write MOD xref
            else if (cv.getMODIdentifier() != null){
                writer.write(CvTerm.PSI_MOD);
                writer.write(MitabUtils.XREF_SEPARATOR);
                writer.write("\"");
                writer.write(cv.getMODIdentifier());
                writer.write("\"");

                // write cv name
                writer.write("(");
                writeCvTermName(cv);
                writer.write(")");
            }
            // write PAR xref
            else if (cv.getPARIdentifier() != null){
                writer.write(CvTerm.PSI_PAR);
                writer.write(MitabUtils.XREF_SEPARATOR);
                writer.write("\"");
                writer.write(cv.getPARIdentifier());
                writer.write("\"");

                // write cv name
                writer.write("(");
                writeCvTermName(cv);
                writer.write(")");
            }
            // write first identifier
            else if (!cv.getIdentifiers().isEmpty()) {
                writeIdentifier(cv.getIdentifiers().iterator().next());

                // write cv name
                writer.write("(");
                writeCvTermName(cv);
                writer.write(")");
            }
            // write empty column
            else{
                writer.write(MitabUtils.UNKNOWN_DATABASE);
                writer.write(MitabUtils.XREF_SEPARATOR);
                writer.write(MitabUtils.UNKNOWN_ID);

                // write cv name
                writer.write("(");
                writeCvTermName(cv);
                writer.write(")");
            }
        }
        else {
            writer.write(MitabUtils.EMPTY_COLUMN);
        }
    }

    public void writeAlias(Alias alias) throws IOException {
        if (alias != null){
            // write db first
            escapeAndWriteString(MitabUtils.findDbSourceForAlias(alias));
            // write xref separator
            writer.write(MitabUtils.XREF_SEPARATOR);
            // write name
            escapeAndWriteString(alias.getName());
            // write type
            if (alias.getType() != null){
                writer.write("(");
                escapeAndWriteString(alias.getType().getShortName());
                writer.write(")");
            }
        }
    }

    public void writeIdentifier(Xref identifier) throws IOException {
        if (identifier != null){
            // write db first
            escapeAndWriteString(identifier.getDatabase().getShortName());
            // write xref separator
            writer.write(MitabUtils.XREF_SEPARATOR);
            // write id
            escapeAndWriteString(identifier.getId());
            // write version
            if (identifier.getVersion() != null){
                writer.write(identifier.getVersion());
            }
        }
    }

    public void escapeAndWriteString(String stringToEscape) throws IOException {
        // replace first tabs and break line with a space and escape double quote
        String replaced = stringToEscape.replaceAll(MitabUtils.LINE_BREAK+"|"+ MitabUtils.COLUMN_SEPARATOR, " ");
        replaced = replaced.replaceAll("\"", "\\\\\"");

        for (String special : MitabUtils.SPECIAL_CHARACTERS){

            if (replaced.contains(special)){
                writer.write("\"");
                writer.write(replaced);
                writer.write("\"");
                return;
            }
        }

        writer.write(replaced);
    }

    public void writeComplexExpansion(T binary) throws IOException {
        if (binary.getComplexExpansion() != null){
            writeCvTerm(binary.getComplexExpansion());
        }
        else {
            getWriter().write(MitabUtils.EMPTY_COLUMN);
        }
    }

    public void writeBiologicalRole(P participant) throws IOException {
        if (participant != null){
            writeCvTerm(participant.getBiologicalRole());
        }
        else {
            getWriter().write(MitabUtils.EMPTY_COLUMN);
        }
    }

    public void writeInteractorType(P participant) throws IOException {
        if (participant != null){
            writeCvTerm(participant.getInteractor().getInteractorType());
        }
        else {
            getWriter().write(MitabUtils.EMPTY_COLUMN);
        }
    }

    public void writeParticipantXrefs(P participant) throws IOException {
        if (participant != null){

            // write interactor ref and participant ref
            if (!participant.getInteractor().getXrefs().isEmpty()){
                Iterator<Xref> interactorXrefIterator = participant.getInteractor().getXrefs().iterator();

                // write each interactor xref
                while (interactorXrefIterator.hasNext()) {
                    Xref ref = interactorXrefIterator.next();
                    writeXref(ref);

                    if (interactorXrefIterator.hasNext()){
                        getWriter().write(MitabUtils.FIELD_SEPARATOR);
                    }
                }

                // write participant xrefs
                if (!participant.getXrefs().isEmpty()){
                    getWriter().write(MitabUtils.FIELD_SEPARATOR);
                    Iterator<Xref> participantXrefIterator = participant.getXrefs().iterator();
                    while (participantXrefIterator.hasNext()) {
                        Xref ref = participantXrefIterator.next();
                        writeXref(ref);

                        if (participantXrefIterator.hasNext()){
                            getWriter().write(MitabUtils.FIELD_SEPARATOR);
                        }
                    }
                }
            }
            // write participant ref only
            else if (!participant.getXrefs().isEmpty()){
                Iterator<Xref> participantXrefIterator = participant.getXrefs().iterator();
                while (participantXrefIterator.hasNext()) {
                    Xref ref = participantXrefIterator.next();
                    writeXref(ref);

                    if (participantXrefIterator.hasNext()){
                        getWriter().write(MitabUtils.FIELD_SEPARATOR);
                    }
                }
            }
            else{
                getWriter().write(MitabUtils.EMPTY_COLUMN);
            }
        }
        else {
            getWriter().write(MitabUtils.EMPTY_COLUMN);
        }
    }

    public void writeParticipantAnnotations(P participant) throws IOException {
        if (participant != null){
            // writes interactor annotations first
            if (!participant.getInteractor().getAnnotations().isEmpty()){
                Iterator<Annotation> interactorAnnotationIterator = participant.getInteractor().getAnnotations().iterator();

                while (interactorAnnotationIterator.hasNext()){
                    Annotation annot = interactorAnnotationIterator.next();
                    writeAnnotation(annot);

                    if(interactorAnnotationIterator.hasNext()){
                        getWriter().write(MitabUtils.FIELD_SEPARATOR);
                    }
                }

                if (!participant.getAnnotations().isEmpty()){
                    getWriter().write(MitabUtils.FIELD_SEPARATOR);
                    Iterator<Annotation> participantAnnotationIterator = participant.getAnnotations().iterator();

                    while (participantAnnotationIterator.hasNext()){
                        Annotation annot = participantAnnotationIterator.next();
                        writeAnnotation(annot);

                        if(participantAnnotationIterator.hasNext()){
                            getWriter().write(MitabUtils.FIELD_SEPARATOR);
                        }
                    }
                }
            }
            // writes participant annotations only
            else if (!participant.getAnnotations().isEmpty()){
                Iterator<Annotation> participantAnnotationIterator = participant.getAnnotations().iterator();

                while (participantAnnotationIterator.hasNext()){
                    Annotation annot = participantAnnotationIterator.next();
                    writeAnnotation(annot);

                    if(participantAnnotationIterator.hasNext()){
                        getWriter().write(MitabUtils.FIELD_SEPARATOR);
                    }
                }
            }
            else{
                getWriter().write(MitabUtils.EMPTY_COLUMN);
            }
        }
        else{
            getWriter().write(MitabUtils.EMPTY_COLUMN);
        }
    }

    public void writeDate(Date date) throws IOException {
        if (date != null){
            getWriter().write(MitabUtils.DATE_FORMAT.format(date));
        }
        else {
            getWriter().write(MitabUtils.EMPTY_COLUMN);
        }
    }

    public void writeParticipantChecksums(P participant) throws IOException {
        if (participant != null){

            if (!participant.getInteractor().getChecksums().isEmpty()){

                Iterator<Checksum> checksumIterator = participant.getInteractor().getChecksums().iterator();
                while(checksumIterator.hasNext()){
                    writeChecksum(checksumIterator.next());
                    if (checksumIterator.hasNext()){
                        getWriter().write(MitabUtils.FIELD_SEPARATOR);
                    }
                }
            }
            else{
                getWriter().write(MitabUtils.EMPTY_COLUMN);
            }
        }
        else {
            getWriter().write(MitabUtils.EMPTY_COLUMN);
        }
    }

    public void writeInteractionChecksums(T interaction) throws IOException {
        if (!interaction.getChecksums().isEmpty()){

            Iterator<Checksum> checksumIterator = interaction.getChecksums().iterator();
            while(checksumIterator.hasNext()){
                writeChecksum(checksumIterator.next());
                if (checksumIterator.hasNext()){
                    getWriter().write(MitabUtils.FIELD_SEPARATOR);
                }
            }
        }
        else{
            getWriter().write(MitabUtils.EMPTY_COLUMN);
        }
    }

    public void writeChecksum(Checksum checksum) throws IOException {
        if (checksum != null){
            // first method
            escapeAndWriteString(checksum.getMethod().getShortName());
            getWriter().write(MitabUtils.XREF_SEPARATOR);
            // then value
            escapeAndWriteString(checksum.getValue());
        }
    }

    public void writeAnnotation(Annotation annotation) throws IOException {
        if (annotation != null){

            // write topic first
            writeCvTermName(annotation.getTopic());

            // write text after
            if (annotation.getValue() != null){
                getWriter().write(MitabUtils.XREF_SEPARATOR);
                escapeAndWriteString(annotation.getValue());
            }
        }
    }

    public void writeXref(Xref xref) throws IOException {
        if (xref != null){
            // write identifier first
            writeIdentifier(xref);

            // write qualifier
            if (xref.getQualifier() != null){
                getWriter().write("(");
                escapeAndWriteString(xref.getQualifier().getShortName());
                getWriter().write(")");
            }
        }
    }

    public void writeParticipantFeatures(P participant) throws IOException {
        if (participant != null){

            if (!participant.getFeatures().isEmpty()){

                Iterator<? extends Feature> featureIterator = participant.getFeatures().iterator();
                while(featureIterator.hasNext()){
                    writeFeature(featureIterator.next());

                    if (featureIterator.hasNext()){
                        getWriter().write(MitabUtils.FIELD_SEPARATOR);
                    }
                }
            }
            else{
                getWriter().write(MitabUtils.EMPTY_COLUMN);
            }
        }
        else {
            getWriter().write(MitabUtils.EMPTY_COLUMN);
        }
    }

    public void writeParticipantStoichiometry(P participant) throws IOException {
        if (participant != null){

            if (participant.getStoichiometry() != null){
                Stoichiometry stc = participant.getStoichiometry();
                // same stoichiometry max/end
                if (stc.getMaxValue() == stc.getMinValue()){
                    getWriter().write(Long.toString(stc.getMinValue()));
                }
                else{
                    getWriter().write(Long.toString(stc.getMinValue()));
                    getWriter().write("-");
                    getWriter().write(Long.toString(stc.getMaxValue()));
                }
            }
            else{
                getWriter().write(MitabUtils.EMPTY_COLUMN);
            }
        }
        else {
            getWriter().write(MitabUtils.EMPTY_COLUMN);
        }
    }

    public void writeFeature(Feature feature) throws IOException {
        if (feature != null){
            // first write interactor type
            if (feature.getType() != null){
                writeCvTermName(feature.getType());
            }
            else {
                getWriter().write(MitabUtils.UNKNOWN_TYPE);
            }
            getWriter().write(MitabUtils.XREF_SEPARATOR);
            // then write ranges
            if (feature.getRanges().isEmpty()){
                getWriter().write(Range.UNDETERMINED_POSITION_SYMBOL);
                getWriter().write(Range.POSITION_SEPARATOR);
                getWriter().write(Range.UNDETERMINED_POSITION_SYMBOL);
            }
            else{
                Iterator<Range> rangeIterator = feature.getRanges().iterator();
                while(rangeIterator.hasNext()){
                    getWriter().write(RangeUtils.convertRangeToString(rangeIterator.next()));
                    if (rangeIterator.hasNext()){
                        getWriter().write(MitabUtils.RANGE_SEPARATOR);
                    }
                }
            }
            // then write text
            if (feature.getInterpro() != null){
                getWriter().write("(");
                escapeAndWriteString(feature.getInterpro());
                getWriter().write(")");
            }
        }
    }

    protected Writer getWriter() {
        return writer;
    }

    /**
     * Write full name if not null, otherwise write shortname
     * @param cv
     * @throws IOException
     */
    protected void writeCvTermName(CvTerm cv) throws IOException {
        if (cv != null){
            if (cv.getFullName() != null){
                escapeAndWriteString(cv.getFullName());
            }
            else{
                escapeAndWriteString(cv.getShortName());
            }
        }
    }
}
