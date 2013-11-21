package psidev.psi.mi.jami.xml.io.writer.elements.impl.abstracts;

import psidev.psi.mi.jami.model.Alias;
import psidev.psi.mi.jami.model.Annotation;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXml25ElementWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXml25XrefWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.Xml25AnnotationWriter;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

/**
 * Xml 2.5 writer for open cv terms
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>12/11/13</pre>
 */

public abstract class AbstractXml25OpenCvTermWriter extends AbstractXml25CvTermWriter{
    private PsiXml25ElementWriter<Annotation> attributeWriter;

    public AbstractXml25OpenCvTermWriter(XMLStreamWriter writer) {
        super(writer);
        this.attributeWriter = new Xml25AnnotationWriter(writer);
    }

    protected AbstractXml25OpenCvTermWriter(XMLStreamWriter writer, PsiXml25ElementWriter<Alias> aliasWriter,
                                            PsiXml25XrefWriter primaryRefWriter, PsiXml25XrefWriter secondaryRefWriter,
                                            PsiXml25ElementWriter<Annotation> attributeWriter) {
        super(writer, aliasWriter, primaryRefWriter, secondaryRefWriter);
        this.attributeWriter = attributeWriter != null ? attributeWriter : new Xml25AnnotationWriter(writer);
    }

    @Override
    protected void writeOtherProperties(CvTerm object) throws XMLStreamException {
        // write attributes
        if (!object.getAnnotations().isEmpty()){
            // write start attribute list
            getStreamWriter().writeStartElement("attributeList");
            for (Annotation ann : object.getAnnotations()){
                this.attributeWriter.write(ann);
            }
            // write end attributeList
            getStreamWriter().writeEndElement();
        }
    }
}
