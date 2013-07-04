package psidev.psi.mi.jami.json;

import psidev.psi.mi.jami.binary.BinaryInteractionEvidence;
import psidev.psi.mi.jami.datasource.InteractionWriter;
import psidev.psi.mi.jami.exception.DataSourceWriterException;
import psidev.psi.mi.jami.factory.InteractionWriterFactory;
import psidev.psi.mi.jami.model.*;

import java.io.*;
import java.util.*;

/**
 * JSON writer for InteractionEvidences
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>03/07/13</pre>
 */

public class JsonBinaryWriter implements InteractionWriter<BinaryInteractionEvidence> {

    private boolean isInitialised = false;
    private Writer writer;
    private boolean hasOpened = false;
    public final static String OPEN = "{";
    public final static String CLOSE = "}";
    public final static String OPEN_ARRAY = "[";
    public final static String CLOSE_ARRAY = "]";
    public final static String PROPERTY_DELIMITER = "\"";
    public final static String PROPERTY_VALUE_SEPARATOR = ":";
    public final static String ELEMENT_SEPARATOR = ",";
    public final static String LINE_SEPARATOR = System.getProperty("line.separator");
    public final static String INDENT = "\t";
    private Set<String> processedInteractors;
    private StringBuilder interactionBuilder;

    public JsonBinaryWriter(){
        processedInteractors = new HashSet<String>();
        interactionBuilder = new StringBuilder(2048);
    }

    public JsonBinaryWriter(File file) throws IOException {

        initialiseFile(file);
        processedInteractors = new HashSet<String>();
        interactionBuilder = new StringBuilder(2048);
    }

    public JsonBinaryWriter(OutputStream output) {

        initialiseOutputStream(output);
        processedInteractors = new HashSet<String>();
        interactionBuilder = new StringBuilder(2048);
    }

    public JsonBinaryWriter(Writer writer) {

        initialiseWriter(writer);
        processedInteractors = new HashSet<String>();
        interactionBuilder = new StringBuilder(2048);
    }

    public void initialiseContext(Map<String, Object> options) {
        if (options == null && !isInitialised){
            throw new IllegalArgumentException("The options for the json writer should contain at least "+ InteractionWriterFactory.OUTPUT_OPTION_KEY + " to know where to write the interactions.");
        }
        else if (options == null){
            return;
        }
        else if (options.containsKey(InteractionWriterFactory.OUTPUT_OPTION_KEY)){
            Object output = options.get(InteractionWriterFactory.OUTPUT_OPTION_KEY);

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
        }
        else if (!isInitialised){
            throw new IllegalArgumentException("The options for the json writer should contain at least "+ InteractionWriterFactory.OUTPUT_OPTION_KEY + " to know where to write the interactions.");
        }

        isInitialised = true;
    }

    public void write(BinaryInteractionEvidence interaction) throws DataSourceWriterException {
        if (!isInitialised){
            throw new IllegalArgumentException("The json writer has not been initialised. The options for the json writer should contain at least "+ InteractionWriterFactory.OUTPUT_OPTION_KEY + " to know where to write the interactions.");
        }

        try{
            ParticipantEvidence A = interaction.getParticipantA();
            ParticipantEvidence B = interaction.getParticipantB();

            if (A != null || B != null){
                // write start element and interactor and beginning of interaction
                if (!hasOpened){
                    writeStart();

                    if (A != null && B != null){
                        registerAndWriteInteractor(A, false);
                        registerAndWriteInteractor(B, true);
                    }
                    else if (A != null){
                        registerAndWriteInteractor(A, false);
                    }
                    else {
                        registerAndWriteInteractor(B, false);
                    }

                    appendStartInteractions();
                }
                // write interactors
                else if (A != null && B != null){
                    registerAndWriteInteractor(A, true);
                    registerAndWriteInteractor(B, true);
                }
                else if (A != null){
                    registerAndWriteInteractor(A, true);
                }
                else {
                    registerAndWriteInteractor(B, true);
                }


            }
        }
        catch (IOException e) {
            throw new DataSourceWriterException("Impossible to write " +interaction.toString(), e);
        }
    }

    public void write(Collection<BinaryInteractionEvidence> interactions) throws DataSourceWriterException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void write(Iterator<BinaryInteractionEvidence> interactions) throws DataSourceWriterException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void flush() throws DataSourceWriterException{
        if (isInitialised){
            try {
                writer.write(interactionBuilder.toString());
                writer.flush();
                interactionBuilder.setLength(0);
            } catch (IOException e) {
                throw new DataSourceWriterException("Impossible to flush the JSON writer", e);
            }
        }
    }

    public void close() throws DataSourceWriterException{
        if (isInitialised){
            try {
                flush();
                writer.close();
            } catch (IOException e) {
                throw new DataSourceWriterException("Impossible to close the JSON writer", e);
            }

            isInitialised = false;
            writer = null;
            hasOpened = false;
            processedInteractors.clear();
            interactionBuilder.setLength(0);
        }
    }

