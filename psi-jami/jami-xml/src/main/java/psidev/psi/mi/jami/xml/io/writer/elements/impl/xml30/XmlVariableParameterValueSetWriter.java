package psidev.psi.mi.jami.xml.io.writer.elements.impl.xml30;

import psidev.psi.mi.jami.exception.MIIOException;
import psidev.psi.mi.jami.model.VariableParameterValue;
import psidev.psi.mi.jami.model.VariableParameterValueSet;
import psidev.psi.mi.jami.xml.cache.PsiXmlObjectCache;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXmlElementWriter;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

/**
 * XML 3.0 writer for variable parameter value set
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>11/11/13</pre>
 */

public class XmlVariableParameterValueSetWriter implements PsiXmlElementWriter<VariableParameterValueSet> {
    private XMLStreamWriter streamWriter;
    private PsiXmlObjectCache objectIndex;

    public XmlVariableParameterValueSetWriter(XMLStreamWriter writer, PsiXmlObjectCache objectIndex){
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
    public void write(VariableParameterValueSet object) throws MIIOException {
        if (object != null){
            try {
                // write start
                this.streamWriter.writeStartElement("experimentalVariableValues");

                for (VariableParameterValue value : object){
                    // write value reference
                    this.streamWriter.writeStartElement("variableValueRef");
                    this.streamWriter.writeCharacters(Integer.toString(this.objectIndex.extractIdForVariableParameterValue(value)));
                    this.streamWriter.writeEndElement();
                }

                // write end variable value set
                this.streamWriter.writeEndElement();

            } catch (XMLStreamException e) {
                throw new MIIOException("Impossible to write the experimental variable parameter value set : "+object.toString(), e);
            }
        }
    }
}
