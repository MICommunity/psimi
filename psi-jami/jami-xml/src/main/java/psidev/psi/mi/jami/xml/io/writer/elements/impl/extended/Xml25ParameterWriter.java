package psidev.psi.mi.jami.xml.io.writer.elements.impl.extended;

import psidev.psi.mi.jami.model.Parameter;
import psidev.psi.mi.jami.xml.cache.PsiXmlObjectCache;
import psidev.psi.mi.jami.xml.model.extension.XmlParameter;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

/**
 * XML 2.5 writer of a parameter
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>14/11/13</pre>
 */

public class Xml25ParameterWriter extends psidev.psi.mi.jami.xml.io.writer.elements.impl.Xml25ParameterWriter {

    public Xml25ParameterWriter(XMLStreamWriter writer, PsiXmlObjectCache objectIndex){
        super(writer, objectIndex);
    }

    @Override
    protected void writeOtherProperties(Parameter object) throws XMLStreamException {
        // write experiment Ref
        if (object instanceof XmlParameter){
            XmlParameter xmlParameter = (XmlParameter)object;
            if (xmlParameter.getExperiment() != null){
                getStreamWriter().writeStartElement("experimentRef");
                getStreamWriter().writeCharacters(Integer.toString(getObjectIndex().extractIdForExperiment(xmlParameter.getExperiment())));
                getStreamWriter().writeEndElement();
            }
            else{
                super.writeOtherProperties(object);

            }
        }
        else{
            super.writeOtherProperties(object);
        }
    }
}
