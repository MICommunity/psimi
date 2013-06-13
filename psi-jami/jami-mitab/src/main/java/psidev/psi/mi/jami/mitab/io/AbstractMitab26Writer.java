package psidev.psi.mi.jami.mitab.io;

import psidev.psi.mi.jami.binary.BinaryInteraction;
import psidev.psi.mi.jami.binary.BinaryInteractionEvidence;
import psidev.psi.mi.jami.binary.ModelledBinaryInteraction;
import psidev.psi.mi.jami.binary.expansion.ComplexExpansionMethod;
import psidev.psi.mi.jami.mitab.MitabVersion;
import psidev.psi.mi.jami.mitab.utils.MitabWriterUtils;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.utils.ParameterUtils;
import psidev.psi.mi.jami.utils.XrefUtils;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.util.Date;
import java.util.Iterator;

/**
 * Abstract writer for Mitab 2.6.
 *
 * The general options when calling method initialiseContext(Map<String, Object> options) are :
 *  - output_file_key : File. Specifies the file where to write
 *  - output_stream_key : OutputStream. Specifies the stream where to write
 *  - output_writer_key : Writer. Specifies the writer.
 *  If these three options are given, output_file_key will take priority, then output_stream_key an finally output_writer_key. At leats
 *  one of these options should be provided when initialising the context of the writer
 *  - complex_expansion_key : Class<? extends ComplexExpansionMethod>. Specifies the ComplexExpansion class to use. By default, it is SpokeExpansion if nothing is specified
 *  - mitab_header_key : Boolean. Specifies if the writer should write the MITAB header when starting to write or not
 *
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>13/06/13</pre>
 */

public abstract class AbstractMitab26Writer extends AbstractMitab25Writer {

    public AbstractMitab26Writer() {
        super();
        setVersion(MitabVersion.v2_6);
    }

    public AbstractMitab26Writer(File file) throws IOException {
        super(file);
        setVersion(MitabVersion.v2_6);
    }

    public AbstractMitab26Writer(OutputStream output) throws IOException {
        super(output);
        setVersion(MitabVersion.v2_6);
    }

    public AbstractMitab26Writer(Writer writer) throws IOException {
        super(writer);
        setVersion(MitabVersion.v2_6);
    }

    public AbstractMitab26Writer(File file, ComplexExpansionMethod expansionMethod) throws IOException {
        super(file, expansionMethod);
        setVersion(MitabVersion.v2_6);
    }

    public AbstractMitab26Writer(OutputStream output, ComplexExpansionMethod expansionMethod) throws IOException {
        super(output, expansionMethod);
        setVersion(MitabVersion.v2_6);
    }

    public AbstractMitab26Writer(Writer writer, ComplexExpansionMethod expansionMethod) throws IOException {
        super(writer, expansionMethod);
        setVersion(MitabVersion.v2_6);
    }

