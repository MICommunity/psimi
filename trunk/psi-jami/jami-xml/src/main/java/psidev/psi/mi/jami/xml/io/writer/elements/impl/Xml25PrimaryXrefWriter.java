package psidev.psi.mi.jami.xml.io.writer.elements.impl;

import org.codehaus.stax2.XMLStreamWriter2;
import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.abstracts.AbstractXml25XrefWriter;

import javax.xml.stream.XMLStreamException;

/**
 * XML 2.5 writer for primary ref
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>11/11/13</pre>
 */

public class Xml25PrimaryXrefWriter extends AbstractXml25XrefWriter<Xref> {

    public Xml25PrimaryXrefWriter(XMLStreamWriter2 writer) {
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
