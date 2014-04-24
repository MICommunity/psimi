package psidev.psi.mi.jami.xml.io.writer.elements.impl.extended;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

/**
 * XML 2.5 writer for an extended primary Xref having a secondary property and annotations
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>13/11/13</pre>
 */

public class XmlPrimaryXrefWriter extends AbstractXmlXrefWriter {
    public XmlPrimaryXrefWriter(XMLStreamWriter writer) {
        super(writer);
    }

    @Override
    protected void writeStartDbRef() throws XMLStreamException {
        getStreamWriter().writeStartElement("primaryRef");
    }
}
