package psidev.psi.mi.jami.tab.io.writer;

import psidev.psi.mi.jami.binary.BinaryInteraction;
import psidev.psi.mi.jami.binary.expansion.ComplexExpansionMethod;
import psidev.psi.mi.jami.datasource.InteractionWriter;
import psidev.psi.mi.jami.exception.DataSourceWriterException;
import psidev.psi.mi.jami.factory.InteractionWriterFactory;
import psidev.psi.mi.jami.model.Interaction;
import psidev.psi.mi.jami.model.Participant;
import psidev.psi.mi.jami.tab.MitabVersion;
import psidev.psi.mi.jami.tab.utils.MitabUtils;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
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
 *  - complex_expansion_key : ComplexExpansionMethod. Specifies the ComplexExpansion object to use. By default, it is SpokeExpansion if nothing is specified
 *  - mitab_header_key : Boolean. Specifies if the writer should write the MITAB header when starting to write or not
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>11/06/13</pre>
 */

public abstract class AbstractMitabWriter<T extends Interaction, B extends BinaryInteraction, P extends Participant> implements InteractionWriter<T>{

    private ComplexExpansionMethod<T, B> expansionMethod;
    private AbstractMitab25BinaryWriter<B, P> binaryWriter;

    public AbstractMitabWriter(){

    }

    public AbstractMitabWriter(File file) throws IOException {

        initialiseFile(file);
        initialiseExpansionMethod(null);
    }

    public AbstractMitabWriter(OutputStream output) {

        initialiseOutputStream(output);
        initialiseExpansionMethod(null);
    }

    public AbstractMitabWriter(Writer writer) {

        initialiseWriter(writer);
        initialiseExpansionMethod(null);
    }

    public AbstractMitabWriter(File file, ComplexExpansionMethod<T, B> expansionMethod) throws IOException {

        initialiseFile(file);
        initialiseExpansionMethod(expansionMethod);
    }

    public AbstractMitabWriter(OutputStream output, ComplexExpansionMethod<T, B> expansionMethod) {

        initialiseOutputStream(output);
        initialiseExpansionMethod(expansionMethod);
    }

    public AbstractMitabWriter(Writer writer, ComplexExpansionMethod<T, B> expansionMethod) {

        initialiseWriter(writer);
        initialiseExpansionMethod(expansionMethod);
    }

    public void initialiseContext(Map<String, Object> options) {

        if (options == null && this.binaryWriter == null){
            throw new IllegalArgumentException("The options for the Mitab writer should contain at least "+ InteractionWriterFactory.OUTPUT_OPTION_KEY + " to know where to write the interactions.");
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
        else if (this.binaryWriter == null){
            throw new IllegalArgumentException("The options for the Mitab writer should contain at least "+ InteractionWriterFactory.OUTPUT_OPTION_KEY + " to know where to write the interactions.");
        }

        if (options.containsKey(MitabUtils.MITAB_HEADER_OPTION)){
            setWriteHeader((Boolean)options.get(MitabUtils.MITAB_HEADER_OPTION));
        }

        if (options.containsKey(InteractionWriterFactory.COMPLEX_EXPANSION_OPTION_KEY)){
            initialiseExpansionMethod((ComplexExpansionMethod<T,B>)options.get(InteractionWriterFactory.COMPLEX_EXPANSION_OPTION_KEY));
        }
    }

    public void write(T interaction) throws DataSourceWriterException {
        if (this.binaryWriter == null){
            throw new IllegalStateException("The mitab writer was not initialised. The options for the Mitab writer should contain at least "+ InteractionWriterFactory.OUTPUT_OPTION_KEY + " to know where to write the interactions.");
        }

        if (interaction != null){

            this.binaryWriter.write(getExpansionMethod().expand(interaction));
        }
    }

    public void write(Collection<T> interactions) throws DataSourceWriterException {
        for (T interaction : interactions){
            write(interaction);
        }
    }

    public void write(Iterator<T> interactions) throws DataSourceWriterException {
        while (interactions.hasNext()){
            write(interactions.next());
        }
    }

    public void flush() throws DataSourceWriterException{
        if (this.binaryWriter != null){
            this.binaryWriter.flush();
        }
    }

    public void close() throws DataSourceWriterException{
        if (this.binaryWriter != null){
            this.binaryWriter.close();
        }
        this.binaryWriter = null;
        this.expansionMethod = null;
    }

    public abstract MitabVersion getVersion();

    public boolean isWriteHeader() {
        return binaryWriter != null ? binaryWriter.isWriteHeader():false;
    }

    public void setWriteHeader(boolean writeHeader) {
        if (this.binaryWriter != null){
            this.binaryWriter.setWriteHeader(writeHeader);
        }
    }

    public boolean hasWrittenHeader() {
        return binaryWriter != null ? binaryWriter.hasWrittenHeader():false;
    }

    public void setHasWrittenHeader(boolean hasWrittenHeader) {
        if (this.binaryWriter != null){
            this.binaryWriter.setHasWrittenHeader(hasWrittenHeader);
        }
    }

    protected abstract void initialiseExpansionMethod(ComplexExpansionMethod<T, B> expansionMethod);

    protected abstract void initialiseWriter(Writer writer);

    protected abstract void initialiseOutputStream(OutputStream output);

    protected abstract void initialiseFile(File file) throws IOException;

    protected void setExpansionMethod(ComplexExpansionMethod<T, B> expansionMethod) {
        this.expansionMethod = expansionMethod;
    }

    protected void setBinaryWriter(AbstractMitab25BinaryWriter<B, P> binaryWriter) {
        this.binaryWriter = binaryWriter;
    }

    protected AbstractMitab25BinaryWriter<B, P> getBinaryWriter() {
        return binaryWriter;
    }

    protected ComplexExpansionMethod<T, B> getExpansionMethod() {
        if (expansionMethod == null){
            initialiseExpansionMethod(null);
        }
        return expansionMethod;
    }
}
