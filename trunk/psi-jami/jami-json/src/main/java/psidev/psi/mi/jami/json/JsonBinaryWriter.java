package psidev.psi.mi.jami.json;

import psidev.psi.mi.jami.binary.BinaryInteractionEvidence;
import psidev.psi.mi.jami.datasource.InteractionWriter;
import psidev.psi.mi.jami.exception.DataSourceWriterException;
import psidev.psi.mi.jami.factory.InteractionWriterFactory;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.utils.AnnotationUtils;
import psidev.psi.mi.jami.utils.comparator.interactor.UnambiguousExactInteractorBaseComparator;

import java.io.*;
import java.util.*;
import java.util.logging.Logger;

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
    private Set<String> processedInteractors;
    private static final Logger logger = Logger.getLogger("MitabParserLogger");
    private Integer expansionId;
    private int currentExpansion=1;

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

                writeInteraction(interaction, A, B);
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
                writer.flush();
            } catch (IOException e) {
                throw new DataSourceWriterException("Impossible to flush the JSON writer", e);
            }
        }
    }

    public void close() throws DataSourceWriterException{
        if (isInitialised){
            try {
                flush();
            }
            finally {
                try {
                    writer.close();
                } catch (IOException e) {
                    throw new DataSourceWriterException("Impossible to close the JSON writer", e);
                }
            }

            isInitialised = false;
            writer = null;
            hasOpened = false;
            processedInteractors.clear();
            expansionId = null;
            currentExpansion=1;
        }
    }

    protected void writePublication(Publication publication) throws IOException {
        // publication identifiers
        if (!publication.getIdentifiers().isEmpty()){
            writeStartObject("pubid");
            writeAllIdentifiers(publication.getIdentifiers());
        }

        // publication source
        if (publication.getSource() != null){
            writer.write(JsonUtils.ELEMENT_SEPARATOR);
            writeNextPropertySeparatorAndIndent();
            writer.write(JsonUtils.INDENT);
            writeStartObject("source");
            writeCvTerm(publication.getSource());
        }
    }

    protected boolean writeExperiment(InteractionEvidence interaction) throws IOException {
        Experiment experiment = interaction.getExperiment();
        Collection<Annotation> figures = AnnotationUtils.collectAllAnnotationsHavingTopic(interaction.getAnnotations(), Annotation.FIGURE_LEGEND_MI, Annotation.FIGURE_LEGEND);

        if (experiment != null){
            writeNextPropertySeparatorAndIndent();
            writeStartObject("experiment");
            writer.write(JsonUtils.OPEN);
            writeNextPropertySeparatorAndIndent();
            writer.write(JsonUtils.INDENT);

            // write detection method
            writeStartObject("detmethod");
            writeCvTerm(experiment.getInteractionDetectionMethod());

            if (experiment.getHostOrganism() != null){
                writer.write(JsonUtils.ELEMENT_SEPARATOR);
                writeNextPropertySeparatorAndIndent();
                writer.write(JsonUtils.INDENT);
                writeStartObject("host");
                writeOrganism(experiment.getHostOrganism());
            }

            if (experiment.getHostOrganism() != null){
                writer.write(JsonUtils.ELEMENT_SEPARATOR);
                writeNextPropertySeparatorAndIndent();
                writer.write(JsonUtils.INDENT);
                writeStartObject("host");
                writeOrganism(experiment.getHostOrganism());
            }

            // write publication
            if (experiment.getPublication() != null){
                writer.write(JsonUtils.ELEMENT_SEPARATOR);
                writeNextPropertySeparatorAndIndent();
                writer.write(JsonUtils.INDENT);
                writePublication(experiment.getPublication());
            }

            Collection<Annotation> expModifications = AnnotationUtils.collectAllAnnotationsHavingTopic(experiment.getAnnotations(), Annotation.EXP_MODIFICATION_MI, Annotation.EXP_MODIFICATION);
            if (!expModifications.isEmpty()){
                writer.write(JsonUtils.ELEMENT_SEPARATOR);
                writeNextPropertySeparatorAndIndent();
                writer.write(JsonUtils.INDENT);
                writeStartObject("experimentModifications");
                writeAllAnnotations("modification", expModifications);
            }

            if (!figures.isEmpty()){
                writer.write(JsonUtils.ELEMENT_SEPARATOR);
                writeNextPropertySeparatorAndIndent();
                writer.write(JsonUtils.INDENT);
                writeStartObject("figures");
                writeAllAnnotations("figure", figures);
            }

            writeNextPropertySeparatorAndIndent();
            writer.write(JsonUtils.INDENT);
            writer.write(JsonUtils.CLOSE);
            return true;
        }
        else if (!figures.isEmpty()){
            writeNextPropertySeparatorAndIndent();
            writeStartObject("experiment");
            writer.write(JsonUtils.OPEN);
            writeNextPropertySeparatorAndIndent();
            writer.write(JsonUtils.INDENT);

            // write figures
            writeStartObject("figures");
            writeAllAnnotations("figure", figures);

            writeNextPropertySeparatorAndIndent();
            writer.write(JsonUtils.INDENT);
            writer.write(JsonUtils.CLOSE);
            return true;
        }
        return false;
    }

    private void writeAllAnnotations(String name, Collection<Annotation> figures) throws IOException {
        writer.write(JsonUtils.OPEN_ARRAY);

        Iterator<Annotation> annotIterator = figures.iterator();
        while (annotIterator.hasNext()){
            Annotation annot = annotIterator.next();
            writeAnnotation(name, annot != null ? annot.getValue():"");
            if (annotIterator.hasNext()){
                writer.write(JsonUtils.ELEMENT_SEPARATOR);
            }
        }

        writer.write(JsonUtils.CLOSE_ARRAY);
    }

    protected void writeInteraction(BinaryInteractionEvidence binary, ParticipantEvidence A, ParticipantEvidence B) throws IOException {
        writer.write(JsonUtils.ELEMENT_SEPARATOR);
        writer.write(JsonUtils.LINE_SEPARATOR);
        writer.write(JsonUtils.INDENT);
        writer.write(JsonUtils.OPEN);

        // first experiment
        boolean hasExperiment = writeExperiment(binary);

        // then interaction type
        boolean hasType = binary.getInteractionType() != null;
        if (hasType){
            if (hasExperiment){
                writer.write(JsonUtils.ELEMENT_SEPARATOR);
            }
            writeNextPropertySeparatorAndIndent();
            writeStartObject("interactionType");
            writeCvTerm(binary.getInteractionType());
        }

        // then interaction identifiers
        boolean hasIdentifiers = !binary.getIdentifiers().isEmpty();
        if (hasIdentifiers){
            if (hasType || (!hasType && hasExperiment)){
                writer.write(JsonUtils.ELEMENT_SEPARATOR);
            }
            writeNextPropertySeparatorAndIndent();
            writeStartObject("identifiers");
            writeAllIdentifiers(binary.getIdentifiers());
        }

        // then confidences
        boolean hasConfidences = !binary.getConfidences().isEmpty();
        if (hasConfidences){
            if (hasIdentifiers || (!hasIdentifiers && (hasType || (!hasType && hasExperiment)))){
                writer.write(JsonUtils.ELEMENT_SEPARATOR);
            }
            writeNextPropertySeparatorAndIndent();
            writeStartObject("confidences");
            writeAllConfidences(binary.getConfidences());
        }

        // then complex expansion
        boolean hasComplexExpansion = binary.getComplexExpansion() != null;
        if (hasComplexExpansion){
            if (hasConfidences || (!hasConfidences && (hasIdentifiers || (!hasIdentifiers && (hasType || (!hasType && hasExperiment)))))){
                writer.write(JsonUtils.ELEMENT_SEPARATOR);
            }
            writeNextPropertySeparatorAndIndent();
            writeStartObject("expansion");
            writeExpansionMethod(binary.getComplexExpansion());
        }
    }

    protected void writeAllConfidences(Collection<Confidence> confidences) throws IOException {
        writer.write(JsonUtils.OPEN_ARRAY);

        Iterator<Confidence> confidencesIterator = confidences.iterator();
        while (confidencesIterator.hasNext()){
            Confidence conf = confidencesIterator.next();
            if (conf != null){
                writeConfidence(conf.getType().getShortName(), conf.getValue());
            }
            else {
                writeConfidence("unknown","");
            }
            if (confidencesIterator.hasNext()){
                writer.write(JsonUtils.ELEMENT_SEPARATOR);
            }
        }

        writer.write(JsonUtils.CLOSE_ARRAY);
    }

    protected void writeAllIdentifiers(Collection<Xref> identifiers) throws IOException {
        writer.write(JsonUtils.OPEN_ARRAY);

        Iterator<Xref> identifierIterator = identifiers.iterator();
        while (identifierIterator.hasNext()){
            Xref identifier = identifierIterator.next();
            if (identifier != null){
                writeIdentifier(identifier.getDatabase().getShortName(), identifier.getId());
            }
            else {
                writeIdentifier("unknown", identifier.getId());
            }
            if (identifierIterator.hasNext()){
                writer.write(JsonUtils.ELEMENT_SEPARATOR);
            }
        }

        writer.write(JsonUtils.CLOSE_ARRAY);
    }

    protected void registerAndWriteInteractor(ParticipantEvidence participant, boolean writeElementSeparator) throws IOException {
        if (participant != null){
            Interactor interactor = participant.getInteractor();

            Xref preferredIdentifier = interactor.getPreferredIdentifier();
            String interactorId = null;
            String db = null;
            if (preferredIdentifier != null){
                interactorId = preferredIdentifier.getId();
                db = preferredIdentifier.getDatabase().getShortName();
            }
            else{
                interactorId = Integer.toString(UnambiguousExactInteractorBaseComparator.hashCode(interactor));
                db = "generated";
            }
            String interactorKey = db+"_"+interactorId;

            // if the interactor has not yet been processed, we write the interactor
            if (processedInteractors.add(interactorKey)){
                if (writeElementSeparator){
                    writer.write(JsonUtils.ELEMENT_SEPARATOR);
                    writer.write(JsonUtils.LINE_SEPARATOR);
                }
                writer.write(JsonUtils.INDENT);
                writer.write(JsonUtils.OPEN);
                writeNextPropertySeparatorAndIndent();

                writerProperty("object","interactor");
                writer.write(JsonUtils.ELEMENT_SEPARATOR);
                writeNextPropertySeparatorAndIndent();

                // write sequence if possible
                if (interactor instanceof Polymer){
                    Polymer polymer = (Polymer) interactor;
                    if (polymer.getSequence() != null){
                        writerProperty("sequence", polymer.getSequence());
                        writer.write(JsonUtils.ELEMENT_SEPARATOR);
                        writeNextPropertySeparatorAndIndent();
                    }
                }
                // write interactor type
                writeStartObject("type");
                writeCvTerm(interactor.getInteractorType());
                writer.write(JsonUtils.ELEMENT_SEPARATOR);
                writeNextPropertySeparatorAndIndent();
                // write organism
                if (interactor.getOrganism() != null){
                    writeStartObject("organism");
                    writer.write(JsonUtils.ELEMENT_SEPARATOR);
                    writeOrganism(interactor.getOrganism());
                    writeNextPropertySeparatorAndIndent();
                }
                // write accession
                writeStartObject("identifier");
                writeIdentifier(db, interactorId);
                writer.write(JsonUtils.ELEMENT_SEPARATOR);
                writeNextPropertySeparatorAndIndent();
                // write label
                writerProperty("label", interactor.getShortName());
                writer.write(JsonUtils.ELEMENT_SEPARATOR);
                writer.write(JsonUtils.LINE_SEPARATOR);
                writer.write(JsonUtils.INDENT);
                writer.write(JsonUtils.CLOSE);
            }
        }
    }

    protected void writeExpansionMethod(CvTerm expansion) throws IOException {
        writer.write(JsonUtils.OPEN);
        if (expansionId != null){
            writerProperty("id", Integer.toString(expansionId));
            writer.write(JsonUtils.ELEMENT_SEPARATOR);
        }
        writerProperty("name", expansion.getShortName());
        writer.write(JsonUtils.CLOSE);
    }

    protected void writeOrganism(Organism organism) throws IOException {
        writer.write(JsonUtils.OPEN);
        writerProperty("taxid", Integer.toString(organism.getTaxId()));
        if (organism.getCommonName() != null){
            writer.write(JsonUtils.ELEMENT_SEPARATOR);
            writerProperty("common", organism.getCommonName());
        }
        if (organism.getScientificName() != null){
            writer.write(JsonUtils.ELEMENT_SEPARATOR);
            writerProperty("scientific", organism.getScientificName());
        }
        writer.write(JsonUtils.CLOSE);
    }

    protected void writeCvTerm(CvTerm term) throws IOException {
        writer.write(JsonUtils.OPEN);
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
            writer.write(JsonUtils.ELEMENT_SEPARATOR);
        }

        if (term.getFullName() != null){
            writerProperty("name", term.getFullName());
        }
        else {
            writerProperty("name", term.getShortName());
        }
        writer.write(JsonUtils.CLOSE);
    }

    protected void writeAnnotation(String name, String text) throws IOException {
        writer.write(JsonUtils.OPEN);
        writerProperty(name, text != null ? text : "");
        writer.write(JsonUtils.CLOSE);

    }

    protected void writeConfidence(String type, String value) throws IOException {
        writer.write(JsonUtils.OPEN);
        writerProperty("type", type);
        writer.write(JsonUtils.ELEMENT_SEPARATOR);
        writerProperty("value", value);
        writer.write(JsonUtils.CLOSE);
    }

    protected void writeIdentifier(String db, String id) throws IOException {
        writer.write(JsonUtils.OPEN);
        writerProperty("db", db);
        writer.write(JsonUtils.ELEMENT_SEPARATOR);
        writerProperty("id", id);
        writer.write(JsonUtils.CLOSE);
    }

    protected void writerProperty(String propertyName, String value) throws IOException {
        writeStartObject(propertyName);
        writer.write(JsonUtils.PROPERTY_DELIMITER);
        writer.write(value);
        writer.write(JsonUtils.PROPERTY_DELIMITER);
    }

    protected void writeNextPropertySeparatorAndIndent() throws IOException {
        writer.write(JsonUtils.LINE_SEPARATOR);
        writer.write(JsonUtils.INDENT);
        writer.write(JsonUtils.INDENT);
    }

    private void writeStartObject(String object) throws IOException {
        writer.write(JsonUtils.PROPERTY_DELIMITER);
        writer.write(object);
        writer.write(JsonUtils.PROPERTY_DELIMITER);
        writer.write(JsonUtils.PROPERTY_VALUE_SEPARATOR);
    }

    protected void writeStart() throws IOException {
        hasOpened = true;
        writer.write(JsonUtils.OPEN);
        writer.write(JsonUtils.LINE_SEPARATOR);
        writeStartObject("data");
        writer.write(JsonUtils.OPEN_ARRAY);
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
