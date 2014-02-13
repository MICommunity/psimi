package psidev.psi.mi.jami.enricher.impl.full;

import psidev.psi.mi.jami.bridges.fetcher.ProteinFetcher;
import psidev.psi.mi.jami.enricher.impl.minimal.MinimalProteinEnricher;
import psidev.psi.mi.jami.enricher.listener.ProteinEnricherListener;
import psidev.psi.mi.jami.model.Protein;

/**
 * Provides full enrichment of the Protein.
 *
 * See description of full enrichment in AbstractInteractorEnricher
 * If the protein remapper is not null and it cannot find a uniprot identifier, it will remap to uniprot using the proteinMapper.
 *
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 13/06/13
 */
public class FullProteinEnricher extends MinimalProteinEnricher {

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
            if (getListener() instanceof ProteinEnricherListener){
                ((ProteinEnricherListener)getListener()).onSequenceUpdate(proteinToEnrich, null);
            }
        }
    }
}
