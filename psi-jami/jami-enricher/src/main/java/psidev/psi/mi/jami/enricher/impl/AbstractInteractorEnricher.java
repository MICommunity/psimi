package psidev.psi.mi.jami.enricher.impl;

import psidev.psi.mi.jami.bridges.fetcher.InteractorFetcher;
import psidev.psi.mi.jami.enricher.CvTermEnricher;
import psidev.psi.mi.jami.enricher.InteractorEnricher;
import psidev.psi.mi.jami.enricher.OrganismEnricher;
import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.enricher.listener.InteractorEnricherListener;
import psidev.psi.mi.jami.enricher.util.EnricherUtils;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Interactor;

/**
 * Abstract class for enricher of interactors
 *
 * Minimal enrichment:
 *
 * - enrich fullname of an interactor. If the fullname is not null in the interactor to enrich,
 * it will ignore the fullname loaded from the fetched interactor.
 * - enrich identifiers (uniprot, ensembl, etc.) of an interactor. It will use DefaultXrefComparator to compare identifiers and add missing identifiers without
 * removing any existing identifiers.
 * - enrich aliases (gene name, etc.) of an interactor. It will use DefaultAliasComparator to compare aliases and add missing aliases without
 * removing any existing aliases.
 * - enrich interactor type of an interactor if the cv term enricher is not null. If the interactor type is not null in the interactor to enrich,
 * it will ignore the interactor type loaded from the fetched interactor.
 * - enrich organism of an interactor if the organismEnricher is not null. If the organism is not null in the interactor to enrich,
 * it will ignore the organism loaded from the fetched interactor.
 *
 * Full enrichment:
 *
 * - enrich xrefs. It will use DefaultXrefComparator to compare xrefs and add missing xrefs without
 * removing any existing xrefs.
 * - enrich checksums, It will use DefaultChecksumComparator to compare checksums and add missing xrefs without
 * removing any existing checksums.
 * - enrich annotations It will use DefaultAnnotationComparator to compare annotations and add missing xrefs without
 * removing any existing annotations.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>30/09/13</pre>
 */

public abstract class AbstractInteractorEnricher<T extends Interactor> extends AbstractMIEnricher<T> implements InteractorEnricher<T>{

    private CvTermEnricher<CvTerm> cvTermEnricher = null;
    private OrganismEnricher organismEnricher = null;
    private int retryCount = 5;
    private InteractorFetcher<T> fetcher;
    private InteractorEnricherListener<T> listener = null;

    public AbstractInteractorEnricher() {
    }

    public AbstractInteractorEnricher(InteractorFetcher<T> fetcher) {
        this.fetcher = fetcher;
    }

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

    public void setCvTermEnricher(CvTermEnricher<CvTerm> cvTermEnricher){
        this.cvTermEnricher = cvTermEnricher;
    }

    public CvTermEnricher<CvTerm> getCvTermEnricher(){
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
    protected void onEnrichedVersionNotFound(T objectToEnrich) throws EnricherException{
        enrich(objectToEnrich, null);
    }

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

    protected boolean canEnrichInteractor(T objectToEnrich, T fetchedObject) {
        return true;
    }

    protected void processOtherProperties(T bioactiveEntityToEnrich, T fetched) throws EnricherException {
        // do nothing by default. Only for sequences
    }

    protected void processShortLabel(T bioactiveEntityToEnrich, T fetched) {
        // do nothing by default as shortlabel is mandatory and only the updater can override it
    }

    public void processAliases(T bioactiveEntityToEnrich, T fetched) {
        EnricherUtils.mergeAliases(bioactiveEntityToEnrich, bioactiveEntityToEnrich.getAliases(), fetched.getAliases(), false,
                getListener());
    }

    protected void processIdentifiers(T bioactiveEntityToEnrich, T fetched) {
        EnricherUtils.mergeXrefs(bioactiveEntityToEnrich, bioactiveEntityToEnrich.getIdentifiers(), fetched.getIdentifiers(), false, true,
                getListener(), getListener());
    }

    public void processFullName(T bioactiveEntityToEnrich, T fetched) {
        if(bioactiveEntityToEnrich.getFullName() == null
                && fetched.getFullName() != null){
            bioactiveEntityToEnrich.setFullName(fetched.getFullName());
            if(getListener() != null)
                getListener().onFullNameUpdate(bioactiveEntityToEnrich , null);
        }
    }

    public void processInteractorType(T entityToEnrich, T fetched) throws EnricherException {
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

    public void processOrganism(T entityToEnrich, T fetched) throws EnricherException {
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

    private void processMinimalEnrichment(T objectToEnrich, T fetchedObject) throws EnricherException {
        if (fetchedObject != null){
            // SHORT NAME is never null
            processShortLabel(objectToEnrich, fetchedObject);
            // FULL NAME
            processFullName(objectToEnrich, fetchedObject);

            // IDENTIFIERS
            processIdentifiers(objectToEnrich, fetchedObject);

            //ALIASES
            processAliases(objectToEnrich, fetchedObject);
        }

        // Interactor type
        processInteractorType(objectToEnrich, fetchedObject);

        // Organism
        processOrganism(objectToEnrich, fetchedObject);
    }
}
