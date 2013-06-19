package psidev.psi.mi.jami.enricher.impl.feature;

import psidev.psi.mi.jami.enricher.FeatureEnricher;
import psidev.psi.mi.jami.model.Feature;
import uk.ac.ebi.intact.commons.util.diff.Diff;
import uk.ac.ebi.intact.commons.util.diff.DiffCalculator;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 19/06/13
 * Time: 11:41
 */
public class MaximumFeatureEnricher
        extends MinimumFeatureEnricher
        implements FeatureEnricher {


    @Override
    public boolean processFeature(Feature featureToEnrich) {
        super.processFeature(featureToEnrich);

        featureToEnrich.getRanges();

        //If ranges are invalid - stop

        //else

        //

        return true;
    }
}
