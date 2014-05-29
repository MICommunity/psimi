package psidev.psi.mi.jami.xml.io.writer.elements.impl.extended;

import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.xml.cache.PsiXmlObjectCache;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXmlElementWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXmlExtendedInteractionWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.*;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.xml25.XmlExperimentWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.xml25.XmlParameterWriter;
import psidev.psi.mi.jami.xml.model.extension.ExtendedPsiXmlInteraction;
import psidev.psi.mi.jami.xml.model.extension.ExtendedPsiXmlInteractionEvidence;
import psidev.psi.mi.jami.xml.model.extension.InferredInteraction;
import psidev.psi.mi.jami.xml.utils.PsiXmlUtils;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Abstract class for interaction evidence writers that write expanded interactions (having modelled, intramolecular properties, list
 * of experiments, list of interaction types, etc.)
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>18/11/13</pre>
 */

public abstract class AbstractXmlInteractionEvidenceWriter<I extends InteractionEvidence>
        extends psidev.psi.mi.jami.xml.io.writer.elements.impl.abstracts.AbstractXmlInteractionEvidenceWriter<I>
        implements PsiXmlExtendedInteractionWriter<I> {

    private PsiXmlElementWriter<InferredInteraction> inferredInteractionWriter;
    private PsiXmlElementWriter<Alias> aliasWriter;

    public AbstractXmlInteractionEvidenceWriter(XMLStreamWriter writer, PsiXmlObjectCache objectIndex) {
        super(writer, objectIndex);

    }

    public PsiXmlElementWriter<InferredInteraction> getXmlInferredInteractionWriter() {
        if (this.inferredInteractionWriter == null){
            this.inferredInteractionWriter = new XmlInferredInteractionWriter(getStreamWriter(), getObjectIndex());
        }
        return inferredInteractionWriter;
    }

    public void setXmlInferredInteractionWriter(PsiXmlElementWriter<InferredInteraction> inferredInteractionWriter) {
        this.inferredInteractionWriter = inferredInteractionWriter;
    }

    public PsiXmlElementWriter<Alias> getAliasWriter() {
        if (this.aliasWriter == null){
            this.aliasWriter =  new XmlAliasWriter(getStreamWriter());
        }
        return aliasWriter;
    }

    public void setAliasWriter(PsiXmlElementWriter<Alias> aliasWriter) {
        this.aliasWriter = aliasWriter;
    }

    @Override
    public List<Experiment> extractDefaultExperimentsFrom(I interaction) {
        if (interaction instanceof ExtendedPsiXmlInteractionEvidence){
            List<Experiment> exp = ((ExtendedPsiXmlInteractionEvidence)interaction).getExperiments();
            return !exp.isEmpty() ? exp : Collections.singletonList(getDefaultExperiment());
        }
        else{
            return Collections.singletonList(getDefaultExperiment());
        }
    }

    @Override
    protected void initialiseInferredInteractionWriter() {
        super.setInferredInteractionWriter(new psidev.psi.mi.jami.xml.io.writer.elements.impl.XmlInferredInteractionWriter(getStreamWriter(), getObjectIndex()));
    }

    @Override
    protected void initialiseInteractionTypeWriter() {
        super.setInteractionTypeWriter(new XmlCvTermWriter(getStreamWriter()));
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
        super.setConfidenceWriter(new XmlConfidenceWriter(getStreamWriter(), getObjectIndex()));
    }

    @Override
    protected void initialiseParameterWriter(){
        super.setParameterWriter(new XmlParameterWriter(getStreamWriter(), getObjectIndex()));
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

    @Override
    protected void writeIntraMolecular(I object) throws XMLStreamException {
        if (object instanceof ExtendedPsiXmlInteraction){
            ExtendedPsiXmlInteraction xmlInteraction = (ExtendedPsiXmlInteraction)object;
            if (xmlInteraction.isIntraMolecular()){
                getStreamWriter().writeStartElement("intraMolecular");
                getStreamWriter().writeCharacters(Boolean.toString(xmlInteraction.isIntraMolecular()));
                // write end intra molecular
                getStreamWriter().writeEndElement();
            }
        }
        else{
            super.writeIntraMolecular(object);
        }
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
    protected void writeInferredInteractions(I object) throws XMLStreamException {
        if (object instanceof ExtendedPsiXmlInteraction){
            ExtendedPsiXmlInteraction xmlInteraction = (ExtendedPsiXmlInteraction)object;
            if (!xmlInteraction.getInferredInteractions().isEmpty()){
                getStreamWriter().writeStartElement("inferredInteractionList");
                for (Object inferred : xmlInteraction.getInferredInteractions()){
                    getXmlInferredInteractionWriter().write((InferredInteraction)inferred);
                }
                getStreamWriter().writeEndElement();
            }
        }
        else{
            super.writeInferredInteractions(object);
        }
    }

    protected void writeExperimentRef(I object) throws XMLStreamException {
        if (object instanceof ExtendedPsiXmlInteractionEvidence){
            ExtendedPsiXmlInteractionEvidence xmlInteraction = (ExtendedPsiXmlInteractionEvidence)object;
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
        else {
            super.writeExperimentRef();
        }
    }

    protected void writeExperimentDescription(I object) throws XMLStreamException {
        if (object instanceof ExtendedPsiXmlInteractionEvidence){
            ExtendedPsiXmlInteractionEvidence xmlInteraction = (ExtendedPsiXmlInteractionEvidence)object;
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
        else {
            super.writeExperimentDescription();
        }
    }

}
