package psidev.psi.mi.jami.xml.io.writer.elements.impl.extended;

import org.codehaus.stax2.XMLStreamWriter2;
import psidev.psi.mi.jami.model.Annotation;
import psidev.psi.mi.jami.xml.extension.ExtendedPsi25Xref;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXml25ElementWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.Xml25AnnotationWriter;
import psidev.psi.mi.jami.xml.utils.PsiXml25Utils;

import javax.xml.stream.XMLStreamException;

/**
 * XML 2.5 writer for an extended PSI25Xref having secondary and annotations
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>13/11/13</pre>
 */

public abstract class AbstractXml25XrefWriter extends psidev.psi.mi.jami.xml.io.writer.elements.impl.abstracts.AbstractXml25XrefWriter<ExtendedPsi25Xref> {
    private PsiXml25ElementWriter<Annotation> annotationWriter;

    protected AbstractXml25XrefWriter(XMLStreamWriter2 writer) {
        super(writer);
        this.annotationWriter = new Xml25AnnotationWriter(writer);
    }

    protected AbstractXml25XrefWriter(XMLStreamWriter2 writer, PsiXml25ElementWriter<Annotation> annotationWriter) {
        super(writer);
        this.annotationWriter = annotationWriter != null ? annotationWriter : new Xml25AnnotationWriter(writer);
    }

    @Override
    protected void writeOtherProperties(ExtendedPsi25Xref xmlXref) throws XMLStreamException {
        // write secondary and attributes

        // write secondary
        if (xmlXref.getSecondary() != null){
            getStreamWriter().writeAttribute("secondary", xmlXref.getSecondary());
        }
        // write attributes
        if (!xmlXref.getAnnotations().isEmpty()){
            getStreamWriter().writeCharacters(PsiXml25Utils.LINE_BREAK);
            getStreamWriter().writeStartElement("attributeList");
            getStreamWriter().writeCharacters(PsiXml25Utils.LINE_BREAK);
            for (Annotation annot : xmlXref.getAnnotations()){
                // write annotations
                this.annotationWriter.write(annot);
                getStreamWriter().writeCharacters(PsiXml25Utils.LINE_BREAK);
            }

            // write end attributeList
            getStreamWriter().writeEndElement();
            getStreamWriter().writeCharacters(PsiXml25Utils.LINE_BREAK);
        }
    }
}
