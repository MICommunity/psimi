package psidev.psi.mi.jami.enricher.impl.minimal;

import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.enricher.util.EnricherUtils;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Feature;
import psidev.psi.mi.jami.model.Range;
import psidev.psi.mi.jami.utils.PositionUtils;
import psidev.psi.mi.jami.utils.comparator.cv.DefaultCvTermComparator;

import java.util.Collection;

/**
 * Provides minimal update of feature.
 *
 * - update shortName if different
 * - update fullName if different
 * - enrich feature type with CvTerm enricher if not null. It will override existing feature type if different using DefaultCvTermComparator
 * - enrich identifiers. Add missing identifiers (using DefaultXrefComparator) and remove identifiers not in the feature source
 * - shift ranges if the protein sequence change
 *
 * - Ignore all other properties of a feature
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

    @Override
    protected void processRanges(F objectToEnrich, F objectSource) {
        EnricherUtils.mergeRanges(objectToEnrich, objectToEnrich.getRanges(), objectSource.getRanges(), true,
                getFeatureEnricherListener());
    }

    @Override
    protected void processFeatureType(F featureToEnrich, F objectSource) throws EnricherException {
        if (!DefaultCvTermComparator.areEquals(featureToEnrich.getType(), objectSource.getType())){
            CvTerm oldType = featureToEnrich.getType();
            featureToEnrich.setType(objectSource.getType());
            if (getFeatureEnricherListener() != null){
                getFeatureEnricherListener().onTypeUpdate(featureToEnrich, oldType);
            }
        }
        else if (getCvTermEnricher() != null
                && featureToEnrich.getType() != objectSource.getType()){
            getCvTermEnricher().enrich(featureToEnrich.getType(), objectSource.getType());
        }
        processFeatureType(featureToEnrich);
    }

    @Override
    protected void processShortLabel(F objectToEnrich, F objectSource) throws EnricherException{
        if(objectSource.getShortName() != null
                && ! objectSource.getShortName().equalsIgnoreCase(objectToEnrich.getShortName())){

            String oldValue = objectToEnrich.getShortName();
            objectToEnrich.setShortName(objectSource.getShortName());
            if (getFeatureEnricherListener() != null)
                getFeatureEnricherListener().onShortNameUpdate(objectToEnrich, oldValue);
        }
    }

    @Override
    protected void processFullName(F objectToEnrich, F objectSource) throws EnricherException{
        // == Full Name ======================================================================
        if((objectSource.getFullName() != null && !objectSource.getFullName().equals(objectToEnrich.getFullName()))
                || (objectSource.getFullName() == null
                && objectToEnrich.getFullName() != null)){

            String oldValue = objectToEnrich.getFullName();
            objectToEnrich.setFullName(objectSource.getFullName());
            if (getFeatureEnricherListener() != null)
                getFeatureEnricherListener().onFullNameUpdate(objectToEnrich, oldValue);
        }
    }

    @Override
    protected void processIdentifiers(F objectToEnrich, F objectSource) throws EnricherException{
        EnricherUtils.mergeXrefs(objectToEnrich, objectToEnrich.getIdentifiers(), objectSource.getIdentifiers(), true, true,
                getFeatureEnricherListener(), getFeatureEnricherListener());
    }
}
