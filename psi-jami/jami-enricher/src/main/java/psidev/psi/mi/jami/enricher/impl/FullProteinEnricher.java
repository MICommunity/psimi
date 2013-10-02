package psidev.psi.mi.jami.enricher.impl;

import psidev.psi.mi.jami.bridges.fetcher.ProteinFetcher;
import psidev.psi.mi.jami.model.Protein;

/**
 * Provides maximum enrichment of the Protein.
 * Will enrich all aspects covered by the minimum enricher as well as enriching the Xrefs.
 * As an enricher, no values from the provided protein to enrich will be changed.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 13/06/13
 */
public class FullProteinEnricher extends MinimalProteinEnricher{

    /**
     * The only constructor which forces the setting of the fetcher
     * If the cvTerm fetcher is null, an illegal state exception will be thrown at the next enrichment.
     * @param proteinFetcher    The protein fetcher to use.
     */
    public FullProteinEnricher(ProteinFetcher proteinFetcher) {
        super(proteinFetcher);
    }


    @Override
    protected boolean isFullEnrichment() {
        return true;
    }

    @Override
    protected void processOtherProperties(Protein proteinToEnrich, Protein fetched) {

        // sequence
        if (proteinToEnrich.getSequence() == null && fetched.getSequence() != null){
            proteinToEnrich.setSequence(fetched.getSequence());
            if (getProteinEnricherListener() != null){
                getProteinEnricherListener().onSequenceUpdate(proteinToEnrich, null);
            }
        }
    }
}
