package psidev.psi.mi.jami.xml.io.writer.elements.impl.xml25;

import psidev.psi.mi.jami.model.Range;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.abstracts.AbstractXmlRangeWriter;

import javax.xml.stream.XMLStreamWriter;

/**
 * Xml 2.5 writer for a feature range
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>13/11/13</pre>
 */

public class XmlRangeWriter extends AbstractXmlRangeWriter {

    public XmlRangeWriter(XMLStreamWriter writer){
        super(writer);
    }

    @Override
    protected void writeOtherProperties(Range object) {
        // nothing to write in XML 2.5
    }


}
