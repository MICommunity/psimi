package psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.xml30;

import psidev.psi.mi.jami.model.Experiment;
import psidev.psi.mi.jami.model.FeatureEvidence;
import psidev.psi.mi.jami.xml.cache.PsiXmlObjectCache;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.XmlCvTermWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.XmlDbXrefWriter;
import psidev.psi.mi.jami.xml.model.extension.ExtendedPsiXmlFeatureEvidence;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

/**
 * Writer for expanded feature evidence having experiment references
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>13/11/13</pre>
 */

public class XmlFeatureEvidenceWriter extends psidev.psi.mi.jami.xml.io.writer.elements.impl.xml30.XmlFeatureEvidenceWriter {
    public XmlFeatureEvidenceWriter(XMLStreamWriter writer, PsiXmlObjectCache objectIndex) {
        super(writer, objectIndex);
    }

    @Override
    protected void initialiseXrefWriter(){
        super.setXrefWriter(new XmlDbXrefWriter(getStreamWriter()));
    }

    @Override
    protected void initialiseRangeWriter() {
        super.setRangeWriter(new XmlRangeWriter(getStreamWriter(), getObjectIndex()));
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

    @Override
    protected void initialiseFeatureTypeWriter() {
        super.setFeatureTypeWriter(new XmlCvTermWriter(getStreamWriter()));
    }
}
