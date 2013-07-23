package psidev.psi.mi.enricher.batch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.configuration.ListableJobLocator;
import org.springframework.batch.core.launch.NoSuchJobException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import java.util.Arrays;
import java.util.Collection;

/**
 * Job registry for PsiEnricher
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>23/07/13</pre>
 */

public class PsiEnricherJobRegistry implements ListableJobLocator {

    @Autowired
    private ApplicationContext applicationContext;

    public Job getJob(String name) throws NoSuchJobException {
        Job job = (Job) applicationContext.getBean(name);

        if (job == null) {
            throw new NoSuchJobException("IntactContext is not aware of this job: "+name);
        }

        return job;
    }

    public Collection<String> getJobNames() {
        return Arrays.asList(applicationContext.getBeanNamesForType(Job.class));
    }
}
