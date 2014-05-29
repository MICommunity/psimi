package psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.xml30;

import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.xml.cache.PsiXmlObjectCache;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXmlElementWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXmlExtendedInteractionWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.XmlOrganismWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.xml30.XmlBindingFeaturesWriter;
import psidev.psi.mi.jami.xml.model.extension.ExtendedPsiXmlModelledInteraction;

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
    private PsiXmlElementWriter<Organism> organismWriter;
    private PsiXmlElementWriter<Preassembly> preAssemblyWriter;
    private PsiXmlElementWriter<Allostery> allosteryWriter;

    public AbstractXmlModelledInteractionWriter(XMLStreamWriter writer, PsiXmlObjectCache objectIndex) {
        super(writer, objectIndex);

    }

    public PsiXmlElementWriter<Preassembly> getPreAssemblyWriter() {
        if (this.preAssemblyWriter == null){
            this.preAssemblyWriter = new XmlPreAssemblyWriter(getStreamWriter(), getObjectIndex());
        }
        return preAssemblyWriter;
    }

    public void setPreAssemblyWriter(PsiXmlElementWriter<Preassembly> preAssemblyWriter) {
        this.preAssemblyWriter = preAssemblyWriter;
    }

    public PsiXmlElementWriter<Allostery> getAllosteryWriter() {
        if (this.allosteryWriter == null){
            this.allosteryWriter = new XmlAllosteryWriter(getStreamWriter(), getObjectIndex());
        }
        return allosteryWriter;
    }

    public void setAllosteryWriter(PsiXmlElementWriter<Allostery> allosteryWriter) {
        this.allosteryWriter = allosteryWriter;
    }

    public PsiXmlElementWriter<Organism> getOrganismWriter() {
        if (this.organismWriter == null){
            this.organismWriter = new XmlOrganismWriter(getStreamWriter());
        }
        return organismWriter;
    }

    public void setOrganismWriter(PsiXmlElementWriter<Organism> organismWriter) {
        this.organismWriter = organismWriter;
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
    protected void initialiseConfidenceWriter(){
        super.setConfidenceWriter(new XmlModelledConfidenceWriter(getStreamWriter()));
    }

    @Override
    protected void initialiseParameterWriter(){
        super.setParameterWriter(new XmlModelledParameterWriter(getStreamWriter(), getObjectIndex()));
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
    protected void writeIntraMolecular(I object) throws XMLStreamException {
        if (object instanceof ExtendedPsiXmlModelledInteraction){
            ExtendedPsiXmlModelledInteraction xmlInteraction = (ExtendedPsiXmlModelledInteraction)object;
            if (xmlInteraction.isIntraMolecular()){
                getStreamWriter().writeStartElement("intraMolecular");
                getStreamWriter().writeCharacters(Boolean.toString(xmlInteraction.isIntraMolecular()));
                // write end intra molecular
                getStreamWriter().writeEndElement();
            }
        }
        else {
            super.writeIntraMolecular(object);
        }
    }

    @Override
    protected void writeOtherProperties(I object) throws XMLStreamException {
        if (object instanceof Complex) {
            Complex complex = (Complex) object;
            // write organism
            writeOrganism(complex);
            // write interactor type
            writeInteractorType(complex);
        }
        // write evidence type
        writeEvidenceType(object);
        // write cooperative effects
        writeCooperativeEffects(object);
    }

    protected void writeCooperativeEffects(I object) throws XMLStreamException {
        if (!object.getCooperativeEffects().isEmpty()){
            getStreamWriter().writeStartElement("cooperativeEffectList");
            for (CooperativeEffect effect : object.getCooperativeEffects()){
                // preassembly
                if (effect instanceof Preassembly){
                    getPreAssemblyWriter().write((Preassembly)effect);
                }
                // allsotery
                else if (effect instanceof Allostery){
                    getAllosteryWriter().write((Allostery)effect);
                }
                // skip the effect
            }
            getStreamWriter().writeEndElement();
        }
    }

    protected void writeEvidenceType(I object){
        if (object.getEvidenceType() != null){
            getInteractionTypeWriter().write(object.getEvidenceType(), "evidenceType");
        }
    }

    protected void writeInteractorType(Complex complex){
        if (complex.getInteractorType() != null){
            getInteractionTypeWriter().write(complex.getInteractorType(), "interactorType");
        }
    }

    protected void writeOrganism(Complex complex){
        if (complex.getOrganism() != null){
            getOrganismWriter().write(complex.getOrganism());
        }
    }

    @Override
    protected void writeStartInteraction() throws XMLStreamException {
        getStreamWriter().writeStartElement("abstractInteraction");
    }

}
