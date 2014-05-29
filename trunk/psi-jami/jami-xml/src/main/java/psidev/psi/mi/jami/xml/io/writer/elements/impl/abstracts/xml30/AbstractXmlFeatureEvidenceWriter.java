package psidev.psi.mi.jami.xml.io.writer.elements.impl.abstracts.xml30;

import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.FeatureEvidence;
import psidev.psi.mi.jami.xml.cache.PsiXmlObjectCache;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

/**
 * Abstract class for feature evidence 3.0 writers
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>13/11/13</pre>
 */

public class AbstractXmlFeatureEvidenceWriter extends AbstractXmlFeatureWriter<FeatureEvidence> {

    public AbstractXmlFeatureEvidenceWriter(XMLStreamWriter writer, PsiXmlObjectCache objectIndex) {
        super(writer, objectIndex);
    }

    @Override
    protected void writeOtherProperties(FeatureEvidence object) throws XMLStreamException {
        // write feature detection method
        writeFeatureDetectionMethod(object);
    }

    protected void writeFeatureDetectionMethod(FeatureEvidence object) throws XMLStreamException {
        for (CvTerm method : object.getDetectionMethods()){
            // write repeatable detection method
            getFeatureTypeWriter().write(method, "featureDetectionMethod");
        }
    }
}
