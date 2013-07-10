package psidev.psi.mi.jami.enricher.impl.feature;

import psidev.psi.mi.jami.enricher.CvTermEnricher;
import psidev.psi.mi.jami.enricher.FeatureEnricher;
import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.enricher.impl.cvterm.CvTermUpdaterMinimum;
import psidev.psi.mi.jami.model.Feature;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since  19/06/13
 */
public class FeatureUpdaterMinimum<F extends Feature>
        extends AbstractFeatureEnricher <F>
        implements FeatureEnricher <F> {


    @Override
    public void processFeature(F featureToEnrich) throws EnricherException {

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
        if(cvTermEnricher == null) cvTermEnricher = new CvTermUpdaterMinimum();
        return cvTermEnricher;
    }
}
