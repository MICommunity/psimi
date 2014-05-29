package psidev.psi.mi.jami.xml.io.writer.elements.impl.xml30;

import psidev.psi.mi.jami.exception.MIIOException;
import psidev.psi.mi.jami.model.ResultingSequence;
import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXmlElementWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXmlXrefWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.XmlDbXrefWriter;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.util.Iterator;

/**
 * Xml 30 writer for resulting sequence
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>12/11/13</pre>
 */

public class XmlResultingSequenceWriter implements PsiXmlElementWriter<ResultingSequence> {
    private XMLStreamWriter streamWriter;
    private PsiXmlXrefWriter xrefWriter;

    public XmlResultingSequenceWriter(XMLStreamWriter writer){
        if (writer == null){
            throw new IllegalArgumentException("The XML stream writer is mandatory for the AbstractXmlParticipantWriter");
        }
        this.streamWriter = writer;
    }

    public PsiXmlXrefWriter getXrefWriter() {
        if (this.xrefWriter == null){
            initialiseXrefWriter();
        }
        return xrefWriter;
    }

    protected void initialiseXrefWriter() {
        this.xrefWriter = new XmlDbXrefWriter(streamWriter);
    }

    public void setXrefWriter(PsiXmlXrefWriter xrefWriter) {
        this.xrefWriter = xrefWriter;
    }

    @Override
    public void write(ResultingSequence object) throws MIIOException {
        try {
            // write start
            this.streamWriter.writeStartElement("resultingSequence");
            // write original sequence
            this.streamWriter.writeStartElement("originalSequence");
            if (object.getOriginalSequence() != null){
                this.streamWriter.writeCharacters(object.getOriginalSequence());
            }
            this.streamWriter.writeEndElement();
            // write new sequence
            this.streamWriter.writeStartElement("newSequence");
            if (object.getNewSequence() != null){
                this.streamWriter.writeCharacters(object.getNewSequence());
            }
            this.streamWriter.writeEndElement();
            // write Xref
            writeXref(object);
            // write end resulting sequence
            this.streamWriter.writeEndElement();

        } catch (XMLStreamException e) {
            throw new MIIOException("Impossible to write the participant : "+object.toString(), e);
        }
    }

    protected void writeXref(ResultingSequence object) throws XMLStreamException {
        if (!object.getXrefs().isEmpty()){
            Iterator<Xref> refIterator = object.getXrefs().iterator();
            // default qualifier is null as we are not processing identifiers
            getXrefWriter().setDefaultRefType(null);
            getXrefWriter().setDefaultRefTypeAc(null);
            // write start xref
            this.streamWriter.writeStartElement("xref");

            int index = 0;
            while (refIterator.hasNext()){
                Xref ref = refIterator.next();
                // write primaryRef
                if (index == 0){
                    getXrefWriter().write(ref,"primaryRef");
                    index++;
                }
                // write secondaryref
                else{
                    getXrefWriter().write(ref,"secondaryRef");
                    index++;
                }
            }

            // write end xref
            this.streamWriter.writeEndElement();
        }
    }

    protected XMLStreamWriter getStreamWriter() {
        return streamWriter;
    }
}
