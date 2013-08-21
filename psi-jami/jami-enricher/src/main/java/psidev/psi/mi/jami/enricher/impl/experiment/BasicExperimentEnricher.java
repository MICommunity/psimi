package psidev.psi.mi.jami.enricher.impl.experiment;

import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.model.Experiment;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 13/08/13
 */
public class BasicExperimentEnricher
        extends AbstractExperimentEnricher{
    /**
     * Processes the specific details of the experiment which are delegated to a subEnricher.
     * @param experimentToEnrich    The experiment which is to be enriched
     * @throws EnricherException    Thrown if problems are encountered in a fetcher.
     */
    @Override
    protected void processExperiment(Experiment experimentToEnrich) throws EnricherException {
        // Empty - no additional processing
    }
}
