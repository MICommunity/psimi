package psidev.psi.mi.jami.xml.io.writer.elements.impl;

import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.xml.cache.PsiXmlObjectCache;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXmlElementWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXmlXrefWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.abstracts.AbstractXmlFeatureWriter;

import javax.xml.stream.XMLStreamWriter;

/**
 * XML 2.5 writer for a modelled feature (ignore experimental details)
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>13/11/13</pre>
 */

public class XmlModelledFeatureWriter extends AbstractXmlFeatureWriter<ModelledFeature> {
    public XmlModelledFeatureWriter(XMLStreamWriter writer, PsiXmlObjectCache objectIndex) {
        super(writer, objectIndex);
    }

    public XmlModelledFeatureWriter(XMLStreamWriter writer, PsiXmlObjectCache objectIndex,
                                    PsiXmlElementWriter<Alias> aliasWriter,
                                    PsiXmlXrefWriter primaryRefWriter, PsiXmlXrefWriter secondaryRefWriter,
                                    PsiXmlElementWriter<CvTerm> featureTypeWriter, PsiXmlElementWriter<Range> rangeWriter,
                                    PsiXmlElementWriter<Annotation> attributeWriter) {
        super(writer, objectIndex, aliasWriter, primaryRefWriter, secondaryRefWriter, featureTypeWriter, rangeWriter, attributeWriter);
    }

    @Override
    protected void writeOtherProperties(ModelledFeature object) {
        // nothing to do
    }
}
