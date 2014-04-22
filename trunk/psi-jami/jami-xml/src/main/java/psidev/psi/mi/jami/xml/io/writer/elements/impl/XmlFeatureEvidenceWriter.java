package psidev.psi.mi.jami.xml.io.writer.elements.impl;

import psidev.psi.mi.jami.model.Alias;
import psidev.psi.mi.jami.model.Annotation;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Range;
import psidev.psi.mi.jami.xml.cache.PsiXmlObjectCache;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXmlElementWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXmlXrefWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.abstracts.AbstractXmlFeatureEvidenceWriter;

import javax.xml.stream.XMLStreamWriter;

/**
 * XML 2.5 writer for a feature evidence (with feature detection method)
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>13/11/13</pre>
 */

public class XmlFeatureEvidenceWriter extends AbstractXmlFeatureEvidenceWriter {

    public XmlFeatureEvidenceWriter(XMLStreamWriter writer, PsiXmlObjectCache objectIndex) {
        super(writer, objectIndex);
    }

    public XmlFeatureEvidenceWriter(XMLStreamWriter writer, PsiXmlObjectCache objectIndex,
                                    PsiXmlElementWriter<Alias> aliasWriter,
                                    PsiXmlXrefWriter primaryRefWriter, PsiXmlXrefWriter secondaryRefWriter,
                                    PsiXmlElementWriter<CvTerm> featureTypeWriter, PsiXmlElementWriter<CvTerm> detectionMethodWriter, PsiXmlElementWriter<Range> rangeWriter, PsiXmlElementWriter<Annotation> attributeWriter) {
        super(writer, objectIndex, aliasWriter, primaryRefWriter, secondaryRefWriter, featureTypeWriter, detectionMethodWriter, rangeWriter, attributeWriter);
    }
}
