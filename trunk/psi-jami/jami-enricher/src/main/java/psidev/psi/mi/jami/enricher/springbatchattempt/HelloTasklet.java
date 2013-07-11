package psidev.psi.mi.jami.enricher.springbatchattempt;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 11/07/13
 */
public class HelloTasklet implements Tasklet {

    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext)
            throws Exception {

        System.out.println(" HULLO WORLD! ");

        return RepeatStatus.FINISHED;

    }
}
