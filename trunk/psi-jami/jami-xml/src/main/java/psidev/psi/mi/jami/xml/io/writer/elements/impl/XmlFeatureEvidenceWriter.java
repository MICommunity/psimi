package psidev.psi.mi.jami.xml.io.writer.elements.impl;

import psidev.psi.mi.jami.xml.cache.PsiXmlObjectCache;
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
}
