package psidev.psi.mi.jami.xml.io.writer.elements.impl.xml30;

import psidev.psi.mi.jami.exception.MIIOException;
import psidev.psi.mi.jami.model.VariableParameterValue;
import psidev.psi.mi.jami.xml.cache.PsiXmlObjectCache;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXmlElementWriter;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

/**
 * XML 3.0 writer for variable parameter value
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>11/11/13</pre>
 */

public class XmlVariableParameterValueWriter implements PsiXmlElementWriter<VariableParameterValue> {
    private XMLStreamWriter streamWriter;
    private PsiXmlObjectCache objectIndex;

    public XmlVariableParameterValueWriter(XMLStreamWriter writer, PsiXmlObjectCache objectIndex){
        if (writer == null){
            throw new IllegalArgumentException("The XML stream writer is mandatory for the XmlVariableParameterValueWriter");
        }
        this.streamWriter = writer;
        if (objectIndex == null){
            throw new IllegalArgumentException("The PsiXml 2.5 object index is mandatory for the XmlVariableParameterValueWriter. It is necessary for generating " +
                    "an id to a variable parameter value");
        }
        this.objectIndex = objectIndex;
    }
    @Override
    public void write(VariableParameterValue object) throws MIIOException {
        if (object != null){
            try {
                // write start
                this.streamWriter.writeStartElement("variableValue");
                // write id
                this.streamWriter.writeAttribute("id", Integer.toString(this.objectIndex.extractIdForVariableParameterValue(object)));
                // write order
                if (object.getOrder() != null){
                    this.streamWriter.writeAttribute("order", Integer.toString(object.getOrder()));
                }

                // write value
                this.streamWriter.writeStartElement("value");
                this.streamWriter.writeCharacters(object.getValue());
                this.streamWriter.writeEndElement();
                // write end variable value
                this.streamWriter.writeEndElement();

            } catch (XMLStreamException e) {
                throw new MIIOException("Impossible to write the variable parameter value : "+object.toString(), e);
            }
        }
    }
}
