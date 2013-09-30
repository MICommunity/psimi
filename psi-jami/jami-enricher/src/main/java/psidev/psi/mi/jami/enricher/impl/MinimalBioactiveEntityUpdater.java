package psidev.psi.mi.jami.enricher.impl;

import psidev.psi.mi.jami.bridges.fetcher.BioactiveEntityFetcher;
import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.enricher.util.EnricherUtils;
import psidev.psi.mi.jami.model.BioactiveEntity;
import psidev.psi.mi.jami.utils.comparator.cv.DefaultCvTermComparator;
import psidev.psi.mi.jami.utils.comparator.organism.OrganismTaxIdComparator;

/**
 * Provides minimum updating of the bioactiveEntity.
 * As an updater, values from the provided bioactiveEntity to enrich may be overwritten.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 07/08/13
 */
public class MinimalBioactiveEntityUpdater
        extends MinimalBioactiveEntityEnricher{

    /**
     * A constructor which initiates with a fetcher.
     * @param fetcher   The fetcher to use to gather bioactive entity records.
     */
    public MinimalBioactiveEntityUpdater(BioactiveEntityFetcher fetcher) {
        super(fetcher);
    }

    @Override
    protected void processShortLabel(BioactiveEntity bioactiveEntityToEnrich, BioactiveEntity fetched) {
        if(!fetched.getShortName().equalsIgnoreCase(bioactiveEntityToEnrich.getShortName())){
            String oldValue = bioactiveEntityToEnrich.getShortName();
            bioactiveEntityToEnrich.setShortName(fetched.getShortName());
            if(getBioactiveEntityEnricherListener() != null)
                getBioactiveEntityEnricherListener().onShortNameUpdate(bioactiveEntityToEnrich , oldValue);
        }    }

    @Override
    protected void processAliases(BioactiveEntity bioactiveEntityToEnrich, BioactiveEntity fetched) {
        EnricherUtils.mergeAliases(bioactiveEntityToEnrich, bioactiveEntityToEnrich.getAliases(), fetched.getAliases(), true,
                getListener());
    }

    @Override
    protected void processIdentifiers(BioactiveEntity bioactiveEntityToEnrich, BioactiveEntity fetched) {
        EnricherUtils.mergeXrefs(bioactiveEntityToEnrich, bioactiveEntityToEnrich.getIdentifiers(), fetched.getIdentifiers(), true, true,
                getListener(), getListener());
    }

    @Override
    protected void processFullName(BioactiveEntity bioactiveEntityToEnrich, BioactiveEntity fetched) {
        if((fetched.getFullName() != null && !fetched.getFullName().equalsIgnoreCase(bioactiveEntityToEnrich.getFullName())
             || (fetched.getFullName() == null && bioactiveEntityToEnrich.getFullName() != null))){
            String oldValue = bioactiveEntityToEnrich.getFullName();
            bioactiveEntityToEnrich.setFullName(fetched.getFullName());
            if(getBioactiveEntityEnricherListener() != null)
                getBioactiveEntityEnricherListener().onFullNameUpdate(bioactiveEntityToEnrich , oldValue);
        }    }

    @Override
    protected void processInteractorType(BioactiveEntity entityToEnrich, BioactiveEntity fetched) throws EnricherException {
        if (fetched.getInteractorType() != null && DefaultCvTermComparator.areEquals(entityToEnrich.getInteractorType(), fetched.getInteractorType())){
            entityToEnrich.setInteractorType(fetched.getInteractorType());
            if (getListener() != null){
                getListener().onAddedInteractorType(entityToEnrich);
            }
        }
        if (getCvTermEnricher() != null){
            getCvTermEnricher().enrich(entityToEnrich.getInteractorType());
        }
    }

    @Override
    protected void processOrganism(BioactiveEntity entityToEnrich, BioactiveEntity fetched) throws EnricherException {
        if (fetched.getOrganism() != null && !OrganismTaxIdComparator.areEquals(entityToEnrich.getOrganism(), fetched.getOrganism())){
            entityToEnrich.setOrganism(fetched.getOrganism());
            if (getListener() != null){
                getListener().onAddedOrganism(entityToEnrich);
            }
        }

        if (getOrganismEnricher() != null && entityToEnrich.getOrganism() != null){
            getOrganismEnricher().enrichOrganism(entityToEnrich.getOrganism());
        }
    }
}
