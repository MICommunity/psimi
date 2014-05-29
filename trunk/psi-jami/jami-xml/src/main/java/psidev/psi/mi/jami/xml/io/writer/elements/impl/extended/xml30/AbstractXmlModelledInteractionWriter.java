package psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.xml30;

import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Experiment;
import psidev.psi.mi.jami.model.Feature;
import psidev.psi.mi.jami.model.ModelledInteraction;
import psidev.psi.mi.jami.xml.cache.PsiXmlObjectCache;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXmlElementWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXmlExtendedInteractionWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.xml30.XmlBindingFeaturesWriter;
import psidev.psi.mi.jami.xml.model.extension.ExtendedPsiXmlInteraction;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * Abstract class for XML 3.0 writers of modelled interaction
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>18/11/13</pre>
 */

public abstract class AbstractXmlModelledInteractionWriter<I extends ModelledInteraction>
        extends psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.AbstractXmlModelledInteractionWriter<I>
        implements PsiXmlExtendedInteractionWriter<I> {

    private PsiXmlElementWriter<Set<Feature>> bindingFeaturesWriter;

    public AbstractXmlModelledInteractionWriter(XMLStreamWriter writer, PsiXmlObjectCache objectIndex) {
        super(writer, objectIndex);

    }

    @Override
    public List<Experiment> getDefaultExperiments() {
        return Collections.EMPTY_LIST;
    }

    @Override
    public void setDefaultExperiments(List<Experiment> exp) {

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
    public List<Experiment> extractDefaultExperimentsFrom(I interaction) {
        return Collections.EMPTY_LIST;
    }

    @Override
    protected void writeCooperativeEffect(I object, boolean startAttributeList) throws XMLStreamException {
        // nothing
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
    protected void initialiseExperimentWriter(){
        super.setExperimentWriter(new psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.xml30.XmlExperimentWriter(getStreamWriter(), getObjectIndex()));
    }

    @Override
    protected void writeInteractionType(I object) throws XMLStreamException {
        if (object instanceof ExtendedPsiXmlInteraction){
            ExtendedPsiXmlInteraction xmlInteraction = (ExtendedPsiXmlInteraction)object;
            if (!xmlInteraction.getInteractionTypes().isEmpty()){
                for (Object type : xmlInteraction.getInteractionTypes()){
                    getInteractionTypeWriter().write((CvTerm)type,"interactionType");
                }
            }
        }
        else{
            super.writeInteractionType(object);
        }
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
    protected void writeOtherProperties(I object) {
        // nothing to write
    }

    @Override
    protected void writeStartInteraction() throws XMLStreamException {
        getStreamWriter().writeStartElement("abstractInteraction");
    }

}
