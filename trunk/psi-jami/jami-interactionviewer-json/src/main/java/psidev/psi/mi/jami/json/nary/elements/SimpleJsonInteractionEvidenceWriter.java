package psidev.psi.mi.jami.json.nary.elements;

import psidev.psi.mi.jami.json.MIJsonUtils;
import psidev.psi.mi.jami.json.nary.IncrementalIdGenerator;
import psidev.psi.mi.jami.model.*;

import java.io.IOException;
import java.io.Writer;
import java.util.Iterator;
import java.util.Map;

/**
 * Json writer for interaction evidences
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>18/07/14</pre>
 */

public class SimpleJsonInteractionEvidenceWriter extends SimpleJsonInteractionWriter<InteractionEvidence>{

    private JsonElementWriter<InteractionEvidence> experimentWriter;
    private JsonElementWriter<Confidence> confidenceWriter;
    private JsonElementWriter<Parameter> parameterWriter;

    public SimpleJsonInteractionEvidenceWriter(Writer writer, Map<Feature, Integer> processedFeatures,
                                               Map<String, Integer> processedInteractors){
        super(writer, processedFeatures, processedInteractors);
    }

    public SimpleJsonInteractionEvidenceWriter(Writer writer, Map<Feature, Integer> processedFeatures,
                                               Map<String, Integer> processedInteractors, IncrementalIdGenerator idGenerator){
        super(writer, processedFeatures, processedInteractors, idGenerator);
    }

    protected void writeOtherProperties(InteractionEvidence object) throws IOException {

        // write experiment
        getExperimentWriter().write(object);

        // confidences
        if (!object.getConfidences().isEmpty()) {
            MIJsonUtils.writeSeparator(getWriter());
            MIJsonUtils.writePropertyKey("confidences", getWriter());
            MIJsonUtils.writeOpenArray(getWriter());

            Iterator<Confidence> confIterator = object.getConfidences().iterator();
            while (confIterator.hasNext()) {
                getConfidenceWriter().write(confIterator.next());
                if (confIterator.hasNext()) {
                    MIJsonUtils.writeSeparator(getWriter());
                }
            }

            MIJsonUtils.writeEndArray(getWriter());
        }
        // parameters
        if (!object.getParameters().isEmpty()) {
            MIJsonUtils.writeSeparator(getWriter());
            MIJsonUtils.writePropertyKey("parameters", getWriter());
            MIJsonUtils.writeOpenArray(getWriter());

            Iterator<Parameter> paramIterator = object.getParameters().iterator();
            while (paramIterator.hasNext()) {
                getParameterWriter().write(paramIterator.next());
                if (paramIterator.hasNext()) {
                    MIJsonUtils.writeSeparator(getWriter());
                }
            }

            MIJsonUtils.writeEndArray(getWriter());
        }
    }

    protected void initialiseDefaultParticipantWriter() {
        super.setParticipantWriter(new SimpleJsonModelledParticipantWriter(getWriter(), getProcessedFeatures(), getProcessedInteractors(),
                getIdGenerator(), getFetcher()));
        ((SimpleJsonModelledParticipantWriter)getParticipantWriter()).setCvWriter(getCvWriter());
    }

    public JsonElementWriter<InteractionEvidence> getExperimentWriter() {
        if (experimentWriter == null){
            experimentWriter = new SimpleJsonExperimentWriter(getWriter());
            ((SimpleJsonExperimentWriter)experimentWriter).setCvWriter(getCvWriter());
        }
        return experimentWriter;
    }

    public void setExperimentWriter(JsonElementWriter<InteractionEvidence> experimentWriter) {
        this.experimentWriter = experimentWriter;
    }

    public JsonElementWriter<Confidence> getConfidenceWriter() {
        if (this.confidenceWriter == null){
            this.confidenceWriter = new SimpleJsonConfidenceWriter(getWriter());
        }
        return confidenceWriter;
    }

    public void setConfidenceWriter(JsonElementWriter<Confidence> confidenceWriter) {
        this.confidenceWriter = confidenceWriter;
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
