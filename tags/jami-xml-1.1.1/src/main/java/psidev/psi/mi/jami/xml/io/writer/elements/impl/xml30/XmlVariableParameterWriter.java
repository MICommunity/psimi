package psidev.psi.mi.jami.xml.io.writer.elements.impl.xml30;

import psidev.psi.mi.jami.exception.MIIOException;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.VariableParameter;
import psidev.psi.mi.jami.model.VariableParameterValue;
import psidev.psi.mi.jami.xml.cache.PsiXmlObjectCache;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXmlElementWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXmlVariableNameWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.XmlCvTermWriter;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

/**
 * XML 3.0 writer for variable parameter
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>11/11/13</pre>
 */

public class XmlVariableParameterWriter implements PsiXmlElementWriter<VariableParameter> {
    private XMLStreamWriter streamWriter;
    private PsiXmlObjectCache objectIndex;

    private PsiXmlElementWriter<VariableParameterValue> variableParameterValueWriter;
    private PsiXmlVariableNameWriter<CvTerm> unitWriter;

    public XmlVariableParameterWriter(XMLStreamWriter writer, PsiXmlObjectCache objectIndex){
        if (writer == null){
            throw new IllegalArgumentException("The XML stream writer is mandatory for the XmlVariableParameterWriter");
        }
        this.streamWriter = writer;
        if (objectIndex == null){
            throw new IllegalArgumentException("The PsiXml 2.5 object index is mandatory for the XmlVariableParameterWriter. It is necessary for generating " +
                    "an id to a variable parameter value");
        }
        this.objectIndex = objectIndex;
    }

    public PsiXmlElementWriter<VariableParameterValue> getVariableParameterValueWriter() {
        if (this.variableParameterValueWriter == null){
            this.variableParameterValueWriter = new XmlVariableParameterValueWriter(this.streamWriter, this.objectIndex);
        }
        return variableParameterValueWriter;
    }

    public void setVariableParameterValueWriter(PsiXmlElementWriter<VariableParameterValue> variableParameterValueWriter) {
        this.variableParameterValueWriter = variableParameterValueWriter;
    }

    public PsiXmlVariableNameWriter<CvTerm> getUnitWriter() {
        if (this.unitWriter == null){
            initialiseUnitWriter();
        }
        return unitWriter;
    }

    protected void initialiseUnitWriter() {
        this.unitWriter = new XmlCvTermWriter(this.streamWriter);
    }

    public void setUnitWriter(PsiXmlVariableNameWriter<CvTerm> unitWriter) {
        this.unitWriter = unitWriter;
    }

    @Override
    public void write(VariableParameter object) throws MIIOException {
        if (object != null){
            try {
                // write start
                this.streamWriter.writeStartElement("variableParameter");
                // write description
                writeDescription(object);
                // write unit
                writeUnit(object);
                // write variable values
                writeVariableParameterValues(object);
                // write end variable parameter
                this.streamWriter.writeEndElement();

            } catch (XMLStreamException e) {
                throw new MIIOException("Impossible to write the variable parameter value : "+object.toString(), e);
            }
        }
    }

    protected void writeVariableParameterValues(VariableParameter object) throws XMLStreamException {
         if (!object.getVariableValues().isEmpty()){
             this.streamWriter.writeStartElement("variableValueList");
             for (VariableParameterValue value : object.getVariableValues()){
                 getVariableParameterValueWriter().write(value);
             }

             // end variable value list
             this.streamWriter.writeEndElement();
         }
    }

    protected void writeUnit(VariableParameter object) {
        if (object.getUnit() != null){
            getUnitWriter().write(object.getUnit(), "unit");
        }
    }

    protected void writeDescription(VariableParameter object) throws XMLStreamException {
        this.streamWriter.writeStartElement("description");
        this.streamWriter.writeCharacters(object.getDescription());
        this.streamWriter.writeEndElement();
    }

    protected XMLStreamWriter getStreamWriter() {
        return streamWriter;
    }

    protected PsiXmlObjectCache getObjectIndex() {
        return objectIndex;
    }
}
