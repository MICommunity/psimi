package psidev.psi.mi.jami.tab.io.parser;

import psidev.psi.mi.jami.binary.BinaryInteraction;
import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.datasource.MIFileDataSource;
import psidev.psi.mi.jami.datasource.StreamingInteractionSource;
import psidev.psi.mi.jami.factory.InteractionWriterFactory;
import psidev.psi.mi.jami.factory.MIDataSourceFactory;
import psidev.psi.mi.jami.listener.MIFileParserListener;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.tab.extension.*;
import psidev.psi.mi.jami.tab.listener.MitabParserListener;

import java.io.*;
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

    private File originalFile;
    private InputStream originalStream;
    private Reader originalReader;

    private Boolean isValid = null;

    private MitabParserListener parserListener;

    /**
     * Empty constructor for the factory
     */
    public AbstractMitabDataSource(){
    }

    public AbstractMitabDataSource(File file) throws IOException {

        initialiseFile(file);
        isInitialised = true;
    }

    public AbstractMitabDataSource(InputStream input) {

        initialiseInputStream(input);
        isInitialised = true;
    }

    public AbstractMitabDataSource(Reader reader) {

        initialiseReader(reader);
        isInitialised = true;
    }

    public MitabParserListener getFileParserListener() {
        return this.parserListener;
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

        if (options.containsKey(MIDataSourceFactory.PARSE_LISTENER_OPTION_KEY)){
            setMIFileParserListener((MitabParserListener) options.get(MIDataSourceFactory.PARSE_LISTENER_OPTION_KEY));
        }

        isInitialised = true;
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
            this.parserListener = null;
            isInitialised = false;
            isValid = null;
        }
    }

    public boolean validateSyntax(MIFileParserListener listener) {
        if (!(listener instanceof MitabParserListener)){
            throw new IllegalArgumentException("A MITAB data source is expecting a MitabParserListener. It does not accept "+listener.getClass());
        }
        setMIFileParserListener((MitabParserListener)listener);
        return validateSyntax();
    }

    public boolean validateSyntax() {
        if (!isInitialised){
            throw new IllegalArgumentException("The options for the MitabDataSource should contain at least "+ MIDataSourceFactory.INPUT_FILE_OPTION_KEY
                    + " or " + MIDataSourceFactory.INPUT_STREAM_OPTION_KEY + " or " + MIDataSourceFactory.READER_OPTION_KEY+ " to know where to load the interactions from.");
        }

        if (isValid != null){
            return isValid;
        }

        if (lineParser.hasFinished()){
            reInit();
        }

        // read the datasource
        Iterator<T> interactionIterator = getInteractionsIterator();
        while(interactionIterator.hasNext()){
            interactionIterator.next();
        }
        // if isValid is not null, it means that the file syntax is invalid, otherwise, we say that the file syntax is valid
        if (isValid == null){
            isValid = true;
        }
        return isValid;
    }

    public void onInvalidSyntax(FileSourceContext context, Exception e) {
        isValid = false;
    }

    public void onSyntaxWarning(FileSourceContext context, String message) {
        // do nothing
    }

    public void onMissingCvTermName(CvTerm term, FileSourceContext context, String message) {
        // do nothing
    }

    public void onMissingInteractorName(Interactor interactor, FileSourceContext context) {
        // do nothing
    }

    public void onSeveralCvTermsFound(Collection<? extends CvTerm> terms, FileSourceContext context, String message) {
        // do nothing
    }

    public void onSeveralHostOrganismFound(Collection<? extends Organism> organisms, FileSourceContext context) {
        // do nothing
    }

    public void onParticipantWithoutInteractor(Participant participant, FileSourceContext context) {
        isValid = false;
    }

    public void onInteractionWithoutParticipants(Interaction interaction, FileSourceContext context) {
        isValid = false;
    }


    public void onMissingInteractorIdentifierColumns(int line, int column, int mitabColumn) {
        isValid = false;
    }

    public void onSeveralOrganismFound(Collection<MitabOrganism> organisms) {
        // do nothing
    }

    public void onParticipantWithoutInteractorDetails(int line, int column, int mitabColumn) {
        isValid = false;
    }

    public void onSeveralStoichiometryFound(Collection<MitabStoichiometry> stoichiometry) {
        // do nothing
    }

    public void onSeveralFirstAuthorFound(Collection<MitabAuthor> authors) {
        // do nothing
    }

    public void onSeveralSourceFound(Collection<MitabSource> sources) {
        // do nothing
    }

    public void onSeveralCreatedDateFound(Collection<MitabDate> dates) {
        // do nothing
    }

    public void onSeveralUpdatedDateFound(Collection<MitabDate> dates) {
        // do nothing
    }

    public void onTextFoundInIdentifier(MitabXref xref) {
        // do nothing
    }

    public void onTextFoundInConfidence(MitabConfidence conf) {
        // do nothing
    }

    public void onMissingExpansionId(MitabCvTerm expansion) {
        // do nothing
    }

    public void onSeveralUniqueIdentifiers(Collection<MitabXref> ids) {
        // do nothing
    }

    public void onEmptyUniqueIdentifiers(int line, int column, int mitabColumn) {
        isValid = false;
    }

    public Iterator<T> getInteractionsIterator() {
        if (!isInitialised){
            throw new IllegalArgumentException("The options for the MitabDataSource should contain at least "+ MIDataSourceFactory.INPUT_FILE_OPTION_KEY
                    + " or " + MIDataSourceFactory.INPUT_STREAM_OPTION_KEY + " or " + MIDataSourceFactory.READER_OPTION_KEY+ " to know where to load the interactions from.");
        }
        // reset parser if possible
        if (lineParser.hasFinished()){
           reInit();
        }
        return createMitabIterator();
    }

    protected MitabLineParser<B,P> getLineParser() {
        return lineParser;
    }

    protected void setLineParser(MitabLineParser<B,P> lineParser) {
        this.lineParser = lineParser;
        this.lineParser.setParserListener(this);
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

    protected void setOriginalFile(File originalFile) {
        this.originalFile = originalFile;
    }

    protected void setOriginalStream(InputStream originalStream) {
        this.originalStream = originalStream;
    }

    protected void setOriginalReader(Reader originalReader) {
        this.originalReader = originalReader;
    }

    protected void setMIFileParserListener(MitabParserListener listener) {
        this.parserListener = listener;
    }
}
