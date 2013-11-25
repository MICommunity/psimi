package psidev.psi.mi.jami.xml.io.writer.elements.impl;

import psidev.psi.mi.jami.model.Alias;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXml25ElementWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXml25XrefWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.abstracts.AbstractXml25CvTermWriter;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

/**
 * XML 2.5 writer for interaction type
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>14/11/13</pre>
 */

public class Xml25InteractionTypeWriter extends AbstractXml25CvTermWriter {
    public Xml25InteractionTypeWriter(XMLStreamWriter writer) {
        super(writer);
    }

    public Xml25InteractionTypeWriter(XMLStreamWriter writer, PsiXml25ElementWriter<Alias> aliasWriter,
                                      PsiXml25XrefWriter primaryRefWriter, PsiXml25XrefWriter secondaryRefWriter) {
        super(writer, aliasWriter, primaryRefWriter, secondaryRefWriter);
    }

    @Override
    protected void writeStartCvTerm() throws XMLStreamException {
        getStreamWriter().writeStartElement("interactionType");
    }

    @Override
    protected void writeOtherProperties(CvTerm term) throws XMLStreamException {
        // do nothing
    }
}
