package psidev.psi.mi.jami.xml.io.writer.elements.impl.abstracts;

import psidev.psi.mi.jami.model.FeatureEvidence;
import psidev.psi.mi.jami.xml.cache.PsiXmlObjectCache;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

/**
 * Abstract class for feature evidence 2.5 writers
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>13/11/13</pre>
 */

public class AbstractXml25FeatureEvidenceWriter extends AbstractXmlFeatureWriter<FeatureEvidence> {

    public AbstractXml25FeatureEvidenceWriter(XMLStreamWriter writer, PsiXmlObjectCache objectIndex) {
        super(writer, objectIndex);
    }

    @Override
    protected void writeOtherProperties(FeatureEvidence object) throws XMLStreamException {
        // write feature detection method
        writeFeatureDetectionMethod(object);
    }

    protected void writeFeatureDetectionMethod(FeatureEvidence object) throws XMLStreamException {
        if (!object.getDetectionMethods().isEmpty()){
            // only write the first one
            getFeatureTypeWriter().write(object.getDetectionMethods().iterator().next(), "featureDetectionMethod");
        }
    }
}
