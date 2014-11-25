package psidev.psi.mi.jami.batch;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.ChunkListener;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.scope.context.ChunkContext;

/**
 * Listener to chumk operations
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>02/08/13</pre>
 */

public class BasicChunkLoggerListener implements StepExecutionListener, ChunkListener {

    private static final Log log = LogFactory.getLog(BasicChunkLoggerListener.class);

    private StepExecution stepExecution;
    private long startTime;

    public ExitStatus afterStep(StepExecution stepExecution) {
        log.info("Step finished after " + startTime + " milliseconds");

        return null;
    }

    public void beforeStep(StepExecution stepExecution) {
        this.stepExecution = stepExecution;

        this.startTime = System.currentTimeMillis();
    }

    public void beforeChunk(ChunkContext chunkContext) {
        // do nothing
    }

    public void afterChunk(ChunkContext chunkContext) {
        if (!log.isInfoEnabled()) {
            return;
        }

        final int readCount = stepExecution.getReadCount();

        log.info("Number of lines read : " + readCount);
    }

    public void afterChunkError(ChunkContext chunkContext) {
        log.error("Chunk error: " + chunkContext.toString());
    }
}
