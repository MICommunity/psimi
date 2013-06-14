package psidev.psi.mi.jami.enricher.impl.organism;

import psidev.psi.mi.jami.bridges.exception.BridgeFailedException;
import psidev.psi.mi.jami.bridges.fetcher.OrganismFetcher;
import psidev.psi.mi.jami.enricher.OrganismEnricher;
import psidev.psi.mi.jami.enricher.exception.BadEnrichedFormException;
import psidev.psi.mi.jami.enricher.exception.BadToEnrichFormException;
import psidev.psi.mi.jami.enricher.exception.MissingServiceException;
import psidev.psi.mi.jami.model.Organism;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 24/05/13
 * Time: 13:35
 */
public class MaximumOrganismUpdater
        extends MinimumOrganismUpdater
        implements OrganismEnricher {


    @Override
    protected void processOrganism(Organism organismToEnrich) throws BadEnrichedFormException {
        super.processOrganism(organismToEnrich);

        //Synonyms.
    }


    /*public void enrichOrganism(Organism organismToEnrich) throws BadEnrichedFormException, MissingServiceException, BadToEnrichFormException, BridgeFailedException {

        Organism organismEnriched = getFullyEnrichedForm(organismToEnrich);
        runOrganismAdditionEnrichment(organismToEnrich, organismEnriched);
        runOrganismOverwriteUpdate(organismToEnrich, organismEnriched);
       // fireEnricherEvent(enricherEvent);
    }   */
}
