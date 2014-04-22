package psidev.psi.mi.jami.xml.io.writer.elements.impl.abstracts;

import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.xml.cache.PsiXmlObjectCache;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXmlElementWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXmlExperimentWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXmlParticipantWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXmlXrefWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.XmlAliasWriter;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.util.Set;

/**
 * Abstract class for named interactions
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>18/11/13</pre>
 */

public abstract class AbstractXmlNamedInteractionWriter<I extends Interaction, P extends Participant> extends AbstractXmlInteractionWriter<I,P> {

    private PsiXmlElementWriter<Alias> aliasWriter;

    public AbstractXmlNamedInteractionWriter(XMLStreamWriter writer, PsiXmlObjectCache objectIndex, PsiXmlParticipantWriter<P> participantWriter) {
        super(writer, objectIndex, participantWriter);
        this.aliasWriter = new XmlAliasWriter(writer);
    }

    protected AbstractXmlNamedInteractionWriter(XMLStreamWriter writer, PsiXmlObjectCache objectIndex,
                                                PsiXmlElementWriter<Alias> aliasWriter, PsiXmlXrefWriter primaryRefWriter,
                                                PsiXmlXrefWriter secondaryRefWriter, PsiXmlExperimentWriter experimentWriter,
                                                PsiXmlParticipantWriter<P> participantWriter, PsiXmlElementWriter<Set<Feature>> inferredInteractionWriter,
                                                PsiXmlElementWriter<CvTerm> interactionTypeWriter, PsiXmlElementWriter<Annotation> attributeWriter,
                                                PsiXmlElementWriter<Checksum> checksumWriter) {
        super(writer, objectIndex, primaryRefWriter, secondaryRefWriter, experimentWriter, participantWriter, inferredInteractionWriter, interactionTypeWriter, attributeWriter, checksumWriter);
        this.aliasWriter = aliasWriter != null ? aliasWriter : new XmlAliasWriter(writer);
    }

    @Override
    protected void writeNames(Interaction object) throws XMLStreamException {
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
