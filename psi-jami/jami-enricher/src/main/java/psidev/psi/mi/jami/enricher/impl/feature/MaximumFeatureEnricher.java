package psidev.psi.mi.jami.enricher.impl.feature;

import psidev.psi.mi.jami.enricher.FeatureEnricher;
import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.enricher.listener.EnrichmentStatus;
import psidev.psi.mi.jami.model.Feature;
import psidev.psi.mi.jami.model.Protein;

/**
 * Created with IntelliJ IDEA.
 *
 * @author  Gabriel Aldam (galdam@ebi.ac.uk)
 * @since   19/06/13
 */
public class MaximumFeatureEnricher<F extends Feature>
        extends MinimumFeatureEnricher<F>
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
