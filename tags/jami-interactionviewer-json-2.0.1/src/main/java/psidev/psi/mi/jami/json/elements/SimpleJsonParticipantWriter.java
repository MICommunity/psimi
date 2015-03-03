package psidev.psi.mi.jami.json.elements;

import psidev.psi.mi.jami.bridges.fetcher.OntologyTermFetcher;
import psidev.psi.mi.jami.json.IncrementalIdGenerator;
import psidev.psi.mi.jami.json.MIJsonUtils;
import psidev.psi.mi.jami.model.*;

import java.io.IOException;
import java.io.Writer;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Json writer for participants
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>18/07/14</pre>
 */

public class SimpleJsonParticipantWriter<P extends Participant> implements JsonElementWriter<P>{

    private Writer writer;
    private JsonElementWriter<Stoichiometry> stoichiometryWriter;
    private JsonElementWriter<CvTerm> cvWriter;
    private JsonElementWriter featureWriter;
    private Map<Feature, Integer> processedFeatures;
    private Map<Entity, Integer> processedParticipants;
    private Map<String, String> processedInteractors;
    private IncrementalIdGenerator idGenerator;
    private OntologyTermFetcher fetcher;

    private static final Logger logger = Logger.getLogger("SimpleJsonParticipantWriter");

    public SimpleJsonParticipantWriter(Writer writer, Map<Feature, Integer> processedFeatures,
                                       Map<String, String> processedInteractors, Map<Entity, Integer> processedParticipants){
        if (writer == null){
            throw new IllegalArgumentException("The json participant writer needs a non null Writer");
        }
        this.writer = writer;
        if (processedFeatures == null){
            throw new IllegalArgumentException("The json participant writer needs a non null map of processed features");
        }
        this.processedFeatures = processedFeatures;
        if (processedInteractors == null){
            throw new IllegalArgumentException("The json participant writer needs a non null map of processed interactors");
        }
        this.processedInteractors = processedInteractors;
        if (processedParticipants == null){
            throw new IllegalArgumentException("The json participant writer needs a non null map of processed participants");
        }
        this.processedParticipants = processedParticipants;
    }

    public SimpleJsonParticipantWriter(Writer writer, Map<Feature, Integer> processedFeatures,
                                       Map<String, String> processedInteractors, Map<Entity, Integer> processedParticipants,
                                       IncrementalIdGenerator idGenerator,
                                       OntologyTermFetcher fetcher){
        this(writer, processedFeatures, processedInteractors, processedParticipants);
        if (fetcher == null){
            logger.warning("The ontology fetcher is null so all the features will be listed as otherFeatures");
        }
        this.fetcher = fetcher;
        this.idGenerator = idGenerator;
    }

    public void write(P object) throws IOException {

        MIJsonUtils.writeStartObject(writer);
        // generate participant id
        int id = 0;
        if (this.processedParticipants.containsKey(object)){
            id = this.processedParticipants.get(object);
        }
        else{
            id = getIdGenerator().nextId();
            this.processedParticipants.put(object, id);
        }
        MIJsonUtils.writeProperty("id", Integer.toString(id), writer);

        String [] keyValues;
        // write interactor ref
        MIJsonUtils.writeSeparator(writer);
        if (object.getInteractor() instanceof Complex){
            Interactor interactor = object.getInteractor();
            // interactor are always processed before so the map must contain this key
            keyValues = MIJsonUtils.extractInteractionId(interactor.getPreferredIdentifier(), (Complex)interactor);
        }
        else{
            Interactor interactor = object.getInteractor();
            // interactor are always processed before so the map must contain this key
            keyValues = MIJsonUtils.extractInteractorId(interactor.getPreferredIdentifier(), interactor);
        }

        MIJsonUtils.writeProperty("interactorRef",
                this.processedInteractors.get(keyValues[0] + "_" + keyValues[1]), this.writer);

        // write stoichiometry
        getStoichiometryWriter().write(object.getStoichiometry());

        // write biorole
        MIJsonUtils.writeSeparator(writer);
        MIJsonUtils.writePropertyKey("bioRole", writer);
        getCvWriter().write(object.getBiologicalRole());

        writeOtherProperties(object);

        // features
        if (!object.getFeatures().isEmpty()){
            writeAllFeatures(object.getFeatures());
        }

        MIJsonUtils.writeEndObject(writer);
    }

    protected <F extends Feature> void writeAllFeatures(Collection<F> features) throws IOException {

        writeFeatures("features", features);
    }

    protected <F extends Feature> void writeFeatures(String name, Collection<F> features) throws IOException {
        MIJsonUtils.writeSeparator(writer);
        MIJsonUtils.writePropertyKey(name, writer);
        MIJsonUtils.writeOpenArray(writer);

        Iterator<F> featureIterator = features.iterator();
        while (featureIterator.hasNext()){
            getFeatureWriter().write(featureIterator.next());
            if (featureIterator.hasNext()){
                MIJsonUtils.writeSeparator(writer);
            }
        }

        MIJsonUtils.writeEndArray(writer);
    }

    protected void writeOtherProperties(P object) throws IOException {
        // nothing to write here but can be overridden
    }

    public JsonElementWriter<CvTerm> getCvWriter() {
        if (this.cvWriter == null){
            this.cvWriter = new SimpleJsonCvTermWriter(writer);
        }
        return cvWriter;
    }

    public void setCvWriter(JsonElementWriter<CvTerm> cvWriter) {
        this.cvWriter = cvWriter;
    }

    public JsonElementWriter getFeatureWriter() {
        if (this.featureWriter == null){
            initialiseDefaultFeatureWriter();
        }
        return featureWriter;
    }

    protected void initialiseDefaultFeatureWriter() {
        this.featureWriter = new SimpleJsonFeatureWriter(this.writer, this.processedFeatures, this.processedInteractors,
                this.processedParticipants, getIdGenerator(), this.fetcher);
        ((SimpleJsonFeatureWriter)this.featureWriter).setCvWriter(getCvWriter());
    }

    public void setFeatureWriter(JsonElementWriter featureWriter) {
        this.featureWriter = featureWriter;
    }

    public IncrementalIdGenerator getIdGenerator() {
        if (this.idGenerator == null){
            this.idGenerator = new IncrementalIdGenerator();
        }
        return idGenerator;
    }

    public void setIdGenerator(IncrementalIdGenerator idGenerator) {
        this.idGenerator = idGenerator;
    }

    public JsonElementWriter<Stoichiometry> getStoichiometryWriter() {
        if (this.stoichiometryWriter == null){
            this.stoichiometryWriter = new SimpleJsonStoichiometryWriter(this.writer);
        }
        return stoichiometryWriter;
    }

    public void setStoichiometryWriter(JsonElementWriter<Stoichiometry> stoichiometryWriter) {
        this.stoichiometryWriter = stoichiometryWriter;
    }

    protected Writer getWriter() {
        return writer;
    }

    protected Map<Feature, Integer> getProcessedFeatures() {
        return processedFeatures;
    }

    protected Map<String, String> getProcessedInteractors() {
        return processedInteractors;
    }

    protected Map<Entity, Integer> getProcessedParticipants() {
        return processedParticipants;
    }

    protected OntologyTermFetcher getFetcher() {
        return fetcher;
    }
}
