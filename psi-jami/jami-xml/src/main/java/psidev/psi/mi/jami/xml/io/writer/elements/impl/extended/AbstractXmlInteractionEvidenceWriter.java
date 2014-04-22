package psidev.psi.mi.jami.xml.io.writer.elements.impl.extended;

import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.xml.cache.PsiXmlObjectCache;
import psidev.psi.mi.jami.xml.model.extension.BibRef;
import psidev.psi.mi.jami.xml.model.extension.ExtendedPsi25InteractionEvidence;
import psidev.psi.mi.jami.xml.model.extension.InferredInteraction;
import psidev.psi.mi.jami.xml.model.extension.XmlExperiment;
import psidev.psi.mi.jami.xml.io.writer.elements.*;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.XmlAvailabilityWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.XmlConfidenceWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.XmlParameterWriter;

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

public abstract class AbstractXmlInteractionEvidenceWriter<I extends InteractionEvidence, P extends ParticipantEvidence> extends AbstractXmlInteractionWriter<I,P> {
    private PsiXmlElementWriter<String> availabilityWriter;
    private PsiXmlElementWriter<Confidence> confidenceWriter;
    private PsiXmlParameterWriter parameterWriter;

    public AbstractXmlInteractionEvidenceWriter(XMLStreamWriter writer, PsiXmlObjectCache objectIndex, PsiXmlParticipantWriter<P> participantWriter) {
        super(writer, objectIndex, participantWriter);
        this.availabilityWriter = new XmlAvailabilityWriter(writer, objectIndex);
        this.confidenceWriter = new XmlConfidenceWriter(writer);
        this.parameterWriter = new XmlParameterWriter(writer, objectIndex);
    }

    public AbstractXmlInteractionEvidenceWriter(XMLStreamWriter writer, PsiXmlObjectCache objectIndex,
                                                PsiXmlElementWriter<Alias> aliasWriter, PsiXmlXrefWriter primaryRefWriter,
                                                PsiXmlXrefWriter secondaryRefWriter, PsiXmlElementWriter<String> availabilityWriter,
                                                PsiXmlExperimentWriter experimentWriter, PsiXmlParticipantWriter<P> participantWriter,
                                                PsiXmlElementWriter<InferredInteraction> inferredInteractionWriter1, PsiXmlElementWriter<CvTerm> interactionTypeWriter,
                                                PsiXmlElementWriter<Confidence> confidenceWriter, PsiXmlParameterWriter parameterWriter,
                                                PsiXmlElementWriter<Annotation> attributeWriter, PsiXmlElementWriter<Checksum> checksumWriter) {
        super(writer, objectIndex, aliasWriter, primaryRefWriter, secondaryRefWriter, experimentWriter, participantWriter,
                inferredInteractionWriter1, interactionTypeWriter, attributeWriter, checksumWriter);
        this.availabilityWriter = availabilityWriter != null ? availabilityWriter : new XmlAvailabilityWriter(writer, objectIndex);
        this.confidenceWriter = confidenceWriter != null ? confidenceWriter : new XmlConfidenceWriter(writer);
        this.parameterWriter = parameterWriter != null ? parameterWriter : new XmlParameterWriter(writer, objectIndex);
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
