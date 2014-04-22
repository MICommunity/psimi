package psidev.psi.mi.jami.xml.io.writer.elements.impl.abstracts;

import psidev.psi.mi.jami.exception.MIIOException;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Position;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXmlElementWriter;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

/**
 * Abstract Xml 2.5 writer for a range position
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>13/11/13</pre>
 */

public abstract class AbstractXmlPositionWriter implements PsiXmlElementWriter<Position> {
    private XMLStreamWriter streamWriter;
    private PsiXmlElementWriter<CvTerm> statusWriter;

    public AbstractXmlPositionWriter(XMLStreamWriter writer, PsiXmlElementWriter<CvTerm> statusWriter){
        if (writer == null){
            throw new IllegalArgumentException("The XML stream writer is mandatory for the AbstractXmlPositionWriter");
        }
        this.streamWriter = writer;
        if (statusWriter == null){
            throw new IllegalArgumentException("The XML range status writer is mandatory for the AbstractXmlPositionWriter");
        }
        this.statusWriter = statusWriter;
    }

    @Override
    public void write(Position object) throws MIIOException {
        try {
            // write status
            this.statusWriter.write(object.getStatus());
            // write position
            if (!object.isPositionUndetermined()){
                if (object.getStart() == object.getEnd()){
                    // write start
                    writeStartPositionNode();
                    // write position attribute
                    this.streamWriter.writeAttribute("position", Long.toString(object.getStart()));
                    // write end
                    this.streamWriter.writeEndElement();
                }
                // write interval
                else{
                    // write start
                    writeStartIntervalNode();
                    // write start attribute
                    this.streamWriter.writeAttribute("begin", Long.toString(object.getStart()));
                    // write end attribute
                    this.streamWriter.writeAttribute("end", Long.toString(object.getEnd()));
                    // write end
                    this.streamWriter.writeEndElement();
                }
            }

        } catch (Exception e) {
            throw new MIIOException("Impossible to write the range position : "+object.toString(), e);
        }
    }

    protected abstract void writeStartPositionNode() throws XMLStreamException;
    protected abstract void writeStartIntervalNode() throws XMLStreamException;

    protected XMLStreamWriter getStreamWriter() {
        return streamWriter;
    }
}
