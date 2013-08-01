package psidev.psi.mi.jami.enricher.impl.organism;


import psidev.psi.mi.jami.bridges.fetcher.OrganismFetcher;
import psidev.psi.mi.jami.enricher.OrganismEnricher;
import psidev.psi.mi.jami.enricher.util.AliasUpdateMerger;
import psidev.psi.mi.jami.model.Alias;
import psidev.psi.mi.jami.model.Organism;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 24/05/13
 * Time: 13:35
 */
public class MaximumOrganismUpdater
        extends MinimumOrganismUpdater
        implements OrganismEnricher {

    public MaximumOrganismUpdater(){}

    public MaximumOrganismUpdater(OrganismFetcher organismFetcher) {
        super(organismFetcher);
    }

    @Override
    protected void processOrganism(Organism organismToEnrich)  {
        super.processOrganism(organismToEnrich);

        // Override TaxID but obviously not possible if organism is unknown
        if(organismFetched.getTaxId() != -3){

            if(! organismFetched.getAliases().isEmpty()) {
                AliasUpdateMerger merger = new AliasUpdateMerger();
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
