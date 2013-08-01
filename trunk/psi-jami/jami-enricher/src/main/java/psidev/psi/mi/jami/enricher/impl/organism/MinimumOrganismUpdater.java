package psidev.psi.mi.jami.enricher.impl.organism;

import psidev.psi.mi.jami.bridges.fetcher.OrganismFetcher;
import psidev.psi.mi.jami.enricher.OrganismEnricher;
import psidev.psi.mi.jami.model.Organism;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 13/06/13
 * Time: 17:05
 */
public class MinimumOrganismUpdater
        extends AbstractOrganismEnricher
        implements OrganismEnricher {

    public MinimumOrganismUpdater(){}

    public MinimumOrganismUpdater(OrganismFetcher organismFetcher) {
        super(organismFetcher);
    }

    @Override
    protected void processOrganism(Organism organismToEnrich) {
        // Only enrich if an organism was fetched
        if(organismFetched.getTaxId() != -3){

            // TaxID
            if(organismToEnrich.getTaxId() != organismFetched.getTaxId() ){

                String oldValue = Integer.toString(organismToEnrich.getTaxId());
                organismToEnrich.setTaxId(organismFetched.getTaxId());
                if (getOrganismEnricherListener() != null)
                    getOrganismEnricherListener().onTaxidUpdate(organismToEnrich, oldValue);
            }

            // Scientific name
            if(organismFetched.getScientificName() != null
                    && ! organismFetched.getScientificName().equalsIgnoreCase(
                    organismToEnrich.getScientificName())){

                String oldValue = organismToEnrich.getScientificName();
                organismToEnrich.setScientificName(organismFetched.getScientificName());
                if (getOrganismEnricherListener() != null)
                    getOrganismEnricherListener().onScientificNameUpdate(organismToEnrich, oldValue);
            }

            // Common name
            if(organismFetched.getCommonName() != null
                    && ! organismFetched.getCommonName().equalsIgnoreCase(
                    organismToEnrich.getCommonName())){

                String oldValue = organismToEnrich.getCommonName();
                organismToEnrich.setCommonName(organismFetched.getCommonName());
                if (getOrganismEnricherListener() != null)
                    getOrganismEnricherListener().onCommonNameUpdate(organismToEnrich, oldValue);
            }
        }

    }

}
