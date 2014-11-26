package psidev.psi.mi.jami.crosslink.extension.datasource;

import com.googlecode.jcsv.CSVStrategy;
import com.googlecode.jcsv.reader.CSVReader;
import com.googlecode.jcsv.reader.internal.CSVReaderBuilder;
import psidev.psi.mi.jami.crosslink.extension.CsvProtein;
import psidev.psi.mi.jami.crosslink.extension.CsvRange;
import psidev.psi.mi.jami.crosslink.extension.CsvSourceLocator;
import psidev.psi.mi.jami.crosslink.io.iterator.CsvInteractionEvidenceIterator;
import psidev.psi.mi.jami.crosslink.io.parser.AbstractCsvInteractionEvidenceParser;
import psidev.psi.mi.jami.crosslink.listener.CsvParserListener;
import psidev.psi.mi.jami.datasource.DefaultFileSourceContext;
import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.datasource.SourceCategory;
import psidev.psi.mi.jami.exception.MIIOException;
import psidev.psi.mi.jami.factory.options.MIFileDataSourceOptions;
import psidev.psi.mi.jami.listener.MIFileParserListener;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.utils.MIFileDatasourceUtils;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Abstract class for an InteractionSource coming from a Crosslink CSV file.
 *
 * This datasource provides interaction iterator and collection
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>08/11/13</pre>
 */

public abstract class AbstractCsvSource<T extends InteractionEvidence> implements CsvSource<T> {
    private static final Logger logger = Logger.getLogger("AbstractCsvStreamSource");
    private AbstractCsvInteractionEvidenceParser<T> lineParser;
    private boolean isInitialised = false;

    private URL originalURL;
    private File originalFile;
    private InputStream originalStream;
    private Reader originalReader;

    private Boolean isValid = null;

    private CsvParserListener parserListener;
    private MIFileParserListener defaultParserListener;

    private CSVReader<T> csvReader;

    private Collection<T> interactions= Collections.EMPTY_LIST;

    /**
     * Empty constructor for the factory
     */
    public AbstractCsvSource(){
    }

    public AbstractCsvSource(File file) throws IOException {

        initialiseFile(file);
        isInitialised = true;
    }

    public AbstractCsvSource(InputStream input) {

        initialiseInputStream(input);
        isInitialised = true;
    }

    public AbstractCsvSource(Reader reader) {

        initialiseReader(reader);
        isInitialised = true;
    }

    public AbstractCsvSource(URL url) {

        initialiseURL(url);
        isInitialised = true;
    }

    public MIFileParserListener getFileParserListener() {
        return this.defaultParserListener;
    }

    public void setFileParserListener(MIFileParserListener listener) {
        this.defaultParserListener = listener;
        if (listener instanceof CsvParserListener){
            this.parserListener = (CsvParserListener)listener;
        }
    }

    public void initialiseContext(Map<String, Object> options) {
        if (options == null && !isInitialised){
            throw new IllegalArgumentException("The options for the CrossLink CSV interaction datasource should contain at least "+
                    MIFileDataSourceOptions.INPUT_OPTION_KEY + " to know where to read the interactions from.");
        }
        else if (options == null){
            return;
        }
        else if (options.containsKey(MIFileDataSourceOptions.INPUT_OPTION_KEY)){
            Object input = options.get(MIFileDataSourceOptions.INPUT_OPTION_KEY);
            if (input instanceof URL){
                initialiseURL((URL) input);
            }
            else if (input instanceof File){
                initialiseFile((File) input);
            }
            else if (input instanceof InputStream){
                initialiseInputStream((InputStream) input);
            }
            else if (input instanceof Reader){
                initialiseReader((Reader) input);
            }
            // suspect a file/url path
            else if (input instanceof String){
                String inputString = (String)input;
                SourceCategory category = MIFileDatasourceUtils.findSourceCategoryFromString(inputString);
                switch (category){
                    // file uri
                    case file_URI:
                        try {
                            initialiseFile(new File(new URI(inputString)));
                        } catch (URISyntaxException e) {
                            throw new IllegalArgumentException("Impossible to open and read the file " + inputString, e);
                        }
                        break;
                    // we have a url
                    case URL:
                        try {
                            initialiseURL(new URL(inputString));
                        } catch (MalformedURLException e) {
                            throw new IllegalArgumentException("Impossible to open and read the URL " + inputString, e);
                        }
                        break;
                    // we have a file
                    default:
                        initialiseFile(new File(inputString));
                        break;

                }
            }
            else {
                throw new IllegalArgumentException("Impossible to read the provided input "+input.getClass().getName() + ", a File, InputStream, Reader, URL or file/URL path was expected.");
            }
        }
        else if (!isInitialised){
            throw new IllegalArgumentException("The options for the Mitab interaction datasource should contain at least "+ MIFileDataSourceOptions.INPUT_OPTION_KEY + " to know where to read the interactions from.");
        }

        if (options.containsKey(MIFileDataSourceOptions.PARSER_LISTENER_OPTION_KEY)){
            setMIFileParserListener((MIFileParserListener) options.get(MIFileDataSourceOptions.PARSER_LISTENER_OPTION_KEY));
        }

        isInitialised = true;
    }

