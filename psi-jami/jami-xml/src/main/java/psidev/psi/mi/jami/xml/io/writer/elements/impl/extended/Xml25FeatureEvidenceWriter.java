package psidev.psi.mi.jami.xml.io.writer.elements.impl.extended;

import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.xml.cache.PsiXmlObjectCache;
import psidev.psi.mi.jami.xml.model.extension.ExtendedPsi25FeatureEvidence;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXmlElementWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXmlXrefWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.XmlFeatureEvidenceWriter;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

/**
 * Writer for extended feature evidence having experiment references
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>13/11/13</pre>
 */

public class Xml25FeatureEvidenceWriter extends XmlFeatureEvidenceWriter {
    public Xml25FeatureEvidenceWriter(XMLStreamWriter writer, PsiXmlObjectCache objectIndex) {
        super(writer, objectIndex);
    }

    public Xml25FeatureEvidenceWriter(XMLStreamWriter writer, PsiXmlObjectCache objectIndex,
                                      PsiXmlElementWriter<Alias> aliasWriter, PsiXmlXrefWriter primaryRefWriter,
                                      PsiXmlXrefWriter secondaryRefWriter, PsiXmlElementWriter<CvTerm> featureTypeWriter,
                                      PsiXmlElementWriter<CvTerm> detectionMethodWriter, PsiXmlElementWriter<Range> rangeWriter,
                                      PsiXmlElementWriter<Annotation> attributeWriter) {
        super(writer, objectIndex, aliasWriter, primaryRefWriter, secondaryRefWriter, featureTypeWriter, detectionMethodWriter, rangeWriter, attributeWriter);
    }

    @Override
    protected void writeOtherProperties(FeatureEvidence object) throws XMLStreamException {
        // write detection method
        super.writeOtherProperties(object);
        ExtendedPsi25FeatureEvidence extendedFeature = (ExtendedPsi25FeatureEvidence)object;
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
