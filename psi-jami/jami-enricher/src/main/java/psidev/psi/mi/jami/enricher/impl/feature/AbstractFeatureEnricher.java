package psidev.psi.mi.jami.enricher.impl.feature;


import psidev.psi.mi.jami.enricher.CvTermEnricher;
import psidev.psi.mi.jami.enricher.FeatureEnricher;
import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.enricher.impl.feature.listener.FeatureEnricherListener;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Feature;
import psidev.psi.mi.jami.model.FeatureEvidence;

/**
 * Created with IntelliJ IDEA.
 *
 * @author : Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 13/06/13
 * Time: 16:54
 */
public abstract class AbstractFeatureEnricher
        implements FeatureEnricher{


    protected FeatureEnricherListener listener;
    protected CvTermEnricher cvTermEnricher;

    public void enrichFeature(Feature featureToEnrich) throws EnricherException {
        if(featureToEnrich == null) throw new IllegalArgumentException("Feature enricher was passed a null feature.");

        processFeature(featureToEnrich);
        if(featureToEnrich instanceof FeatureEvidence){
            processFeatureEvidence((FeatureEvidence)featureToEnrich);
        }

        if(listener != null) listener.onFeatureEnriched(featureToEnrich, "Success. Feature enriched.");
    }

    public void enrichFeature(Feature featureToEnrich, String sequenceOld, String sequenceNew){
    }


    public abstract void processFeature(Feature featureToEnrich);


    public void processFeatureEvidence(FeatureEvidence featureEvidenceToEnrich) throws EnricherException {
        for(CvTerm cvTerm : featureEvidenceToEnrich.getDetectionMethods()){
            if(getCvTermEnricher() != null) getCvTermEnricher().enrichCvTerm(cvTerm);
        }
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
}
