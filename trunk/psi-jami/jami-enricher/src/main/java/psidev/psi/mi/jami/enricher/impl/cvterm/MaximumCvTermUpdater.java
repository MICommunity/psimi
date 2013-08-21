package psidev.psi.mi.jami.enricher.impl.cvterm;


import psidev.psi.mi.jami.bridges.fetcher.CvTermFetcher;
import psidev.psi.mi.jami.enricher.CvTermEnricher;

import psidev.psi.mi.jami.enricher.util.AliasMerger;
import psidev.psi.mi.jami.model.Alias;
import psidev.psi.mi.jami.model.CvTerm;



/**
 * Provides maximum updating of the CvTerm.
 * Will update all aspects covered by the minimum updater as well as updating the Aliases.
 * As an updater, values from the provided CvTerm to enrich may be overwritten.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since  13/05/13
 */
public class MaximumCvTermUpdater
        extends MinimumCvTermUpdater
        implements CvTermEnricher {

    /**
     * A constructor matching super.
     * @param cvTermFetcher The fetcher to initiate the enricher with.
     *                      If null, an illegal state exception will be thrown at the next enrichment.
     */
    public MaximumCvTermUpdater(CvTermFetcher cvTermFetcher) {
        super(cvTermFetcher);
    }

    /**
     * A method that can be overridden to add to or change the behaviour of enrichment without effecting fetching.
     * @param cvTermToEnrich the CvTerm to enrich
     */
    @Override
    protected void processCvTerm(CvTerm cvTermToEnrich){

        super.processCvTerm(cvTermToEnrich);


        // == Synonyms =============================================================
        if(! cvTermFetched.getSynonyms().isEmpty()) {
            AliasMerger merger = new AliasMerger();
            merger.merge(cvTermFetched.getSynonyms() , cvTermToEnrich.getSynonyms());

            for(Alias alias: merger.getToRemove()){
                cvTermToEnrich.getSynonyms().remove(alias);
                if(getCvTermEnricherListener() != null)
                    getCvTermEnricherListener().onRemovedSynonym(cvTermToEnrich , alias);
            }

            for(Alias alias: merger.getToAdd()){
                cvTermToEnrich.getSynonyms().add(alias);
                if(getCvTermEnricherListener() != null)
                    getCvTermEnricherListener().onAddedSynonym(cvTermToEnrich, alias);
            }
        }
    }

}