    protected void appendPublication(Publication publication) throws IOException {
        // publication identifier
        if (publication.getImexId() != null){
            appendProperty("pubid", publication.getImexId());
            interactionBuilder.append(ELEMENT_SEPARATOR);
            appendNextPropertySeparatorAndIndent();
            interactionBuilder.append(INDENT);
        }
        else if (publication.getPubmedId() != null){
            appendProperty("pubid", publication.getPubmedId());
            interactionBuilder.append(ELEMENT_SEPARATOR);
            appendNextPropertySeparatorAndIndent();
            interactionBuilder.append(INDENT);
        }
        else if (publication.getDoi() != null){
            appendProperty("pubid", publication.getDoi());
            interactionBuilder.append(ELEMENT_SEPARATOR);
            appendNextPropertySeparatorAndIndent();
            interactionBuilder.append(INDENT);
        }
        else if (!publication.getIdentifiers().isEmpty()){
            appendProperty("pubid", publication.getIdentifiers().iterator().next().getId());
            interactionBuilder.append(ELEMENT_SEPARATOR);
            appendNextPropertySeparatorAndIndent();
            interactionBuilder.append(INDENT);
        }

        // publication source
        if (publication.getSource() != null){
            appendStartObject("source");
            appendCvTerm(publication.getSource());
        }
    }

    protected void appendExperiment(Experiment experiment) throws IOException {
        interactionBuilder.append(OPEN);

        // write detection method
        appendStartObject("detmethod");
        appendCvTerm(experiment.getInteractionDetectionMethod());
        interactionBuilder.append(ELEMENT_SEPARATOR);
        appendNextPropertySeparatorAndIndent();
        interactionBuilder.append(INDENT);

        if (experiment.getHostOrganism() != null){
            appendStartObject("host");
            appendOrganism(experiment.getHostOrganism());
            interactionBuilder.append(ELEMENT_SEPARATOR);
            appendNextPropertySeparatorAndIndent();
            interactionBuilder.append(INDENT);
        }

        // write publication
        if (experiment.getPublication() != null){
            appendPublication(experiment.getPublication());
        }

        interactionBuilder.append(CLOSE);
    }

    protected void appendOrganism(Organism organism) throws IOException {
        interactionBuilder.append(OPEN);
        appendProperty("taxid", Integer.toString(organism.getTaxId()));
        if (organism.getCommonName() != null){
            writer.write(ELEMENT_SEPARATOR);
            appendProperty("common", organism.getCommonName());
        }
        if (organism.getScientificName() != null){
            writer.write(ELEMENT_SEPARATOR);
            appendProperty("scientific", organism.getScientificName());
        }
        interactionBuilder.append(CLOSE);
    }

    protected void appendCvTerm(CvTerm term) throws IOException {
        if (term != null){
            interactionBuilder.append(OPEN);
            boolean hasId = false;
            if (term.getMIIdentifier() != null){
                appendProperty("id", term.getMIIdentifier());
                hasId = true;
            }
            else if (term.getMODIdentifier() != null){
                appendProperty("id", term.getMODIdentifier());
                hasId = true;
            }
            else if (term.getPARIdentifier() != null){
                appendProperty("id", term.getPARIdentifier());
                hasId = true;
            }

            if (hasId){
                interactionBuilder.append(ELEMENT_SEPARATOR);
            }

            if (term.getFullName() != null){
                appendProperty("name", term.getFullName());
            }
            else {
                appendProperty("name", term.getShortName());
            }
            interactionBuilder.append(CLOSE);
        }
    }

    protected void appendInteraction(BinaryInteractionEvidence binary, boolean writeElementSeparator) throws IOException {
        if (writeElementSeparator){
            interactionBuilder.append(ELEMENT_SEPARATOR);
            interactionBuilder.append(LINE_SEPARATOR);
        }
        interactionBuilder.append(INDENT);
        interactionBuilder.append(OPEN);
        appendNextPropertySeparatorAndIndent();

        // first experiment
        if (binary.getExperiment() != null){
            appendStartObject("experiment");
        }

    }

    protected void appendStartInteractions() throws IOException {
        interactionBuilder.append(CLOSE_ARRAY);
        interactionBuilder.append(ELEMENT_SEPARATOR);
        interactionBuilder.append(LINE_SEPARATOR);
        appendStartObject("interactions");
        interactionBuilder.append(OPEN_ARRAY);
    }

