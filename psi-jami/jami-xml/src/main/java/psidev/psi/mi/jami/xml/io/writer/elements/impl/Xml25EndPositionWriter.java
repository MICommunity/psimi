package psidev.psi.mi.jami.xml.io.writer.elements.impl;

import org.codehaus.stax2.XMLStreamWriter2;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXml25ElementWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.abstracts.AbstractXml25PositionWriter;

import javax.xml.stream.XMLStreamException;

/**
 * XML 2.5 writer for the end position of a range
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>13/11/13</pre>
 */

public class Xml25EndPositionWriter extends AbstractXml25PositionWriter {
    public Xml25EndPositionWriter(XMLStreamWriter2 writer) {
        super(writer, new Xml25EndStatusWriter(writer));
    }

    public Xml25EndPositionWriter(XMLStreamWriter2 writer, PsiXml25ElementWriter<CvTerm> statusWriter) {
        super(writer, statusWriter != null ? statusWriter : new Xml25EndStatusWriter(writer));
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
