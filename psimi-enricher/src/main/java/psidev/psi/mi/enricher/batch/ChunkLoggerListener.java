package psidev.psi.mi.enricher.batch;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.ChunkListener;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;

/**
 * Listener to chumk operations
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>02/08/13</pre>
 */

public class ChunkLoggerListener implements StepExecutionListener, ChunkListener {

    private static final Log log = LogFactory.getLog(ChunkLoggerListener.class);

    private StepExecution stepExecution;
    private long startTime;

    public ExitStatus afterStep(StepExecution stepExecution) {
        log.info("Steap finished after " + startTime + " milliseconds");

        return null;
    }

    public void beforeStep(StepExecution stepExecution) {
        this.stepExecution = stepExecution;

        this.startTime = System.currentTimeMillis();
    }

    public void beforeChunk() {
    }

    public void afterChunk() {
        if (!log.isInfoEnabled()) {
            return;
        }

        final int readCount = stepExecution.getReadCount();

        log.info("Number of lines read : " + readCount);
    }
}
