package psidev.psi.mi.jami.enricher.impl;

import com.sun.deploy.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import psidev.psi.mi.jami.enricher.CvTermEnricher;
import psidev.psi.mi.jami.enricher.ProteinListeningFeatureEnricher;
import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.enricher.listener.EnrichmentStatus;
import psidev.psi.mi.jami.enricher.listener.FeatureEnricherListener;
import psidev.psi.mi.jami.exception.IllegalRangeException;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.model.impl.DefaultPosition;
import psidev.psi.mi.jami.utils.AnnotationUtils;
import psidev.psi.mi.jami.utils.RangeUtils;
import uk.ac.ebi.intact.commons.util.DiffUtils;
import uk.ac.ebi.intact.commons.util.diff.Diff;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 13/08/13
 */
public class MinimalFeatureEnricher<F extends Feature> implements ProteinListeningFeatureEnricher<F> {

    private static final Logger log = LoggerFactory.getLogger(MinimalFeatureEnricher.class.getName());

    private FeatureEnricherListener listener;
    private CvTermEnricher cvTermEnricher;
    private Collection<F> featuresWithRangesToUpdate;

    public MinimalFeatureEnricher(){
    }

    /**
     * Enrichment of a single feature.
     * @param featureToEnrich       The feature which is to be enriched.
     * @throws EnricherException    Thrown if problems are encountered in the fetcher
     */
    public void enrich(F featureToEnrich) throws EnricherException {
        if(featureToEnrich == null)
            throw new IllegalArgumentException("Cannot enrich a null feature.");

        // == TYPE ==================================================================
        processFeatureType(featureToEnrich);

        if(getFeatureEnricherListener() != null)
            getFeatureEnricherListener().onEnrichmentComplete(featureToEnrich, EnrichmentStatus.SUCCESS, "Feature enriched successfully.");
    }

    public void enrich(Collection<F> objects) throws EnricherException {
        if( objects == null )
            throw new IllegalArgumentException("Cannot enrich a null collection of features.");
        for(F feature : objects){
            enrich(feature);
        }
    }

    public void setFeaturesWithRangesToUpdate(Collection<F> features) {
        log.trace("Setting the features");
        if (features == null){
            this.featuresWithRangesToUpdate = Collections.EMPTY_LIST;
        }
        else{
            this.featuresWithRangesToUpdate = features;
        }
    }

    /**
     * Sets the listener of feature changes. Can be null.
     * @param featureEnricherListener   The listener of feature changes.
     */
    public void setFeatureEnricherListener(FeatureEnricherListener featureEnricherListener) {
        this.listener = featureEnricherListener;
    }

    /**
     * Retrieves the listener of feature changes.
     * May be null if changes are not being listened to.
     * @return  The current listener of feature changes.
     */
    public FeatureEnricherListener getFeatureEnricherListener() {
        return listener;
    }

    /**
     * Sets the subEnricher for CvTerms. Can be null.
     * @param cvTermEnricher    The CvTerm enricher to be used
     */
    public void setCvTermEnricher(CvTermEnricher cvTermEnricher) {
        this.cvTermEnricher = cvTermEnricher;
    }

    /**
     * Gets the subEnricher for CvTerms. Can be null.
     * @return  The CvTerm enricher which is being used.
     */
    public CvTermEnricher getCvTermEnricher() {
        return cvTermEnricher;
    }

