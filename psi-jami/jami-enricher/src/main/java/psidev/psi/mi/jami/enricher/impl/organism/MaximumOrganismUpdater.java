package psidev.psi.mi.jami.enricher.impl.organism;


import psidev.psi.mi.jami.bridges.fetcher.OrganismFetcher;
import psidev.psi.mi.jami.enricher.OrganismEnricher;
import psidev.psi.mi.jami.enricher.util.AliasMerger;
import psidev.psi.mi.jami.model.Alias;
import psidev.psi.mi.jami.model.Organism;

/**
 * Provides maximum updating of the organism.
 * Will update all aspects covered by the minimum updater as well as updating the Aliases.
 * As an updater, values from the provided CvTerm to enrich may be overwritten.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since  24/05/13
 */
public class MaximumOrganismUpdater
        extends MinimumOrganismUpdater
        implements OrganismEnricher {

    public MaximumOrganismUpdater(){}

    public MaximumOrganismUpdater(OrganismFetcher organismFetcher) {
        super(organismFetcher);
    }

    /**
     * Strategy for the organism enrichment.
     * This method can be overwritten to change how the organism is enriched.
     * @param organismToEnrich   The protein to be enriched.
     */
    @Override
    protected void processOrganism(Organism organismToEnrich)  {
        super.processOrganism(organismToEnrich);

        // Override TaxID but obviously not possible if organism is unknown
        if(organismFetched.getTaxId() != -3){

            if(! organismFetched.getAliases().isEmpty()) {
                AliasMerger merger = new AliasMerger();
                merger.merge(organismFetched.getAliases() , organismToEnrich.getAliases());
                for(Alias alias: merger.getToRemove()){
                    organismToEnrich.getAliases().remove(alias);
                    if(getOrganismEnricherListener() != null)
                        getOrganismEnricherListener().onRemovedAlias(organismToEnrich , alias);
                }
                for(Alias alias: merger.getToAdd()){
                    organismToEnrich.getAliases().add(alias);
                    if(getOrganismEnricherListener() != null)
                        getOrganismEnricherListener().onAddedAlias(organismToEnrich, alias);
                }
            }
        }
    }
}
