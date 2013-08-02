package psidev.psi.mi.jami.enricher.impl.experiment;

import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.model.Experiment;

/**
 * The experiment enricher has no fetcher and can enrich either a single experiment of a collection.
 * It has subEnrichers for CvTerms, Organisms, and publications.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since  01/08/13
 */
public class MinimumExperimentUpdater
        extends AbstractExperimentEnricher{

    /**
     * Processes the specific details of the experiment which are delegated to a subEnricher.
     * @param experimentToEnrich    The experiment which is to be enriched
     * @throws EnricherException    Thrown if problems are encountered in a fetcher.
     */
    @Override
    protected void processExperiment(Experiment experimentToEnrich)
            throws EnricherException {

    }
}
