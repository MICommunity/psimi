package psidev.psi.mi.jami.enricher.impl.cvterm;


import psidev.psi.mi.jami.enricher.CvTermEnricher;
import psidev.psi.mi.jami.enricher.util.CollectionManipulationUtils;
import psidev.psi.mi.jami.model.Alias;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.utils.comparator.alias.DefaultAliasComparator;

import java.util.Collection;

/**
 * Provides maximum enrichment of the CvTerm.
 * Will enrich all aspects covered by the minimum enricher as well as enriching the Aliases.
 * As an enricher, no values from the provided CvTerm to enrich will be changed.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 13/06/13
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
