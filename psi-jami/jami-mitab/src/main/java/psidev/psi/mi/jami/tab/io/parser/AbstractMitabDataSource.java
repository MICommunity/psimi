package psidev.psi.mi.jami.tab.io.parser;

import psidev.psi.mi.jami.binary.BinaryInteraction;
import psidev.psi.mi.jami.datasource.FileSourceError;
import psidev.psi.mi.jami.datasource.MIFileDataSource;
import psidev.psi.mi.jami.datasource.StreamingInteractionSource;
import psidev.psi.mi.jami.factory.InteractionWriterFactory;
import psidev.psi.mi.jami.factory.MIDataSourceFactory;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Interaction;
import psidev.psi.mi.jami.model.Participant;
import psidev.psi.mi.jami.tab.extension.*;
import psidev.psi.mi.jami.tab.listener.MitabParserListener;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

/**
 * abstract class for Mitab datasource
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>21/06/13</pre>
 */

public abstract class AbstractMitabDataSource<T extends Interaction, B extends BinaryInteraction, P extends Participant> implements MIFileDataSource, StreamingInteractionSource, MitabParserListener{

    private MitabLineParser<B,P> lineParser;
    private boolean isInitialised = false;
    private Collection<FileSourceError> errors;

    private File originalFile;
    private InputStream originalStream;
    private Reader originalReader;

    private boolean isConsumed = false;
    private boolean hasValidated = false;

    /**
     * Empty constructor for the factory
     */
    public AbstractMitabDataSource(){
        errors = new ArrayList<FileSourceError>();
    }

    public AbstractMitabDataSource(File file) throws IOException {

        initialiseFile(file);
        isInitialised = true;
        errors = new ArrayList<FileSourceError>();
    }

    public AbstractMitabDataSource(InputStream input) {

        initialiseInputStream(input);
        isInitialised = true;
        errors = new ArrayList<FileSourceError>();
    }

    public AbstractMitabDataSource(Reader reader) {

        initialiseReader(reader);
        isInitialised = true;
        errors = new ArrayList<FileSourceError>();
    }

    public void initialiseContext(Map<String, Object> options) {
        if (options == null && !isInitialised){
            throw new IllegalArgumentException("The options for the MitabDataSource should contain at least "+ MIDataSourceFactory.INPUT_FILE_OPTION_KEY
                    + " or " + MIDataSourceFactory.INPUT_STREAM_OPTION_KEY + " or " + MIDataSourceFactory.READER_OPTION_KEY+ " to know where to load the interactions from.");
        }
        else if (options == null){
            return;
        }
        else if (options.containsKey(MIDataSourceFactory.INPUT_FILE_OPTION_KEY)){
            initialiseFile((File) options.get(MIDataSourceFactory.INPUT_FILE_OPTION_KEY));
        }
        else if (options.containsKey(MIDataSourceFactory.INPUT_STREAM_OPTION_KEY)){
            initialiseInputStream((InputStream) options.get(MIDataSourceFactory.INPUT_STREAM_OPTION_KEY));
        }
        else if (options.containsKey(MIDataSourceFactory.READER_OPTION_KEY)){
            initialiseReader((Reader) options.get(MIDataSourceFactory.READER_OPTION_KEY));
        }
        else if (!isInitialised){
            throw new IllegalArgumentException("The options for the Mitab25Writer should contain at least "+InteractionWriterFactory.OUTPUT_FILE_OPTION_KEY
                    + " or " + InteractionWriterFactory.OUTPUT_STREAM_OPTION_KEY + " or " + InteractionWriterFactory.WRITER_OPTION_KEY + " to know where to write the interactions.");
        }

        isInitialised = true;
    }

    public Collection<FileSourceError> getDataSourceErrors() {
        return errors;
    }

    public void close() {
        if (isInitialised){
            if (this.originalStream != null){
                try {
                    this.originalStream.close();
                } catch (IOException e) {
                    throw new RuntimeException("Impossible to close the original stream", e);
                }
            }
            if (this.originalReader != null){
                try {
                    this.originalReader.close();
                } catch (IOException e) {
                    throw new RuntimeException("Impossible to close the original reader", e);
                }
            }
            this.originalFile = null;
            this.lineParser = null;
            this.errors.clear();
            isConsumed = false;
            isInitialised = false;
            hasValidated = false;
        }
    }

    public boolean validateFileSyntax() {
        if (hasValidated || isConsumed){
            return errors.isEmpty();
        }
        else{
            hasValidated = true;
            // read the datasource
            Iterator<T> interactionIterator = getInteractionsIterator();
            while(interactionIterator.hasNext()){
                interactionIterator.next();
            }
            isConsumed = true;
            return errors.isEmpty();
        }
    }

