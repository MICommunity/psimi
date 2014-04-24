package psidev.psi.mi.jami.xml.io.writer.elements.impl;

import psidev.psi.mi.jami.exception.MIIOException;
import psidev.psi.mi.jami.model.Confidence;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXmlVariableNameWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXmlElementWriter;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

/**
 * Xml25 writer for confidences
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>12/11/13</pre>
 */

public class XmlConfidenceWriter implements PsiXmlElementWriter<Confidence> {
    private XMLStreamWriter streamWriter;
    private PsiXmlVariableNameWriter<CvTerm> typeWriter;

    public XmlConfidenceWriter(XMLStreamWriter writer){
        if (writer == null){
            throw new IllegalArgumentException("The XML stream writer is mandatory for the XmlConfidenceWriter");
        }
        this.streamWriter = writer;
    }

    public PsiXmlVariableNameWriter<CvTerm> getTypeWriter() {
        if (this.typeWriter == null){
            this.typeWriter = new XmlOpenCvTermWriter(streamWriter);

        }
        return typeWriter;
    }

    public void setTypeWriter(PsiXmlVariableNameWriter<CvTerm> typeWriter) {
        this.typeWriter = typeWriter;
    }

    @Override
    public void write(Confidence object) throws MIIOException {
        if (object != null){
            try {
                // write start
                this.streamWriter.writeStartElement("confidence");
                // write confidence type
                CvTerm type = object.getType();
                getTypeWriter().write(type, "unit");
                // write value
                this.streamWriter.writeStartElement("value");
                this.streamWriter.writeCharacters(object.getValue());
                this.streamWriter.writeEndElement();
                // write end confidence
                this.streamWriter.writeEndElement();

            } catch (XMLStreamException e) {
                throw new MIIOException("Impossible to write the confidence : "+object.toString(), e);
            }
        }
    }
}
