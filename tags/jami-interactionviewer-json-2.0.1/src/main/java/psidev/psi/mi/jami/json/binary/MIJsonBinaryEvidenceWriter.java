package psidev.psi.mi.jami.json.binary;

import psidev.psi.mi.jami.binary.BinaryInteractionEvidence;
import psidev.psi.mi.jami.bridges.fetcher.OntologyTermFetcher;
import psidev.psi.mi.jami.json.IncrementalIdGenerator;
import psidev.psi.mi.jami.json.binary.elements.SimpleJsonBinaryInteractionEvidenceWriter;
import psidev.psi.mi.jami.model.Entity;
import psidev.psi.mi.jami.model.Feature;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.util.Map;

/**
 * JSON writer for InteractionEvidences
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>03/07/13</pre>
 */

public class MIJsonBinaryEvidenceWriter extends AbstractMIJsonBinaryWriter<BinaryInteractionEvidence>{

    public MIJsonBinaryEvidenceWriter() {
        super();
    }

    public MIJsonBinaryEvidenceWriter(File file, OntologyTermFetcher fetcher) throws IOException {
        super(file, fetcher);
    }

    public MIJsonBinaryEvidenceWriter(OutputStream output, OntologyTermFetcher fetcher) {
        super(output, fetcher);
    }

    public MIJsonBinaryEvidenceWriter(Writer writer, OntologyTermFetcher fetcher) {
        super(writer, fetcher);
    }

    public MIJsonBinaryEvidenceWriter(Writer writer, OntologyTermFetcher fetcher, Map<String, String> processedInteractors, Map<Feature, Integer> processedFeatures, Map<Entity, Integer> processedParticipants, IncrementalIdGenerator idGenerator) {
        super(writer, fetcher, processedInteractors, processedFeatures, processedParticipants, idGenerator);
    }

    public MIJsonBinaryEvidenceWriter(Map<String, String> processedInteractors, Map<Feature, Integer> processedFeatures, Map<Entity, Integer> processedParticipants, IncrementalIdGenerator idGenerator) {
        super(processedInteractors, processedFeatures, processedParticipants, idGenerator);
    }

    @Override
    protected void initialiseInteractionWriter() {
        super.setInteractionWriter(new SimpleJsonBinaryInteractionEvidenceWriter(getWriter(), getProcessedFeatures(),
                getProcessedInteractors(), getProcessedParticipants(), getIdGenerator()));
        if (getExpansionId() != null){
            ((SimpleJsonBinaryInteractionEvidenceWriter)getInteractionWriter()).setExpansionId(getExpansionId());
        }
    }

    @Override
    protected void initExpansionMethodInteractionWriter(Integer expansionId) {
        ((SimpleJsonBinaryInteractionEvidenceWriter) getInteractionWriter()).setExpansionId(expansionId);
    }
}
