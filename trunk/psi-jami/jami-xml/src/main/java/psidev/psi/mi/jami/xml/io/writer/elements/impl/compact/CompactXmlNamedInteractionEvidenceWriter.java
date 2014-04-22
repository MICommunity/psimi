package psidev.psi.mi.jami.xml.io.writer.elements.impl.compact;

import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.xml.cache.PsiXmlObjectCache;
import psidev.psi.mi.jami.xml.io.writer.elements.*;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.XmlAliasWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.abstracts.AbstractXmlInteractionEvidenceWriter;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.util.Set;

/**
 * Compact XML 2.5 writer for a named interaction evidence (with full experimental details).
 * The interaction has aliases and a fullname in addition to the shortname
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>18/11/13</pre>
 */

public class CompactXmlNamedInteractionEvidenceWriter extends AbstractXmlInteractionEvidenceWriter<InteractionEvidence, ParticipantEvidence>
                                                   implements CompactPsiXmlElementWriter<InteractionEvidence> {
    private PsiXmlElementWriter<Alias> aliasWriter;
    public CompactXmlNamedInteractionEvidenceWriter(XMLStreamWriter writer, PsiXmlObjectCache objectIndex) {
        super(writer, objectIndex, new CompactXmlParticipantEvidenceWriter(writer, objectIndex));
        this.aliasWriter = new XmlAliasWriter(writer);
    }

    public CompactXmlNamedInteractionEvidenceWriter(XMLStreamWriter writer, PsiXmlObjectCache objectIndex,
                                                    PsiXmlElementWriter<Alias> aliasWriter, PsiXmlXrefWriter primaryRefWriter,
                                                    PsiXmlXrefWriter secondaryRefWriter, PsiXmlElementWriter<String> availabilityWriter,
                                                    PsiXmlExperimentWriter experimentWriter, PsiXmlParticipantWriter<ParticipantEvidence> participantWriter,
                                                    PsiXmlElementWriter<Set<Feature>> inferredInteractionWriter, PsiXmlElementWriter<CvTerm> interactionTypeWriter,
                                                    PsiXmlElementWriter<Confidence> confidenceWriter, PsiXmlParameterWriter parameterWriter,
                                                    PsiXmlElementWriter<Annotation> attributeWriter, PsiXmlElementWriter<Checksum> checksumWriter) {
        super(writer, objectIndex, primaryRefWriter, secondaryRefWriter, availabilityWriter, experimentWriter,
                participantWriter != null ? participantWriter : new CompactXmlParticipantEvidenceWriter(writer, objectIndex), inferredInteractionWriter, interactionTypeWriter, confidenceWriter, parameterWriter, attributeWriter, checksumWriter);
        this.aliasWriter = aliasWriter != null ? aliasWriter : new XmlAliasWriter(writer);
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

    @Override
    protected void writeNames(InteractionEvidence object) throws XMLStreamException {
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
}