    public void close() throws MIIOException{
        if (isInitialised){

            if (this.originalStream != null){
                try {
                    this.originalStream.close();
                } catch (IOException e) {
                    throw new MIIOException("Impossible to close the original stream", e);
                }
                finally {
                    this.originalFile = null;
                    this.originalURL = null;
                    this.originalStream = null;
                    this.originalReader = null;
                    this.lineParser = null;
                    this.parserListener = null;
                    this.defaultParserListener = null;
                    isInitialised = false;
                    isValid = null;
                    isInitialised = false;
                    this.csvReader = null;
                    this.interactions = Collections.EMPTY_LIST;
                }
            }
            else if (this.originalReader != null){
                try {
                    this.originalReader.close();
                } catch (IOException e) {
                    throw new MIIOException("Impossible to close the original reader", e);
                }
                finally {
                    this.originalFile = null;
                    this.originalURL = null;
                    this.originalStream = null;
                    this.originalReader = null;
                    this.lineParser = null;
                    this.parserListener = null;
                    this.defaultParserListener = null;
                    isInitialised = false;
                    isValid = null;
                    isInitialised = false;
                    this.csvReader = null;
                    this.interactions = Collections.EMPTY_LIST;
                }
            }
            else{
                this.originalFile = null;
                this.originalURL = null;
                this.originalStream = null;
                this.originalReader = null;
                this.lineParser = null;
                this.parserListener = null;
                this.defaultParserListener = null;
                isInitialised = false;
                isValid = null;
                isInitialised = false;
                this.csvReader = null;
                this.interactions = Collections.EMPTY_LIST;
            }
        }
    }

    public void reset() throws MIIOException{
        if (isInitialised){
            this.originalFile = null;
            this.originalURL = null;
            this.originalStream = null;
            this.originalReader = null;
            this.lineParser = null;
            this.parserListener = null;
            this.defaultParserListener = null;
            isInitialised = false;
            isValid = null;
            isInitialised = false;
            this.csvReader = null;
            this.interactions = Collections.EMPTY_LIST;
        }
    }

    public boolean validateSyntax(MIFileParserListener listener) {
        setMIFileParserListener(listener);
        return validateSyntax();
    }

    public boolean validateSyntax() {
        if (!isInitialised){
            throw new IllegalStateException("The Mitab interaction datasource has not been initialised. The options for the Mitab interaction datasource should contain at least "+ MIFileDataSourceOptions.INPUT_OPTION_KEY + " to know where to read the interactions from.");
        }

        if (!this.interactions.isEmpty() && isValid == null){
            isValid = true;
        }

        if (isValid != null){
            return isValid;
        }

        // read the datasource
        getInteractions();
        // if isValid is not null, it means that the file syntax is invalid, otherwise, we say that the file syntax is valid
        if (isValid == null){
            isValid = true;
        }
        return isValid;
    }

    public void onInvalidSyntax(FileSourceContext context, Exception e) {
        isValid = false;
        if (defaultParserListener != null){
            defaultParserListener.onInvalidSyntax(context, e);
        }
    }

    public void onSyntaxWarning(FileSourceContext context, String message) {
        if (defaultParserListener != null){
            defaultParserListener.onSyntaxWarning(context, message);
        }
    }

