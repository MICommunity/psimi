package psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.xml30;

import psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.XmlCvTermWriter;

import javax.xml.stream.XMLStreamWriter;

/**
 * Xml 30 writer for cooperativity evidence
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>12/11/13</pre>
 */

public class XmlCooperativityEvidenceWriter extends psidev.psi.mi.jami.xml.io.writer.elements.impl.xml30.XmlCooperativityEvidenceWriter {

    public XmlCooperativityEvidenceWriter(XMLStreamWriter writer){
        super(writer);
    }

    @Override
    protected void initialisePublicationWriter() {
        super.setPublicationWriter(new XmlPublicationWriter(getStreamWriter()));
    }

    @Override
    protected void initialiseCvWriter() {
        super.setCvWriter(new XmlCvTermWriter(getStreamWriter()));
    }
}
