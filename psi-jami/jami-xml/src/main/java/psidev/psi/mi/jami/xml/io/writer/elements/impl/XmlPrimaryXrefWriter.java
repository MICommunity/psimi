package psidev.psi.mi.jami.xml.io.writer.elements.impl;

import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.abstracts.AbstractXmlXrefWriter;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

/**
 * XML 2.5 writer for primary ref
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>11/11/13</pre>
 */

public class XmlPrimaryXrefWriter extends AbstractXmlXrefWriter {

    public XmlPrimaryXrefWriter(XMLStreamWriter writer) {
        super(writer);
    }

    @Override
    protected void writeOtherProperties(Xref object) throws XMLStreamException {
        // nothing to do
    }

    @Override
    protected void writeStartDbRef() throws XMLStreamException {
        getStreamWriter().writeStartElement("primaryRef");
    }
}
