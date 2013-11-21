package psidev.psi.mi.jami.xml.io.writer.elements.impl;

import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXml25ElementWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.abstracts.AbstractXml25PositionWriter;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

/**
 * XML 2.5 writer for the begin position of a range
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>13/11/13</pre>
 */

public class Xml25BeginPositionWriter extends AbstractXml25PositionWriter {
    public Xml25BeginPositionWriter(XMLStreamWriter writer) {
        super(writer, new Xml25StartStatusWriter(writer));
    }

    public Xml25BeginPositionWriter(XMLStreamWriter writer, PsiXml25ElementWriter<CvTerm> statusWriter) {
        super(writer, statusWriter != null ? statusWriter : new Xml25StartStatusWriter(writer));
    }

    @Override
    protected void writeStartPositionNode() throws XMLStreamException {
        getStreamWriter().writeStartElement("begin");
    }

    @Override
    protected void writeStartIntervalNode() throws XMLStreamException {
        getStreamWriter().writeStartElement("beginInterval");
    }
}
