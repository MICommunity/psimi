package psidev.psi.mi.jami.json.elements;

import org.json.simple.JSONValue;
import psidev.psi.mi.jami.binary.BinaryInteraction;
import psidev.psi.mi.jami.bridges.fetcher.OntologyTermFetcher;
import psidev.psi.mi.jami.json.MIJsonUtils;
import psidev.psi.mi.jami.json.IncrementalIdGenerator;
import psidev.psi.mi.jami.model.*;

import java.io.IOException;
import java.io.Writer;
import java.util.Iterator;
import java.util.Map;

/**
 * Json writer for interactions
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>18/07/14</pre>
 */

public class SimpleJsonInteractionWriter<I extends Interaction> implements JsonElementWriter<I>{

    private Writer writer;
    private JsonElementWriter<CvTerm> cvWriter;
    private JsonElementWriter<Xref> identifierWriter;
    private JsonElementWriter participantWriter;
    private Map<Feature, Integer> processedFeatures;
    private Map<Entity, Integer> processedParticipants;
    private Map<String, String> processedInteractors;
    private IncrementalIdGenerator idGenerator;
    private OntologyTermFetcher fetcher;

    public SimpleJsonInteractionWriter(Writer writer, Map<Feature, Integer> processedFeatures,
                                       Map<String, String> processedInteractors, Map<Entity, Integer> processedParticipants){
        if (writer == null){
            throw new IllegalArgumentException("The json interactions writer needs a non null Writer");
        }
        this.writer = writer;
        if (processedFeatures == null){
            throw new IllegalArgumentException("The json interactions writer needs a non null map of processed features");
        }
        this.processedFeatures = processedFeatures;
        if (processedInteractors == null){
            throw new IllegalArgumentException("The json interactions writer needs a non null map of processed interactors");
        }
        this.processedInteractors = processedInteractors;
        if (processedParticipants == null){
            throw new IllegalArgumentException("The json interactions writer needs a non null map of processed participants");
        }
        this.processedParticipants = processedParticipants;
    }

    public SimpleJsonInteractionWriter(Writer writer, Map<Feature, Integer> processedFeatures,
                                       Map<String, String> processedInteractors, Map<Entity, Integer> processedParticipants,
                                       IncrementalIdGenerator idGenerator){
        this(writer, processedFeatures, processedInteractors, processedParticipants);
        this.idGenerator = idGenerator;
    }

    public void write(I object) throws IOException {
        Xref preferredIdentifier = !object.getIdentifiers().isEmpty() ? (Xref)object.getIdentifiers().iterator().next() : null;
        String[] keyValues = generateInteractionIdentifier(object, preferredIdentifier);
        String id = null;

        // if the interaction has not yet been processed, we write the interactor
        if (!processedInteractors.containsKey(keyValues[0]+"_"+keyValues[1])){
            // when the interactor is not the first one, we write an element separator
            if (!processedInteractors.isEmpty()){
                MIJsonUtils.writeSeparator(writer);
            }
            id = keyValues[0]+"_"+keyValues[1];
            this.processedInteractors.put(keyValues[0]+"_"+keyValues[1], keyValues[0]+"_"+keyValues[1]);

            MIJsonUtils.writeStartObject(writer);
            MIJsonUtils.writeProperty("object","interaction", writer);
            // write accession
            MIJsonUtils.writeSeparator(writer);
            MIJsonUtils.writeProperty("id", id, writer);

            // then interaction type
            if (object.getInteractionType() != null){
                MIJsonUtils.writeSeparator(writer);
                MIJsonUtils.writePropertyKey("interactionType", writer);
                getCvWriter().write(object.getInteractionType());
            }

            writeOtherProperties(object);

            // then interaction identifiers
            if (hasIdentifiers(object)){
                MIJsonUtils.writeSeparator(writer);
                MIJsonUtils.writePropertyKey("identifiers", writer);
                writeAllIdentifiers(object);
            }

            // expansion method if necessary
            if (object instanceof BinaryInteraction){
                BinaryInteraction binary = (BinaryInteraction)object;
                if (binary.getComplexExpansion() != null){
                    MIJsonUtils.writeSeparator(writer);
                    MIJsonUtils.writePropertyKey("expansion", writer);
                    writeExpansionMethod(binary.getComplexExpansion());
                }
            }

            // then participant A and B
            MIJsonUtils.writeSeparator(writer);
            MIJsonUtils.writePropertyKey("participants", writer);
            MIJsonUtils.writeOpenArray(writer);

            Iterator<Participant> participantIterator = object.getParticipants().iterator();
            while (participantIterator.hasNext()){
                getParticipantWriter().write(participantIterator.next());
                if (participantIterator.hasNext()){
                    MIJsonUtils.writeSeparator(writer);
                }
            }
            MIJsonUtils.writeEndArray(writer);

            MIJsonUtils.writeEndObject(writer);

        }
    }

