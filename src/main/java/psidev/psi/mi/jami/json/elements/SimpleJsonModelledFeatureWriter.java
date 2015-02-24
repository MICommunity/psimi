package psidev.psi.mi.jami.json.elements;

import psidev.psi.mi.jami.bridges.fetcher.OntologyTermFetcher;
import psidev.psi.mi.jami.json.IncrementalIdGenerator;
import psidev.psi.mi.jami.model.Entity;
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

    public SimpleJsonModelledFeatureWriter(Writer writer, Map<Feature, Integer> processedFeatures, Map<String, String> processedInteractors,
                                           Map<Entity, Integer> processedParticipants) {
        super(writer, processedFeatures, processedInteractors, processedParticipants);
    }

    public SimpleJsonModelledFeatureWriter(Writer writer, Map<Feature, Integer> processedFeatures, Map<String, String> processedInteractors,
                                           Map<Entity, Integer> processedParticipants, IncrementalIdGenerator idGenerator,
                                           OntologyTermFetcher fetcher) {
        super(writer, processedFeatures, processedInteractors, processedParticipants, idGenerator, fetcher);
    }
}