    protected void registerAndWriteInteractor(ParticipantEvidence participant, boolean writeElementSeparator) throws IOException {
        if (participant != null){
            Interactor interactor = participant.getInteractor();

            Xref preferredIdentifier = interactor.getPreferredIdentifier();
            String interactorId = null;
            if (preferredIdentifier != null){
                interactorId = preferredIdentifier.getId();
            }
            else{
                interactorId = "unspecified";
            }

            // if the interactor has not yet been processed, we write the interactor
            if (processedInteractors.add(interactorId)){
                if (writeElementSeparator){
                    writer.write(ELEMENT_SEPARATOR);
                    writer.write(LINE_SEPARATOR);
                }
                writer.write(INDENT);
                writer.write(OPEN);
                writeNextPropertySeparatorAndIndent();
                // write sequence if possible
                if (interactor instanceof Polymer){
                    Polymer polymer = (Polymer) interactor;
                    if (polymer.getSequence() != null){
                        writerProperty("sequence", polymer.getSequence());
                        writer.write(ELEMENT_SEPARATOR);
                        writeNextPropertySeparatorAndIndent();
                    }
                }
                // write interactor type
                writeStartObject("type");
                writeCvTerm(interactor.getInteractorType());
                writer.write(ELEMENT_SEPARATOR);
                writeNextPropertySeparatorAndIndent();
                // write organism
                if (interactor.getOrganism() != null){
                    writeStartObject("organism");
                    writer.write(ELEMENT_SEPARATOR);
                    writeOrganism(interactor.getOrganism());
                    writeNextPropertySeparatorAndIndent();
                }
                // write accession
                writerProperty("accession", interactorId);
                writer.write(ELEMENT_SEPARATOR);
                writeNextPropertySeparatorAndIndent();
                // write label
                writerProperty("label", interactor.getShortName());
                writer.write(ELEMENT_SEPARATOR);
                writer.write(LINE_SEPARATOR);
                writer.write(INDENT);
                writer.write(CLOSE);
            }
        }
    }

    protected void writeOrganism(Organism organism) throws IOException {
        writer.write(OPEN);
        writerProperty("taxid", Integer.toString(organism.getTaxId()));
        if (organism.getCommonName() != null){
            writer.write(ELEMENT_SEPARATOR);
            writerProperty("common", organism.getCommonName());
        }
        if (organism.getScientificName() != null){
            writer.write(ELEMENT_SEPARATOR);
            writerProperty("scientific", organism.getScientificName());
        }
        writer.write(CLOSE);
    }

    protected void writeCvTerm(CvTerm term) throws IOException {
        if (term != null){
            writer.write(OPEN);
            boolean hasId = false;
            if (term.getMIIdentifier() != null){
                writerProperty("id", term.getMIIdentifier());
                hasId = true;
            }
            else if (term.getMODIdentifier() != null){
                writerProperty("id", term.getMODIdentifier());
                hasId = true;
            }
            else if (term.getPARIdentifier() != null){
                writerProperty("id", term.getPARIdentifier());
                hasId = true;
            }

            if (hasId){
                writer.write(ELEMENT_SEPARATOR);
            }

            if (term.getFullName() != null){
                writerProperty("name", term.getFullName());
            }
            else {
                writerProperty("name", term.getShortName());
            }
            writer.write(CLOSE);
        }
    }

    protected void writerProperty(String propertyName, String value) throws IOException {
        writeStartObject(propertyName);
        writer.write(PROPERTY_DELIMITER);
        writer.write(value);
        writer.write(PROPERTY_DELIMITER);
    }

    protected void writeNextPropertySeparatorAndIndent() throws IOException {
        writer.write(LINE_SEPARATOR);
        writer.write(INDENT);
        writer.write(INDENT);
    }

    private void writeStartObject(String object) throws IOException {
        writer.write(PROPERTY_DELIMITER);
        writer.write(object);
        writer.write(PROPERTY_DELIMITER);
        writer.write(PROPERTY_VALUE_SEPARATOR);
    }

    protected void appendProperty(String propertyName, String value) throws IOException {
        appendStartObject(propertyName);
        interactionBuilder.append(PROPERTY_DELIMITER);
        interactionBuilder.append(value);
        interactionBuilder.append(PROPERTY_DELIMITER);
    }

    protected void appendNextPropertySeparatorAndIndent() throws IOException {
        interactionBuilder.append(LINE_SEPARATOR);
        interactionBuilder.append(INDENT);
        interactionBuilder.append(INDENT);
    }

    private void appendStartObject(String object) throws IOException {
        interactionBuilder.append(PROPERTY_DELIMITER);
        interactionBuilder.append(object);
        interactionBuilder.append(PROPERTY_DELIMITER);
        interactionBuilder.append(PROPERTY_VALUE_SEPARATOR);
    }

    protected void writeStart() throws IOException {
        hasOpened = true;
        writer.write(OPEN);
        writer.write(LINE_SEPARATOR);
        writeStartObject("interactors");
        writer.write(OPEN_ARRAY);
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
}
