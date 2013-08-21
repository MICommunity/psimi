package psidev.psi.mi.jami.enricher.impl.feature;

import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.model.Annotation;
import psidev.psi.mi.jami.model.Feature;
import psidev.psi.mi.jami.model.Range;
import psidev.psi.mi.jami.utils.AnnotationUtils;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 13/08/13
 */
public class BasicFeatureEnricher<F extends Feature>
        extends AbstractFeatureEnricher<F>{

    @Override
    protected void processFeature(F featureToEnrich) throws EnricherException {

    }

    @Override
    protected void processInvalidRange(Feature feature, Range range , String message){
        Annotation annotation = AnnotationUtils.createCaution("Invalid range: " + message);

        feature.getAnnotations().add(annotation);
        if(getFeatureEnricherListener() != null)
            getFeatureEnricherListener().onAddedAnnotation(feature , annotation);
    }
}
