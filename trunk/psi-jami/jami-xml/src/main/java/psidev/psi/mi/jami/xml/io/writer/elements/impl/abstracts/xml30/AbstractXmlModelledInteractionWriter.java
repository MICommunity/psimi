package psidev.psi.mi.jami.xml.io.writer.elements.impl.abstracts.xml30;

import psidev.psi.mi.jami.model.Feature;
import psidev.psi.mi.jami.model.ModelledInteraction;
import psidev.psi.mi.jami.xml.cache.PsiXmlObjectCache;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXmlElementWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.XmlCvTermWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.XmlDbXrefWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.xml30.XmlBindingFeaturesWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.xml30.XmlExperimentWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.xml30.XmlModelledConfidenceWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.xml30.XmlModelledParameterWriter;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.util.Collection;
import java.util.Set;

/**
 * Abstract class for XML 3.0 writers of modelled interaction
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>18/11/13</pre>
 */

public abstract class AbstractXmlModelledInteractionWriter<I extends ModelledInteraction>
        extends psidev.psi.mi.jami.xml.io.writer.elements.impl.abstracts.AbstractXmlModelledInteractionWriter<I> {

    private PsiXmlElementWriter<Set<Feature>> bindingFeaturesWriter;

    public AbstractXmlModelledInteractionWriter(XMLStreamWriter writer, PsiXmlObjectCache objectIndex) {
        super(writer, objectIndex);
    }

    public PsiXmlElementWriter<Set<Feature>> getBindingFeaturesWriter() {
        if (this.bindingFeaturesWriter == null){
            this.bindingFeaturesWriter = new XmlBindingFeaturesWriter(getStreamWriter(), getObjectIndex());
        }
        return bindingFeaturesWriter;
    }

    public void setBindingFeaturesWriter(PsiXmlElementWriter<Set<Feature>> bindingFeaturesWriter) {
        this.bindingFeaturesWriter = bindingFeaturesWriter;
    }

    @Override
    protected void initialiseInferredInteractionWriter() {
        // nothing to do here
    }

    @Override
    protected void initialiseXrefWriter(){
        super.setXrefWriter(new XmlDbXrefWriter(getStreamWriter()));
    }

    @Override
    protected void initialiseExperimentWriter(){
        super.setExperimentWriter(new XmlExperimentWriter(getStreamWriter(), getObjectIndex()));
    }

    @Override
    protected void initialiseConfidenceWriter(){
        super.setConfidenceWriter(new XmlModelledConfidenceWriter(getStreamWriter()));
    }

    @Override
    protected void initialiseParameterWriter(){
        super.setParameterWriter(new XmlModelledParameterWriter(getStreamWriter(), getObjectIndex()));
    }

    protected void writeCooperativeEffect(I object, boolean startAttributeList) throws XMLStreamException {
        // nothing to do here
    }

    @Override
    protected void initialiseInteractionTypeWriter() {
        super.setInteractionTypeWriter(new XmlCvTermWriter(getStreamWriter()));
    }

    @Override
    protected void writeInferredInteractions(I object) throws XMLStreamException {
        Collection<Set<Feature>> inferredInteractions = collectInferredInteractionsFrom(object);
        if (inferredInteractions != null && !inferredInteractions.isEmpty()){
            getStreamWriter().writeStartElement("bindingFeaturesList");
            for (Set<Feature> inferred : inferredInteractions){
                getBindingFeaturesWriter().write(inferred);
            }
            getStreamWriter().writeEndElement();
        }
    }

    @Override
    protected void writeOtherProperties(I object) {
        // nothing to do
    }

    @Override
    protected void writeExperimentRef() throws XMLStreamException {
        // nothing to do
    }

    @Override
    protected void writeExperimentDescription() throws XMLStreamException {
        // nothing to do
    }

    @Override
    protected void writeExperiments(I object) throws XMLStreamException {
        // nothing to write
    }

    @Override
    protected void writeStartInteraction() throws XMLStreamException {
        getStreamWriter().writeStartElement("abstractInteraction");
    }
}
