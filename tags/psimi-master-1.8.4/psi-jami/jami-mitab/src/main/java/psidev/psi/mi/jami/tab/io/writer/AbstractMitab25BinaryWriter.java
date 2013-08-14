package psidev.psi.mi.jami.tab.io.writer;

import psidev.psi.mi.jami.binary.BinaryInteraction;
import psidev.psi.mi.jami.datasource.InteractionWriter;
import psidev.psi.mi.jami.exception.MIIOException;
import psidev.psi.mi.jami.factory.InteractionWriterFactory;
import psidev.psi.mi.jami.model.Participant;
import psidev.psi.mi.jami.tab.MitabColumnName;
import psidev.psi.mi.jami.tab.MitabVersion;
import psidev.psi.mi.jami.tab.io.writer.feeder.MitabColumnFeeder;
import psidev.psi.mi.jami.tab.utils.MitabUtils;

import java.io.*;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

/**
 * Abstract class for BinaryInteraction writer.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>19/06/13</pre>
 */

public abstract class AbstractMitab25BinaryWriter<T extends BinaryInteraction, P extends Participant> implements InteractionWriter<T> {

    private Writer writer;
    private boolean isInitialised = false;
    private MitabVersion version = MitabVersion.v2_5;
    private boolean writeHeader = true;
    private MitabColumnFeeder<T, P> columnFeeder;
    private boolean hasStarted;

    public AbstractMitab25BinaryWriter(){

    }

    public AbstractMitab25BinaryWriter(File file) throws IOException {

        initialiseFile(file);
        isInitialised = true;
    }

    public AbstractMitab25BinaryWriter(OutputStream output) {

        initialiseOutputStream(output);
        isInitialised = true;
    }

    public AbstractMitab25BinaryWriter(Writer writer) {

        initialiseWriter(writer);
        isInitialised = true;
    }

    public MitabVersion getVersion() {
        return version;
    }

    public boolean isWriteHeader() {
        return writeHeader;
    }

    public void setWriteHeader(boolean writeHeader) {
        this.writeHeader = writeHeader;
    }

    public void initialiseContext(Map<String, Object> options) {

        if (options == null && !isInitialised){
            throw new IllegalArgumentException("The options for the Mitab writer should contain at least "+ InteractionWriterFactory.OUTPUT_OPTION_KEY + " to know where to write the interactions.");
        }
        else if (options == null){
            return;
        }
        else if (options.containsKey(InteractionWriterFactory.OUTPUT_OPTION_KEY)){
            Object output = options.get(InteractionWriterFactory.OUTPUT_OPTION_KEY);

            if (output instanceof File){
                try {
                    initialiseFile((File) output);
                } catch (IOException e) {
                    throw new IllegalArgumentException("Impossible to open and write in output file " + ((File)output).getName(), e);
                }
            }
            else if (output instanceof OutputStream){
                initialiseOutputStream((OutputStream) output);
            }
            else if (output instanceof Writer){
                initialiseWriter((Writer) output);
            }
            // suspect a file path
            else if (output instanceof String){
                try {
                    initialiseFile(new File((String)output));
                } catch (IOException e) {
                    throw new IllegalArgumentException("Impossible to open and write in output file " + output, e);
                }
            }
            else {
                throw new IllegalArgumentException("Impossible to write in the provided output "+output.getClass().getName() + ", a File, OuputStream, Writer or file path was expected.");
            }
        }
        else if (!isInitialised){
            throw new IllegalArgumentException("The options for the Mitab writer should contain at least "+ InteractionWriterFactory.OUTPUT_OPTION_KEY + " to know where to write the interactions.");
        }

        if (options.containsKey(MitabUtils.MITAB_HEADER_OPTION)){
            setWriteHeader((Boolean)options.get(MitabUtils.MITAB_HEADER_OPTION));
        }

        isInitialised = true;
    }

