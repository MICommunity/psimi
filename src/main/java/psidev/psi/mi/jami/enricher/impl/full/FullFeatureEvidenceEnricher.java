package psidev.psi.mi.jami.enricher.impl.full;

import psidev.psi.mi.jami.enricher.CvTermEnricher;
import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.enricher.impl.minimal.MinimalFeatureEvidenceEnricher;
import psidev.psi.mi.jami.enricher.listener.EnrichmentStatus;
import psidev.psi.mi.jami.enricher.listener.FeatureEnricherListener;
import psidev.psi.mi.jami.enricher.listener.FeatureEvidenceEnricherListener;
import psidev.psi.mi.jami.enricher.util.EnricherUtils;
import psidev.psi.mi.jami.model.*;

/**
 * Provides full enrichment of feature evidence.
 *
 * - enrich full properties of feature. See FullFeatureEnricher
 * - enrich detection methods if cv term enricher is not null
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 13/08/13
 */
public class FullFeatureEvidenceEnricher extends FullFeatureEnricher<FeatureEvidence> {

    private MinimalFeatureEvidenceEnricher minimalEnricher;

    public FullFeatureEvidenceEnricher(){
        super();
        this.minimalEnricher = new MinimalFeatureEvidenceEnricher();
    }

    protected FullFeatureEvidenceEnricher(MinimalFeatureEvidenceEnricher minimalEnricher){
        super();
        this.minimalEnricher = minimalEnricher != null ? minimalEnricher : new MinimalFeatureEvidenceEnricher();
    }

    @Override
    public void processMinimalUpdates(FeatureEvidence objectToEnrich, FeatureEvidence objectSource) throws EnricherException {
        this.minimalEnricher.processMinimalUpdates(objectToEnrich, objectSource);
    }

    @Override
    public void setFeatureEnricherListener(FeatureEnricherListener<FeatureEvidence> featureEnricherListener) {
        this.minimalEnricher.setFeatureEnricherListener(featureEnricherListener);
    }

    @Override
    public FeatureEnricherListener<FeatureEvidence> getFeatureEnricherListener() {
        return this.minimalEnricher.getFeatureEnricherListener();
    }

    @Override
    public void setCvTermEnricher(CvTermEnricher cvTermEnricher) {
        this.minimalEnricher.setCvTermEnricher(cvTermEnricher);
    }

    @Override
    public CvTermEnricher getCvTermEnricher() {
        return this.minimalEnricher.getCvTermEnricher();
    }

    @Override
    public void onSequenceUpdate(Protein protein, String oldSequence) {
        this.minimalEnricher.onSequenceUpdate(protein, oldSequence);
    }

    @Override
    public void onShortNameUpdate(Protein interactor, String oldShortName) {
        this.minimalEnricher.onShortNameUpdate(interactor, oldShortName);
    }

    @Override
    public void onFullNameUpdate(Protein interactor, String oldFullName) {
        this.minimalEnricher.onFullNameUpdate(interactor, oldFullName);
    }

    @Override
    public void onOrganismUpdate(Protein interactor, Organism o) {
        this.minimalEnricher.onOrganismUpdate(interactor, o);
    }

    @Override
    public void onInteractorTypeUpdate(Protein interactor, CvTerm old) {
        this.minimalEnricher.onInteractorTypeUpdate(interactor, old);
    }

    @Override
    public void onAddedAlias(Protein o, Alias added) {
        this.minimalEnricher.onAddedAlias(o, added);
    }

    @Override
    public void onRemovedAlias(Protein o, Alias removed) {
        this.minimalEnricher.onRemovedAlias(o, removed);
    }

    @Override
    public void onAddedAnnotation(Protein o, Annotation added) {
        this.minimalEnricher.onAddedAnnotation(o, added);
    }

    @Override
    public void onRemovedAnnotation(Protein o, Annotation removed) {
        this.minimalEnricher.onRemovedAnnotation(o, removed);
    }

    @Override
    public void onAddedChecksum(Protein interactor, Checksum added) {
        this.minimalEnricher.onAddedChecksum(interactor, added);
    }

    @Override
    public void onRemovedChecksum(Protein interactor, Checksum removed) {
        this.minimalEnricher.onRemovedChecksum(interactor, removed);
    }

    @Override
    public void onAddedIdentifier(Protein o, Xref added) {
        this.minimalEnricher.onAddedIdentifier(o, added);
    }

    @Override
    public void onRemovedIdentifier(Protein o, Xref removed) {
        this.minimalEnricher.onRemovedIdentifier(o, removed);
    }

    @Override
    public void onAddedXref(Protein o, Xref added) {
        this.minimalEnricher.onAddedXref(o, added);
    }

    @Override
    public void onRemovedXref(Protein o, Xref removed) {
        this.minimalEnricher.onRemovedXref(o, removed);
    }

    @Override
    public void onEnrichmentComplete(Protein object, EnrichmentStatus status, String message) {
        this.minimalEnricher.onEnrichmentComplete(object, status, message);
    }

    @Override
    public void onEnrichmentError(Protein object, String message, Exception e) {
        this.minimalEnricher.onEnrichmentError(object, message, e);
    }

    @Override
    protected void processOtherProperties(FeatureEvidence featureToEnrich, FeatureEvidence objectSource) throws EnricherException {
        super.processOtherProperties(featureToEnrich, objectSource);

        // process parameters
        processParameters(featureToEnrich, objectSource);
    }

    protected void processParameters(FeatureEvidence featureToEnrich, FeatureEvidence objectSource) throws EnricherException{
        EnricherUtils.mergeParameters(featureToEnrich, objectSource.getParameters(), objectSource.getParameters(), false,
                getFeatureEnricherListener() instanceof FeatureEvidenceEnricherListener ? (psidev.psi.mi.jami.listener.ParametersChangeListener<FeatureEvidence>)getFeatureEnricherListener() : null);
    }

    protected MinimalFeatureEvidenceEnricher getMinimalEnricher() {
        return minimalEnricher;
    }
}
