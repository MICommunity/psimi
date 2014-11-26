package psidev.psi.mi.jami.json.nary.elements;

import psidev.psi.mi.jami.bridges.fetcher.OntologyTermFetcher;
import psidev.psi.mi.jami.json.MIJsonUtils;
import psidev.psi.mi.jami.json.nary.IncrementalIdGenerator;
import psidev.psi.mi.jami.model.*;

import java.io.IOException;
import java.io.Writer;
import java.util.Iterator;
import java.util.Map;

/**
 * Json writer for participant evidences
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>18/07/14</pre>
 */

public class SimpleJsonParticipantEvidenceWriter extends SimpleJsonParticipantWriter<ParticipantEvidence>{

    private JsonElementWriter<Parameter> parameterWriter;
    private JsonElementWriter<Confidence> confidenceWriter;
    private JsonElementWriter<Organism> hostOrganismWriter;

    public SimpleJsonParticipantEvidenceWriter(Writer writer, Map<Feature, Integer> processedFeatures,
                                               Map<String, String> processedInteractors, Map<Entity, Integer> processedParticipants) {
        super(writer, processedFeatures, processedInteractors, processedParticipants);
    }

    public SimpleJsonParticipantEvidenceWriter(Writer writer, Map<Feature, Integer> processedFeatures,
                                               Map<String, String> processedInteractors, Map<Entity, Integer> processedParticipants, IncrementalIdGenerator idGenerator, OntologyTermFetcher fetcher) {
        super(writer, processedFeatures, processedInteractors, processedParticipants, idGenerator, fetcher);
    }

    @Override
    protected void initialiseDefaultFeatureWriter() {
        super.setFeatureWriter(new SimpleJsonFeatureEvidenceWriter(getWriter(), getProcessedFeatures(), getProcessedInteractors(),
                getProcessedParticipants(), getIdGenerator()));
        ((SimpleJsonFeatureEvidenceWriter)getFeatureWriter()).setCvWriter(getCvWriter());
        ((SimpleJsonFeatureEvidenceWriter)getFeatureWriter()).setParameterWriter(getParameterWriter());
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

    public JsonElementWriter<Confidence> getConfidenceWriter() {
        if (this.confidenceWriter == null){
            this.confidenceWriter = new SimpleJsonConfidenceWriter(getWriter());
        }
        return confidenceWriter;
    }

    public void setConfidenceWriter(JsonElementWriter<Confidence> confidenceWriter) {
        this.confidenceWriter = confidenceWriter;
    }

    @Override
    protected void writeOtherProperties(ParticipantEvidence object) throws IOException {

        // write expRole
        MIJsonUtils.writeSeparator(getWriter());
        MIJsonUtils.writePropertyKey("expRole", getWriter());
        getCvWriter().write(object.getExperimentalRole());

        // identification methods
        if (!object.getIdentificationMethods().isEmpty()){
            MIJsonUtils.writeSeparator(getWriter());
            MIJsonUtils.writePropertyKey("identificationMethods", getWriter());
            MIJsonUtils.writeOpenArray(getWriter());

            Iterator<CvTerm> methodIterator = object.getIdentificationMethods().iterator();
            while (methodIterator.hasNext()){
                getCvWriter().write(methodIterator.next());
                if (methodIterator.hasNext()){
                    getWriter().write(MIJsonUtils.ELEMENT_SEPARATOR);
                }
            }

            MIJsonUtils.writeEndArray(getWriter());
        }

        // expressed in
        if (object.getExpressedInOrganism() != null){
            MIJsonUtils.writeSeparator(getWriter());
            MIJsonUtils.writePropertyKey("expressedIn", getWriter());
            getHostOrganismWriter().write(object.getExpressedInOrganism());
        }

        // confidences
        if (!object.getConfidences().isEmpty()){
            MIJsonUtils.writeSeparator(getWriter());
            MIJsonUtils.writePropertyKey("confidences", getWriter());
            MIJsonUtils.writeOpenArray(getWriter());

            Iterator<Confidence> confIterator = object.getConfidences().iterator();
            while (confIterator.hasNext()){
                getConfidenceWriter().write(confIterator.next());
                if (confIterator.hasNext()){
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

    public JsonElementWriter<Organism> getHostOrganismWriter() {
        if (this.hostOrganismWriter == null){
            this.hostOrganismWriter = new SimpleJsonHostOrganismWriter(getWriter());
            ((SimpleJsonHostOrganismWriter)hostOrganismWriter).setCvWriter(getCvWriter());
        }
        return hostOrganismWriter;
    }

    public void setHostOrganismWriter(JsonElementWriter<Organism> hostOrganismWriter) {
        this.hostOrganismWriter = hostOrganismWriter;
    }
}
