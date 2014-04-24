package psidev.psi.mi.jami.xml.io.writer.elements.impl.extended;

import psidev.psi.mi.jami.model.Annotation;
import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXmlElementWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.XmlAnnotationWriter;
import psidev.psi.mi.jami.xml.model.extension.ExtendedPsiXmlXref;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

/**
 * XML 2.5 writer for an extended PSI25Xref having secondary and annotations
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>13/11/13</pre>
 */

public abstract class AbstractXmlXrefWriter extends psidev.psi.mi.jami.xml.io.writer.elements.impl.abstracts.AbstractXmlXrefWriter {
    private PsiXmlElementWriter<Annotation> annotationWriter;

    protected AbstractXmlXrefWriter(XMLStreamWriter writer) {
        super(writer);
    }

    public PsiXmlElementWriter<Annotation> getAnnotationWriter() {
        if (this.annotationWriter == null){
            this.annotationWriter = new XmlAnnotationWriter(getStreamWriter());
        }
        return annotationWriter;
    }

    public void setAnnotationWriter(PsiXmlElementWriter<Annotation> annotationWriter) {
        this.annotationWriter = annotationWriter;
    }

    @Override
    protected void writeOtherProperties(Xref object) throws XMLStreamException {
        // write secondary and attributes
        ExtendedPsiXmlXref xmlXref = (ExtendedPsiXmlXref)object;
        // write secondary
        if (xmlXref.getSecondary() != null){
            getStreamWriter().writeAttribute("secondary", xmlXref.getSecondary());
        }
        // write attributes
        if (!xmlXref.getAnnotations().isEmpty()){
            getStreamWriter().writeStartElement("attributeList");
            for (Annotation annot : xmlXref.getAnnotations()){
                // write annotations
                getAnnotationWriter().write(annot);
            }

            // write end attributeList
            getStreamWriter().writeEndElement();
        }
    }
}
