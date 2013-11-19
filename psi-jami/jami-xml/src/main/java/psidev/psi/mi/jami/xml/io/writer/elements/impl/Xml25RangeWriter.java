package psidev.psi.mi.jami.xml.io.writer.elements.impl;

import org.codehaus.stax2.XMLStreamWriter2;
import psidev.psi.mi.jami.exception.MIIOException;
import psidev.psi.mi.jami.model.Position;
import psidev.psi.mi.jami.model.Range;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXml25ElementWriter;
import psidev.psi.mi.jami.xml.utils.PsiXml25Utils;

import javax.xml.stream.XMLStreamException;

/**
 * Xml 2.5 writer for a feature range
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>13/11/13</pre>
 */

public class Xml25RangeWriter implements PsiXml25ElementWriter<Range>{
    private XMLStreamWriter2 streamWriter;
    private PsiXml25ElementWriter<Position> startPositionWriter;
    private PsiXml25ElementWriter<Position> endPositionWriter;

    public Xml25RangeWriter(XMLStreamWriter2 writer){
        if (writer == null){
            throw new IllegalArgumentException("The XML stream writer is mandatory for the Xml25RangeWriter");
        }
        this.streamWriter = writer;
        this.startPositionWriter = new Xml25BeginPositionWriter(writer);
        this.endPositionWriter = new Xml25EndPositionWriter(writer);
    }

    public Xml25RangeWriter(XMLStreamWriter2 writer, PsiXml25ElementWriter<Position> startPositionWriter, PsiXml25ElementWriter<Position> endPositionWriter) {
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
                this.streamWriter.writeCharacters(PsiXml25Utils.LINE_BREAK);
                this.startPositionWriter.write(object.getStart());
                this.streamWriter.writeCharacters(PsiXml25Utils.LINE_BREAK);
                // write end position
                this.streamWriter.writeCharacters(PsiXml25Utils.LINE_BREAK);
                this.endPositionWriter.write(object.getEnd());
                this.streamWriter.writeCharacters(PsiXml25Utils.LINE_BREAK);
                // write isLink
                if (object.isLink()){
                    this.streamWriter.writeCharacters(PsiXml25Utils.LINE_BREAK);
                    this.streamWriter.writeStartElement("isLink");
                    this.streamWriter.writeCharacters(Boolean.toString(object.isLink()));
                    this.streamWriter.writeEndElement();
                    this.streamWriter.writeCharacters(PsiXml25Utils.LINE_BREAK);
                }
                // write end feature range
                this.streamWriter.writeEndElement();

            } catch (XMLStreamException e) {
                throw new MIIOException("Impossible to write the range : "+object.toString(), e);
            }
        }
    }
}
