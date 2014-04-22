package psidev.psi.mi.jami.xml.io.writer.elements.impl.abstracts;

import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.xml.cache.PsiXmlObjectCache;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXmlElementWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXmlXrefWriter;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

/**
 * Abstract class for a named participant XML 2.5 writer
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>13/11/13</pre>
 */

public abstract class AbstractXmlNamedParticipantWriter<P extends Participant, F extends Feature> extends AbstractXmlParticipantWriter<P,F> {
    public AbstractXmlNamedParticipantWriter(XMLStreamWriter writer, PsiXmlObjectCache objectIndex, PsiXmlElementWriter<F> featureWriter) {
        super(writer, objectIndex, featureWriter);
    }

    public AbstractXmlNamedParticipantWriter(XMLStreamWriter writer, PsiXmlObjectCache objectIndex,
                                             PsiXmlElementWriter<Alias> aliasWriter, PsiXmlXrefWriter primaryRefWriter,
                                             PsiXmlXrefWriter secondaryRefWriter, PsiXmlElementWriter<Interactor> interactorWriter,
                                             PsiXmlElementWriter<CvTerm> biologicalRoleWriter, PsiXmlElementWriter<F> featureWriter,
                                             PsiXmlElementWriter<Annotation> attributeWriter) {
        super(writer, objectIndex, aliasWriter, primaryRefWriter, secondaryRefWriter, interactorWriter, biologicalRoleWriter, featureWriter, attributeWriter);
    }

    @Override
    protected void writeNames(P object) throws XMLStreamException {
        NamedParticipant xmlParticipant = (NamedParticipant) object;
        // write names
        boolean hasShortLabel = xmlParticipant.getShortName() != null;
        boolean hasFullLabel = xmlParticipant.getFullName() != null;
        boolean hasAliases = !xmlParticipant.getAliases().isEmpty();
        if (hasShortLabel || hasFullLabel | hasAliases){
            getStreamWriter().writeStartElement("names");
            // write shortname
            if (hasShortLabel){
                getStreamWriter().writeStartElement("shortLabel");
                getStreamWriter().writeCharacters(xmlParticipant.getShortName());
                getStreamWriter().writeEndElement();
            }
            // write fullname
            if (hasFullLabel){
                getStreamWriter().writeStartElement("fullName");
                getStreamWriter().writeCharacters(xmlParticipant.getFullName());
                getStreamWriter().writeEndElement();
            }
            // write aliases
            for (Object alias : xmlParticipant.getAliases()){
                getAliasWriter().write((Alias)alias);
            }
            // write end names
            getStreamWriter().writeEndElement();
        }
    }
}
