package psidev.psi.mi.jami.enricher.impl.feature;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import psidev.psi.mi.jami.enricher.CvTermEnricher;
import psidev.psi.mi.jami.enricher.ProteinListeningFeatureEnricher;
import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.enricher.listener.feature.FeatureEnricherListener;
import psidev.psi.mi.jami.enricher.listener.EnrichmentStatus;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.model.Range;
import psidev.psi.mi.jami.utils.AnnotationUtils;
import psidev.psi.mi.jami.utils.PositionUtils;
import psidev.psi.mi.jami.utils.RangeUtils;
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
        implements ProteinListeningFeatureEnricher<F> {

    protected static final Logger log = LoggerFactory.getLogger(AbstractFeatureEnricher.class.getName());

    protected FeatureEnricherListener listener;
    protected CvTermEnricher cvTermEnricher;

    protected Collection<F> featuresToEnrich;

    /**
     * Enriches a collection of features.
     * @param featuresToEnrich   The features which are to be enriched.
     * @throws EnricherException    Thrown if problems are encountered in a fetcher
     */
    public void enrichFeatures(Collection<F> featuresToEnrich) throws EnricherException {
        for(F featureToEnrich : featuresToEnrich){
            enrichFeature(featureToEnrich);
        }
    }

    /**
     * Enrichment of a single feature.
     * @param featureToEnrich       The feature which is to be enriched.
     * @throws EnricherException    Thrown if problems are encountered in the fetcher
     */
    public void enrichFeature(F featureToEnrich) throws EnricherException {
        if(featureToEnrich == null)
            throw new IllegalArgumentException("Feature enricher was passed a null feature.");

        // == TYPE ==================================================================
        if(getCvTermEnricher() != null) {
            if(featureToEnrich.getType() != null) getCvTermEnricher().enrichCvTerm( featureToEnrich.getType() );
        }

        // == ENRICH ================================================================
        processFeature(featureToEnrich);

        if(getFeatureEnricherListener() != null)
            getFeatureEnricherListener().onEnrichmentComplete(featureToEnrich, EnrichmentStatus.SUCCESS, null);
    }

    /**
     * Processes the specific details of the feature which are not delegated to a subEnricher.
     * @param featureToEnrich       The feature being enriched.
     * @throws EnricherException    Thrown if problems are encountered in a fetcher.
     */
    protected abstract void processFeature(F featureToEnrich) throws EnricherException;

    /**
     * The way in which an invalid range in processed.
     * Adds the invalid range as an annotation to the feature.
     * @param feature   The feature being enriched.
     * @param range     The range which is invalid.
     * @param message   The reason the range is invalid.
     */
    protected void processInvalidRange(Feature feature, Range range , String message){
        Annotation annotation = AnnotationUtils.createCaution("Invalid range: " +message );

        feature.getAnnotations().add(annotation);
        if(getFeatureEnricherListener() != null)
            getFeatureEnricherListener().onAddedAnnotation(feature , annotation);
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


    public void setFeaturesToEnrich(Participant participant){

        log.trace("Setting the features");

        featuresToEnrich = participant.getFeatures();

        if(participant.getInteractor() instanceof Polymer){
            String sequence = ((Polymer)participant.getInteractor()).getSequence();


            if(sequence != null && sequence.length() > 0)
                for(Feature feature : featuresToEnrich){
                    for(Object object : feature.getRanges()) {
                        Range range = (Range)object;
                        //log.info("seq "+sequence+" , range "+range);

                        List<String> rangeValidationMsg = RangeUtils.validateRange(range, sequence);
                        //log.info("invalid message: "+rangeValidationMsg);

                        if( ! rangeValidationMsg.isEmpty()){
                            if(getFeatureEnricherListener() != null)
                                getFeatureEnricherListener().onInvalidRange(feature , range , rangeValidationMsg.toString());
                            processInvalidRange(feature , range , rangeValidationMsg.toString());
                        }
                    }
                }
            else
                for(Feature feature : featuresToEnrich){
                    for(Object object : feature.getRanges()) {
                        Range range = (Range)object;
                        String rangeValidationMsg = "Sequence is null making ranges invalid.";
                        if(getFeatureEnricherListener() != null)
                            getFeatureEnricherListener().onInvalidRange(feature , range , rangeValidationMsg);
                        processInvalidRange(feature , range , rangeValidationMsg);
                    }
                }
        }
    }

    public void onSequenceUpdate(Protein protein, String oldSequence) {
        if( oldSequence != null){
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
            }
            // shift range where protein.getSequence is new sequence and oldSequence is previous sequence
        }
    }

    public void onEnrichmentComplete(Protein protein, EnrichmentStatus status, String message) {}

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