    public void onMissingCvTermName(CvTerm term, FileSourceContext context, String message) {
        if (defaultParserListener != null){
            defaultParserListener.onMissingCvTermName(term, context, message);
        }
    }

    public void onMissingInteractorName(Interactor interactor, FileSourceContext context) {
        if (defaultParserListener != null){
            defaultParserListener.onMissingInteractorName(interactor, context);
        }
    }

    public void onMismatchBetweenPeptideAndLinkedPositions(List<CsvRange> peptidePositions, List<CsvRange> linkedPositions) {
        if (parserListener != null){
            parserListener.onMismatchBetweenPeptideAndLinkedPositions(peptidePositions, linkedPositions);
        }
        else if (defaultParserListener != null){
            defaultParserListener.onSyntaxWarning(!peptidePositions.isEmpty()? peptidePositions.iterator().next() : linkedPositions.iterator().next()
                    , "The number of peptide positions does not match the number of linked positions and therefore, will be ignored");
        }
    }

    public void onMismatchBetweenRangePositionsAndProteins(List<CsvRange> rangePositions, List<CsvProtein> proteins) {
        if (parserListener != null){
            parserListener.onMismatchBetweenRangePositionsAndProteins(rangePositions, proteins);
        }
        else if (defaultParserListener != null){
            defaultParserListener.onSyntaxWarning(!rangePositions.isEmpty()? rangePositions.iterator().next() : proteins.iterator().next()
                    , "The number of range/linked positions does not match the number of protein identifiers and therefore, will be ignored");
        }
    }

    public void onInvalidProteinIdentifierSyntax(String[] identifiers, int lineNumber, int columnNumber) {
        if (parserListener != null){
            parserListener.onInvalidProteinIdentifierSyntax(identifiers, lineNumber, columnNumber);
        }
        else if (defaultParserListener != null){
            defaultParserListener.onSyntaxWarning(new DefaultFileSourceContext(new CsvSourceLocator(lineNumber, -1, columnNumber))
                    , "The protein1/protein2 columns syntax should be sp|uniprotId|name and if several proteins are provided, the identifiers " +
                    "should be separeted by ';'");
        }
    }

    public void onMissingProtein1Column(int lineNumber) {
        if (parserListener != null){
            parserListener.onMissingProtein1Column(lineNumber);
        }
        else if (defaultParserListener != null){
            defaultParserListener.onSyntaxWarning(new DefaultFileSourceContext(new CsvSourceLocator(lineNumber, -1, -1))
                    , "The protein1 column is missing or empty and it is required.");
        }
    }

    public void onParticipantWithoutInteractor(Participant participant, FileSourceContext context) {
        isValid = false;
        if (defaultParserListener != null){
            defaultParserListener.onParticipantWithoutInteractor(participant, context);
        }
    }

    public void onInteractionWithoutParticipants(Interaction interaction, FileSourceContext context) {
        isValid = false;
        if (defaultParserListener != null){
            defaultParserListener.onInteractionWithoutParticipants(interaction, context);
        }
    }

    public void onInvalidOrganismTaxid(String taxid, FileSourceContext context) {
        if (defaultParserListener != null){
            defaultParserListener.onInvalidOrganismTaxid(taxid, context);
        }
    }

    public void onMissingParameterValue(FileSourceContext context) {
        isValid = false;
        if (defaultParserListener != null){
            defaultParserListener.onMissingParameterValue(context);
        }
    }

    public void onMissingParameterType(FileSourceContext context) {
        isValid = false;
        if (defaultParserListener != null){
            defaultParserListener.onMissingParameterType(context);
        }
    }

    public void onMissingConfidenceValue(FileSourceContext context) {
        isValid = false;
        if (defaultParserListener != null){
            defaultParserListener.onMissingConfidenceValue(context);
        }
    }

    public void onMissingConfidenceType(FileSourceContext context) {
        isValid = false;
        if (defaultParserListener != null){
            defaultParserListener.onMissingConfidenceType(context);
        }
    }

    public void onMissingChecksumValue(FileSourceContext context) {
        isValid = false;
        if (defaultParserListener != null){
            defaultParserListener.onMissingChecksumValue(context);
        }
    }

