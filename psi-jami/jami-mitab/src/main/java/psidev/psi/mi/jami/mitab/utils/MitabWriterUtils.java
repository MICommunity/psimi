package psidev.psi.mi.jami.mitab.utils;

import psidev.psi.mi.jami.binary.BinaryInteraction;
import psidev.psi.mi.jami.mitab.MitabColumnName;
import psidev.psi.mi.jami.mitab.MitabVersion;
import psidev.psi.mi.jami.mitab.extension.MitabAlias;
import psidev.psi.mi.jami.mitab.extension.MitabConfidence;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.utils.AliasUtils;
import psidev.psi.mi.jami.utils.XrefUtils;

import java.io.IOException;
import java.io.Writer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Iterator;

/**
 * Utilisty class for MitabWriter
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>10/06/13</pre>
 */

public class MitabWriterUtils {

    public static final String LINE_BREAK = System.getProperty("line.separator");
    public static final String COLUMN_SEPARATOR = "\t";
    public static final String FIELD_SEPARATOR = "|";
    public static final String EMPTY_COLUMN = "-";
    public static final String XREF_SEPARATOR = ":";
    public static final String RANGE_SEPARATOR = ",";
    public static final String COMMENT_PREFIX = "#";
    public static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy/MM/dd");
    public static final DateFormat PUBLICATION_YEAR_FORMAT = new SimpleDateFormat("yyyy");
    public static final String UNKNOWN_DATABASE = "unknown";
    public static final String AUTHOR_SUFFIX = " et al.";
    public static final String TAXID = "taxid";

    public static final String [] SPECIAL_CHARACTERS = new String[]{FIELD_SEPARATOR,
            XREF_SEPARATOR, "(", ")"};

    public static final String MITAB_VERSION_OPTION = "mitab_version_key";
    public static final String MITAB_HEADER_OPTION = "mitab_header_key";

    /**
     * Build the header and return an array of String which is an array of column names
     * @param version
     * @return
     * @throws IllegalArgumentException
     */
    public static String[] buildHeader(MitabVersion version) throws IllegalArgumentException {
        if (version == null) {
            throw new IllegalArgumentException("The header for this version of MITAB can not be created ");
        }

        MitabColumnName[] columns = MitabColumnName.values();
        int numberOfColumns = version.getNumberOfColumns();
        String[] header = new String[numberOfColumns];

        for (int i = 0; i < numberOfColumns; i++) {
            header[i] = columns[i].toString();
        }
        //We add the start of the header #
        header[0] = COMMENT_PREFIX + header[0];

        return header;
    }

    /**
     * Write the header for a specific version and a specific writer
     * @param version
     * @param writer
     * @throws IOException
     */
    public static void writeHeader(MitabVersion version, Writer writer) throws IOException {
        writer.write(COMMENT_PREFIX);
        writer.write(" ");

        for (MitabColumnName colName : MitabColumnName.values()) {
            writer.write(colName.toString());
            // starts with 0
            if (colName.ordinal() < version.getNumberOfColumns() - 1){
                writer.write(COLUMN_SEPARATOR);
            }
            else {
                break;
            }
        }
    }

    /**
     * This method will write the unique identifier of a participant
     * @param participant
     * @param writer
     * @throws IOException
     */
    public static void writeUniqueIdentifier(Participant participant, Writer writer) throws IOException {

        if (participant == null){
            writer.write(EMPTY_COLUMN);
        }
        else {
            Interactor interactor = participant.getInteractor();
            // write first identifier
            if (!interactor.getIdentifiers().isEmpty()){
                writeIdentifier(interactor.getIdentifiers().iterator().next(), writer);
            }
            else{
                writer.write(EMPTY_COLUMN);
            }
        }
    }

