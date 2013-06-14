package psidev.psi.mi.jami.enricher.impl.cvterm;


import psidev.psi.mi.jami.enricher.CvTermEnricher;
import psidev.psi.mi.jami.enricher.util.CollectionManipulationUtils;
import psidev.psi.mi.jami.model.Alias;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.utils.comparator.alias.DefaultAliasComparator;

import java.util.Collection;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 13/06/13
 * Time: 17:02
 */
public class MaximumCvTermEnricher
        extends MinimumCvTermEnricher
        implements CvTermEnricher {


    @Override
    protected void processCvTerm(CvTerm cvTermToEnrich){
        super.processCvTerm(cvTermToEnrich);

        //Add synonyms
        Collection<Alias> subtractedSynonyms = CollectionManipulationUtils.comparatorSubtract(
                cvTermFetched.getSynonyms(),
                cvTermToEnrich.getSynonyms(),
                new DefaultAliasComparator());

        for(Alias aliasSynonym: subtractedSynonyms){
            cvTermToEnrich.getSynonyms().add(aliasSynonym);
            if (listener != null) listener.onAddedSynonym(cvTermToEnrich , aliasSynonym);
        }


    }


}
