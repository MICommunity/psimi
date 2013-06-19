package psidev.psi.mi.jami.enricher.impl.organism;

import psidev.psi.mi.jami.bridges.exception.BridgeFailedException;
import psidev.psi.mi.jami.bridges.fetcher.OrganismFetcher;
import psidev.psi.mi.jami.enricher.OrganismEnricher;
import psidev.psi.mi.jami.enricher.exception.BadEnrichedFormException;
import psidev.psi.mi.jami.enricher.exception.BadToEnrichFormException;
import psidev.psi.mi.jami.enricher.exception.MissingServiceException;
import psidev.psi.mi.jami.enricher.impl.organism.listener.OrganismEnricherListener;
import psidev.psi.mi.jami.model.Organism;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 14/06/13
 */
public abstract class AbstractOrganismEnricher
        implements OrganismEnricher {

    protected OrganismFetcher fetcher = null;
    protected OrganismEnricherListener listener = null;

    protected Organism organismFetched = null;


    public boolean enrichOrganism(Organism organismToEnrich) throws BadEnrichedFormException, MissingServiceException, BadToEnrichFormException, BridgeFailedException {
        organismFetched = fetchOrganism(organismToEnrich);
        if(organismFetched == null){
            if(listener != null) listener.onOrganismEnriched(organismToEnrich,"Failed. No organism could be found.");
            return false;
        }

        processOrganism(organismToEnrich);

        if(listener != null) listener.onOrganismEnriched(organismToEnrich,"Success. Organism enriched.");
        return true;
    }

    protected abstract void processOrganism(Organism organismToEnrich) throws BadEnrichedFormException;


    private Organism fetchOrganism(Organism organismToEnrich){
        return null;
    }




    public void setFetcher(OrganismFetcher fetcher) {
        this.fetcher = fetcher;
    }

    public OrganismFetcher getFetcher() {
        return fetcher;
    }

    public void setOrganismEnricherListener(OrganismEnricherListener organismEnricherListener) {
        this.listener = organismEnricherListener;
    }

    public OrganismEnricherListener getOrganismEnricherListener() {
        return listener;
    }
}
