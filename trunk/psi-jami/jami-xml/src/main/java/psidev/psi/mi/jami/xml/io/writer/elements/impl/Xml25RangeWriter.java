package psidev.psi.mi.jami.xml.io.writer.elements.impl;

import psidev.psi.mi.jami.exception.MIIOException;
import psidev.psi.mi.jami.model.Position;
import psidev.psi.mi.jami.model.Range;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXml25ElementWriter;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

/**
 * Xml 2.5 writer for a feature range
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>13/11/13</pre>
 */

public class Xml25RangeWriter implements PsiXml25ElementWriter<Range>{
    private XMLStreamWriter streamWriter;
    private PsiXml25ElementWriter<Position> startPositionWriter;
    private PsiXml25ElementWriter<Position> endPositionWriter;

    public Xml25RangeWriter(XMLStreamWriter writer){
        if (writer == null){
            throw new IllegalArgumentException("The XML stream writer is mandatory for the Xml25RangeWriter");
        }
        this.streamWriter = writer;
        this.startPositionWriter = new Xml25BeginPositionWriter(writer);
        this.endPositionWriter = new Xml25EndPositionWriter(writer);
    }

    public Xml25RangeWriter(XMLStreamWriter writer, PsiXml25ElementWriter<Position> startPositionWriter,
                            PsiXml25ElementWriter<Position> endPositionWriter) {
        if (writer == null){
            throw new IllegalArgumentException("The XML stream writer is mandatory for the Xml25RangeWriter");
        }
        this.streamWriter = writer;
        this.startPositionWriter = startPositionWriter != null ? startPositionWriter : new Xml25BeginPositionWriter(writer);
        this.endPositionWriter = endPositionWriter != null ? endPositionWriter : new Xml25EndPositionWriter(writer);
    }

    @Override
    public void write(Range object) throws MIIOException {
        if (object != null){
            try {
                // write start
                this.streamWriter.writeStartElement("featureRange");
                // write start position
                this.startPositionWriter.write(object.getStart());
                // write end position
                this.endPositionWriter.write(object.getEnd());
                // write isLink
                if (object.isLink()){
                    this.streamWriter.writeStartElement("isLink");
                    this.streamWriter.writeCharacters(Boolean.toString(object.isLink()));
                    this.streamWriter.writeEndElement();
                }
                // write end feature range
                this.streamWriter.writeEndElement();

            } catch (XMLStreamException e) {
                throw new MIIOException("Impossible to write the range : "+object.toString(), e);
            }
        }
    }
}