    public void onMissingChecksumMethod(FileSourceContext context) {
        isValid = false;
        if (defaultParserListener != null){
            defaultParserListener.onMissingChecksumMethod(context);
        }
    }

    public void onInvalidPosition(String message, FileSourceContext context) {
        if (defaultParserListener != null){
            defaultParserListener.onInvalidPosition(message, context);
        }
    }

    public void onInvalidRange(String message, FileSourceContext context) {
        if (defaultParserListener != null){
            defaultParserListener.onInvalidRange(message, context);
        }
    }

    public void onInvalidStoichiometry(String message, FileSourceContext context) {
        if (defaultParserListener != null){
            defaultParserListener.onInvalidStoichiometry(message, context);
        }
    }

    public void onXrefWithoutDatabase(FileSourceContext context) {
        isValid = false;
        if (defaultParserListener != null){
            defaultParserListener.onXrefWithoutDatabase(context);
        }
    }

    public void onXrefWithoutId(FileSourceContext context) {
        isValid = false;
        if (defaultParserListener != null){
            defaultParserListener.onXrefWithoutId(context);
        }
    }

    public void onAnnotationWithoutTopic(FileSourceContext context) {
        isValid = false;
        if (defaultParserListener != null){
            defaultParserListener.onAnnotationWithoutTopic(context);
        }
    }

    public void onAliasWithoutName(FileSourceContext context) {
        isValid = false;
        if (defaultParserListener != null){
            defaultParserListener.onAliasWithoutName(context);
        }
    }

    public Iterator<T> getInteractionsIterator() {
        if (!isInitialised){
            throw new IllegalStateException("The Mitab interaction datasource has not been initialised. The options for the Mitab interaction datasource should contain at least "+ MIFileDataSourceOptions.INPUT_OPTION_KEY + " to know where to read the interactions from.");
        }
        // reset parser if possible
        if (this.interactions.isEmpty()){
            try {
                this.interactions = this.csvReader.readAll();
            } catch (IOException e) {
                onInvalidSyntax(new DefaultFileSourceContext(), e);
                this.interactions = Collections.EMPTY_LIST;
            }
        }
        return this.interactions.iterator();
    }

    public Collection<T> getInteractions() throws MIIOException {
        // reset parser if possible
        if (this.interactions.isEmpty()){
            try {
                this.interactions = this.csvReader.readAll();
            } catch (IOException e) {
                onInvalidSyntax(new DefaultFileSourceContext(), e);
                this.interactions = Collections.EMPTY_LIST;
            }
        }
        return this.interactions;
    }

    public long getNumberOfInteractions() {
        // reset parser if possible
        if (this.interactions.isEmpty()){
            try {
                this.interactions = this.csvReader.readAll();
            } catch (IOException e) {
                onInvalidSyntax(new DefaultFileSourceContext(), e);
                this.interactions = Collections.EMPTY_LIST;
            }
        }
        return this.interactions.size();
    }

    protected AbstractCsvInteractionEvidenceParser<T> getLineParser() {
        return lineParser;
    }

    protected void setLineParser(AbstractCsvInteractionEvidenceParser<T> lineParser) {
        this.lineParser = lineParser;
        this.lineParser.setParserListener(this);
    }

    protected Iterator<T> createCsvIterator(){
        return new CsvInteractionEvidenceIterator<T>(this.csvReader, this);
    }

