package psidev.psi.mi.jami.xml.io.writer.elements.impl.abstracts;

import psidev.psi.mi.jami.model.Alias;
import psidev.psi.mi.jami.model.Annotation;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXmlElementWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXmlXrefWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.XmlAnnotationWriter;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

/**
 * Xml 2.5 writer for open cv terms
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>12/11/13</pre>
 */

public abstract class AbstractXmlOpenCvTermWriter extends AbstractXmlCvTermWriter {
    private PsiXmlElementWriter<Annotation> attributeWriter;

    public AbstractXmlOpenCvTermWriter(XMLStreamWriter writer) {
        super(writer);
        this.attributeWriter = new XmlAnnotationWriter(writer);
    }

    protected AbstractXmlOpenCvTermWriter(XMLStreamWriter writer, PsiXmlElementWriter<Alias> aliasWriter,
                                          PsiXmlXrefWriter primaryRefWriter, PsiXmlXrefWriter secondaryRefWriter,
                                          PsiXmlElementWriter<Annotation> attributeWriter) {
        super(writer, aliasWriter, primaryRefWriter, secondaryRefWriter);
        this.attributeWriter = attributeWriter != null ? attributeWriter : new XmlAnnotationWriter(writer);
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
