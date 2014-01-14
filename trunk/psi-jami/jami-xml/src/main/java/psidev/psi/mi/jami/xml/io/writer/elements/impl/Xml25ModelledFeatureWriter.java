package psidev.psi.mi.jami.xml.io.writer.elements.impl;

import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.xml.cache.PsiXml25ObjectCache;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXml25ElementWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXml25XrefWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.abstracts.AbstractXml25FeatureWriter;

import javax.xml.stream.XMLStreamWriter;

/**
 * XML 2.5 writer for a modelled feature (ignore experimental details)
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>13/11/13</pre>
 */

public class Xml25ModelledFeatureWriter extends AbstractXml25FeatureWriter<ModelledFeature> {
    public Xml25ModelledFeatureWriter(XMLStreamWriter writer, PsiXml25ObjectCache objectIndex) {
        super(writer, objectIndex);
    }

    public Xml25ModelledFeatureWriter(XMLStreamWriter writer, PsiXml25ObjectCache objectIndex,
                                      PsiXml25ElementWriter<Alias> aliasWriter,
                                      PsiXml25XrefWriter primaryRefWriter, PsiXml25XrefWriter secondaryRefWriter,
                                      PsiXml25ElementWriter<CvTerm> featureTypeWriter, PsiXml25ElementWriter<Range> rangeWriter,
                                      PsiXml25ElementWriter<Annotation> attributeWriter) {
        super(writer, objectIndex, aliasWriter, primaryRefWriter, secondaryRefWriter, featureTypeWriter, rangeWriter, attributeWriter);
    }

    @Override
    protected void writeOtherProperties(ModelledFeature object) {
        // nothing to do
    }
}
