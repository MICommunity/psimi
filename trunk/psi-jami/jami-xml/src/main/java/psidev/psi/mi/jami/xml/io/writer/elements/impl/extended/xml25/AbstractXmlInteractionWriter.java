package psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.xml25;

import psidev.psi.mi.jami.model.Interaction;
import psidev.psi.mi.jami.xml.cache.PsiXmlObjectCache;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXmlExtendedInteractionWriter;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

/**
 * Abstract class for expanded interaction writer
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>18/11/13</pre>
 */

public abstract class AbstractXmlInteractionWriter<I extends Interaction>
        extends psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.AbstractXmlInteractionWriter<I>
        implements PsiXmlExtendedInteractionWriter<I> {

    public AbstractXmlInteractionWriter(XMLStreamWriter writer, PsiXmlObjectCache objectIndex) {
        super(writer, objectIndex);
    }

    @Override
    protected void initialiseExperimentWriter(){
        super.setExperimentWriter(new psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.xml25.XmlExperimentWriter(getStreamWriter(), getObjectIndex()));
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
        // do nothing
    }

    @Override
    protected void writeParameters(I object) {
        // nothing to do
    }

    @Override
    protected void writeConfidences(I object) {
        // nothing to do
    }

    @Override
    protected void writeNegative(I object) {
        // nothing to do
    }

    @Override
    protected void writeStartInteraction() throws XMLStreamException {
        getStreamWriter().writeStartElement("interaction");
    }
}
