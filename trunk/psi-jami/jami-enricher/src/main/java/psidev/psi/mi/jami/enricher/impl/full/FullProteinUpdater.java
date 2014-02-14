package psidev.psi.mi.jami.enricher.impl.full;


import psidev.psi.mi.jami.bridges.fetcher.ProteinFetcher;
import psidev.psi.mi.jami.enricher.impl.minimal.MinimalProteinUpdater;
import psidev.psi.mi.jami.enricher.listener.ProteinEnricherListener;
import psidev.psi.mi.jami.model.Protein;


/**
 * Full updater of proteins. As an updater, some data may be overwritten in the protein being enriched.
 * See description of full update in AbstractInteractorUpdater
 * If the protein remapper is not null and the enricher cannot find a uniprot identifier, it will remap to uniprot using the proteinMapper.
 * In case of dead uniprot entries, it will move the dead uniprot identifier to the xrefs and add a qualifier 'uniprot-removed-ac' and add a caution in the annotations.
 * If the remapping is successfull, it will remove any cautions left because of dead entry and set the uniprot identifier of the protein
 *
 * - update sequence of polymer. If the sequence of the polymer to enrich is different from the one of the fetched polymer, it will enrich it with the
 * sequence of the fetched polymer
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
