package psidev.psi.mi.jami.xml.io.writer;

import org.codehaus.stax2.XMLStreamWriter2;
import psidev.psi.mi.jami.model.Annotation;

import javax.xml.stream.XMLStreamException;

/**
 * Xml 2.5 writer for SecondaryXref
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>11/11/13</pre>
 */

public class Xml25SecondaryXrefWriter extends AbstractXml25XrefWriter{

    public Xml25SecondaryXrefWriter(XMLStreamWriter2 writer, PsiXml25ElementWriter<Annotation> annotationWriter) {
        super(writer, annotationWriter);
    }

    public Xml25SecondaryXrefWriter(XMLStreamWriter2 writer) {
        super(writer);
    }

    @Override
    protected void writeStartDbRef() throws XMLStreamException {
        getStreamWriter().writeStartElement("secondaryRef");
    }
}
