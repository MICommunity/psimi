package psidev.psi.mi.jami.tab.io.parser;

import psidev.psi.mi.jami.datasource.FileSourceError;
import psidev.psi.mi.jami.datasource.MIFileDataSource;
import psidev.psi.mi.jami.datasource.StreamingInteractionSource;
import psidev.psi.mi.jami.model.Interaction;
import psidev.psi.mi.jami.model.InteractionEvidence;
import psidev.psi.mi.jami.model.ModelledInteraction;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

/**
 * The basic MITAB streaming data source
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>18/06/13</pre>
 */

public class MitabStreamingDataSource implements MIFileDataSource, StreamingInteractionSource {

    private MitabLineParser mitabLineParser;
    private Collection<FileSourceError> sourceErrors;
    private boolean isInitialised = false;

    public void initialiseContext(Map<String, Object> options) {
        /*if (options == null && !isInitialised){
            throw new IllegalArgumentException("The options for the MitabStreamingDataSource should contain at least "+ MIDataSourceFactory.INPUT_FILE_OPTION_KEY
                    + " or " + MIDataSourceFactory.INPUT_STREAM_OPTION_KEY + " or " + MIDataSourceFactory.READER_OPTION_KEY + " to know where to write the interactions.");
        }
        else if (options == null){
            return;
        }
        else if (options.containsKey(MIDataSourceFactory.INPUT_FILE_OPTION_KEY)){
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
        } */

        isInitialised = true;
    }

    public Collection<FileSourceError> getDataSourceErrors() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public boolean validateSyntax() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void open() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void close() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public Iterator<? extends InteractionEvidence> getInteractionEvidencesIterator() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Iterator<? extends ModelledInteraction> getModelledInteractionsIterator() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Iterator<? extends Interaction> getInteractionsIterator() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /*private void initialiseReader(Reader reader) {
        if (writer == null){
            throw new IllegalArgumentException("The writer cannot be null.");
        }

        this.writer = writer;
    }

    private void initialiseInputStream(InputStream input) {
        if (output == null){
            throw new IllegalArgumentException("The output stream cannot be null.");
        }

        this.writer = new OutputStreamWriter(output);
    }

    private void initialiseFile(File file) throws IOException {
        if (file == null){
            throw new IllegalArgumentException("The file cannot be null.");
        }
        else if (!file.canRead()){
            throw new IllegalArgumentException("Does not have the permissions to read file "+file.getAbsolutePath());
        }

        this.mitabLineParser = new MitabL;
    }*/
}
