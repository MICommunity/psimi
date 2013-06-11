package psidev.psi.mi.jami.mitab.io;

import psidev.psi.mi.jami.binary.BinaryInteraction;
import psidev.psi.mi.jami.binary.BinaryInteractionEvidence;
import psidev.psi.mi.jami.binary.ModelledBinaryInteraction;
import psidev.psi.mi.jami.binary.expansion.ComplexExpansionMethod;
import psidev.psi.mi.jami.binary.expansion.SpokeExpansion;
import psidev.psi.mi.jami.datasource.InteractionDataSourceWriter;
import psidev.psi.mi.jami.datasource.InteractionWriterFactory;
import psidev.psi.mi.jami.exception.DataSourceWriterException;
import psidev.psi.mi.jami.mitab.MitabVersion;
import psidev.psi.mi.jami.mitab.utils.MitabWriterUtils;
import psidev.psi.mi.jami.model.*;

import java.io.*;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

/**
 * Abstract writer for Mitab.
 *
 * The general options when calling method initialiseContext(Map<String, Object> options) are :
 *  - output_file_key : File. Specifies the file where to write
 *  - output_stream_key : OutputStream. Specifies the stream where to write
 *  - output_writer_key : Writer. Specifies the writer.
 *  If these three options are given, output_file_key will take priority, then output_stream_key an finally output_writer_key. At leats
 *  one of these options should be provided when initialising the context of the writer
 *  - complex_expansion_key : Class<? extends ComplexExpansionMethod>. Specifies the ComplexExpansion class to use. By default, it is SpokeExpansion if nothing is specified
 *  - mitab_version_key : MitabVersion. Specifies the mitab version of this writer
 *  - mitab_header_key : Boolean. Specifies if the writer should write the MITAB header when starting to write or not
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>11/06/13</pre>
 */

public abstract class AbstractMitabWriter implements InteractionDataSourceWriter{

    private Writer writer;
    private ComplexExpansionMethod expansionMethod;
    private boolean isInitialised = false;
    private MitabVersion version = MitabVersion.v2_5;
    private boolean writeHeader = true;
    private boolean hasWrittenHeader = false;

    public AbstractMitabWriter(){

    }

    public AbstractMitabWriter(File file) throws IOException {

        initialiseFile(file);
        this.expansionMethod = new SpokeExpansion();
        isInitialised = true;
    }

    public AbstractMitabWriter(OutputStream output) throws IOException {

        initialiseOutputStream(output);
        this.expansionMethod = new SpokeExpansion();
        isInitialised = true;
    }

    public AbstractMitabWriter(Writer writer) throws IOException {

        initialiseWriter(writer);
        this.expansionMethod = new SpokeExpansion();
        isInitialised = true;
    }

    public AbstractMitabWriter(File file, ComplexExpansionMethod expansionMethod) throws IOException {

        initialiseFile(file);
        initialiseExpansionMethod(expansionMethod);
        isInitialised = true;
    }

    public AbstractMitabWriter(OutputStream output, ComplexExpansionMethod expansionMethod) throws IOException {

        initialiseOutputStream(output);
        initialiseExpansionMethod(expansionMethod);
        isInitialised = true;
    }

