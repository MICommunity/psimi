package psidev.psi.mi.jami.xml.io.writer.elements.impl.extended;

import org.codehaus.stax2.XMLStreamWriter2;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.xml.PsiXml25ObjectCache;
import psidev.psi.mi.jami.xml.extension.ExtendedPsi25InteractionEvidence;
import psidev.psi.mi.jami.xml.extension.InferredInteraction;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXml25ElementWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXml25ParameterWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXml25XrefWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.Xml25AvailabilityWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.Xml25ConfidenceWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.Xml25ParameterWriter;
import psidev.psi.mi.jami.xml.utils.PsiXml25Utils;

import javax.xml.stream.XMLStreamException;

/**
 * Abstract class for interaction evidence writers that write extended interactions (having modelled, intramolecular properties, list
 * of experiments, list of interaction types, etc.)
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>18/11/13</pre>
 */

public abstract class AbstractXml25InteractionEvidenceWriter<I extends InteractionEvidence, P extends ParticipantEvidence> extends AbstractXml25InteractionWriter<I,P> {
    private PsiXml25ElementWriter<String> availabilityWriter;
    private PsiXml25ElementWriter<Confidence> confidenceWriter;
    private PsiXml25ParameterWriter parameterWriter;

    public AbstractXml25InteractionEvidenceWriter(XMLStreamWriter2 writer, PsiXml25ObjectCache objectIndex, PsiXml25ElementWriter<P> participantWriter) {
        super(writer, objectIndex, participantWriter);
        this.availabilityWriter = new Xml25AvailabilityWriter(writer, objectIndex);
        this.confidenceWriter = new Xml25ConfidenceWriter(writer);
        this.parameterWriter = new Xml25ParameterWriter(writer, objectIndex);
    }

    protected AbstractXml25InteractionEvidenceWriter(XMLStreamWriter2 writer, PsiXml25ObjectCache objectIndex, PsiXml25XrefWriter primaryRefWriter, PsiXml25XrefWriter secondaryRefWriter, PsiXml25ElementWriter<P> participantWriter, PsiXml25ElementWriter<CvTerm> interactionTypeWriter, PsiXml25ElementWriter<Annotation> attributeWriter, PsiXml25ElementWriter<Experiment> experimentWriter, PsiXml25ElementWriter<Alias> aliasWriter, PsiXml25ElementWriter<InferredInteraction> inferredInteractionWriter1, PsiXml25ElementWriter<String> availabilityWriter, PsiXml25ElementWriter<Confidence> confidenceWriter, PsiXml25ParameterWriter parameterWriter) {
        super(writer, objectIndex, primaryRefWriter, secondaryRefWriter, participantWriter, interactionTypeWriter, attributeWriter, experimentWriter, aliasWriter, inferredInteractionWriter1);
        this.availabilityWriter = availabilityWriter != null ? availabilityWriter : new Xml25AvailabilityWriter(writer, objectIndex);
        this.confidenceWriter = confidenceWriter != null ? confidenceWriter : new Xml25ConfidenceWriter(writer);
        this.parameterWriter = parameterWriter != null ? parameterWriter : new Xml25ParameterWriter(writer, objectIndex);
    }

    @Override
    protected void initialiseDefaultExperiment() {
        super.initialiseDefaultExperiment();
        this.parameterWriter.setDefaultExperiment(getDefaultExperiment());
    }

    @Override
    public void setDefaultExperiment(Experiment defaultExperiment) {
        super.setDefaultExperiment(defaultExperiment);
        this.parameterWriter.setDefaultExperiment(defaultExperiment);
    }

    @Override
    protected void writeExperiments(I object) throws XMLStreamException {
        if (object.getExperiment() != null){
            setDefaultExperiment(object.getExperiment());
        }
    }

    protected void writeExperimentRef(I object) throws XMLStreamException {
        ExtendedPsi25InteractionEvidence xmlInteraction = (ExtendedPsi25InteractionEvidence)object;
        // write experimental evidences
        if (!xmlInteraction.getExperiments().isEmpty()){
            getStreamWriter().writeCharacters(PsiXml25Utils.LINE_BREAK);
            getStreamWriter().writeStartElement("experimentList");
            for (Experiment evidence : xmlInteraction.getExperiments()){
                getStreamWriter().writeCharacters(PsiXml25Utils.LINE_BREAK);
                getStreamWriter().writeStartElement("experimentRef");
                getStreamWriter().writeCharacters(Integer.toString(getObjectIndex().extractIdForExperiment(evidence)));
                getStreamWriter().writeEndElement();
                getStreamWriter().writeCharacters(PsiXml25Utils.LINE_BREAK);
            }
            // write end experiment list
            getStreamWriter().writeEndElement();
            getStreamWriter().writeCharacters(PsiXml25Utils.LINE_BREAK);
        }
        else {
            super.writeExperimentRef();
        }
    }

