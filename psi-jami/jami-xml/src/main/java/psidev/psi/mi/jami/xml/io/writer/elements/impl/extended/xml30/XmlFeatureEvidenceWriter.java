package psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.xml30;

import psidev.psi.mi.jami.model.Experiment;
import psidev.psi.mi.jami.model.FeatureEvidence;
import psidev.psi.mi.jami.xml.cache.PsiXmlObjectCache;
import psidev.psi.mi.jami.xml.model.extension.ExtendedPsiXmlFeatureEvidence;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

/**
 * Writer for extended feature evidence having experiment references
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>13/11/13</pre>
 */

public class XmlFeatureEvidenceWriter extends psidev.psi.mi.jami.xml.io.writer.elements.impl.Xml30FeatureEvidenceWriter {
    public XmlFeatureEvidenceWriter(XMLStreamWriter writer, PsiXmlObjectCache objectIndex) {
        super(writer, objectIndex);
    }

    @Override
    protected void writeOtherProperties(FeatureEvidence object) throws XMLStreamException {
        // write detection method
        super.writeOtherProperties(object);
        if (object instanceof ExtendedPsiXmlFeatureEvidence){
            ExtendedPsiXmlFeatureEvidence extendedFeature = (ExtendedPsiXmlFeatureEvidence)object;
            // write experiment refs
            if (!extendedFeature.getExperiments().isEmpty()){
                getStreamWriter().writeStartElement("experimentRefList");
                for (Experiment exp : extendedFeature.getExperiments()){
                    getStreamWriter().writeStartElement("experimentRef");
                    getStreamWriter().writeCharacters(Integer.toString(getObjectIndex().extractIdForExperiment(exp)));
                    getStreamWriter().writeEndElement();
                }
                getStreamWriter().writeEndElement();
            }
        }
    }
}
