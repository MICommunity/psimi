package psidev.psi.mi.jami.xml.io.writer.elements.impl.compact;

import psidev.psi.mi.jami.binary.BinaryInteraction;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.xml.cache.PsiXmlObjectCache;
import psidev.psi.mi.jami.xml.io.writer.elements.*;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.XmlAliasWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.abstracts.AbstractXmlInteractionWriter;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.util.Set;

/**
 * Compact XML 2.5 writer for a basic binary interaction (ignore experimental details) which has a fullname
 * and aliases in addition to a shortname
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>15/11/13</pre>
 */

public class CompactXmlNamedBinaryInteractionWriter extends AbstractXmlInteractionWriter<BinaryInteraction,Participant> implements CompactPsiXmlElementWriter<BinaryInteraction> {
    private PsiXmlElementWriter<Alias> aliasWriter;

    public CompactXmlNamedBinaryInteractionWriter(XMLStreamWriter writer, PsiXmlObjectCache objectIndex) {
        super(writer, objectIndex, new CompactXmlParticipantWriter(writer, objectIndex));
        this.aliasWriter = new XmlAliasWriter(writer);
    }

    public CompactXmlNamedBinaryInteractionWriter(XMLStreamWriter writer, PsiXmlObjectCache objectIndex,
                                                  PsiXmlElementWriter<Alias> aliasWriter, PsiXmlXrefWriter primaryRefWriter,
                                                  PsiXmlXrefWriter secondaryRefWriter, PsiXmlExperimentWriter experimentWriter,
                                                  PsiXmlParticipantWriter<Participant> participantWriter, PsiXmlElementWriter<Set<Feature>> inferredInteractionWriter,
                                                  PsiXmlElementWriter<CvTerm> interactionTypeWriter, PsiXmlElementWriter<Annotation> attributeWriter,
                                                  PsiXmlElementWriter<Checksum> checksumWriter) {
        super(writer, objectIndex, primaryRefWriter, secondaryRefWriter,experimentWriter,
                participantWriter != null ? participantWriter : new CompactXmlParticipantWriter(writer, objectIndex),
                inferredInteractionWriter, interactionTypeWriter, attributeWriter,
                checksumWriter);
        this.aliasWriter = aliasWriter != null ? aliasWriter : new XmlAliasWriter(writer);
    }

    @Override
    protected void writeAvailability(BinaryInteraction object) {
        // nothing to do
    }

    @Override
    protected void writeExperiments(BinaryInteraction object) throws XMLStreamException {
        writeExperimentRef();
    }

    @Override
    protected void writeOtherAttributes(BinaryInteraction object) {
        // nothing to do
    }

    @Override
    protected void writeIntraMolecular(BinaryInteraction object) {
        // nothing to do
    }

    @Override
    protected void writeModelled(BinaryInteraction object) {
        // nothing to do
    }

    @Override
    protected void writeParameters(BinaryInteraction object) {
        // nothing to do
    }

    @Override
    protected void writeConfidences(BinaryInteraction object) {
        // nothing to do
    }

    @Override
    protected void writeNegative(BinaryInteraction object) {
        // nothing to do
    }

    @Override
    protected void writeNames(BinaryInteraction object) throws XMLStreamException {
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
    protected void writeAttributes(BinaryInteraction object) throws XMLStreamException {
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
