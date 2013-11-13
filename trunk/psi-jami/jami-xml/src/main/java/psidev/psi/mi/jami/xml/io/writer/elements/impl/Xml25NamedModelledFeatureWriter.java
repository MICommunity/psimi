package psidev.psi.mi.jami.xml.io.writer.elements.impl;

import org.codehaus.stax2.XMLStreamWriter2;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.xml.PsiXml25ObjectIndex;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXml25ElementWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXml25XrefWriter;

/**
 * XML 2.5 writer for a modelled feature (ignore experimental details) having aliases in
 * addition to shortname and fullname
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>13/11/13</pre>
 */

public class Xml25NamedModelledFeatureWriter extends AbstractXml25NamedFeatureWriter<ModelledFeature>{
    public Xml25NamedModelledFeatureWriter(XMLStreamWriter2 writer, PsiXml25ObjectIndex objectIndex) {
        super(writer, objectIndex);
    }

    public Xml25NamedModelledFeatureWriter(XMLStreamWriter2 writer, PsiXml25ObjectIndex objectIndex, PsiXml25XrefWriter primaryRefWriter, PsiXml25XrefWriter secondaryRefWriter, PsiXml25ElementWriter<CvTerm> featureTypeWriter, PsiXml25ElementWriter<Annotation> attributeWriter, PsiXml25ElementWriter<Range> rangeWriter, PsiXml25ElementWriter<Alias> aliasWriter) {
        super(writer, objectIndex, primaryRefWriter, secondaryRefWriter, featureTypeWriter, attributeWriter, rangeWriter, aliasWriter);
    }

    @Override
    protected void writeOtherProperties(ModelledFeature object) {
        // nothing to do
    }
}