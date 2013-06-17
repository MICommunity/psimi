package psidev.psi.mi.jami.mitab.io.writer;

import psidev.psi.mi.jami.binary.BinaryInteraction;
import psidev.psi.mi.jami.binary.BinaryInteractionEvidence;
import psidev.psi.mi.jami.binary.ModelledBinaryInteraction;
import psidev.psi.mi.jami.binary.expansion.ComplexExpansionMethod;
import psidev.psi.mi.jami.binary.expansion.SpokeExpansion;
import psidev.psi.mi.jami.datasource.InteractionDataSourceWriter;
import psidev.psi.mi.jami.datasource.InteractionWriterFactory;
import psidev.psi.mi.jami.exception.DataSourceWriterException;
import psidev.psi.mi.jami.tab.MitabColumnName;
import psidev.psi.mi.jami.tab.MitabVersion;
import psidev.psi.mi.jami.mitab.utils.MitabWriterUtils;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.utils.XrefUtils;

import java.io.*;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

/**
 * Abstract writer for Mitab 2.5.
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
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>11/06/13</pre>
 */

public abstract class AbstractMitab25Writer implements InteractionDataSourceWriter{

    private Writer writer;
    private ComplexExpansionMethod expansionMethod;
    private boolean isInitialised = false;
    private MitabVersion version = MitabVersion.v2_5;
    private boolean writeHeader = true;
    private boolean hasWrittenHeader = false;

    public AbstractMitab25Writer(){

    }

    public AbstractMitab25Writer(File file) throws IOException {

        initialiseFile(file);
        this.expansionMethod = new SpokeExpansion();
        isInitialised = true;
    }

    public AbstractMitab25Writer(OutputStream output) throws IOException {

        initialiseOutputStream(output);
        this.expansionMethod = new SpokeExpansion();
        isInitialised = true;
    }

    public AbstractMitab25Writer(Writer writer) throws IOException {

        initialiseWriter(writer);
        this.expansionMethod = new SpokeExpansion();
        isInitialised = true;
    }

    public AbstractMitab25Writer(File file, ComplexExpansionMethod expansionMethod) throws IOException {

        initialiseFile(file);
        initialiseExpansionMethod(expansionMethod);
        isInitialised = true;
    }

    public AbstractMitab25Writer(OutputStream output, ComplexExpansionMethod expansionMethod) throws IOException {

        initialiseOutputStream(output);
        initialiseExpansionMethod(expansionMethod);
        isInitialised = true;
    }

    public AbstractMitab25Writer(Writer writer, ComplexExpansionMethod expansionMethod) throws IOException {

        initialiseWriter(writer);
        initialiseExpansionMethod(expansionMethod);
        isInitialised = true;
    }

    public ComplexExpansionMethod getExpansionMethod() {
        return expansionMethod;
    }

    public MitabVersion getVersion() {
        return version;
    }

    protected void setVersion(MitabVersion version){
        this.version = version;
    }

    public boolean isWriteHeader() {
        return writeHeader;
    }

    public void setWriteHeader(boolean writeHeader) {
        this.writeHeader = writeHeader;
    }

    public Writer getWriter() {
        return writer;
    }

    public void initialiseContext(Map<String, Object> options) throws DataSourceWriterException {

        if (options == null && !isInitialised){
            throw new IllegalArgumentException("The options for the Mitab25Writer should contain at least "+ InteractionWriterFactory.OUTPUT_FILE_OPTION_KEY
                    + " or " + InteractionWriterFactory.OUTPUT_STREAM_OPTION_KEY + " or " + InteractionWriterFactory.WRITER_OPTION_KEY + " to know where to write the interactions.");
        }
        else if (options == null){
             return;
        }
        else if (options.containsKey(InteractionWriterFactory.OUTPUT_FILE_OPTION_KEY)){
            try {
                initialiseFile((File) options.get(InteractionWriterFactory.OUTPUT_FILE_OPTION_KEY));
            } catch (IOException e) {
                throw new DataSourceWriterException("Impossible to open output file", e);
            }
        }
        else if (options.containsKey(InteractionWriterFactory.OUTPUT_STREAM_OPTION_KEY)){
            initialiseOutputStream((OutputStream) options.get(InteractionWriterFactory.OUTPUT_STREAM_OPTION_KEY));
        }
        else if (options.containsKey(InteractionWriterFactory.WRITER_OPTION_KEY)){
            initialiseWriter((Writer) options.get(InteractionWriterFactory.WRITER_OPTION_KEY));
        }
        else if (!isInitialised){
            throw new IllegalArgumentException("The options for the Mitab25Writer should contain at least "+InteractionWriterFactory.OUTPUT_FILE_OPTION_KEY
                    + " or " + InteractionWriterFactory.OUTPUT_STREAM_OPTION_KEY + " or " + InteractionWriterFactory.WRITER_OPTION_KEY + " to know where to write the interactions.");
        }

        if (options.containsKey(InteractionWriterFactory.COMPLEX_EXPANSION_OPTION_KEY)){
            try {
                initialiseExpansionMethod(((Class<? extends ComplexExpansionMethod>)options.get(InteractionWriterFactory.COMPLEX_EXPANSION_OPTION_KEY)).newInstance());
            } catch (InstantiationException e) {
                throw new DataSourceWriterException("Impossible to initialise the complex expansion method ", e);
            } catch (IllegalAccessException e) {
                throw new DataSourceWriterException("Impossible to initialise the complex expansion method ", e);
            }
        }

        isInitialised = true;
    }

