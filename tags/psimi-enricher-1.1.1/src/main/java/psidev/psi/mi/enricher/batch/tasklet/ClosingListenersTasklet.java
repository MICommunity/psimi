package psidev.psi.mi.enricher.batch.tasklet;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import psidev.psi.mi.jami.bridges.mapper.listener.ProteinMappingStatisticsWriter;
import psidev.psi.mi.jami.enricher.listener.impl.writer.EnricherStatisticsWriter;

import java.io.IOException;
import java.util.List;

/**
 * This tasklet will close a set of statistics listener writer
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>08/04/14</pre>
 */

public class ClosingListenersTasklet implements Tasklet {
    
    private List<EnricherStatisticsWriter> statisticsWriters;
    private ProteinMappingStatisticsWriter mapperStatisticsWriter;
    
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {

        if (this.statisticsWriters != null){
            for (EnricherStatisticsWriter writer : statisticsWriters){
                try {
                    writer.close();
                }
                catch (IOException e){
                    e.printStackTrace();
                }
            }
        }

        if (mapperStatisticsWriter != null){
            try {
                mapperStatisticsWriter.close();
            }
            catch (IOException e){
                e.printStackTrace();
            }
        }

        return RepeatStatus.FINISHED;
    }

    public List<EnricherStatisticsWriter> getStatisticsWriters() {
        return statisticsWriters;
    }

    public void setStatisticsWriters(List<EnricherStatisticsWriter> statisticsWriters) {
        this.statisticsWriters = statisticsWriters;
    }

    public ProteinMappingStatisticsWriter getMapperStatisticsWriter() {
        return mapperStatisticsWriter;
    }

    public void setMapperStatisticsWriter(ProteinMappingStatisticsWriter mapperStatisticsWriter) {
        this.mapperStatisticsWriter = mapperStatisticsWriter;
    }
}
