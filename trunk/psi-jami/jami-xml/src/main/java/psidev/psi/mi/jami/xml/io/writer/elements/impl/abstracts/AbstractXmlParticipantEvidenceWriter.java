package psidev.psi.mi.jami.xml.io.writer.elements.impl.abstracts;

import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.xml.cache.PsiXmlObjectCache;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXmlElementWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXmlVariableNameWriter;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

/**
 * Abstract class for writer of participant evidences
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>14/11/13</pre>
 */

public abstract class AbstractXmlParticipantEvidenceWriter
        extends AbstractXmlParticipantWriter<ParticipantEvidence, FeatureEvidence> {
    private PsiXmlVariableNameWriter<CvTerm> cvWriter;
    private PsiXmlElementWriter<Confidence> confidenceWriter;
    private PsiXmlElementWriter<Organism> hostOrganismWriter;
    private PsiXmlElementWriter<Parameter> parameterWriter;

    public AbstractXmlParticipantEvidenceWriter(XMLStreamWriter writer, PsiXmlObjectCache objectIndex) {
        super(writer, objectIndex);
    }

    public PsiXmlVariableNameWriter<CvTerm> getExperimentalCvWriter() {
        if (this.cvWriter == null){
            initialiseCvWriter();
        }
        return cvWriter;
    }

    protected abstract void initialiseCvWriter();

    public void setExperimentalCvWriter(PsiXmlVariableNameWriter<CvTerm> cvWriter) {
        this.cvWriter = cvWriter;
    }

    public PsiXmlElementWriter<Confidence> getConfidenceWriter() {
        if (this.confidenceWriter == null){
            initialiseConfidenceWriter();
        }
        return confidenceWriter;
    }

    protected abstract void initialiseConfidenceWriter();

    public void setConfidenceWriter(PsiXmlElementWriter<Confidence> confidenceWriter) {
        this.confidenceWriter = confidenceWriter;
    }

    public void setHostOrganismWriter(PsiXmlElementWriter<Organism> hostOrganismWriter) {

        this.hostOrganismWriter = hostOrganismWriter;
    }

    public PsiXmlElementWriter<Organism> getHostOrganismWriter() {
        if (this.hostOrganismWriter == null){
            initialiseHostOrganismWriter();
        }
        return hostOrganismWriter;
    }

    protected abstract void initialiseHostOrganismWriter();

    public PsiXmlElementWriter<Parameter> getParameterWriter() {
        if (this.parameterWriter == null){
            initialiseParameterWriter();
        }
        return parameterWriter;
    }

    protected abstract void initialiseParameterWriter();

    public void setParameterWriter(PsiXmlElementWriter<Parameter> parameterWriter) {
        this.parameterWriter = parameterWriter;
    }

    @Override
    protected void writeExperimentalPreparations(ParticipantEvidence object) throws XMLStreamException {
        if (!object.getExperimentalPreparations().isEmpty()){
            getStreamWriter().writeStartElement("experimentalPreparationList");
            for (CvTerm prep : object.getExperimentalPreparations()){
                getExperimentalCvWriter().write(prep, "experimentalPreparation");
            }
            getStreamWriter().writeEndElement();
        }
    }

    @Override
    protected void writeExperimentalRoles(ParticipantEvidence object) throws XMLStreamException {
        getStreamWriter().writeStartElement("experimentalRoleList");
        getExperimentalCvWriter().write(object.getExperimentalRole(), "experimentalRole");
        getStreamWriter().writeEndElement();
    }

    @Override
    protected void writeParticipantIdentificationMethods(ParticipantEvidence object) throws XMLStreamException {
        if (!object.getIdentificationMethods().isEmpty()){
            getStreamWriter().writeStartElement("participantIdentificationMethodList");
            for (CvTerm method : object.getIdentificationMethods()){
                getExperimentalCvWriter().write(method, "participantIdentificationMethod");
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