    @Override
    /**
     * Writes the binary interaction and its participants in MITAB 2.6
     * @param interaction
     * @param a
     * @param b
     * @throws IOException
     */
    protected void writeBinary(BinaryInteraction interaction, Participant a, Participant b) throws IOException {
        // write tab 25 columns first
        super.writeBinary(interaction, a, b);
        getWriter().write(MitabWriterUtils.COLUMN_SEPARATOR);
        // complex expansion
        writeComplexExpansion(interaction);
        getWriter().write(MitabWriterUtils.COLUMN_SEPARATOR);
        // biorole A
        writeBiologicalRole(a);
        getWriter().write(MitabWriterUtils.COLUMN_SEPARATOR);
        // biorole B
        writeBiologicalRole(b);
        getWriter().write(MitabWriterUtils.COLUMN_SEPARATOR);
        // skip exprole A
        getWriter().write(MitabWriterUtils.EMPTY_COLUMN);
        getWriter().write(MitabWriterUtils.COLUMN_SEPARATOR);
        // skip exprole B
        getWriter().write(MitabWriterUtils.EMPTY_COLUMN);
        getWriter().write(MitabWriterUtils.COLUMN_SEPARATOR);
        // interactor type A
        writeInteractorType(a);
        getWriter().write(MitabWriterUtils.COLUMN_SEPARATOR);
        // interactor type B
        writeInteractorType(b);
        getWriter().write(MitabWriterUtils.COLUMN_SEPARATOR);
        // xref A
        writeParticipantXrefs(a);
        getWriter().write(MitabWriterUtils.COLUMN_SEPARATOR);
        // xref B
        writeParticipantXrefs(b);
        getWriter().write(MitabWriterUtils.COLUMN_SEPARATOR);
        // xref
        writeInteractionXrefs(interaction);
        getWriter().write(MitabWriterUtils.COLUMN_SEPARATOR);
        // annotation A
        writeParticipantAnnotations(a);
        getWriter().write(MitabWriterUtils.COLUMN_SEPARATOR);
        // annotation B
        writeParticipantAnnotations(b);
        getWriter().write(MitabWriterUtils.COLUMN_SEPARATOR);
        // annotation
        writeInteractionAnnotations(interaction);
        getWriter().write(MitabWriterUtils.COLUMN_SEPARATOR);
        // skip host organism
        getWriter().write(MitabWriterUtils.EMPTY_COLUMN);
        getWriter().write(MitabWriterUtils.COLUMN_SEPARATOR);
        // skip interaction parameter
        getWriter().write(MitabWriterUtils.EMPTY_COLUMN);
        getWriter().write(MitabWriterUtils.COLUMN_SEPARATOR);
        // created date
        writeDate(interaction.getCreatedDate());
        getWriter().write(MitabWriterUtils.COLUMN_SEPARATOR);
        // update date
        writeDate(interaction.getUpdatedDate());
        getWriter().write(MitabWriterUtils.COLUMN_SEPARATOR);
        // checksum A
        writeParticipantChecksums(a);
        getWriter().write(MitabWriterUtils.COLUMN_SEPARATOR);
        // checksum B
        writeParticipantChecksums(b);
        getWriter().write(MitabWriterUtils.COLUMN_SEPARATOR);
        // checksum I
        writeInteractionChecksums(interaction);
        getWriter().write(MitabWriterUtils.COLUMN_SEPARATOR);
        // skip negative
        getWriter().write(MitabWriterUtils.EMPTY_COLUMN);
    }

