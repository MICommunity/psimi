package psidev.psi.mi.jami.xml.io.writer;

import org.codehaus.stax2.XMLStreamWriter2;
import psidev.psi.mi.jami.exception.MIIOException;
import psidev.psi.mi.jami.model.Checksum;
import psidev.psi.mi.jami.model.CvTerm;

import javax.xml.stream.XMLStreamException;

/**
 * Xml 25 writer of checksums
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>12/11/13</pre>
 */

public class Xml25ChecksumWriter implements PsiXml25ElementWriter<Checksum> {
    private XMLStreamWriter2 streamWriter;

    public Xml25ChecksumWriter(XMLStreamWriter2 writer){
        if (writer == null){
            throw new IllegalArgumentException("The XML stream writer is mandatory for the Xml25ChecksumWriter");
        }
        this.streamWriter = writer;
    }
    @Override
    public void write(Checksum object) throws MIIOException {
        if (object != null){
            try {
                // write start
                this.streamWriter.writeStartElement("attribute");
                // write topic
                CvTerm topic = object.getMethod();
                this.streamWriter.writeAttribute("name", topic.getShortName());
                if (topic.getMIIdentifier() != null){
                    this.streamWriter.writeAttribute("nameAc", topic.getMIIdentifier());
                }
                // write description
                this.streamWriter.writeCharacters(object.getValue());

                // write end attribute
                this.streamWriter.writeEndElement();

            } catch (XMLStreamException e) {
                throw new MIIOException("Impossible to write the checksum : "+object.toString(), e);
            }
        }
    }
}
