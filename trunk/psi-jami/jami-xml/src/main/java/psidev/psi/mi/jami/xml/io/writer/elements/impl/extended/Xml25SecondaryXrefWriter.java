package psidev.psi.mi.jami.xml.io.writer.elements.impl.extended;

import psidev.psi.mi.jami.model.Annotation;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXml25ElementWriter;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

/**
 * XML 2.5 writer for an extended secondary Xref having secondary and annotations.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>13/11/13</pre>
 */

public class Xml25SecondaryXrefWriter extends AbstractXml25XrefWriter {
    public Xml25SecondaryXrefWriter(XMLStreamWriter writer) {
        super(writer);
    }

    public Xml25SecondaryXrefWriter(XMLStreamWriter writer, PsiXml25ElementWriter<Annotation> annotationWriter) {
        super(writer, annotationWriter);
    }

    @Override
    protected void writeStartDbRef() throws XMLStreamException {
        getStreamWriter().writeStartElement("secondaryRef");
    }
}
