package psidev.psi.mi.jami.json.nary.elements;

import psidev.psi.mi.jami.json.MIJsonUtils;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Feature;
import psidev.psi.mi.jami.model.FeatureEvidence;

import java.io.IOException;
import java.io.Writer;
import java.util.Iterator;
import java.util.Map;

/**
 * Json writer for modelled features
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>18/07/14</pre>
 */

public class SimpleJsonFeatureEvidenceWriter extends SimpleJsonFeatureWriter<FeatureEvidence>{

    public SimpleJsonFeatureEvidenceWriter(Writer writer, Map<Feature, Integer> processedFeatures,
                                           Map<String, Integer> processedInteractors){
        super(writer, processedFeatures, processedInteractors);
    }

    @Override
    protected void writeOtherProperties(FeatureEvidence object) throws IOException {
        // detection methods
        if (!object.getDetectionMethods().isEmpty()){
            MIJsonUtils.writeSeparator(getWriter());
            MIJsonUtils.writeStartObject("detmethods", getWriter());
            MIJsonUtils.writeOpenArray(getWriter());

            Iterator<CvTerm> methodIterator = object.getDetectionMethods().iterator();
            while (methodIterator.hasNext()){
                getCvWriter().write(methodIterator.next());
                if (methodIterator.hasNext()){
                    MIJsonUtils.writeSeparator(getWriter());
                }
            }

            MIJsonUtils.writeEndArray(getWriter());
        }
    }
}
