package psidev.psi.mi.jami.json.nary.elements;

import psidev.psi.mi.jami.bridges.fetcher.OntologyTermFetcher;
import psidev.psi.mi.jami.json.nary.IncrementalIdGenerator;
import psidev.psi.mi.jami.model.Entity;
import psidev.psi.mi.jami.model.Feature;
import psidev.psi.mi.jami.model.ModelledParticipant;

import java.io.Writer;
import java.util.Map;

/**
 * Json writer for modelled participants
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>18/07/14</pre>
 */

public class SimpleJsonModelledParticipantWriter extends SimpleJsonParticipantWriter<ModelledParticipant>{

    public SimpleJsonModelledParticipantWriter(Writer writer, Map<Feature, Integer> processedFeatures,
                                               Map<String, String> processedInteractors, Map<Entity, Integer> processedParticipants) {
        super(writer, processedFeatures, processedInteractors, processedParticipants);
    }

    public SimpleJsonModelledParticipantWriter(Writer writer, Map<Feature, Integer> processedFeatures,
                                               Map<String, String> processedInteractors, Map<Entity, Integer> processedParticipants,
                                               IncrementalIdGenerator idGenerator, OntologyTermFetcher fetcher) {
        super(writer, processedFeatures, processedInteractors, processedParticipants, idGenerator, fetcher);
    }

    @Override
    protected void initialiseDefaultFeatureWriter() {
        super.setFeatureWriter(new SimpleJsonModelledFeatureWriter(getWriter(), getProcessedFeatures(), getProcessedInteractors(),
                getProcessedParticipants(), getIdGenerator()));
        ((SimpleJsonModelledFeatureWriter)getFeatureWriter()).setCvWriter(getCvWriter());
    }
}
