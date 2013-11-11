package psidev.psi.mi.jami.xml.io.parser;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.xml.sax.*;
import org.xml.sax.helpers.XMLReaderFactory;
import psidev.psi.mi.jami.binary.BinaryInteraction;
import psidev.psi.mi.jami.binary.expansion.ComplexExpansionMethod;
import psidev.psi.mi.jami.datasource.*;
import psidev.psi.mi.jami.exception.MIIOException;
import psidev.psi.mi.jami.factory.InteractionWriterFactory;
import psidev.psi.mi.jami.factory.MIDataSourceFactory;
import psidev.psi.mi.jami.listener.MIFileParserListener;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Interaction;
import psidev.psi.mi.jami.model.Interactor;
import psidev.psi.mi.jami.model.Participant;
import psidev.psi.mi.jami.utils.MIFileDatasourceUtils;
import psidev.psi.mi.jami.xml.PsiXml25IdIndex;
import psidev.psi.mi.jami.xml.XmlEntryContext;
import psidev.psi.mi.jami.xml.reference.XmlIdReference;
import psidev.psi.mi.jami.xml.exception.PsiXmlParserException;
import psidev.psi.mi.jami.xml.listener.PsiXmlParserListener;
import psidev.psi.mi.jami.xml.utils.PsiXml25Utils;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Abstract class for psiXml data source
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>16/10/13</pre>
 */

public abstract class AbstractPsiXml25Stream<T extends Interaction> implements MIFileDataSource, InteractionStream<T>, PsiXmlParserListener,ErrorHandler {

    private static final Logger logger = Logger.getLogger("AbstractPsiXml25Stream");

    private boolean isInitialised = false;
    private PsiXml25Parser<T> parser;
    private MIFileParserListener defaultParserListener;
    private PsiXmlParserListener parserListener;
    private URL originalURL;
    private File originalFile;
    private InputStream originalStream;
    private Reader originalReader;

    private Boolean isValid = null;
    private PsiXml25IdIndex elementCache;
    private PsiXml25IdIndex complexCache;

    public static final String VALIDATION_FEATURE = "http://xml.org/sax/features/validation";
    public static final String SCHEMA_FEATURE = "http://apache.org/xml/features/validation/schema";

    public AbstractPsiXml25Stream(){
    }

    public AbstractPsiXml25Stream(File file) {

        initialiseFile(file);
        isInitialised = true;
    }

    public AbstractPsiXml25Stream(InputStream input) {

        initialiseInputStream(input);
        isInitialised = true;
    }

    public AbstractPsiXml25Stream(Reader reader) {

        initialiseReader(reader);
        isInitialised = true;
    }

    public AbstractPsiXml25Stream(URL url) {

        initialiseURL(url);
        isInitialised = true;
    }

    @Override
    public Iterator<T> getInteractionsIterator() throws MIIOException {
        if (!isInitialised){
            throw new IllegalStateException("The PsiXml interaction datasource has not been initialised. The options for the Psi xml 2.5 interaction datasource should contain at least "+ MIDataSourceFactory.INPUT_OPTION_KEY + " to know where to read the interactions from.");
        }
        // reset parser if possible
        try {
            if (this.parser.hasFinished()){
                reInit();
            }
        } catch (PsiXmlParserException e) {
            if (defaultParserListener != null){
                defaultParserListener.onInvalidSyntax(new DefaultFileSourceContext(e.getLocator()), e);
            }
            else{
                throw new MIIOException("Cannot know if the parser has finished.", e);
            }
        }
        return createXmlIterator();
    }

    @Override
    public MIFileParserListener getFileParserListener() {
        return this.defaultParserListener;
    }

