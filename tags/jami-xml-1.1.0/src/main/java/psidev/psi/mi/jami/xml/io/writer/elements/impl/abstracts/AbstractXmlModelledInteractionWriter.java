package psidev.psi.mi.jami.xml.io.writer.elements.impl.abstracts;

import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.xml.cache.PsiXmlObjectCache;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXmlElementWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXmlParameterWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.XmlAliasWriter;
import psidev.psi.mi.jami.xml.model.extension.BibRef;
import psidev.psi.mi.jami.xml.model.extension.XmlExperiment;
import psidev.psi.mi.jami.xml.utils.PsiXmlUtils;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.util.Date;

/**
 * Abstract class for XML writers of modelled interaction
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>18/11/13</pre>
 */

public abstract class AbstractXmlModelledInteractionWriter<I extends ModelledInteraction> extends AbstractXmlInteractionWriter<I, ModelledParticipant> {
    private PsiXmlElementWriter<Confidence> confidenceWriter;
    private PsiXmlParameterWriter parameterWriter;
    private PsiXmlElementWriter<Alias> aliasWriter;

    public AbstractXmlModelledInteractionWriter(XMLStreamWriter writer, PsiXmlObjectCache objectIndex) {
        super(writer, objectIndex);
    }

    public PsiXmlElementWriter<Confidence> getConfidenceWriter() {
        if (this.confidenceWriter == null){
            initialiseConfidenceWriter();
        }
        return confidenceWriter;
    }

    protected abstract void initialiseConfidenceWriter();

    public PsiXmlElementWriter<Alias> getAliasWriter() {
        if (this.aliasWriter == null){
            this.aliasWriter =  new XmlAliasWriter(getStreamWriter());
        }
        return aliasWriter;
    }

    public void setAliasWriter(PsiXmlElementWriter<Alias> aliasWriter) {
        this.aliasWriter = aliasWriter;
    }

    public void setConfidenceWriter(PsiXmlElementWriter<Confidence> confidenceWriter) {
        this.confidenceWriter = confidenceWriter;
    }

    public PsiXmlParameterWriter getParameterWriter() {
        if (this.parameterWriter == null){
            initialiseParameterWriter();
        }
        return parameterWriter;
    }

    protected abstract void initialiseParameterWriter();

    public void setParameterWriter(PsiXmlParameterWriter parameterWriter) {
        this.parameterWriter = parameterWriter;
    }

    @Override
    protected void initialiseDefaultExperiment() {
        Experiment defaultExperiment = new XmlExperiment(new BibRef("Mock publication and experiment for abstract interactions that are not interaction evidences.",(String)null,(Date)null));
        setDefaultExperiment(defaultExperiment);
    }

    @Override
    public void setDefaultExperiment(Experiment defaultExperiment) {
        super.setDefaultExperiment(defaultExperiment);
    }

    @Override
    protected void writeAvailability(I object) {
        // nothing to do
    }

    @Override
    protected void writeOtherAttributes(I object) {
        // nothing to do
    }

    @Override
    protected void writeModelled(I object) {
        // nothing to do
    }

    @Override
    protected void writeParameters(I object) throws XMLStreamException {
        // write parameters
        if (!object.getModelledParameters().isEmpty()){
            // write start parameter list
            getStreamWriter().writeStartElement("parameterList");
            for (Object ann : object.getModelledParameters()){
                getParameterWriter().write((ModelledParameter)ann);
            }
            // write end parameterList
            getStreamWriter().writeEndElement();
        }
    }

    @Override
    protected void writeConfidences(I object) throws XMLStreamException {
        // write confidences
        if (!object.getModelledConfidences().isEmpty()){
            // write start confidence list
            getStreamWriter().writeStartElement("confidenceList");
            for (Object ann : object.getModelledConfidences()){
                getConfidenceWriter().write((ModelledConfidence)ann);
            }
            // write end confidenceList
            getStreamWriter().writeEndElement();
        }
    }

    @Override
    protected void writeAttributes(I object) throws XMLStreamException {
        // write attributes
        if (!object.getAnnotations().isEmpty()){
            // write start attribute list
            getStreamWriter().writeStartElement("attributeList");
            // write existing attributes
            for (Object ann : object.getAnnotations()){
                getAttributeWriter().write((Annotation) ann);
            }
            // write cooperative effect
            // can only write the FIRST cooperative effect
            if (!object.getCooperativeEffects().isEmpty()){
                writeCooperativeEffect(object, false);
            }
            // write end attributeList
            getStreamWriter().writeEndElement();
        }
        // write cooperative effects
        else if (!object.getCooperativeEffects().isEmpty()){
            // write cooperative effects
            writeCooperativeEffect(object, true);
        }
    }

    protected abstract void writeCooperativeEffect(I object, boolean startAttributeList) throws XMLStreamException;

    @Override
    protected void writeNegative(I object) {
        // nothing to do
    }

    @Override
    protected CvTerm writeExperiments(I object) throws XMLStreamException {
        // write experimental evidences
        if (!object.getCooperativeEffects().isEmpty()){
            CooperativeEffect effect = object.getCooperativeEffects().iterator().next();
            if (!effect.getCooperativityEvidences().isEmpty()){
                CooperativityEvidence evidence = effect.getCooperativityEvidences().iterator().next();
                // set first experiment as default experiment
                if (evidence.getPublication() != null){
                    NamedExperiment exp = new XmlExperiment(evidence.getPublication());
                    exp.setFullName(evidence.getPublication().getTitle());
                    setDefaultExperiment(exp);
                }
            }
        }
        return null;
    }

    @Override
    protected void writeNames(I object) throws XMLStreamException {
        if (object instanceof NamedInteraction){
            NamedInteraction namedInteraction = (NamedInteraction) object;
            // write names
            PsiXmlUtils.writeCompleteNamesElement(namedInteraction.getShortName(),
                    namedInteraction.getFullName(), namedInteraction.getAliases(), getStreamWriter(),
                    getAliasWriter());
        }
        else{
            super.writeNames(object);
        }
    }
}
