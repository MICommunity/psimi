package psidev.psi.mi.jami.xml.io.writer.elements.impl.abstracts;

import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.xml.cache.PsiXmlObjectCache;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXmlElementWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXmlXrefWriter;
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
        this.detectionMethodWriter = new XmlFeatureDetectionMethodWriter(writer);
    }

    public AbstractXmlFeatureEvidenceWriter(XMLStreamWriter writer, PsiXmlObjectCache objectIndex,
                                            PsiXmlElementWriter<Alias> aliasWriter,
                                            PsiXmlXrefWriter primaryRefWriter, PsiXmlXrefWriter secondaryRefWriter,
                                            PsiXmlElementWriter<CvTerm> featureTypeWriter, PsiXmlElementWriter<CvTerm> detectionMethodWriter,
                                            PsiXmlElementWriter<Range> rangeWriter, PsiXmlElementWriter<Annotation> attributeWriter) {
        super(writer, objectIndex, aliasWriter, primaryRefWriter, secondaryRefWriter, featureTypeWriter, rangeWriter, attributeWriter);
        this.detectionMethodWriter = detectionMethodWriter != null ? detectionMethodWriter : new XmlFeatureDetectionMethodWriter(writer);
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
