package psidev.psi.mi.jami.enricher.impl.feature;

import psidev.psi.mi.jami.enricher.CvTermEnricher;
import psidev.psi.mi.jami.enricher.FeatureEnricher;
import psidev.psi.mi.jami.enricher.impl.cvterm.CvTermUpdaterMaximum;
import psidev.psi.mi.jami.model.Feature;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since  19/06/13
 */
public class FeatureUpdaterMaximum<F extends Feature>
        extends FeatureUpdaterMinimum<F>
        implements FeatureEnricher <F> {
    /*
    public CvTermEnricher getCvTermEnricher(){
        if(cvTermEnricher == null) cvTermEnricher = new CvTermUpdaterMaximum();
        return cvTermEnricher;
    }  */
}