    protected void reInit() throws MIIOException{
        if (isInitialised){

            if (this.originalFile != null){
                // close the previous stream
                if (this.originalStream != null){
                    try {
                        this.originalStream.close();
                    } catch (IOException e) {
                        logger.log(Level.SEVERE, "Could not close the inputStream.", e);
                    }
                }
                // reinitialise mitab parser
                try {
                    this.originalStream = new BufferedInputStream(new FileInputStream(this.originalFile));
                    this.originalReader = new BufferedReader(new InputStreamReader(this.originalStream));
                } catch (FileNotFoundException e) {
                    throw new MIIOException("We cannot open the file " + this.originalFile.getName(), e);
                }   catch (IOException e) {
                    throw new MIIOException("The CSV file does not have valid headers: " + this.originalFile.getName(), e);
                }
            }
            else if (this.originalURL != null){
                // close the previous stream
                if (this.originalStream != null){
                    try {
                        this.originalStream.close();
                    } catch (IOException e) {
                        logger.log(Level.SEVERE, "Could not close the inputStream.", e);
                    }
                }
                // reinitialise mitab parser
                try {
                    this.originalStream = originalURL.openStream();
                    this.originalReader = new BufferedReader(new InputStreamReader(this.originalStream));

                } catch (IOException e) {
                    throw new MIIOException("We cannot open the URL " + this.originalURL.toExternalForm(), e);
                }
            }
            else if (this.originalStream != null){
                // reinit line parser if inputStream can be reset
                if (this.originalStream.markSupported()){
                    try {
                        this.originalStream.reset();
                        this.originalReader = new BufferedReader(new InputStreamReader(this.originalStream));

                    } catch (IOException e) {
                        throw new MIIOException("The inputStream has been consumed and cannot be reset", e);
                    }
                }
                else {
                    throw new MIIOException("The inputStream has been consumed and cannot be reset");
                }
            }
            else if (this.originalReader != null){
                // reinit line parser if reader can be reset
                if (this.originalReader.markSupported()){
                    try {
                        this.originalReader.reset();

                    } catch (IOException e) {
                        throw new MIIOException("The reader has been consumed and cannot be reset", e);
                    }
                }
                else {
                    throw new MIIOException("The reader has been consumed and cannot be reset");
                }
            }
            createCsvReader();
        }
    }

    protected abstract AbstractCsvInteractionEvidenceParser<T> instantiateLineParser();

    private void initialiseReader(Reader reader) {
        if (reader == null){
            throw new IllegalArgumentException("The reader cannot be null.");
        }
        this.originalFile = null;
        this.originalReader = reader;
        this.originalStream = null;
        this.originalURL = null;

        createCsvReader();
    }

    private void initialiseInputStream(InputStream input) {
        if (input == null){
            throw new IllegalArgumentException("The input stream cannot be null.");
        }
        this.originalFile = null;
        this.originalStream = input;
        this.originalURL = null;
        this.originalReader = new BufferedReader(new InputStreamReader(this.originalStream));

        createCsvReader();
    }

    private void initialiseFile(File file)  {
        if (file == null){
            throw new IllegalArgumentException("The file cannot be null.");
        }
        else if (!file.canRead()){
            throw new IllegalArgumentException("Does not have the permissions to read the file "+file.getAbsolutePath());
        }
        this.originalFile = file;
        try {
            this.originalStream = new BufferedInputStream(new FileInputStream(this.originalFile));
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("Cannot find file "+file.getAbsolutePath());
        }
        this.originalReader = new BufferedReader(new InputStreamReader(this.originalStream));
        this.originalURL = null;
        createCsvReader();
    }

    private void initialiseURL(URL url)  {
        if (url == null){
            throw new IllegalArgumentException("The url cannot be null.");
        }
        this.originalURL = url;
        try {
            this.originalStream = originalURL.openStream();
        } catch (IOException e) {
            throw new IllegalArgumentException("Cannot open URL  "+this.originalURL.toString());
        }
        this.originalReader = new BufferedReader(new InputStreamReader(this.originalStream));
        this.originalFile = null;

        createCsvReader();
    }

    private void createCsvReader() {
        this.lineParser = instantiateLineParser();
        this.lineParser.setParserListener(this);

        this.csvReader = new CSVReaderBuilder<T>(this.originalReader).
                entryParser(this.lineParser).
                strategy(CSVStrategy.UK_DEFAULT).
                build();
        try {
            this.lineParser.initialiseColumnNames(this.csvReader.readHeader());
        } catch (IOException e) {
            throw new MIIOException("The CSV file does not have valid headers", e);
        }
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

    protected void setOriginalURL(URL originalURL) {
        this.originalURL = originalURL;
    }

    protected void setCsvFileParserListener(CsvParserListener listener) {
        this.parserListener = listener;
        this.defaultParserListener = listener;
    }

    protected void setMIFileParserListener(MIFileParserListener listener) {
        if (listener instanceof CsvParserListener){
            setCsvFileParserListener((CsvParserListener) listener);
        }
        else{
            this.parserListener = null;
            this.defaultParserListener = listener;
        }
    }
}
