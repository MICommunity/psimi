package psidev.psi.mi.jami.enricher.impl.organism;

import psidev.psi.mi.jami.bridges.fetcher.OrganismFetcher;
import psidev.psi.mi.jami.enricher.OrganismEnricher;
import psidev.psi.mi.jami.enricher.util.AliasMerger;
import psidev.psi.mi.jami.model.Alias;
import psidev.psi.mi.jami.model.Organism;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 13/06/13
 * Time: 17:05
 */
public class MaximumOrganismEnricher
        extends MinimumOrganismEnricher
        implements OrganismEnricher {


    public MaximumOrganismEnricher(){}

    public MaximumOrganismEnricher(OrganismFetcher organismFetcher) {
        super(organismFetcher);
    }

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
