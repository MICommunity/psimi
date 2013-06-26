package psidev.psi.mi.jami.enricher.impl.organism;

import psidev.psi.mi.jami.enricher.OrganismEnricher;
import psidev.psi.mi.jami.enricher.exception.BadEnrichedFormException;
import psidev.psi.mi.jami.enricher.util.CollectionManipulationUtils;
import psidev.psi.mi.jami.model.Alias;
import psidev.psi.mi.jami.model.Organism;
import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.jami.utils.comparator.alias.DefaultAliasComparator;
import psidev.psi.mi.jami.utils.comparator.xref.DefaultXrefComparator;

import java.util.ArrayList;
import java.util.Collection;

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


    @Override
    protected void processOrganism(Organism organismToEnrich) {
        super.processOrganism(organismToEnrich);

        if(organismToEnrich.getTaxId() == organismFetched.getTaxId()){
            //TODO confirm this is the correct set of conditions
            if( ! organismFetched.getAliases().isEmpty()
                    && organismToEnrich.getAliases().isEmpty()){

                for(Alias alias : organismFetched.getAliases()){
                    if(listener != null) listener.onAddedAlias(organismToEnrich , alias);
                    organismToEnrich.getAliases().add(alias);
                }
            }
        }

    }

}