    @Override
    /**
     * Writes the binary interaction and its participants in MITAB 2.6
     * @param interaction
     * @param a
     * @param b
     * @throws IOException
     */
    protected void writeModelledBinary(ModelledBinaryInteraction interaction, ModelledParticipant a, ModelledParticipant b) throws IOException {
        // write tab 25 columns first
        super.writeModelledBinary(interaction, a, b);
        getWriter().write(MitabWriterUtils.COLUMN_SEPARATOR);
        // complex expansion
        writeComplexExpansion(interaction);
        getWriter().write(MitabWriterUtils.COLUMN_SEPARATOR);
        // biorole A
        writeBiologicalRole(a);
        getWriter().write(MitabWriterUtils.COLUMN_SEPARATOR);
        // biorole B
        writeBiologicalRole(b);
        getWriter().write(MitabWriterUtils.COLUMN_SEPARATOR);
        // skip exprole A
        getWriter().write(MitabWriterUtils.EMPTY_COLUMN);
        getWriter().write(MitabWriterUtils.COLUMN_SEPARATOR);
        // skip exprole B
        getWriter().write(MitabWriterUtils.EMPTY_COLUMN);
        getWriter().write(MitabWriterUtils.COLUMN_SEPARATOR);
        // interactor type A
        writeInteractorType(a);
        getWriter().write(MitabWriterUtils.COLUMN_SEPARATOR);
        // interactor type B
        writeInteractorType(b);
        getWriter().write(MitabWriterUtils.COLUMN_SEPARATOR);
        // xref A
        writeParticipantXrefs(a);
        getWriter().write(MitabWriterUtils.COLUMN_SEPARATOR);
        // xref B
        writeParticipantXrefs(b);
        getWriter().write(MitabWriterUtils.COLUMN_SEPARATOR);
        // xref
        writeInteractionXrefs(interaction);
        getWriter().write(MitabWriterUtils.COLUMN_SEPARATOR);
        // annotation A
        writeParticipantAnnotations(a);
        getWriter().write(MitabWriterUtils.COLUMN_SEPARATOR);
        // annotation B
        writeParticipantAnnotations(b);
        getWriter().write(MitabWriterUtils.COLUMN_SEPARATOR);
        // annotation
        writeInteractionAnnotations(interaction);
        getWriter().write(MitabWriterUtils.COLUMN_SEPARATOR);
        // skip host organism
        getWriter().write(MitabWriterUtils.EMPTY_COLUMN);
        getWriter().write(MitabWriterUtils.COLUMN_SEPARATOR);
        // write interaction parameter
        writeInteractionParameters(interaction);
        getWriter().write(MitabWriterUtils.COLUMN_SEPARATOR);
        // created date
        writeDate(interaction.getCreatedDate());
        getWriter().write(MitabWriterUtils.COLUMN_SEPARATOR);
        // update date
        writeDate(interaction.getUpdatedDate());
        getWriter().write(MitabWriterUtils.COLUMN_SEPARATOR);
        // checksum A
        writeParticipantChecksums(a);
        getWriter().write(MitabWriterUtils.COLUMN_SEPARATOR);
        // checksum B
        writeParticipantChecksums(b);
        getWriter().write(MitabWriterUtils.COLUMN_SEPARATOR);
        // checksum I
        writeInteractionChecksums(interaction);
        getWriter().write(MitabWriterUtils.COLUMN_SEPARATOR);
        // skip negative
        getWriter().write(MitabWriterUtils.EMPTY_COLUMN);
    }

    @Override
    /**
     * Writes the binary interaction and its participants in MITAB 2.6
     * @param interaction
     * @param a
     * @param b
     * @throws IOException
     */
    protected void writeBinaryEvidence(BinaryInteractionEvidence interaction, ParticipantEvidence a, ParticipantEvidence b) throws IOException {
        // write tab 25 columns first
        super.writeBinaryEvidence(interaction, a, b);
        getWriter().write(MitabWriterUtils.COLUMN_SEPARATOR);
        // complex expansion
        writeComplexExpansion(interaction);
        getWriter().write(MitabWriterUtils.COLUMN_SEPARATOR);
        // biorole A
        writeBiologicalRole(a);
        getWriter().write(MitabWriterUtils.COLUMN_SEPARATOR);
        // biorole B
        writeBiologicalRole(b);
        getWriter().write(MitabWriterUtils.COLUMN_SEPARATOR);
        // write exprole A
        writeExperimentalRole(a);
        getWriter().write(MitabWriterUtils.COLUMN_SEPARATOR);
        // write exprole B
        writeExperimentalRole(b);
        getWriter().write(MitabWriterUtils.COLUMN_SEPARATOR);
        // interactor type A
        writeInteractorType(a);
        getWriter().write(MitabWriterUtils.COLUMN_SEPARATOR);
        // interactor type B
        writeInteractorType(b);
        getWriter().write(MitabWriterUtils.COLUMN_SEPARATOR);
        // xref A
        writeParticipantXrefs(a);
        getWriter().write(MitabWriterUtils.COLUMN_SEPARATOR);
        // xref B
        writeParticipantXrefs(b);
        getWriter().write(MitabWriterUtils.COLUMN_SEPARATOR);
        // xref
        writeInteractionXrefs(interaction);
        getWriter().write(MitabWriterUtils.COLUMN_SEPARATOR);
        // annotation A
        writeParticipantAnnotations(a);
        getWriter().write(MitabWriterUtils.COLUMN_SEPARATOR);
        // annotation B
        writeParticipantAnnotations(b);
        getWriter().write(MitabWriterUtils.COLUMN_SEPARATOR);
        // annotation
        writeInteractionAnnotations(interaction);
        getWriter().write(MitabWriterUtils.COLUMN_SEPARATOR);
        // write host organism
        writeHostOrganism(interaction.getExperiment());
        getWriter().write(MitabWriterUtils.COLUMN_SEPARATOR);
        // write interaction parameter
        writeInteractionParameters(interaction);
        getWriter().write(MitabWriterUtils.COLUMN_SEPARATOR);
        // created date
        writeDate(interaction.getCreatedDate());
        getWriter().write(MitabWriterUtils.COLUMN_SEPARATOR);
        // update date
        writeDate(interaction.getUpdatedDate());
        getWriter().write(MitabWriterUtils.COLUMN_SEPARATOR);
        // checksum A
        writeParticipantChecksums(a);
        getWriter().write(MitabWriterUtils.COLUMN_SEPARATOR);
        // checksum B
        writeParticipantChecksums(b);
        getWriter().write(MitabWriterUtils.COLUMN_SEPARATOR);
        // checksum I
        writeInteractionChecksums(interaction);
        getWriter().write(MitabWriterUtils.COLUMN_SEPARATOR);
        // write negative
        writeNegativeProperty(interaction);
    }

