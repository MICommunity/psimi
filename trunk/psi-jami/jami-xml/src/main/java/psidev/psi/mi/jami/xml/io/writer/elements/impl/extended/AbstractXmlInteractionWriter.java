package psidev.psi.mi.jami.xml.io.writer.elements.impl.extended;

import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.xml.cache.PsiXmlObjectCache;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXmlElementWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXmlExtendedInteractionWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.*;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.xml25.XmlExperimentWriter;
import psidev.psi.mi.jami.xml.model.extension.ExtendedPsiXmlInteraction;
import psidev.psi.mi.jami.xml.model.extension.InferredInteraction;
import psidev.psi.mi.jami.xml.utils.PsiXmlUtils;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * Abstract class for expanded interaction writer
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>18/11/13</pre>
 */

public abstract class AbstractXmlInteractionWriter<I extends Interaction>
        extends psidev.psi.mi.jami.xml.io.writer.elements.impl.abstracts.AbstractXmlInteractionWriter<I,Participant>
        implements PsiXmlExtendedInteractionWriter<I> {

    private PsiXmlElementWriter<InferredInteraction> inferredInteractionWriter;
    private PsiXmlElementWriter<Alias> aliasWriter;

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
    protected void initialiseInferredInteractionWriter() {
        super.setInferredInteractionWriter(new psidev.psi.mi.jami.xml.io.writer.elements.impl.XmlInferredInteractionWriter(getStreamWriter(), getObjectIndex()));
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
            Collection<Set<Feature>> inferredInteractions = collectInferredInteractionsFrom(object);
            if (inferredInteractions != null && !inferredInteractions.isEmpty()){
                getStreamWriter().writeStartElement("inferredInteractionList");
                for (Set<Feature> inferred : inferredInteractions){
                    getInferredInteractionWriter().write(inferred);
                }
                getStreamWriter().writeEndElement();
            }
        }
    }
}
