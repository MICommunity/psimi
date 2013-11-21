package psidev.psi.mi.jami.xml.io.writer.elements.impl;

import psidev.psi.mi.jami.model.Alias;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXml25ElementWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXml25XrefWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.abstracts.AbstractXml25CvTermWriter;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

/**
 * Xml25 writer for Xml25InteractionDetectionMethod
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>12/11/13</pre>
 */

public class Xml25InteractionDetectionMethodWriter extends AbstractXml25CvTermWriter {
    public Xml25InteractionDetectionMethodWriter(XMLStreamWriter writer, PsiXml25ElementWriter<Alias> aliasWriter, PsiXml25XrefWriter primaryRefWriter, PsiXml25XrefWriter secondaryRefWriter) {
        super(writer, aliasWriter, primaryRefWriter, secondaryRefWriter);
    }

    public Xml25InteractionDetectionMethodWriter(XMLStreamWriter writer) {
        super(writer);
    }

    @Override
    protected void writeStartCvTerm() throws XMLStreamException {
        getStreamWriter().writeStartElement("interactionDetectionMethod");
    }

    @Override
    protected void writeOtherProperties(CvTerm term) throws XMLStreamException {
        // nothing to do
    }
}
