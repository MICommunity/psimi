package psidev.psi.mi.jami.enricher.impl.organism;


import psidev.psi.mi.jami.enricher.OrganismEnricher;
import psidev.psi.mi.jami.model.Alias;
import psidev.psi.mi.jami.model.Organism;
import psidev.psi.mi.jami.utils.comparator.alias.DefaultAliasComparator;

import java.util.Collection;
import java.util.TreeSet;

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


    @Override
    protected void processOrganism(Organism organismToEnrich)  {
        super.processOrganism(organismToEnrich);

        // Override TaxID but obviously not possible if organism is unknown
        if(organismFetched.getTaxId() != -3){

            // ALIASES
           /* if( ! organismFetched.getAliases().isEmpty()){
                // Remove old aliases
                Collection<Alias> aliasesToChange = new TreeSet<Alias>(new DefaultAliasComparator());
                aliasesToChange.addAll(organismToEnrich.getAliases());
                aliasesToChange.removeAll(organismFetched.getAliases());

                for(Alias alias : aliasesToChange){
                    if(listener != null) listener.onRemovedAlias(organismToEnrich , alias);
                    organismToEnrich.getAliases().remove(alias);
                }

                // Add new aliases
                aliasesToChange.clear();
                aliasesToChange.addAll(organismFetched.getAliases());
                aliasesToChange.removeAll(organismToEnrich.getAliases());

                for(Alias alias : aliasesToChange){
                    if(listener != null) listener.onAddedAlias(organismToEnrich , alias);
                    organismToEnrich.getAliases().add(alias);
                }
            } */
        }
    }
}
