package psidev.psi.mi.jami.xml.io.writer.elements.impl;

import psidev.psi.mi.jami.exception.MIIOException;
import psidev.psi.mi.jami.model.Annotation;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXmlElementWriter;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

/**
 * XML 2.5 writer for attributes
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>11/11/13</pre>
 */

public class XmlAnnotationWriter implements PsiXmlElementWriter<Annotation> {
    private XMLStreamWriter streamWriter;

    public XmlAnnotationWriter(XMLStreamWriter writer){
        if (writer == null){
            throw new IllegalArgumentException("The XML stream writer is mandatory for the XmlAnnotationWriter");
        }
        this.streamWriter = writer;
    }
    @Override
    public void write(Annotation object) throws MIIOException {
        if (object != null){
            try {
                // write start
                this.streamWriter.writeStartElement("attribute");
                // write topic
                CvTerm topic = object.getTopic();
                this.streamWriter.writeAttribute("name", topic.getShortName());
                if (topic.getMIIdentifier() != null){
                    this.streamWriter.writeAttribute("nameAc", topic.getMIIdentifier());
                }
                // write description
                if (object.getValue() != null){
                    this.streamWriter.writeCharacters(object.getValue());
                }

                // write end attribute
                this.streamWriter.writeEndElement();

            } catch (XMLStreamException e) {
                throw new MIIOException("Impossible to write the attribute : "+object.toString(), e);
            }
        }
    }
}
