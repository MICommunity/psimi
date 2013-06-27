package psidev.psi.mi.jami.enricher.impl.feature;

import psidev.psi.mi.jami.enricher.CvTermEnricher;
import psidev.psi.mi.jami.enricher.FeatureEnricher;
import psidev.psi.mi.jami.enricher.impl.cvterm.MinimumCvTermUpdater;
import psidev.psi.mi.jami.model.Feature;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 19/06/13
 * Time: 11:41
 */
public class MinimumFeatureUpdater
        extends AbstractFeatureEnricher
        implements FeatureEnricher {


    @Override
    public void processFeature(Feature featureToEnrich) {

        /*if(featureFetched.getType() != null
                && ! featureFetched.getType().getShortName().equalsIgnoreCase(
                    featureToEnrich.getType().getShortName())){
            //if(listener != null) listener.on(featureToEnrich , featureToEnrich.getType());
            featureToEnrich.setType(featureFetched.getType());
        }   */
        //update feature type
        return;  //To change body of implemented methods use File | Settings | File Templates.
    }


    public CvTermEnricher getCvTermEnricher(){
        if(cvTermEnricher == null) cvTermEnricher = new MinimumCvTermUpdater();
        return cvTermEnricher;
    }
}