    public void onSequenceUpdate(Protein protein, String oldSequence) {
        for (F feature : featuresWithRangesToUpdate){
            for(Object obj : feature.getRanges()){
                Range range = (Range)obj;

                // first validate with old sequence
                List<String> errorMessages = RangeUtils.validateRange(range, oldSequence);
                // No errors, can shift the ranges
                if (errorMessages.isEmpty()){
                    if (oldSequence != null && protein.getSequence() != null){
                        if (updateRangePositions()){
                            onRangeToShift(feature, range, oldSequence, protein.getSequence());
                        }
                    }
                    // check new sequence
                    else if (protein.getSequence() != null){
                        // then validate with new sequence
                        List<String> errorMessages2 = RangeUtils.validateRange(range, protein.getSequence());
                        // the range is invalid from the start
                        if (!errorMessages2.isEmpty()){
                            if (getFeatureEnricherListener() != null){
                                getFeatureEnricherListener().onEnrichmentError(feature, StringUtils.join(errorMessages, ", "), new IllegalRangeException("The range " + range.toString() + " is invalid."));
                            }
                            onOutOfDateRange(feature, range, errorMessages2, oldSequence);
                        }
                    }
                }
                else{
                    // then validate with new sequence
                    List<String> errorMessages2 = RangeUtils.validateRange(range, protein.getSequence());
                    // the range is invalid from the start
                    if (!errorMessages2.isEmpty()){
                        if (getFeatureEnricherListener() != null){
                            getFeatureEnricherListener().onEnrichmentError(feature, StringUtils.join(errorMessages2, ", "), new IllegalRangeException("The range " + range.toString() + " is invalid."));
                        }
                        onInvalidRange(feature, range, errorMessages2);
                    }
                    // the range was invalid before but is now valid so do not shift, just report the error
                    else{
                        if (getFeatureEnricherListener() != null){
                            getFeatureEnricherListener().onEnrichmentError(feature, StringUtils.join(errorMessages, ", "), new IllegalRangeException("The range " + range.toString() + " was invalid with the original sequence but it seems to be valid with the new sequence."));
                        }
                    }
                }
            }
        }
    }

    public void onUniprotKbUpdate(Protein protein, String oldUniprot) {
        // do nothing
    }

    public void onRefseqUpdate(Protein protein, String oldRefseq) {
        // do nothing
    }

    public void onGeneNameUpdate(Protein protein, String oldGeneName) {
        // do nothing
    }

    public void onRogidUpdate(Protein protein, String oldRogid) {
        // do nothing
    }

    public void onShortNameUpdate(Protein interactor, String oldShortName) {
        // do nothing
    }

    public void onFullNameUpdate(Protein interactor, String oldFullName) {
        // do nothing
    }

    public void onAddedOrganism(Protein interactor) {
        // do nothing
    }

    public void onAddedInteractorType(Protein interactor) {
        // do nothing
    }

    public void onAddedAlias(Protein o, Alias added) {
        // do nothing
    }

    public void onRemovedAlias(Protein o, Alias removed) {
        // do nothing
    }

    public void onAddedAnnotation(Protein o, Annotation added) {
        // do nothing
    }

    public void onRemovedAnnotation(Protein o, Annotation removed) {
        // do nothing
    }

    public void onAddedChecksum(Protein interactor, Checksum added) {
        // do nothing
    }

    public void onRemovedChecksum(Protein interactor, Checksum removed) {
        // do nothing
    }

    public void onAddedIdentifier(Protein o, Xref added) {
        // do nothing
    }

    public void onRemovedIdentifier(Protein o, Xref removed) {
        // do nothing
    }

    public void onAddedXref(Protein o, Xref added) {
        // do nothing
    }

    public void onRemovedXref(Protein o, Xref removed) {
        // do nothing
    }

    protected void processFeatureType(F featureToEnrich) throws EnricherException {
        if(getCvTermEnricher() != null && featureToEnrich.getType() != null) {
            getCvTermEnricher().enrich( featureToEnrich.getType() );
        }
    }

    protected void onInvalidRange(F feature, Range range, Collection<String> errorMessages){
        Annotation annotation = AnnotationUtils.createCaution("Invalid range "+range.toString()+": " + StringUtils.join(errorMessages, ", "));

        feature.getAnnotations().add(annotation);
        if(getFeatureEnricherListener() != null)
            getFeatureEnricherListener().onAddedAnnotation(feature , annotation);
    }

    protected void onOutOfDateRange(F feature, Range range, Collection<String> errorMessages, String oldSequence){
        Annotation annotation = AnnotationUtils.createCaution("Out of date range "+range.toString()+": " + StringUtils.join(errorMessages, ", "));

        feature.getAnnotations().add(annotation);
        if(getFeatureEnricherListener() != null)
            getFeatureEnricherListener().onAddedAnnotation(feature , annotation);
    }

    protected boolean updateRangePositions(){
        return false;
    }

