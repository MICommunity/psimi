package psidev.psi.mi.jami.json.nary.elements;

import psidev.psi.mi.jami.json.MIJsonUtils;
import psidev.psi.mi.jami.json.nary.IncrementalIdGenerator;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Feature;
import psidev.psi.mi.jami.model.FeatureEvidence;
import psidev.psi.mi.jami.model.Parameter;

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

    private JsonElementWriter<Parameter> parameterWriter;

    public SimpleJsonFeatureEvidenceWriter(Writer writer, Map<Feature, Integer> processedFeatures,
                                           Map<String, String> processedInteractors){
        super(writer, processedFeatures, processedInteractors);
    }

    public SimpleJsonFeatureEvidenceWriter(Writer writer, Map<Feature, Integer> processedFeatures, Map<String, String> processedInteractors,
                                           IncrementalIdGenerator idGenerator) {
        super(writer, processedFeatures, processedInteractors, idGenerator);
    }

    @Override
    protected void writeOtherProperties(FeatureEvidence object) throws IOException {
        // detection methods
        if (!object.getDetectionMethods().isEmpty()){
            MIJsonUtils.writeSeparator(getWriter());
            MIJsonUtils.writePropertyKey("detmethods", getWriter());
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

        // parameters
        if (!object.getParameters().isEmpty()){
            MIJsonUtils.writeSeparator(getWriter());
            MIJsonUtils.writePropertyKey("parameters", getWriter());
            MIJsonUtils.writeOpenArray(getWriter());

            Iterator<Parameter> paramIterator = object.getParameters().iterator();
            while (paramIterator.hasNext()){
                getParameterWriter().write(paramIterator.next());
                if (paramIterator.hasNext()){
                    MIJsonUtils.writeSeparator(getWriter());
                }
            }

            MIJsonUtils.writeEndArray(getWriter());
        }
    }

    public JsonElementWriter<Parameter> getParameterWriter() {
        if (this.parameterWriter == null){
           this.parameterWriter = new SimpleJsonParameterWriter(getWriter());
        }
        return parameterWriter;
    }

    public void setParameterWriter(JsonElementWriter<Parameter> parameterWriter) {
        this.parameterWriter = parameterWriter;
    }
}
