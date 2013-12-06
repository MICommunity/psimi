package psidev.psi.mi.jami.json;

import psidev.psi.mi.jami.binary.BinaryInteractionEvidence;
import psidev.psi.mi.jami.binary.expansion.ComplexExpansionMethod;
import psidev.psi.mi.jami.binary.expansion.InteractionEvidenceSpokeExpansion;
import psidev.psi.mi.jami.bridges.fetcher.OntologyTermFetcher;
import psidev.psi.mi.jami.datasource.InteractionWriter;
import psidev.psi.mi.jami.datasource.InteractionWriterOptions;
import psidev.psi.mi.jami.exception.MIIOException;
import psidev.psi.mi.jami.model.InteractionEvidence;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

/**
 * The jsonWriter which writes the interaction evidences
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>05/07/13</pre>
 */

public class MIJsonEvidenceWriter implements InteractionWriter<InteractionEvidence> {

    private MIJsonBinaryEvidenceWriter binaryWriter;
    private ComplexExpansionMethod<InteractionEvidence, BinaryInteractionEvidence> expansionMethod;
    private int currentExpansionId = 0;

    public MIJsonEvidenceWriter(){
    }

    public MIJsonEvidenceWriter(File file, OntologyTermFetcher fetcher) throws IOException {
        this.binaryWriter = new MIJsonBinaryEvidenceWriter(file, fetcher);
        this.expansionMethod = new InteractionEvidenceSpokeExpansion();
    }

    public MIJsonEvidenceWriter(OutputStream output, OntologyTermFetcher fetcher) {
        this.binaryWriter = new MIJsonBinaryEvidenceWriter(output, fetcher);
        this.expansionMethod = new InteractionEvidenceSpokeExpansion();
    }

    public MIJsonEvidenceWriter(Writer writer, OntologyTermFetcher fetcher) {
        this.binaryWriter = new MIJsonBinaryEvidenceWriter(writer, fetcher);
        this.expansionMethod = new InteractionEvidenceSpokeExpansion();
    }

    public MIJsonEvidenceWriter(File file, OntologyTermFetcher fetcher, ComplexExpansionMethod<InteractionEvidence, BinaryInteractionEvidence> expansionMethod) throws IOException {
        this.binaryWriter = new MIJsonBinaryEvidenceWriter(file, fetcher);
        if(expansionMethod != null){
            this.expansionMethod = expansionMethod;
        }
        else{
            this.expansionMethod = new InteractionEvidenceSpokeExpansion();
        }
    }

    public MIJsonEvidenceWriter(OutputStream output, OntologyTermFetcher fetcher, ComplexExpansionMethod<InteractionEvidence, BinaryInteractionEvidence> expansionMethod) {
        this.binaryWriter = new MIJsonBinaryEvidenceWriter(output, fetcher);
        if(expansionMethod != null){
            this.expansionMethod = expansionMethod;
        }
        else{
            this.expansionMethod = new InteractionEvidenceSpokeExpansion();
        }
    }

    public MIJsonEvidenceWriter(Writer writer, OntologyTermFetcher fetcher, ComplexExpansionMethod<InteractionEvidence, BinaryInteractionEvidence> expansionMethod) {
        this.binaryWriter = new MIJsonBinaryEvidenceWriter(writer, fetcher);
        if(expansionMethod != null){
            this.expansionMethod = expansionMethod;
        }
        else{
            this.expansionMethod = new InteractionEvidenceSpokeExpansion();
        }
    }

    public void initialiseContext(Map<String, Object> options) {
        if (options == null && binaryWriter == null){
            throw new IllegalArgumentException("The options for the json writer should contain at least "+ InteractionWriterOptions.OUTPUT_OPTION_KEY + " to know where to write the interactions and "+ MIJsonWriterOptions.ONTOLOGY_FETCHER_OPTION_KEY+" to know which OntologyTermFetcher to use.");
        }
        else if (options == null){
            return;
        }
        else if (this.binaryWriter == null){
            this.binaryWriter = new MIJsonBinaryEvidenceWriter();
            this.binaryWriter.initialiseContext(options);
        }
        else {
            this.binaryWriter.initialiseContext(options);
        }

        if (options.containsKey(InteractionWriterOptions.COMPLEX_EXPANSION_OPTION_KEY)){
            Object o = options.get(InteractionWriterOptions.COMPLEX_EXPANSION_OPTION_KEY);
            if (o == null){
               this.expansionMethod = new InteractionEvidenceSpokeExpansion();
            }
            else {
                this.expansionMethod = (ComplexExpansionMethod<InteractionEvidence, BinaryInteractionEvidence>)options.get(InteractionWriterOptions.COMPLEX_EXPANSION_OPTION_KEY);
            }
        }
        else{
            this.expansionMethod = new InteractionEvidenceSpokeExpansion();
        }
    }

    public void start() throws MIIOException {
        if (this.binaryWriter == null){
            throw new IllegalStateException("The json writer has not been initialised. The options for the json writer should contain at least "+ InteractionWriterOptions.OUTPUT_OPTION_KEY + " to know where to write the interactions and "+ MIJsonWriterOptions.ONTOLOGY_FETCHER_OPTION_KEY+" to know which OntologyTermFetcher to use.");
        }
        this.binaryWriter.start();
    }

    public void end() throws MIIOException {
        if (this.binaryWriter == null){
            throw new IllegalStateException("The json writer has not been initialised. The options for the json writer should contain at least "+ InteractionWriterOptions.OUTPUT_OPTION_KEY + " to know where to write the interactions and "+ MIJsonWriterOptions.ONTOLOGY_FETCHER_OPTION_KEY+" to know which OntologyTermFetcher to use.");
        }
        this.binaryWriter.end();
    }

    public void write(InteractionEvidence interaction) throws MIIOException {
        if (this.binaryWriter == null){
            throw new IllegalStateException("The json writer has not been initialised. The options for the json writer should contain at least "+ InteractionWriterOptions.OUTPUT_OPTION_KEY + " to know where to write the interactions and "+ MIJsonWriterOptions.ONTOLOGY_FETCHER_OPTION_KEY+" to know which OntologyTermFetcher to use.");
        }

        // reset expansion id
        this.binaryWriter.setExpansionId(null);
        Collection<BinaryInteractionEvidence> binaryInteractions = expansionMethod.expand(interaction);

        // we expanded a n-ary interaction
        if(binaryInteractions.size() > 1){
            this.binaryWriter.setExpansionId(currentExpansionId);
            currentExpansionId++;
        }
        this.binaryWriter.write(binaryInteractions);
    }

    public void write(Collection<? extends InteractionEvidence> interactions) throws MIIOException {
        Iterator<? extends InteractionEvidence> iterator = interactions.iterator();
        write(iterator);
    }

    public void write(Iterator<? extends InteractionEvidence> interactions) throws MIIOException {
        while(interactions.hasNext()){
            write(interactions.next());
        }
    }

    public void flush() throws MIIOException {
        if (binaryWriter != null){
            binaryWriter.flush();
        }
    }

    public void close() throws MIIOException {
        try{
            if (binaryWriter != null){
                binaryWriter.close();
            }
        }
        finally {
            binaryWriter = null;
            currentExpansionId = 0;
            expansionMethod = null;
        }
    }

    public void reset() throws MIIOException {
        try{
            if (binaryWriter != null){
                binaryWriter.reset();
            }
        }
        finally {
            binaryWriter = null;
            currentExpansionId = 0;
            expansionMethod = null;
        }
    }
}
