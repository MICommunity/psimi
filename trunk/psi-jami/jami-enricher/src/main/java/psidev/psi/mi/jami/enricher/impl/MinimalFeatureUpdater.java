package psidev.psi.mi.jami.enricher.impl;

import psidev.psi.mi.jami.model.Feature;
import psidev.psi.mi.jami.model.Range;
import psidev.psi.mi.jami.utils.PositionUtils;

import java.util.Collection;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 13/08/13
 */
public class MinimalFeatureUpdater<F extends  Feature> extends MinimalFeatureEnricher<F> {

    @Override
    protected boolean updateRangePositions() {
        return true;
    }

    @Override
    protected void onInvalidRange(F feature, Range range, Collection<String> errorMessages) {
        if (getFeatureEnricherListener() != null){
            getFeatureEnricherListener().onRemovedRange(feature, range);
        }
        range.setPositions(PositionUtils.createUndeterminedPosition(), PositionUtils.createUndeterminedPosition());
        if (getFeatureEnricherListener() != null){
            getFeatureEnricherListener().onAddedRange(feature, range);
        }
    }

    @Override
    protected void onOutOfDateRange(F feature, Range range, Collection<String> errorMessages, String oldSequence) {
        if (getFeatureEnricherListener() != null){
            getFeatureEnricherListener().onRemovedRange(feature, range);
        }
        range.setPositions(PositionUtils.createUndeterminedPosition(), PositionUtils.createUndeterminedPosition());
        if (getFeatureEnricherListener() != null){
            getFeatureEnricherListener().onAddedRange(feature, range);
        }
    }
}
