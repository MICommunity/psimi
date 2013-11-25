package psidev.psi.mi.jami.xml.io.writer.elements.impl.expanded;

import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.xml.PsiXml25ObjectCache;
import psidev.psi.mi.jami.xml.io.writer.elements.*;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.Xml25AliasWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.abstracts.AbstractXml25InteractionEvidenceWriter;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.util.Set;

/**
 * Expanded XML 2.5 writer for a named interaction evidence (with full experimental details).
 * The interaction has aliases and a fullname in addition to the shortname
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>18/11/13</pre>
 */

public class ExpandedXml25NamedInteractionEvidenceWriter extends AbstractXml25InteractionEvidenceWriter<InteractionEvidence, ParticipantEvidence>
        implements ExpandedPsiXml25ElementWriter<InteractionEvidence> {
    private PsiXml25ElementWriter<Alias> aliasWriter;

    public ExpandedXml25NamedInteractionEvidenceWriter(XMLStreamWriter writer, PsiXml25ObjectCache objectIndex) {
        super(writer, objectIndex, new ExpandedXml25ParticipantEvidenceWriter(writer, objectIndex));
        this.aliasWriter = new Xml25AliasWriter(writer);
    }

    public ExpandedXml25NamedInteractionEvidenceWriter(XMLStreamWriter writer, PsiXml25ObjectCache objectIndex,
                                                       PsiXml25ElementWriter<Alias> aliasWriter,
                                                       PsiXml25XrefWriter primaryRefWriter, PsiXml25XrefWriter secondaryRefWriter,
                                                       PsiXml25ElementWriter<String> availabilityWriter, PsiXml25ExperimentWriter experimentWriter,
                                                       PsiXml25ParticipantWriter<ParticipantEvidence> participantWriter, PsiXml25ElementWriter<Set<Feature>> inferredInteractionWriter,
                                                       PsiXml25ElementWriter<CvTerm> interactionTypeWriter, PsiXml25ElementWriter<Confidence> confidenceWriter,
                                                       PsiXml25ParameterWriter parameterWriter, PsiXml25ElementWriter<Annotation> attributeWriter,
                                                       PsiXml25ElementWriter<Checksum> checksumWriter) {
        super(writer, objectIndex, primaryRefWriter, secondaryRefWriter, availabilityWriter, experimentWriter,
                participantWriter != null ? participantWriter : new ExpandedXml25ParticipantEvidenceWriter(writer, objectIndex), inferredInteractionWriter, interactionTypeWriter, confidenceWriter, parameterWriter, attributeWriter, checksumWriter);
        this.aliasWriter = aliasWriter != null ? aliasWriter : new Xml25AliasWriter(writer);

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
        writeExperimentDescription();
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
