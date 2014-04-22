package psidev.psi.mi.jami.xml.io.writer.elements.impl;

import psidev.psi.mi.jami.model.Alias;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXmlElementWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXmlXrefWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.abstracts.AbstractXmlCvTermWriter;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

/**
 * Writer for Feature detection method
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>12/11/13</pre>
 */

public class XmlFeatureDetectionMethodWriter extends AbstractXmlCvTermWriter {
    public XmlFeatureDetectionMethodWriter(XMLStreamWriter writer) {
        super(writer);
    }

    public XmlFeatureDetectionMethodWriter(XMLStreamWriter writer, PsiXmlElementWriter<Alias> aliasWriter,
                                           PsiXmlXrefWriter primaryRefWriter, PsiXmlXrefWriter secondaryRefWriter) {
        super(writer, aliasWriter, primaryRefWriter, secondaryRefWriter);
    }

    @Override
    protected void writeStartCvTerm() throws XMLStreamException {
        getStreamWriter().writeStartElement("featureDetectionMethod");
    }

    @Override
    protected void writeOtherProperties(CvTerm term) throws XMLStreamException {
        // nothing to do
    }
}
