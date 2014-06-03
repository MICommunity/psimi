package psidev.psi.mi.jami.xml.io.writer.elements.impl.xml30;

import psidev.psi.mi.jami.model.FeatureEvidence;
import psidev.psi.mi.jami.model.Parameter;
import psidev.psi.mi.jami.xml.cache.PsiXmlObjectCache;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXmlParameterWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.abstracts.xml30.AbstractXmlFeatureEvidenceWriter;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

/**
 * XML 3.0 writer for a feature evidence (with feature detection method)
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>13/11/13</pre>
 */

public class XmlFeatureEvidenceWriter extends AbstractXmlFeatureEvidenceWriter {

    private PsiXmlParameterWriter parameterWriter;

    public XmlFeatureEvidenceWriter(XMLStreamWriter writer, PsiXmlObjectCache objectIndex) {
        super(writer, objectIndex);
    }

    public PsiXmlParameterWriter getParameterWriter() {
        if (this.parameterWriter == null){
            initialiseParameterWriter();
        }
        return parameterWriter;
    }

    protected void initialiseParameterWriter() {
        this.parameterWriter = new XmlParameterWriter(getStreamWriter(), getObjectIndex());
    }

    public void setParameterWriter(PsiXmlParameterWriter parameterWriter) {
        this.parameterWriter = parameterWriter;
    }

    @Override
    protected void writeOtherProperties(FeatureEvidence object) throws XMLStreamException {
        super.writeOtherProperties(object);

        // write parameters
        writeParameters(object);
    }

    protected void writeParameters(FeatureEvidence object) throws XMLStreamException {
        if (!object.getParameters().isEmpty()){
            getStreamWriter().writeStartElement("parameterList");
            for (Parameter param : object.getParameters()){
                getParameterWriter().write(param);
            }
            getStreamWriter().writeEndElement();
        }
    }
}
