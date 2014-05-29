package psidev.psi.mi.jami.xml.io.writer.elements.impl;

import psidev.psi.mi.jami.model.Annotation;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXmlElementWriter;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

/**
 * Xml writer for open cv terms
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>12/11/13</pre>
 */

public class XmlOpenCvTermWriter extends XmlCvTermWriter {
    private PsiXmlElementWriter<Annotation> attributeWriter;

    public XmlOpenCvTermWriter(XMLStreamWriter writer) {
        super(writer);
    }

    public PsiXmlElementWriter<Annotation> getAttributeWriter() {
        if (this.attributeWriter == null){
            this.attributeWriter = new XmlAnnotationWriter(getStreamWriter());
        }
        return attributeWriter;
    }

    public void setAttributeWriter(PsiXmlElementWriter<Annotation> attributeWriter) {
        this.attributeWriter = attributeWriter;
    }

    @Override
    protected void writeOtherProperties(CvTerm object) throws XMLStreamException {
        // write attributes
        if (!object.getAnnotations().isEmpty()){
            // write start attribute list
            getStreamWriter().writeStartElement("attributeList");
            for (Annotation ann : object.getAnnotations()){
                getAttributeWriter().write(ann);
            }
            // write end attributeList
            getStreamWriter().writeEndElement();
        }
    }
}
