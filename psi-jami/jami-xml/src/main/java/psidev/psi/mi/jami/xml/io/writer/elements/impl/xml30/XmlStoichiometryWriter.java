package psidev.psi.mi.jami.xml.io.writer.elements.impl.xml30;

import psidev.psi.mi.jami.exception.MIIOException;
import psidev.psi.mi.jami.model.Stoichiometry;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXmlElementWriter;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

/**
 * XML 3.0 writer for stoichiometry
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>11/11/13</pre>
 */

public class XmlStoichiometryWriter implements PsiXmlElementWriter<Stoichiometry> {
    private XMLStreamWriter streamWriter;

    public XmlStoichiometryWriter(XMLStreamWriter writer){
        if (writer == null){
            throw new IllegalArgumentException("The XML stream writer is mandatory for the XmlAnnotationWriter");
        }
        this.streamWriter = writer;
    }
    @Override
    public void write(Stoichiometry object) throws MIIOException {
        if (object != null && (object.getMinValue() > 0 || object.getMaxValue() > 0)){
            try {
                // stoichiometry range
                if (object.getMaxValue() != object.getMinValue()){
                    // write start
                    this.streamWriter.writeStartElement("stoichiometryRange");
                    // write min value
                    this.streamWriter.writeAttribute("minValue", Integer.toString(object.getMinValue()));
                    // write max value
                    this.streamWriter.writeAttribute("maxValue", Integer.toString(object.getMaxValue()));
                    // write end attribute
                    this.streamWriter.writeEndElement();
                }
                // mean stoichiometry
                else{
                    // write start
                    this.streamWriter.writeStartElement("stoichiometry");
                    // write value
                    this.streamWriter.writeAttribute("value", Integer.toString(object.getMinValue()));
                    // write end attribute
                    this.streamWriter.writeEndElement();
                }

            } catch (XMLStreamException e) {
                throw new MIIOException("Impossible to write the stoichiometry : "+object.toString(), e);
            }
        }
    }
}
