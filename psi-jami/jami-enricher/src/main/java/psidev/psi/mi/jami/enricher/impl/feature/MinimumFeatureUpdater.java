package psidev.psi.mi.jami.enricher.impl.feature;

import psidev.psi.mi.jami.enricher.FeatureEnricher;
import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.model.Feature;
import psidev.psi.mi.jami.model.Range;
import psidev.psi.mi.jami.utils.PositionUtils;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since  19/06/13
 */
public class MinimumFeatureUpdater<F extends Feature>
        extends AbstractFeatureEnricher <F>
        implements FeatureEnricher <F> {


    @Override
    public void processFeature(F featureToEnrich) throws EnricherException {
        super.processFeature(featureToEnrich);

        /*if(featureFetched.getType() != null
                && ! featureFetched.getType().getShortName().equalsIgnoreCase(
                    featureToEnrich.getType().getShortName())){
            //if(listener != null) listener.on(featureToEnrich , featureToEnrich.getType());
            featureToEnrich.setType(featureFetched.getType());
        }   */

        //update feature type
        return;  //To change body of implemented methods use File | Settings | File Templates.
    }


    protected void processInvalidRange(Feature feature, Range range , String message){
        super.processInvalidRange(feature, range , message);
        range.setPositions(PositionUtils.createUndeterminedPosition(), PositionUtils.createUndeterminedPosition());
        if( getFeatureEnricherListener() != null)
            getFeatureEnricherListener().onUpdatedRange(feature , range , "Marked invalid range as undetermined");
    }

    /*
    public CvTermEnricher getCvTermEnricher(){
        if(cvTermEnricher == null) cvTermEnricher = new MinimumCvTermUpdater();
        return cvTermEnricher;
    } */
}
