package psidev.psi.mi.jami.json.nary;

import psidev.psi.mi.jami.bridges.fetcher.OntologyTermFetcher;
import psidev.psi.mi.jami.datasource.InteractionWriter;
import psidev.psi.mi.jami.exception.MIIOException;
import psidev.psi.mi.jami.factory.options.InteractionWriterOptions;
import psidev.psi.mi.jami.json.IncrementalIdGenerator;
import psidev.psi.mi.jami.json.MIJsonUtils;
import psidev.psi.mi.jami.json.MIJsonWriterOptions;
import psidev.psi.mi.jami.json.elements.JsonElementWriter;
import psidev.psi.mi.jami.json.elements.SimpleJsonInteractorWriter;
import psidev.psi.mi.jami.model.*;

import java.io.*;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Abstract JSON writer for interactions (n-ary json format)
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>03/07/13</pre>
 */

public abstract class AbstractMIJsonWriter<I extends Interaction> implements InteractionWriter<I> {

    private boolean isInitialised = false;
    private Writer writer;
    private Map<String, String> processedInteractors;
    private Map<Feature, Integer> processedFeatures;
    private Map<Entity, Integer> processedParticipants;
    private static final Logger logger = Logger.getLogger("AbstractMIJsonWriter");

    private IncrementalIdGenerator idGenerator;
    private OntologyTermFetcher fetcher;
    private JsonElementWriter<Interactor> interactorWriter;
    private JsonElementWriter<I> interactionWriter;

    public AbstractMIJsonWriter(){
        processedInteractors = new HashMap<String, String>();
        processedFeatures = new HashMap<Feature, Integer>();
        processedParticipants = new HashMap<Entity, Integer>();
        idGenerator = new IncrementalIdGenerator();
    }

    public AbstractMIJsonWriter(File file, OntologyTermFetcher fetcher) throws IOException {

        initialiseFile(file);
        processedInteractors = new HashMap<String, String>();
        processedFeatures = new HashMap<Feature, Integer>();
        processedParticipants = new HashMap<Entity, Integer>();
        idGenerator = new IncrementalIdGenerator();
        if (fetcher == null){
            logger.warning("The ontology fetcher is null so all the features will be listed as otherFeatures");
        }
        this.fetcher = fetcher;
    }

    public AbstractMIJsonWriter(OutputStream output, OntologyTermFetcher fetcher) {

        initialiseOutputStream(output);
        processedInteractors = new HashMap<String, String>();
        processedFeatures = new HashMap<Feature, Integer>();
        processedParticipants = new HashMap<Entity, Integer>();
        idGenerator = new IncrementalIdGenerator();
        if (fetcher == null){
            logger.warning("The ontology fetcher is null so all the features will be listed as otherFeatures");
        }
        this.fetcher = fetcher;
    }

    public AbstractMIJsonWriter(Writer writer, OntologyTermFetcher fetcher) {

        initialiseWriter(writer);
        processedInteractors = new HashMap<String, String>();
        processedFeatures = new HashMap<Feature, Integer>();
        processedParticipants = new HashMap<Entity, Integer>();
        idGenerator = new IncrementalIdGenerator();
        if (fetcher == null){
            logger.warning("The ontology fetcher is null so all the features will be listed as otherFeatures");
        }
        this.fetcher = fetcher;
    }

    protected AbstractMIJsonWriter(Writer writer, OntologyTermFetcher fetcher,
                                   Map<String, String> processedInteractors, Map<Feature, Integer> processedFeatures,
                                   Map<Entity, Integer> processedParticipants, IncrementalIdGenerator idGenerator) {

        initialiseWriter(writer);
        this.processedInteractors = processedInteractors;
        this.processedFeatures = processedFeatures;
        this.processedParticipants = processedParticipants;
        this.idGenerator = idGenerator;
        if (fetcher == null){
            logger.warning("The ontology fetcher is null so all the features will be listed as otherFeatures");
        }
        this.fetcher = fetcher;
    }

    protected AbstractMIJsonWriter(Map<String, String> processedInteractors, Map<Feature, Integer> processedFeatures,
                                   Map<Entity, Integer> processedParticipants, IncrementalIdGenerator idGenerator) {

        this.processedInteractors = processedInteractors;
        this.processedFeatures = processedFeatures;
        this.processedParticipants = processedParticipants;
        this.idGenerator = idGenerator;
    }

