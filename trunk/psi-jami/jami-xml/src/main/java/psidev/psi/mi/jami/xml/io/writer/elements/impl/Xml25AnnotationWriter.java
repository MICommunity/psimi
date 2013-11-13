package psidev.psi.mi.jami.xml.io.writer.elements.impl;

import org.codehaus.stax2.XMLStreamWriter2;
import psidev.psi.mi.jami.exception.MIIOException;
import psidev.psi.mi.jami.model.Annotation;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXml25ElementWriter;

import javax.xml.stream.XMLStreamException;

/**
 * XML 2.5 writer for attributes
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>11/11/13</pre>
 */

public class Xml25AnnotationWriter implements PsiXml25ElementWriter<Annotation> {
    private XMLStreamWriter2 streamWriter;

    public Xml25AnnotationWriter(XMLStreamWriter2 writer){
        if (writer == null){
            throw new IllegalArgumentException("The XML stream writer is mandatory for the Xml25AnnotationWriter");
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
