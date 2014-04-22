package psidev.psi.mi.jami.xml.io.writer.elements.impl.extended;

import psidev.psi.mi.jami.binary.BinaryInteractionEvidence;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.xml.cache.PsiXmlObjectCache;
import psidev.psi.mi.jami.xml.model.extension.InferredInteraction;
import psidev.psi.mi.jami.xml.io.writer.elements.*;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.compact.CompactXmlParticipantEvidenceWriter;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

/**
 * Compact XML 2.5 writer for an extended binary interaction evidence (having modelled, intramolecular properties, list
 * of experiments, list of interaction types, etc.).
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>18/11/13</pre>
 */

public class CompactXml25BinaryInteractionEvidenceWriter extends AbstractXml25InteractionEvidenceWriter<BinaryInteractionEvidence, ParticipantEvidence>
                                                   implements CompactPsiXmlElementWriter<BinaryInteractionEvidence> {
    public CompactXml25BinaryInteractionEvidenceWriter(XMLStreamWriter writer, PsiXmlObjectCache objectIndex) {
        super(writer, objectIndex, new CompactXmlParticipantEvidenceWriter(writer, objectIndex));
    }

    public CompactXml25BinaryInteractionEvidenceWriter(XMLStreamWriter writer, PsiXmlObjectCache objectIndex,
                                                       PsiXmlElementWriter<Alias> aliasWriter, PsiXmlXrefWriter primaryRefWriter,
                                                       PsiXmlXrefWriter secondaryRefWriter, PsiXmlElementWriter<String> availabilityWriter,
                                                       PsiXmlExperimentWriter experimentWriter, PsiXmlParticipantWriter<ParticipantEvidence> participantWriter,
                                                       PsiXmlElementWriter<InferredInteraction> inferredInteractionWriter1, PsiXmlElementWriter<CvTerm> interactionTypeWriter,
                                                       PsiXmlElementWriter<Confidence> confidenceWriter, PsiXmlParameterWriter parameterWriter,
                                                       PsiXmlElementWriter<Annotation> attributeWriter, PsiXmlElementWriter<Checksum> checksumWriter) {
        super(writer, objectIndex, aliasWriter, primaryRefWriter, secondaryRefWriter, availabilityWriter, experimentWriter,
                participantWriter != null ? participantWriter : new CompactXmlParticipantEvidenceWriter(writer, objectIndex),
                inferredInteractionWriter1, interactionTypeWriter, confidenceWriter, parameterWriter, attributeWriter, checksumWriter);
    }

    @Override
    protected void writeAvailability(BinaryInteractionEvidence object) throws XMLStreamException {
        if (object.getAvailability() != null){
            writeAvailabilityRef(object.getAvailability());
        }
    }

    @Override
    protected void writeExperiments(BinaryInteractionEvidence object) throws XMLStreamException {
        super.writeExperiments(object);
        writeExperimentRef(object);
    }

    @Override
    protected void writeAttributes(BinaryInteractionEvidence object) throws XMLStreamException {
        // write attributes
        if (!object.getAnnotations().isEmpty()){
            // write start attribute list
            getStreamWriter().writeStartElement("attributeList");
            for (Object ann : object.getAnnotations()){
                getAttributeWriter().write((Annotation)ann);
            }
            for (Object c : object.getChecksums()){
                getChecksumWriter().write((Checksum)c);
            }
            // write complex expansion if any
            if (object.getComplexExpansion() != null){
                super.writeAttribute(object.getComplexExpansion().getShortName(), object.getComplexExpansion().getMIIdentifier());
            }
            // write end attributeList
            getStreamWriter().writeEndElement();
        }
        // write checksum
        else if (!object.getChecksums().isEmpty()){
            // write start attribute list
            getStreamWriter().writeStartElement("attributeList");
            for (Object c : object.getChecksums()){
                getChecksumWriter().write((Checksum)c);
            }
            // write complex expansion if any
            if (object.getComplexExpansion() != null){
                super.writeAttribute(object.getComplexExpansion().getShortName(), object.getComplexExpansion().getMIIdentifier());
            }
            // write end attributeList
            getStreamWriter().writeEndElement();
        }
        // write complex expansion if any
        else if (object.getComplexExpansion() != null){
            // write start attribute list
            getStreamWriter().writeStartElement("attributeList");
            super.writeAttribute(object.getComplexExpansion().getShortName(), object.getComplexExpansion().getMIIdentifier());
            // write end attributeList
            getStreamWriter().writeEndElement();
        }
    }
}
