package psidev.psi.mi.jami.xml.io.writer.elements.impl.extended;

import psidev.psi.mi.jami.model.Annotation;
import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.jami.xml.extension.ExtendedPsi25Xref;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXml25ElementWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.Xml25AnnotationWriter;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

/**
 * XML 2.5 writer for an extended PSI25Xref having secondary and annotations
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>13/11/13</pre>
 */

public abstract class AbstractXml25XrefWriter extends psidev.psi.mi.jami.xml.io.writer.elements.impl.abstracts.AbstractXml25XrefWriter {
    private PsiXml25ElementWriter<Annotation> annotationWriter;

    protected AbstractXml25XrefWriter(XMLStreamWriter writer) {
        super(writer);
        this.annotationWriter = new Xml25AnnotationWriter(writer);
    }

    protected AbstractXml25XrefWriter(XMLStreamWriter writer, PsiXml25ElementWriter<Annotation> annotationWriter) {
        super(writer);
        this.annotationWriter = annotationWriter != null ? annotationWriter : new Xml25AnnotationWriter(writer);
    }

    @Override
    protected void writeOtherProperties(Xref object) throws XMLStreamException {
        // write secondary and attributes
        ExtendedPsi25Xref xmlXref = (ExtendedPsi25Xref)object;
        // write secondary
        if (xmlXref.getSecondary() != null){
            getStreamWriter().writeAttribute("secondary", xmlXref.getSecondary());
        }
        // write attributes
        if (!xmlXref.getAnnotations().isEmpty()){
            getStreamWriter().writeStartElement("attributeList");
            for (Annotation annot : xmlXref.getAnnotations()){
                // write annotations
                this.annotationWriter.write(annot);
            }

            // write end attributeList
            getStreamWriter().writeEndElement();
        }
    }
}
