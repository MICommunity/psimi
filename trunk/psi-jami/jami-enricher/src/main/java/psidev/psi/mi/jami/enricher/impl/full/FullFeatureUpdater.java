package psidev.psi.mi.jami.enricher.impl.full;

import psidev.psi.mi.jami.enricher.CvTermEnricher;
import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.enricher.impl.minimal.MinimalFeatureUpdater;
import psidev.psi.mi.jami.enricher.listener.EnrichmentStatus;
import psidev.psi.mi.jami.enricher.listener.FeatureEnricherListener;
import psidev.psi.mi.jami.enricher.util.EnricherUtils;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.utils.comparator.cv.DefaultCvTermComparator;

/**
 * Provides full updater of feature.
 *
 * - update minimal properties of feature (see MinimalFeatureUpdater)
 * - update interaction dependency
 * - update interaction effect
 * - update xrefs
 * - update aliases
 * - update annotations
 * - update linked features
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 13/08/13
 */
public class FullFeatureUpdater<F extends Feature> extends FullFeatureEnricher<F> {

    private MinimalFeatureUpdater<F> minimalUpdater;

    public FullFeatureUpdater(){
        super();
        this.minimalUpdater = new MinimalFeatureUpdater<F>();
    }

    protected FullFeatureUpdater(MinimalFeatureUpdater<F> minimalUpdater){
        if (minimalUpdater == null){
            throw new IllegalArgumentException("The minimal feature updater is required");
        }
        this.minimalUpdater = minimalUpdater;
    }

    @Override
    protected void processLinkedFeatures(F featureToEnrich, F objectSource) throws EnricherException {
        mergeLinkedFeatures(featureToEnrich, featureToEnrich.getLinkedFeatures(), objectSource.getLinkedFeatures(), true);
    }

    @Override
    protected void processInteractionDependency(F featureToEnrich, F objectSource) throws EnricherException {
        if (!DefaultCvTermComparator.areEquals(objectSource.getInteractionDependency(), featureToEnrich.getInteractionDependency())){
            CvTerm old = featureToEnrich.getInteractionDependency();
            featureToEnrich.setInteractionDependency(objectSource.getInteractionDependency());
            if(getFeatureEnricherListener() != null) {
                getFeatureEnricherListener().onInteractionDependencyUpdate(featureToEnrich, old);
            }
        }
        else if (getCvTermEnricher() != null
                && featureToEnrich.getInteractionDependency() != objectSource.getInteractionDependency()){
            getCvTermEnricher().enrich(featureToEnrich.getInteractionDependency(), objectSource.getInteractionDependency());
        }
        processInteractionDependency(featureToEnrich);
    }

    @Override
    protected void processInteractionEffect(F featureToEnrich, F objectSource) throws EnricherException {
        if (!DefaultCvTermComparator.areEquals(objectSource.getInteractionEffect(), featureToEnrich.getInteractionEffect())){
            CvTerm old = featureToEnrich.getInteractionEffect();
            featureToEnrich.setInteractionEffect(objectSource.getInteractionEffect());
            if(getFeatureEnricherListener() != null) {
                getFeatureEnricherListener().onInteractionEffectUpdate(featureToEnrich, old);
            }
        }
        else if (getCvTermEnricher() != null
                && featureToEnrich.getInteractionEffect() != objectSource.getInteractionEffect()){
            getCvTermEnricher().enrich(featureToEnrich.getInteractionEffect(), objectSource.getInteractionEffect());
        }
        processInteractionEffect(featureToEnrich);
    }

    @Override
    protected void processXrefs(F objectToEnrich, F cvTermFetched) {
        EnricherUtils.mergeXrefs(objectToEnrich, objectToEnrich.getXrefs(), cvTermFetched.getXrefs(), true, false,
                getFeatureEnricherListener(), getFeatureEnricherListener());
    }

    @Override
    protected void processAnnotations(F objectToEnrich, F fetchedObject){
        EnricherUtils.mergeAnnotations(objectToEnrich, objectToEnrich.getAnnotations(), fetchedObject.getAnnotations(), true,
                getFeatureEnricherListener());
    }

    @Override
    protected void processAliases(F objectToEnrich, F termFetched) {
        EnricherUtils.mergeAliases(objectToEnrich, objectToEnrich.getAliases(), termFetched.getAliases(), true, getFeatureEnricherListener());
    }

    @Override
    public void setFeatureEnricherListener(FeatureEnricherListener<F> featureEnricherListener) {
        this.minimalUpdater.setFeatureEnricherListener(featureEnricherListener);
    }

    @Override
    public FeatureEnricherListener<F> getFeatureEnricherListener() {
        return this.minimalUpdater.getFeatureEnricherListener();
    }

    @Override
    public void setCvTermEnricher(CvTermEnricher cvTermEnricher) {
        this.minimalUpdater.setCvTermEnricher(cvTermEnricher);
    }

    @Override
    public CvTermEnricher getCvTermEnricher() {
        return this.minimalUpdater.getCvTermEnricher();
    }

    @Override
    public void onSequenceUpdate(Protein protein, String oldSequence) {
        this.minimalUpdater.onSequenceUpdate(protein, oldSequence);
    }

    @Override
    public void onShortNameUpdate(Protein interactor, String oldShortName) {
        this.minimalUpdater.onShortNameUpdate(interactor, oldShortName);
    }

    @Override
    public void onFullNameUpdate(Protein interactor, String oldFullName) {
        this.minimalUpdater.onFullNameUpdate(interactor, oldFullName);
    }

    @Override
    public void onOrganismUpdate(Protein interactor, Organism o) {
        this.minimalUpdater.onOrganismUpdate(interactor, o);
    }

    @Override
    public void onInteractorTypeUpdate(Protein interactor, CvTerm old) {
        this.minimalUpdater.onInteractorTypeUpdate(interactor, old);
    }

    @Override
    public void onAddedAlias(Protein o, Alias added) {
        this.minimalUpdater.onAddedAlias(o, added);
    }

    @Override
    public void onRemovedAlias(Protein o, Alias removed) {
        this.minimalUpdater.onRemovedAlias(o, removed);
    }

    @Override
    public void onAddedAnnotation(Protein o, Annotation added) {
        this.minimalUpdater.onAddedAnnotation(o, added);
    }

    @Override
    public void onRemovedAnnotation(Protein o, Annotation removed) {
        this.minimalUpdater.onRemovedAnnotation(o, removed);
    }

    @Override
    public void onAddedChecksum(Protein interactor, Checksum added) {
        this.minimalUpdater.onAddedChecksum(interactor, added);
    }

    @Override
    public void onRemovedChecksum(Protein interactor, Checksum removed) {
        this.minimalUpdater.onRemovedChecksum(interactor, removed);
    }

    @Override
    public void onAddedIdentifier(Protein o, Xref added) {
        this.minimalUpdater.onAddedIdentifier(o, added);
    }

    @Override
    public void onRemovedIdentifier(Protein o, Xref removed) {
        this.minimalUpdater.onRemovedIdentifier(o, removed);
    }

    @Override
    public void onAddedXref(Protein o, Xref added) {
        this.minimalUpdater.onAddedXref(o, added);
    }

    @Override
    public void onRemovedXref(Protein o, Xref removed) {
        this.minimalUpdater.onRemovedXref(o, removed);
    }

    @Override
    public void onEnrichmentComplete(Protein object, EnrichmentStatus status, String message) {
        this.minimalUpdater.onEnrichmentComplete(object, status, message);
    }

    @Override
    public void onEnrichmentError(Protein object, String message, Exception e) {
        this.minimalUpdater.onEnrichmentError(object, message, e);
    }
}
