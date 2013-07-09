package psidev.psi.mi.jami.enricher.impl.feature;

import psidev.psi.mi.jami.enricher.CvTermEnricher;
import psidev.psi.mi.jami.enricher.FeatureEnricher;
import psidev.psi.mi.jami.enricher.impl.cvterm.MaximumCvTermEnricher;
import psidev.psi.mi.jami.model.Feature;
import psidev.psi.mi.jami.model.FeatureEvidence;
import uk.ac.ebi.intact.commons.util.diff.Diff;
import uk.ac.ebi.intact.commons.util.diff.DiffCalculator;

/**
 * Created with IntelliJ IDEA.
 *
 * @author  Gabriel Aldam (galdam@ebi.ac.uk)
 * @since   19/06/13
 */
public class MaximumFeatureEnricher <F extends Feature>
        extends MinimumFeatureEnricher <F>
        implements FeatureEnricher <F> {


    @Override
    public void processFeature(F featureToEnrich) {
        super.processFeature(featureToEnrich);

        featureToEnrich.getRanges();

        //If ranges are invalid - stop

        //else

        //

        return;
    }


    public CvTermEnricher getCvTermEnricher(){
        if(cvTermEnricher == null) cvTermEnricher = new MaximumCvTermEnricher();
        return cvTermEnricher;
    }

}
