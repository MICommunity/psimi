package psidev.psi.mi.jami.xml.io.writer.elements.impl.compact;

import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.xml.cache.PsiXmlObjectCache;
import psidev.psi.mi.jami.xml.io.writer.elements.CompactPsiXmlElementWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXmlElementWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXmlParameterWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXmlXrefWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.XmlFeatureEvidenceWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.abstracts.AbstractXmlParticipantEvidenceWriter;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

/**
 * Compact Xml 2.5 writer for a named participant evidence with a shortname and a fullname.
 * It writes full experimental details
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>14/11/13</pre>
 */

public class CompactXmlNamedParticipantEvidenceWriter extends AbstractXmlParticipantEvidenceWriter implements CompactPsiXmlElementWriter<ParticipantEvidence> {
    public CompactXmlNamedParticipantEvidenceWriter(XMLStreamWriter writer, PsiXmlObjectCache objectIndex) {
        super(writer, objectIndex, new XmlFeatureEvidenceWriter(writer, objectIndex));
    }

    public CompactXmlNamedParticipantEvidenceWriter(XMLStreamWriter writer, PsiXmlObjectCache objectIndex,
                                                    PsiXmlElementWriter<Alias> aliasWriter, PsiXmlXrefWriter primaryRefWriter,
                                                    PsiXmlXrefWriter secondaryRefWriter, PsiXmlElementWriter<Interactor> interactorWriter,
                                                    PsiXmlElementWriter identificationMethodWriter, PsiXmlElementWriter<CvTerm> biologicalRoleWriter,
                                                    PsiXmlElementWriter experimentalRoleWriter, PsiXmlElementWriter experimentalPreparationWriter,
                                                    PsiXmlElementWriter<FeatureEvidence> featureWriter, PsiXmlElementWriter<Organism> hostOrganismWriter,
                                                    PsiXmlElementWriter<Confidence> confidenceWriter, PsiXmlParameterWriter parameterWriter,
                                                    PsiXmlElementWriter<Annotation> attributeWriter) {
        super(writer, objectIndex, aliasWriter, primaryRefWriter, secondaryRefWriter, interactorWriter, identificationMethodWriter, biologicalRoleWriter, experimentalRoleWriter, experimentalPreparationWriter,
                featureWriter != null ? featureWriter : new psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.Xml25FeatureEvidenceWriter(writer, objectIndex), hostOrganismWriter, parameterWriter, confidenceWriter, attributeWriter);
    }

    @Override
    protected void writeMolecule(Interactor interactor) throws XMLStreamException {
        super.writeMoleculeRef(interactor);
    }

    @Override
    protected void writeNames(ParticipantEvidence object) throws XMLStreamException {
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
