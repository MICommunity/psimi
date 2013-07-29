package psidev.psi.mi.jami.enricher.impl.feature;

import psidev.psi.mi.jami.enricher.FeatureEnricher;
import psidev.psi.mi.jami.model.Feature;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since  19/06/13
 */
public class MaximumFeatureUpdater<F extends Feature>
        extends MinimumFeatureUpdater<F>
        implements FeatureEnricher <F> {
    /*
    public CvTermEnricher getCvTermEnricher(){
        if(cvTermEnricher == null) cvTermEnricher = new MaximumCvTermUpdater();
        return cvTermEnricher;
    }  */
}
