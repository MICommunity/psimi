package psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.xml30;

import javax.xml.stream.XMLStreamWriter;

/**
 * XML 3.0 writer for expanded XML source having a release description and a release date
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>13/11/13</pre>
 */

public class XmlSourceWriter extends psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.xml25.XmlSourceWriter {
    public XmlSourceWriter(XMLStreamWriter writer) {
        super(writer);
    }

    protected void initialisePublicationWriter() {
        super.setPublicationWriter(new psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.xml30.XmlPublicationWriter(getStreamWriter()));
    }
}