    public void onTextFoundInIdentifier(MitabXref xref, int line, int column, int mitabColumn) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void onMissingCvTermName(CvTerm term, int line, int column, int mitabColumn) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void onTextFoundInConfidence(MitabConfidence conf, int line, int column, int mitabColumn) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void onMissingExpansionId(MitabCvTerm expansion, int line, int column, int mitabColumn) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void onInvalidSyntax(int line, int column, int mitabColumn) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void onSeveralUniqueIdentifiers(Collection<MitabXref> ids) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void onEmptyUniqueIdentifiers(int line, int column, int mitabColumn) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void onEmptyAliases(int line, int column, int mitabColumn) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void onMissingInteractorIdentifierColumns(int line, int column, int mitabColumn) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void onAliasFoundInAlternativeIds(MitabXref ref, int line, int column, int mitabColumn) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void onChecksumFoundInAlternativeIds(MitabXref ref, int line, int column, int mitabColumn) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void onChecksumFoundInAliases(MitabAlias alias, int line, int column, int mitabColumn) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void onSeveralCvTermFound(Collection<MitabCvTerm> terms) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void onSeveralOrganismFound(Collection<MitabOrganism> organisms) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void onParticipantWithoutInteractorDetails(int line, int column, int mitabColumn) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void onSeveralStoichiometryFound(Collection<MitabStoichiometry> stoichiometry) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void onInteractionWithoutParticipants(int line) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void onSeveralFirstAuthorFound(Collection<MitabAuthor> authors) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void onSeveralSourceFound(Collection<MitabSource> sources) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void onSeveralHostOrganismFound(Collection<MitabOrganism> organisms) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void onSeveralCreatedDateFound(Collection<MitabDate> dates) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void onSeveralUpdatedDateFound(Collection<MitabDate> dates) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public Iterator<T> getInteractionsIterator() {
        // reset parser if possible
        if (isConsumed){
           reInit();
        }
        return createMitabIterator();
    }

    protected MitabLineParser<B,P> getLineParser() {
        return lineParser;
    }

    protected void setLineParser(MitabLineParser<B,P> lineParser) {
        this.lineParser = lineParser;
    }

    protected abstract void initialiseMitabLineParser(Reader reader);

    protected abstract void initialiseMitabLineParser(File file);

    protected abstract void initialiseMitabLineParser(InputStream input);

    protected abstract Iterator<T> createMitabIterator();

    protected void reInit(){
        if (isInitialised){
            if (this.originalFile != null){
                // close the previous stream
                if (this.originalStream != null){
                    try {
                        this.originalStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                // reinitialise mitab parser
                try {
                    this.originalStream = new BufferedInputStream(new FileInputStream(this.originalFile));
                    this.lineParser.ReInit(this.originalStream);

                } catch (FileNotFoundException e) {
                    throw new RuntimeException("We cannot open the file " + this.originalFile.getName(), e);
                }
            }
            else if (this.originalStream != null){
                // reinit line parser if inputStream can be reset
                if (this.originalStream.markSupported()){
                    try {
                        this.originalStream.reset();
                        this.lineParser.ReInit(this.originalStream);
                    } catch (IOException e) {
                        throw new RuntimeException("The inputStream has been consumed and cannot be reset", e);
                    }
                }
                else {
                    throw new RuntimeException("The inputStream has been consumed and cannot be reset");
                }
            }
            else if (this.originalReader != null){
                // reinit line parser if reader can be reset
                if (this.originalReader.markSupported()){
                    try {
                        this.originalReader.reset();
                        this.lineParser.ReInit(this.originalReader);
                    } catch (IOException e) {
                        throw new RuntimeException("The reader has been consumed and cannot be reset", e);
                    }
                }
                else {
                    throw new RuntimeException("The reader has been consumed and cannot be reset");
                }
            }
            isConsumed = false;
        }
    }

    private void initialiseReader(Reader reader) {
        if (reader == null){
            throw new IllegalArgumentException("The reader cannot be null.");
        }
        this.originalFile = null;
        this.originalReader = reader;
        this.originalStream = null;

        initialiseMitabLineParser(reader);
    }

    private void initialiseInputStream(InputStream input) {
        if (input == null){
            throw new IllegalArgumentException("The input stream cannot be null.");
        }
        this.originalFile = null;
        this.originalReader = null;
        this.originalStream = input;

        initialiseMitabLineParser(input);
    }

    private void initialiseFile(File file)  {
        if (file == null){
            throw new IllegalArgumentException("The file cannot be null.");
        }
        else if (!file.canRead()){
            throw new IllegalArgumentException("Does not have the permissions to read the file "+file.getAbsolutePath());
        }
        this.originalFile = file;
        this.originalReader = null;
        this.originalStream = null;

        initialiseMitabLineParser(file);
    }
}
