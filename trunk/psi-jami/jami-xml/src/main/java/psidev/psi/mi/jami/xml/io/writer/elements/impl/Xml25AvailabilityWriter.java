package psidev.psi.mi.jami.xml.io.writer.elements.impl;

import psidev.psi.mi.jami.exception.MIIOException;
import psidev.psi.mi.jami.xml.cache.PsiXml25ObjectCache;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXml25ElementWriter;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

/**
 * XML 2.5 availability writer
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>12/11/13</pre>
 */

public class Xml25AvailabilityWriter implements PsiXml25ElementWriter<String> {

    private XMLStreamWriter streamWriter;
    private PsiXml25ObjectCache objectIndex;

    public Xml25AvailabilityWriter(XMLStreamWriter writer, PsiXml25ObjectCache objectIndex){
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
            int id = this.objectIndex.extractIdForAvailability(object);
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