    private void onRangeToShift(F feature, Range range, String oldSequence, String newSequence){
        String oldFeatureSeq = RangeUtils.extractRangeSequence( range, oldSequence);
        List<Diff> diffs = DiffUtils.diff(oldSequence, newSequence);

        // to know if we have shifted a position
        boolean rangeShifted = false;
        // to know if it is possible to shift the start positions of the range
        boolean canShiftFromCvFuzzyType = false;
        // to know if it is possible to shift the end positions of the range
        boolean canShiftToCvFuzzyType = false;

        // case 'from': undetermined, cannot be shifted
        // if not undetermined, we have different cases.
        if (!range.getStart().isPositionUndetermined()){
            canShiftFromCvFuzzyType = true;
        }
        // if not undetermined, we can shift the ranges.
        if (!range.getEnd().isPositionUndetermined()){
            canShiftToCvFuzzyType = true;
        }

        // If we can shift the positions, calculate the shift of each position based on the diffs between the old and the new sequences.
        // We need to apply a correction (-1/+1) because the shift calculation is index based (0 is the first position),
        // whereas the range positions are not (first position is 1)
        long shiftedFromIntervalStart=0;
        long shiftedFromIntervalEnd=0;
        long shiftedToIntervalStart=0;
        long shiftedToIntervalEnd=0;

        Position oldStart = range.getStart();
        Position oldEnd = range.getEnd();
        Position newStart = null;
        Position newEnd = null;

        // we can shift the start positions, the range is not undetermined
        if (canShiftFromCvFuzzyType){
            shiftedFromIntervalStart = calculatePositionShift(diffs, oldStart.getStart(), oldSequence);
            if (oldStart.getStart() == oldStart.getEnd()){
                shiftedFromIntervalEnd = shiftedFromIntervalStart;
            }
            else{
                shiftedFromIntervalEnd = calculatePositionShift(diffs, oldStart.getEnd(), oldSequence);
            }

            if (shiftedFromIntervalStart != oldStart.getStart() || shiftedFromIntervalEnd != oldStart.getEnd()) {
                // the shifted start position is invalid
                if (shiftedFromIntervalStart > shiftedFromIntervalEnd){
                    onOutOfDateRange(feature, range, Collections.singleton("Impossible to shift start position " + oldStart.toString()+". The new shifted positions were overlapping " + shiftedFromIntervalStart+"-"+shiftedFromIntervalEnd),oldSequence);
                    return;
                }
                newStart = new DefaultPosition(shiftedFromIntervalStart, shiftedFromIntervalEnd);
                rangeShifted = true;
            }
        }

        // we can shift the end positions, the range is not undetermined
        if (canShiftToCvFuzzyType){
            shiftedToIntervalStart = calculatePositionShift(diffs, oldEnd.getStart(), oldSequence);
            if (oldEnd.getStart() == oldEnd.getEnd()){
                shiftedToIntervalEnd = shiftedToIntervalStart;
            }
            else{
                shiftedToIntervalEnd = calculatePositionShift(diffs, oldEnd.getEnd(), oldSequence);
            }

            if (shiftedToIntervalStart != oldEnd.getStart() || shiftedToIntervalEnd != oldEnd.getEnd()) {
                // the shifted end position is invalid
                if (shiftedToIntervalStart > shiftedToIntervalEnd){
                    onOutOfDateRange(feature, range, Collections.singleton("Impossible to shift end position " + oldEnd.toString()+". The new shifted positions were overlapping " + shiftedToIntervalStart+"-"+shiftedToIntervalEnd),oldSequence);
                    return;
                }
                newEnd = new DefaultPosition(shiftedToIntervalStart, shiftedToIntervalEnd);
                rangeShifted = true;
            }
        }

        // One of the range positions has been shifted
        if (rangeShifted){
            // case we have first amino acid deleted, it is possible the feature is still conserved,
            if (shiftedFromIntervalStart == 0 && shiftedToIntervalEnd > 0){
                shiftedFromIntervalStart = shiftedToIntervalEnd - (oldEnd.getEnd() - oldStart.getStart());
                shiftedFromIntervalEnd = shiftedFromIntervalStart;

            }

            // positions are overlapping
            if (shiftedFromIntervalEnd != 0 && shiftedToIntervalStart != 0 && shiftedFromIntervalEnd > shiftedToIntervalStart){
                onOutOfDateRange(feature, range, Collections.singleton("Impossible to shift positions " + range.toString()+". The new shifted positions were overlapping " + shiftedFromIntervalEnd+"-"+shiftedFromIntervalStart),oldSequence);
            }

            // check that the new shifted range is within the new sequence and consistent
            range.setPositions(new DefaultPosition(shiftedFromIntervalStart, shiftedFromIntervalEnd), new DefaultPosition(shiftedToIntervalStart, shiftedToIntervalEnd));
            List<String> errorMessages = RangeUtils.validateRange(range, newSequence);

            // the new range is valid
            if (errorMessages.isEmpty()){
                boolean wasCTerminal = false;
                boolean wasNTerminal = false;

                // the end position was at the end of the sequence and now is not anymore
                if (oldEnd.getEnd() == oldSequence.length() && newEnd.getEnd() != newSequence.length()){
                    // the range has been shifted but the feature is not at the C-terminal position anymore
                    wasCTerminal = true;
                }
                // the start position was at the beginning of the sequence and now is not anymore
                else if (oldStart.getStart() == 1 && newStart.getStart() != 1){
                    // the range has been shifted but the feature is not at the N-terminal position anymore
                    wasNTerminal = true;
                }

                // the new full feature sequence
                String newFeatureSeq = RangeUtils.extractRangeSequence(range, newSequence);

                // the full feature sequence was and is still not null
                if (newFeatureSeq != null && oldFeatureSeq != null){
                    // check that the new feature sequence is the same
                    if (checkNewFeatureContent(range, newSequence, oldFeatureSeq, newFeatureSeq)){
                        rangeShifted = true;
                    }
                    else{
                        rangeShifted = false;
                        range.setPositions(oldStart, oldEnd);
                        onOutOfDateRange(feature, range, Collections.singleton("The new feature sequence is different from the original feature sequence."), oldSequence);
                    }
                }
                // the new full feature sequence is null but was not before shifting the ranges
                else if (newFeatureSeq == null && oldFeatureSeq != null){
                    // the new full sequence couldn't be computed, a problem occured : we can't shift the ranges
                    rangeShifted = false;
                    range.setPositions(oldStart, oldEnd);
                    onOutOfDateRange(feature, range, Collections.singleton("The new feature sequence cannot be extracted from the shifted range positions."), oldSequence);
                }
                // Either the previous feature sequence was null and is not anymore, or the previous sequence was null and is still null.
                // if it was null, we need to update anyway (maybe a problem from a previous bug when loading xml files)
                // but we check that the feature length is not affected
                else {
                    if (oldEnd.getEnd() - oldStart.getStart() == newEnd.getEnd() - newStart.getStart()
                            && oldEnd.getEnd() - oldEnd.getStart() == newEnd.getEnd() - newEnd.getStart()
                            && oldStart.getEnd()- oldStart.getStart() == newStart.getEnd() - newStart.getStart()){
                        // do nothing
                    }
                    else {
                        rangeShifted = false;
                        range.setPositions(oldStart, oldEnd);
                        onOutOfDateRange(feature, range, Collections.singleton("The new feature sequence has a different sequence length."), oldSequence);
                    }
                }

                Collection<Annotation> annotations = feature.getAnnotations();

                if (rangeShifted && wasCTerminal){
                    String cautionMessage = "The updated range " + range.toString() + " was C-terminal and is not anymore.";
                    addCautionMessageToFeature(feature, annotations, cautionMessage);
                }
                else if (rangeShifted && wasNTerminal){
                    String cautionMessage = "The updated range " + range.toString() + " was N-terminal and is not anymore.";
                    addCautionMessageToFeature(feature, annotations, cautionMessage);
                }

                // log range shifted
                if(getFeatureEnricherListener() != null){
                   getFeatureEnricherListener().onAddedRange(feature, range);
                }
            }
            // one position has been shifted but is not valid
            else {
                range.setPositions(oldStart, oldEnd);
                onOutOfDateRange(feature, range, errorMessages, oldSequence);
                // we couldn't shift the ranges properly for one reason
            }
        }
        else {
            List<String> errorMessages = RangeUtils.validateRange(range, newSequence);
            String newFeatureSeq = RangeUtils.extractRangeSequence(range, newSequence);

            if (errorMessages.isEmpty()){
                // the full feature sequence was and is still not null
                if (oldFeatureSeq != null && newFeatureSeq != null){
                    // check that the new feature sequence is the same
                    if (!checkNewFeatureContent(range, newSequence, oldFeatureSeq, newFeatureSeq)){
                        onOutOfDateRange(feature, range, Collections.singleton("The new feature sequence is different from the original feature sequence."), oldSequence);
                    }
                }
            }
            // don't shift positions but the ranges are invalid with new sequence
            else{
                onOutOfDateRange(feature, range, errorMessages, oldSequence);
            }
        }
    }

