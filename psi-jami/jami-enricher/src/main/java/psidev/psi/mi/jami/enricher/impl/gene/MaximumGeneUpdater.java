package psidev.psi.mi.jami.enricher.impl.gene;

import psidev.psi.mi.jami.bridges.fetcher.GeneFetcher;
import psidev.psi.mi.jami.model.Gene;
import psidev.psi.mi.jami.model.Xref;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 04/09/13
 */
public class MaximumGeneUpdater
        extends MinimumGeneUpdater{

    public MaximumGeneUpdater(GeneFetcher fetcher) {
        super(fetcher);
    }

    public void processGene(Gene geneToEnrich){

        super.processGene(geneToEnrich);

        // == Xref ====================================================================
        if( ! geneToEnrich.getXrefs().isEmpty()
                && ! geneFetched.getXrefs().isEmpty() ){
            XrefMergeUtils merger = new XrefMergeUtils();
            merger.merge(geneFetched.getXrefs() , geneToEnrich.getXrefs() , false);
            for(Xref xref : merger.getToAdd()){
                geneToEnrich.getXrefs().add(xref);
                if(getGeneEnricherListener() != null)
                    getGeneEnricherListener().onAddedXref(geneToEnrich , xref);
            }
            for(Xref xref : merger.getToRemove()){
                geneToEnrich.getXrefs().remove(xref);
                if(getGeneEnricherListener() != null)
                    getGeneEnricherListener().onRemovedXref(geneToEnrich , xref);
            }
        }
    }
}
