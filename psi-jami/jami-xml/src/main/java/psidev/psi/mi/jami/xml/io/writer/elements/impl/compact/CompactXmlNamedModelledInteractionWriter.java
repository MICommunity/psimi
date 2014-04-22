package psidev.psi.mi.jami.xml.io.writer.elements.impl.compact;

import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.xml.cache.PsiXmlObjectCache;
import psidev.psi.mi.jami.xml.io.writer.elements.*;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.XmlAliasWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.abstracts.AbstractXmlModelledInteractionWriter;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.util.Set;

/**
 * Compact XML 2.5 writer for a modelled interaction (ignore experimental details) which have a fullname and aliases in
 * addition to the shortname.
 * It will write cooperative effects as attributes
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>15/11/13</pre>
 */

public class CompactXmlNamedModelledInteractionWriter extends AbstractXmlModelledInteractionWriter<ModelledInteraction, ModelledParticipant> implements CompactPsiXmlElementWriter<ModelledInteraction> {
    private PsiXmlElementWriter<Alias> aliasWriter;

    public CompactXmlNamedModelledInteractionWriter(XMLStreamWriter writer, PsiXmlObjectCache objectIndex) {
        super(writer, objectIndex, new CompactXmlModelledParticipantWriter(writer, objectIndex));
        this.aliasWriter = new XmlAliasWriter(writer);
    }

    public CompactXmlNamedModelledInteractionWriter(XMLStreamWriter writer, PsiXmlObjectCache objectIndex,
                                                    PsiXmlElementWriter<Alias> aliasWriter, PsiXmlXrefWriter primaryRefWriter,
                                                    PsiXmlXrefWriter secondaryRefWriter, PsiXmlExperimentWriter experimentWriter,
                                                    PsiXmlParticipantWriter<ModelledParticipant> participantWriter, PsiXmlElementWriter<Set<Feature>> inferredInteractionWriter,
                                                    PsiXmlElementWriter<CvTerm> interactionTypeWriter, PsiXmlElementWriter<Confidence> confidenceWriter,
                                                    PsiXmlParameterWriter parameterWriter, PsiXmlElementWriter<Annotation> attributeWriter,
                                                    PsiXmlElementWriter<Checksum> checksumWriter) {
        super(writer, objectIndex, primaryRefWriter, secondaryRefWriter, experimentWriter,
                participantWriter != null ? participantWriter : new CompactXmlModelledParticipantWriter(writer, objectIndex), inferredInteractionWriter, interactionTypeWriter, confidenceWriter, parameterWriter, attributeWriter, checksumWriter);
        this.aliasWriter = aliasWriter != null ? aliasWriter : new XmlAliasWriter(writer);
    }

    @Override
    protected void writeExperiments(ModelledInteraction object) throws XMLStreamException {
        super.writeExperiments(object);
        writeExperimentRef();
    }

    @Override
    protected void writeNames(ModelledInteraction object) throws XMLStreamException {
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
