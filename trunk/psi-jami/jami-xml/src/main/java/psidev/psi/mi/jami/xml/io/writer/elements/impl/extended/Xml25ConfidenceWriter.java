package psidev.psi.mi.jami.xml.io.writer.elements.impl.extended;

import org.codehaus.stax2.XMLStreamWriter2;
import psidev.psi.mi.jami.exception.MIIOException;
import psidev.psi.mi.jami.model.Confidence;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXml25ElementWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.Xml25ConfidenceTypeWriter;
import psidev.psi.mi.jami.xml.utils.PsiXml25Utils;

import javax.xml.stream.XMLStreamException;

/**
 * Extended Xml25 writer for confidences.
 *
 * It will write the confidence experiment references
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>12/11/13</pre>
 */

public class Xml25ConfidenceWriter implements PsiXml25ElementWriter<Confidence> {
    private XMLStreamWriter2 streamWriter;
    private PsiXml25ElementWriter<CvTerm> typeWriter;

    public Xml25ConfidenceWriter(XMLStreamWriter2 writer){
        if (writer == null){
            throw new IllegalArgumentException("The XML stream writer is mandatory for the Xml25ConfidenceWriter");
        }
        this.streamWriter = writer;
        this.typeWriter = new Xml25ConfidenceTypeWriter(writer);
    }

    public Xml25ConfidenceWriter(XMLStreamWriter2 writer, PsiXml25ElementWriter<CvTerm> typeWriter){
        if (writer == null){
            throw new IllegalArgumentException("The XML stream writer is mandatory for the Xml25ConfidenceWriter");
        }
        this.streamWriter = writer;
        this.typeWriter = typeWriter != null ? typeWriter : new Xml25ConfidenceTypeWriter(writer);
    }

    @Override
    public void write(Confidence object) throws MIIOException {
        if (object != null){
            try {
                // write start
                this.streamWriter.writeStartElement("confidence");
                this.streamWriter.writeCharacters(PsiXml25Utils.LINE_BREAK);
                // write confidence type
                CvTerm type = object.getType();
                this.typeWriter.write(type);
                this.streamWriter.writeCharacters(PsiXml25Utils.LINE_BREAK);
                // write value
                this.streamWriter.writeCharacters(object.getValue());
                this.streamWriter.writeCharacters(PsiXml25Utils.LINE_BREAK);
                // TODO write experiments
                // write end confidence
                this.streamWriter.writeEndElement();

            } catch (XMLStreamException e) {
                throw new MIIOException("Impossible to write the confidence : "+object.toString(), e);
            }
        }
    }
}
