package psidev.psi.mi.jami.xml.io.writer.elements.impl.extended;

import javax.xml.stream.XMLStreamWriter;

/**
 * PSI-XML writer for organism
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>12/11/13</pre>
 */

public class XmlOrganismWriter extends psidev.psi.mi.jami.xml.io.writer.elements.impl.XmlOrganismWriter {
    public XmlOrganismWriter(XMLStreamWriter writer) {
        super(writer);
    }

    @Override
    protected void initialiseCvWriter() {
        super.setCvWriter(new XmlOpenCvTermWriter(getStreamWriter()));
    }
}