    @Override
    public boolean validateSyntax() throws MIIOException {
        if (!isInitialised){
            throw new IllegalStateException("The PsiXml interaction datasource has not been initialised. The options for the Psi xml interaction datasource should contain at least "+ MIDataSourceFactory.INPUT_OPTION_KEY + " to know where to read the interactions from.");
        }

        if (isValid != null){
            return isValid;
        }

        // if the parser did parse all interactions, we reset the current streams
        try {
            if (this.parser.hasFinished()){
                reInit();
            }
        } catch (PsiXmlParserException e) {
            if (defaultParserListener != null){
                defaultParserListener.onInvalidSyntax(new DefaultFileSourceContext(e.getLocator()), e);
            }
            else{
                throw new MIIOException("Cannot know if the parser has finished.", e);
            }
        }

        try {
            XMLReader r = XMLReaderFactory.createXMLReader();

            r.setFeature( VALIDATION_FEATURE, true );
            r.setFeature( SCHEMA_FEATURE, true );

            r.setErrorHandler( this );
            if (this.originalFile != null){
                r.parse( new InputSource( new FileInputStream(this.originalFile)));
            }
            else if (this.originalURL != null){
                r.parse( new InputSource( this.originalURL.openStream()));
            }
            else if (this.originalStream != null){
                r.parse( new InputSource( this.originalStream));
            }
            else if (this.originalReader != null){
                r.parse( new InputSource( this.originalReader));
            }

        } catch (SAXException e) {
            onInvalidSyntax(new DefaultFileSourceContext(), e);
        } catch (FileNotFoundException e) {
            throw new MIIOException("Impossible to validate the source file",e);
        } catch (IOException e) {
            throw new MIIOException("Impossible to validate the source file",e);
        }
        if (isValid == null){
           isValid = true;
        }

        return isValid;
    }

    @Override
    public boolean validateSyntax(MIFileParserListener listener) throws MIIOException {
        setMIFileParserListener(listener);
        return validateSyntax();
    }

    @Override
    public void initialiseContext(Map<String, Object> options) {
        File sourceFile = null;
        URL sourceUrl = null;
        InputStream sourceStream = null;
        Reader sourceReader = null;

        if (options == null && !isInitialised){
            throw new IllegalArgumentException("The options for the PsiXml interaction datasource should contain at least "+ MIDataSourceFactory.INPUT_OPTION_KEY + " to know where to read the interactions from.");
        }
        else if (options == null){
            return;
        }
        else if (options.containsKey(MIDataSourceFactory.INPUT_OPTION_KEY)){
            Object input = options.get(MIDataSourceFactory.INPUT_OPTION_KEY);
            if (input instanceof URL){
                sourceUrl = (URL) input;
            }
            else if (input instanceof File){
                sourceFile = (File) input;
            }
            else if (input instanceof InputStream){
                sourceStream = (InputStream) input;
            }
            else if (input instanceof Reader){
                sourceReader = (Reader) input;
            }
            // suspect a file/url path
            else if (input instanceof String){
                String inputString = (String)input;
                SourceCategory category = MIFileDatasourceUtils.findSourceCategoryFromString(inputString);
                switch (category){
                    // file uri
                    case file_URI:
                        try {
                            sourceFile = new File(new URI(inputString));
                        } catch (URISyntaxException e) {
                            throw new IllegalArgumentException("Impossible to open and read the file " + inputString, e);
                        }
                        break;
                    // we have a url
                    case URL:
                        try {
                            sourceUrl = new URL(inputString);
                        } catch (MalformedURLException e) {
                            throw new IllegalArgumentException("Impossible to open and read the URL " + inputString, e);
                        }
                        break;
                    // we have a file
                    default:
                        sourceFile = new File(inputString);
                        break;

                }
            }
            else {
                throw new IllegalArgumentException("Impossible to read the provided input "+input.getClass().getName() + ", a File, InputStream, Reader, URL or file/URL path was expected.");
            }
        }
        else if (!isInitialised){
            throw new IllegalArgumentException("The options for the Psi xml interaction datasource should contain at least "+ MIDataSourceFactory.INPUT_OPTION_KEY + " to know where to read the interactions from.");
        }

        if (options.containsKey(InteractionWriterFactory.COMPLEX_EXPANSION_OPTION_KEY)){
            initialiseExpansionMethod((ComplexExpansionMethod<T,? extends BinaryInteraction>)options.get(MIDataSourceFactory.COMPLEX_EXPANSION_OPTION_KEY));
        }

        if (options.containsKey(MIDataSourceFactory.PARSER_LISTENER_OPTION_KEY)){
            setMIFileParserListener((MIFileParserListener) options.get(MIDataSourceFactory.PARSER_LISTENER_OPTION_KEY));
        }

        if (options.containsKey(PsiXml25Utils.ELEMENT_WITH_ID_CACHE_OPTION)){
            this.elementCache = (PsiXml25IdIndex)options.get(PsiXml25Utils.ELEMENT_WITH_ID_CACHE_OPTION);
        }

        if (options.containsKey(PsiXml25Utils.COMPLEX_CACHE_OPTION)){
            this.complexCache = (PsiXml25IdIndex)options.get(PsiXml25Utils.COMPLEX_CACHE_OPTION);
        }

        // initialise parser after reading all options
        if (sourceFile != null){
            initialiseFile(sourceFile);
        }
        else if (sourceUrl != null){
            initialiseURL(sourceUrl);
        }
        else if (sourceStream != null){
            initialiseInputStream(sourceStream);
        }
        else if (sourceReader!= null){
            initialiseReader(sourceReader);
        }
        isInitialised = true;
    }

