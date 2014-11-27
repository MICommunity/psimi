package psidev.psi.mi.jami.json.binary;

import psidev.psi.mi.jami.binary.ModelledBinaryInteraction;
import psidev.psi.mi.jami.bridges.fetcher.OntologyTermFetcher;
import psidev.psi.mi.jami.json.IncrementalIdGenerator;
import psidev.psi.mi.jami.json.binary.elements.SimpleJsonModelledBinaryInteractionWriter;
import psidev.psi.mi.jami.model.Entity;
import psidev.psi.mi.jami.model.Feature;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.util.Map;

/**
 * JSON writer for ModelledInteractions
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>03/07/13</pre>
 */

public class MIJsonModelledBinaryWriter extends AbstractMIJsonBinaryWriter<ModelledBinaryInteraction> {

    public MIJsonModelledBinaryWriter() {
        super();
    }

    public MIJsonModelledBinaryWriter(File file, OntologyTermFetcher fetcher) throws IOException {
        super(file, fetcher);
    }

    public MIJsonModelledBinaryWriter(OutputStream output, OntologyTermFetcher fetcher) {
        super(output, fetcher);
    }

    public MIJsonModelledBinaryWriter(Writer writer, OntologyTermFetcher fetcher) {
        super(writer, fetcher);
    }

    public MIJsonModelledBinaryWriter(Writer writer, OntologyTermFetcher fetcher, Map<String, String> processedInteractors, Map<Feature, Integer> processedFeatures, Map<Entity, Integer> processedParticipants, IncrementalIdGenerator idGenerator) {
        super(writer, fetcher, processedInteractors, processedFeatures, processedParticipants, idGenerator);
    }

    public MIJsonModelledBinaryWriter(Map<String, String> processedInteractors, Map<Feature, Integer> processedFeatures, Map<Entity, Integer> processedParticipants, IncrementalIdGenerator idGenerator) {
        super(processedInteractors, processedFeatures, processedParticipants, idGenerator);
    }

    @Override
    protected void initExpansionMethodInteractionWriter(Integer expansionId) {
        ((SimpleJsonModelledBinaryInteractionWriter)getInteractionWriter()).setExpansionId(expansionId);
    }

    @Override
    protected void initialiseInteractionWriter() {
        super.setInteractionWriter(new SimpleJsonModelledBinaryInteractionWriter(getWriter(), getProcessedFeatures(), getProcessedInteractors(),
                getProcessedParticipants(), getIdGenerator()));
        if (getExpansionId() != null){
            ((SimpleJsonModelledBinaryInteractionWriter)getInteractionWriter()).setExpansionId(getExpansionId());
        }
    }
}