    /**
     * This method writes all the remaining identifiers (ignore the first identifier) of the participant
     * @param participant
     * @param writer
     * @throws IOException
     */
    public static void writeAlternativeIdentifiers(Participant participant, Writer writer) throws IOException {

        if (participant == null){
            writer.write(EMPTY_COLUMN);
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
                    writeIdentifier(identifierIterator.next(), writer);
                    // write field separator
                    if (identifierIterator.hasNext()){
                        writer.write(FIELD_SEPARATOR);
                    }
                }
            }
            else{
                writer.write(EMPTY_COLUMN);
            }
        }
    }

    /**
     * This method writes all the aliases of the participant
     * @param participant
     * @param writer
     * @param areMitabExtendedAliases : boolean value to know if the aliases of the participant are MITAB extended aliases. In this case,
     *                                The dbsource is provided and does not need to be extracted from the participant
     * @throws IOException
     */
    public static void writeAliases(Participant participant, Writer writer, boolean areMitabExtendedAliases) throws IOException {

        if (participant == null){
            writer.write(EMPTY_COLUMN);
        }
        else {
            Interactor interactor = participant.getInteractor();
            // write aliases
            if (!interactor.getAliases().isEmpty()){
                Iterator<Alias> aliasIterator = interactor.getAliases().iterator();

                while (aliasIterator.hasNext()){
                    Alias alias = aliasIterator.next();
                    // write extended alias
                    if (areMitabExtendedAliases){
                        MitabAlias mitabAlias = (MitabAlias) alias;
                        writeAlias(mitabAlias, mitabAlias.getDbSource(), writer);
                    }
                    // write default Alias
                    else{
                        writeAlias(alias, findDbSourceForAlias(alias), writer);
                    }
                    // write field separator
                    if (aliasIterator.hasNext()){
                        writer.write(FIELD_SEPARATOR);
                    }
                }
            }
            else{
                writer.write(EMPTY_COLUMN);
            }
        }
    }

    /**
     * Writes the interaction detection method of the experiment.
     * @param experiment
     * @param writer
     */
    public static void writeInteractionDetectionMethod(Experiment experiment, Writer writer) throws IOException {
        if (experiment != null){
            writeCvTerm(experiment.getInteractionDetectionMethod(), writer);
        }
        else{
            writer.write(EMPTY_COLUMN);
        }
    }

    /**
     * Writes the first author of a publication in an experiment
     * @param experiment
     * @param writer
     */
    public static void writeFirstAuthor(Experiment experiment, Writer writer) throws IOException {
        if (experiment != null){
            Publication pub = experiment.getPublication();

            if (pub != null){
                // authors and maybe publication date
                if (!pub.getAuthors().isEmpty()){
                    escapeAndWriteString(pub.getAuthors().iterator().next() + AUTHOR_SUFFIX, writer);
                    if (pub.getPublicationDate() != null){
                        writer.write(" (");
                        writer.write(PUBLICATION_YEAR_FORMAT.format(pub.getPublicationDate()));
                        writer.write(")");
                    }
                }
                // publication date only
                else if (pub.getPublicationDate() != null){
                    writer.write("unknown (");
                    writer.write(PUBLICATION_YEAR_FORMAT.format(pub.getPublicationDate()));
                    writer.write(")");
                }
                else {
                    writer.write(EMPTY_COLUMN);
                }
            }
            else{
                writer.write(EMPTY_COLUMN);
            }
        }
        else{
            writer.write(EMPTY_COLUMN);
        }
    }

    /**
     * Writes the publication identifiers of a publication in an experiment.
     * This method will write the first publication identifier (pubmed before doi) and also the IMEx id
     * @param experiment
     * @param writer
     */
    public static void writePublicationIdentifiers(Experiment experiment, Writer writer) throws IOException {
        if (experiment != null){
            Publication pub = experiment.getPublication();

            if (pub != null){
                // identifiers
                if (pub.getPubmedId() != null){
                    writer.write(Xref.PUBMED);
                    writer.write(XREF_SEPARATOR);
                    escapeAndWriteString(pub.getPubmedId(), writer);

                    // IMEx as well
                    writer.write(FIELD_SEPARATOR);
                    writePublicationImexId(writer, pub);
                }
                // doi only
                else if (pub.getDoi() != null){
                    writer.write(Xref.DOI);
                    writer.write(XREF_SEPARATOR);
                    escapeAndWriteString(pub.getDoi(), writer);

                    // IMEx as well
                    writer.write(FIELD_SEPARATOR);
                    writePublicationImexId(writer, pub);
                }
                // other identfiers
                else if (!pub.getIdentifiers().isEmpty()){
                    writeIdentifier(pub.getIdentifiers().iterator().next(), writer);

                    // IMEx as well
                    writer.write(FIELD_SEPARATOR);
                    writePublicationImexId(writer, pub);
                }
                // IMEx only
                else if (pub.getImexId() != null) {
                    writePublicationImexId(writer, pub);
                }
                // nothing
                else{
                    writer.write(EMPTY_COLUMN);
                }
            }
            else{
                writer.write(EMPTY_COLUMN);
            }
        }
        else{
            writer.write(EMPTY_COLUMN);
        }
    }

    /**
     * Writes the organism of a participant.
     * Empty column if the organism is not provided
     * @param participant
     * @param writer
     */
    public static void writeInteractorOrganism(Participant participant, Writer writer) throws IOException {
        if (participant != null){
            Interactor interactor = participant.getInteractor();

            writeOrganism(writer, interactor.getOrganism());
        }
        else{
            writer.write(EMPTY_COLUMN);
        }
    }

    /**
     * Writes the interaction type of an interaction
     * @param interaction
     */
    public static void writeInteractionType(Interaction interaction, Writer writer) throws IOException {

        writeCvTerm(interaction.getInteractionType(), writer);
    }

    /**
     * Writes the interaction source from the publication of the experiment
     * @param experiment
     * @param writer
     */
    public static void writeSource(Experiment experiment, Writer writer) throws IOException {
        if (experiment == null){
            Publication pub = experiment.getPublication();

            if (pub != null){
                writeCvTerm(pub.getSource(), writer);
            }
            else {
                writer.write(EMPTY_COLUMN);
            }
        }
        else{
            writer.write(EMPTY_COLUMN);
        }
    }

    /**
     * Writes the interaction source from the modelled interaction
     * @param interaction
     * @param writer
     */
    public static void writeSource(ModelledInteraction interaction, Writer writer) throws IOException {
        writeCvTerm(interaction.getSource(), writer);
    }

    /**
     * Writes the interaction identifiers of an interaction.
     * This method will write the first interaction identifier and also the IMEx id
     * @param interaction
     * @param writer
     */
    public static void writeInteractionIdentifiers(Interaction interaction, Writer writer) throws IOException {

        // get imex id
        Collection<Xref> imexId = XrefUtils.collectAllXrefsHavingDatabaseAndQualifier(interaction.getXrefs(), Xref.IMEX_MI, Xref.IMEX, Xref.IMEX_PRIMARY_MI, Xref.IMEX_PRIMARY);

        // other identfiers
        if (!interaction.getIdentifiers().isEmpty()){
            writeIdentifier(interaction.getIdentifiers().iterator().next(), writer);

            // IMEx as well
            if (!imexId.isEmpty()){
                writer.write(FIELD_SEPARATOR);
                writer.write(Xref.IMEX);
                writer.write(XREF_SEPARATOR);
                escapeAndWriteString(imexId.iterator().next().getId(), writer);
            }
        }
        // IMEx only
        else if (!imexId.isEmpty()) {
            writer.write(FIELD_SEPARATOR);
            writer.write(Xref.IMEX);
            writer.write(XREF_SEPARATOR);
            escapeAndWriteString(imexId.iterator().next().getId(), writer);
        }
        // nothing
        else{
            writer.write(EMPTY_COLUMN);
        }
    }

    /**
     * Writes the interaction identifiers of an interaction evidence.
     * This method will write the first interaction identifier and also the IMEx id
     * @param interaction
     * @param writer
     */
    public static void writeInteractionEvidenceIdentifiers(InteractionEvidence interaction, Writer writer) throws IOException {

        // other identfiers
        if (!interaction.getIdentifiers().isEmpty()){
            writeIdentifier(interaction.getIdentifiers().iterator().next(), writer);

            // IMEx as well
            if (interaction.getImexId() != null){
                writer.write(FIELD_SEPARATOR);
                writer.write(Xref.IMEX);
                writer.write(XREF_SEPARATOR);
                escapeAndWriteString(interaction.getImexId(), writer);
            }
        }
        // IMEx only
        else if (interaction.getImexId() != null) {
            writer.write(FIELD_SEPARATOR);
            writer.write(Xref.IMEX);
            writer.write(XREF_SEPARATOR);
            escapeAndWriteString(interaction.getImexId(), writer);
        }
        // nothing
        else{
            writer.write(EMPTY_COLUMN);
        }
    }

    /**
     * Writes the confidences of an interaction evidence
     * @param interaction
     * @param writer
     * @param isMitabExtendedConfidence : true if the confidences attached to this interaction are extended MITAB confidences with a text
     *                                  to write
     */
    public static void writeInteractionConfidences(InteractionEvidence interaction, Writer writer, boolean isMitabExtendedConfidence) throws IOException {

        if (!interaction.getConfidences().isEmpty()){

            Iterator<Confidence> confIterator = interaction.getConfidences().iterator();
            while (confIterator.hasNext()) {
                Confidence conf = confIterator.next();
                // write confidence
                if (isMitabExtendedConfidence){
                    MitabConfidence mitabConf = (MitabConfidence) conf;
                    writeConfidence(mitabConf, mitabConf.getText(), writer);
                }
                else{
                    writeConfidence(conf, null, writer);
                }

                if (confIterator.hasNext()){
                     writer.write(FIELD_SEPARATOR);
                }
            }
        }
        else {
            writer.write(EMPTY_COLUMN);
        }
    }

    /**
     * Writes the complex expansion of a binary interaction
     * @param binary
     * @param writer
     */
    public static void writeComplexExpansion(BinaryInteraction binary, Writer writer) throws IOException {

        if (binary.getComplexExpansion() != null){
            writeCvTerm(binary.getComplexExpansion(), writer);
        }
        else {
            writer.write(EMPTY_COLUMN);
        }
    }

    /**
     * Writes the biological role of a participant
     * @param participant
     * @param writer
     * @throws IOException
     */
    public static void writeBiologicalRole(Participant participant, Writer writer) throws IOException {

        if (participant != null){
            writeCvTerm(participant.getBiologicalRole(), writer);
        }
        else {
            writer.write(EMPTY_COLUMN);
        }
    }

    /**
     * Writes the experimentsl role of a participant evidence
     * @param participant
     * @param writer
     * @throws IOException
     */
    public static void writeExperimentalRole(ParticipantEvidence participant, Writer writer) throws IOException {

        if (participant != null){
            writeCvTerm(participant.getExperimentalRole(), writer);
        }
        else {
            writer.write(EMPTY_COLUMN);
        }
    }

    /**
     * Writes the interactor type of a participant
     * @param participant
     * @param writer
     * @throws IOException
     */
    public static void writeInteractorType(Participant participant, Writer writer) throws IOException {

        if (participant != null){
            writeCvTerm(participant.getInteractor().getInteractorType(), writer);
        }
        else {
            writer.write(EMPTY_COLUMN);
        }
    }

    /**
     * Write Xref of participant and interactor
     * @param participant
     * @param writer
     * @throws IOException
     */
    public static void writeParticipantXrefs(Participant participant, Writer writer) throws IOException {
        if (participant != null){

            // write interactor ref and participant ref
            if (!participant.getInteractor().getXrefs().isEmpty()){
                Iterator<Xref> interactorXrefIterator = participant.getInteractor().getXrefs().iterator();

                // write each interactor xref
                while (interactorXrefIterator.hasNext()) {
                    Xref ref = interactorXrefIterator.next();
                    writeXref(ref, writer);

                    if (interactorXrefIterator.hasNext()){
                        writer.write(FIELD_SEPARATOR);
                    }
                }

                // write participant xrefs
                if (!participant.getXrefs().isEmpty()){
                    writer.write(FIELD_SEPARATOR);
                    Iterator<Xref> participantXrefIterator = participant.getXrefs().iterator();
                    while (participantXrefIterator.hasNext()) {
                        Xref ref = participantXrefIterator.next();
                        writeXref(ref, writer);

                        if (participantXrefIterator.hasNext()){
                            writer.write(FIELD_SEPARATOR);
                        }
                    }
                }
            }
            // write participant ref only
            else if (!participant.getXrefs().isEmpty()){
                Iterator<Xref> participantXrefIterator = participant.getXrefs().iterator();
                while (participantXrefIterator.hasNext()) {
                    Xref ref = participantXrefIterator.next();
                    writeXref(ref, writer);

                    if (participantXrefIterator.hasNext()){
                        writer.write(FIELD_SEPARATOR);
                    }
                }
            }
            else{
                writer.write(EMPTY_COLUMN);
            }
        }
        else {
            writer.write(EMPTY_COLUMN);
        }
    }

    /**
     * Write interaction Xref r
     * @param interaction
     * @param writer
     * @throws IOException
     */
    public static void writeInteractionXrefs(Interaction interaction, Writer writer) throws IOException {
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
                        writer.write(FIELD_SEPARATOR);
                    }
                    // write xref ony if it is not an imex id
                    writeXref(next, writer);
                    isFirst = false;
                }
                else {
                    next = null;
                }
            }
            while (next != null) ;
        }
        else{
            writer.write(EMPTY_COLUMN);
        }
    }

    /**
     * Writes participant annotations
     * @param participant
     * @param writer
     */
    public static void writeParticipantAnnotations(Participant participant, Writer writer) throws IOException {

        if (participant != null){
            // writes interactor annotations first
            if (!participant.getInteractor().getAnnotations().isEmpty()){
                Iterator<Annotation> interactorAnnotationIterator = participant.getInteractor().getAnnotations().iterator();

                while (interactorAnnotationIterator.hasNext()){
                    Annotation annot = interactorAnnotationIterator.next();
                    writeAnnotation(annot, writer);

                    if(interactorAnnotationIterator.hasNext()){
                       writer.write(FIELD_SEPARATOR);
                    }
                }

                if (!participant.getAnnotations().isEmpty()){
                    writer.write(FIELD_SEPARATOR);
                    Iterator<Annotation> participantAnnotationIterator = participant.getAnnotations().iterator();

                    while (participantAnnotationIterator.hasNext()){
                        Annotation annot = participantAnnotationIterator.next();
                        writeAnnotation(annot, writer);

                        if(participantAnnotationIterator.hasNext()){
                            writer.write(FIELD_SEPARATOR);
                        }
                    }
                }
            }
            // writes participant annotations only
            else if (!participant.getAnnotations().isEmpty()){
                Iterator<Annotation> participantAnnotationIterator = participant.getAnnotations().iterator();

                while (participantAnnotationIterator.hasNext()){
                    Annotation annot = participantAnnotationIterator.next();
                    writeAnnotation(annot, writer);

                    if(participantAnnotationIterator.hasNext()){
                        writer.write(FIELD_SEPARATOR);
                    }
                }
            }
            else{
                writer.write(EMPTY_COLUMN);
            }
        }
        else{
            writer.write(EMPTY_COLUMN);
        }
    }

    /**
     * Writes interaction annotations
     * @param interaction
     * @param writer
     */
    public static void writeInteractionAnnotations(Interaction interaction, Writer writer) throws IOException {

        // writes interaction annotations first
        if (!interaction.getAnnotations().isEmpty()){
            Iterator<Annotation> interactorAnnotationIterator = interaction.getAnnotations().iterator();

            while (interactorAnnotationIterator.hasNext()){
                Annotation annot = interactorAnnotationIterator.next();
                writeAnnotation(annot, writer);

                if(interactorAnnotationIterator.hasNext()){
                    writer.write(FIELD_SEPARATOR);
                }
            }
        }
        else{
            writer.write(EMPTY_COLUMN);
        }
    }

    /**
     * Writes interaction evidence annotations .
     *
     * It will also add annotation from the publication that are used as a tag
     * @param interaction
     * @param writer
     */
    public static void writeInteractionEvidenceAnnotations(InteractionEvidence interaction, Writer writer) throws IOException {

        // writes interaction annotations first
        if (!interaction.getAnnotations().isEmpty()){
            Iterator<Annotation> interactorAnnotationIterator = interaction.getAnnotations().iterator();

            while (interactorAnnotationIterator.hasNext()){
                Annotation annot = interactorAnnotationIterator.next();
                writeAnnotation(annot, writer);

                if(interactorAnnotationIterator.hasNext()){
                    writer.write(FIELD_SEPARATOR);
                }
            }

            if (interaction.getExperiment() != null){
                Publication pub = interaction.getExperiment().getPublication();
                writer.write(FIELD_SEPARATOR);
                Iterator<Annotation> participantAnnotationIterator = interaction.getAnnotations().iterator();

                while (participantAnnotationIterator.hasNext()){
                    Annotation annot = participantAnnotationIterator.next();
                    writeAnnotation(annot, writer);

                    if(participantAnnotationIterator.hasNext()){
                        writer.write(FIELD_SEPARATOR);
                    }
                }
            }
        }
        else if (interaction.getExperiment() != null){
            Publication pub = interaction.getExperiment().getPublication();

            if (pub != null){

            }
        }
        else{
            writer.write(EMPTY_COLUMN);
        }
    }

    /**
     * Writes an annotation
     * @param annotation
     * @param writer
     * @throws IOException
     */
    public static void writeAnnotation(Annotation annotation, Writer writer) throws IOException {
        if (annotation != null){

            // write topic first
            if (annotation.getTopic().getFullName() != null){
                escapeAndWriteString(annotation.getTopic().getFullName(), writer);
            }
            else{
                escapeAndWriteString(annotation.getTopic().getShortName(), writer);
            }

            // write text after
            if (annotation.getValue() != null){
                writer.write(XREF_SEPARATOR);
                escapeAndWriteString(annotation.getValue(), writer);
            }
        }
    }

    /**
     * This methods write the database, id, version and qualifier of an xref
     * @param xref
     * @param writer
     * @throws IOException
     */
    public static void writeXref(Xref xref, Writer writer) throws IOException {
        if (xref != null){
            // write identifier first
            writeIdentifier(xref, writer);

            // write qualifier
            if (xref.getQualifier() != null){
                writer.write("(");
                escapeAndWriteString(xref.getQualifier().getShortName(), writer);
                writer.write(")");
            }
        }
    }

    /**
     * This method will write a confidence with a text if text is not null
     * @param conf
     * @param text
     * @param writer
     */
    public static void writeConfidence(Confidence conf, String text, Writer writer) throws IOException {

        if (conf != null){
            // write confidence type first
            if (conf.getType().getFullName() != null){
                 escapeAndWriteString(conf.getType().getFullName(), writer);
            }
            else{
                escapeAndWriteString(conf.getType().getShortName(), writer);
            }

            // write confidence value
            writer.write(XREF_SEPARATOR);
            escapeAndWriteString(conf.getValue(), writer);

            if (text != null){
                writer.write("(");
                escapeAndWriteString(text, writer);
                writer.write(")");
            }
        }
    }

    /**
     * Write an organism.
     * Will duplicate taxid if needs to provide both common name and scientific name
     * @param writer
     * @param organism
     * @throws IOException
     */
    public static void writeOrganism(Writer writer, Organism organism) throws IOException {
        if (organism != null){

            writer.write(TAXID);
            writer.write(XREF_SEPARATOR);
            writer.write(Integer.toString(organism.getTaxId()));

            // write common name if provided
            if (organism.getCommonName() != null){
                writer.write("(");
                escapeAndWriteString(organism.getCommonName(), writer);
                writer.write(")");

                // write scientific name if provided
                if (organism.getScientificName() != null){
                    writer.write(FIELD_SEPARATOR);
                    writer.write(TAXID);
                    writer.write(XREF_SEPARATOR);
                    writer.write(Integer.toString(organism.getTaxId()));
                    writer.write("(");
                    escapeAndWriteString(organism.getScientificName(), writer);
                    writer.write(")");
                }
            }
            // write scientific name if provided
            else if (organism.getScientificName() != null){
                writer.write("(");
                escapeAndWriteString(organism.getScientificName(), writer);
                writer.write(")");
            }
        }
        else {
            writer.write(EMPTY_COLUMN);
        }
    }

    /**
     * Write the CvTerm. If it is null, it writes an empty column (-)
     * @param cv
     * @param writer
     */
    public static void writeCvTerm(CvTerm cv, Writer writer) throws IOException {
        if (cv != null){
            // write MI xref first
            if (cv.getMIIdentifier() != null){
                writer.write(CvTerm.PSI_MI);
                writer.write(XREF_SEPARATOR);
                writer.write("\"");
                writer.write(cv.getMIIdentifier());
                writer.write("\"");

                // write cv name
                writer.write("(");
                writeCvTermName(cv, writer);
                writer.write(")");
            }
            // write MOD xref
            else if (cv.getMODIdentifier() != null){
                writer.write(CvTerm.PSI_MOD);
                writer.write(XREF_SEPARATOR);
                writer.write("\"");
                writer.write(cv.getMODIdentifier());
                writer.write("\"");

                // write cv name
                writer.write("(");
                writeCvTermName(cv, writer);
                writer.write(")");
            }
            // write PAR xref
            else if (cv.getPARIdentifier() != null){
                writer.write(CvTerm.PSI_PAR);
                writer.write(XREF_SEPARATOR);
                writer.write("\"");
                writer.write(cv.getPARIdentifier());
                writer.write("\"");

                // write cv name
                writer.write("(");
                writeCvTermName(cv, writer);
                writer.write(")");
            }
            // write first identifier
            else if (!cv.getIdentifiers().isEmpty()) {
                writeIdentifier(cv.getIdentifiers().iterator().next(), writer);

                // write cv name
                writer.write("(");
                writeCvTermName(cv, writer);
                writer.write(")");
            }
            // write empty column
            else{
                writer.write(EMPTY_COLUMN);
            }
        }
        else {
            writer.write(EMPTY_COLUMN);
        }
    }

    /**
     * This methods write the dbsource, alias name and alias type of an alias
     * @param alias
     * @param dbSource
     * @param writer
     * @throws IOException
     */
    public static void writeAlias(Alias alias, String dbSource, Writer writer) throws IOException {
        if (alias != null){
            // write db first
            if (dbSource == null){
                writer.write(UNKNOWN_DATABASE);
            }
            else{
                escapeAndWriteString(dbSource, writer);
            }
            // write xref separator
            writer.write(XREF_SEPARATOR);
            // write name
            escapeAndWriteString(alias.getName(), writer);
            // write type
            if (alias.getType() != null){
                escapeAndWriteString(alias.getType().getShortName(), writer);
            }
        }
    }

    /**
     * This methods write the database, id and version of an identifier
     * @param identifier
     * @param writer
     * @throws IOException
     */
    public static void writeIdentifier(Xref identifier, Writer writer) throws IOException {
        if (identifier != null){
            // write db first
            escapeAndWriteString(identifier.getDatabase().getShortName(), writer);
            // write xref separator
            writer.write(XREF_SEPARATOR);
            // write id
            escapeAndWriteString(identifier.getId(), writer);
            // write version
            if (identifier.getVersion() != null){
                writer.write(identifier.getVersion());
            }
        }
    }

    /**
     * This method replaces line breaks and tab characters with a space.
     *
     * It escapes the StringToEscape with doble quote if it finds a special MITAB character
     * @param stringToEscape
     * @param writer
     * @throws IOException
     */
    public static void escapeAndWriteString(String stringToEscape, Writer writer) throws IOException {

        // replace first tabs and break line with a space
        stringToEscape.replaceAll(LINE_BREAK+"|"+COLUMN_SEPARATOR, " ");

        for (String special : SPECIAL_CHARACTERS){

            if (stringToEscape.contains(special)){
                writer.write("\"");
                writer.write(stringToEscape);
                writer.write("\"");
                return;
            }
        }

        writer.write(stringToEscape);
    }

    /**
     * The source of the Alias is uniprotkb if the alias type is gene name, gene name synonym, isoform synonym,
     * locus name or orf name. It is unknown otherwise.
     * @param alias
     * @return the default dbsource for this alias
     */
    public static String findDbSourceForAlias(Alias alias){

        // these aliases should come from uniprotkb
        if (AliasUtils.doesAliasHaveType(alias, Alias.GENE_NAME_MI, Alias.GENE_NAME)
                || AliasUtils.doesAliasHaveType(alias, Alias.GENE_NAME_SYNONYM_MI, Alias.GENE_NAME_SYNONYM)
                || AliasUtils.doesAliasHaveType(alias, Alias.ISOFORM_SYNONYM_MI, Alias.ISOFORM_SYNONYM)
                || AliasUtils.doesAliasHaveType(alias, Alias.LOCUS_NAME_MI, Alias.LOCUS_NAME)
                || AliasUtils.doesAliasHaveType(alias, Alias.ORF_NAME_MI, Alias.ORF_NAME)){
            return Xref.UNIPROTKB;
        }

        return UNKNOWN_DATABASE;
    }

    /**
     * The source of the Alias is uniprotkb if the alias type is gene name, gene name synonym, isoform synonym,
     * locus name or orf name. If the participant evidence has an interaction evidence with an experiment, publication and source,
     * it will return the source shortname. It is unknown otherwise.
     * @param participant
     * @param alias
     * @return the default dbsource for this alias and participant
     */
    public static String findDbSourceForAlias(ParticipantEvidence participant, Alias alias){

        if (participant != null){

            // these aliases should come from uniprotkb
            if (AliasUtils.doesAliasHaveType(alias, Alias.GENE_NAME_MI, Alias.GENE_NAME)
                    || AliasUtils.doesAliasHaveType(alias, Alias.GENE_NAME_SYNONYM_MI, Alias.GENE_NAME_SYNONYM)
                    || AliasUtils.doesAliasHaveType(alias, Alias.ISOFORM_SYNONYM_MI, Alias.ISOFORM_SYNONYM)
                    || AliasUtils.doesAliasHaveType(alias, Alias.LOCUS_NAME_MI, Alias.LOCUS_NAME)
                    || AliasUtils.doesAliasHaveType(alias, Alias.ORF_NAME_MI, Alias.ORF_NAME)){
                return Xref.UNIPROTKB;
            }
            // check source
            else if (participant.getInteractionEvidence() != null){
                InteractionEvidence interaction = participant.getInteractionEvidence();
                if (interaction.getExperiment() != null){
                    Experiment exp = interaction.getExperiment();
                    if (exp.getPublication() != null){
                        Publication pub = exp.getPublication();
                        if (pub.getSource() != null){
                            return pub.getSource().getShortName();
                        }
                    }
                }
            }
        }

        return UNKNOWN_DATABASE;
    }

    /**
     * The source of the Alias is uniprotkb if the alias type is gene name, gene name synonym, isoform synonym,
     * locus name or orf name. If the participant has an modelled interaction with a source,
     * it will return the source shortname. It is unknown otherwise.
     * @param participant
     * @param alias
     * @return
     */
    public static String findDbSourceForAlias(ModelledParticipant participant, Alias alias){

        if (participant != null){

            // these aliases should come from uniprotkb
            if (AliasUtils.doesAliasHaveType(alias, Alias.GENE_NAME_MI, Alias.GENE_NAME)
                    || AliasUtils.doesAliasHaveType(alias, Alias.GENE_NAME_SYNONYM_MI, Alias.GENE_NAME_SYNONYM)
                    || AliasUtils.doesAliasHaveType(alias, Alias.ISOFORM_SYNONYM_MI, Alias.ISOFORM_SYNONYM)
                    || AliasUtils.doesAliasHaveType(alias, Alias.LOCUS_NAME_MI, Alias.LOCUS_NAME)
                    || AliasUtils.doesAliasHaveType(alias, Alias.ORF_NAME_MI, Alias.ORF_NAME)){
                return Xref.UNIPROTKB;
            }
            // check source
            else if (participant.getModelledInteraction() != null){
                ModelledInteraction interaction = participant.getModelledInteraction();
                if (interaction.getSource() != null){
                    return interaction.getSource().getShortName();
                }
            }
        }

        return UNKNOWN_DATABASE;
    }

    /**
     * Write full name if not null, otherwise write shortname
     * @param cv
     * @param writer
     * @throws IOException
     */
    private static void writeCvTermName(CvTerm cv, Writer writer) throws IOException {
        if (cv.getFullName() != null){
            escapeAndWriteString(cv.getFullName(), writer);
        }
        else{
            escapeAndWriteString(cv.getShortName(), writer);
        }
    }

    private static void writePublicationImexId(Writer writer, Publication pub) throws IOException {
        // IMEx as well
        if (pub.getImexId() != null) {
            writer.write(Xref.IMEX);
            writer.write(XREF_SEPARATOR);
            escapeAndWriteString(pub.getImexId(), writer);
        }
    }
}
