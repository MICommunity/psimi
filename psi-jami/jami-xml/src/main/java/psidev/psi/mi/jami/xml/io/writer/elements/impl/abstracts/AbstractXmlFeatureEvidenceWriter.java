package psidev.psi.mi.jami.xml.io.writer.elements.impl.abstracts;

import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.FeatureEvidence;
import psidev.psi.mi.jami.xml.cache.PsiXmlObjectCache;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXmlElementWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.XmlFeatureDetectionMethodWriter;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

/**
 * Abstract class for feature evidence writers
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>13/11/13</pre>
 */

public class AbstractXmlFeatureEvidenceWriter extends AbstractXmlFeatureWriter<FeatureEvidence> {
    private PsiXmlElementWriter<CvTerm> detectionMethodWriter;

    public AbstractXmlFeatureEvidenceWriter(XMLStreamWriter writer, PsiXmlObjectCache objectIndex) {
        super(writer, objectIndex);
    }

    public PsiXmlElementWriter<CvTerm> getDetectionMethodWriter() {
        if (this.detectionMethodWriter == null){
            this.detectionMethodWriter = new XmlFeatureDetectionMethodWriter(getStreamWriter());
        }
        return detectionMethodWriter;
    }

    public void setDetectionMethodWriter(PsiXmlElementWriter<CvTerm> detectionMethodWriter) {
        this.detectionMethodWriter = detectionMethodWriter;
    }

    @Override
    protected void writeOtherProperties(FeatureEvidence object) throws XMLStreamException {
        // write feature detection method
        writeFeatureDetectionMethod(object);
    }

    protected void writeFeatureDetectionMethod(FeatureEvidence object) throws XMLStreamException {
        if (!object.getDetectionMethods().isEmpty()){
            // only write the first one
            getDetectionMethodWriter().write(object.getDetectionMethods().iterator().next());
        }
    }
}
