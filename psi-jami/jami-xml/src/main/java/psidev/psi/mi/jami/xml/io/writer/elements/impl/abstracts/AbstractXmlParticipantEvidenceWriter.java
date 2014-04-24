package psidev.psi.mi.jami.xml.io.writer.elements.impl.abstracts;

import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.xml.cache.PsiXmlObjectCache;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXmlElementWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.*;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

/**
 * Abstract class for XML 2.5 writer of participant evidences
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>14/11/13</pre>
 */

public abstract class AbstractXmlParticipantEvidenceWriter extends AbstractXmlParticipantWriter<ParticipantEvidence, FeatureEvidence> {
    private PsiXmlElementWriter experimentalPreparationWriter;
    private PsiXmlElementWriter identificationMethodWriter;
    private PsiXmlElementWriter<Confidence> confidenceWriter;
    private PsiXmlElementWriter experimentalRoleWriter;
    private PsiXmlElementWriter<Organism> hostOrganismWriter;
    private PsiXmlElementWriter<Parameter> parameterWriter;

    public AbstractXmlParticipantEvidenceWriter(XMLStreamWriter writer, PsiXmlObjectCache objectIndex) {
        super(writer, objectIndex);
    }

    public PsiXmlElementWriter getExperimentalPreparationWriter() {
        if (this.experimentalPreparationWriter == null){
            this.experimentalPreparationWriter = new XmlExperimentalPreparationWriter(getStreamWriter());
        }
        return experimentalPreparationWriter;
    }

    public void setExperimentalPreparationWriter(PsiXmlElementWriter experimentalPreparationWriter) {
        this.experimentalPreparationWriter = experimentalPreparationWriter;
    }

    public PsiXmlElementWriter getIdentificationMethodWriter() {
        if (this.identificationMethodWriter == null){
            this.identificationMethodWriter = new XmlParticipantIdentificationMethodWriter(getStreamWriter());
        }
        return identificationMethodWriter;
    }

    public void setIdentificationMethodWriter(PsiXmlElementWriter identificationMethodWriter) {
        this.identificationMethodWriter = identificationMethodWriter;
    }

    public PsiXmlElementWriter<Confidence> getConfidenceWriter() {
        if (this.confidenceWriter == null){
            this.confidenceWriter = new XmlConfidenceWriter(getStreamWriter());
        }
        return confidenceWriter;
    }

    public void setConfidenceWriter(PsiXmlElementWriter<Confidence> confidenceWriter) {
        this.confidenceWriter = confidenceWriter;
    }

    public void setExperimentalRoleWriter(PsiXmlElementWriter experimentalRoleWriter) {

        this.experimentalRoleWriter = experimentalRoleWriter;
    }

    public void setHostOrganismWriter(PsiXmlElementWriter<Organism> hostOrganismWriter) {

        this.hostOrganismWriter = hostOrganismWriter;
    }


    public PsiXmlElementWriter<CvTerm> getExperimentalRoleWriter() {
        if (this.experimentalRoleWriter == null){
            this.experimentalRoleWriter = new XmlExperimentalRoleWriter(getStreamWriter());
        }
        return experimentalRoleWriter;
    }

    public PsiXmlElementWriter<Organism> getHostOrganismWriter() {
        if (this.hostOrganismWriter == null){
            this.hostOrganismWriter = new XmlHostOrganismWriter(getStreamWriter());
        }
        return hostOrganismWriter;
    }

    public PsiXmlElementWriter<Parameter> getParameterWriter() {
        if (this.parameterWriter == null){
            this.parameterWriter = new XmlParameterWriter(getStreamWriter(), getObjectIndex());
        }
        return parameterWriter;
    }

    public void setParameterWriter(PsiXmlElementWriter<Parameter> parameterWriter) {
        this.parameterWriter = parameterWriter;
    }

    @Override
    protected void writeExperimentalPreparations(ParticipantEvidence object) throws XMLStreamException {
        if (!object.getExperimentalPreparations().isEmpty()){
            getStreamWriter().writeStartElement("experimentalPreparationList");
            for (CvTerm prep : object.getExperimentalPreparations()){
                getExperimentalPreparationWriter().write(prep);
            }
            getStreamWriter().writeEndElement();
        }
    }

    @Override
    protected void writeExperimentalRoles(ParticipantEvidence object) throws XMLStreamException {
        getStreamWriter().writeStartElement("experimentalRoleList");
        getExperimentalRoleWriter().write(object.getExperimentalRole());
        getStreamWriter().writeEndElement();
    }

    @Override
    protected void writeParticipantIdentificationMethods(ParticipantEvidence object) throws XMLStreamException {
        if (!object.getIdentificationMethods().isEmpty()){
            getStreamWriter().writeStartElement("participantIdentificationMethodList");
            for (CvTerm method : object.getIdentificationMethods()){
                getIdentificationMethodWriter().write(method);
            }
            getStreamWriter().writeEndElement();
        }
    }

    @Override
    protected void writeExperimentalInteractor(ParticipantEvidence object) throws XMLStreamException {
        // nothing to do here
    }

    @Override
    protected void writeHostOrganisms(ParticipantEvidence object) throws XMLStreamException {
        if (object.getExpressedInOrganism() != null){
            getStreamWriter().writeStartElement("hostOrganismList");
            getHostOrganismWriter().write(object.getExpressedInOrganism());
            getStreamWriter().writeEndElement();
        }
    }

    @Override
    protected void writeConfidences(ParticipantEvidence object) throws XMLStreamException {
        if (!object.getConfidences().isEmpty()){
            getStreamWriter().writeStartElement("confidenceList");
            for (Confidence conf : object.getConfidences()){
                getConfidenceWriter().write(conf);
            }
            getStreamWriter().writeEndElement();
        }
    }

    @Override
    protected void writeParameters(ParticipantEvidence object) throws XMLStreamException {
        if (!object.getParameters().isEmpty()){
            getStreamWriter().writeStartElement("parameterList");
            for (Parameter param : object.getParameters()){
                getParameterWriter().write(param);
            }
            getStreamWriter().writeEndElement();
        }
    }
}
