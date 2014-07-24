package psidev.psi.mi.jami.json.nary.elements;

import psidev.psi.mi.jami.json.nary.IncrementalIdGenerator;
import psidev.psi.mi.jami.model.Feature;
import psidev.psi.mi.jami.model.ModelledFeature;

import java.io.Writer;
import java.util.Map;

/**
 * Json writer for modelled features
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>18/07/14</pre>
 */

public class SimpleJsonModelledFeatureWriter extends SimpleJsonFeatureWriter<ModelledFeature>{

    public SimpleJsonModelledFeatureWriter(Writer writer, Map<Feature, Integer> processedFeatures,
                                           Map<String, Integer> processedInteractors){
        super(writer, processedFeatures, processedInteractors);
    }

    public SimpleJsonModelledFeatureWriter(Writer writer, Map<Feature, Integer> processedFeatures, Map<String, Integer> processedInteractors,
                                           IncrementalIdGenerator idGenerator) {
        super(writer, processedFeatures, processedInteractors, idGenerator);
    }
}
