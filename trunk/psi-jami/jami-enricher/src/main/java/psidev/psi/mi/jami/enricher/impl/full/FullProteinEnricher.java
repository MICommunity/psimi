package psidev.psi.mi.jami.enricher.impl.full;

import psidev.psi.mi.jami.bridges.fetcher.ProteinFetcher;
import psidev.psi.mi.jami.enricher.impl.minimal.MinimalProteinEnricher;
import psidev.psi.mi.jami.enricher.listener.ProteinEnricherListener;
import psidev.psi.mi.jami.model.Protein;

/**
 * Full enricher of proteins. As an enricher, no data will be overwritten in the protein being enriched.
 * See description of full enrichment in AbstractInteractorEnricher
 * If the protein remapper is not null and the enricher cannot find a uniprot identifier, it will remap to uniprot using the proteinMapper.
 * In case of dead uniprot entries, it will move the dead uniprot identifier to the xrefs and add a caution in the annotations.
 * If the remapping is successfull, it will remove any cautions left because of dead entry and set the uniprot identifier of the protein
 *
 * - enrich sequence of protein. If the sequence of the polymer to enrich is null, it will enrich it with the
 * sequence of the fetched protein
 *
 * The protein fetcher is require to enrich proteins
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
