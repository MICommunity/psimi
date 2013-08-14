package psidev.psi.mi.enricher.batch.writer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStream;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.util.FileUtils;
import org.springframework.core.io.Resource;
import org.springframework.util.Assert;
import psidev.psi.mi.jami.commons.PsiJami;
import psidev.psi.mi.jami.datasource.InteractionWriter;
import psidev.psi.mi.jami.exception.MIIOException;
import psidev.psi.mi.jami.factory.InteractionObjectCategory;
import psidev.psi.mi.jami.factory.InteractionWriterFactory;
import psidev.psi.mi.jami.model.InteractionEvidence;

import java.io.*;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.util.List;
import java.util.Map;

/**
 * The spring batch writer for interaction evidences
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>23/07/13</pre>
 */

public class PsiInteractionEvidenceWriter implements ItemWriter<InteractionEvidence>, ItemStream{

    private Resource output;

    private long currentPosition=0;
    private Writer outputBufferedWriter;
    private FileChannel fileChannel;
    private FileOutputStream os;
    private Map<String, Object> writerOptions;

    private InteractionWriter<InteractionEvidence> interactionWriter;

    private final static String CURRENT_POSITION = "current_position";

    private static final Log logger = LogFactory.getLog(PsiInteractionEvidenceWriter.class);

    public void open(ExecutionContext executionContext) throws ItemStreamException {
        Assert.notNull(executionContext, "ExecutionContext must not be null");

        if (output == null){
            throw new IllegalStateException("Output resource must be provided. ");
        }

        if (this.writerOptions == null || this.writerOptions.isEmpty()){
            throw new IllegalStateException("Options to instantiate the writer must be provided");
        }

        try {
            File outputFile = output.getFile();

            if (!outputFile.getParentFile().canWrite()) {
                logger.warn("Cannot write to the output file " + output.getDescription());
                throw new IllegalStateException("Needs to write in output file: " + output);
            }

            // we initialize the bufferWriter
            if (executionContext.containsKey(CURRENT_POSITION)) {
                this.currentPosition = executionContext.getLong(CURRENT_POSITION);
                initializeBufferedWriter(outputFile, true, false);
            }
            else {
                initializeBufferedWriter(outputFile, false, false);
            }
        } catch (IOException e) {
            throw new ItemStreamException("Cannot open the output file", e);
        }
    }

    public void update(ExecutionContext executionContext) throws ItemStreamException {
        Assert.notNull(executionContext, "ExecutionContext must not be null");

        try {
            currentPosition = position();
            executionContext.putLong(CURRENT_POSITION, currentPosition);

        } catch (IOException e) {
            throw new ItemStreamException( "Impossible to get the last position of the writer" );
        }
    }

    public void close() throws ItemStreamException {
        if (interactionWriter != null) {
            try {
                interactionWriter.close();
            } catch (MIIOException e) {
                throw new ItemStreamException( "Impossible to close " + output.getDescription(), e );
            }
            finally {
                if (fileChannel != null) {
                    try {
                        fileChannel.close();
                    } catch (IOException e) {
                        throw new ItemStreamException( "Impossible to close " + output.getDescription(), e );
                    }
                }
            }
        }

        this.currentPosition = 0;
        this.interactionWriter = null;
        this.fileChannel = null;
        this.os = null;
    }

    /**
     * Creates the buffered writer for the output file channel based on
     * configuration information.
     * @throws IOException
     */
    private void initializeBufferedWriter(File file, boolean restarted, boolean shouldDeleteIfExists) throws IOException {

        FileUtils.setUpOutputFile(file, restarted, !shouldDeleteIfExists, shouldDeleteIfExists);

        os = new FileOutputStream(file.getAbsolutePath(), true);
        fileChannel = os.getChannel();

        Writer writer = Channels.newWriter(fileChannel, "UTF-8");
        outputBufferedWriter = new BufferedWriter(writer);

        Assert.state(outputBufferedWriter != null);

        // initialise writers
        PsiJami.initialiseInteractionEvidenceWriters();
        // add mandatory options
        this.writerOptions.put(InteractionWriterFactory.OUTPUT_OPTION_KEY, this.outputBufferedWriter);
        this.writerOptions.put(InteractionWriterFactory.OUTPUT_FORMAT_OPTION_KEY, InteractionObjectCategory.evidence);

        InteractionWriterFactory writerFactory = InteractionWriterFactory.getInstance();
        this.interactionWriter = writerFactory.getInteractionWriterWith(this.writerOptions);

        if (this.interactionWriter == null){
            throw new IllegalStateException("We cannot find a valid interaction writer with the given options.");
        }

        // in case of restarting reset position to last committed point
        if (restarted) {
            checkFileSize();
            truncate();
        }

        outputBufferedWriter.flush();
    }

    /**
     * Checks (on setState) to make sure that the current output file's size
     * is not smaller than the last saved commit point. If it is, then the
     * file has been damaged in some way and whole task must be started over
     * again from the beginning.
     * @throws IOException if there is an IO problem
     */
    private void checkFileSize() throws IOException {
        long size = -1;

        outputBufferedWriter.flush();
        size = fileChannel.size();

        if (size < currentPosition) {
            throw new ItemStreamException("Current file size is smaller than size at last commit");
        }
    }

    /**
     * Truncate the output at the last known good point.
     *
     * @throws IOException
     */
    public void truncate() throws IOException {
        fileChannel.truncate(currentPosition);
        fileChannel.position(currentPosition);
    }

    /**
     * Return the byte offset position of the cursor in the output file as a
     * long integer.
     */
    public long position() throws IOException {
        long pos = 0;

        if (fileChannel == null) {
            return 0;
        }

        outputBufferedWriter.flush();
        pos = fileChannel.position();

        return pos;

    }

    public void write(List<? extends InteractionEvidence> items) throws Exception {
        if (this.interactionWriter == null){
            throw new IllegalStateException("The writer needs to be initialised before writing");
        }

        this.interactionWriter.write(items);
    }

    public void setWriterOptions(Map<String, Object> writerOptions) {
        this.writerOptions = writerOptions;
    }

    public void setOutput(Resource output) {
        this.output = output;
    }
}
