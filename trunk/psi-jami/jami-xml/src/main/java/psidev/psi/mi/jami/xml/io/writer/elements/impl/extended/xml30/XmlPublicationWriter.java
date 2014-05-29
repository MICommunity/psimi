package psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.xml30;

import psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.XmlDbXrefWriter;

import javax.xml.stream.XMLStreamWriter;

/**
 * Xml30 writer for publications (bibref objects)
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>11/11/13</pre>
 */

public class XmlPublicationWriter extends psidev.psi.mi.jami.xml.io.writer.elements.impl.xml30.XmlPublicationWriter {

    public XmlPublicationWriter(XMLStreamWriter writer){
        super(writer);
    }

    protected void initialiseXrefWriter() {
        super.setXrefWriter(new XmlDbXrefWriter(getStreamWriter()));
    }
}
