package psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.compact.xml25;

import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Organism;
import psidev.psi.mi.jami.model.ParticipantEvidence;
import psidev.psi.mi.jami.xml.cache.PsiXmlObjectCache;
import psidev.psi.mi.jami.xml.io.writer.elements.CompactPsiXmlElementWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.*;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.xml25.XmlFeatureEvidenceWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.xml25.XmlParameterWriter;
import psidev.psi.mi.jami.xml.model.extension.ExperimentalInteractor;
import psidev.psi.mi.jami.xml.model.extension.ExtendedPsiXmlParticipantEvidence;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

/**
 * Compact XML 2.5 writer for an expanded participant evidence with full experimental details and having experimental interactors, list of host organisms and list of experimental roles.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>14/11/13</pre>
 */

public class XmlParticipantEvidenceWriter extends psidev.psi.mi.jami.xml.io.writer.elements.impl.compact.xml25.XmlNamedParticipantEvidenceWriter{
    private CompactPsiXmlElementWriter<ExperimentalInteractor> experimentalInteractorWriter;

    public XmlParticipantEvidenceWriter(XMLStreamWriter writer, PsiXmlObjectCache objectIndex) {
        super(writer, objectIndex);

    }

    @Override
    protected void initialiseXrefWriter() {
        super.setXrefWriter(new XmlDbXrefWriter(getStreamWriter()));
    }

    @Override
    protected void initialiseBiologicalRoleWriter() {
        super.setBiologicalRoleWriter(new XmlCvTermWriter(getStreamWriter()));
    }

    @Override
    protected void initialiseInteractorWriter() {
        super.setInteractorWriter(new XmlInteractorWriter(getStreamWriter(), getObjectIndex()));
    }

    @Override
    protected void initialiseConfidenceWriter() {
        super.setConfidenceWriter(new XmlConfidenceWriter(getStreamWriter(), getObjectIndex()));
    }

    @Override
    protected void initialiseHostOrganismWriter() {
        super.setHostOrganismWriter(new XmlHostOrganismWriter(getStreamWriter(), getObjectIndex()));
    }

    @Override
    protected void initialiseParameterWriter() {
        super.setParameterWriter(new XmlParameterWriter(getStreamWriter(), getObjectIndex()));
    }

    @Override
    protected void initialiseCvWriter() {
        super.setExperimentalCvWriter(new XmlExperimentalCvTermWriter(getStreamWriter(), getObjectIndex()));
    }

    @Override
    protected void initialiseFeatureWriter() {
        super.setFeatureWriter(new XmlFeatureEvidenceWriter(getStreamWriter(), getObjectIndex()));
    }

    public CompactPsiXmlElementWriter<ExperimentalInteractor> getExperimentalInteractorWriter() {
        if (this.experimentalInteractorWriter == null){
            this.experimentalInteractorWriter = new XmlExperimentalInteractorWriter(getStreamWriter(), getObjectIndex());
        }
        return experimentalInteractorWriter;
    }

    public void setExperimentalInteractorWriter(CompactPsiXmlElementWriter<ExperimentalInteractor> experimentalInteractorWriter) {
        this.experimentalInteractorWriter = experimentalInteractorWriter;
    }

    @Override
    protected void writeExperimentalRoles(ParticipantEvidence object) throws XMLStreamException {
        if (object instanceof ExtendedPsiXmlParticipantEvidence){
            ExtendedPsiXmlParticipantEvidence xmlParticipant = (ExtendedPsiXmlParticipantEvidence)object;
            getStreamWriter().writeStartElement("experimentalRoleList");
            for (CvTerm expRole : xmlParticipant.getExperimentalRoles()){
                getExperimentalCvWriter().write(expRole, "experimentalRole");
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
}
