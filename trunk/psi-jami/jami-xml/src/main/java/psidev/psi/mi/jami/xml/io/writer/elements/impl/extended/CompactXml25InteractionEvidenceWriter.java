package psidev.psi.mi.jami.xml.io.writer.elements.impl.extended;

import org.codehaus.stax2.XMLStreamWriter2;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.xml.PsiXml25ObjectIndex;
import psidev.psi.mi.jami.xml.extension.InferredInteraction;
import psidev.psi.mi.jami.xml.io.writer.elements.CompactPsiXml25ElementWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXml25ElementWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXml25ParameterWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXml25XrefWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.compact.CompactXml25ParticipantEvidenceWriter;

import javax.xml.stream.XMLStreamException;

/**
 * Compact XML 2.5 writer for an extended interaction evidence (having modelled, intramolecular properties, list
 * of experiments, list of interaction types, etc.).
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>18/11/13</pre>
 */

public class CompactXml25InteractionEvidenceWriter extends AbstractXml25InteractionEvidenceWriter<InteractionEvidence, ParticipantEvidence>
                                                   implements CompactPsiXml25ElementWriter<InteractionEvidence>{
    public CompactXml25InteractionEvidenceWriter(XMLStreamWriter2 writer, PsiXml25ObjectIndex objectIndex) {
        super(writer, objectIndex, new psidev.psi.mi.jami.xml.io.writer.elements.impl.compact.CompactXml25ParticipantEvidenceWriter(writer, objectIndex));
    }

    public CompactXml25InteractionEvidenceWriter(XMLStreamWriter2 writer, PsiXml25ObjectIndex objectIndex, PsiXml25XrefWriter primaryRefWriter, PsiXml25XrefWriter secondaryRefWriter, CompactPsiXml25ElementWriter<ParticipantEvidence> participantWriter, PsiXml25ElementWriter<CvTerm> interactionTypeWriter, PsiXml25ElementWriter<Annotation> attributeWriter, PsiXml25ElementWriter<Experiment> experimentWriter, PsiXml25ElementWriter<Alias> aliasWriter, PsiXml25ElementWriter<InferredInteraction> inferredInteractionWriter1, PsiXml25ElementWriter<String> availabilityWriter, PsiXml25ElementWriter<Confidence> confidenceWriter, PsiXml25ParameterWriter parameterWriter) {
        super(writer, objectIndex, primaryRefWriter, secondaryRefWriter, participantWriter != null ? participantWriter : new CompactXml25ParticipantEvidenceWriter(writer, objectIndex),
                interactionTypeWriter, attributeWriter, experimentWriter, aliasWriter, inferredInteractionWriter1, availabilityWriter, confidenceWriter, parameterWriter);
    }

    @Override
    protected void writeAvailability(InteractionEvidence object) throws XMLStreamException {
        if (object.getAvailability() != null){
            writeAvailabilityRef(object.getAvailability());
        }
    }

    @Override
    protected void writeExperiments(InteractionEvidence object) throws XMLStreamException {
        writeExperimentRef(object);
    }
}