    public void write(Interaction interaction) throws DataSourceWriterException {
        if (!isInitialised){
            throw new IllegalStateException("The Mitab25Writer has not been initialised with a map of options." +
                    "The options for the Mitab25Writer should contain at least "+InteractionWriterFactory.OUTPUT_FILE_OPTION_KEY
                    + " or " + InteractionWriterFactory.OUTPUT_STREAM_OPTION_KEY + " or " + InteractionWriterFactory.WRITER_OPTION_KEY + " to know where to write the interactions.");
        }

        if (interaction != null){

            Collection<BinaryInteraction> binaryInteractions = expansionMethod.expandInteraction(interaction);
            try {
                writeBinaryInteractions(binaryInteractions);
            } catch (IOException e) {
                throw new DataSourceWriterException("Impossible to write " +interaction.toString(), e);
            }
        }
    }

    public void write(InteractionEvidence interaction) throws DataSourceWriterException {
        if (!isInitialised){
            throw new IllegalStateException("The Mitab25Writer has not been initialised with a map of options." +
                    "The options for the Mitab25Writer should contain at least "+InteractionWriterFactory.OUTPUT_FILE_OPTION_KEY
                    + " or " + InteractionWriterFactory.OUTPUT_STREAM_OPTION_KEY + " or " + InteractionWriterFactory.WRITER_OPTION_KEY + " to know where to write the interactions.");
        }

        if (interaction != null){

            Collection<BinaryInteractionEvidence> binaryInteractions = expansionMethod.expandInteractionEvidence(interaction);
            try {
                writeBinaryInteractionEvidences(binaryInteractions);
            } catch (IOException e) {
                throw new DataSourceWriterException("Impossible to write " +interaction.toString(), e);
            }
        }
    }

    public void write(ModelledInteraction interaction) throws DataSourceWriterException {
        if (!isInitialised){
            throw new IllegalStateException("The Mitab25Writer has not been initialised with a map of options." +
                    "The options for the Mitab25Writer should contain at least "+InteractionWriterFactory.OUTPUT_FILE_OPTION_KEY
                    + " or " + InteractionWriterFactory.OUTPUT_STREAM_OPTION_KEY + " or " + InteractionWriterFactory.WRITER_OPTION_KEY + " to know where to write the interactions.");
        }

        if (interaction != null){

            Collection<ModelledBinaryInteraction> binaryInteractions = expansionMethod.expandModelledInteraction(interaction);
            try {
                writeModelledBinaryInteractions(binaryInteractions);
            } catch (IOException e) {
                throw new DataSourceWriterException("Impossible to write " +interaction.toString(), e);
            }
        }
    }

    public void writeInteractions(Collection<Interaction> interactions) throws DataSourceWriterException {
        for (Interaction interaction : interactions){
            write(interaction);
        }
    }

    public void writeInteractionEvidences(Collection<InteractionEvidence> interactions) throws DataSourceWriterException{
        for (InteractionEvidence interaction : interactions){
            write(interaction);
        }
    }

    public void writeModelledInteractions(Collection<ModelledInteraction> interactions) throws DataSourceWriterException{
        for (ModelledInteraction interaction : interactions){
            write(interaction);
        }
    }

    /**
     * Writes a binary interaction.
     * Does not write any extended properties from participants, interaction and features
     * This method will write empty columns for interaction detection method, publication author and identifier,
     * source and confidences.
     * It will also ignore experimental roles, host organism, interaction parameters and participant identification methods
     * @param interaction
     * @throws IOException
     */
    public void writeBinary(BinaryInteraction interaction) throws IOException {
        writeHeaderAndLineBreakIfNotDone();

        Participant A = interaction.getParticipantA();
        Participant B = interaction.getParticipantB();
        writeBinary(interaction, A, B);

    }