    @Override
    public void close() throws MIIOException{
        if (isInitialised){
            this.elementCache = null;
            this.complexCache = null;
            this.parserListener = null;
            this.defaultParserListener = null;
            this.isValid = null;
            isInitialised = false;
            if (this.parser != null){
                this.parser.close();
            }
        }
    }

    @Override
    public void reset() throws MIIOException{
        if (isInitialised){
            this.elementCache = null;
            this.complexCache = null;
            this.parser = null;
            this.parserListener = null;
            this.defaultParserListener = null;
            isInitialised = false;
            this.isValid = null;
        }
    }

    @Override
    public void onUnresolvedReference(XmlIdReference ref, String message) {
        if (parserListener != null){
            parserListener.onUnresolvedReference(ref, message);
        }
        else if (defaultParserListener != null){
            defaultParserListener.onSyntaxWarning(ref, message);
        }
    }

    @Override
    public void onInvalidSyntax(FileSourceContext context, Exception e) {
        if (defaultParserListener != null){
            defaultParserListener.onInvalidSyntax(context, e);
        }
    }

    @Override
    public void onSyntaxWarning(FileSourceContext context, String message) {
        if (defaultParserListener != null){
            defaultParserListener.onSyntaxWarning(context, message);
        }
    }

    @Override
    public void onMissingCvTermName(CvTerm term, FileSourceContext context, String message) {
        if (defaultParserListener != null){
            defaultParserListener.onMissingCvTermName(term, context, message);
        }
    }

    @Override
    public void onMissingInteractorName(Interactor interactor, FileSourceContext context) {
        if (defaultParserListener != null){
            defaultParserListener.onMissingInteractorName(interactor, context);
        }
    }

    @Override
    public void onParticipantWithoutInteractor(Participant participant, FileSourceContext context) {
        if (defaultParserListener != null){
            defaultParserListener.onParticipantWithoutInteractor(participant, context);
        }
    }

    @Override
    public void onInteractionWithoutParticipants(Interaction interaction, FileSourceContext context) {
        if (defaultParserListener != null){
            defaultParserListener.onInteractionWithoutParticipants(interaction, context);
        }
    }

    @Override
    public void warning(SAXParseException exception) throws SAXException {
        isValid = false;
        if (defaultParserListener != null){
            defaultParserListener.onSyntaxWarning(new DefaultFileSourceContext(new FileSourceLocator(exception.getLineNumber(), exception.getColumnNumber())), ExceptionUtils.getFullStackTrace(exception));
        }
    }