    private void addCautionMessageToFeature(F feature, Collection<Annotation> annotations, String cautionMessage) {
        boolean hasAnnotation = false;

        for (Annotation a : annotations){
            if (AnnotationUtils.doesAnnotationHaveTopic(a, Annotation.CAUTION_MI, Annotation.CAUTION)){
                if (a.getValue() != null && cautionMessage.equals(a.getValue())){
                    hasAnnotation = true;
                }
            }
        }

        if (!hasAnnotation){
            Annotation cautionRange = AnnotationUtils.createCaution(cautionMessage);
            feature.getAnnotations().add(cautionRange);

            if (getFeatureEnricherListener() != null){
                getFeatureEnricherListener().onAddedAnnotation(feature, cautionRange);
            }
        }
    }

    /**
     * Calculates the shift in position (1-based)
     * @param diffs The differences between the sequences
     * @param sequencePosition The original position in the sequence
     * @return The final position in the sequence. If it couldn't be found, returns 0.
     */
    private long calculatePositionShift(List<Diff> diffs, long sequencePosition, String oldSequence) {
        // cannot shift negative positions or positions out of the provided sequence
        if (sequencePosition <= 0 || sequencePosition > oldSequence.length()) {
            return sequencePosition;
        }

        int index = (int)sequencePosition-1; // index 0-based (sequence is 1-based)
        long shiftedIndex = (long)DiffUtils.calculateIndexShift(diffs, index);

        long shiftedPos;

        // if exists (is is not -1) return the index 1-based (position)
        // if not, return 0
        if (shiftedIndex > -1) {
            shiftedPos = shiftedIndex+1;
        } else {
            shiftedPos = 0;
        }

        return shiftedPos;
    }

