package psidev.psi.mi.jami.enricher.impl.full;


import psidev.psi.mi.jami.bridges.fetcher.ProteinFetcher;
import psidev.psi.mi.jami.enricher.impl.minimal.MinimalProteinUpdater;
import psidev.psi.mi.jami.enricher.listener.ProteinEnricherListener;
import psidev.psi.mi.jami.model.Protein;


/**
 * Provides maximum updating of the Protein.
 * See description of full update in AbstractInteractorUpdater
 * If the protein remapper is not null and it cannot find a uniprot identifier, it will remap to uniprot using the proteinMapper.
 * - enrich sequence of protein. If the sequence of the protein to enrich is null, it will enrich it with the
 * sequence of the fetched protein
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 20/05/13
 */
public class FullProteinUpdater extends MinimalProteinUpdater {

    public FullProteinUpdater(ProteinFetcher proteinFetcher) {
        super(proteinFetcher);
    }

    @Override
    protected boolean isFullEnrichment() {
        return true;
    }

    @Override
    protected void processOtherProperties(Protein proteinToEnrich, Protein fetched) {

        // sequence
        if ((fetched.getSequence() != null && !fetched.getSequence().equalsIgnoreCase(proteinToEnrich.getSequence())
             || (fetched.getSequence() == null && proteinToEnrich.getSequence() != null))){
            String oldSeq = proteinToEnrich.getSequence();
            proteinToEnrich.setSequence(fetched.getSequence());
            if (getListener() instanceof ProteinEnricherListener){
                ((ProteinEnricherListener)getListener()).onSequenceUpdate(proteinToEnrich, oldSeq);
            }
        }
    }
}
