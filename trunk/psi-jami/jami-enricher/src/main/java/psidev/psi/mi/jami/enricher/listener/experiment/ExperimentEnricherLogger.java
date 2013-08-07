package psidev.psi.mi.jami.enricher.listener.experiment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import psidev.psi.mi.jami.enricher.listener.EnrichmentStatus;
import psidev.psi.mi.jami.model.Experiment;

/**
 * A logging listener. It will display a message when each event if fired.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 01/08/13
 */
public class ExperimentEnricherLogger
        implements ExperimentEnricherListener{


    protected static final Logger log = LoggerFactory.getLogger(ExperimentEnricherLogger.class.getName());

    public void onEnrichmentComplete(Experiment experiment, EnrichmentStatus status, String message) {
        log.info(experiment.toString()+" enrichment complete " +
                "with status ["+status+"], message: "+message);
    }
}