    /**
     * Writes the complex expansion of a binary interaction
     * @param binary
     */
    protected void writeComplexExpansion(BinaryInteraction binary) throws IOException {

        if (binary.getComplexExpansion() != null){
            writeCvTerm(binary.getComplexExpansion());
        }
        else {
            getWriter().write(MitabWriterUtils.EMPTY_COLUMN);
        }
    }

    /**
     * Writes the biological role of a participant
     * @param participant
     * @throws IOException
     */
    protected void writeBiologicalRole(Participant participant) throws IOException {

        if (participant != null){
            writeCvTerm(participant.getBiologicalRole());
        }
        else {
            getWriter().write(MitabWriterUtils.EMPTY_COLUMN);
        }
    }

    /**
     * Writes the experimentsl role of a participant evidence
     * @param participant
     * @throws IOException
     */
    protected void writeExperimentalRole(ParticipantEvidence participant) throws IOException {

        if (participant != null){
            writeCvTerm(participant.getExperimentalRole());
        }
        else {
            getWriter().write(MitabWriterUtils.EMPTY_COLUMN);
        }
    }

    /**
     * Writes the interactor type of a participant
     * @param participant
     * @throws IOException
     */
    protected void writeInteractorType(Participant participant) throws IOException {

        if (participant != null){
            writeCvTerm(participant.getInteractor().getInteractorType());
        }
        else {
            getWriter().write(MitabWriterUtils.EMPTY_COLUMN);
        }
    }