    public AbstractMitabWriter(Writer writer, ComplexExpansionMethod expansionMethod) throws IOException {

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

    public void setVersion(MitabVersion version) {
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
            throw new IllegalArgumentException("The options for the DefaultMitabWriter should contain at least "+ InteractionWriterFactory.OUTPUT_FILE_OPTION_KEY
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
            throw new IllegalArgumentException("The options for the DefaultMitabWriter should contain at least "+InteractionWriterFactory.OUTPUT_FILE_OPTION_KEY
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

        if (options.containsKey(MitabWriterUtils.MITAB_VERSION_OPTION)){
            Object version = options.get(MitabWriterUtils.MITAB_VERSION_OPTION);
            this.version = version != null ? (MitabVersion) version : MitabVersion.v2_5;
        }

        if (options.containsKey(MitabWriterUtils.MITAB_HEADER_OPTION)){
            this.writeHeader = (Boolean) options.get(MitabWriterUtils.MITAB_HEADER_OPTION);
        }

        isInitialised = true;
    }

    public void write(Interaction interaction) throws DataSourceWriterException {
        if (!isInitialised){
            throw new IllegalStateException("The DefaultMitabWriter has not been initialised with a map of options." +
                    "The options for the DefaultMitabWriter should contain at least "+InteractionWriterFactory.OUTPUT_FILE_OPTION_KEY
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
            throw new IllegalStateException("The DefaultMitabWriter has not been initialised with a map of options." +
                    "The options for the DefaultMitabWriter should contain at least "+InteractionWriterFactory.OUTPUT_FILE_OPTION_KEY
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
            throw new IllegalStateException("The DefaultMitabWriter has not been initialised with a map of options." +
                    "The options for the DefaultMitabWriter should contain at least "+InteractionWriterFactory.OUTPUT_FILE_OPTION_KEY
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

        switch (version){
            case v2_5:
                writeMitab2_5Columns(interaction, A, B);
                break;
            case v2_6:
                writeMitab2_5Columns(interaction, A, B);
                break;
            case v2_7:
                writeMitab2_7Columns(interaction, A, B);
        }
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

        switch (version){
            case v2_5:
                writeMitab2_5Columns(interaction, A, B);
                break;
            case v2_6:
                writeMitab2_5Columns(interaction, A, B);
                break;
            case v2_7:
                writeMitab2_7Columns(interaction, A, B);
        }
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

        switch (version){
            case v2_5:
                writeMitab2_5Columns(interaction, A, B);
                break;
            case v2_6:
                writeMitab2_5Columns(interaction, A, B);
                break;
            case v2_7:
                writeMitab2_7Columns(interaction, A, B);
        }
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

    protected void writeMitab2_5Columns(BinaryInteraction interaction, Participant a, Participant b) throws IOException {
        // id A
        MitabWriterUtils.writeUniqueIdentifier(a, writer);
        writer.write(MitabWriterUtils.COLUMN_SEPARATOR);
        // id B
        MitabWriterUtils.writeUniqueIdentifier(b, writer);
        writer.write(MitabWriterUtils.COLUMN_SEPARATOR);
        // altid A
        MitabWriterUtils.writeAlternativeIdentifiers(a, writer);
        writer.write(MitabWriterUtils.COLUMN_SEPARATOR);
        // altid B
        MitabWriterUtils.writeAlternativeIdentifiers(b, writer);
        writer.write(MitabWriterUtils.COLUMN_SEPARATOR);
        // aliases
        writeParticipantAliases(a, b);
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
        MitabWriterUtils.writeInteractorOrganism(a, writer);
        writer.write(MitabWriterUtils.COLUMN_SEPARATOR);
        // taxid B
        MitabWriterUtils.writeInteractorOrganism(b, writer);
        writer.write(MitabWriterUtils.COLUMN_SEPARATOR);
        // interaction type
        MitabWriterUtils.writeInteractionType(interaction, writer);
        writer.write(MitabWriterUtils.COLUMN_SEPARATOR);
        // skip source identifier
        writer.write(MitabWriterUtils.EMPTY_COLUMN);
        writer.write(MitabWriterUtils.COLUMN_SEPARATOR);
        // interaction identifiers
        MitabWriterUtils.writeInteractionIdentifiers(interaction, writer);
        writer.write(MitabWriterUtils.COLUMN_SEPARATOR);
        // skip interaction confidence
        writer.write(MitabWriterUtils.EMPTY_COLUMN);
    }

    protected void writeMitab2_6Columns(BinaryInteraction interaction, Participant a, Participant b) throws IOException {
        // write tab 25 columns first
        writeMitab2_5Columns(interaction, a, b);
        writer.write(MitabWriterUtils.COLUMN_SEPARATOR);
        // complex expansion
        MitabWriterUtils.writeComplexExpansion(interaction, writer);
        writer.write(MitabWriterUtils.COLUMN_SEPARATOR);
        // biorole A
        MitabWriterUtils.writeBiologicalRole(a, writer);
        writer.write(MitabWriterUtils.COLUMN_SEPARATOR);
        // biorole B
        MitabWriterUtils.writeBiologicalRole(b, writer);
        writer.write(MitabWriterUtils.COLUMN_SEPARATOR);
        // skip exprole A
        writer.write(MitabWriterUtils.EMPTY_COLUMN);
        writer.write(MitabWriterUtils.COLUMN_SEPARATOR);
        // skip exprole B
        writer.write(MitabWriterUtils.EMPTY_COLUMN);
        writer.write(MitabWriterUtils.COLUMN_SEPARATOR);
        // interactor type A
        MitabWriterUtils.writeInteractorType(a, writer);
        writer.write(MitabWriterUtils.COLUMN_SEPARATOR);
        // interactor type B
        MitabWriterUtils.writeInteractorType(b, writer);
        writer.write(MitabWriterUtils.COLUMN_SEPARATOR);
        // xref A
        MitabWriterUtils.writeParticipantXrefs(a, writer);
        writer.write(MitabWriterUtils.COLUMN_SEPARATOR);
        // xref B
        MitabWriterUtils.writeParticipantXrefs(b, writer);
        writer.write(MitabWriterUtils.COLUMN_SEPARATOR);
        // xref
        MitabWriterUtils.writeInteractionXrefs(interaction, writer);
        writer.write(MitabWriterUtils.COLUMN_SEPARATOR);
        // annotation A
        MitabWriterUtils.writeParticipantAnnotations(a, writer);
        writer.write(MitabWriterUtils.COLUMN_SEPARATOR);
        // annotation B
        MitabWriterUtils.writeParticipantAnnotations(b, writer);
        writer.write(MitabWriterUtils.COLUMN_SEPARATOR);
        // annotation
        MitabWriterUtils.writeInteractionAnnotations(interaction, writer);
        writer.write(MitabWriterUtils.COLUMN_SEPARATOR);
        // skip host organism
        writer.write(MitabWriterUtils.EMPTY_COLUMN);
        writer.write(MitabWriterUtils.COLUMN_SEPARATOR);
        // skip interaction parameter
        writer.write(MitabWriterUtils.EMPTY_COLUMN);
        writer.write(MitabWriterUtils.COLUMN_SEPARATOR);
        // created date
        MitabWriterUtils.writeDate(interaction.getCreatedDate(), writer);
        writer.write(MitabWriterUtils.COLUMN_SEPARATOR);
        // update date
        MitabWriterUtils.writeDate(interaction.getUpdatedDate(), writer);
        writer.write(MitabWriterUtils.COLUMN_SEPARATOR);
        // checksum A
        MitabWriterUtils.writeParticipantChecksums(a, writer);
        writer.write(MitabWriterUtils.COLUMN_SEPARATOR);
        // checksum B
        MitabWriterUtils.writeParticipantChecksums(b, writer);
        writer.write(MitabWriterUtils.COLUMN_SEPARATOR);
        // checksum I
        MitabWriterUtils.writeInteractionChecksums(interaction, writer);
        writer.write(MitabWriterUtils.COLUMN_SEPARATOR);
        // skip negative
        writer.write(MitabWriterUtils.EMPTY_COLUMN);
    }

    protected void writeMitab2_7Columns(BinaryInteraction interaction, Participant a, Participant b) throws IOException {
        // write 2.6 columns
        writeMitab2_6Columns(interaction, a, b);
        writer.write(MitabWriterUtils.COLUMN_SEPARATOR);
        // write features
        writeParticipantFeatures(a, b);
        writer.write(MitabWriterUtils.COLUMN_SEPARATOR);

        // write stc A
        MitabWriterUtils.writeParticipantStoichiometry(a, writer);
        writer.write(MitabWriterUtils.COLUMN_SEPARATOR);
        // write stc B
        MitabWriterUtils.writeParticipantStoichiometry(b, writer);
        writer.write(MitabWriterUtils.COLUMN_SEPARATOR);
        // skip identification A
        writer.write(MitabWriterUtils.EMPTY_COLUMN);
        writer.write(MitabWriterUtils.COLUMN_SEPARATOR);
        // skip identification B
        writer.write(MitabWriterUtils.EMPTY_COLUMN);
    }

    protected void writeMitab2_5Columns(ModelledBinaryInteraction interaction, ModelledParticipant a, ModelledParticipant b) throws IOException {
        // id A
        MitabWriterUtils.writeUniqueIdentifier(a, writer);
        writer.write(MitabWriterUtils.COLUMN_SEPARATOR);
        // id B
        MitabWriterUtils.writeUniqueIdentifier(b, writer);
        writer.write(MitabWriterUtils.COLUMN_SEPARATOR);
        // altid A
        MitabWriterUtils.writeAlternativeIdentifiers(a, writer);
        writer.write(MitabWriterUtils.COLUMN_SEPARATOR);
        // altid B
        MitabWriterUtils.writeAlternativeIdentifiers(b, writer);
        writer.write(MitabWriterUtils.COLUMN_SEPARATOR);
        // aliases
        writeParticipantAliases(a, b);
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
        MitabWriterUtils.writeInteractorOrganism(a, writer);
        writer.write(MitabWriterUtils.COLUMN_SEPARATOR);
        // taxid B
        MitabWriterUtils.writeInteractorOrganism(b, writer);
        writer.write(MitabWriterUtils.COLUMN_SEPARATOR);
        // interaction type
        MitabWriterUtils.writeInteractionType(interaction, writer);
        writer.write(MitabWriterUtils.COLUMN_SEPARATOR);
        // write source identifier
        MitabWriterUtils.writeSource(interaction, writer);
        writer.write(MitabWriterUtils.COLUMN_SEPARATOR);
        // interaction identifiers
        MitabWriterUtils.writeInteractionIdentifiers(interaction, writer);
        writer.write(MitabWriterUtils.COLUMN_SEPARATOR);
        // confidences
        writeConfidences(interaction);

    }

    protected void writeMitab2_6Columns(ModelledBinaryInteraction interaction, ModelledParticipant a, ModelledParticipant b) throws IOException {
        // write tab 25 columns first
        writeMitab2_5Columns(interaction, a, b);
        writer.write(MitabWriterUtils.COLUMN_SEPARATOR);
        // complex expansion
        MitabWriterUtils.writeComplexExpansion(interaction, writer);
        writer.write(MitabWriterUtils.COLUMN_SEPARATOR);
        // biorole A
        MitabWriterUtils.writeBiologicalRole(a, writer);
        writer.write(MitabWriterUtils.COLUMN_SEPARATOR);
        // biorole B
        MitabWriterUtils.writeBiologicalRole(b, writer);
        writer.write(MitabWriterUtils.COLUMN_SEPARATOR);
        // skip exprole A
        writer.write(MitabWriterUtils.EMPTY_COLUMN);
        writer.write(MitabWriterUtils.COLUMN_SEPARATOR);
        // skip exprole B
        writer.write(MitabWriterUtils.EMPTY_COLUMN);
        writer.write(MitabWriterUtils.COLUMN_SEPARATOR);
        // interactor type A
        MitabWriterUtils.writeInteractorType(a, writer);
        writer.write(MitabWriterUtils.COLUMN_SEPARATOR);
        // interactor type B
        MitabWriterUtils.writeInteractorType(b, writer);
        writer.write(MitabWriterUtils.COLUMN_SEPARATOR);
        // xref A
        MitabWriterUtils.writeParticipantXrefs(a, writer);
        writer.write(MitabWriterUtils.COLUMN_SEPARATOR);
        // xref B
        MitabWriterUtils.writeParticipantXrefs(b, writer);
        writer.write(MitabWriterUtils.COLUMN_SEPARATOR);
        // xref
        MitabWriterUtils.writeInteractionXrefs(interaction, writer);
        writer.write(MitabWriterUtils.COLUMN_SEPARATOR);
        // annotation A
        MitabWriterUtils.writeParticipantAnnotations(a, writer);
        writer.write(MitabWriterUtils.COLUMN_SEPARATOR);
        // annotation B
        MitabWriterUtils.writeParticipantAnnotations(b, writer);
        writer.write(MitabWriterUtils.COLUMN_SEPARATOR);
        // annotation
        MitabWriterUtils.writeInteractionAnnotations(interaction, writer);
        writer.write(MitabWriterUtils.COLUMN_SEPARATOR);
        // skip host organism
        writer.write(MitabWriterUtils.EMPTY_COLUMN);
        writer.write(MitabWriterUtils.COLUMN_SEPARATOR);
        // write interaction parameter
        MitabWriterUtils.writeInteractionParameters(interaction, writer);
        writer.write(MitabWriterUtils.COLUMN_SEPARATOR);
        // created date
        MitabWriterUtils.writeDate(interaction.getCreatedDate(), writer);
        writer.write(MitabWriterUtils.COLUMN_SEPARATOR);
        // update date
        MitabWriterUtils.writeDate(interaction.getUpdatedDate(), writer);
        writer.write(MitabWriterUtils.COLUMN_SEPARATOR);
        // checksum A
        MitabWriterUtils.writeParticipantChecksums(a, writer);
        writer.write(MitabWriterUtils.COLUMN_SEPARATOR);
        // checksum B
        MitabWriterUtils.writeParticipantChecksums(b, writer);
        writer.write(MitabWriterUtils.COLUMN_SEPARATOR);
        // checksum I
        MitabWriterUtils.writeInteractionChecksums(interaction, writer);
        writer.write(MitabWriterUtils.COLUMN_SEPARATOR);
        // skip negative
        writer.write(MitabWriterUtils.EMPTY_COLUMN);
    }

    protected void writeMitab2_7Columns(ModelledBinaryInteraction interaction, ModelledParticipant a, ModelledParticipant b) throws IOException {
        // write 2.6 columns
        writeMitab2_6Columns(interaction, a, b);
        writer.write(MitabWriterUtils.COLUMN_SEPARATOR);
        // write features
        writeParticipantFeatures(a, b);
        writer.write(MitabWriterUtils.COLUMN_SEPARATOR);
        // write stc A
        MitabWriterUtils.writeParticipantStoichiometry(a, writer);
        writer.write(MitabWriterUtils.COLUMN_SEPARATOR);
        // write stc B
        MitabWriterUtils.writeParticipantStoichiometry(b, writer);
        writer.write(MitabWriterUtils.COLUMN_SEPARATOR);
        // skip identification A
        writer.write(MitabWriterUtils.EMPTY_COLUMN);
        writer.write(MitabWriterUtils.COLUMN_SEPARATOR);
        // skip identification B
        writer.write(MitabWriterUtils.EMPTY_COLUMN);
    }

    protected void writeMitab2_5Columns(BinaryInteractionEvidence interaction, ParticipantEvidence a, ParticipantEvidence b) throws IOException {
        // id A
        MitabWriterUtils.writeUniqueIdentifier(a, writer);
        writer.write(MitabWriterUtils.COLUMN_SEPARATOR);
        // id B
        MitabWriterUtils.writeUniqueIdentifier(b, writer);
        writer.write(MitabWriterUtils.COLUMN_SEPARATOR);
        // altid A
        MitabWriterUtils.writeAlternativeIdentifiers(a, writer);
        writer.write(MitabWriterUtils.COLUMN_SEPARATOR);
        // altid B
        MitabWriterUtils.writeAlternativeIdentifiers(b, writer);
        writer.write(MitabWriterUtils.COLUMN_SEPARATOR);
        // aliases
        writeParticipantAliases(a, b);
        writer.write(MitabWriterUtils.COLUMN_SEPARATOR);
        // write detection method
        MitabWriterUtils.writeInteractionDetectionMethod(interaction.getExperiment(), writer);
        writer.write(MitabWriterUtils.COLUMN_SEPARATOR);
        // write pub author
        MitabWriterUtils.writeFirstAuthor(interaction.getExperiment(), writer);
        writer.write(MitabWriterUtils.COLUMN_SEPARATOR);
        // write publication identifier
        MitabWriterUtils.writePublicationIdentifiers(interaction.getExperiment(), writer);
        writer.write(MitabWriterUtils.COLUMN_SEPARATOR);
        // taxid A
        MitabWriterUtils.writeInteractorOrganism(a, writer);
        writer.write(MitabWriterUtils.COLUMN_SEPARATOR);
        // taxid B
        MitabWriterUtils.writeInteractorOrganism(b, writer);
        writer.write(MitabWriterUtils.COLUMN_SEPARATOR);
        // interaction type
        MitabWriterUtils.writeInteractionType(interaction, writer);
        writer.write(MitabWriterUtils.COLUMN_SEPARATOR);
        // write source identifier
        MitabWriterUtils.writeSource(interaction.getExperiment(), writer);
        writer.write(MitabWriterUtils.COLUMN_SEPARATOR);
        // interaction identifiers
        MitabWriterUtils.writeInteractionIdentifiers(interaction, writer);
        writer.write(MitabWriterUtils.COLUMN_SEPARATOR);
        // confidences
        writeConfidences(interaction);

    }

    protected void writeMitab2_6Columns(BinaryInteractionEvidence interaction, ParticipantEvidence a, ParticipantEvidence b) throws IOException {
        // write tab 25 columns first
        writeMitab2_5Columns(interaction, a, b);
        writer.write(MitabWriterUtils.COLUMN_SEPARATOR);
        // complex expansion
        MitabWriterUtils.writeComplexExpansion(interaction, writer);
        writer.write(MitabWriterUtils.COLUMN_SEPARATOR);
        // biorole A
        MitabWriterUtils.writeBiologicalRole(a, writer);
        writer.write(MitabWriterUtils.COLUMN_SEPARATOR);
        // biorole B
        MitabWriterUtils.writeBiologicalRole(b, writer);
        writer.write(MitabWriterUtils.COLUMN_SEPARATOR);
        // write exprole A
        MitabWriterUtils.writeExperimentalRole(a, writer);
        writer.write(MitabWriterUtils.COLUMN_SEPARATOR);
        // write exprole B
        MitabWriterUtils.writeExperimentalRole(b, writer);
        writer.write(MitabWriterUtils.COLUMN_SEPARATOR);
        // interactor type A
        MitabWriterUtils.writeInteractorType(a, writer);
        writer.write(MitabWriterUtils.COLUMN_SEPARATOR);
        // interactor type B
        MitabWriterUtils.writeInteractorType(b, writer);
        writer.write(MitabWriterUtils.COLUMN_SEPARATOR);
        // xref A
        MitabWriterUtils.writeParticipantXrefs(a, writer);
        writer.write(MitabWriterUtils.COLUMN_SEPARATOR);
        // xref B
        MitabWriterUtils.writeParticipantXrefs(b, writer);
        writer.write(MitabWriterUtils.COLUMN_SEPARATOR);
        // xref
        MitabWriterUtils.writeInteractionXrefs(interaction, writer);
        writer.write(MitabWriterUtils.COLUMN_SEPARATOR);
        // annotation A
        MitabWriterUtils.writeParticipantAnnotations(a, writer);
        writer.write(MitabWriterUtils.COLUMN_SEPARATOR);
        // annotation B
        MitabWriterUtils.writeParticipantAnnotations(b, writer);
        writer.write(MitabWriterUtils.COLUMN_SEPARATOR);
        // annotation
        MitabWriterUtils.writeInteractionAnnotations(interaction, writer);
        writer.write(MitabWriterUtils.COLUMN_SEPARATOR);
        // write host organism
        MitabWriterUtils.writeHostOrganism(interaction.getExperiment(), writer);
        writer.write(MitabWriterUtils.COLUMN_SEPARATOR);
        // write interaction parameter
        MitabWriterUtils.writeInteractionParameters(interaction, writer);
        writer.write(MitabWriterUtils.COLUMN_SEPARATOR);
        // created date
        MitabWriterUtils.writeDate(interaction.getCreatedDate(), writer);
        writer.write(MitabWriterUtils.COLUMN_SEPARATOR);
        // update date
        MitabWriterUtils.writeDate(interaction.getUpdatedDate(), writer);
        writer.write(MitabWriterUtils.COLUMN_SEPARATOR);
        // checksum A
        MitabWriterUtils.writeParticipantChecksums(a, writer);
        writer.write(MitabWriterUtils.COLUMN_SEPARATOR);
        // checksum B
        MitabWriterUtils.writeParticipantChecksums(b, writer);
        writer.write(MitabWriterUtils.COLUMN_SEPARATOR);
        // checksum I
        MitabWriterUtils.writeInteractionChecksums(interaction, writer);
        writer.write(MitabWriterUtils.COLUMN_SEPARATOR);
        // write negative
        MitabWriterUtils.writeNegativeProperty(interaction, writer);
    }

    protected void writeMitab2_7Columns(BinaryInteractionEvidence interaction, ParticipantEvidence a, ParticipantEvidence b) throws IOException {
        // write 2.6 columns
        writeMitab2_6Columns(interaction, a, b);
        writer.write(MitabWriterUtils.COLUMN_SEPARATOR);
        // write features
        writeParticipantFeatures(a, b);
        writer.write(MitabWriterUtils.COLUMN_SEPARATOR);
        // write stc A
        MitabWriterUtils.writeParticipantStoichiometry(a, writer);
        writer.write(MitabWriterUtils.COLUMN_SEPARATOR);
        // write stc B
        MitabWriterUtils.writeParticipantStoichiometry(b, writer);
        writer.write(MitabWriterUtils.COLUMN_SEPARATOR);
        // skip identification A
        MitabWriterUtils.writeParticipantIdentificationMethod(a, writer);
        writer.write(MitabWriterUtils.COLUMN_SEPARATOR);
        // skip identification B
        MitabWriterUtils.writeParticipantIdentificationMethod(b, writer);
    }

    protected abstract void writeParticipantAliases(Participant a, Participant b) throws IOException;

    protected abstract void writeParticipantFeatures(Participant a, Participant b) throws IOException;

    protected abstract void writeConfidences(ModelledBinaryInteraction interaction) throws IOException;

    protected abstract void writeConfidences(BinaryInteractionEvidence interaction) throws IOException;

    private void writeHeaderAndLineBreakIfNotDone() throws IOException {
        if (!hasWrittenHeader){
            MitabWriterUtils.writeHeader(this.version, this.writer);
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

        this.writer = new BufferedWriter(new OutputStreamWriter(output));
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
