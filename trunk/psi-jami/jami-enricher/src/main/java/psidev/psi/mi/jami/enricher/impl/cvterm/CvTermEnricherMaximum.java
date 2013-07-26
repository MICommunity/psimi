package psidev.psi.mi.jami.enricher.impl.cvterm;

import psidev.psi.mi.jami.bridges.fetcher.CvTermFetcher;
import psidev.psi.mi.jami.enricher.CvTermEnricher;
import psidev.psi.mi.jami.enricher.util.AliasUpdateMerger;
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
public class CvTermEnricherMaximum
        extends CvTermEnricherMinimum
        implements CvTermEnricher {


    public CvTermEnricherMaximum(CvTermFetcher cvTermFetcher) {
        super(cvTermFetcher);
    }

    @Override
    protected void processCvTerm(CvTerm cvTermToEnrich){
        super.processCvTerm(cvTermToEnrich);

        if(! cvTermFetched.getSynonyms().isEmpty()) {
            AliasUpdateMerger merger = new AliasUpdateMerger();
            merger.merge(cvTermFetched.getSynonyms() , cvTermToEnrich.getSynonyms());

            for(Alias alias: merger.getToAdd()){
                cvTermToEnrich.getSynonyms().add(alias);
                if(listener != null) listener.onAddedSynonym(cvTermToEnrich, alias);
            }
        }
    }
}
