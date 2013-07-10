package psidev.psi.mi.jami.enricher.impl.feature;

import psidev.psi.mi.jami.enricher.CvTermEnricher;
import psidev.psi.mi.jami.enricher.FeatureEnricher;
import psidev.psi.mi.jami.enricher.impl.cvterm.CvTermEnricherMinimum;
import psidev.psi.mi.jami.model.Feature;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since  19/06/13
 */
public class FeatureEnricherMinimum<F extends Feature>
        extends AbstractFeatureEnricher <F>
        implements FeatureEnricher <F> {


    @Override
    public void processFeature(F featureToEnrich) {

        //add feature type
        return;
    }

    public CvTermEnricher getCvTermEnricher(){
        if(cvTermEnricher == null) cvTermEnricher = new CvTermEnricherMinimum();
        return cvTermEnricher;
    }
}
