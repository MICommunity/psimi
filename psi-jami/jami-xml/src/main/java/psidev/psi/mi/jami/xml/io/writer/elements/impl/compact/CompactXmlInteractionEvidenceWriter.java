package psidev.psi.mi.jami.xml.io.writer.elements.impl.compact;

import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.xml.cache.PsiXmlObjectCache;
import psidev.psi.mi.jami.xml.io.writer.elements.*;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.abstracts.AbstractXmlInteractionEvidenceWriter;

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

public class CompactXmlInteractionEvidenceWriter extends AbstractXmlInteractionEvidenceWriter<InteractionEvidence, ParticipantEvidence>
                                                   implements CompactPsiXmlElementWriter<InteractionEvidence> {
    public CompactXmlInteractionEvidenceWriter(XMLStreamWriter writer, PsiXmlObjectCache objectIndex) {
        super(writer, objectIndex, new CompactXmlParticipantEvidenceWriter(writer, objectIndex));
    }

    public CompactXmlInteractionEvidenceWriter(XMLStreamWriter writer, PsiXmlObjectCache objectIndex,
                                               PsiXmlXrefWriter primaryRefWriter, PsiXmlXrefWriter secondaryRefWriter,
                                               PsiXmlElementWriter<String> availabilityWriter, PsiXmlExperimentWriter experimentWriter,
                                               PsiXmlParticipantWriter<ParticipantEvidence> participantWriter, PsiXmlElementWriter<Set<Feature>> inferredInteractionWriter,
                                               PsiXmlElementWriter<CvTerm> interactionTypeWriter, PsiXmlElementWriter<Confidence> confidenceWriter,
                                               PsiXmlParameterWriter parameterWriter, PsiXmlElementWriter<Annotation> attributeWriter,
                                               PsiXmlElementWriter<Checksum> checksumWriter) {
        super(writer, objectIndex, primaryRefWriter, secondaryRefWriter, availabilityWriter, experimentWriter,
                participantWriter != null ? participantWriter : new CompactXmlParticipantEvidenceWriter(writer, objectIndex),
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