    /**
     * Write Xref of participant and interactor
     * @param participant
     * @throws IOException
     */
    protected void writeParticipantXrefs(Participant participant) throws IOException {
        if (participant != null){

            // write interactor ref and participant ref
            if (!participant.getInteractor().getXrefs().isEmpty()){
                Iterator<Xref> interactorXrefIterator = participant.getInteractor().getXrefs().iterator();

                // write each interactor xref
                while (interactorXrefIterator.hasNext()) {
                    Xref ref = interactorXrefIterator.next();
                    writeXref(ref);

                    if (interactorXrefIterator.hasNext()){
                        getWriter().write(MitabWriterUtils.FIELD_SEPARATOR);
                    }
                }

                // write participant xrefs
                if (!participant.getXrefs().isEmpty()){
                    getWriter().write(MitabWriterUtils.FIELD_SEPARATOR);
                    Iterator<Xref> participantXrefIterator = participant.getXrefs().iterator();
                    while (participantXrefIterator.hasNext()) {
                        Xref ref = participantXrefIterator.next();
                        writeXref(ref);

                        if (participantXrefIterator.hasNext()){
                            getWriter().write(MitabWriterUtils.FIELD_SEPARATOR);
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
                        getWriter().write(MitabWriterUtils.FIELD_SEPARATOR);
                    }
                }
            }
            else{
                getWriter().write(MitabWriterUtils.EMPTY_COLUMN);
            }
        }
        else {
            getWriter().write(MitabWriterUtils.EMPTY_COLUMN);
        }
    }

    /**
     * Write interaction Xref r
     * @param interaction
     * @throws IOException
     */
    protected void writeInteractionXrefs(Interaction interaction) throws IOException {
        // write interaction ref
        if (!interaction.getXrefs().isEmpty()){
            Iterator<Xref> interactionXrefIterator = interaction.getXrefs().iterator();

            Xref next = null;
            boolean isFirst = true;
            do {
                next = interactionXrefIterator.next();
                while (interactionXrefIterator.hasNext() && (XrefUtils.doesXrefHaveQualifier(next, Xref.IMEX_PRIMARY_MI, Xref.IMEX_PRIMARY)
                        && XrefUtils.isXrefFromDatabase(next, Xref.IMEX_MI, Xref.IMEX))){
                    next = interactionXrefIterator.next();
                }

                if (next != null && !(XrefUtils.doesXrefHaveQualifier(next, Xref.IMEX_PRIMARY_MI, Xref.IMEX_PRIMARY)
                        && XrefUtils.isXrefFromDatabase(next, Xref.IMEX_MI, Xref.IMEX))){
                    if (!isFirst){
                        getWriter().write(MitabWriterUtils.FIELD_SEPARATOR);
                    }
                    // write xref ony if it is not an imex id
                    writeXref(next);
                    isFirst = false;
                }
                else {
                    next = null;
                }
            }
            while (next != null) ;
        }
        else{
            getWriter().write(MitabWriterUtils.EMPTY_COLUMN);
        }
    }

    /**
     * Writes participant annotations
     * @param participant
     */
    protected void writeParticipantAnnotations(Participant participant) throws IOException {

        if (participant != null){
            // writes interactor annotations first
            if (!participant.getInteractor().getAnnotations().isEmpty()){
                Iterator<Annotation> interactorAnnotationIterator = participant.getInteractor().getAnnotations().iterator();

                while (interactorAnnotationIterator.hasNext()){
                    Annotation annot = interactorAnnotationIterator.next();
                    writeAnnotation(annot);

                    if(interactorAnnotationIterator.hasNext()){
                        getWriter().write(MitabWriterUtils.FIELD_SEPARATOR);
                    }
                }

                if (!participant.getAnnotations().isEmpty()){
                    getWriter().write(MitabWriterUtils.FIELD_SEPARATOR);
                    Iterator<Annotation> participantAnnotationIterator = participant.getAnnotations().iterator();

                    while (participantAnnotationIterator.hasNext()){
                        Annotation annot = participantAnnotationIterator.next();
                        writeAnnotation(annot);

                        if(participantAnnotationIterator.hasNext()){
                            getWriter().write(MitabWriterUtils.FIELD_SEPARATOR);
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
                        getWriter().write(MitabWriterUtils.FIELD_SEPARATOR);
                    }
                }
            }
            else{
                getWriter().write(MitabWriterUtils.EMPTY_COLUMN);
            }
        }
        else{
            getWriter().write(MitabWriterUtils.EMPTY_COLUMN);
        }
    }

    /**
     * Writes interaction annotations
     * @param interaction
     */
    protected void writeInteractionAnnotations(Interaction interaction) throws IOException {

        // writes interaction annotations first
        if (!interaction.getAnnotations().isEmpty()){
            Iterator<Annotation> interactorAnnotationIterator = interaction.getAnnotations().iterator();

            while (interactorAnnotationIterator.hasNext()){
                Annotation annot = interactorAnnotationIterator.next();
                writeAnnotation(annot);

                if(interactorAnnotationIterator.hasNext()){
                    getWriter().write(MitabWriterUtils.FIELD_SEPARATOR);
                }
            }
        }
        else{
            getWriter().write(MitabWriterUtils.EMPTY_COLUMN);
        }
    }

    /**
     * Writes experiment host organism
     * @param experiment
     */
    protected void writeHostOrganism(Experiment experiment) throws IOException {

        // writes interaction annotations first
        if (experiment != null){
            writeOrganism(experiment.getHostOrganism());
        }
        else{
            getWriter().write(MitabWriterUtils.EMPTY_COLUMN);
        }
    }

    /**
     * Writes interaction evidence parameters
     * @param interaction
     */
    protected void writeInteractionParameters(InteractionEvidence interaction) throws IOException {

        if (!interaction.getParameters().isEmpty()){

            Iterator<Parameter> parameterIterator = interaction.getParameters().iterator();
            while(parameterIterator.hasNext()){
                writeParameter(parameterIterator.next());
                if (parameterIterator.hasNext()){
                    getWriter().write(MitabWriterUtils.FIELD_SEPARATOR);
                }
            }
        }
        else {
            getWriter().write(MitabWriterUtils.EMPTY_COLUMN);
        }
    }

    /**
     * Writes modelled interaction parameters
     * @param interaction
     */
    protected void writeInteractionParameters(ModelledInteraction interaction) throws IOException {

        if (!interaction.getModelledParameters().isEmpty()){

            Iterator<ModelledParameter> parameterIterator = interaction.getModelledParameters().iterator();
            while(parameterIterator.hasNext()){
                writeParameter(parameterIterator.next());
                if (parameterIterator.hasNext()){
                    getWriter().write(MitabWriterUtils.FIELD_SEPARATOR);
                }
            }
        }
        else {
            getWriter().write(MitabWriterUtils.EMPTY_COLUMN);
        }
    }

    /**
     * Writes created date of an interaction
     * @param date
     */
    protected void writeDate(Date date) throws IOException {

        if (date != null){
            getWriter().write(MitabWriterUtils.DATE_FORMAT.format(date));
        }
        else {
            getWriter().write(MitabWriterUtils.EMPTY_COLUMN);
        }
    }

    /**
     * Writes participant checksum
     * @param participant
     */
    protected void writeParticipantChecksums(Participant participant) throws IOException {

        if (participant != null){

            if (!participant.getInteractor().getChecksums().isEmpty()){

                Iterator<Checksum> checksumIterator = participant.getInteractor().getChecksums().iterator();
                while(checksumIterator.hasNext()){
                    writeChecksum(checksumIterator.next());
                    if (checksumIterator.hasNext()){
                        getWriter().write(MitabWriterUtils.FIELD_SEPARATOR);
                    }
                }
            }
            else{
                getWriter().write(MitabWriterUtils.EMPTY_COLUMN);
            }
        }
        else {
            getWriter().write(MitabWriterUtils.EMPTY_COLUMN);
        }
    }

    /**
     * Writes interaction checksum
     * @param interaction
     */
    protected void writeInteractionChecksums(Interaction interaction) throws IOException {

        if (!interaction.getChecksums().isEmpty()){

            Iterator<Checksum> checksumIterator = interaction.getChecksums().iterator();
            while(checksumIterator.hasNext()){
                writeChecksum(checksumIterator.next());
                if (checksumIterator.hasNext()){
                    getWriter().write(MitabWriterUtils.FIELD_SEPARATOR);
                }
            }
        }
        else{
            getWriter().write(MitabWriterUtils.EMPTY_COLUMN);
        }
    }

    /**
     * Writes interaction negative property if true
     * @param interaction
     */
    protected void writeNegativeProperty(InteractionEvidence interaction) throws IOException {

        if (interaction.isNegative()){
            getWriter().write("true");
        }
        else {
            getWriter().write(MitabWriterUtils.EMPTY_COLUMN);
        }
    }

    /**
     * Writes the checksum
     * @param checksum
     */
    protected void writeChecksum(Checksum checksum) throws IOException {

        if (checksum != null){
            // first method
            escapeAndWriteString(checksum.getMethod().getShortName());
            getWriter().write(MitabWriterUtils.XREF_SEPARATOR);
            // then value
            escapeAndWriteString(checksum.getValue());
        }
    }

    /**
     * Writes the parameter
     * @param parameter
     * @throws IOException
     */
    protected void writeParameter(Parameter parameter) throws IOException {

        if (parameter != null){
            // first parameter type
            escapeAndWriteString(parameter.getType().getShortName());
            getWriter().write(MitabWriterUtils.XREF_SEPARATOR);
            // then parameter value
            getWriter().write(ParameterUtils.getParameterValueAsString(parameter));
            // then write unit
            if (parameter.getUnit() != null){
                getWriter().write("(");
                escapeAndWriteString(parameter.getUnit().getShortName());
                getWriter().write(")");
            }
        }
    }

    /**
     * Writes interaction evidence annotations .
     *
     * It will also add annotation from the publication that are used as a tag
     * @param interaction
     */
    protected void writeInteractionEvidenceAnnotations(InteractionEvidence interaction) throws IOException {

        // writes interaction annotations first
        if (!interaction.getAnnotations().isEmpty()){
            Iterator<Annotation> interactorAnnotationIterator = interaction.getAnnotations().iterator();

            while (interactorAnnotationIterator.hasNext()){
                Annotation annot = interactorAnnotationIterator.next();
                writeAnnotation(annot);

                if(interactorAnnotationIterator.hasNext()){
                    getWriter().write(MitabWriterUtils.FIELD_SEPARATOR);
                }
            }

            if (interaction.getExperiment() != null){
                Publication pub = interaction.getExperiment().getPublication();

                if (pub != null){
                    getWriter().write(MitabWriterUtils.FIELD_SEPARATOR);
                    writeInteractionAnnotationTagsFrom(pub);


                }
            }
        }
        else if (interaction.getExperiment() != null){
            Publication pub = interaction.getExperiment().getPublication();

            if (pub != null){

                // writes curation depth first
                writeInteractionAnnotationTagsFrom(pub);
            }
        }
        else{
            getWriter().write(MitabWriterUtils.EMPTY_COLUMN);
        }
    }

    /**
     * Writes an annotation
     * @param annotation
     * @throws IOException
     */
    protected void writeAnnotation(Annotation annotation) throws IOException {
        if (annotation != null){

            // write topic first
            if (annotation.getTopic().getFullName() != null){
                escapeAndWriteString(annotation.getTopic().getFullName());
            }
            else{
                escapeAndWriteString(annotation.getTopic().getShortName());
            }

            // write text after
            if (annotation.getValue() != null){
                getWriter().write(MitabWriterUtils.XREF_SEPARATOR);
                escapeAndWriteString(annotation.getValue());
            }
        }
    }

    /**
     * This methods write the database, id, version and qualifier of an xref
     * @param xref
     * @throws IOException
     */
    protected void writeXref(Xref xref) throws IOException {
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

    protected void writeInteractionAnnotationTagsFrom(Publication pub) throws IOException {
        // writes curation depth first
        switch (pub.getCurationDepth()){
            case IMEx:
                getWriter().write(Annotation.IMEX_CURATION);
                getWriter().write(MitabWriterUtils.FIELD_SEPARATOR);
                break;
            case MIMIx:
                getWriter().write(Annotation.MIMIX_CURATION);
                getWriter().write(MitabWriterUtils.FIELD_SEPARATOR);
                break;
            case rapid_curation:
                getWriter().write(Annotation.RAPID_CURATION);
                getWriter().write(MitabWriterUtils.FIELD_SEPARATOR);
                break;
            default:
                break;
        }

        // writes special annotations
        Iterator<Annotation> publicationAnnotationIterator = pub.getAnnotations().iterator();

        Annotation next = null;
        boolean isFirst = true;
        do {
            next = publicationAnnotationIterator.next();
            while (publicationAnnotationIterator.hasNext() &&
                    !MitabWriterUtils.isAnnotationAnInteractionTag(next)){
                next = publicationAnnotationIterator.next();
            }

            if (next != null && MitabWriterUtils.isAnnotationAnInteractionTag(next)){
                if (!isFirst){
                    getWriter().write(MitabWriterUtils.FIELD_SEPARATOR);
                }
                // write annotation if interaction tag
                writeAnnotation(next);
                isFirst = false;
            }
            else {
                next = null;
            }
        }
        while (next != null) ;
    }
}
