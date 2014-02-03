package psidev.psi.mi.jami.enricher.impl;

import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.enricher.util.EnricherUtils;
import psidev.psi.mi.jami.model.Feature;

/**
 * Full enricher for features
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 13/08/13
 */
public class FullFeatureEnricher<F extends Feature> extends MinimalFeatureEnricher<F> {

    public FullFeatureEnricher(){
    }

    @Override
    protected void processOtherProperties(F featureToEnrich, F objectSource) throws EnricherException {
        processInteractionDependency(featureToEnrich, objectSource);
        processInteractionEffect(featureToEnrich, objectSource);

        // process xrefs
        processXrefs(featureToEnrich, objectSource);

        // process synonyms
        processAliases(featureToEnrich, objectSource);

        // process annotations
        processAnnotations(featureToEnrich, objectSource);
    }

    @Override
    protected void processOtherProperties(F featureToEnrich) throws EnricherException {
        // process interaction dependency
        processInteractionDependency(featureToEnrich);

        // process interaction effect
        processInteractionEffect(featureToEnrich);
    }

    protected void processInteractionDependency(F featureToEnrich) throws EnricherException {
        if(getCvTermEnricher() != null && featureToEnrich.getInteractionDependency() != null) {
            getCvTermEnricher().enrich( featureToEnrich.getInteractionDependency() );
        }
    }

    protected void processInteractionDependency(F featureToEnrich, F objectSource) throws EnricherException {
        if (objectSource.getInteractionDependency() != null && featureToEnrich.getInteractionDependency() == null){
            featureToEnrich.setInteractionDependency(objectSource.getInteractionDependency());
            if(getFeatureEnricherListener() != null) {
                getFeatureEnricherListener().onInteractionDependencyUpdate(featureToEnrich, null);
            }
        }
        processInteractionDependency(featureToEnrich);
    }

    protected void processInteractionEffect(F featureToEnrich) throws EnricherException {
        if(getCvTermEnricher() != null && featureToEnrich.getInteractionEffect() != null) {
            getCvTermEnricher().enrich( featureToEnrich.getInteractionEffect() );
        }
    }

    protected void processInteractionEffect(F featureToEnrich, F objectSource) throws EnricherException {
        if (objectSource.getInteractionEffect() != null && featureToEnrich.getInteractionEffect() == null){
            featureToEnrich.setInteractionEffect(objectSource.getInteractionEffect());
            if(getFeatureEnricherListener() != null) {
                getFeatureEnricherListener().onInteractionEffectUpdate(featureToEnrich, null);
            }
        }
        processInteractionEffect(featureToEnrich);
    }

    protected void processXrefs(F objectToEnrich, F cvTermFetched) {
        EnricherUtils.mergeXrefs(objectToEnrich, objectToEnrich.getXrefs(), cvTermFetched.getXrefs(), false, false,
                getFeatureEnricherListener(), getFeatureEnricherListener());
    }

    protected void processAnnotations(F objectToEnrich, F fetchedObject){
        EnricherUtils.mergeAnnotations(objectToEnrich, objectToEnrich.getAnnotations(), fetchedObject.getAnnotations(), false,
                getFeatureEnricherListener());
    }

    protected void processAliases(F objectToEnrich, F termFetched) {
        EnricherUtils.mergeAliases(objectToEnrich, objectToEnrich.getAliases(), termFetched.getAliases(), false, getFeatureEnricherListener());
    }
}
