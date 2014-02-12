package psidev.psi.mi.jami.enricher.impl;

import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.enricher.listener.InteractorEnricherListener;
import psidev.psi.mi.jami.enricher.util.EnricherUtils;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Interactor;
import psidev.psi.mi.jami.model.Organism;
import psidev.psi.mi.jami.utils.comparator.cv.DefaultCvTermComparator;
import psidev.psi.mi.jami.utils.comparator.organism.DefaultOrganismComparator;

/**
 * Abstract class for updating interactor
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>01/10/13</pre>
 */

public abstract class AbstractInteractorUpdater<T extends Interactor> extends AbstractInteractorEnricher<T> {

    private AbstractInteractorEnricher<T> interactorEnricher;

    public AbstractInteractorUpdater(AbstractInteractorEnricher<T> interactorEnricher){
        if (interactorEnricher == null){
           throw new IllegalArgumentException("The base interactor enricher is required");
        }
        this.interactorEnricher = interactorEnricher;
    }

    @Override
    protected void processShortLabel(T bioactiveEntityToEnrich, T fetched) {
        if(!fetched.getShortName().equalsIgnoreCase(bioactiveEntityToEnrich.getShortName())){
            String oldValue = bioactiveEntityToEnrich.getShortName();
            bioactiveEntityToEnrich.setShortName(fetched.getShortName());
            if(getListener() != null)
                getListener().onShortNameUpdate(bioactiveEntityToEnrich , oldValue);
        }    }

    @Override
    protected void processAliases(T bioactiveEntityToEnrich, T fetched) {
        EnricherUtils.mergeAliases(bioactiveEntityToEnrich, bioactiveEntityToEnrich.getAliases(), fetched.getAliases(), true,
                getListener());
    }

    @Override
    protected void processIdentifiers(T bioactiveEntityToEnrich, T fetched) {
        EnricherUtils.mergeXrefs(bioactiveEntityToEnrich, bioactiveEntityToEnrich.getIdentifiers(), fetched.getIdentifiers(), true, true,
                getListener(), getListener());
    }

    @Override
    protected void processFullName(T bioactiveEntityToEnrich, T fetched) {
        if((fetched.getFullName() != null && !fetched.getFullName().equalsIgnoreCase(bioactiveEntityToEnrich.getFullName())
                || (fetched.getFullName() == null && bioactiveEntityToEnrich.getFullName() != null))){
            String oldValue = bioactiveEntityToEnrich.getFullName();
            bioactiveEntityToEnrich.setFullName(fetched.getFullName());
            if(getListener() != null)
                getListener().onFullNameUpdate(bioactiveEntityToEnrich , oldValue);
        }    }

    @Override
    protected void processInteractorType(T entityToEnrich, T fetched) throws EnricherException {
        if (!DefaultCvTermComparator.areEquals(entityToEnrich.getInteractorType(), fetched.getInteractorType())){
            CvTerm old = entityToEnrich.getInteractorType();
            entityToEnrich.setInteractorType(fetched.getInteractorType());
            if (getListener() != null){
                getListener().onInteractorTypeUpdate(entityToEnrich, old);
            }
        }
        else if (getCvTermEnricher() != null
                && entityToEnrich.getInteractorType() != fetched.getInteractorType()){
            getCvTermEnricher().enrich(entityToEnrich.getInteractorType(), fetched.getInteractorType());
        }

        if (getCvTermEnricher() != null){
            getCvTermEnricher().enrich(entityToEnrich.getInteractorType());
        }
    }

    @Override
    protected void processOrganism(T entityToEnrich, T fetched) throws EnricherException {
        if (!DefaultOrganismComparator.areEquals(entityToEnrich.getOrganism(), fetched.getOrganism())){
            Organism old = entityToEnrich.getOrganism();
            entityToEnrich.setOrganism(fetched.getOrganism());
            if (getListener() != null){
                getListener().onOrganismUpdate(entityToEnrich, old);
            }
        }
        else if (getOrganismEnricher() != null
                && entityToEnrich.getOrganism() != fetched.getOrganism()){
            getOrganismEnricher().enrich(entityToEnrich.getOrganism(), fetched.getOrganism());
        }

        if (getOrganismEnricher() != null && entityToEnrich.getOrganism() != null){
            getOrganismEnricher().enrich(entityToEnrich.getOrganism());
        }
    }

    @Override
    protected void processChecksums(T bioactiveEntityToEnrich, T fetched) throws EnricherException {
        EnricherUtils.mergeChecksums(bioactiveEntityToEnrich, bioactiveEntityToEnrich.getChecksums(), fetched.getChecksums(), true,
                getListener());
    }

    @Override
    protected void processAnnotations(T objectToEnrich, T fetchedObject){
        EnricherUtils.mergeAnnotations(objectToEnrich, objectToEnrich.getAnnotations(), fetchedObject.getAnnotations(), true,
                getListener());
    }

    @Override
    protected void processXrefs(T bioactiveEntityToEnrich, T fetched) {
        EnricherUtils.mergeXrefs(bioactiveEntityToEnrich, bioactiveEntityToEnrich.getXrefs(), fetched.getXrefs(), true, false,
                getListener(), getListener());
    }

    @Override
    public InteractorEnricherListener<T> getListener() {
        return interactorEnricher.getListener();
    }

    @Override
    public void setListener(InteractorEnricherListener<T> listener) {
        this.interactorEnricher.setListener(listener);
    }

    @Override
    public T find(T objectToEnrich) throws EnricherException {
        return interactorEnricher.find(objectToEnrich);
    }

    @Override
    protected void onEnrichedVersionNotFound(T objectToEnrich) throws EnricherException {
        interactorEnricher.onEnrichedVersionNotFound(objectToEnrich);
    }

    @Override
    protected boolean isFullEnrichment() {
        return interactorEnricher.isFullEnrichment();
    }

    @Override
    protected void onCompletedEnrichment(T objectToEnrich) {
        interactorEnricher.onCompletedEnrichment(objectToEnrich);
    }

    @Override
    protected void onInteractorCheckFailure(T objectToEnrich, T fetchedObject) {
        interactorEnricher.onInteractorCheckFailure(objectToEnrich, fetchedObject);
    }

    @Override
    protected boolean canEnrichInteractor(T entityToEnrich, T fetchedEntity) {
        return interactorEnricher.canEnrichInteractor(entityToEnrich, fetchedEntity);
    }

    protected AbstractInteractorEnricher<T> getInteractorEnricher() {
        return interactorEnricher;
    }
}
