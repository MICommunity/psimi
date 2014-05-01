package psidev.psi.mi.jami.xml.io.writer.elements.impl.abstracts;

import psidev.psi.mi.jami.exception.MIIOException;
import psidev.psi.mi.jami.model.Position;
import psidev.psi.mi.jami.model.Range;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXmlElementWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.XmlBeginPositionWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.XmlEndPositionWriter;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

/**
 * Abstract Xml writer for a feature range
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>13/11/13</pre>
 */

public abstract class AbstractXmlRangeWriter implements PsiXmlElementWriter<Range> {
    private XMLStreamWriter streamWriter;
    private PsiXmlElementWriter<Position> startPositionWriter;
    private PsiXmlElementWriter<Position> endPositionWriter;

    public AbstractXmlRangeWriter(XMLStreamWriter writer){
        if (writer == null){
            throw new IllegalArgumentException("The XML stream writer is mandatory for the XmlRangeWriter");
        }
        this.streamWriter = writer;
    }

    public PsiXmlElementWriter<Position> getStartPositionWriter() {
        if (this.startPositionWriter == null){
            this.startPositionWriter = new XmlBeginPositionWriter(streamWriter);

        }
        return startPositionWriter;
    }

    public void setStartPositionWriter(PsiXmlElementWriter<Position> startPositionWriter) {
        this.startPositionWriter = startPositionWriter;
    }

    public PsiXmlElementWriter<Position> getEndPositionWriter() {
        if (this.endPositionWriter == null){
            this.endPositionWriter = new XmlEndPositionWriter(streamWriter);

        }
        return endPositionWriter;
    }

    public void setEndPositionWriter(PsiXmlElementWriter<Position> endPositionWriter) {
        this.endPositionWriter = endPositionWriter;
    }

    @Override
    public void write(Range object) throws MIIOException {
        if (object != null){
            try {
                // write start
                this.streamWriter.writeStartElement("featureRange");
                // write start position
                getStartPositionWriter().write(object.getStart());
                // write end position
                getEndPositionWriter().write(object.getEnd());
                // write isLink
                if (object.isLink()){
                    this.streamWriter.writeStartElement("isLink");
                    this.streamWriter.writeCharacters(Boolean.toString(object.isLink()));
                    this.streamWriter.writeEndElement();
                }

                // write additional information
                writeOtherProperties(object);

                // write end feature range
                this.streamWriter.writeEndElement();

            } catch (XMLStreamException e) {
                throw new MIIOException("Impossible to write the range : "+object.toString(), e);
            }
        }
    }

    protected abstract void writeOtherProperties(Range object) throws XMLStreamException;

    protected XMLStreamWriter getStreamWriter() {
        return streamWriter;
    }
}
