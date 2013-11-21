package psidev.psi.mi.jami.xml.io.writer.elements.impl.abstracts;

import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.xml.PsiXml25ObjectCache;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXml25ElementWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXml25ParameterWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXml25XrefWriter;
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

public abstract class AbstractXml25ParticipantEvidenceWriter extends AbstractXml25ParticipantWriter<ParticipantEvidence, FeatureEvidence>{
    private PsiXml25ElementWriter experimentalPreparationWriter;
    private PsiXml25ElementWriter identificationMethodWriter;
    private PsiXml25ElementWriter<Confidence> confidenceWriter;
    private PsiXml25ElementWriter experimentalRoleWriter;
    private PsiXml25ElementWriter<Organism> hostOrganismWriter;
    private PsiXml25ElementWriter<Parameter> parameterWriter;

    public AbstractXml25ParticipantEvidenceWriter(XMLStreamWriter writer, PsiXml25ObjectCache objectIndex, PsiXml25ElementWriter<FeatureEvidence> featureWriter) {
        super(writer, objectIndex, featureWriter);
        this.experimentalPreparationWriter = new Xml25ExperimentalPreparationWriter(writer);
        this.identificationMethodWriter = new Xml25ParticipantIdentificationMethodWriter(writer);
        this.confidenceWriter = new Xml25ConfidenceWriter(writer);
        this.experimentalRoleWriter = new Xml25ExperimentalRoleWriter(writer);
        this.hostOrganismWriter = new Xml25HostOrganismWriter(writer);
        this.parameterWriter = new Xml25ParameterWriter(writer, objectIndex);
    }

    public AbstractXml25ParticipantEvidenceWriter(XMLStreamWriter writer, PsiXml25ObjectCache objectIndex,
                                                  PsiXml25ElementWriter<Alias> aliasWriter, PsiXml25XrefWriter primaryRefWriter,
                                                  PsiXml25XrefWriter secondaryRefWriter, PsiXml25ElementWriter<CvTerm> biologicalRoleWriter,
                                                  PsiXml25ElementWriter<FeatureEvidence> featureWriter, PsiXml25ElementWriter<Annotation> attributeWriter,
                                                  PsiXml25ElementWriter<Interactor> interactorWriter, PsiXml25ElementWriter experimentalPreparationWriter,
                                                  PsiXml25ElementWriter identificationMethodWriter, PsiXml25ElementWriter<Confidence> confidenceWriter,
                                                  PsiXml25ElementWriter experimentalRoleWriter, PsiXml25ElementWriter<Organism> hostOrganismWriter,
                                                  PsiXml25ParameterWriter parameterWriter) {
        super(writer, objectIndex, aliasWriter, primaryRefWriter, secondaryRefWriter, biologicalRoleWriter, featureWriter, attributeWriter, interactorWriter);
        this.experimentalPreparationWriter = experimentalPreparationWriter != null ? experimentalPreparationWriter : new Xml25ExperimentalPreparationWriter(writer);
        this.identificationMethodWriter = identificationMethodWriter != null ? identificationMethodWriter : new Xml25ParticipantIdentificationMethodWriter(writer);
        this.confidenceWriter = confidenceWriter != null ? confidenceWriter : new Xml25ConfidenceWriter(writer);
        this.experimentalRoleWriter = experimentalRoleWriter != null ? experimentalRoleWriter : new Xml25ExperimentalRoleWriter(writer);
        this.hostOrganismWriter = hostOrganismWriter != null ? hostOrganismWriter : new Xml25HostOrganismWriter(writer);
        this.parameterWriter = parameterWriter != null ? parameterWriter : new Xml25ParameterWriter(writer, objectIndex);
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

    protected PsiXml25ElementWriter<CvTerm> getExperimentalRoleWriter() {
        return experimentalRoleWriter;
    }

    protected PsiXml25ElementWriter<Organism> getHostOrganismWriter() {
        return hostOrganismWriter;
    }
}
