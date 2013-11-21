package psidev.psi.mi.jami.xml.io.writer.elements.impl;

import psidev.psi.mi.jami.model.Alias;
import psidev.psi.mi.jami.model.Annotation;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXml25ElementWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXml25XrefWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.abstracts.AbstractXml25OpenCvTermWriter;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

/**
 * XML 2.5 compartment writer
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>12/11/13</pre>
 */

public class Xml25CompartmentWriter extends AbstractXml25OpenCvTermWriter {
    public Xml25CompartmentWriter(XMLStreamWriter writer) {
        super(writer);
    }

    public Xml25CompartmentWriter(XMLStreamWriter writer, PsiXml25ElementWriter<Alias> aliasWriter,
                                  PsiXml25XrefWriter primaryRefWriter,
                                  PsiXml25XrefWriter secondaryRefWriter, PsiXml25ElementWriter<Annotation> attributeWriter) {
        super(writer, aliasWriter, primaryRefWriter, secondaryRefWriter, attributeWriter);
    }

    @Override
    protected void writeStartCvTerm() throws XMLStreamException {
        getStreamWriter().writeStartElement("compartment");
    }
}
