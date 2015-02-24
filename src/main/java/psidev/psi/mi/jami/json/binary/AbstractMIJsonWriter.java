package psidev.psi.mi.jami.json.binary;

import psidev.psi.mi.jami.binary.BinaryInteraction;
import psidev.psi.mi.jami.binary.expansion.ComplexExpansionMethod;
import psidev.psi.mi.jami.bridges.fetcher.OntologyTermFetcher;
import psidev.psi.mi.jami.datasource.InteractionWriter;
import psidev.psi.mi.jami.exception.ComplexExpansionException;
import psidev.psi.mi.jami.exception.MIIOException;
import psidev.psi.mi.jami.factory.options.InteractionWriterOptions;
import psidev.psi.mi.jami.json.MIJsonWriterOptions;
import psidev.psi.mi.jami.model.Interaction;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

/**
 * The jsonWriter which writes the interactions
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>05/07/13</pre>
 */

public abstract class AbstractMIJsonWriter<I extends Interaction, B extends BinaryInteraction> implements InteractionWriter<I> {

    private AbstractMIJsonBinaryWriter<B> binaryWriter;
    private ComplexExpansionMethod<I, B> expansionMethod;
    private int currentExpansionId = 0;

    public AbstractMIJsonWriter(){
    }

    public AbstractMIJsonWriter(File file, OntologyTermFetcher fetcher) throws IOException {
        initialiseBinaryWriter(file, fetcher);
        initialiseDefaultExpansionMethod();
    }

    public AbstractMIJsonWriter(OutputStream output, OntologyTermFetcher fetcher) {
        initialiseBinaryWriter(output, fetcher);
        initialiseDefaultExpansionMethod();
    }

    public AbstractMIJsonWriter(Writer writer, OntologyTermFetcher fetcher) {
        initialiseBinaryWriter(writer, fetcher);
        initialiseDefaultExpansionMethod();
    }

    public AbstractMIJsonWriter(File file, OntologyTermFetcher fetcher, ComplexExpansionMethod<I, B> expansionMethod) throws IOException {
        initialiseBinaryWriter(file, fetcher);
        if(expansionMethod != null){
            this.expansionMethod = expansionMethod;
        }
        else{
            initialiseDefaultExpansionMethod();
        }
    }

    public AbstractMIJsonWriter(OutputStream output, OntologyTermFetcher fetcher, ComplexExpansionMethod<I, B> expansionMethod) {
        initialiseBinaryWriter(output, fetcher);
        if(expansionMethod != null){
            this.expansionMethod = expansionMethod;
        }
        else{
            initialiseDefaultExpansionMethod();
        }
    }

    public AbstractMIJsonWriter(Writer writer, OntologyTermFetcher fetcher, ComplexExpansionMethod<I, B> expansionMethod) {
        initialiseBinaryWriter(writer, fetcher);
        if(expansionMethod != null){
            this.expansionMethod = expansionMethod;
        }
        else{
            initialiseDefaultExpansionMethod();
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
            initialiseDefaultBinaryWriter();
            this.binaryWriter.initialiseContext(options);
        }
        else {
            this.binaryWriter.initialiseContext(options);
        }

        if (options.containsKey(InteractionWriterOptions.COMPLEX_EXPANSION_OPTION_KEY)){
            Object o = options.get(InteractionWriterOptions.COMPLEX_EXPANSION_OPTION_KEY);
            if (o == null){
                initialiseDefaultExpansionMethod();
            }
            else {
                this.expansionMethod = (ComplexExpansionMethod<I, B>)options.get(InteractionWriterOptions.COMPLEX_EXPANSION_OPTION_KEY);
            }
        }
        else{
            initialiseDefaultExpansionMethod();
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

    public void write(I interaction) throws MIIOException {
        if (this.binaryWriter == null){
            throw new IllegalStateException("The json writer has not been initialised. The options for the json writer should contain at least "+ InteractionWriterOptions.OUTPUT_OPTION_KEY + " to know where to write the interactions and "+ MIJsonWriterOptions.ONTOLOGY_FETCHER_OPTION_KEY+" to know which OntologyTermFetcher to use.");
        }

        // reset expansion id
        this.binaryWriter.setExpansionId(null);
        Collection<B> binaryInteractions = null;
        try {
            binaryInteractions = expansionMethod.expand(interaction);
            // we expanded a n-ary interaction
            if(binaryInteractions.size() >= 1){
                this.binaryWriter.setExpansionId(currentExpansionId);
                currentExpansionId++;
            }
            this.binaryWriter.write(binaryInteractions);
        } catch (ComplexExpansionException e) {
            // do not write anything as this interaction cannot be expanded
        }
    }

    public void write(Collection<? extends I> interactions) throws MIIOException {
        Iterator<? extends I> iterator = interactions.iterator();
        write(iterator);
    }

    public void write(Iterator<? extends I> interactions) throws MIIOException {
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

    protected AbstractMIJsonBinaryWriter<B> getBinaryWriter() {
        return binaryWriter;
    }

    protected void setBinaryWriter(AbstractMIJsonBinaryWriter<B> binaryWriter) {
        this.binaryWriter = binaryWriter;
    }

    protected void setExpansionMethod(ComplexExpansionMethod<I, B> expansionMethod) {
        this.expansionMethod = expansionMethod;
    }

    protected abstract void initialiseDefaultExpansionMethod();
    protected abstract void initialiseBinaryWriter(File file, OntologyTermFetcher fetcher) throws IOException;
    protected abstract void initialiseBinaryWriter(OutputStream output, OntologyTermFetcher fetcher);
    protected abstract void initialiseBinaryWriter(Writer writer, OntologyTermFetcher fetcher);
    protected abstract void initialiseDefaultBinaryWriter();
}
