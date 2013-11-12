package psidev.psi.mi.jami.xml.io.writer;

import org.codehaus.stax2.XMLStreamWriter2;
import psidev.psi.mi.jami.exception.MIIOException;
import psidev.psi.mi.jami.xml.PsiXml25ObjectIndex;

import javax.xml.stream.XMLStreamException;

/**
 * XML 2.5 availability writer
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>12/11/13</pre>
 */

public class Xml25AvailabilityWriter implements PsiXml25ElementWriter<String>{

    private XMLStreamWriter2 streamWriter;
    private PsiXml25ObjectIndex objectIndex;

    public Xml25AvailabilityWriter(XMLStreamWriter2 writer, PsiXml25ObjectIndex objectIndex){
        if (writer == null){
            throw new IllegalArgumentException("The XML stream writer is mandatory for the Xml25AvailabilityWriter");
        }
        this.streamWriter = writer;
        if (objectIndex == null){
            throw new IllegalArgumentException("The PsiXml 2.5 object index is mandatory for the Xml25AvailabilityWriter. It is necessary for generating an id to availability");
        }
        this.objectIndex = objectIndex;
    }

    @Override
    public void write(String object) throws MIIOException {
        try {
            // write start
            this.streamWriter.writeStartElement("availability");
            int id = this.objectIndex.extractIdFor(object);
            // write id attribute
            this.streamWriter.writeAttribute("id", Integer.toString(id));
            // write value
            this.streamWriter.writeCharacters(object);
            // write end availability
            this.streamWriter.writeEndElement();

        } catch (XMLStreamException e) {
            throw new MIIOException("Impossible to write the availability : "+object, e);
        }
    }
}
