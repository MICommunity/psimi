package psidev.psi.mi.jami.enricher.impl;

import psidev.psi.mi.jami.bridges.fetcher.InteractorFetcher;
import psidev.psi.mi.jami.enricher.CvTermEnricher;
import psidev.psi.mi.jami.enricher.InteractorEnricher;
import psidev.psi.mi.jami.enricher.OrganismEnricher;
import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.enricher.listener.InteractorEnricherListener;
import psidev.psi.mi.jami.enricher.util.EnricherUtils;
import psidev.psi.mi.jami.model.Interactor;

/**
 * Minimum enricher for interactors
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>30/09/13</pre>
 */

public abstract class AbstractInteractorEnricher<T extends Interactor> extends AbstractMIEnricher<T> implements InteractorEnricher<T>{

    private CvTermEnricher cvTermEnricher = null;
    private OrganismEnricher organismEnricher = null;
    private int retryCount = 5;
    private InteractorFetcher<T> fetcher;
    private InteractorEnricherListener<T> listener = null;

    public int getRetryCount() {
        return retryCount;
    }

    public void setRetryCount(int retryCount) {
        this.retryCount = retryCount;
    }

    public OrganismEnricher getOrganismEnricher() {
        return organismEnricher;
    }

    public void setOrganismEnricher(OrganismEnricher organismEnricher) {
        this.organismEnricher = organismEnricher;
    }

    public void setCvTermEnricher(CvTermEnricher cvTermEnricher){
        this.cvTermEnricher = cvTermEnricher;
    }

    public CvTermEnricher getCvTermEnricher(){
        return cvTermEnricher;
    }

    public InteractorEnricherListener<T> getListener(){
        return this.listener;
    }

    public void setListener(InteractorEnricherListener<T> listener){
        this.listener = listener;
    }

    @Override
    public abstract T find(T objectToEnrich) throws EnricherException;

    @Override
    protected abstract void onEnrichedVersionNotFound(T objectToEnrich) throws EnricherException;

    @Override
    public void enrich(T objectToEnrich, T fetchedObject) throws EnricherException {
        if (canEnrichInteractor(objectToEnrich, fetchedObject)){
            processMinimalEnrichment(objectToEnrich, fetchedObject);

            if (isFullEnrichment()){
                // Xrefs
                processXrefs(objectToEnrich, fetchedObject);

                // Checksums
                processChecksums(objectToEnrich, fetchedObject);

                // annotations
                processAnnotations(objectToEnrich, fetchedObject);

                // other properties
                processOtherProperties(objectToEnrich, fetchedObject);
            }

            onCompletedEnrichment(objectToEnrich);
        }
        // fails to enrich
        else{
            onInteractorCheckFailure(objectToEnrich, fetchedObject);
        }
    }

    protected void processAnnotations(T objectToEnrich, T fetchedObject){
        EnricherUtils.mergeAnnotations(objectToEnrich, objectToEnrich.getAnnotations(), fetchedObject.getAnnotations(), false,
                getListener());
    }

    public InteractorFetcher<T> getInteractorFetcher() {
        return this.fetcher;
    }

    protected abstract boolean isFullEnrichment();

    protected abstract void onCompletedEnrichment(T objectToEnrich);

    protected abstract void onInteractorCheckFailure(T objectToEnrich, T fetchedObject);

    protected boolean canEnrichInteractor(T objectToEnrich, T fetchedObject){
        return true;
    }

    protected void processOtherProperties(T bioactiveEntityToEnrich, T fetched) throws EnricherException {
        // do nothing by default. Only for sequences
    }

    protected void processShortLabel(T bioactiveEntityToEnrich, T fetched) {
        // do nothing by default as shortlabel is mandatory and only the updater can override it
    }

    protected void processAliases(T bioactiveEntityToEnrich, T fetched) {
        EnricherUtils.mergeAliases(bioactiveEntityToEnrich, bioactiveEntityToEnrich.getAliases(), fetched.getAliases(), false,
                getListener());
    }

    protected void processIdentifiers(T bioactiveEntityToEnrich, T fetched) {
        EnricherUtils.mergeXrefs(bioactiveEntityToEnrich, bioactiveEntityToEnrich.getIdentifiers(), fetched.getIdentifiers(), false, true,
                getListener(), getListener());
    }

    protected void processFullName(T bioactiveEntityToEnrich, T fetched) {
        if(bioactiveEntityToEnrich.getFullName() == null
                && fetched.getFullName() != null){
            bioactiveEntityToEnrich.setFullName(fetched.getFullName());
            if(getListener() != null)
                getListener().onFullNameUpdate(bioactiveEntityToEnrich , null);
        }
    }

    protected void processInteractorType(T entityToEnrich, T fetched) throws EnricherException {
        if (fetched != null && entityToEnrich.getInteractorType() == null && fetched.getInteractorType() != null){
            entityToEnrich.setInteractorType(fetched.getInteractorType());
            if (getListener() != null){
                getListener().onInteractorTypeUpdate(entityToEnrich, null);
            }
        }
        if (cvTermEnricher != null && entityToEnrich.getInteractorType() != null){
            cvTermEnricher.enrich(entityToEnrich.getInteractorType());
        }
    }

    protected void processOrganism(T entityToEnrich, T fetched) throws EnricherException {
        if (fetched != null && entityToEnrich.getOrganism() == null && fetched.getOrganism() != null){
            entityToEnrich.setOrganism(fetched.getOrganism());
            if (getListener() != null){
                getListener().onOrganismUpdate(entityToEnrich, null);
            }
        }

        if (organismEnricher != null && entityToEnrich.getOrganism() != null){
            organismEnricher.enrich(entityToEnrich.getOrganism());
        }
    }

    protected void processChecksums(T bioactiveEntityToEnrich, T fetched) throws EnricherException {
        EnricherUtils.mergeChecksums(bioactiveEntityToEnrich, bioactiveEntityToEnrich.getChecksums(), fetched.getChecksums(), false,
                getListener());
    }

    protected void processXrefs(T bioactiveEntityToEnrich, T fetched) {
        EnricherUtils.mergeXrefs(bioactiveEntityToEnrich, bioactiveEntityToEnrich.getXrefs(), fetched.getXrefs(), false, false,
                getListener(), getListener());
    }

    protected void setFetcher(InteractorFetcher<T> fetcher) {
        this.fetcher = fetcher;
    }

    private void processMinimalEnrichment(T objectToEnrich, T fetchedObject) throws EnricherException {
        // SHORT NAME is never null
        processShortLabel(objectToEnrich, fetchedObject);

        // Interactor type
        processInteractorType(objectToEnrich, fetchedObject);

        // Organism
        processOrganism(objectToEnrich, fetchedObject);

        // FULL NAME
        processFullName(objectToEnrich, fetchedObject);

        // IDENTIFIERS
        processIdentifiers(objectToEnrich, fetchedObject);

        //ALIASES
        processAliases(objectToEnrich, fetchedObject);
    }
}
