package psidev.psi.mi.jami.enricher.impl.cvterm;

import psidev.psi.mi.jami.bridges.fetcher.CvTermFetcher;
import psidev.psi.mi.jami.enricher.CvTermEnricher;
import psidev.psi.mi.jami.enricher.util.AliasMerger;
import psidev.psi.mi.jami.model.Alias;
import psidev.psi.mi.jami.model.CvTerm;

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

    /**
     * A constructor matching super.
     * @param cvTermFetcher The fetcher to initiate the enricher with.
     *                      If null, an illegal state exception will be thrown at the next enrichment.
     */
    public MaximumCvTermEnricher(CvTermFetcher cvTermFetcher) {
        super(cvTermFetcher);
    }

    /**
     * Strategy for the cvTerm enrichment.
     * This method can be overwritten to change how the cvTerm is enriched.
     * @param cvTermToEnrich   The protein to be enriched.
     */
    @Override
    protected void processCvTerm(CvTerm cvTermToEnrich){
        super.processCvTerm(cvTermToEnrich);

        // == Synonyms =======================================================
        if(! cvTermFetched.getSynonyms().isEmpty()) {
            AliasMerger merger = new AliasMerger();
            merger.merge(cvTermFetched.getSynonyms() , cvTermToEnrich.getSynonyms());

            for(Alias alias: merger.getToAdd()){
                cvTermToEnrich.getSynonyms().add(alias);
                if(getCvTermEnricherListener() != null)
                    getCvTermEnricherListener().onAddedSynonym(cvTermToEnrich, alias);
            }
        }
    }
}