    public void initialiseContext(Map<String, Object> options) {
        if (options == null && !isInitialised){
            throw new IllegalArgumentException("The options for the json writer should contain at least "+ InteractionWriterOptions.OUTPUT_OPTION_KEY + " to know where to write the interactions and "+ MIJsonWriterOptions.ONTOLOGY_FETCHER_OPTION_KEY+" to know which OntologyTermFetcher to use.");
        }
        else if (options == null){
            return;
        }
        else if (options.containsKey(InteractionWriterOptions.OUTPUT_OPTION_KEY)){
            Object output = options.get(InteractionWriterOptions.OUTPUT_OPTION_KEY);

            if (output instanceof File){
                try {
                    initialiseFile((File) output);
                } catch (IOException e) {
                    throw new IllegalArgumentException("Impossible to open and write in output file " + ((File)output).getName(), e);
                }
            }
            else if (output instanceof OutputStream){
                initialiseOutputStream((OutputStream) output);
            }
            else if (output instanceof Writer){
                initialiseWriter((Writer) output);
            }
            // suspect a file path
            else if (output instanceof String){
                try {
                    initialiseFile(new File((String)output));
                } catch (IOException e) {
                    throw new IllegalArgumentException("Impossible to open and write in output file " + output, e);
                }
            }
            else {
                throw new IllegalArgumentException("Impossible to write in the provided output "+output.getClass().getName() + ", a File, OuputStream, Writer or file path was expected.");
            }

            if (options.containsKey(MIJsonWriterOptions.ONTOLOGY_FETCHER_OPTION_KEY)){
                setFetcher((OntologyTermFetcher)options.get(MIJsonWriterOptions.ONTOLOGY_FETCHER_OPTION_KEY));
            }
            else{
                logger.warning("The ontology fetcher is null so all the features will be listed as otherFeatures");
            }
        }
        else if (!isInitialised){
            throw new IllegalArgumentException("The options for the json writer should contain at least "+ InteractionWriterOptions.OUTPUT_OPTION_KEY + " to know where to write the interactions and "+ MIJsonWriterOptions.ONTOLOGY_FETCHER_OPTION_KEY+" to know which OntologyTermFetcher to use.");
        }

        isInitialised = true;
    }

    public void start() throws MIIOException {
        if (!isInitialised){
            throw new IllegalStateException("The json writer has not been initialised. The options for the json writer should contain at least "+ InteractionWriterOptions.OUTPUT_OPTION_KEY + " to know where to write the interactions and "+ MIJsonWriterOptions.ONTOLOGY_FETCHER_OPTION_KEY+" to know which OntologyTermFetcher to use.");
        }
        try {
            writeStart();
        } catch (IOException e) {
            throw new MIIOException("Impossible to write start of JSON file", e);
        }
    }

    public void end() throws MIIOException {
        if (!isInitialised){
            throw new IllegalStateException("The json writer has not been initialised. The options for the json writer should contain at least "+ InteractionWriterOptions.OUTPUT_OPTION_KEY + " to know where to write the interactions and "+ MIJsonWriterOptions.ONTOLOGY_FETCHER_OPTION_KEY+" to know which OntologyTermFetcher to use.");
        }
        try {
            writeEnd();
        } catch (IOException e) {
            throw new MIIOException("Impossible to write end of JSON file", e);
        }
    }

    public void write(I interaction) throws MIIOException {
        if (!isInitialised){
            throw new IllegalStateException("The json writer has not been initialised. The options for the json writer should contain at least "+ InteractionWriterOptions.OUTPUT_OPTION_KEY + " to know where to write the interactions and "+ MIJsonWriterOptions.ONTOLOGY_FETCHER_OPTION_KEY+" to know which OntologyTermFetcher to use.");
        }

        try{

            if (!interaction.getParticipants().isEmpty()){

                // write interactors
                Iterator<Participant> pIterator = interaction.getParticipants().iterator();
                while(pIterator.hasNext()){
                    registerAndWriteInteractor(pIterator.next());
                }
                getInteractionWriter().write(interaction);
            }
            else {
                logger.log(Level.WARNING, "Ignore interaction as it does not contain any participants : "+interaction.toString());
            }
        }
        catch (IOException e) {
            throw new MIIOException("Impossible to write " +interaction.toString(), e);
        }
    }

