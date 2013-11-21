package psidev.psi.mi.jami.xml.io.writer.elements.impl;

import psidev.psi.mi.jami.model.Annotation;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Range;
import psidev.psi.mi.jami.xml.PsiXml25ObjectCache;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXml25ElementWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXml25XrefWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.abstracts.AbstractXml25FeatureEvidenceWriter;

import javax.xml.stream.XMLStreamWriter;

/**
 * XML 2.5 writer for a feature evidence (with feature detection method)
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>13/11/13</pre>
 */

public class Xml25FeatureEvidenceWriter extends AbstractXml25FeatureEvidenceWriter {

    public Xml25FeatureEvidenceWriter(XMLStreamWriter writer, PsiXml25ObjectCache objectIndex) {
        super(writer, objectIndex);
    }

    public Xml25FeatureEvidenceWriter(XMLStreamWriter writer, PsiXml25ObjectCache objectIndex, PsiXml25XrefWriter primaryRefWriter, PsiXml25XrefWriter secondaryRefWriter, PsiXml25ElementWriter<CvTerm> featureTypeWriter, PsiXml25ElementWriter<Annotation> attributeWriter, PsiXml25ElementWriter<Range> rangeWriter, PsiXml25ElementWriter<CvTerm> detectionMethodWriter) {
        super(writer, objectIndex, primaryRefWriter, secondaryRefWriter, featureTypeWriter, attributeWriter, rangeWriter, detectionMethodWriter);
    }
}
