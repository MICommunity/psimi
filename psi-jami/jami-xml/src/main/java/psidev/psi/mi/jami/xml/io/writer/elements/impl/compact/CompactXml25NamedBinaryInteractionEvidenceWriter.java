package psidev.psi.mi.jami.xml.io.writer.elements.impl.compact;

import psidev.psi.mi.jami.binary.BinaryInteractionEvidence;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.xml.PsiXml25ObjectCache;
import psidev.psi.mi.jami.xml.io.writer.elements.*;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.Xml25AliasWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.abstracts.AbstractXml25InteractionEvidenceWriter;

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

public class CompactXml25NamedBinaryInteractionEvidenceWriter extends AbstractXml25InteractionEvidenceWriter<BinaryInteractionEvidence, ParticipantEvidence>
                                                   implements CompactPsiXml25ElementWriter<BinaryInteractionEvidence>{
    private PsiXml25ElementWriter<Alias> aliasWriter;
    public CompactXml25NamedBinaryInteractionEvidenceWriter(XMLStreamWriter writer, PsiXml25ObjectCache objectIndex) {
        super(writer, objectIndex, new CompactXml25ParticipantEvidenceWriter(writer, objectIndex));
        this.aliasWriter = new Xml25AliasWriter(writer);
    }

    public CompactXml25NamedBinaryInteractionEvidenceWriter(XMLStreamWriter writer, PsiXml25ObjectCache objectIndex,
                                                            PsiXml25XrefWriter primaryRefWriter, PsiXml25XrefWriter secondaryRefWriter,
                                                            PsiXml25ParticipantWriter<ParticipantEvidence> participantWriter, PsiXml25ElementWriter<CvTerm> interactionTypeWriter,
                                                            PsiXml25ElementWriter<Annotation> attributeWriter, PsiXml25ElementWriter<Set<Feature>> inferredInteractionWriter,
                                                            PsiXml25ElementWriter<Experiment> experimentWriter, PsiXml25ElementWriter<String> availabilityWriter,
                                                            PsiXml25ElementWriter<Confidence> confidenceWriter, PsiXml25ParameterWriter parameterWriter,
                                                            PsiXml25ElementWriter<Alias> aliasWriter) {
        super(writer, objectIndex, primaryRefWriter, secondaryRefWriter, participantWriter != null ? participantWriter : new CompactXml25ParticipantEvidenceWriter(writer, objectIndex),
                interactionTypeWriter, attributeWriter, inferredInteractionWriter, experimentWriter, availabilityWriter, confidenceWriter, parameterWriter);
        this.aliasWriter = aliasWriter != null ? aliasWriter : new Xml25AliasWriter(writer);
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
}