    public void write(Collection<? extends I> interactions) throws MIIOException {
        Iterator<? extends I> binaryIterator = interactions.iterator();
        write(binaryIterator);
    }

    public void write(Iterator<? extends I> interactions) throws MIIOException {
        while(interactions.hasNext()){
            write(interactions.next());
        }
    }

    public void flush() throws MIIOException{
        if (isInitialised){
            try {
                writer.flush();
            } catch (IOException e) {
                throw new MIIOException("Impossible to flush the JSON writer", e);
            }
        }
    }

    public void close() throws MIIOException{
        if (isInitialised){

            try {
                flush();
            }
            finally {
                try {
                    writer.close();
                } catch (IOException e) {
                    throw new MIIOException("Impossible to close the JSON writer", e);
                }
            }
            isInitialised = false;
            writer = null;
            processedInteractors.clear();
            processedFeatures.clear();
            processedParticipants.clear();
            interactionWriter = null;
            interactorWriter = null;
            idGenerator = new IncrementalIdGenerator();
        }
    }

    public void clear(){
        if (isInitialised){
            processedInteractors.clear();
            processedFeatures.clear();
            processedParticipants.clear();
            idGenerator = new IncrementalIdGenerator();
        }
    }

    public void reset() throws MIIOException {
        if (isInitialised){

            try {
                writer.flush();
            }
            catch (IOException e) {
                throw new MIIOException("Impossible to close the JSON writer", e);
            }
            finally {
                isInitialised = false;
                writer = null;
                processedInteractors.clear();
                processedFeatures.clear();
                processedParticipants.clear();
                idGenerator = new IncrementalIdGenerator();
                this.fetcher = null;
                interactionWriter = null;
                interactorWriter = null;
            }
        }
    }

    protected abstract void writeComplex(Complex complex);

    protected void registerAndWriteInteractor(Participant participant) throws IOException {
        if (participant != null){
            Interactor interactor = participant.getInteractor();

            if (interactor instanceof Complex){
                Complex complex = (Complex)interactor;
                // write as an interaction
                if (!complex.getParticipants().isEmpty()){
                     writeComplex(complex);
                }
                // write as an interactor
                else{
                    getInteractorWriter().write(interactor);
                }
            }
            else{
                getInteractorWriter().write(interactor);
            }
        }
    }

    protected void writeStart() throws IOException {
        MIJsonUtils.writeStartObject(writer);
        MIJsonUtils.writePropertyKey("data", writer);
        MIJsonUtils.writeOpenArray(writer);
    }

    protected void writeEnd() throws IOException {
        MIJsonUtils.writeEndArray(writer);
        MIJsonUtils.writeEndObject(writer);
    }

    protected Writer getWriter() {
        return writer;
    }

    private void initialiseWriter(Writer writer) {
        if (writer == null){
            throw new IllegalArgumentException("The writer cannot be null.");
        }

        this.writer = writer;
        isInitialised = true;
    }

    private void initialiseOutputStream(OutputStream output) {
        if (output == null){
            throw new IllegalArgumentException("The output stream cannot be null.");
        }

        this.writer = new OutputStreamWriter(output);
        isInitialised = true;
    }

    private void initialiseFile(File file) throws IOException {
        if (file == null){
            throw new IllegalArgumentException("The file cannot be null.");
        }
        else if (!file.canWrite()){
            throw new IllegalArgumentException("Does not have the permissions to write in file "+file.getAbsolutePath());
        }

        this.writer = new BufferedWriter(new FileWriter(file));
        isInitialised = true;
    }

    protected OntologyTermFetcher getFetcher() {
        return fetcher;
    }

    public JsonElementWriter<Interactor> getInteractorWriter() {
        if (this.interactorWriter == null){
            this.interactorWriter = new SimpleJsonInteractorWriter(this.writer, this.processedInteractors, this.idGenerator);
        }
        return interactorWriter;
    }

    public JsonElementWriter<I> getInteractionWriter() {
        if (this.interactionWriter == null){
             initialiseInteractionWriter();
        }
        return interactionWriter;
    }

    protected abstract void initialiseInteractionWriter();

    protected void setInteractionWriter(JsonElementWriter<I> interactionWriter) {
        this.interactionWriter = interactionWriter;
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

    protected IncrementalIdGenerator getIdGenerator() {
        return idGenerator;
    }

    protected void setFetcher(OntologyTermFetcher fetcher) {
        this.fetcher = fetcher;
    }
}