    /**
     * Writes a binary interaction evidence.
     * This method will write all experimental fields as well
     * @param interaction
     * @throws IOException
     */
    public void writeBinaryEvidence(BinaryInteractionEvidence interaction) throws IOException {
        writeHeaderAndLineBreakIfNotDone();

        ParticipantEvidence A = interaction.getParticipantA();
        ParticipantEvidence B = interaction.getParticipantB();
        writeBinaryEvidence(interaction, A, B);
    }

    /**
     * Writes a modelled binary interaction.
     * This method will write empty columns for interaction detection method, publication author and identifier and confidences.
     * It will also ignore experimental roles, host organism and participant identification methods
     * @param interaction
     * @throws IOException
     */
    public void writeModelledBinary(ModelledBinaryInteraction interaction) throws IOException {
        writeHeaderAndLineBreakIfNotDone();

        ModelledParticipant A = interaction.getParticipantA();
        ModelledParticipant B = interaction.getParticipantB();
        writeModelledBinary(interaction, A, B);
    }

    /**
     * Writes a collection of binary interactions.
     * Does not write any extended properties from participants, interaction and features
     * This method will write empty columns for interaction detection method, publication author and identifier,
     * source and confidences.
     * It will also ignore experimental roles, host organism, interaction parameters and participant identification methods
     * @param interactions
     * @throws IOException
     */
    public void writeBinaryInteractions(Collection<BinaryInteraction> interactions) throws IOException {

        Iterator<BinaryInteraction> binaryIterator = interactions.iterator();
        while(binaryIterator.hasNext()){
            writeBinary(binaryIterator.next());
        }
    }

    /**
     * Writes a collection of binary interaction evidences.
     * This method will write all experimental fields as well
     * @param interactions
     * @throws IOException
     */
    public void writeBinaryInteractionEvidences(Collection<BinaryInteractionEvidence> interactions) throws IOException {
        Iterator<BinaryInteractionEvidence> binaryIterator = interactions.iterator();
        while(binaryIterator.hasNext()){
            writeBinaryEvidence(binaryIterator.next());
        }
    }

    /**
     * Writes a collection of modelled binary interaction.
     * This method will write empty columns for interaction detection method, publication author and identifier and confidences.
     * It will also ignore experimental roles, host organism and participant identification methods
     * @param interactions
     * @throws IOException
     */
    public void writeModelledBinaryInteractions(Collection<ModelledBinaryInteraction> interactions) throws IOException {
        Iterator<ModelledBinaryInteraction> binaryIterator = interactions.iterator();
        while(binaryIterator.hasNext()){
            writeModelledBinary(binaryIterator.next());
        }
    }

    public void flush() throws DataSourceWriterException{
        if (isInitialised){
            try {
                writer.flush();
            } catch (IOException e) {
                throw new DataSourceWriterException("Impossible to flush the MITAB writer", e);
            }
        }
    }

    public void close() throws DataSourceWriterException{
        if (isInitialised){
            try {
                writer.close();
            } catch (IOException e) {
                throw new DataSourceWriterException("Impossible to close the MITAB writer", e);
            }

            isInitialised = false;
            writer = null;
            expansionMethod = new SpokeExpansion();
            writeHeader = true;
            version = MitabVersion.v2_5;
            hasWrittenHeader = false;
        }
    }

