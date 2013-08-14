package psidev.psi.mi.jami.enricher.impl.organism;

import psidev.psi.mi.jami.bridges.fetcher.OrganismFetcher;
import psidev.psi.mi.jami.enricher.OrganismEnricher;
import psidev.psi.mi.jami.enricher.util.AliasMerger;
import psidev.psi.mi.jami.model.Alias;
import psidev.psi.mi.jami.model.Organism;

/**
 * Provides maximum enrichment of the organism.
 * Will enrich all aspects covered by the minimum enricher as well as enriching the Aliases.
 * As an enricher, no values from the provided organism to enrich will be changed.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since  13/06/13
 */
public class MaximumOrganismEnricher
        extends MinimumOrganismEnricher
        implements OrganismEnricher {


    public MaximumOrganismEnricher(){}

    public MaximumOrganismEnricher(OrganismFetcher organismFetcher) {
        super(organismFetcher);
    }

    /**
     * Strategy for the organism enrichment.
     * This method can be overwritten to change how the organism is enriched.
     * @param organismToEnrich   The protein to be enriched.
     */
    @Override
    protected void processOrganism(Organism organismToEnrich) {
        super.processOrganism(organismToEnrich);

        if(organismToEnrich.getTaxId() == organismFetched.getTaxId()
                && organismFetched.getTaxId() != -3){


            if(! organismFetched.getAliases().isEmpty()) {
                AliasMerger merger = new AliasMerger();
                merger.merge(organismFetched.getAliases() , organismToEnrich.getAliases());

                for(Alias alias: merger.getToAdd()){
                    organismToEnrich.getAliases().add(alias);
                    if(getOrganismEnricherListener() != null)
                        getOrganismEnricherListener().onAddedAlias(organismToEnrich, alias);
                }
            }

        }

    }

}
