package psidev.psi.mi.jami.xml.io.writer.elements.impl.extended;

import psidev.psi.mi.jami.exception.MIIOException;
import psidev.psi.mi.jami.model.Confidence;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Experiment;
import psidev.psi.mi.jami.xml.PsiXml25ObjectCache;
import psidev.psi.mi.jami.xml.extension.XmlConfidence;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXml25ElementWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.Xml25ConfidenceTypeWriter;

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

public class Xml25ConfidenceWriter implements PsiXml25ElementWriter<Confidence> {
    private XMLStreamWriter streamWriter;
    private PsiXml25ElementWriter<CvTerm> typeWriter;
    private PsiXml25ObjectCache objectIndex;

    public Xml25ConfidenceWriter(XMLStreamWriter writer, PsiXml25ObjectCache objectIndex){
        if (writer == null){
            throw new IllegalArgumentException("The XML stream writer is mandatory for the Xml25ConfidenceWriter");
        }
        this.streamWriter = writer;
        if (objectIndex == null){
            throw new IllegalArgumentException("The PsiXml 2.5 object index is mandatory for the Xml25ConfidenceWriter. It is necessary for generating an id to an experimentDescription");
        }
        this.objectIndex = objectIndex;
        this.typeWriter = new Xml25ConfidenceTypeWriter(writer);
    }

    public Xml25ConfidenceWriter(XMLStreamWriter writer, PsiXml25ObjectCache objectIndex, PsiXml25ElementWriter<CvTerm> typeWriter){
        if (writer == null){
            throw new IllegalArgumentException("The XML stream writer is mandatory for the Xml25ConfidenceWriter");
        }
        this.streamWriter = writer;
        if (objectIndex == null){
            throw new IllegalArgumentException("The PsiXml 2.5 object index is mandatory for the Xml25ConfidenceWriter. It is necessary for generating an id to an experimentDescription");
        }
        this.objectIndex = objectIndex;
        this.typeWriter = typeWriter != null ? typeWriter : new Xml25ConfidenceTypeWriter(writer);
    }

    @Override
    public void write(Confidence object) throws MIIOException {
        if (object != null){
            try {
                // write start
                this.streamWriter.writeStartElement("confidence");
                // write confidence type
                CvTerm type = object.getType();
                this.typeWriter.write(type);
                // write value
                this.streamWriter.writeCharacters(object.getValue());
                // write experiments
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
                // write end confidence
                this.streamWriter.writeEndElement();

            } catch (XMLStreamException e) {
                throw new MIIOException("Impossible to write the confidence : "+object.toString(), e);
            }
        }
    }
}
