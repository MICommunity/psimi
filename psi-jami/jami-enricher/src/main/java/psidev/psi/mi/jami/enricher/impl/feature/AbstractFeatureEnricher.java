package psidev.psi.mi.jami.enricher.impl.feature;

import psidev.psi.mi.jami.enricher.CvTermEnricher;
import psidev.psi.mi.jami.enricher.FeatureEnricher;
import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.enricher.impl.feature.listener.FeatureEnricherListener;
import psidev.psi.mi.jami.enricher.listener.EnrichmentStatus;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.model.Range;
import psidev.psi.mi.jami.utils.PositionUtils;
import uk.ac.ebi.intact.commons.util.DiffUtils;
import uk.ac.ebi.intact.commons.util.diff.Diff;



import java.util.Collection;
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

    public void enrichFeatures(Collection<F> featuresToEnrich) throws EnricherException {
        for(F featureToEnrich : featuresToEnrich){
            enrichFeature(featureToEnrich);
        }
    }

    public void enrichFeature(F featureToEnrich) throws EnricherException {
        if(featureToEnrich == null) throw new IllegalArgumentException("Feature enricher was passed a null feature.");

        processFeature(featureToEnrich);

        if(listener != null) listener.onFeatureEnriched(featureToEnrich, EnrichmentStatus.SUCCESS, null);
    }

    protected void processFeature(F featureToEnrich) throws EnricherException{
        if(getCvTermEnricher() != null) {
            getCvTermEnricher().enrichCvTerm( featureToEnrich.getType() );
            getCvTermEnricher().enrichCvTerm(featureToEnrich.getInteractionDependency());
            getCvTermEnricher().enrichCvTerm(featureToEnrich.getInteractionEffect());
        }







        /*
        if(featureEvidenceToEnrich ==  null) {
            if(listener != null) listener.onFeatureEvidenceEnriched(featureEvidenceToEnrich,
                    "Failed. Attempted to enrich null featureEvidence");
            return false;
        }

        if(cvTermEnricher != null){
            cvTermEnricher.enrichCvTerm(featureEvidenceToEnrich.getType());
            for(CvTerm cvTerm : featureEvidenceToEnrich.getDetectionMethods()){
                cvTermEnricher.enrichCvTerm(cvTerm);
            }
        }

        if(sequenceOld == null){
            if(listener != null) listener.onFeatureEvidenceEnriched(featureEvidenceToEnrich,
                    "Failed. The original sequence is null which invalidates all features.");
            return false;
        }

        //boolean allValid = true;
        for(Range range : featureEvidenceToEnrich.getRanges()) {

            if( range.getStart().isPositionUndetermined()
                    || range.getEnd().isPositionUndetermined()){
                //Entry already considered invalid;
            }
            else if( PositionUtils.areRangePositionsValid(
                        range.getStart().getStart(), range.getEnd().getEnd())
                    && PositionUtils.areRangePositionsOutOfBounds(
                        range.getStart().getStart(), range.getEnd().getEnd(),
                        sequenceOld.length())) {
                //VALID
            }
            else {
                if(listener != null) ;//listener. note that the feature has invalid range
                range.getStart().getStatus().getAnnotations().add(
                        AnnotationUtils.createCaution(
                                "Invalid range: "+
                                range.getStart().getStart() + "," +
                                range.getEnd().getEnd()));  //todo
                //range.getStart().PositionUtils.createUndeterminedPosition());
                //allValid = false;
            }
        }
        /*if( ! allValid){

            if(listener != null) listener.onFeatureEvidenceEnriched(featureEvidenceToEnrich,
                    "Failed. Feature has invalid ranges.");
            return false;
        } */

         /*
        List<Diff> sequenceChanges  = DiffUtils.diff(sequenceOld, sequenceNew);

        if(sequenceChanges != null
                && ! sequenceChanges.isEmpty()){
            for(Range range : featureEvidenceToEnrich.getRanges()) {
                if( ! range.getStart().isPositionUndetermined()
                        && ! range.getEnd().isPositionUndetermined()){
                    // Try and remap the valid sequences.
                    String oldValue = sequenceOld.substring(
                            (int)range.getStart().getStart() , (int)range.getEnd().getEnd());

                    range.setPositions(
                            PositionUtils.createCertainPosition(
                                    DiffUtils.calculateIndexShift(sequenceChanges, (int) range.getStart().getStart())),
                            PositionUtils.createCertainPosition(
                                    DiffUtils.calculateIndexShift(sequenceChanges ,(int)range.getEnd().getEnd())));

                    String newValue = sequenceNew.substring(
                            (int) range.getStart().getStart(), (int) range.getEnd().getEnd());

                    if( ! oldValue.equalsIgnoreCase(newValue)){ //TODO
                        log.warn("Sub sequence: " + oldValue +
                                " is not equal to new sub sequence: " + newValue);
                    }
                }

            }

        }  */





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

    protected Protein oldSequenceSource;
    protected String oldSequence;
    protected Protein lastEnrichedSequence;

    public void onSequenceUpdate(Protein protein, String oldSequence) {
        this.oldSequence = oldSequence;
        this.oldSequenceSource = protein;
    }

    public void onProteinEnriched(Protein protein, EnrichmentStatus status, String message) {
        this.lastEnrichedSequence = protein;
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
