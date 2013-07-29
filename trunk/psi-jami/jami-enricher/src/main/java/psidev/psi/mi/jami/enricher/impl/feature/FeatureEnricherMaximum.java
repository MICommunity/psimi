package psidev.psi.mi.jami.enricher.impl.feature;

import psidev.psi.mi.jami.enricher.FeatureEnricher;
import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.model.Feature;

/**
 * Created with IntelliJ IDEA.
 *
 * @author  Gabriel Aldam (galdam@ebi.ac.uk)
 * @since   19/06/13
 */
public class FeatureEnricherMaximum<F extends Feature>
        extends FeatureEnricherMinimum<F>
        implements FeatureEnricher <F> {


    protected void processFeature(F featureToEnrich) throws EnricherException {
        super.processFeature(featureToEnrich);

        if(getCvTermEnricher() != null) {
            if( featureToEnrich.getInteractionDependency() != null)
                getCvTermEnricher().enrichCvTerm( featureToEnrich.getInteractionDependency() );
            if( featureToEnrich.getInteractionEffect() != null)
                getCvTermEnricher().enrichCvTerm( featureToEnrich.getInteractionEffect() );
        }
    }

    /*
    public CvTermEnricher getCvTermEnricher(){
        if(cvTermEnricher == null) cvTermEnricher = new MaximumCvTermEnricher();
        return cvTermEnricher;
    }*/

}
