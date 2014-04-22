package psidev.psi.mi.jami.xml.io.writer.elements.impl.abstracts;

import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.xml.cache.PsiXmlObjectCache;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXmlElementWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXmlParameterWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXmlXrefWriter;
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

    public AbstractXmlParticipantEvidenceWriter(XMLStreamWriter writer, PsiXmlObjectCache objectIndex, PsiXmlElementWriter<FeatureEvidence> featureWriter) {
        super(writer, objectIndex, featureWriter);
        this.experimentalPreparationWriter = new XmlExperimentalPreparationWriter(writer);
        this.identificationMethodWriter = new XmlParticipantIdentificationMethodWriter(writer);
        this.confidenceWriter = new XmlConfidenceWriter(writer);
        this.experimentalRoleWriter = new XmlExperimentalRoleWriter(writer);
        this.hostOrganismWriter = new XmlHostOrganismWriter(writer);
        this.parameterWriter = new XmlParameterWriter(writer, objectIndex);
    }

    public AbstractXmlParticipantEvidenceWriter(XMLStreamWriter writer, PsiXmlObjectCache objectIndex,
                                                PsiXmlElementWriter<Alias> aliasWriter, PsiXmlXrefWriter primaryRefWriter,
                                                PsiXmlXrefWriter secondaryRefWriter, PsiXmlElementWriter<Interactor> interactorWriter,
                                                PsiXmlElementWriter identificationMethodWriter, PsiXmlElementWriter<CvTerm> biologicalRoleWriter,
                                                PsiXmlElementWriter experimentalRoleWriter, PsiXmlElementWriter experimentalPreparationWriter,
                                                PsiXmlElementWriter<FeatureEvidence> featureWriter, PsiXmlElementWriter<Organism> hostOrganismWriter,
                                                PsiXmlParameterWriter parameterWriter, PsiXmlElementWriter<Confidence> confidenceWriter,
                                                PsiXmlElementWriter<Annotation> attributeWriter) {
        super(writer, objectIndex, aliasWriter, primaryRefWriter, secondaryRefWriter, interactorWriter, biologicalRoleWriter, featureWriter, attributeWriter);
        this.experimentalPreparationWriter = experimentalPreparationWriter != null ? experimentalPreparationWriter : new XmlExperimentalPreparationWriter(writer);
        this.identificationMethodWriter = identificationMethodWriter != null ? identificationMethodWriter : new XmlParticipantIdentificationMethodWriter(writer);
        this.confidenceWriter = confidenceWriter != null ? confidenceWriter : new XmlConfidenceWriter(writer);
        this.experimentalRoleWriter = experimentalRoleWriter != null ? experimentalRoleWriter : new XmlExperimentalRoleWriter(writer);
        this.hostOrganismWriter = hostOrganismWriter != null ? hostOrganismWriter : new XmlHostOrganismWriter(writer);
        this.parameterWriter = parameterWriter != null ? parameterWriter : new XmlParameterWriter(writer, objectIndex);
    }

    @Override
    protected void writeExperimentalPreparations(ParticipantEvidence object) throws XMLStreamException {
        if (!object.getExperimentalPreparations().isEmpty()){
            getStreamWriter().writeStartElement("experimentalPreparationList");
            for (CvTerm prep : object.getExperimentalPreparations()){
                this.experimentalPreparationWriter.write(prep);
            }
            getStreamWriter().writeEndElement();
        }
    }

    @Override
    protected void writeExperimentalRoles(ParticipantEvidence object) throws XMLStreamException {
        getStreamWriter().writeStartElement("experimentalRoleList");
        this.experimentalRoleWriter.write(object.getExperimentalRole());
        getStreamWriter().writeEndElement();
    }

    @Override
    protected void writeParticipantIdentificationMethods(ParticipantEvidence object) throws XMLStreamException {
        if (!object.getIdentificationMethods().isEmpty()){
            getStreamWriter().writeStartElement("participantIdentificationMethodList");
            for (CvTerm method : object.getIdentificationMethods()){
                this.identificationMethodWriter.write(method);
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
            this.hostOrganismWriter.write(object.getExpressedInOrganism());
            getStreamWriter().writeEndElement();
        }
    }

    @Override
    protected void writeConfidences(ParticipantEvidence object) throws XMLStreamException {
        if (!object.getConfidences().isEmpty()){
            getStreamWriter().writeStartElement("confidenceList");
            for (Confidence conf : object.getConfidences()){
                this.confidenceWriter.write(conf);
            }
            getStreamWriter().writeEndElement();
        }
    }

    @Override
    protected void writeParameters(ParticipantEvidence object) throws XMLStreamException {
        if (!object.getParameters().isEmpty()){
            getStreamWriter().writeStartElement("parameterList");
            for (Parameter param : object.getParameters()){
                this.parameterWriter.write(param);
            }
            getStreamWriter().writeEndElement();
        }
    }

    protected PsiXmlElementWriter<CvTerm> getExperimentalRoleWriter() {
        return experimentalRoleWriter;
    }

    protected PsiXmlElementWriter<Organism> getHostOrganismWriter() {
        return hostOrganismWriter;
    }
}
