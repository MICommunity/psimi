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

    public void enrichFeatures(Collection<F> featuresToEnrich) throws EnricherException {
        for(F featureToEnrich : featuresToEnrich){
            enrichFeature(featureToEnrich);
        }
    }

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

    protected void processFeature(F featureToEnrich) throws EnricherException{

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

    public CvTermEnricher getCvTermEnricher(){
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