    /**
     * Writes the binary interaction and its participants in MITAB 2.5
     * @param interaction
     * @param a
     * @param b
     * @throws IOException
     */
    protected void writeBinary(BinaryInteraction interaction, Participant a, Participant b) throws IOException {
        // id A
        writeUniqueIdentifier(a);
        writer.write(MitabWriterUtils.COLUMN_SEPARATOR);
        // id B
        writeUniqueIdentifier(b);
        writer.write(MitabWriterUtils.COLUMN_SEPARATOR);
        // altid A
        writeAlternativeIdentifiers(a);
        writer.write(MitabWriterUtils.COLUMN_SEPARATOR);
        // altid B
        writeAlternativeIdentifiers(b);
        writer.write(MitabWriterUtils.COLUMN_SEPARATOR);
        // aliases
        // alias A
        writeAliases(a);
        getWriter().write(MitabWriterUtils.COLUMN_SEPARATOR);
        // alias B
        writeAliases(b);
        writer.write(MitabWriterUtils.COLUMN_SEPARATOR);

        // skip detection method
        writer.write(MitabWriterUtils.EMPTY_COLUMN);
        writer.write(MitabWriterUtils.COLUMN_SEPARATOR);
        // skip pub author
        writer.write(MitabWriterUtils.EMPTY_COLUMN);
        writer.write(MitabWriterUtils.COLUMN_SEPARATOR);
        // skip publication identifier
        writer.write(MitabWriterUtils.EMPTY_COLUMN);
        writer.write(MitabWriterUtils.COLUMN_SEPARATOR);
        // taxid A
        writeInteractorOrganism(a);
        writer.write(MitabWriterUtils.COLUMN_SEPARATOR);
        // taxid B
        writeInteractorOrganism(b);
        writer.write(MitabWriterUtils.COLUMN_SEPARATOR);
        // interaction type
        writeInteractionType(interaction);
        writer.write(MitabWriterUtils.COLUMN_SEPARATOR);
        // skip source identifier
        writer.write(MitabWriterUtils.EMPTY_COLUMN);
        writer.write(MitabWriterUtils.COLUMN_SEPARATOR);
        // interaction identifiers
        writeInteractionIdentifiers(interaction);
        writer.write(MitabWriterUtils.COLUMN_SEPARATOR);
        // skip interaction confidence
        writer.write(MitabWriterUtils.EMPTY_COLUMN);
    }

    /**
     * Writes the binary interaction evidence and its participants in MITAB 2.5
     * @param interaction
     * @param a
     * @param b
     * @throws IOException
     */
    protected void writeBinaryEvidence(BinaryInteractionEvidence interaction, ParticipantEvidence a, ParticipantEvidence b) throws IOException {
        // id A
        writeUniqueIdentifier(a);
        writer.write(MitabWriterUtils.COLUMN_SEPARATOR);
        // id B
        writeUniqueIdentifier(b);
        writer.write(MitabWriterUtils.COLUMN_SEPARATOR);
        // altid A
        writeAlternativeIdentifiers(a);
        writer.write(MitabWriterUtils.COLUMN_SEPARATOR);
        // altid B
        writeAlternativeIdentifiers(b);
        writer.write(MitabWriterUtils.COLUMN_SEPARATOR);
        // aliases
        // alias A
        writeAliases(a);
        writer.write(MitabWriterUtils.COLUMN_SEPARATOR);
        // alias B
        writeAliases(b);
        writer.write(MitabWriterUtils.COLUMN_SEPARATOR);
        // write detection method
        writeInteractionDetectionMethod(interaction.getExperiment());
        writer.write(MitabWriterUtils.COLUMN_SEPARATOR);
        // write pub author
        writeFirstAuthor(interaction.getExperiment());
        writer.write(MitabWriterUtils.COLUMN_SEPARATOR);
        // write publication identifier
        writePublicationIdentifiers(interaction.getExperiment());
        writer.write(MitabWriterUtils.COLUMN_SEPARATOR);
        // taxid A
        writeInteractorOrganism(a);
        writer.write(MitabWriterUtils.COLUMN_SEPARATOR);
        // taxid B
        writeInteractorOrganism(b);
        writer.write(MitabWriterUtils.COLUMN_SEPARATOR);
        // interaction type
        writeInteractionType(interaction);
        writer.write(MitabWriterUtils.COLUMN_SEPARATOR);
        // write source identifier
        writeSource(interaction.getExperiment());
        writer.write(MitabWriterUtils.COLUMN_SEPARATOR);
        // interaction identifiers
        writeInteractionIdentifiers(interaction);
        writer.write(MitabWriterUtils.COLUMN_SEPARATOR);
        // confidences
        // write interaction confidence
        writeInteractionConfidences(interaction);
    }

