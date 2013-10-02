package psidev.psi.mi.jami.enricher.impl;


import psidev.psi.mi.jami.bridges.fetcher.ProteinFetcher;
import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.model.Annotation;
import psidev.psi.mi.jami.model.Protein;
import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.jami.model.impl.DefaultXref;
import psidev.psi.mi.jami.utils.AnnotationUtils;

import java.util.Collection;

/**
 * Updates a protein to the minimum level. As an updater, data may be overwritten.
 * This covers short name, full name, primary AC, checksums, identifiers and aliases.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since  13/06/13
 */
public class MinimalProteinUpdater extends MinimalProteinEnricher {

    /**
     * The only constructor which forces the setting of the fetcher
     * If the cvTerm fetcher is null, an illegal state exception will be thrown at the next enrichment.
     * @param proteinFetcher    The protein fetcher to use.
     */
    public MinimalProteinUpdater(ProteinFetcher proteinFetcher) {
        super(proteinFetcher);
    }

    @Override
    protected void processDeadUniprotIdentity(Protein proteinToEnrich, Xref uniprotIdentity) {
        proteinToEnrich.getIdentifiers().remove(uniprotIdentity);
        if(getProteinEnricherListener() != null){
            getProteinEnricherListener().onRemovedIdentifier(proteinToEnrich, uniprotIdentity);
        }
        Xref removedIdentity = new DefaultXref(uniprotIdentity.getDatabase(), uniprotIdentity.getId(), UNIPROT_REMOVED_QUALIFIER);
        proteinToEnrich.getXrefs().add(removedIdentity);
        if(getProteinEnricherListener() != null){
            getProteinEnricherListener().onAddedXref(proteinToEnrich, removedIdentity);
        }
    }

    /**
     * Attempts to remap the protein using the provided proteinRemapper.
     * If one has not been included, this method returns false and reports a failure to the listener.
     * If after remapping, the protein still has no entry, this entry returns false.
     *
     * @param proteinToEnrich   The protein to find a remapping for.
     * @return                  Whether the remapping was successful.
     */
    @Override
    protected boolean remapProtein(Protein proteinToEnrich) throws EnricherException {
        if (super.remapProtein(proteinToEnrich)){

            Collection<Annotation> cautions = AnnotationUtils.collectAllAnnotationsHavingTopic(proteinToEnrich.getAnnotations(), Annotation.CAUTION_MI, Annotation.CAUTION);
            for (Annotation caution : cautions){
                if (caution.getValue() != null && CAUTION_MESSAGE.equalsIgnoreCase(caution.getValue())){
                    proteinToEnrich.getAnnotations().remove(caution);
                    if(getProteinEnricherListener() != null){
                        getProteinEnricherListener().onRemovedAnnotation(proteinToEnrich, caution);
                    }
                }
            }
            return true;
        }
        return false;
    }
}
