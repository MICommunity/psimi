package psidev.psi.mi.jami.enricher.impl.feature;

import psidev.psi.mi.jami.enricher.CvTermEnricher;
import psidev.psi.mi.jami.enricher.FeatureEnricher;
import psidev.psi.mi.jami.enricher.ProteinListeningFeatureEnricher;
import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.enricher.impl.feature.listener.FeatureEnricherListener;
import psidev.psi.mi.jami.enricher.impl.protein.listener.ProteinEnricherListener;
import psidev.psi.mi.jami.enricher.listener.EnrichmentStatus;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.model.Range;
import psidev.psi.mi.jami.utils.AnnotationUtils;
import psidev.psi.mi.jami.utils.PositionUtils;
import psidev.psi.mi.jami.utils.RangeUtils;
import uk.ac.ebi.intact.commons.util.DiffUtils;
import uk.ac.ebi.intact.commons.util.diff.Diff;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author  Gabriel Aldam (galdam@ebi.ac.uk)
 * @since   13/06/13
 */
public abstract class AbstractFeatureEnricher <F extends Feature>
        implements ProteinListeningFeatureEnricher<F> {

    protected FeatureEnricherListener listener;
    protected CvTermEnricher cvTermEnricher;

    // protected Polymer oldSequencePolymer;
    // protected String oldSequence;
    // protected Polymer lastEnrichedPolymer;
    Collection<F> featuresToEnrich;

    public void enrichFeatures(Collection<F> featuresToEnrich) throws EnricherException {
        for(F featureToEnrich : featuresToEnrich){
            enrichFeature(featureToEnrich);
        }
    }

    public void enrichFeature(F featureToEnrich) throws EnricherException {
        if(featureToEnrich == null) throw new IllegalArgumentException("Feature enricher was passed a null feature.");

        processFeature(featureToEnrich);
    }

    protected void processFeature(F featureToEnrich)
            throws EnricherException{

        if(getCvTermEnricher() != null) {
            if(featureToEnrich.getType() != null) getCvTermEnricher().enrichCvTerm( featureToEnrich.getType() );
        }


        if(listener != null) listener.onFeatureEnriched(featureToEnrich, EnrichmentStatus.SUCCESS, null);

    }

    protected void processInvalidRange(Feature feature, Range range , String message){
        Annotation annotation = AnnotationUtils.createCaution("Invalid range: " +message );

        feature.getAnnotations().add(annotation);
        if(getFeatureEnricherListener() != null)
            getFeatureEnricherListener().onAddedAnnotation(feature , annotation);
    }

    public void setFeatureEnricherListener(FeatureEnricherListener featureEnricherListener) {
        this.listener = featureEnricherListener;
    }

    public FeatureEnricherListener getFeatureEnricherListener() {
        return listener;
    }

    public void setCvTermEnricher(CvTermEnricher cvTermEnricher){
        this.cvTermEnricher = cvTermEnricher;
    }

    public void setFeaturesToEnrich(Participant participant){
        featuresToEnrich = participant.getFeatures();

        if(participant.getInteractor() instanceof Polymer){
            String sequence = ((Polymer)participant.getInteractor()).getSequence();

            for(Feature feature : featuresToEnrich){
                for(Object object : feature.getRanges()) {
                    Range range = (Range)object;

                    List<String> rangeValidationMsg = RangeUtils.validateRange(range, sequence);

                    if( ! rangeValidationMsg.isEmpty()){
                        if(getFeatureEnricherListener() != null)
                            getFeatureEnricherListener().onInvalidRange(feature , range , rangeValidationMsg.toString());
                        processInvalidRange(feature , range , rangeValidationMsg.toString());
                    }
                }
            }
        }

    }

    public void onSequenceUpdate(Protein protein, String oldSequence) {

        List<Diff> sequenceChanges;
        sequenceChanges  = DiffUtils.diff(oldSequence, protein.getSequence());

        for (F feature : featuresToEnrich){
            for(Object obj : feature.getRanges()){
                Range range = (Range)obj;
                String oldFeatureSeq =
                        RangeUtils.extractRangeSequence( range, oldSequence);
                String newFeatureSeq =
                        RangeUtils.extractRangeSequence( range, protein.getSequence());

                if(oldFeatureSeq.equals(newFeatureSeq)){
                    range.setPositions(
                            PositionUtils.createCertainPosition(
                                    DiffUtils.calculateIndexShift(sequenceChanges, (int) range.getStart().getStart())),
                            PositionUtils.createCertainPosition(
                                    DiffUtils.calculateIndexShift(sequenceChanges ,(int)range.getEnd().getEnd())));
                    if(getFeatureEnricherListener() != null)
                        getFeatureEnricherListener().onUpdatedRange(feature , range , "Shifted range to match sequence update");
                }
                else {
                    String failMessage = "New sequence invalidates feature range";
                    if(getFeatureEnricherListener() != null)
                        getFeatureEnricherListener().onInvalidRange(feature , range , failMessage);
                    processInvalidRange(feature, range , failMessage);
                }
            }

            // shift range where protein.getSequence is new sequence and oldSequence is previous sequence
        }
    }

    public void onProteinEnriched(Protein protein, EnrichmentStatus status, String message) {}

    public void onProteinRemapped(Protein protein, String oldUniprot) {}
    public void onUniprotKbUpdate(Protein protein, String oldUniprot)  {}
    public void onRefseqUpdate(Protein protein, String oldRefseq) {}
    public void onGeneNameUpdate(Protein protein, String oldGeneName) {}
    public void onRogidUpdate(Protein protein, String oldRogid) {}
    public void onShortNameUpdate(Protein protein, String oldShortName) {}
    public void onFullNameUpdate(Protein protein, String oldFullName) {}
    public void onAddedInteractorType(Protein protein) {}
    public void onAddedOrganism(Protein protein)  {}
    public void onAddedIdentifier(Protein protein, Xref added)  {}
    public void onRemovedIdentifier(Protein protein, Xref removed) {}
    public void onAddedXref(Protein protein, Xref added) {}
    public void onRemovedXref(Protein protein, Xref removed) {}
    public void onAddedAlias(Protein protein, Alias added)  {}
    public void onRemovedAlias(Protein protein, Alias removed) {}
    public void onAddedChecksum(Protein protein, Checksum added) {}
    public void onRemovedChecksum(Protein protein, Checksum removed) {}
}
