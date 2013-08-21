package psidev.psi.mi.jami.enricher.impl.feature;

import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.model.Feature;
import psidev.psi.mi.jami.model.Range;
import psidev.psi.mi.jami.utils.PositionUtils;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 13/08/13
 */
public class BasicFeatureUpdater<F extends  Feature>
        extends BasicFeatureEnricher <F>{

    @Override
    protected void processFeature(F featureToEnrich) throws EnricherException {
        super.processFeature(featureToEnrich);
    }

    /**
     * The way in which an invalid range in processed.
     * Sets the limits on the range to undefined
     * @param feature   The feature being enriched.
     * @param range     The range which is invalid.
     * @param message   The reason the range is invalid.
     */
    @Override
    protected void processInvalidRange(Feature feature, Range range , String message){
        super.processInvalidRange(feature, range , message);
        range.setPositions(PositionUtils.createUndeterminedPosition(), PositionUtils.createUndeterminedPosition());
        if( getFeatureEnricherListener() != null)
            getFeatureEnricherListener().onUpdatedRange(feature , range , "Marked invalid range as undetermined");
    }
}
