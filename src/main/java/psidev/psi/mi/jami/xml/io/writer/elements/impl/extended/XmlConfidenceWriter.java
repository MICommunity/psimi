package psidev.psi.mi.jami.xml.io.writer.elements.impl.extended;

import psidev.psi.mi.jami.exception.MIIOException;
import psidev.psi.mi.jami.model.Confidence;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Experiment;
import psidev.psi.mi.jami.xml.cache.PsiXmlObjectCache;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXmlElementWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXmlVariableNameWriter;
import psidev.psi.mi.jami.xml.model.extension.XmlConfidence;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

/**
 * Extended Xml25 writer for confidences.
 *
 * It will write the confidence experiment references
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>12/11/13</pre>
 */

public class XmlConfidenceWriter implements PsiXmlElementWriter<Confidence> {
    private XMLStreamWriter streamWriter;
    private PsiXmlVariableNameWriter<CvTerm> typeWriter;
    private PsiXmlObjectCache objectIndex;

    public XmlConfidenceWriter(XMLStreamWriter writer, PsiXmlObjectCache objectIndex){
        if (writer == null){
            throw new IllegalArgumentException("The XML stream writer is mandatory for the XmlConfidenceWriter");
        }
        this.streamWriter = writer;
        if (objectIndex == null){
            throw new IllegalArgumentException("The PsiXml 2.5 object index is mandatory for the XmlConfidenceWriter. It is necessary for generating an id to an experimentDescription");
        }
        this.objectIndex = objectIndex;
    }

    public PsiXmlVariableNameWriter<CvTerm> getTypeWriter() {
        if (this.typeWriter == null){
            initialiseTypeWriter();

        }
        return typeWriter;
    }

    protected void initialiseTypeWriter() {
        this.typeWriter = new XmlOpenCvTermWriter(streamWriter);
    }

    public void setTypeWriter(PsiXmlVariableNameWriter<CvTerm> typeWriter) {
        this.typeWriter = typeWriter;
    }

    @Override
    public void write(Confidence object) throws MIIOException {
        if (object != null){
            try {
                // write start
                this.streamWriter.writeStartElement("confidence");
                // write confidence type
                CvTerm type = object.getType();
                getTypeWriter().write(type,"unit");
                // write value
                this.streamWriter.writeStartElement("value");
                this.streamWriter.writeCharacters(object.getValue());
                this.streamWriter.writeEndElement();
                // write experiments
                if (object instanceof XmlConfidence){
                    XmlConfidence xmlConfidence = (XmlConfidence)object;
                    if (!xmlConfidence.getExperiments().isEmpty()){
                        this.streamWriter.writeStartElement("experimentRefList");
                        for (Experiment exp : xmlConfidence.getExperiments()){
                            this.streamWriter.writeStartElement("experimentRef");
                            this.streamWriter.writeCharacters(Integer.toString(this.objectIndex.extractIdForExperiment(exp)));
                            this.streamWriter.writeEndElement();
                        }
                        this.streamWriter.writeEndElement();
                    }
                }

                // write end confidence
                this.streamWriter.writeEndElement();

            } catch (XMLStreamException e) {
                throw new MIIOException("Impossible to write the confidence : "+object.toString(), e);
            }
        }
    }
}
