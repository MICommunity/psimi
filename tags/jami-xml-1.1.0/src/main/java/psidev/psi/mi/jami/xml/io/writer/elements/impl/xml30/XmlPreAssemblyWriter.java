package psidev.psi.mi.jami.xml.io.writer.elements.impl.xml30;

import psidev.psi.mi.jami.model.Preassembly;
import psidev.psi.mi.jami.xml.cache.PsiXmlObjectCache;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.abstracts.xml30.AbstractXmlCooperativeEffectWriter;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

/**
 * Xml 30 writer for preassembly
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>12/11/13</pre>
 */

public class XmlPreAssemblyWriter extends AbstractXmlCooperativeEffectWriter<Preassembly> {

    public XmlPreAssemblyWriter(XMLStreamWriter writer, PsiXmlObjectCache objectIndex) {
        super(writer, objectIndex);
    }

    @Override
    protected void writeOtherProperties(Preassembly object) throws XMLStreamException {
        // nothing to do
    }

    @Override
    protected void writeStartCooperativeEffect() throws XMLStreamException {
        getStreamWriter().writeStartElement("preassembly");
    }
}
