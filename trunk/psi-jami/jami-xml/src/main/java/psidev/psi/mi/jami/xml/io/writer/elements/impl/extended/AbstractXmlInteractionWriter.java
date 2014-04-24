package psidev.psi.mi.jami.xml.io.writer.elements.impl.extended;

import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.xml.cache.PsiXmlObjectCache;
import psidev.psi.mi.jami.xml.model.extension.BibRef;
import psidev.psi.mi.jami.xml.model.extension.ExtendedPsiXmlInteraction;
import psidev.psi.mi.jami.xml.model.extension.InferredInteraction;
import psidev.psi.mi.jami.xml.model.extension.XmlExperiment;
import psidev.psi.mi.jami.xml.io.writer.elements.*;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.abstracts.AbstractXmlNamedInteractionWriter;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Abstract class for extended interaction writer
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>18/11/13</pre>
 */

public abstract class AbstractXmlInteractionWriter<I extends Interaction, P extends Participant> extends AbstractXmlNamedInteractionWriter<I,P> implements PsiXmlExtendedInteractionWriter<I> {

    private PsiXmlElementWriter<InferredInteraction> inferredInteractionWriter;

    public AbstractXmlInteractionWriter(XMLStreamWriter writer, PsiXmlObjectCache objectIndex) {
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

    @Override
    public List<Experiment> extractDefaultExperimentsFrom(I interaction) {
        return Collections.singletonList(getDefaultExperiment());
    }

    @Override
    protected void writeIntraMolecular(Interaction object) throws XMLStreamException {
        ExtendedPsiXmlInteraction xmlInteraction = (ExtendedPsiXmlInteraction)object;
        if (xmlInteraction.isIntraMolecular()){
            getStreamWriter().writeStartElement("intraMolecular");
            getStreamWriter().writeCharacters(Boolean.toString(xmlInteraction.isIntraMolecular()));
            // write end intra molecular
            getStreamWriter().writeEndElement();
        }
    }

    @Override
    protected void writeInteractionType(Interaction object) throws XMLStreamException {
        ExtendedPsiXmlInteraction xmlInteraction = (ExtendedPsiXmlInteraction)object;
        if (!xmlInteraction.getInteractionTypes().isEmpty()){
            for (Object type : xmlInteraction.getInteractionTypes()){
                getInteractionTypeWriter().write((CvTerm)type,"interactionType");
            }
        }
    }

    @Override
    protected void writeInferredInteractions(Interaction object) throws XMLStreamException {
        ExtendedPsiXmlInteraction xmlInteraction = (ExtendedPsiXmlInteraction)object;
        if (!xmlInteraction.getInferredInteractions().isEmpty()){
            getStreamWriter().writeStartElement("inferredInteractionList");
            for (Object inferred : xmlInteraction.getInferredInteractions()){
                getXmlInferredInteractionWriter().write((InferredInteraction)inferred);
            }
            getStreamWriter().writeEndElement();
        }
    }

    @Override
    protected void initialiseDefaultExperiment() {
        super.setDefaultExperiment(new XmlExperiment(new BibRef("Mock publication for interactions that do not have experimental details.", (String) null, (Date) null)));
    }
}
