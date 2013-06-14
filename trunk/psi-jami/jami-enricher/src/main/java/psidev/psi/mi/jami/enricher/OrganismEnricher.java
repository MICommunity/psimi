package psidev.psi.mi.jami.enricher;

import psidev.psi.mi.jami.bridges.exception.BridgeFailedException;
import psidev.psi.mi.jami.bridges.fetcher.OrganismFetcher;
import psidev.psi.mi.jami.enricher.exception.BadEnrichedFormException;
import psidev.psi.mi.jami.enricher.exception.BadToEnrichFormException;
import psidev.psi.mi.jami.enricher.exception.MissingServiceException;
import psidev.psi.mi.jami.enricher.impl.organism.listener.OrganismEnricherListener;
import psidev.psi.mi.jami.model.Organism;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 22/05/13
 * Time: 10:06
 */
public interface OrganismEnricher{

    public boolean enrichOrganism(Organism organismToEnrich) throws BadEnrichedFormException, MissingServiceException, BadToEnrichFormException, BridgeFailedException;

    public void setFetcher(OrganismFetcher fetcher);
    public OrganismFetcher getFetcher();

    public void setOrganismEnricherListener(OrganismEnricherListener organismEnricherListener);
    public OrganismEnricherListener getOrganismEnricherListener();
}