    /**
     * Writes the modelled binary interaction and its participants in MITAB 2.5
     * @param interaction
     * @param a
     * @param b
     * @throws IOException
     */
    protected void writeModelledBinary(ModelledBinaryInteraction interaction, ModelledParticipant a, ModelledParticipant b) throws IOException {
        // id A
        writeUniqueIdentifier(a);
        writer.write(MitabWriterUtils.COLUMN_SEPARATOR);
        // id B
        writeUniqueIdentifier(b);
        writer.write(MitabWriterUtils.COLUMN_SEPARATOR);
        // altid A
        writeAlternativeIdentifiers(a);
        writer.write(MitabWriterUtils.COLUMN_SEPARATOR);
        // altid B
        writeAlternativeIdentifiers(b);
        writer.write(MitabWriterUtils.COLUMN_SEPARATOR);
        // aliases
        // alias A
        writeAliases(a);
        getWriter().write(MitabWriterUtils.COLUMN_SEPARATOR);
        // alias B
        writeAliases(b);
        writer.write(MitabWriterUtils.COLUMN_SEPARATOR);

        // skip detection method
        writer.write(MitabWriterUtils.EMPTY_COLUMN);
        writer.write(MitabWriterUtils.COLUMN_SEPARATOR);
        // skip pub author
        writer.write(MitabWriterUtils.EMPTY_COLUMN);
        writer.write(MitabWriterUtils.COLUMN_SEPARATOR);
        // skip publication identifier
        writer.write(MitabWriterUtils.EMPTY_COLUMN);
        writer.write(MitabWriterUtils.COLUMN_SEPARATOR);
        // taxid A
        writeInteractorOrganism(a);
        writer.write(MitabWriterUtils.COLUMN_SEPARATOR);
        // taxid B
        writeInteractorOrganism(b);
        writer.write(MitabWriterUtils.COLUMN_SEPARATOR);
        // interaction type
        writeInteractionType(interaction);
        writer.write(MitabWriterUtils.COLUMN_SEPARATOR);
        // write source identifier
        writeSource(interaction);
        writer.write(MitabWriterUtils.COLUMN_SEPARATOR);
        // interaction identifiers
        writeInteractionIdentifiers(interaction);
        writer.write(MitabWriterUtils.COLUMN_SEPARATOR);
        // confidences
        // write interaction confidence
        writeInteractionConfidences(interaction);
    }

    /**
     * Write the header
     * @throws IOException
     */
    protected void writeHeader() throws IOException{
        writer.write(MitabWriterUtils.COMMENT_PREFIX);
        writer.write(" ");

        for (MitabColumnName colName : MitabColumnName.values()) {
            writer.write(colName.toString());
            // starts with 0
            if (colName.ordinal() < version.getNumberOfColumns() - 1){
                writer.write(MitabWriterUtils.COLUMN_SEPARATOR);
            }
            else {
                break;
            }
        }
    }

    /**
     * This method will write the unique identifier of a participant
     * @param participant
     * @throws IOException
     */
    protected void writeUniqueIdentifier(Participant participant) throws IOException {

        if (participant == null){
            writer.write(MitabWriterUtils.EMPTY_COLUMN);
        }
        else {
            Interactor interactor = participant.getInteractor();
            // write first identifier
            if (!interactor.getIdentifiers().isEmpty()){
                writeIdentifier(interactor.getIdentifiers().iterator().next());
            }
            else{
                writer.write(MitabWriterUtils.EMPTY_COLUMN);
            }
        }
    }

