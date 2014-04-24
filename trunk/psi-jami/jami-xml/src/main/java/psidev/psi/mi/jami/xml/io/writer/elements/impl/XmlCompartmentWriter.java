package psidev.psi.mi.jami.xml.io.writer.elements.impl;

import psidev.psi.mi.jami.xml.io.writer.elements.impl.abstracts.AbstractXmlOpenCvTermWriter;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

/**
 * XML 2.5 compartment writer
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>12/11/13</pre>
 */

public class XmlCompartmentWriter extends AbstractXmlOpenCvTermWriter {
    public XmlCompartmentWriter(XMLStreamWriter writer) {
        super(writer);
    }

    @Override
    protected void writeStartCvTerm() throws XMLStreamException {
        getStreamWriter().writeStartElement("compartment");
    }
}
