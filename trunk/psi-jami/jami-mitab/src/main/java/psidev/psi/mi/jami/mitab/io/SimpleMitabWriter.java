package psidev.psi.mi.jami.mitab.io;

import psidev.psi.mi.jami.binary.BinaryInteraction;
import psidev.psi.mi.jami.binary.BinaryInteractionEvidence;
import psidev.psi.mi.jami.binary.ModelledBinaryInteraction;
import psidev.psi.mi.jami.binary.expansion.ComplexExpansionMethod;
import psidev.psi.mi.jami.binary.expansion.SpokeExpansion;
import psidev.psi.mi.jami.datasource.InteractionDataSourceWriter;
import psidev.psi.mi.jami.datasource.InteractionWriterFactory;
import psidev.psi.mi.jami.exception.DataSourceWriterException;
import psidev.psi.mi.jami.mitab.MitabVersion;
import psidev.psi.mi.jami.mitab.utils.MitabWriterUtils;
import psidev.psi.mi.jami.model.*;

import java.io.*;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

/**
 * The simple MITAB writer will write interactions using the JAMI interfaces.
 *
 * It will not check for MITAB extended objects (such as MitabAlias and MitabFeature).
 *
 * The default Complex expansion method is spoke expansion.
 *
 * The options accepted for this SimpleMitabWriter are :
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>10/06/13</pre>
 */

public class SimpleMitabWriter implements InteractionDataSourceWriter {

    private Writer writer;
    private ComplexExpansionMethod expansionMethod;
    private boolean isInitialised = false;
    private MitabVersion version = MitabVersion.v2_5;
    private boolean writeHeader = true;
    private boolean hasWrittenHeader = false;

    public SimpleMitabWriter(){

    }

    public SimpleMitabWriter(File file) throws IOException {

        initialiseFile(file);
        this.expansionMethod = new SpokeExpansion();
        isInitialised = true;
    }

    public SimpleMitabWriter(OutputStream output) throws IOException {

        initialiseOutputStream(output);
        this.expansionMethod = new SpokeExpansion();
        isInitialised = true;
    }

    public SimpleMitabWriter(Writer writer) throws IOException {

        initialiseWriter(writer);
        this.expansionMethod = new SpokeExpansion();
        isInitialised = true;
    }

    public SimpleMitabWriter(File file, ComplexExpansionMethod expansionMethod) throws IOException {

        initialiseFile(file);
        initialiseExpansionMethod(expansionMethod);
        isInitialised = true;
    }

    public SimpleMitabWriter(OutputStream output, ComplexExpansionMethod expansionMethod) throws IOException {

        initialiseOutputStream(output);
        initialiseExpansionMethod(expansionMethod);
        isInitialised = true;
    }

    public SimpleMitabWriter(Writer writer, ComplexExpansionMethod expansionMethod) throws IOException {

        initialiseWriter(writer);
        initialiseExpansionMethod(expansionMethod);
        isInitialised = true;
    }

    private void initialiseExpansionMethod(ComplexExpansionMethod expansionMethod) {
        this.expansionMethod = expansionMethod != null ? expansionMethod : new SpokeExpansion();
    }

    private void initialiseWriter(Writer writer) {
        if (writer == null){
            throw new IllegalArgumentException("The writer cannot be null.");
        }

        this.writer = writer;
    }

    private void initialiseOutputStream(OutputStream output) {
        if (output == null){
            throw new IllegalArgumentException("The output stream cannot be null.");
        }

        this.writer = new BufferedWriter(new OutputStreamWriter(output));
    }

    private void initialiseFile(File file) throws IOException {
        if (file == null){
            throw new IllegalArgumentException("The file cannot be null.");
        }
        else if (!file.canWrite()){
            throw new IllegalArgumentException("Does not have the permissions to write in file "+file.getAbsolutePath());
        }

        this.writer = new BufferedWriter(new FileWriter(file));
    }

    public ComplexExpansionMethod getExpansionMethod() {
        return expansionMethod;
    }

    public MitabVersion getVersion() {
        return version;
    }

    public void initialiseContext(Map<String, Object> options) throws DataSourceWriterException{

        if (options == null){
           throw new IllegalArgumentException("The options for the SimpleMitabWriter should contain at least "+InteractionWriterFactory.OUTPUT_FILE_OPTION_KEY
           + " or " + InteractionWriterFactory.OUTPUT_STREAM_OPTION_KEY + " or " + InteractionWriterFactory.WRITER_OPTION_KEY + " to know where to write the interactions.");
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
            throw new IllegalArgumentException("The options for the SimpleMitabWriter should contain at least "+InteractionWriterFactory.OUTPUT_FILE_OPTION_KEY
                    + " or " + InteractionWriterFactory.OUTPUT_STREAM_OPTION_KEY + " or " + InteractionWriterFactory.WRITER_OPTION_KEY + " to know where to write the interactions.");
        }

        if (options.containsKey(InteractionWriterFactory.COMPLEX_EXPANSION_OPTION_KEY)){
            try {
                initialiseExpansionMethod(((Class<? extends ComplexExpansionMethod>)options.get(InteractionWriterFactory.COMPLEX_EXPANSION_OPTION_KEY)).newInstance());
            } catch (InstantiationException e) {
                throw new DataSourceWriterException("Impossible to initialise the complex expansion method ", e);
            } catch (IllegalAccessException e) {
                throw new DataSourceWriterException("Impossible to initialise the complex expansion method ", e);
            }
        }

        if (options.containsKey(MitabWriterUtils.MITAB_VERSION_OPTION)){
            Object version = options.get(MitabWriterUtils.MITAB_VERSION_OPTION);
            this.version = version != null ? (MitabVersion) version : MitabVersion.v2_5;
        }

        if (options.containsKey(MitabWriterUtils.MITAB_HEADER_OPTION)){
            this.writeHeader = (Boolean) options.get(MitabWriterUtils.MITAB_HEADER_OPTION);
        }

        isInitialised = true;
    }