    public void end() throws MIIOException {
        // nothing to do
        if (!isInitialised){
            throw new IllegalStateException("The mitab writer was not initialised. The options for the Mitab writer should contain at least "+ InteractionWriterFactory.OUTPUT_OPTION_KEY + " to know where to write the interactions.");
        }
    }

    public void start() throws MIIOException {
        if (!isInitialised){
            throw new IllegalStateException("The mitab writer was not initialised. The options for the Mitab25Writer should contain at least "+ InteractionWriterFactory.OUTPUT_OPTION_KEY + " to know where to write the interactions.");
        }
        else if (writeHeader){
            try {
                writeHeader();
            } catch (IOException e) {
                throw new MIIOException("Impossible to write the MITAB header.", e);
            }
        }
        hasStarted = true;
    }

    /**
     * Writes a binary interaction.
     * Does not write any extended properties from participants, interaction and features
     * This method will write empty columns for interaction detection method, publication author and identifier,
     * source and confidences.
     * It will also ignore experimental roles, host organism, interaction parameters and participant identification methods
     * @param interaction
     * @throws MIIOException
     */
    public void write(T interaction) throws MIIOException {
        if (!isInitialised){
            throw new IllegalStateException("The mitab writer was not initialised. The options for the Mitab25Writer should contain at least "+ InteractionWriterFactory.OUTPUT_OPTION_KEY + " to know where to write the interactions.");
        }

        try{
            P A = (P) interaction.getParticipantA();
            P B = (P) interaction.getParticipantB();
            writeBinary(interaction, A, B);
        }
        catch (IOException e) {
            throw new MIIOException("Impossible to write " +interaction.toString(), e);
        }
    }

    /**
     * Writes a collection of binary interactions.
     * Does not write any extended properties from participants, interaction and features
     * This method will write empty columns for interaction detection method, publication author and identifier,
     * source and confidences.
     * It will also ignore experimental roles, host organism, interaction parameters and participant identification methods
     * @param interactions
     * @throws MIIOException
     */
    public void write(Collection<? extends T> interactions) throws MIIOException {
        Iterator<? extends T> binaryIterator = interactions.iterator();
        write(binaryIterator);
    }

    public void write(Iterator<? extends T> interactions) throws MIIOException {
        while(interactions.hasNext()){
            write(interactions.next());
        }
    }

    public void flush() throws MIIOException{
        if (isInitialised){
            try {
                writer.flush();
            } catch (IOException e) {
                throw new MIIOException("Impossible to flush the MITAB writer", e);
            }
        }
    }

    public void close() throws MIIOException{
        if (isInitialised){
            try {
                writer.flush();
            } catch (IOException e) {
                throw new MIIOException("Impossible to flush the MITAB writer", e);
            }
            finally {
                try {
                    writer.close();
                } catch (IOException e) {
                    throw new MIIOException("Impossible to close the MITAB writer", e);
                }
                finally {
                    isInitialised = false;
                    writer = null;
                    writeHeader = true;
                    version = MitabVersion.v2_5;
                    columnFeeder = null;
                }
            }
        }
    }
    public void reset() throws MIIOException{
        if (isInitialised){
            try {
                writer.flush();
            } catch (IOException e) {
                throw new MIIOException("Impossible to flush the MITAB writer", e);
            }
            finally {
                isInitialised = false;
                writer = null;
                writeHeader = true;
                version = MitabVersion.v2_5;
                columnFeeder = null;
            }
        }
    }

    protected MitabColumnFeeder<T, P> getColumnFeeder() {
        return columnFeeder;
    }

    protected void setColumnFeeder(MitabColumnFeeder<T, P> columnFeeder) {
        this.columnFeeder = columnFeeder;
    }

    protected abstract void initialiseColumnFeeder();

