package psidev.psi.mi.jami.xml.io.writer.elements.impl.xml25;

import psidev.psi.mi.jami.model.ModelledFeature;
import psidev.psi.mi.jami.xml.cache.PsiXmlObjectCache;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.abstracts.xml25.AbstractXmlFeatureWriter;

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

    @Override
    protected void writeOtherProperties(ModelledFeature object) {
        // nothing to do
    }
}
