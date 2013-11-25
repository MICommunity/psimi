package psidev.psi.mi.jami.xml.io.writer.elements.impl.compact;

import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.xml.PsiXml25ObjectCache;
import psidev.psi.mi.jami.xml.io.writer.elements.*;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.abstracts.AbstractXml25InteractionEvidenceWriter;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.util.Set;

/**
 * Compact XML 2.5 writer for an interaction evidence (with full experimental details).
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>18/11/13</pre>
 */

public class CompactXml25InteractionEvidenceWriter extends AbstractXml25InteractionEvidenceWriter<InteractionEvidence, ParticipantEvidence>
                                                   implements CompactPsiXml25ElementWriter<InteractionEvidence>{
    public CompactXml25InteractionEvidenceWriter(XMLStreamWriter writer, PsiXml25ObjectCache objectIndex) {
        super(writer, objectIndex, new CompactXml25ParticipantEvidenceWriter(writer, objectIndex));
    }

    public CompactXml25InteractionEvidenceWriter(XMLStreamWriter writer, PsiXml25ObjectCache objectIndex,
                                                 PsiXml25XrefWriter primaryRefWriter, PsiXml25XrefWriter secondaryRefWriter,
                                                 PsiXml25ElementWriter<String> availabilityWriter, PsiXml25ExperimentWriter experimentWriter,
                                                 PsiXml25ParticipantWriter<ParticipantEvidence> participantWriter, PsiXml25ElementWriter<Set<Feature>> inferredInteractionWriter,
                                                 PsiXml25ElementWriter<CvTerm> interactionTypeWriter, PsiXml25ElementWriter<Confidence> confidenceWriter,
                                                 PsiXml25ParameterWriter parameterWriter, PsiXml25ElementWriter<Annotation> attributeWriter,
                                                 PsiXml25ElementWriter<Checksum> checksumWriter) {
        super(writer, objectIndex, primaryRefWriter, secondaryRefWriter, availabilityWriter, experimentWriter,
                participantWriter != null ? participantWriter : new CompactXml25ParticipantEvidenceWriter(writer, objectIndex),
                inferredInteractionWriter, interactionTypeWriter, confidenceWriter, parameterWriter,
                attributeWriter, checksumWriter);
    }

    @Override
    protected void writeAvailability(InteractionEvidence object) throws XMLStreamException {
        if (object.getAvailability() != null){
            writeAvailabilityRef(object.getAvailability());
        }
    }

    @Override
    protected void writeExperiments(InteractionEvidence object) throws XMLStreamException {
        super.writeExperiments(object);
        writeExperimentRef();
    }
}