    /**
     * This method writes all the remaining identifiers (ignore the first identifier) of the participant
     * @param participant
     * @throws IOException
     */
    protected void writeAlternativeIdentifiers(Participant participant) throws IOException {

        if (participant == null){
            writer.write(MitabWriterUtils.EMPTY_COLUMN);
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
                        writer.write(MitabWriterUtils.FIELD_SEPARATOR);
                    }
                }
            }
            else{
                writer.write(MitabWriterUtils.EMPTY_COLUMN);
            }
        }
    }

    /**
     * This method writes all the aliases of the participant
     * @param participant
     * @throws IOException
     */
    protected void writeAliases(Participant participant) throws IOException {

        if (participant == null){
            writer.write(MitabWriterUtils.EMPTY_COLUMN);
        }
        else {
            Interactor interactor = participant.getInteractor();
            // write aliases
            if (!interactor.getAliases().isEmpty()){
                Iterator<Alias> aliasIterator = interactor.getAliases().iterator();

                while (aliasIterator.hasNext()){
                    writeAlias(aliasIterator.next());
                    // write field separator
                    if (aliasIterator.hasNext()){
                        writer.write(MitabWriterUtils.FIELD_SEPARATOR);
                    }
                }
            }
            else{
                writer.write(MitabWriterUtils.EMPTY_COLUMN);
            }
        }
    }

    /**
     * Writes the interaction detection method of the experiment.
     * @param experiment
     */
    protected void writeInteractionDetectionMethod(Experiment experiment) throws IOException {
        if (experiment != null){
            writeCvTerm(experiment.getInteractionDetectionMethod());
        }
        else{
            writer.write(MitabWriterUtils.EMPTY_COLUMN);
        }
    }

    /**
     * Writes the first author of a publication in an experiment
     * @param experiment
     */
    protected void writeFirstAuthor(Experiment experiment) throws IOException {
        if (experiment != null){
            Publication pub = experiment.getPublication();

            if (pub != null){
                // authors and maybe publication date
                if (!pub.getAuthors().isEmpty()){
                    escapeAndWriteString(pub.getAuthors().iterator().next() + MitabWriterUtils.AUTHOR_SUFFIX);
                    if (pub.getPublicationDate() != null){
                        writer.write(" (");
                        writer.write(MitabWriterUtils.PUBLICATION_YEAR_FORMAT.format(pub.getPublicationDate()));
                        writer.write(")");
                    }
                }
                // publication date only
                else if (pub.getPublicationDate() != null){
                    writer.write("unknown (");
                    writer.write(MitabWriterUtils.PUBLICATION_YEAR_FORMAT.format(pub.getPublicationDate()));
                    writer.write(")");
                }
                else {
                    writer.write(MitabWriterUtils.EMPTY_COLUMN);
                }
            }
            else{
                writer.write(MitabWriterUtils.EMPTY_COLUMN);
            }
        }
        else{
            writer.write(MitabWriterUtils.EMPTY_COLUMN);
        }
    }

    /**
     * Writes the publication identifiers of a publication in an experiment.
     * This method will write the first publication identifier (pubmed before doi) and also the IMEx id
     * @param experiment
     */
    protected void writePublicationIdentifiers(Experiment experiment) throws IOException {
        if (experiment != null){
            Publication pub = experiment.getPublication();

            if (pub != null){
                // identifiers
                if (pub.getPubmedId() != null){
                    writer.write(Xref.PUBMED);
                    writer.write(MitabWriterUtils.XREF_SEPARATOR);
                    escapeAndWriteString(pub.getPubmedId());

                    // IMEx as well
                    writer.write(MitabWriterUtils.FIELD_SEPARATOR);
                    writePublicationImexId(pub);
                }
                // doi only
                else if (pub.getDoi() != null){
                    writer.write(Xref.DOI);
                    writer.write(MitabWriterUtils.XREF_SEPARATOR);
                    escapeAndWriteString(pub.getDoi());

                    // IMEx as well
                    writer.write(MitabWriterUtils.FIELD_SEPARATOR);
                    writePublicationImexId(pub);
                }
                // other identfiers
                else if (!pub.getIdentifiers().isEmpty()){
                    writeIdentifier(pub.getIdentifiers().iterator().next());

                    // IMEx as well
                    writer.write(MitabWriterUtils.FIELD_SEPARATOR);
                    writePublicationImexId(pub);
                }
                // IMEx only
                else if (pub.getImexId() != null) {
                    writePublicationImexId(pub);
                }
                // nothing
                else{
                    writer.write(MitabWriterUtils.EMPTY_COLUMN);
                }
            }
            else{
                writer.write(MitabWriterUtils.EMPTY_COLUMN);
            }
        }
        else{
            writer.write(MitabWriterUtils.EMPTY_COLUMN);
        }
    }

    /**
     * Writes the organism of a participant.
     * Empty column if the organism is not provided
     * @param participant
     */
    protected void writeInteractorOrganism(Participant participant) throws IOException {
        if (participant != null){
            Interactor interactor = participant.getInteractor();

            writeOrganism(interactor.getOrganism());
        }
        else{
            writer.write(MitabWriterUtils.EMPTY_COLUMN);
        }
    }

    /**
     * Writes the interaction type of an interaction
     * @param interaction
     */
    protected void writeInteractionType(Interaction interaction) throws IOException {

        writeCvTerm(interaction.getInteractionType());
    }

    /**
     * Writes the interaction source from the publication of the experiment
     * @param experiment
     */
    protected void writeSource(Experiment experiment) throws IOException {
        if (experiment == null){
            Publication pub = experiment.getPublication();

            if (pub != null){
                writeCvTerm(pub.getSource());
            }
            else {
                writer.write(MitabWriterUtils.EMPTY_COLUMN);
            }
        }
        else{
            writer.write(MitabWriterUtils.EMPTY_COLUMN);
        }
    }

    /**
     * Writes the interaction source from the modelled interaction
     * @param interaction
     */
    protected void writeSource(ModelledInteraction interaction) throws IOException {
        writeCvTerm(interaction.getSource());
    }

    /**
     * Writes the interaction identifiers of an interaction.
     * This method will write the first interaction identifier and also the IMEx id
     * @param interaction
     */
    protected void writeInteractionIdentifiers(Interaction interaction) throws IOException {

        // get imex id
        Collection<Xref> imexId = XrefUtils.collectAllXrefsHavingDatabaseAndQualifier(interaction.getXrefs(), Xref.IMEX_MI, Xref.IMEX, Xref.IMEX_PRIMARY_MI, Xref.IMEX_PRIMARY);

        // other identfiers
        if (!interaction.getIdentifiers().isEmpty()){
            writeIdentifier(interaction.getIdentifiers().iterator().next());

            // IMEx as well
            if (!imexId.isEmpty()){
                writer.write(MitabWriterUtils.FIELD_SEPARATOR);
                writer.write(Xref.IMEX);
                writer.write(MitabWriterUtils.XREF_SEPARATOR);
                escapeAndWriteString(imexId.iterator().next().getId());
            }
        }
        // IMEx only
        else if (!imexId.isEmpty()) {
            writer.write(MitabWriterUtils.FIELD_SEPARATOR);
            writer.write(Xref.IMEX);
            writer.write(MitabWriterUtils.XREF_SEPARATOR);
            escapeAndWriteString(imexId.iterator().next().getId());
        }
        // nothing
        else{
            writer.write(MitabWriterUtils.EMPTY_COLUMN);
        }
    }

    /**
     * Writes the interaction identifiers of an interaction evidence.
     * This method will write the first interaction identifier and also the IMEx id
     * @param interaction
     */
    protected void writeInteractionEvidenceIdentifiers(InteractionEvidence interaction) throws IOException {

        // other identfiers
        if (!interaction.getIdentifiers().isEmpty()){
            writeIdentifier(interaction.getIdentifiers().iterator().next());

            // IMEx as well
            if (interaction.getImexId() != null){
                writer.write(MitabWriterUtils.FIELD_SEPARATOR);
                writer.write(Xref.IMEX);
                writer.write(MitabWriterUtils.XREF_SEPARATOR);
                escapeAndWriteString(interaction.getImexId());
            }
        }
        // IMEx only
        else if (interaction.getImexId() != null) {
            writer.write(MitabWriterUtils.FIELD_SEPARATOR);
            writer.write(Xref.IMEX);
            writer.write(MitabWriterUtils.XREF_SEPARATOR);
            escapeAndWriteString(interaction.getImexId());
        }
        // nothing
        else{
            writer.write(MitabWriterUtils.EMPTY_COLUMN);
        }
    }

    /**
     * Writes the confidences of an interaction evidence
     * @param interaction
     */
    protected void writeInteractionConfidences(InteractionEvidence interaction) throws IOException {

        if (!interaction.getConfidences().isEmpty()){

            Iterator<Confidence> confIterator = interaction.getConfidences().iterator();
            while (confIterator.hasNext()) {
                writeConfidence(confIterator.next());

                if (confIterator.hasNext()){
                    writer.write(MitabWriterUtils.FIELD_SEPARATOR);
                }
            }
        }
        else {
            writer.write(MitabWriterUtils.EMPTY_COLUMN);
        }
    }

    /**
     * Writes the confidences of an interaction evidence
     * @param interaction
     */
    protected void writeInteractionConfidences(ModelledInteraction interaction) throws IOException {

        if (!interaction.getModelledConfidences().isEmpty()){

            Iterator<ModelledConfidence> confIterator = interaction.getModelledConfidences().iterator();
            while (confIterator.hasNext()) {
                Confidence conf = confIterator.next();
                writeConfidence(confIterator.next());

                if (confIterator.hasNext()){
                    writer.write(MitabWriterUtils.FIELD_SEPARATOR);
                }
            }
        }
        else {
            writer.write(MitabWriterUtils.EMPTY_COLUMN);
        }
    }

    /**
     * This method will write a confidence with a text if text is not null
     * @param conf
     */
    protected abstract void writeConfidence(Confidence conf) throws IOException;

    /**
     * Write an organism.
     * Will duplicate taxid if needs to provide both common name and scientific name
     * @param organism
     * @throws IOException
     */
    protected void writeOrganism(Organism organism) throws IOException {
        if (organism != null){

            writer.write(MitabWriterUtils.TAXID);
            writer.write(MitabWriterUtils.XREF_SEPARATOR);
            writer.write(Integer.toString(organism.getTaxId()));

            // write common name if provided
            if (organism.getCommonName() != null){
                writer.write("(");
                escapeAndWriteString(organism.getCommonName());
                writer.write(")");

                // write scientific name if provided
                if (organism.getScientificName() != null){
                    writer.write(MitabWriterUtils.FIELD_SEPARATOR);
                    writer.write(MitabWriterUtils.TAXID);
                    writer.write(MitabWriterUtils.XREF_SEPARATOR);
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
            writer.write(MitabWriterUtils.EMPTY_COLUMN);
        }
    }

    /**
     * Write the CvTerm. If it is null, it writes an empty column (-)
     * @param cv
     */
    protected void writeCvTerm(CvTerm cv) throws IOException {
        if (cv != null){
            // write MI xref first
            if (cv.getMIIdentifier() != null){
                writer.write(CvTerm.PSI_MI);
                writer.write(MitabWriterUtils.XREF_SEPARATOR);
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
                writer.write(MitabWriterUtils.XREF_SEPARATOR);
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
                writer.write(MitabWriterUtils.XREF_SEPARATOR);
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
                writer.write(MitabWriterUtils.EMPTY_COLUMN);
            }
        }
        else {
            writer.write(MitabWriterUtils.EMPTY_COLUMN);
        }
    }

    /**
     * This methods write the dbsource, alias name and alias type of an alias
     * @param alias
     * @throws IOException
     */
    protected abstract void writeAlias(Alias alias) throws IOException;

    /**
     * This methods write the dbsource, alias name and alias type of an alias. It can use the participant evidence to find dbsource
     * @param alias
     * @throws IOException
     */
    protected abstract void writeAlias(ParticipantEvidence participant, Alias alias) throws IOException;

    /**
     * This methods write the dbsource, alias name and alias type of an alias.  It can use the modelled participant to find dbsource
     * @param alias
     * @throws IOException
     */
    protected abstract void writeAlias(ModelledParticipant participant, Alias alias) throws IOException;

    /**
     * This methods write the database, id and version of an identifier
     * @param identifier
     * @throws IOException
     */
    protected void writeIdentifier(Xref identifier) throws IOException {
        if (identifier != null){
            // write db first
            escapeAndWriteString(identifier.getDatabase().getShortName());
            // write xref separator
            writer.write(MitabWriterUtils.XREF_SEPARATOR);
            // write id
            escapeAndWriteString(identifier.getId());
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
     * @throws IOException
     */
    protected void escapeAndWriteString(String stringToEscape) throws IOException {

        // replace first tabs and break line with a space and escape double quote
        String replaced = stringToEscape.replaceAll(MitabWriterUtils.LINE_BREAK+"|"+MitabWriterUtils.COLUMN_SEPARATOR, " ");
        replaced = replaced.replaceAll("\"", "\\\"");

        for (String special : MitabWriterUtils.SPECIAL_CHARACTERS){

            if (replaced.contains(special)){
                writer.write("\"");
                writer.write(replaced);
                writer.write("\"");
                return;
            }
        }

        writer.write(replaced);
    }

    /**
     * Write full name if not null, otherwise write shortname
     * @param cv
     * @throws IOException
     */
    protected void writeCvTermName(CvTerm cv) throws IOException {
        if (cv.getFullName() != null){
            escapeAndWriteString(cv.getFullName());
        }
        else{
            escapeAndWriteString(cv.getShortName());
        }
    }

    protected void writePublicationImexId(Publication pub) throws IOException {
        // IMEx as well
        if (pub.getImexId() != null) {
            writer.write(Xref.IMEX);
            writer.write(MitabWriterUtils.XREF_SEPARATOR);
            escapeAndWriteString(pub.getImexId());
        }
    }

    private void writeHeaderAndLineBreakIfNotDone() throws IOException {
        if (!hasWrittenHeader){
            writeHeader();
            hasWrittenHeader = true;
        }
        else{
            writer.write(MitabWriterUtils.LINE_BREAK);
        }
    }

    private void initialiseExpansionMethod(ComplexExpansionMethod expansionMethod) {
        this.expansionMethod = expansionMethod != null ? expansionMethod : new SpokeExpansion();
    }

    private void initialiseWriter(Writer writer) {
        if (writer == null){
            throw new IllegalArgumentException("The writer cannot be null.");
        }

        this.writer = writer;
    }

    private void initialiseOutputStream(OutputStream output) {
        if (output == null){
            throw new IllegalArgumentException("The output stream cannot be null.");
        }

        this.writer = new OutputStreamWriter(output);
    }

    private void initialiseFile(File file) throws IOException {
        if (file == null){
            throw new IllegalArgumentException("The file cannot be null.");
        }
        else if (!file.canWrite()){
            throw new IllegalArgumentException("Does not have the permissions to write in file "+file.getAbsolutePath());
        }

        this.writer = new BufferedWriter(new FileWriter(file));
    }
}