    @Override
    public void error(SAXParseException exception) throws SAXException {
        isValid = false;
        if (defaultParserListener != null){
            defaultParserListener.onInvalidSyntax(new DefaultFileSourceContext(new FileSourceLocator(exception.getLineNumber(), exception.getColumnNumber())), exception);
        }
    }

    @Override
    public void fatalError(SAXParseException exception) throws SAXException {
        isValid = false;
        if (defaultParserListener != null){
            defaultParserListener.onInvalidSyntax(new DefaultFileSourceContext(new FileSourceLocator(exception.getLineNumber(), exception.getColumnNumber())), exception);
        }
    }

    @Override
    public void onInvalidOrganismTaxid(String taxid, FileSourceContext context) {
        if (defaultParserListener != null){
            defaultParserListener.onInvalidOrganismTaxid(taxid, context);
        }
    }

    @Override
    public void onMissingParameterValue(FileSourceContext context) {
        if (defaultParserListener != null){
            defaultParserListener.onMissingParameterValue(context);
        }
    }

    @Override
    public void onMissingParameterType(FileSourceContext context) {
        if (defaultParserListener != null){
            defaultParserListener.onMissingParameterType(context);
        }
    }

    @Override
    public void onMissingConfidenceValue(FileSourceContext context) {
        if (defaultParserListener != null){
            defaultParserListener.onMissingConfidenceValue(context);
        }
    }

    @Override
    public void onMissingConfidenceType(FileSourceContext context) {
        if (defaultParserListener != null){
            defaultParserListener.onMissingConfidenceType(context);
        }
    }

    @Override
    public void onMissingChecksumValue(FileSourceContext context) {
        if (defaultParserListener != null){
            defaultParserListener.onMissingChecksumValue(context);
        }
    }

    @Override
    public void onMissingChecksumMethod(FileSourceContext context) {
        if (defaultParserListener != null){
            defaultParserListener.onMissingChecksumMethod(context);
        }
    }

    @Override
    public void onInvalidPosition(String message, FileSourceContext context) {
        if (defaultParserListener != null){
            defaultParserListener.onInvalidPosition(message, context);
        }
    }

    @Override
    public void onInvalidRange(String message, FileSourceContext context) {
        if (defaultParserListener != null){
            defaultParserListener.onInvalidRange(message, context);
        }
    }

    @Override
    public void onInvalidStoichiometry(String message, FileSourceContext context) {
        if (defaultParserListener != null){
            defaultParserListener.onInvalidStoichiometry(message, context);
        }
    }

    @Override
    public void onXrefWithoutDatabase(FileSourceContext context) {
        if (defaultParserListener != null){
            defaultParserListener.onXrefWithoutDatabase(context);
        }
    }

    @Override
    public void onXrefWithoutId(FileSourceContext context) {
        if (defaultParserListener != null){
            defaultParserListener.onXrefWithoutId(context);
        }
    }

    @Override
    public void onAnnotationWithoutTopic(FileSourceContext context) {
        if (defaultParserListener != null){
            defaultParserListener.onAnnotationWithoutTopic(context);
        }
    }

    @Override
    public void onAliasWithoutName(FileSourceContext context) {
        if (defaultParserListener != null){
            defaultParserListener.onAliasWithoutName(context);
        }
    }

    protected abstract void initialiseXmlParser(Reader reader);

    protected abstract void initialiseXmlParser(File file);

    protected abstract void initialiseXmlParser(InputStream input);

    protected abstract void initialiseXmlParser(URL url);

    protected void setXmlFileParserListener(PsiXmlParserListener listener) {
        this.parserListener = listener;
        this.defaultParserListener = listener;
    }

    protected void setMIFileParserListener(MIFileParserListener listener) {
        if (listener instanceof PsiXmlParserListener){
            setXmlFileParserListener((PsiXmlParserListener) listener);
        }
        else{
            this.parserListener = null;
            this.defaultParserListener = listener;
        }
    }

