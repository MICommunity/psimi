package psidev.psi.mi.jami.xml.io.writer.elements.impl;

import org.codehaus.stax2.XMLStreamWriter2;
import psidev.psi.mi.jami.exception.MIIOException;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Parameter;
import psidev.psi.mi.jami.model.ParameterValue;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXml25ElementWriter;
import psidev.psi.mi.jami.xml.utils.PsiXml25Utils;

import javax.xml.stream.XMLStreamException;

/**
 * XML 2.5 writer of a parameter
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>14/11/13</pre>
 */

public class Xml25ParameterWriter implements PsiXml25ElementWriter<Parameter> {
    private XMLStreamWriter2 streamWriter;

    public Xml25ParameterWriter(XMLStreamWriter2 writer){
        if (writer == null){
            throw new IllegalArgumentException("The XML stream writer is mandatory for the Xml25ParameterWriter");
        }
        this.streamWriter = writer;
    }

    @Override
    public void write(Parameter object) throws MIIOException {
        if (object != null){
            try {
                // write start
                this.streamWriter.writeStartElement("parameter");
                this.streamWriter.writeCharacters(PsiXml25Utils.LINE_BREAK);
                // write parameter type
                CvTerm type = object.getType();
                this.streamWriter.writeAttribute("term", type.getShortName());
                if (type.getMIIdentifier() != null){
                    this.streamWriter.writeAttribute("termAc", type.getMIIdentifier());
                }
                // write parameter unit
                CvTerm unit = object.getUnit();
                if (unit != null){
                    this.streamWriter.writeAttribute("unit", unit.getShortName());
                    if (unit.getMIIdentifier() != null){
                        this.streamWriter.writeAttribute("unitAc", unit.getMIIdentifier());
                    }
                }
                // write value
                ParameterValue value = object.getValue();
                // write base
                this.streamWriter.writeAttribute("base",Short.toString(value.getBase()));
                // write exponent
                this.streamWriter.writeAttribute("exponent",Short.toString(value.getExponent()));
                // write factor
                this.streamWriter.writeAttribute("factor",value.getFactor().toString());
                // write uncertainty
                if (object.getUncertainty() != null){
                    this.streamWriter.writeAttribute("uncertainty",object.getUncertainty().toString());
                }
                // write end parameter
                this.streamWriter.writeEndElement();

            } catch (XMLStreamException e) {
                throw new MIIOException("Impossible to write the parameter : "+object.toString(), e);
            }
        }
    }
}
