package psidev.psi.mi.jami.enricher.impl.minimal;

import psidev.psi.mi.jami.bridges.fetcher.OrganismFetcher;
import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.model.Organism;

/**
 * Provides minimum update of a Organism.
 *
 * - update taxid if different from fetched organism taxid.
 * - update common name if different from fetched organism common name.
 * - update scientific name if different from fetched organism scientific name.
 *
 * The organism fetcher is required for enriching organism
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since  13/06/13
 */
public class MinimalOrganismUpdater extends MinimalOrganismEnricher {

    public MinimalOrganismUpdater(OrganismFetcher organismFetcher) {
        super(organismFetcher);
    }

    @Override
    protected void processScientificName(Organism organismToEnrich, Organism organismFetched) {
        // Scientific name
        if((organismFetched.getScientificName() != null
                && ! organismFetched.getScientificName().equalsIgnoreCase(organismToEnrich.getScientificName())
            ||(organismFetched.getScientificName() == null && organismToEnrich.getScientificName() != null))){

            String oldValue = organismToEnrich.getScientificName();
            organismToEnrich.setScientificName(organismFetched.getScientificName());
            if (getOrganismEnricherListener() != null)
                getOrganismEnricherListener().onScientificNameUpdate(organismToEnrich, oldValue);
        }
    }

    @Override
    protected void processCommonName(Organism organismToEnrich, Organism organismFetched) {
// Common name
        if((organismFetched.getCommonName() != null
                && ! organismFetched.getCommonName().equalsIgnoreCase(organismToEnrich.getCommonName()))
                ||(organismFetched.getCommonName() == null && organismToEnrich.getCommonName() != null)){

            String oldValue = organismToEnrich.getCommonName();
            organismToEnrich.setCommonName(organismFetched.getCommonName());
            if (getOrganismEnricherListener() != null)
                getOrganismEnricherListener().onCommonNameUpdate(organismToEnrich, oldValue);
        }
    }

    @Override
    protected void processTaxid(Organism organismToEnrich, Organism organismFetched) throws EnricherException{
        if (organismToEnrich.getTaxId() != organismFetched.getTaxId()){
            int oldTaxid = organismToEnrich.getTaxId();
            organismToEnrich.setTaxId(organismFetched.getTaxId());

            if(getOrganismEnricherListener() != null)
                getOrganismEnricherListener().onTaxidUpdate(organismToEnrich, Integer.toString(oldTaxid));
        }

    }

}