    protected void writeExperimentDescription(I object) throws XMLStreamException {
        ExtendedPsi25InteractionEvidence xmlInteraction = (ExtendedPsi25InteractionEvidence)object;
        // write experimental evidences
        if (!xmlInteraction.getExperiments().isEmpty()){
            getStreamWriter().writeCharacters(PsiXml25Utils.LINE_BREAK);
            getStreamWriter().writeStartElement("experimentList");
            for (Experiment evidence : xmlInteraction.getExperiments()){
                getExperimentWriter().write(evidence);
                getStreamWriter().writeCharacters(PsiXml25Utils.LINE_BREAK);
            }
            // write end experiment list
            getStreamWriter().writeEndElement();
            getStreamWriter().writeCharacters(PsiXml25Utils.LINE_BREAK);
        }
        else {
            super.writeExperimentDescription();
        }
    }

    @Override
    protected void writeOtherAttributes(I object) throws XMLStreamException {
        // write IMEx id
        if (object.getImexId() != null){
            getStreamWriter().writeAttribute("imexId", object.getImexId());
        }
    }

    @Override
    protected void writeModelled(I object) throws XMLStreamException {
        ExtendedPsi25InteractionEvidence xmlInteraction = (ExtendedPsi25InteractionEvidence)object;
        if (xmlInteraction.isModelled()){
            getStreamWriter().writeCharacters(PsiXml25Utils.LINE_BREAK);
            getStreamWriter().writeStartElement("modelled");
            getStreamWriter().writeCharacters(Boolean.toString(xmlInteraction.isModelled()));
            // write end modelled
            getStreamWriter().writeEndElement();
            getStreamWriter().writeCharacters(PsiXml25Utils.LINE_BREAK);
        }
    }

    @Override
    protected void writeParameters(I object) throws XMLStreamException {
        // write parameters
        if (!object.getParameters().isEmpty()){
            getStreamWriter().writeCharacters(PsiXml25Utils.LINE_BREAK);
            // write start parameter list
            getStreamWriter().writeStartElement("parameterList");
            getStreamWriter().writeCharacters(PsiXml25Utils.LINE_BREAK);
            for (Object ann : object.getParameters()){
                this.parameterWriter.write((Parameter)ann);
                getStreamWriter().writeCharacters(PsiXml25Utils.LINE_BREAK);
            }
            // write end parameterList
            getStreamWriter().writeEndElement();
            getStreamWriter().writeCharacters(PsiXml25Utils.LINE_BREAK);
        }
    }

    @Override
    protected void writeConfidences(I object) throws XMLStreamException {
        // write confidences
        if (!object.getConfidences().isEmpty()){
            getStreamWriter().writeCharacters(PsiXml25Utils.LINE_BREAK);
            // write start confidence list
            getStreamWriter().writeStartElement("confidenceList");
            getStreamWriter().writeCharacters(PsiXml25Utils.LINE_BREAK);
            for (Object ann : object.getConfidences()){
                this.confidenceWriter.write((Confidence)ann);
                getStreamWriter().writeCharacters(PsiXml25Utils.LINE_BREAK);
            }
            // write end confidenceList
            getStreamWriter().writeEndElement();
            getStreamWriter().writeCharacters(PsiXml25Utils.LINE_BREAK);
        }
    }

    @Override
    protected void writeNegative(I object) throws XMLStreamException {
        if (object.isNegative()){
            getStreamWriter().writeCharacters(PsiXml25Utils.LINE_BREAK);
            getStreamWriter().writeStartElement("negative");
            getStreamWriter().writeCharacters(Boolean.toString(object.isNegative()));
            // write end negative
            getStreamWriter().writeEndElement();
            getStreamWriter().writeCharacters(PsiXml25Utils.LINE_BREAK);
        }
    }

    protected void writeAvailabilityRef(String availability) throws XMLStreamException {
        getStreamWriter().writeCharacters(PsiXml25Utils.LINE_BREAK);
        getStreamWriter().writeStartElement("availabilityRef");
        getStreamWriter().writeCharacters(Integer.toString(getObjectIndex().extractIdForAvailability(availability)));
        getStreamWriter().writeEndElement();
        getStreamWriter().writeCharacters(PsiXml25Utils.LINE_BREAK);
    }

    protected void writeAvailabilityDescription(String availability) throws XMLStreamException {
        getStreamWriter().writeCharacters(PsiXml25Utils.LINE_BREAK);
        this.availabilityWriter.write(availability);
        getStreamWriter().writeCharacters(PsiXml25Utils.LINE_BREAK);
    }
}
