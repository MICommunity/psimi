package psidev.psi.mi.jami.enricher.impl;


import psidev.psi.mi.jami.bridges.fetcher.ProteinFetcher;
import psidev.psi.mi.jami.model.Protein;


/**
 * Provides maximum updating of the Protein.
 * Will update all aspects covered by the minimum updater as well as updating the xrefs.
 * As an updater, values from the provided protein to enrich may be overwritten.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 20/05/13
 */
public class FullProteinUpdater extends MinimalProteinUpdater{


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
            if (getProteinEnricherListener() != null){
                getProteinEnricherListener().onSequenceUpdate(proteinToEnrich, oldSeq);
            }
        }
    }
}
