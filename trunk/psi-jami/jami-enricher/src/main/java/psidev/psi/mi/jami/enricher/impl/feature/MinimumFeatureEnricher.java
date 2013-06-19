package psidev.psi.mi.jami.enricher.impl.feature;

import psidev.psi.mi.jami.enricher.FeatureEnricher;
import psidev.psi.mi.jami.model.Feature;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 19/06/13
 * Time: 11:41
 */
public class MinimumFeatureEnricher
        extends AbstractFeatureEnricher
        implements FeatureEnricher {


    @Override
    public boolean processFeature(Feature featureToEnrich) {

        //add feature type
        return false;
    }
}
