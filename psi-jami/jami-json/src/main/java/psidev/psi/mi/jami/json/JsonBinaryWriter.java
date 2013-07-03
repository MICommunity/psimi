package psidev.psi.mi.jami.json;

import psidev.psi.mi.jami.binary.BinaryInteractionEvidence;
import psidev.psi.mi.jami.datasource.InteractionWriter;
import psidev.psi.mi.jami.exception.DataSourceWriterException;
import psidev.psi.mi.jami.factory.InteractionWriterFactory;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Experiment;
import psidev.psi.mi.jami.model.ParticipantEvidence;
import psidev.psi.mi.jami.model.Publication;

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
    private boolean hasOpenedFirstArray = false;
    public final static String OPEN = "{";
    public final static String CLOSE = "}";
    public final static String OPEN_ARRAY = "[";
    public final static String CLOSE_ARRAY = "]";
    public final static String PROPERTY_DELIMITER = "\"";
    public final static String PROPERTY_VALUE_SEPARATOR = ":";
    public final static String ELEMENT_SEPARATOR = ",";

    private Set<String> processedInteractors;

    public JsonBinaryWriter(){
        processedInteractors = new HashSet<String>();
    }

    public JsonBinaryWriter(File file) throws IOException {

        initialiseFile(file);
        processedInteractors = new HashSet<String>();
    }

    public JsonBinaryWriter(OutputStream output) {

        initialiseOutputStream(output);
        processedInteractors = new HashSet<String>();
    }

    public JsonBinaryWriter(Writer writer) {

        initialiseWriter(writer);
        processedInteractors = new HashSet<String>();
    }

    public void initialiseContext(Map<String, Object> options) throws DataSourceWriterException {
        if (options == null && !isInitialised){
            throw new IllegalArgumentException("The options for the JSON writer should contain at least "+ InteractionWriterFactory.OUTPUT_FILE_OPTION_KEY
                    + " or " + InteractionWriterFactory.OUTPUT_STREAM_OPTION_KEY + " or " + InteractionWriterFactory.WRITER_OPTION_KEY + " to know where to write the interactions.");
        }
        else if (options == null){
            return;
        }
        else if (options.containsKey(InteractionWriterFactory.OUTPUT_FILE_OPTION_KEY)){
            try {
                initialiseFile((File) options.get(InteractionWriterFactory.OUTPUT_FILE_OPTION_KEY));
            } catch (IOException e) {
                throw new DataSourceWriterException("Impossible to open output file", e);
            }
        }
        else if (options.containsKey(InteractionWriterFactory.OUTPUT_STREAM_OPTION_KEY)){
            initialiseOutputStream((OutputStream) options.get(InteractionWriterFactory.OUTPUT_STREAM_OPTION_KEY));
        }
        else if (options.containsKey(InteractionWriterFactory.WRITER_OPTION_KEY)){
            initialiseWriter((Writer) options.get(InteractionWriterFactory.WRITER_OPTION_KEY));
        }
        else if (!isInitialised){
            throw new IllegalArgumentException("The options for the JSON writer should contain at least "+InteractionWriterFactory.OUTPUT_FILE_OPTION_KEY
                    + " or " + InteractionWriterFactory.OUTPUT_STREAM_OPTION_KEY + " or " + InteractionWriterFactory.WRITER_OPTION_KEY + " to know where to write the interactions.");
        }

        isInitialised = true;
    }

    public void write(BinaryInteractionEvidence interaction) throws DataSourceWriterException {
        if (!isInitialised){
            throw new IllegalStateException("The json writer has not been initialised with a map of options." +
                    "The options for the json writer should contain at least "+InteractionWriterFactory.OUTPUT_FILE_OPTION_KEY
                    + " or " + InteractionWriterFactory.OUTPUT_STREAM_OPTION_KEY + " or " + InteractionWriterFactory.WRITER_OPTION_KEY + " to know where to write the interactions.");
        }

        try{
            if (!hasOpenedFirstArray){
                hasOpenedFirstArray = true;
                writer.write(OPEN_ARRAY);
            }

            ParticipantEvidence A = interaction.getParticipantA();
            ParticipantEvidence B = interaction.getParticipantB();
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
                writer.flush();
            } catch (IOException e) {
                throw new DataSourceWriterException("Impossible to flush the JSON writer", e);
            }
        }
    }

    public void close() throws DataSourceWriterException{
        if (isInitialised){
            try {
                writer.close();
            } catch (IOException e) {
                throw new DataSourceWriterException("Impossible to close the JSON writer", e);
            }

            isInitialised = false;
            writer = null;
            hasOpenedFirstArray = false;
            processedInteractors.clear();
        }
    }

    protected void writeExperiment(Experiment experiment) throws IOException {
        if (experiment != null){
            writeStartObject("experiment");

            // write detection method
            writeStartObject("detmethod");
            writeCvTerm(experiment.getInteractionDetectionMethod());
            writer.write(CLOSE);

            // write pub id


            writer.write(CLOSE);
        }
    }

    protected void writePublication(Publication publication) throws IOException {

    }

    private void writeStartObject(String object) throws IOException {
        writer.write(PROPERTY_DELIMITER);
        writer.write(object);
        writer.write(PROPERTY_DELIMITER);
        writer.write(PROPERTY_VALUE_SEPARATOR);
        writer.write(OPEN);
    }

    protected void writeCvTerm(CvTerm term) throws IOException {
        if (term != null){
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
        }
    }

    private void writerProperty(String propertyName, String value) throws IOException {
        writer.write(PROPERTY_DELIMITER);
        writer.write(propertyName);
        writer.write(PROPERTY_DELIMITER);
        writer.write(PROPERTY_VALUE_SEPARATOR);
        writer.write(PROPERTY_DELIMITER);
        writer.write(value);
        writer.write(PROPERTY_DELIMITER);
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
