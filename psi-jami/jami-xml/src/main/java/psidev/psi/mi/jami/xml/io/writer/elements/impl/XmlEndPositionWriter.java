package psidev.psi.mi.jami.xml.io.writer.elements.impl;

import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXmlElementWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.abstracts.AbstractXmlPositionWriter;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

/**
 * XML 2.5 writer for the end position of a range
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>13/11/13</pre>
 */

public class XmlEndPositionWriter extends AbstractXmlPositionWriter {
    public XmlEndPositionWriter(XMLStreamWriter writer) {
        super(writer, new XmlEndStatusWriter(writer));
    }

    public XmlEndPositionWriter(XMLStreamWriter writer, PsiXmlElementWriter<CvTerm> statusWriter) {
        super(writer, statusWriter != null ? statusWriter : new XmlEndStatusWriter(writer));
    }

    @Override
    protected void writeStartPositionNode() throws XMLStreamException {
        getStreamWriter().writeStartElement("end");
    }

    @Override
    protected void writeStartIntervalNode() throws XMLStreamException {
        getStreamWriter().writeStartElement("endInterval");
    }
}
