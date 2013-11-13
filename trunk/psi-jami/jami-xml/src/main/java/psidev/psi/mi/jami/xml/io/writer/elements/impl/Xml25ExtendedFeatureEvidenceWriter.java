package psidev.psi.mi.jami.xml.io.writer.elements.impl;

import org.codehaus.stax2.XMLStreamWriter2;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.xml.PsiXml25ObjectIndex;
import psidev.psi.mi.jami.xml.extension.ExtendedPsi25FeatureEvidence;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXml25ElementWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXml25XrefWriter;
import psidev.psi.mi.jami.xml.utils.PsiXml25Utils;

import javax.xml.stream.XMLStreamException;

/**
 * Writer for extended feature evidence having experiment references
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>13/11/13</pre>
 */

public class Xml25ExtendedFeatureEvidenceWriter extends Xml25NamedFeatureEvidenceWriter{
    public Xml25ExtendedFeatureEvidenceWriter(XMLStreamWriter2 writer, PsiXml25ObjectIndex objectIndex) {
        super(writer, objectIndex);
    }

    public Xml25ExtendedFeatureEvidenceWriter(XMLStreamWriter2 writer, PsiXml25ObjectIndex objectIndex, PsiXml25XrefWriter primaryRefWriter, PsiXml25XrefWriter secondaryRefWriter,
                                              PsiXml25ElementWriter<CvTerm> featureTypeWriter, PsiXml25ElementWriter<Annotation> attributeWriter,
                                              PsiXml25ElementWriter<Range> rangeWriter, PsiXml25ElementWriter<CvTerm> detectionMethodWriter,
                                              PsiXml25ElementWriter<Alias> aliasWriter) {
        super(writer, objectIndex, primaryRefWriter, secondaryRefWriter, featureTypeWriter, attributeWriter, rangeWriter, detectionMethodWriter, aliasWriter);
    }

    @Override
    protected void writeOtherProperties(FeatureEvidence object) throws XMLStreamException {
        // write detection method
        super.writeOtherProperties(object);
        ExtendedPsi25FeatureEvidence extendedFeature = (ExtendedPsi25FeatureEvidence)object;
        // write experiment refs
        if (!extendedFeature.getExperiments().isEmpty()){
            getStreamWriter().writeCharacters(PsiXml25Utils.LINE_BREAK);
            getStreamWriter().writeStartElement("experimentRefList");
            getStreamWriter().writeCharacters(PsiXml25Utils.LINE_BREAK);
            for (Experiment exp : extendedFeature.getExperiments()){
                getStreamWriter().writeStartElement("experimentRef");
                getStreamWriter().writeCharacters(Integer.toString(getObjectIndex().extractIdFor(exp)));
                getStreamWriter().writeCharacters(PsiXml25Utils.LINE_BREAK);
            }
        }
    }
}
