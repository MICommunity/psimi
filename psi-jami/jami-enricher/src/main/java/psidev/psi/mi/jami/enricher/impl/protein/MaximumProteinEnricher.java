package psidev.psi.mi.jami.enricher.impl.protein;

import psidev.psi.mi.jami.bridges.fetcher.ProteinFetcher;
import psidev.psi.mi.jami.enricher.ProteinEnricher;
import psidev.psi.mi.jami.model.Protein;
import psidev.psi.mi.jami.model.Xref;

/**
 * Provides maximum enrichment of the Protein.
 * Will enrich all aspects covered by the minimum enricher as well as enriching the Xrefs.
 * As an enricher, no values from the provided protein to enrich will be changed.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 13/06/13
 */
public class MaximumProteinEnricher
        extends MinimumProteinEnricher
        implements ProteinEnricher {

    /**
     * The only constructor which forces the setting of the fetcher
     * If the cvTerm fetcher is null, an illegal state exception will be thrown at the next enrichment.
     * @param proteinFetcher    The protein fetcher to use.
     */
    public MaximumProteinEnricher(ProteinFetcher proteinFetcher) {
        super(proteinFetcher);
    }


    /**
     * Strategy for the protein enrichment.
     * This method can be overwritten to change how the protein is enriched.
     * @param proteinToEnrich   The protein to be enriched.
     */
    @Override
    protected void processProtein(Protein proteinToEnrich) {
        super.processProtein(proteinToEnrich);

        if(! proteinFetched.getXrefs().isEmpty()) {
            XrefMergeUtils merger = new XrefMergeUtils();
            merger.merge(proteinFetched.getXrefs() , proteinToEnrich.getXrefs() , true);

            for(Xref xref: merger.getToAdd()){
                proteinToEnrich.getXrefs().add(xref);
                if(getProteinEnricherListener() != null)
                    getProteinEnricherListener().onAddedXref(proteinToEnrich, xref);
            }
        }
    }
}
