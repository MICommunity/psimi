package psidev.psi.mi.jami.enricher.impl.minimal;

import psidev.psi.mi.jami.enricher.CvTermEnricher;
import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.enricher.listener.EnrichmentStatus;
import psidev.psi.mi.jami.enricher.listener.FeatureEnricherListener;
import psidev.psi.mi.jami.model.*;

import java.util.Collection;

/**
 * Provides minimal update of feature evidence.
 *
 * - update minimal properties of feature. See MinimalFeatureUpdater
 * - update detection methods if cv term enricher is not null
 * - Ignore all other properties of a feature
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 13/08/13
 */
public class MinimalFeatureEvidenceUpdater extends MinimalFeatureEvidenceEnricher {

    private MinimalFeatureUpdater<FeatureEvidence> minimalUpdater;

    public MinimalFeatureEvidenceUpdater(){
        super();
        this.minimalUpdater = new MinimalFeatureUpdater<FeatureEvidence>();
    }

    protected MinimalFeatureEvidenceUpdater(MinimalFeatureUpdater<FeatureEvidence> minimalUpdater){
        if (minimalUpdater == null){
            throw new IllegalArgumentException("The minimal feature updater is required");
        }
        this.minimalUpdater = minimalUpdater;
    }

    @Override
    protected void processDetectionMethods(FeatureEvidence featureToEnrich, FeatureEvidence source) throws EnricherException {
        mergeDetectionMethods(featureToEnrich, featureToEnrich.getDetectionMethods(), source.getDetectionMethods(), true);
        processDetectionMethods(featureToEnrich);
    }

    @Override
    public void setFeatureEnricherListener(FeatureEnricherListener<FeatureEvidence> featureEnricherListener) {
        this.minimalUpdater.setFeatureEnricherListener(featureEnricherListener);
    }

    @Override
    public FeatureEnricherListener<FeatureEvidence> getFeatureEnricherListener() {
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
    protected boolean updateRangePositions() {
        return this.minimalUpdater.updateRangePositions();
    }

    @Override
    protected void onInvalidRange(FeatureEvidence feature, Range range, Collection<String> errorMessages) {
        this.minimalUpdater.onInvalidRange(feature, range, errorMessages);
    }

    @Override
    protected void onOutOfDateRange(FeatureEvidence feature, Range range, Collection<String> errorMessages, String oldSequence) {
        this.minimalUpdater.onOutOfDateRange(feature, range, errorMessages, oldSequence);
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
