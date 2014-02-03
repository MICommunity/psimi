package psidev.psi.mi.jami.enricher.impl;

import org.apache.commons.collections.map.IdentityMap;
import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.enricher.util.EnricherUtils;
import psidev.psi.mi.jami.model.Feature;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

/**
 * Full enricher for features
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 13/08/13
 */
public class FullFeatureEnricher<F extends Feature> extends MinimalFeatureEnricher<F> {
    private Map<F, F> processedFeatures;

    public FullFeatureEnricher(){
        this.processedFeatures = new IdentityMap();
    }

    @Override
    public void enrich(F objectToEnrich) throws EnricherException {
        this.processedFeatures.clear();
        this.processedFeatures.put(objectToEnrich, objectToEnrich);
        super.enrich(objectToEnrich);
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

        // process linked features
        processLinkedFeatures(featureToEnrich, objectSource);
    }

    protected void processLinkedFeatures(F featureToEnrich, F objectSource) throws EnricherException {
        mergeLinkedFeatures(featureToEnrich, (Collection<F>)featureToEnrich.getLinkedFeatures(), (Collection<F>)objectSource.getLinkedFeatures(), false);
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

    protected void mergeLinkedFeatures(F objectToEnrich, Collection<F> linkedFeaturesToEnrich, Collection<F> fetchedFeatures, boolean remove) throws EnricherException {

        Iterator<F> featureIterator = linkedFeaturesToEnrich.iterator();
        while(featureIterator.hasNext()){
            F feature = featureIterator.next();
            boolean containsFeature = false;
            for (F feature2 : fetchedFeatures){
                if (feature == feature2){
                    containsFeature = true;
                    // enrich terms that are here
                    if (!processedFeatures.containsKey(feature)){
                        processedFeatures.put(feature, feature);
                        enrich(feature, feature2);
                    }
                    break;
                }
            }
            // remove term not in second list
            if (remove && !containsFeature){
                featureIterator.remove();
                if (getFeatureEnricherListener() != null){
                    getFeatureEnricherListener().onRemovedLinkedFeature(objectToEnrich, feature);
                }
            }
        }

        // add terms from fetchedTerms that are not in toEnrichTerm
        featureIterator = fetchedFeatures.iterator();
        while(featureIterator.hasNext()){
            F feature = featureIterator.next();
            boolean containsFeature = false;
            for (F feature2 : linkedFeaturesToEnrich){
                // identical terms
                if (feature == feature2){
                    containsFeature = true;
                    break;
                }
            }
            // add missing xref not in second list
            if (!containsFeature){
                linkedFeaturesToEnrich.add(feature);
                if (getFeatureEnricherListener() != null){
                    getFeatureEnricherListener().onAddedLinkedFeature(objectToEnrich, feature);
                }
            }
        }
    }
}
