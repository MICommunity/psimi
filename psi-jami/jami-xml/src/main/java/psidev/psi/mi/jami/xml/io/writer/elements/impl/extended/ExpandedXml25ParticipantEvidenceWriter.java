package psidev.psi.mi.jami.xml.io.writer.elements.impl.extended;

import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.xml.cache.PsiXml25ObjectCache;
import psidev.psi.mi.jami.xml.extension.ExperimentalInteractor;
import psidev.psi.mi.jami.xml.extension.ExtendedPsi25ParticipantEvidence;
import psidev.psi.mi.jami.xml.io.writer.elements.ExpandedPsiXml25ElementWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXml25ElementWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXml25ParameterWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXml25XrefWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.Xml25FeatureEvidenceWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.abstracts.AbstractXml25ParticipantEvidenceWriter;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

/**
 * Expanded XML 2.5 writer for an extended participant evidence with full experimental details and having experimental interactors, list of host organisms and list of experimental roles.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>14/11/13</pre>
 */

public class ExpandedXml25ParticipantEvidenceWriter extends AbstractXml25ParticipantEvidenceWriter implements ExpandedPsiXml25ElementWriter<ParticipantEvidence> {
    private ExpandedPsiXml25ElementWriter<ExperimentalInteractor> experimentalInteractorWriter;

    public ExpandedXml25ParticipantEvidenceWriter(XMLStreamWriter writer, PsiXml25ObjectCache objectIndex) {
        super(writer, objectIndex, new Xml25FeatureEvidenceWriter(writer, objectIndex));
        this.experimentalInteractorWriter = new ExpandedXml25ExperimentalInteractorWriter(writer, objectIndex);
    }

    public ExpandedXml25ParticipantEvidenceWriter(XMLStreamWriter writer, PsiXml25ObjectCache objectIndex,
                                                  PsiXml25ElementWriter<Alias> aliasWriter, PsiXml25XrefWriter primaryRefWriter,
                                                  PsiXml25XrefWriter secondaryRefWriter, PsiXml25ElementWriter<Interactor> interactorWriter,
                                                  PsiXml25ElementWriter identificationMethodWriter, PsiXml25ElementWriter<CvTerm> biologicalRoleWriter,
                                                  PsiXml25ElementWriter experimentalRoleWriter, PsiXml25ElementWriter experimentalPreparationWriter,
                                                  ExpandedPsiXml25ElementWriter<ExperimentalInteractor> experimentalInteractorWriter, PsiXml25ElementWriter<FeatureEvidence> featureWriter,
                                                  PsiXml25ElementWriter<Organism> hostOrganismWriter,
                                                  PsiXml25ElementWriter<Confidence> confidenceWriter, PsiXml25ParameterWriter parameterWriter,PsiXml25ElementWriter<Annotation> attributeWriter) {
        super(writer, objectIndex, aliasWriter, primaryRefWriter, secondaryRefWriter, interactorWriter, identificationMethodWriter, biologicalRoleWriter, experimentalRoleWriter, experimentalPreparationWriter,
                featureWriter != null ? featureWriter : new Xml25FeatureEvidenceWriter(writer, objectIndex),
                hostOrganismWriter, parameterWriter, confidenceWriter, attributeWriter);
        this.experimentalInteractorWriter = experimentalInteractorWriter != null ? experimentalInteractorWriter : new ExpandedXml25ExperimentalInteractorWriter(writer, objectIndex);

    }

    @Override
    protected void writeMolecule(Interactor interactor) throws XMLStreamException {
        super.writeMoleculeDescription(interactor);
    }

    @Override
    protected void writeExperimentalRoles(ParticipantEvidence object) throws XMLStreamException {
        ExtendedPsi25ParticipantEvidence xmlParticipant = (ExtendedPsi25ParticipantEvidence)object;
        getStreamWriter().writeStartElement("experimentalRoleList");
        for (CvTerm expRole : xmlParticipant.getExperimentalRoles()){
            getExperimentalRoleWriter().write(expRole);
        }
        getStreamWriter().writeEndElement();
    }

    @Override
    protected void writeHostOrganisms(ParticipantEvidence object) throws XMLStreamException {
        ExtendedPsi25ParticipantEvidence xmlParticipant = (ExtendedPsi25ParticipantEvidence)object;
        if (!xmlParticipant.getHostOrganisms().isEmpty()){
            getStreamWriter().writeStartElement("hostOrganismList");
            for (Organism host : xmlParticipant.getHostOrganisms()){
                getHostOrganismWriter().write(host);
            }
            getStreamWriter().writeEndElement();
        }
    }

    @Override
    protected void writeExperimentalInteractor(ParticipantEvidence object) throws XMLStreamException {
        ExtendedPsi25ParticipantEvidence xmlParticipant = (ExtendedPsi25ParticipantEvidence)object;
        if (!xmlParticipant.getExperimentalInteractors().isEmpty()){
            getStreamWriter().writeStartElement("experimentalInteractorList");
            for (ExperimentalInteractor expInt : xmlParticipant.getExperimentalInteractors()){
                this.experimentalInteractorWriter.write(expInt);
            }
            getStreamWriter().writeEndElement();
        }
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

