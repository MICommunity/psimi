package psidev.psi.mi.jami.xml.io.writer.elements.impl.compact;

import psidev.psi.mi.jami.binary.BinaryInteractionEvidence;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.xml.cache.PsiXmlObjectCache;
import psidev.psi.mi.jami.xml.io.writer.elements.*;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.XmlAliasWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.abstracts.AbstractXmlInteractionEvidenceWriter;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.util.Set;

/**
 * Compact XML 2.5 writer for a named binary interaction evidence (with full experimental details).
 * The interaction has aliases and a fullname in addition to the shortname
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>18/11/13</pre>
 */

public class CompactXmlNamedBinaryInteractionEvidenceWriter extends AbstractXmlInteractionEvidenceWriter<BinaryInteractionEvidence, ParticipantEvidence>
                                                   implements CompactPsiXmlElementWriter<BinaryInteractionEvidence> {
    private PsiXmlElementWriter<Alias> aliasWriter;
    public CompactXmlNamedBinaryInteractionEvidenceWriter(XMLStreamWriter writer, PsiXmlObjectCache objectIndex) {
        super(writer, objectIndex, new CompactXmlParticipantEvidenceWriter(writer, objectIndex));
        this.aliasWriter = new XmlAliasWriter(writer);
    }

    public CompactXmlNamedBinaryInteractionEvidenceWriter(XMLStreamWriter writer, PsiXmlObjectCache objectIndex,
                                                          PsiXmlElementWriter<Alias> aliasWriter, PsiXmlXrefWriter primaryRefWriter,
                                                          PsiXmlXrefWriter secondaryRefWriter, PsiXmlElementWriter<String> availabilityWriter,
                                                          PsiXmlExperimentWriter experimentWriter, PsiXmlParticipantWriter<ParticipantEvidence> participantWriter,
                                                          PsiXmlElementWriter<Set<Feature>> inferredInteractionWriter, PsiXmlElementWriter<CvTerm> interactionTypeWriter,
                                                          PsiXmlElementWriter<Confidence> confidenceWriter, PsiXmlParameterWriter parameterWriter,
                                                          PsiXmlElementWriter<Annotation> attributeWriter, PsiXmlElementWriter<Checksum> checksumWriter) {
        super(writer, objectIndex, primaryRefWriter, secondaryRefWriter, availabilityWriter, experimentWriter,
                participantWriter != null ? participantWriter : new CompactXmlParticipantEvidenceWriter(writer, objectIndex),
                inferredInteractionWriter, interactionTypeWriter, confidenceWriter, parameterWriter,
                attributeWriter, checksumWriter);
        this.aliasWriter = aliasWriter != null ? aliasWriter : new XmlAliasWriter(writer);
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
        writeExperimentRef();
    }

    @Override
    protected void writeNames(BinaryInteractionEvidence object) throws XMLStreamException {
        NamedInteraction xmlInteraction = (NamedInteraction) object;
        // write names
        boolean hasShortLabel = xmlInteraction.getShortName() != null;
        boolean hasInteractionFullLabel = xmlInteraction.getFullName() != null;
        boolean hasAliases = !xmlInteraction.getAliases().isEmpty();
        if (hasShortLabel || hasInteractionFullLabel || hasAliases){
            getStreamWriter().writeStartElement("names");
            // write shortname
            if (hasShortLabel){
                getStreamWriter().writeStartElement("shortLabel");
                getStreamWriter().writeCharacters(xmlInteraction.getShortName());
                getStreamWriter().writeEndElement();
            }
            // write fullname
            if (hasInteractionFullLabel){
                getStreamWriter().writeStartElement("fullName");
                getStreamWriter().writeCharacters(xmlInteraction.getFullName());
                getStreamWriter().writeEndElement();
            }
            // write aliases
            for (Object alias : xmlInteraction.getAliases()){
                this.aliasWriter.write((Alias)alias);
            }
            // write end names
            getStreamWriter().writeEndElement();
        }
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