    public void write(Interaction interaction) {
        if (!isInitialised){
            throw new IllegalStateException("The SimpleMitabWriter has not been initialised with a map of options." +
                    "The options for the SimpleMitabWriter should contain at least "+InteractionWriterFactory.OUTPUT_FILE_OPTION_KEY
                    + " or " + InteractionWriterFactory.OUTPUT_STREAM_OPTION_KEY + " or " + InteractionWriterFactory.WRITER_OPTION_KEY + " to know where to write the interactions.");
        }

        if (interaction != null){

            Collection<BinaryInteraction> binaryInteractions = expansionMethod.expandInteraction(interaction);
            writeBinaryInteractions(binaryInteractions);
        }
    }

    public void write(InteractionEvidence interaction) {
        if (!isInitialised){
            throw new IllegalStateException("The SimpleMitabWriter has not been initialised with a map of options." +
                    "The options for the SimpleMitabWriter should contain at least "+InteractionWriterFactory.OUTPUT_FILE_OPTION_KEY
                    + " or " + InteractionWriterFactory.OUTPUT_STREAM_OPTION_KEY + " or " + InteractionWriterFactory.WRITER_OPTION_KEY + " to know where to write the interactions.");
        }

        if (interaction != null){

            Collection<BinaryInteractionEvidence> binaryInteractions = expansionMethod.expandInteractionEvidence(interaction);
            writeBinaryInteractionEvidences(binaryInteractions);
        }
    }

    public void write(ModelledInteraction interaction) {
        if (!isInitialised){
            throw new IllegalStateException("The SimpleMitabWriter has not been initialised with a map of options." +
                    "The options for the SimpleMitabWriter should contain at least "+InteractionWriterFactory.OUTPUT_FILE_OPTION_KEY
                    + " or " + InteractionWriterFactory.OUTPUT_STREAM_OPTION_KEY + " or " + InteractionWriterFactory.WRITER_OPTION_KEY + " to know where to write the interactions.");
        }

        if (interaction != null){

            Collection<ModelledBinaryInteraction> binaryInteractions = expansionMethod.expandModelledInteraction(interaction);
            writeModelledBinaryInteractions(binaryInteractions);
        }
    }

    public void writeInteractions(Collection<Interaction> interactions) {
        for (Interaction interaction : interactions){
            write(interaction);
        }
    }

    public void writeInteractionEvidences(Collection<InteractionEvidence> interactions) {
        for (InteractionEvidence interaction : interactions){
            write(interaction);
        }
    }

    public void writeModelledInteractions(Collection<ModelledInteraction> interactions) {
        for (ModelledInteraction interaction : interactions){
            write(interaction);
        }
    }

    public void writeBinary(BinaryInteraction interaction) throws IOException {

        if (!hasWrittenHeader){
            MitabWriterUtils.writeHeader(this.version, this.writer);
            hasWrittenHeader = true;
        }

        Participant A = interaction.getParticipantA();
        Participant B = interaction.getParticipantB();

        String [] columns = new String[this.version.getNumberOfColumns()];

        // write fields for A
        if (A != null){

            Interactor interactor = A.getInteractor();

            // write identifiers
            Iterator<Xref> identifierIterator = interactor.getIdentifiers().iterator();
            // we take unique identifier
            if (identifierIterator.hasNext()){

            }
        }
        else {
           /* switch (this.version){
                case v2_7:
                    MitabWriterUtils.fillInteractorA2_7ColumnsWithEmptyValues(columns);
                case v2_6:
                    MitabWriterUtils.fillInteractorA2_6ColumnsWithEmptyValues(columns);
                case v2_5:
                    MitabWriterUtils.fillInteractorA2_5ColumnsWithEmptyValues(columns);
                    break;
                default:
                    break;
            }  */
        }

        // write fields for B
        if (B != null){

        }
        else {
            /*switch (this.version){
                case v2_7:
                    MitabWriterUtils.fillInteractorB2_7ColumnsWithEmptyValues(columns);
                case v2_6:
                    MitabWriterUtils.fillInteractorB2_6ColumnsWithEmptyValues(columns);
                case v2_5:
                    MitabWriterUtils.fillInteractorB2_5ColumnsWithEmptyValues(columns);
                    break;
                default:
                    break;
            } */
        }
    }

    public void writeBinaryEvidence(BinaryInteractionEvidence interaction) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void writeModelledBinary(ModelledBinaryInteraction interaction) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void writeBinaryInteractions(Collection<BinaryInteraction> interactions) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void writeBinaryInteractionEvidences(Collection<BinaryInteractionEvidence> interactions) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void writeModelledBinaryInteractions(Collection<ModelledBinaryInteraction> interactions) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void flush() throws DataSourceWriterException{
        if (isInitialised){
            try {
                writer.flush();
            } catch (IOException e) {
                throw new DataSourceWriterException("Impossible to flush the MITAB writer", e);
            }
        }
    }

    public void close() throws DataSourceWriterException{
        if (isInitialised){
            try {
                writer.close();
            } catch (IOException e) {
                throw new DataSourceWriterException("Impossible to close the MITAB writer", e);
            }

            isInitialised = false;
            writer = null;
            expansionMethod = new SpokeExpansion();
            writeHeader = true;
            version = MitabVersion.v2_5;
            hasWrittenHeader = false;
        }
    }
}
