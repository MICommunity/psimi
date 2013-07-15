package psidev.psi.mi.jami.enricher.impl.feature;

import psidev.psi.mi.jami.enricher.CvTermEnricher;
import psidev.psi.mi.jami.enricher.FeatureEnricher;
import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.enricher.impl.feature.listener.FeatureEnricherListener;
import psidev.psi.mi.jami.enricher.listener.EnrichmentStatus;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.model.Range;
import psidev.psi.mi.jami.utils.AnnotationUtils;
import psidev.psi.mi.jami.utils.PositionUtils;
import uk.ac.ebi.intact.commons.util.DiffUtils;
import uk.ac.ebi.intact.commons.util.diff.Diff;


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
        implements FeatureEnricher<F>{

    protected FeatureEnricherListener listener;
    protected CvTermEnricher cvTermEnricher;

    protected Polymer oldSequencePolymer;
    protected String oldSequence;
    protected Polymer lastEnrichedPolymer;


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
            getCvTermEnricher().enrichCvTerm( featureToEnrich.getType() );
            getCvTermEnricher().enrichCvTerm( featureToEnrich.getInteractionDependency() );
            getCvTermEnricher().enrichCvTerm( featureToEnrich.getInteractionEffect() );
        }

        // The last enriched polymer is participant of this feature
        if(featureToEnrich.getParticipant() == lastEnrichedPolymer){
            String firstSequence = null;
            String secondSequence = null;
            List<Diff> sequenceChanges = Collections.EMPTY_LIST;

            // The sequence was changed - set first and second sequence
            if(featureToEnrich.getParticipant() == oldSequencePolymer){
                if( oldSequence == null || ! oldSequence.equals("") ){
                    if(listener != null) listener.onFeatureEnriched(featureToEnrich, EnrichmentStatus.FAILED ,
                            "The original sequence is null which invalidates all features.");
                    return; //TODO
                } else {
                    firstSequence = oldSequence;
                    // Second sequence is never null/empty
                    secondSequence = lastEnrichedPolymer.getSequence();
                    // Find all the differences between the sequences
                    sequenceChanges  = DiffUtils.diff(firstSequence, secondSequence);
                }
            }
            // The sequence had not changed, set only the second sequence
            else {
                if( lastEnrichedPolymer.getSequence() == null || ! lastEnrichedPolymer.getSequence().equals("") ){
                    if(listener != null) listener.onFeatureEnriched(featureToEnrich, EnrichmentStatus.FAILED ,
                            "The original sequence is null which invalidates all features.");
                    return; //TODO
                } else {
                    firstSequence = lastEnrichedPolymer.getSequence();
                }
            }


            for(Object object : featureToEnrich.getRanges()) {
                Range range = (Range)object;

                // If the start and end are undetermined and invalid
                if( range.getStart().isPositionUndetermined()
                        || range.getEnd().isPositionUndetermined()
                        || ! PositionUtils.areRangePositionsValid( range.getStart().getStart(), range.getEnd().getEnd())
                        || PositionUtils.areRangePositionsOutOfBounds( range.getStart().getStart(), range.getEnd().getEnd(),firstSequence.length())) {

                    // if(listener != null) ;//listener. note that the feature has invalid range
                    // range.getStart().getStatus().getAnnotations().add(
                    featureToEnrich.getAnnotations().add(
                            AnnotationUtils.createCaution("Invalid range: " +
                                            range.getStart().getStart() + "," +
                                            range.getEnd().getEnd()));  //todo
                    // range.getStart().PositionUtils.createUndeterminedPosition());
                }
            }


            // Run an update on all the ranges if the sequence has changed
            if(secondSequence != null && ! sequenceChanges.isEmpty()){
                for(Object object : featureToEnrich.getRanges()) {
                    Range range = (Range)object;

                    // Try and remap the valid sequences.
                    String oldValue = firstSequence.substring(
                            (int)range.getStart().getStart() , (int)range.getEnd().getEnd());

                    range.setPositions(
                            PositionUtils.createCertainPosition(
                                    DiffUtils.calculateIndexShift(sequenceChanges, (int) range.getStart().getStart())),
                            PositionUtils.createCertainPosition(
                                    DiffUtils.calculateIndexShift(sequenceChanges ,(int)range.getEnd().getEnd())));

                    String newValue = secondSequence.substring((int) range.getStart().getStart(), (int) range.getEnd().getEnd());

                    if( ! oldValue.equalsIgnoreCase(newValue)){ //TODO
                        //log.warn("Sub sequence: " + oldValue +
                        //        " is not equal to new sub sequence: " + newValue);
                    }


                }
            }

        }

        if(listener != null) listener.onFeatureEnriched(featureToEnrich, EnrichmentStatus.SUCCESS, null);

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

    public void onSequenceUpdate(Protein protein, String oldSequence) {
        this.oldSequence = oldSequence;
        this.oldSequencePolymer = protein;
    }

    public void onProteinEnriched(Protein protein, EnrichmentStatus status, String message) {
        this.lastEnrichedPolymer = protein;
    }

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