    protected void reInit() throws MIIOException{
        if (this.elementCache != null){
            this.elementCache.clear();
        }
        if (this.complexCache != null){
            this.complexCache.clear();
        }
        // release the thread local
        XmlEntryContext.getInstance().clear();
        XmlEntryContext.remove();
        // release the thread local
        if (this.originalFile != null){
            // close the previous reader
            if (this.originalReader != null){
                try {
                    this.originalReader.close();
                } catch (IOException e) {
                    logger.log(Level.SEVERE, "Could not close the reader.", e);
                }
            }
            // close the previous stream and reader
            if (this.originalStream != null){
                try {
                    this.originalStream.close();
                } catch (IOException e) {
                    logger.log(Level.SEVERE, "Could not close the inputStream.", e);
                }
            }
            initialiseXmlParser(this.originalFile);
        }
        else if (this.originalURL != null){
            // close the previous reader
            if (this.originalReader != null){
                try {
                    this.originalReader.close();
                } catch (IOException e) {
                    logger.log(Level.SEVERE, "Could not close the reader.", e);
                }
            }
            // close the previous stream
            if (this.originalStream != null){
                try {
                    this.originalStream.close();
                } catch (IOException e) {
                    logger.log(Level.SEVERE, "Could not close the inputStream.", e);
                }
            }
            initialiseXmlParser(this.originalURL);
        }
        else if (this.originalReader != null){
            // reinit line parser if reader can be reset
            if (this.originalReader.markSupported()){
                try {
                    this.originalReader.reset();
                } catch (IOException e) {
                    throw new MIIOException("We cannot open the reader  ", e);
                }
            }
            else {
                throw new MIIOException("The reader has been consumed and cannot be reset");
            }
            initialiseXmlParser(this.originalReader);
        }
        else if (this.originalStream != null){
            // reinit parser if inputStream can be reset
            if (this.originalStream.markSupported()){
                try {
                    this.originalStream.reset();
                } catch (IOException e) {
                    throw new MIIOException("We cannot read the inputStream  ", e);
                }
            }
            else {
                throw new MIIOException("The inputStream has been consumed and cannot be reset");
            }
            initialiseXmlParser(this.originalStream);
        }
    }

    protected abstract void initialiseExpansionMethod(ComplexExpansionMethod<? extends Interaction, ? extends BinaryInteraction> expansionMethod);

    protected abstract Iterator<T> createXmlIterator();

    protected PsiXml25Parser<T> getParser() {
        return parser;
    }

    protected void setParser(PsiXml25Parser<T> parser) {
        this.parser = parser;
    }

    protected boolean isInitialised() {
        return isInitialised;
    }

    protected PsiXml25IdIndex getElementCache() {
        return elementCache;
    }

    protected PsiXml25IdIndex getComplexCache() {
        return complexCache;
    }

    private void initialiseReader(Reader reader) {
        if (reader == null){
            throw new IllegalArgumentException("The reader cannot be null.");
        }
        this.originalReader = reader;
        initialiseXmlParser(reader);
    }

    private void initialiseInputStream(InputStream input) {
        if (input == null){
            throw new IllegalArgumentException("The input stream cannot be null.");
        }
        this.originalStream = input;
        initialiseXmlParser(input);
    }

    private void initialiseFile(File file)  {
        if (file == null){
            throw new IllegalArgumentException("The file cannot be null.");
        }
        else if (!file.canRead()){
            throw new IllegalArgumentException("Does not have the permissions to read the file "+file.getAbsolutePath());
        }
        this.originalFile = file;
        initialiseXmlParser(file);
    }

    private void initialiseURL(URL url)  {
        if (url == null){
            throw new IllegalArgumentException("The url cannot be null.");
        }
        this.originalURL = url;
        initialiseXmlParser(url);
    }
}
