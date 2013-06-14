package psidev.psi.mi.jami.enricher.impl.organism;

import psidev.psi.mi.jami.enricher.OrganismEnricher;
import psidev.psi.mi.jami.enricher.exception.BadEnrichedFormException;
import psidev.psi.mi.jami.model.Alias;
import psidev.psi.mi.jami.model.Organism;
import psidev.psi.mi.jami.utils.comparator.alias.DefaultAliasComparator;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 13/06/13
 * Time: 17:05
 */
public class MaximumOrganismEnricher
        extends MinimumOrganismEnricher
        implements OrganismEnricher {


    @Override
    protected void processOrganism(Organism organismToEnrich) throws BadEnrichedFormException {
        super.processOrganism(organismToEnrich);

        //todo Add synonyms
        if(organismToEnrich.getTaxId() == organismFetched.getTaxId()){
            organismToEnrich.getAliases().isEmpty();
        }

    }

}
