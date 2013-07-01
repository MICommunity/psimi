package psidev.psi.mi.jami.enricher.impl.feature;

import psidev.psi.mi.jami.enricher.CvTermEnricher;
import psidev.psi.mi.jami.enricher.FeatureEnricher;
import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.enricher.impl.feature.listener.FeatureEnricherListener;
import psidev.psi.mi.jami.model.Feature;

/**
 * Created with IntelliJ IDEA.
 *
 * @author  Gabriel Aldam (galdam@ebi.ac.uk)
 * @since   13/06/13
 */
public abstract class AbstractFeatureEnricher <T extends Feature>
        implements FeatureEnricher<T>{


    protected FeatureEnricherListener listener;
    protected CvTermEnricher cvTermEnricher;

    public void enrichFeature(T featureToEnrich) throws EnricherException {
        if(featureToEnrich == null) throw new IllegalArgumentException("Feature enricher was passed a null feature.");

        processFeature(featureToEnrich);

        if(listener != null) listener.onFeatureEnriched(featureToEnrich, "Success. Feature enriched.");
    }

    /*public void enrichFeature(Feature featureToEnrich, String sequenceOld, String sequenceNew){
    }  */


    public abstract void processFeature(T featureToEnrich) throws EnricherException;


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
