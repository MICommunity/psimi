package psidev.psi.mi.jami.enricher.impl.gene;

import psidev.psi.mi.jami.bridges.fetcher.GeneFetcher;
import psidev.psi.mi.jami.enricher.util.XrefMerger;
import psidev.psi.mi.jami.model.Gene;
import psidev.psi.mi.jami.model.Xref;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 04/09/13
 */
public class MaximumGeneEnricher
        extends MinimumGeneEnricher{

    public MaximumGeneEnricher(GeneFetcher fetcher) {
        super(fetcher);
    }

    public void processGene(Gene geneToEnrich){

        super.processGene(geneToEnrich);

        // == Xref ====================================================================
        if( ! geneToEnrich.getXrefs().isEmpty()
                && ! geneFetched.getXrefs().isEmpty() ){
            XrefMerger merger = new XrefMerger();
            merger.merge(geneFetched.getXrefs() , geneToEnrich.getXrefs() , false);
            for(Xref xref : merger.getToAdd()){
                geneToEnrich.getXrefs().add(xref);
                if(getGeneEnricherListener() != null)
                    getGeneEnricherListener().onAddedXref(geneToEnrich , xref);
            }
        }

    }
}
