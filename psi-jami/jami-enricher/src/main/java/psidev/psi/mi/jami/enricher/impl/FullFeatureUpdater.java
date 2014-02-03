package psidev.psi.mi.jami.enricher.impl;

import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.enricher.util.EnricherUtils;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Feature;

/**
 * Full updater for features
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 13/08/13
 */
public class FullFeatureUpdater<F extends Feature> extends FullFeatureEnricher<F> {

    private MinimalFeatureUpdater<F> minimalUpdater;

    public FullFeatureUpdater(){
        super();
        this.minimalUpdater = new MinimalFeatureUpdater<F>();
    }

    protected FullFeatureUpdater(MinimalFeatureUpdater<F> minimalUpdater){
        if (minimalUpdater == null){
            throw new IllegalArgumentException("The minimal feature updater is required");
        }
        this.minimalUpdater = minimalUpdater;
    }

    @Override
    protected void processMinimalUpdates(F featureToEnrich) throws EnricherException {
        this.minimalUpdater.processMinimalUpdates(featureToEnrich);
    }

    @Override
    protected void processInteractionDependency(F featureToEnrich, F objectSource) throws EnricherException {
        if (objectSource.getInteractionDependency() !=  featureToEnrich.getInteractionDependency()){
            CvTerm old = featureToEnrich.getInteractionDependency();
            featureToEnrich.setInteractionDependency(objectSource.getInteractionDependency());
            if(getFeatureEnricherListener() != null) {
                getFeatureEnricherListener().onInteractionDependencyUpdate(featureToEnrich, old);
            }
        }
        processInteractionDependency(featureToEnrich);
    }

    @Override
    protected void processInteractionEffect(F featureToEnrich, F objectSource) throws EnricherException {
        if (objectSource.getInteractionEffect() != featureToEnrich.getInteractionEffect()){
            CvTerm old = featureToEnrich.getInteractionEffect();
            featureToEnrich.setInteractionEffect(objectSource.getInteractionEffect());
            if(getFeatureEnricherListener() != null) {
                getFeatureEnricherListener().onInteractionEffectUpdate(featureToEnrich, old);
            }
        }
        processInteractionEffect(featureToEnrich);
    }

    @Override
    protected void processXrefs(F objectToEnrich, F cvTermFetched) {
        EnricherUtils.mergeXrefs(objectToEnrich, objectToEnrich.getXrefs(), cvTermFetched.getXrefs(), true, false,
                getFeatureEnricherListener(), getFeatureEnricherListener());
    }

    @Override
    protected void processAnnotations(F objectToEnrich, F fetchedObject){
        EnricherUtils.mergeAnnotations(objectToEnrich, objectToEnrich.getAnnotations(), fetchedObject.getAnnotations(), true,
                getFeatureEnricherListener());
    }

    @Override
    protected void processAliases(F objectToEnrich, F termFetched) {
        EnricherUtils.mergeAliases(objectToEnrich, objectToEnrich.getAliases(), termFetched.getAliases(), true, getFeatureEnricherListener());
    }
}
