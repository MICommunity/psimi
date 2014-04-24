package psidev.psi.mi.jami.xml.io.writer.elements.impl.extended;

import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.xml.cache.PsiXmlObjectCache;
import psidev.psi.mi.jami.xml.io.writer.elements.ExpandedPsiXmlElementWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.abstracts.AbstractXmlParticipantEvidenceWriter;
import psidev.psi.mi.jami.xml.model.extension.ExperimentalInteractor;
import psidev.psi.mi.jami.xml.model.extension.ExtendedPsiXmlParticipantEvidence;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

/**
 * Expanded XML 2.5 writer for an extended participant evidence with full experimental details and having experimental interactors, list of host organisms and list of experimental roles.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>14/11/13</pre>
 */

public class ExpandedXmlParticipantEvidenceWriter extends AbstractXmlParticipantEvidenceWriter implements ExpandedPsiXmlElementWriter<ParticipantEvidence> {
    private ExpandedPsiXmlElementWriter<ExperimentalInteractor> experimentalInteractorWriter;

    public ExpandedXmlParticipantEvidenceWriter(XMLStreamWriter writer, PsiXmlObjectCache objectIndex) {
        super(writer, objectIndex);
    }

    @Override
    protected void initialiseFeatureWriter() {
        super.setFeatureWriter(new XmlFeatureEvidenceWriter(getStreamWriter(), getObjectIndex()));
    }

    public ExpandedPsiXmlElementWriter<ExperimentalInteractor> getExperimentalInteractorWriter() {
        if (this.experimentalInteractorWriter == null){
            this.experimentalInteractorWriter = new ExpandedXmlExperimentalInteractorWriter(getStreamWriter(), getObjectIndex());

        }
        return experimentalInteractorWriter;
    }

    public void setExperimentalInteractorWriter(ExpandedPsiXmlElementWriter<ExperimentalInteractor> experimentalInteractorWriter) {
        this.experimentalInteractorWriter = experimentalInteractorWriter;
    }

    @Override
    protected void writeMolecule(Interactor interactor) throws XMLStreamException {
        super.writeMoleculeDescription(interactor);
    }

    @Override
    protected void writeExperimentalRoles(ParticipantEvidence object) throws XMLStreamException {
        if (object instanceof ExtendedPsiXmlParticipantEvidence){
            ExtendedPsiXmlParticipantEvidence xmlParticipant = (ExtendedPsiXmlParticipantEvidence)object;
            getStreamWriter().writeStartElement("experimentalRoleList");
            for (CvTerm expRole : xmlParticipant.getExperimentalRoles()){
                getCvWriter().write(expRole,"experimentalRole");
            }
            getStreamWriter().writeEndElement();
        }
        else{
            super.writeExperimentalRoles(object);
        }
    }

    @Override
    protected void writeHostOrganisms(ParticipantEvidence object) throws XMLStreamException {
        if (object instanceof ExtendedPsiXmlParticipantEvidence){
            ExtendedPsiXmlParticipantEvidence xmlParticipant = (ExtendedPsiXmlParticipantEvidence)object;
            if (!xmlParticipant.getHostOrganisms().isEmpty()){
                getStreamWriter().writeStartElement("hostOrganismList");
                for (Organism host : xmlParticipant.getHostOrganisms()){
                    getHostOrganismWriter().write(host);
                }
                getStreamWriter().writeEndElement();
            }
        }
        else{
            super.writeHostOrganisms(object);
        }
    }

    @Override
    protected void writeExperimentalInteractor(ParticipantEvidence object) throws XMLStreamException {
        if (object instanceof ExtendedPsiXmlParticipantEvidence){
            ExtendedPsiXmlParticipantEvidence xmlParticipant = (ExtendedPsiXmlParticipantEvidence)object;
            if (!xmlParticipant.getExperimentalInteractors().isEmpty()){
                getStreamWriter().writeStartElement("experimentalInteractorList");
                for (ExperimentalInteractor expInt : xmlParticipant.getExperimentalInteractors()){
                    getExperimentalInteractorWriter().write(expInt);
                }
                getStreamWriter().writeEndElement();
            }
        }
    }

    @Override
    protected void writeNames(ParticipantEvidence object) throws XMLStreamException {
        if (object instanceof NamedParticipant){
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
        else{
            super.writeNames(object);
        }
    }
}

