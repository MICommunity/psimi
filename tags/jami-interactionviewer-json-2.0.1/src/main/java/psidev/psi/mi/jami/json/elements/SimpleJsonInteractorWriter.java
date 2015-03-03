package psidev.psi.mi.jami.json.elements;

import org.json.simple.JSONValue;
import psidev.psi.mi.jami.json.MIJsonUtils;
import psidev.psi.mi.jami.json.IncrementalIdGenerator;
import psidev.psi.mi.jami.model.*;

import java.io.IOException;
import java.io.Writer;
import java.util.Iterator;
import java.util.Map;

/**
 * Json writer for participants
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>18/07/14</pre>
 */

public class SimpleJsonInteractorWriter implements JsonElementWriter<Interactor>{

    private Writer writer;
    private JsonElementWriter<CvTerm> cvWriter;
    private JsonElementWriter<Xref> identifierWriter;
    private Map<String, String> processedInteractors;
    private IncrementalIdGenerator idGenerator;
    private JsonElementWriter<Organism> organismWriter;

    public SimpleJsonInteractorWriter(Writer writer,  Map<String, String>processedInteractors) {
        if (writer == null) {
            throw new IllegalArgumentException("The json interactor writer needs a non null Writer");
        }
        this.writer = writer;
        if (processedInteractors == null){
            throw new IllegalArgumentException("The json interactor writer needs a non null map of processed interactors");
        }
        this.processedInteractors = processedInteractors;
    }

    public SimpleJsonInteractorWriter(Writer writer,  Map<String, String>processedInteractors, IncrementalIdGenerator idGenerator) {
        if (writer == null) {
            throw new IllegalArgumentException("The json interactor writer needs a non null Writer");
        }
        this.writer = writer;
        if (processedInteractors == null){
            throw new IllegalArgumentException("The json interactor writer needs a non null map of processed interactors");
        }
        this.processedInteractors = processedInteractors;
        this.idGenerator = idGenerator;
    }

    public void write(Interactor object) throws IOException {
        String[] interactorIds = MIJsonUtils.extractInteractorId(object.getPreferredIdentifier(), object);
        String interactorKey = interactorIds[0]+"_"+interactorIds[1];
        // if the interactor has not yet been processed, we write the interactor
        if (!processedInteractors.containsKey(interactorKey)){

            // when the interactor is not the first one, we write an element separator
            if (!processedInteractors.isEmpty()){
                MIJsonUtils.writeSeparator(writer);
            }
            processedInteractors.put(interactorKey, interactorKey);
            MIJsonUtils.writeStartObject(writer);
            MIJsonUtils.writeProperty("object", "interactor", writer);
            // write accession
            MIJsonUtils.writeSeparator(writer);
            MIJsonUtils.writeProperty("id", interactorKey, writer);

            // write sequence if possible
            if (object instanceof Polymer){
                Polymer polymer = (Polymer) object;
                if (polymer.getSequence() != null){
                    MIJsonUtils.writeSeparator(writer);
                    MIJsonUtils.writeProperty("sequence", JSONValue.escape(polymer.getSequence()), writer);
                }
            }
            // write interactor type
            MIJsonUtils.writeSeparator(writer);
            MIJsonUtils.writePropertyKey("type", writer);
            getCvWriter().write(object.getInteractorType());

            // write organism
            if (object.getOrganism() != null){
                MIJsonUtils.writeSeparator(writer);
                MIJsonUtils.writePropertyKey("organism", writer);
                getOrganismWriter().write(object.getOrganism());
            }

            // write accession
            if (object.getPreferredIdentifier() != null){
                MIJsonUtils.writeSeparator(writer);
                MIJsonUtils.writePropertyKey("identifier", writer);
                getIdentifierWriter().write(object.getPreferredIdentifier());
            }

            if (object instanceof InteractorPool){
                InteractorPool pool = (InteractorPool)object;
                if (!pool.isEmpty()){
                    MIJsonUtils.writeSeparator(writer);
                    MIJsonUtils.writePropertyKey("setComponents", writer);
                    MIJsonUtils.writeOpenArray(writer);

                    Iterator<Interactor> interactorIterator = pool.iterator();
                    while (interactorIterator.hasNext()){
                        Interactor interactor = interactorIterator.next();
                        writeInteractorComponent(interactor);

                        if (interactorIterator.hasNext()){
                            MIJsonUtils.writeSeparator(writer);
                        }
                    }

                    MIJsonUtils.writeEndArray(writer);
                }
            }

            // write label
            MIJsonUtils.writeSeparator(writer);
            MIJsonUtils.writeProperty("label", JSONValue.escape(object.getShortName()), writer);
            MIJsonUtils.writeEndObject(writer);
        }
    }

    private void writeInteractorComponent(Interactor object) throws IOException {
        MIJsonUtils.writeStartObject(writer);
        // write accession
        MIJsonUtils.writeProperty("label", JSONValue.escape(object.getShortName()), writer);

        // write sequence if possible
        if (object instanceof Polymer){
            Polymer polymer = (Polymer) object;
            if (polymer.getSequence() != null){
                MIJsonUtils.writeSeparator(writer);
                MIJsonUtils.writeProperty("sequence", JSONValue.escape(polymer.getSequence()), writer);
            }
        }
        // write interactor type
        MIJsonUtils.writeSeparator(writer);
        MIJsonUtils.writePropertyKey("type", writer);
        getCvWriter().write(object.getInteractorType());

        // write organism
        if (object.getOrganism() != null){
            MIJsonUtils.writeSeparator(writer);
            MIJsonUtils.writePropertyKey("organism", writer);
            getOrganismWriter().write(object.getOrganism());
        }

        // write accession
        if (object.getPreferredIdentifier() != null){
            MIJsonUtils.writeSeparator(writer);
            MIJsonUtils.writePropertyKey("identifier", writer);
            getIdentifierWriter().write(object.getPreferredIdentifier());
        }

        // write end object
        MIJsonUtils.writeEndObject(writer);
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

    public IncrementalIdGenerator getIdGenerator() {
        if (this.idGenerator == null){
            this.idGenerator = new IncrementalIdGenerator();
        }
        return idGenerator;
    }

    public void setIdGenerator(IncrementalIdGenerator idGenerator) {
        this.idGenerator = idGenerator;
    }

    public JsonElementWriter<Organism> getOrganismWriter() {
        if (this.organismWriter == null){
             this.organismWriter = new SimpleJsonOrganismWriter(this.writer);
        }
        return organismWriter;
    }

    public void setOrganismWriter(JsonElementWriter<Organism> organismWriter) {
        this.organismWriter = organismWriter;
    }

    public JsonElementWriter<Xref> getIdentifierWriter() {
        if (this.identifierWriter == null){
             this.identifierWriter = new SimpleJsonIdentifierWriter(this.writer);
        }
        return identifierWriter;
    }

    public void setIdentifierWriter(JsonElementWriter<Xref> identifierWriter) {
        this.identifierWriter = identifierWriter;
    }

    protected Writer getWriter() {
        return writer;
    }
}
