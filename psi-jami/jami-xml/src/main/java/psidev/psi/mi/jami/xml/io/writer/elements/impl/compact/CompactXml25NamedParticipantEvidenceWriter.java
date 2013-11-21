package psidev.psi.mi.jami.xml.io.writer.elements.impl.compact;

import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.xml.PsiXml25ObjectCache;
import psidev.psi.mi.jami.xml.io.writer.elements.CompactPsiXml25ElementWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXml25ElementWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXml25ParameterWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXml25XrefWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.Xml25FeatureEvidenceWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.abstracts.AbstractXml25ParticipantEvidenceWriter;

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

public class CompactXml25NamedParticipantEvidenceWriter extends AbstractXml25ParticipantEvidenceWriter implements CompactPsiXml25ElementWriter<ParticipantEvidence> {
    public CompactXml25NamedParticipantEvidenceWriter(XMLStreamWriter writer, PsiXml25ObjectCache objectIndex) {
        super(writer, objectIndex, new Xml25FeatureEvidenceWriter(writer, objectIndex));
    }

    public CompactXml25NamedParticipantEvidenceWriter(XMLStreamWriter writer, PsiXml25ObjectCache objectIndex,
                                                      PsiXml25ElementWriter<Alias> aliasWriter, PsiXml25XrefWriter primaryRefWriter,
                                                      PsiXml25XrefWriter secondaryRefWriter, PsiXml25ElementWriter<CvTerm> biologicalRoleWriter,
                                                      PsiXml25ElementWriter<FeatureEvidence> featureWriter, PsiXml25ElementWriter<Annotation> attributeWriter,
                                                      PsiXml25ElementWriter<Interactor> interactorWriter, PsiXml25ElementWriter<CvTerm> experimentalPreparationWriter,
                                                      PsiXml25ElementWriter<CvTerm> identificationMethodWriter, PsiXml25ElementWriter<Confidence> confidenceWriter,
                                                      PsiXml25ElementWriter<CvTerm> experimentalRoleWriter, PsiXml25ElementWriter<Organism> hostOrganismWriter,
                                                      PsiXml25ParameterWriter parameterWriter) {
        super(writer, objectIndex, aliasWriter, primaryRefWriter, secondaryRefWriter, biologicalRoleWriter, featureWriter != null ? featureWriter : new Xml25FeatureEvidenceWriter(writer, objectIndex), attributeWriter, interactorWriter,
                experimentalPreparationWriter,identificationMethodWriter, confidenceWriter, experimentalRoleWriter, hostOrganismWriter, parameterWriter);
    }

    @Override
    protected void writeMolecule(Interactor interactor) throws XMLStreamException {
        super.writeMoleculeRef(interactor);
    }

    @Override
    protected void writeNames(ParticipantEvidence object) throws XMLStreamException {
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