    /**
     *
     * @param range : the range to update
     * @param newSequence : the new sequence of the protein
     * @param oldFeatureSequence : the old feature sequence
     * @param newFeatureSequence : the new feature sequence
     * @return true if the shifted ranges are valid and the feature sequence is conserved
     */
    private boolean checkNewFeatureContent(Range range, String newSequence, String oldFeatureSequence, String newFeatureSequence) {
        // the feature sequence is conserved, we can update the range
        if (newFeatureSequence.equals(oldFeatureSequence)){
            return true;
        }
        // the feature sequence is not conserved. We need to check if there have been some inserts at the beginning of the feature which could explain
        // this problem. For that, we check if the new feature sequence contains the old feature sequence
        else {
            int indexOfOldFeatureSequence = newFeatureSequence.indexOf(oldFeatureSequence);

            // the new feature sequence contains the old feature sequence : we can correct the positions
            if (indexOfOldFeatureSequence != -1){
                // in case of insertion at the begining of the feature sequence, we could not have shifted the range because the insertion
                long correctedPosition = range.getStart().getStart() + newFeatureSequence.indexOf(oldFeatureSequence);
                // from interval end : the distance between from interval start and from interval end should be conserved. We can determine
                // the new from interval end from that.
                long correctedFromIntervalEnd = correctedPosition + (range.getStart().getEnd() - range.getStart().getStart());
                // to interval end : we have corrected the first position of the feature, we can determine the end position by adding the length of the
                // feature sequence to the corrected first position.
                long correctedToIntervalEnd = correctedPosition + oldFeatureSequence.length() - 1;
                // to interval end : the distance between to interval start and to interval end should be conserved. We can determine
                // the new from interval end from that.
                long correctedToIntervalStart = correctedToIntervalEnd - (range.getStart().getEnd() - range.getStart().getStart());

                // check that the corrected positions are not overlapping because the start/end intervals are conserved
                if (correctedFromIntervalEnd <= correctedToIntervalStart && correctedFromIntervalEnd <= newSequence.length() && correctedToIntervalStart >= 1){
                    range.setPositions(new DefaultPosition(correctedPosition, correctedFromIntervalEnd), new DefaultPosition(correctedToIntervalStart, correctedToIntervalEnd));
                }
                // we can't correct the positions of the ranges to have the conserved feature sequence
                else {
                    // the feature sequence has been changed, we need a curator to check this one, can't shift the ranges
                    return false;
                }
            }
            // we can't correct the positions of the ranges to have the conserved feature sequence
            else {
                // the feature sequence has been changed, we need a curator to check this one, can't shift the ranges
                return false;
            }
        }
        return true;
    }
}
