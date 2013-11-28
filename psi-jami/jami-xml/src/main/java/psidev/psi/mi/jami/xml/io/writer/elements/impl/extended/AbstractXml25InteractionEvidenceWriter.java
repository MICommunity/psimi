package psidev.psi.mi.jami.xml.io.writer.elements.impl.extended;

import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.xml.PsiXml25ObjectCache;
import psidev.psi.mi.jami.xml.extension.BibRef;
import psidev.psi.mi.jami.xml.extension.ExtendedPsi25InteractionEvidence;
import psidev.psi.mi.jami.xml.extension.InferredInteraction;
import psidev.psi.mi.jami.xml.extension.XmlExperiment;
import psidev.psi.mi.jami.xml.io.writer.elements.*;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.Xml25AvailabilityWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.Xml25ConfidenceWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.Xml25ParameterWriter;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.util.Collections;
import java.util.Date;
import java.util.List;

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

    public AbstractXml25InteractionEvidenceWriter(XMLStreamWriter writer, PsiXml25ObjectCache objectIndex, PsiXml25ParticipantWriter<P> participantWriter) {
        super(writer, objectIndex, participantWriter);
        this.availabilityWriter = new Xml25AvailabilityWriter(writer, objectIndex);
        this.confidenceWriter = new Xml25ConfidenceWriter(writer);
        this.parameterWriter = new Xml25ParameterWriter(writer, objectIndex);
    }

    public AbstractXml25InteractionEvidenceWriter(XMLStreamWriter writer, PsiXml25ObjectCache objectIndex,
                                                  PsiXml25ElementWriter<Alias> aliasWriter, PsiXml25XrefWriter primaryRefWriter,
                                                  PsiXml25XrefWriter secondaryRefWriter, PsiXml25ElementWriter<String> availabilityWriter,
                                                  PsiXml25ExperimentWriter experimentWriter, PsiXml25ParticipantWriter<P> participantWriter,
                                                  PsiXml25ElementWriter<InferredInteraction> inferredInteractionWriter1, PsiXml25ElementWriter<CvTerm> interactionTypeWriter,
                                                  PsiXml25ElementWriter<Confidence> confidenceWriter, PsiXml25ParameterWriter parameterWriter,
                                                  PsiXml25ElementWriter<Annotation> attributeWriter, PsiXml25ElementWriter<Checksum> checksumWriter) {
        super(writer, objectIndex, aliasWriter, primaryRefWriter, secondaryRefWriter, experimentWriter, participantWriter,
                inferredInteractionWriter1, interactionTypeWriter, attributeWriter, checksumWriter);
        this.availabilityWriter = availabilityWriter != null ? availabilityWriter : new Xml25AvailabilityWriter(writer, objectIndex);
        this.confidenceWriter = confidenceWriter != null ? confidenceWriter : new Xml25ConfidenceWriter(writer);
        this.parameterWriter = parameterWriter != null ? parameterWriter : new Xml25ParameterWriter(writer, objectIndex);
    }

    @Override
    protected void initialiseDefaultExperiment() {
        super.setDefaultExperiment(new XmlExperiment(new BibRef("Mock publication for interactions that do not have experimental details.", (String) null, (Date) null)));
        this.parameterWriter.setDefaultExperiment(getDefaultExperiment());
    }

    @Override
    public void setDefaultExperiment(Experiment defaultExperiment) {
        super.setDefaultExperiment(defaultExperiment);
        this.parameterWriter.setDefaultExperiment(defaultExperiment);
    }

    @Override
    public Experiment extractDefaultExperimentFrom(I interaction) {
        Experiment exp = interaction.getExperiment();
        return exp != null ? exp : getDefaultExperiment() ;
    }

    @Override
    public List<Experiment> extractDefaultExperimentsFrom(I interaction) {
        List<Experiment> exp = ((ExtendedPsi25InteractionEvidence)interaction).getExperiments();
        return !exp.isEmpty() ? exp : Collections.singletonList(getDefaultExperiment());
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
            getStreamWriter().writeStartElement("experimentList");
            for (Experiment evidence : xmlInteraction.getExperiments()){
                getStreamWriter().writeStartElement("experimentRef");
                getStreamWriter().writeCharacters(Integer.toString(getObjectIndex().extractIdForExperiment(evidence)));
                getStreamWriter().writeEndElement();
            }
            // write end experiment list
            getStreamWriter().writeEndElement();
        }
        else {
            super.writeExperimentRef();
        }
    }

    protected void writeExperimentDescription(I object) throws XMLStreamException {
        ExtendedPsi25InteractionEvidence xmlInteraction = (ExtendedPsi25InteractionEvidence)object;
        // write experimental evidences
        if (!xmlInteraction.getExperiments().isEmpty()){
            getStreamWriter().writeStartElement("experimentList");
            for (Experiment evidence : xmlInteraction.getExperiments()){
                getExperimentWriter().write(evidence);
            }
            // write end experiment list
            getStreamWriter().writeEndElement();
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
            getStreamWriter().writeStartElement("modelled");
            getStreamWriter().writeCharacters(Boolean.toString(xmlInteraction.isModelled()));
            // write end modelled
            getStreamWriter().writeEndElement();
        }
    }

    @Override
    protected void writeParameters(I object) throws XMLStreamException {
        // write parameters
        if (!object.getParameters().isEmpty()){
            // write start parameter list
            getStreamWriter().writeStartElement("parameterList");
            for (Object ann : object.getParameters()){
                this.parameterWriter.write((Parameter)ann);
            }
            // write end parameterList
            getStreamWriter().writeEndElement();
        }
    }

    @Override
    protected void writeConfidences(I object) throws XMLStreamException {
        // write confidences
        if (!object.getConfidences().isEmpty()){
            // write start confidence list
            getStreamWriter().writeStartElement("confidenceList");
            for (Object ann : object.getConfidences()){
                this.confidenceWriter.write((Confidence)ann);
            }
            // write end confidenceList
            getStreamWriter().writeEndElement();
        }
    }

    @Override
    protected void writeNegative(I object) throws XMLStreamException {
        if (object.isNegative()){
            getStreamWriter().writeStartElement("negative");
            getStreamWriter().writeCharacters(Boolean.toString(object.isNegative()));
            // write end negative
            getStreamWriter().writeEndElement();
        }
    }

    protected void writeAvailabilityRef(String availability) throws XMLStreamException {
        getStreamWriter().writeStartElement("availabilityRef");
        getStreamWriter().writeCharacters(Integer.toString(getObjectIndex().extractIdForAvailability(availability)));
        getStreamWriter().writeEndElement();
    }

    protected void writeAvailabilityDescription(String availability) throws XMLStreamException {
        this.availabilityWriter.write(availability);
    }
}