    /**
     * Writes the binary interaction and its participants in MITAB 2.5
     * @param interaction
     * @param a
     * @param b
     * @throws IOException
     */
    protected void writeBinary(T interaction, P a, P b) throws IOException {
        if (hasStarted){
            writer.write(MitabUtils.LINE_BREAK);
        }
        else {
            hasStarted = true;
        }

        // id A
        this.columnFeeder.writeUniqueIdentifier(a);
        writer.write(MitabUtils.COLUMN_SEPARATOR);
        // id B
        this.columnFeeder.writeUniqueIdentifier(b);
        writer.write(MitabUtils.COLUMN_SEPARATOR);
        // altid A
        this.columnFeeder.writeAlternativeIdentifiers(a);
        writer.write(MitabUtils.COLUMN_SEPARATOR);
        // altid B
        this.columnFeeder.writeAlternativeIdentifiers(b);
        writer.write(MitabUtils.COLUMN_SEPARATOR);
        // aliases
        // alias A
        this.columnFeeder.writeAliases(a);
        writer.write(MitabUtils.COLUMN_SEPARATOR);
        // alias B
        this.columnFeeder.writeAliases(b);
        writer.write(MitabUtils.COLUMN_SEPARATOR);

        // skip detection method
        this.columnFeeder.writeInteractionDetectionMethod(interaction);
        writer.write(MitabUtils.COLUMN_SEPARATOR);
        // skip pub author
        this.columnFeeder.writeFirstAuthor(interaction);
        writer.write(MitabUtils.COLUMN_SEPARATOR);
        // write publication identifier
        this.columnFeeder.writePublicationIdentifiers(interaction);
        writer.write(MitabUtils.COLUMN_SEPARATOR);
        // taxid A
        this.columnFeeder.writeInteractorOrganism(a);
        writer.write(MitabUtils.COLUMN_SEPARATOR);
        // taxid B
        this.columnFeeder.writeInteractorOrganism(b);
        writer.write(MitabUtils.COLUMN_SEPARATOR);
        // interaction type
        this.columnFeeder.writeInteractionType(interaction);
        writer.write(MitabUtils.COLUMN_SEPARATOR);
        // skip source identifier
        this.columnFeeder.writeSource(interaction);
        writer.write(MitabUtils.COLUMN_SEPARATOR);
        // interaction identifiers
        this.columnFeeder.writeInteractionIdentifiers(interaction);
        writer.write(MitabUtils.COLUMN_SEPARATOR);
        // skip interaction confidence
        this.columnFeeder.writeInteractionConfidences(interaction);
    }

    protected Writer getWriter() {
        return writer;
    }

    /**
     * Write the header
     * @throws IOException
     */
    protected void writeHeader() throws IOException{
        writer.write(MitabUtils.COMMENT_PREFIX);
        writer.write(" ");

        for (MitabColumnName colName : MitabColumnName.values()) {
            writer.write(colName.toString());
            // starts with 0
            if (colName.ordinal() < version.getNumberOfColumns() - 1){
                writer.write(MitabUtils.COLUMN_SEPARATOR);
            }
            else {
                break;
            }
        }
    }

    protected void setVersion(MitabVersion version){
        this.version = version;
    }

    protected boolean hasStarted() {
        return hasStarted;
    }

    protected void setStarted(boolean start){
        this.hasStarted = start;
    }

    private void initialiseWriter(Writer writer) {
        if (writer == null){
            throw new IllegalArgumentException("The writer cannot be null.");
        }

        this.writer = writer;
        initialiseColumnFeeder();
    }

    private void initialiseOutputStream(OutputStream output) {
        if (output == null){
            throw new IllegalArgumentException("The output stream cannot be null.");
        }

        this.writer = new OutputStreamWriter(output);
        initialiseColumnFeeder();
    }

    private void initialiseFile(File file) throws IOException {
        if (file == null){
            throw new IllegalArgumentException("The file cannot be null.");
        }
        else if (!file.canWrite()){
            throw new IllegalArgumentException("Does not have the permissions to write in file "+file.getAbsolutePath());
        }

        this.writer = new BufferedWriter(new FileWriter(file));
        initialiseColumnFeeder();
    }
}