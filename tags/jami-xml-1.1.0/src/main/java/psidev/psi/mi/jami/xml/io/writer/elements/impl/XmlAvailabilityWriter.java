package psidev.psi.mi.jami.xml.io.writer.elements.impl;

import psidev.psi.mi.jami.exception.MIIOException;
import psidev.psi.mi.jami.xml.cache.PsiXmlObjectCache;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXmlElementWriter;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

/**
 * XML 2.5 availability writer
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>12/11/13</pre>
 */

public class XmlAvailabilityWriter implements PsiXmlElementWriter<String> {

    private XMLStreamWriter streamWriter;
    private PsiXmlObjectCache objectIndex;

    public XmlAvailabilityWriter(XMLStreamWriter writer, PsiXmlObjectCache objectIndex){
        if (writer == null){
            throw new IllegalArgumentException("The XML stream writer is mandatory for the XmlAvailabilityWriter");
        }
        this.streamWriter = writer;
        if (objectIndex == null){
            throw new IllegalArgumentException("The PsiXml 2.5 object index is mandatory for the XmlAvailabilityWriter. It is necessary for generating an id to availability");
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
