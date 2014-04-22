package psidev.psi.mi.jami.xml.io.writer.elements.impl.extended;

import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.xml.cache.PsiXmlObjectCache;
import psidev.psi.mi.jami.xml.model.extension.InferredInteraction;
import psidev.psi.mi.jami.xml.io.writer.elements.*;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.expanded.ExpandedXmlParticipantEvidenceWriter;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

/**
 * Expanded XML 2.5 writer for an extended interaction evidence (having modelled, intramolecular properties, list
 * of experiments, list of interaction types, etc.).
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>18/11/13</pre>
 */

public class ExpandedXml25InteractionEvidenceWriter extends AbstractXml25InteractionEvidenceWriter<InteractionEvidence, ParticipantEvidence>
        implements ExpandedPsiXmlElementWriter<InteractionEvidence> {
    public ExpandedXml25InteractionEvidenceWriter(XMLStreamWriter writer, PsiXmlObjectCache objectIndex) {
        super(writer, objectIndex, new ExpandedXmlParticipantEvidenceWriter(writer, objectIndex));
    }

    public ExpandedXml25InteractionEvidenceWriter(XMLStreamWriter writer, PsiXmlObjectCache objectIndex,
                                                  PsiXmlElementWriter<Alias> aliasWriter, PsiXmlXrefWriter primaryRefWriter,
                                                  PsiXmlXrefWriter secondaryRefWriter, PsiXmlElementWriter<String> availabilityWriter,
                                                  PsiXmlExperimentWriter experimentWriter, PsiXmlParticipantWriter<ParticipantEvidence> participantWriter,
                                                  PsiXmlElementWriter<InferredInteraction> inferredInteractionWriter1, PsiXmlElementWriter<CvTerm> interactionTypeWriter,
                                                  PsiXmlElementWriter<Confidence> confidenceWriter, PsiXmlParameterWriter parameterWriter,
                                                  PsiXmlElementWriter<Annotation> attributeWriter, PsiXmlElementWriter<Checksum> checksumWriter) {
        super(writer, objectIndex, aliasWriter, primaryRefWriter, secondaryRefWriter, availabilityWriter, experimentWriter,
                participantWriter != null ? participantWriter : new ExpandedXmlParticipantEvidenceWriter(writer, objectIndex),
                inferredInteractionWriter1, interactionTypeWriter, confidenceWriter, parameterWriter, attributeWriter, checksumWriter);
    }

    @Override
    protected void writeAvailability(InteractionEvidence object) throws XMLStreamException {
        if (object.getAvailability() != null){
            writeAvailabilityDescription(object.getAvailability());
        }
    }

    @Override
    protected void writeExperiments(InteractionEvidence object) throws XMLStreamException {
        super.writeExperiments(object);
        writeExperimentDescription(object);
    }
}
