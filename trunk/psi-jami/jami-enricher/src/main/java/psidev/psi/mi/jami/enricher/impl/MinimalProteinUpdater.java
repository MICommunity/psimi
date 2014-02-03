package psidev.psi.mi.jami.enricher.impl;


import psidev.psi.mi.jami.bridges.fetcher.ProteinFetcher;
import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.enricher.util.EnricherUtils;
import psidev.psi.mi.jami.model.*;
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
        if(getListener() != null){
            getListener().onRemovedIdentifier(proteinToEnrich, uniprotIdentity);
        }
        Xref removedIdentity = new DefaultXref(uniprotIdentity.getDatabase(), uniprotIdentity.getId(), UNIPROT_REMOVED_QUALIFIER);
        proteinToEnrich.getXrefs().add(removedIdentity);
        if(getListener() != null){
            getListener().onAddedXref(proteinToEnrich, removedIdentity);
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
                    if(getListener() != null){
                        getListener().onRemovedAnnotation(proteinToEnrich, caution);
                    }
                }
            }
            return true;
        }
        return false;
    }

    @Override
    protected void processShortLabel(Protein bioactiveEntityToEnrich, Protein fetched) {
        if(!fetched.getShortName().equalsIgnoreCase(bioactiveEntityToEnrich.getShortName())){
            String oldValue = bioactiveEntityToEnrich.getShortName();
            bioactiveEntityToEnrich.setShortName(fetched.getShortName());
            if(getListener() != null)
                getListener().onShortNameUpdate(bioactiveEntityToEnrich , oldValue);
        }    }

    @Override
    protected void processAliases(Protein bioactiveEntityToEnrich, Protein fetched) {
        EnricherUtils.mergeAliases(bioactiveEntityToEnrich, bioactiveEntityToEnrich.getAliases(), fetched.getAliases(), true,
                getListener());
    }

    @Override
    protected void processIdentifiers(Protein bioactiveEntityToEnrich, Protein fetched) {
        EnricherUtils.mergeXrefs(bioactiveEntityToEnrich, bioactiveEntityToEnrich.getIdentifiers(), fetched.getIdentifiers(), true, true,
                getListener(), getListener());
    }

    @Override
    protected void processFullName(Protein bioactiveEntityToEnrich, Protein fetched) {
        if((fetched.getFullName() != null && !fetched.getFullName().equalsIgnoreCase(bioactiveEntityToEnrich.getFullName())
                || (fetched.getFullName() == null && bioactiveEntityToEnrich.getFullName() != null))){
            String oldValue = bioactiveEntityToEnrich.getFullName();
            bioactiveEntityToEnrich.setFullName(fetched.getFullName());
            if(getListener() != null)
                getListener().onFullNameUpdate(bioactiveEntityToEnrich , oldValue);
        }    }

    @Override
    protected void processInteractorType(Protein entityToEnrich, Protein fetched) throws EnricherException {
        if (fetched.getInteractorType() != entityToEnrich.getInteractorType()){
            CvTerm oldType = entityToEnrich.getInteractorType();
            entityToEnrich.setInteractorType(fetched.getInteractorType());
            if (getListener() != null){
                getListener().onInteractorTypeUpdate(entityToEnrich, oldType);
            }
        }
        if (getCvTermEnricher() != null){
            getCvTermEnricher().enrich(entityToEnrich.getInteractorType());
        }
    }

    @Override
    protected void processOrganism(Protein entityToEnrich, Protein fetched) throws EnricherException {
        if (fetched.getOrganism() != entityToEnrich.getOrganism()){
            Organism old = entityToEnrich.getOrganism();
            entityToEnrich.setOrganism(fetched.getOrganism());
            if (getListener() != null){
                getListener().onOrganismUpdate(entityToEnrich, old);
            }
        }

        if (getOrganismEnricher() != null && entityToEnrich.getOrganism() != null){
            getOrganismEnricher().enrich(entityToEnrich.getOrganism());
        }
    }

    @Override
    protected void processChecksums(Protein bioactiveEntityToEnrich, Protein fetched) throws EnricherException {
        EnricherUtils.mergeChecksums(bioactiveEntityToEnrich, bioactiveEntityToEnrich.getChecksums(), fetched.getChecksums(), true,
                getListener());
    }

    @Override
    protected void processXrefs(Protein bioactiveEntityToEnrich, Protein fetched) {
        EnricherUtils.mergeXrefs(bioactiveEntityToEnrich, bioactiveEntityToEnrich.getXrefs(), fetched.getXrefs(), true, false,
                getListener(), getListener());
    }
}
