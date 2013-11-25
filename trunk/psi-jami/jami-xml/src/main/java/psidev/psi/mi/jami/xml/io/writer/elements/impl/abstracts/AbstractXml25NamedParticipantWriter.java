package psidev.psi.mi.jami.xml.io.writer.elements.impl.abstracts;

import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.xml.PsiXml25ObjectCache;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXml25ElementWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXml25XrefWriter;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

/**
 * Abstract class for a named participant XML 2.5 writer
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>13/11/13</pre>
 */

public abstract class AbstractXml25NamedParticipantWriter<P extends Participant, F extends Feature> extends AbstractXml25ParticipantWriter<P,F>{
    public AbstractXml25NamedParticipantWriter(XMLStreamWriter writer, PsiXml25ObjectCache objectIndex, PsiXml25ElementWriter<F> featureWriter) {
        super(writer, objectIndex, featureWriter);
    }

    public AbstractXml25NamedParticipantWriter(XMLStreamWriter writer, PsiXml25ObjectCache objectIndex,
                                               PsiXml25ElementWriter<Alias> aliasWriter, PsiXml25XrefWriter primaryRefWriter,
                                               PsiXml25XrefWriter secondaryRefWriter, PsiXml25ElementWriter<Interactor> interactorWriter,
                                               PsiXml25ElementWriter<CvTerm> biologicalRoleWriter, PsiXml25ElementWriter<F> featureWriter,
                                               PsiXml25ElementWriter<Annotation> attributeWriter) {
        super(writer, objectIndex, aliasWriter, primaryRefWriter, secondaryRefWriter, interactorWriter, biologicalRoleWriter, featureWriter, attributeWriter);
    }

    @Override
    protected void writeNames(P object) throws XMLStreamException {
        NamedEntity xmlParticipant = (NamedEntity) object;
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
