package psidev.psi.mi.jami.enricher.enricherimplementation.organism;

import psidev.psi.mi.jami.bridges.fetcher.OrganismFetcher;
import psidev.psi.mi.jami.enricher.OrganismEnricher;
import psidev.psi.mi.jami.enricher.exception.EnrichmentException;
import psidev.psi.mi.jami.model.Organism;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 24/05/13
 * Time: 13:35
 */
public class MaximumOrganismUpdater
        extends AbstractOrganismEnricher
        implements OrganismEnricher {

    public MaximumOrganismUpdater()
            throws EnrichmentException {
        super();
    }

    public MaximumOrganismUpdater(OrganismFetcher fetcher)
            throws EnrichmentException{
        super(fetcher);
    }

    public void enrichOrganism(Organism organismToEnrich)
            throws EnrichmentException {

        Organism organismEnriched = getFullyEnrichedForm(organismToEnrich);
        runOrganismAdditionEnrichment(organismToEnrich, organismEnriched);
        runOrganismOverwriteUpdate(organismToEnrich, organismEnriched);
        fireEnricherEvent(enricherEvent);
    }
}
