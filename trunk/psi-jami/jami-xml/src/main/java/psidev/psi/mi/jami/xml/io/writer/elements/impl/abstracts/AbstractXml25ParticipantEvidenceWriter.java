package psidev.psi.mi.jami.xml.io.writer.elements.impl.abstracts;

import org.codehaus.stax2.XMLStreamWriter2;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.xml.PsiXml25ObjectIndex;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXml25ElementWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXml25ParameterWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXml25XrefWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.*;
import psidev.psi.mi.jami.xml.utils.PsiXml25Utils;

import javax.xml.stream.XMLStreamException;

/**
 * Abstract class for XML 2.5 writer of participant evidences
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>14/11/13</pre>
 */

public abstract class AbstractXml25ParticipantEvidenceWriter extends AbstractXml25ParticipantWriter<ParticipantEvidence, FeatureEvidence>{
    private PsiXml25ElementWriter<CvTerm> experimentalPreparationWriter;
    private PsiXml25ElementWriter<CvTerm> identificationMethodWriter;
    private PsiXml25ElementWriter<Confidence> confidenceWriter;
    private PsiXml25ElementWriter<CvTerm> experimentalRoleWriter;
    private PsiXml25ElementWriter<Organism> hostOrganismWriter;
    private PsiXml25ElementWriter<Parameter> parameterWriter;

    public AbstractXml25ParticipantEvidenceWriter(XMLStreamWriter2 writer, PsiXml25ObjectIndex objectIndex, PsiXml25ElementWriter<FeatureEvidence> featureWriter) {
        super(writer, objectIndex, featureWriter);
        this.experimentalPreparationWriter = new Xml25ExperimentalPreparationWriter(writer);
        this.identificationMethodWriter = new Xml25ParticipantIdentificationMethodWriter(writer);
        this.confidenceWriter = new Xml25ConfidenceWriter(writer);
        this.experimentalRoleWriter = new Xml25ExperimentalRoleWriter(writer);
        this.hostOrganismWriter = new Xml25HostOrganismWriter(writer);
        this.parameterWriter = new Xml25ParameterWriter(writer, objectIndex);
    }

    public AbstractXml25ParticipantEvidenceWriter(XMLStreamWriter2 writer, PsiXml25ObjectIndex objectIndex,
                                                  PsiXml25ElementWriter<Alias> aliasWriter, PsiXml25XrefWriter primaryRefWriter,
                                                  PsiXml25XrefWriter secondaryRefWriter, PsiXml25ElementWriter<CvTerm> biologicalRoleWriter,
                                                  PsiXml25ElementWriter<FeatureEvidence> featureWriter, PsiXml25ElementWriter<Annotation> attributeWriter,
                                                  PsiXml25ElementWriter<Interactor> interactorWriter, PsiXml25ElementWriter<CvTerm> experimentalPreparationWriter,
                                                  PsiXml25ElementWriter<CvTerm> identificationMethodWriter, PsiXml25ElementWriter<Confidence> confidenceWriter,
                                                  PsiXml25ElementWriter<CvTerm> experimentalRoleWriter, PsiXml25ElementWriter<Organism> hostOrganismWriter,
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
            getStreamWriter().writeCharacters(PsiXml25Utils.LINE_BREAK);
            getStreamWriter().writeStartElement("experimentalPreparationList");
            getStreamWriter().writeCharacters(PsiXml25Utils.LINE_BREAK);
            for (CvTerm prep : object.getExperimentalPreparations()){
                this.experimentalPreparationWriter.write(prep);
                getStreamWriter().writeCharacters(PsiXml25Utils.LINE_BREAK);
            }
            getStreamWriter().writeEndElement();
            getStreamWriter().writeCharacters(PsiXml25Utils.LINE_BREAK);
        }
    }

    @Override
    protected void writeExperimentalRoles(ParticipantEvidence object) throws XMLStreamException {
        getStreamWriter().writeCharacters(PsiXml25Utils.LINE_BREAK);
        getStreamWriter().writeStartElement("experimentalRoleList");
        getStreamWriter().writeCharacters(PsiXml25Utils.LINE_BREAK);
        this.experimentalRoleWriter.write(object.getExperimentalRole());
        getStreamWriter().writeCharacters(PsiXml25Utils.LINE_BREAK);
        getStreamWriter().writeEndElement();
        getStreamWriter().writeCharacters(PsiXml25Utils.LINE_BREAK);
    }

    @Override
    protected void writeParticipantIdentificationMethods(ParticipantEvidence object) throws XMLStreamException {
        if (!object.getIdentificationMethods().isEmpty()){
            getStreamWriter().writeCharacters(PsiXml25Utils.LINE_BREAK);
            getStreamWriter().writeStartElement("participantIdentificationMethodList");
            getStreamWriter().writeCharacters(PsiXml25Utils.LINE_BREAK);
            for (CvTerm method : object.getIdentificationMethods()){
                this.identificationMethodWriter.write(method);
                getStreamWriter().writeCharacters(PsiXml25Utils.LINE_BREAK);
            }
            getStreamWriter().writeEndElement();
            getStreamWriter().writeCharacters(PsiXml25Utils.LINE_BREAK);
        }
    }

    @Override
    protected void writeExperimentalInteractor(ParticipantEvidence object) throws XMLStreamException {
        // nothing to do here
    }

    @Override
    protected void writeHostOrganisms(ParticipantEvidence object) throws XMLStreamException {
        if (object.getExpressedInOrganism() != null){
            getStreamWriter().writeCharacters(PsiXml25Utils.LINE_BREAK);
            this.hostOrganismWriter.write(object.getExpressedInOrganism());
            getStreamWriter().writeCharacters(PsiXml25Utils.LINE_BREAK);
        }
    }

    @Override
    protected void writeConfidences(ParticipantEvidence object) throws XMLStreamException {
        if (!object.getConfidences().isEmpty()){
            getStreamWriter().writeCharacters(PsiXml25Utils.LINE_BREAK);
            getStreamWriter().writeStartElement("confidenceList");
            getStreamWriter().writeCharacters(PsiXml25Utils.LINE_BREAK);
            for (Confidence conf : object.getConfidences()){
                this.confidenceWriter.write(conf);
                getStreamWriter().writeCharacters(PsiXml25Utils.LINE_BREAK);
            }
            getStreamWriter().writeEndElement();
            getStreamWriter().writeCharacters(PsiXml25Utils.LINE_BREAK);
        }
    }

    @Override
    protected void writeParameters(ParticipantEvidence object) throws XMLStreamException {
        if (!object.getParameters().isEmpty()){
            getStreamWriter().writeCharacters(PsiXml25Utils.LINE_BREAK);
            getStreamWriter().writeStartElement("parameterList");
            getStreamWriter().writeCharacters(PsiXml25Utils.LINE_BREAK);
            for (Parameter param : object.getParameters()){
                this.parameterWriter.write(param);
                getStreamWriter().writeCharacters(PsiXml25Utils.LINE_BREAK);
            }
            getStreamWriter().writeEndElement();
            getStreamWriter().writeCharacters(PsiXml25Utils.LINE_BREAK);
        }
    }

    protected PsiXml25ElementWriter<CvTerm> getExperimentalRoleWriter() {
        return experimentalRoleWriter;
    }

    protected PsiXml25ElementWriter<Organism> getHostOrganismWriter() {
        return hostOrganismWriter;
    }
}