    protected String[] generateInteractionIdentifier(I object, Xref preferredIdentifier) {
        return MIJsonUtils.extractInteractionId(preferredIdentifier, object);
    }

    protected void writeExpansionMethod(CvTerm expansion) throws IOException {
        MIJsonUtils.writeStartObject(writer);
        MIJsonUtils.writeProperty("name", JSONValue.escape(expansion.getShortName()), writer);
        writeOtherExpansionMethodProperties();
        MIJsonUtils.writeEndObject(writer);
    }

    protected void writeOtherExpansionMethodProperties() throws IOException {
        // nothing to do here
    }

    protected void writeAllIdentifiers(I object) throws IOException {
        if (!object.getIdentifiers().isEmpty()){
            MIJsonUtils.writeOpenArray(writer);

            Iterator<Xref> identifierIterator = object.getIdentifiers().iterator();
            while (identifierIterator.hasNext()){
                getIdentifierWriter().write(identifierIterator.next());

                if (identifierIterator.hasNext()){
                    MIJsonUtils.writeSeparator(writer);
                }
            }

            writeOtherIdentifiers(object);
            MIJsonUtils.writeEndArray(writer);
        }
    }

    protected void writeOtherIdentifiers(I object) throws IOException {
        // to be overridden
    }

    protected boolean hasIdentifiers(I object) {
        return !object.getIdentifiers().isEmpty();
    }

    protected void writeOtherProperties(I object) throws IOException {
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

    public JsonElementWriter<Xref> getIdentifierWriter() {
        if (this.identifierWriter == null){
            this.identifierWriter = new SimpleJsonIdentifierWriter(writer);
        }
        return identifierWriter;
    }

    public void setIdentifierWriter(JsonElementWriter<Xref> identifierWriter) {
        this.identifierWriter = identifierWriter;
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

    public JsonElementWriter getParticipantWriter() {
        if (this.participantWriter == null){
            initialiseDefaultParticipantWriter();
        }
        return participantWriter;
    }

    protected void initialiseDefaultParticipantWriter() {
        this.participantWriter = new SimpleJsonParticipantWriter(this.writer, this.processedFeatures, this.processedInteractors,
                this.processedParticipants, getIdGenerator(), this.fetcher);
        ((SimpleJsonParticipantWriter)this.participantWriter).setCvWriter(getCvWriter());
    }

    public void setParticipantWriter(JsonElementWriter participantWriter) {
        this.participantWriter = participantWriter;
    }

    public OntologyTermFetcher getFetcher() {
        return fetcher;
    }

    public void setFetcher(OntologyTermFetcher fetcher) {
        this.fetcher = fetcher;
    }

    protected Writer getWriter() {
        return writer;
    }

    protected Map<String, String> getProcessedInteractors() {
        return processedInteractors;
    }

    protected Map<Feature, Integer> getProcessedFeatures() {
        return processedFeatures;
    }

    protected Map<Entity, Integer> getProcessedParticipants() {
        return processedParticipants;
    }
}
