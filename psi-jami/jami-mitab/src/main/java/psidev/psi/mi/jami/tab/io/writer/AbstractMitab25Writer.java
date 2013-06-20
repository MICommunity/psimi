package psidev.psi.mi.jami.tab.io.writer;

import psidev.psi.mi.jami.binary.BinaryInteraction;
import psidev.psi.mi.jami.binary.expansion.ComplexExpansionMethod;
import psidev.psi.mi.jami.datasource.InteractionDataSourceWriter;
import psidev.psi.mi.jami.exception.DataSourceWriterException;
import psidev.psi.mi.jami.factory.InteractionWriterFactory;
import psidev.psi.mi.jami.model.Interaction;
import psidev.psi.mi.jami.model.Participant;

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
 *  - complex_expansion_key : Class<? extends ComplexExpansionMethod>. Specifies the ComplexExpansion class to use. By default, it is SpokeExpansion if nothing is specified
 *  - mitab_header_key : Boolean. Specifies if the writer should write the MITAB header when starting to write or not
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>11/06/13</pre>
 */

public abstract class AbstractMitab25Writer<T extends Interaction, B extends BinaryInteraction, P extends Participant> implements InteractionDataSourceWriter<T>{

    private ComplexExpansionMethod<T, B> expansionMethod;
    private AbstractMitab25BinaryWriter<B, P> binaryWriter;

    public AbstractMitab25Writer(){

    }

    public AbstractMitab25Writer(File file) throws IOException {

        initialiseFile(file);
        initialiseExpansionMethod(null);
    }

    public AbstractMitab25Writer(OutputStream output) {

        initialiseOutputStream(output);
        initialiseExpansionMethod(null);
    }

    public AbstractMitab25Writer(Writer writer) {

        initialiseWriter(writer);
        initialiseExpansionMethod(null);
    }

    public AbstractMitab25Writer(File file, ComplexExpansionMethod<T, B> expansionMethod) throws IOException {

        initialiseFile(file);
        initialiseExpansionMethod(expansionMethod);
    }

    public AbstractMitab25Writer(OutputStream output, ComplexExpansionMethod<T, B> expansionMethod) {

        initialiseOutputStream(output);
        initialiseExpansionMethod(expansionMethod);
    }

    public AbstractMitab25Writer(Writer writer, ComplexExpansionMethod<T, B> expansionMethod) {

        initialiseWriter(writer);
        initialiseExpansionMethod(expansionMethod);
    }

    public ComplexExpansionMethod<T, B> getExpansionMethod() {
        return expansionMethod;
    }

    public void initialiseContext(Map<String, Object> options) throws DataSourceWriterException {

        if (options == null && this.binaryWriter == null){
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
        else if (this.binaryWriter == null){
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
    }

    public void write(T interaction) throws DataSourceWriterException {
        if (this.binaryWriter == null){
            throw new IllegalStateException("The Mitab25Writer has not been initialised with a map of options." +
                    "The options for the Mitab25Writer should contain at least "+InteractionWriterFactory.OUTPUT_FILE_OPTION_KEY
                    + " or " + InteractionWriterFactory.OUTPUT_STREAM_OPTION_KEY + " or " + InteractionWriterFactory.WRITER_OPTION_KEY + " to know where to write the interactions.");
        }

        if (interaction != null){

            this.binaryWriter.write(expansionMethod.expand(interaction));
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
}
