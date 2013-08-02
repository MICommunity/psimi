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
 * Date: 13/05/13
 */
public class MaximumCvTermUpdater
        extends MinimumCvTermUpdater
        implements CvTermEnricher {

    public MaximumCvTermUpdater(CvTermFetcher cvTermFetcher) {
        super(cvTermFetcher);
    }

    @Override
    protected void processCvTerm(CvTerm cvTermToEnrich){

        super.processCvTerm(cvTermToEnrich);


        // Add synonyms
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
