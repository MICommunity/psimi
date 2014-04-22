package psidev.psi.mi.jami.xml.io.writer.elements.impl;

import psidev.psi.mi.jami.model.Alias;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXmlElementWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXmlXrefWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.abstracts.AbstractXmlCvTermWriter;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

/**
 * XML 2.5 writer for start range status
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>13/11/13</pre>
 */

public class XmlStartStatusWriter extends AbstractXmlCvTermWriter {
    public XmlStartStatusWriter(XMLStreamWriter writer) {
        super(writer);
    }

    public XmlStartStatusWriter(XMLStreamWriter writer, PsiXmlElementWriter<Alias> aliasWriter,
                                PsiXmlXrefWriter primaryRefWriter, PsiXmlXrefWriter secondaryRefWriter) {
        super(writer, aliasWriter, primaryRefWriter, secondaryRefWriter);
    }

    @Override
    protected void writeStartCvTerm() throws XMLStreamException {
        getStreamWriter().writeStartElement("startStatus");
    }

    @Override
    protected void writeOtherProperties(CvTerm term) throws XMLStreamException {
        // nothing to do
    }
}
