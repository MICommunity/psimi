package psidev.psi.mi.jami.xml.io.writer.elements.impl.abstracts.xml25;

import psidev.psi.mi.jami.model.Feature;
import psidev.psi.mi.jami.model.Interaction;
import psidev.psi.mi.jami.model.Participant;
import psidev.psi.mi.jami.xml.cache.PsiXmlObjectCache;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.XmlCvTermWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.XmlDbXrefWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.XmlInferredInteractionWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.xml25.XmlExperimentWriter;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.util.Collection;
import java.util.Set;

/**
 * Abstract writer of interaction in PSI-XML 2.5
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>12/11/13</pre>
 */

public abstract class AbstractXmlInteractionWriter<T extends Interaction>
        extends psidev.psi.mi.jami.xml.io.writer.elements.impl.abstracts.AbstractXmlInteractionWriter<T,Participant> {

    public AbstractXmlInteractionWriter(XMLStreamWriter writer, PsiXmlObjectCache objectIndex){
        super(writer, objectIndex);
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
        super.setInferredInteractionWriter(new XmlInferredInteractionWriter(getStreamWriter(), getObjectIndex()));
    }

    @Override
    protected void initialiseInteractionTypeWriter() {
        super.setInteractionTypeWriter(new XmlCvTermWriter(getStreamWriter()));
    }

    protected void writeInferredInteractions(T object) throws XMLStreamException {
        Collection<Set<Feature>> inferredInteractions = collectInferredInteractionsFrom(object);
        if (inferredInteractions != null && !inferredInteractions.isEmpty()){
            getStreamWriter().writeStartElement("inferredInteractionList");
            for (Set<Feature> inferred : inferredInteractions){
                getInferredInteractionWriter().write(inferred);
            }
            getStreamWriter().writeEndElement();
        }
    }

    @Override
    protected void writeAvailability(T object) {
        // nothing to do
    }

    @Override
    protected void writeExperiments(T object) throws XMLStreamException {
        writeExperimentRef();
    }

    @Override
    protected void writeOtherAttributes(T object) {
        // nothing to do
    }

    @Override
    protected void writeIntraMolecular(T object) throws XMLStreamException{
        // nothing to do
    }

    @Override
    protected void writeModelled(T object) {
        // nothing to do
    }

    @Override
    protected void writeParameters(T object) {
        // nothing to do
    }

    @Override
    protected void writeConfidences(T object) {
        // nothing to do
    }

    @Override
    protected void writeNegative(T object) {
        // nothing to do
    }

    @Override
    protected void writeStartInteraction() throws XMLStreamException {
        getStreamWriter().writeStartElement("interaction");
    }
}
