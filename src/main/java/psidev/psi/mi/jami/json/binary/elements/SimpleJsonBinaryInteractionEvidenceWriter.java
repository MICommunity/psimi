package psidev.psi.mi.jami.json.binary.elements;

import psidev.psi.mi.jami.binary.BinaryInteractionEvidence;
import psidev.psi.mi.jami.json.IncrementalIdGenerator;
import psidev.psi.mi.jami.json.MIJsonUtils;
import psidev.psi.mi.jami.json.elements.SimpleJsonInteractionEvidenceWriter;
import psidev.psi.mi.jami.model.Entity;
import psidev.psi.mi.jami.model.Feature;
import psidev.psi.mi.jami.model.Xref;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;

/**
 * Json writer for interaction evidences
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>18/07/14</pre>
 */

public class SimpleJsonBinaryInteractionEvidenceWriter extends SimpleJsonInteractionEvidenceWriter<BinaryInteractionEvidence> {

    private Integer expansionId;
    private int currentBinaryNumber =0;

    public SimpleJsonBinaryInteractionEvidenceWriter(Writer writer, Map<Feature, Integer> processedFeatures,
                                                     Map<String, String> processedInteractors, Map<Entity, Integer> processedParticipants) {
        super(writer, processedFeatures, processedInteractors, processedParticipants);
    }

    public SimpleJsonBinaryInteractionEvidenceWriter(Writer writer, Map<Feature, Integer> processedFeatures,
                                                     Map<String, String> processedInteractors, Map<Entity, Integer> processedParticipants,
                                                     IncrementalIdGenerator idGenerator) {
        super(writer, processedFeatures, processedInteractors, processedParticipants, idGenerator);
    }

    public void setExpansionId(Integer expansionId) {
        this.expansionId = expansionId;
        this.currentBinaryNumber =0;
    }

    @Override
    protected void writeOtherExpansionMethodProperties() throws IOException {
        if (expansionId != null){
            MIJsonUtils.writeSeparator(getWriter());
            MIJsonUtils.writeProperty("id", Integer.toString(expansionId), getWriter());
        }
    }

    @Override
    protected String[] generateInteractionIdentifier(BinaryInteractionEvidence object, Xref preferredIdentifier) {
        return MIJsonUtils.extractBinaryInteractionId(preferredIdentifier, object, expansionId != null ? currentBinaryNumber:null);
    }

    @Override
    public void write(BinaryInteractionEvidence object) throws IOException {
        super.write(object);
        this.currentBinaryNumber++;
    }
}
