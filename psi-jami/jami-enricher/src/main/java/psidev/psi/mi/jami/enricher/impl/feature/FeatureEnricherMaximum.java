package psidev.psi.mi.jami.enricher.impl.feature;

import psidev.psi.mi.jami.enricher.CvTermEnricher;
import psidev.psi.mi.jami.enricher.FeatureEnricher;
import psidev.psi.mi.jami.enricher.impl.cvterm.CvTermEnricherMaximum;
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

    public CvTermEnricher getCvTermEnricher(){
        if(cvTermEnricher == null) cvTermEnricher = new CvTermEnricherMaximum();
        return cvTermEnricher;
    }

}
