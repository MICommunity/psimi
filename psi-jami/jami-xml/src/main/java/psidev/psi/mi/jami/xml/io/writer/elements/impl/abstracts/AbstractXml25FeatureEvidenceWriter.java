package psidev.psi.mi.jami.xml.io.writer.elements.impl.abstracts;

import psidev.psi.mi.jami.model.Annotation;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.FeatureEvidence;
import psidev.psi.mi.jami.model.Range;
import psidev.psi.mi.jami.xml.PsiXml25ObjectCache;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXml25ElementWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXml25XrefWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.Xml25FeatureDetectionMethodWriter;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

/**
 * Abstract class for feature evidence writers
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>13/11/13</pre>
 */

public class AbstractXml25FeatureEvidenceWriter extends AbstractXml25FeatureWriter<FeatureEvidence>{
    private PsiXml25ElementWriter<CvTerm> detectionMethodWriter;

    public AbstractXml25FeatureEvidenceWriter(XMLStreamWriter writer, PsiXml25ObjectCache objectIndex) {
        super(writer, objectIndex);
        this.detectionMethodWriter = new Xml25FeatureDetectionMethodWriter(writer);
    }

    public AbstractXml25FeatureEvidenceWriter(XMLStreamWriter writer, PsiXml25ObjectCache objectIndex,
                                                 PsiXml25XrefWriter primaryRefWriter, PsiXml25XrefWriter secondaryRefWriter,
                                                 PsiXml25ElementWriter<CvTerm> featureTypeWriter, PsiXml25ElementWriter<CvTerm> detectionMethodWriter,
                                                 PsiXml25ElementWriter<Range> rangeWriter, PsiXml25ElementWriter<Annotation> attributeWriter) {
        super(writer, objectIndex, primaryRefWriter, secondaryRefWriter, featureTypeWriter, rangeWriter, attributeWriter);
        this.detectionMethodWriter = detectionMethodWriter != null ? detectionMethodWriter : new Xml25FeatureDetectionMethodWriter(writer);
    }

    @Override
    protected void writeOtherProperties(FeatureEvidence object) throws XMLStreamException {
        // write feature detection method
        writeFeatureDetectionMethod(object);
    }

    protected void writeFeatureDetectionMethod(FeatureEvidence object) throws XMLStreamException {
        if (!object.getDetectionMethods().isEmpty()){
            // only write the first one
            this.detectionMethodWriter.write(object.getDetectionMethods().iterator().next());
        }
    }
}
