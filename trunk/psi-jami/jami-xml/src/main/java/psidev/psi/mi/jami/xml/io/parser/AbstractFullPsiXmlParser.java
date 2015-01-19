package psidev.psi.mi.jami.xml.io.parser;

import psidev.psi.mi.jami.datasource.DefaultFileSourceContext;
import psidev.psi.mi.jami.exception.MIIOException;
import psidev.psi.mi.jami.model.Interaction;
import psidev.psi.mi.jami.xml.PsiXmlVersion;
import psidev.psi.mi.jami.xml.XmlEntryContext;
import psidev.psi.mi.jami.xml.cache.InMemoryPsiXmlCache;
import psidev.psi.mi.jami.xml.cache.PsiXmlFileIndexCache;
import psidev.psi.mi.jami.xml.cache.PsiXmlIdCache;
import psidev.psi.mi.jami.xml.exception.PsiXmlParserException;
import psidev.psi.mi.jami.xml.listener.PsiXmlParserListener;
import psidev.psi.mi.jami.xml.model.AbstractEntry;
import psidev.psi.mi.jami.xml.model.AbstractEntrySet;
import psidev.psi.mi.jami.xml.model.extension.factory.XmlInteractorFactory;
import psidev.psi.mi.jami.xml.utils.PsiXmlUtils;

import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.net.URL;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class if for parser that will load the all file in memory
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>07/11/13</pre>
 */

public abstract class AbstractFullPsiXmlParser<T extends Interaction> implements PsiXmlParser<T>, FullPsiXmlParser<T> {
    private static final Logger logger = Logger.getLogger("AbstractFullPsiXmlParser");

    private URL originalURL;
    private File originalFile;
    private InputStream originalStream;
    private Reader originalReader;
    private PushbackReader reader;
    private Unmarshaller unmarshaller;
    private Iterator<T> interactionIterator;
    private Iterator<AbstractEntry<T>> entryIterator;
    private PsiXmlParserListener listener;
    private AbstractEntrySet<AbstractEntry<T>> entrySet;
    private XmlInteractorFactory interactorFactory;

    private PsiXmlIdCache indexOfObjects=null;

    private PsiXmlVersion version;

    public AbstractFullPsiXmlParser(File file) throws JAXBException, FileNotFoundException {
        if (file == null){
            throw new IllegalArgumentException("The PsiXmlParser needs a non null File");
        }
        this.originalFile = file;
        this.reader = new PushbackReader(new FileReader(file), PsiXmlUtils.XML_BUFFER_SIZE);
    }

    public AbstractFullPsiXmlParser(InputStream inputStream) throws JAXBException {
        if (inputStream == null){
            throw new IllegalArgumentException("The PsiXmlParser needs a non null InputStream");
        }
        this.originalStream = inputStream;
        this.reader = new PushbackReader(new InputStreamReader(inputStream), PsiXmlUtils.XML_BUFFER_SIZE);
    }

    public AbstractFullPsiXmlParser(URL url) throws IOException, JAXBException {
        if (url == null){
            throw new IllegalArgumentException("The PsiXmlParser needs a non null URL");
        }
        this.originalURL = url;
        this.reader = new PushbackReader(new InputStreamReader(url.openStream()), PsiXmlUtils.XML_BUFFER_SIZE);
    }

    public AbstractFullPsiXmlParser(Reader reader) throws JAXBException {
        if (reader == null){
            throw new IllegalArgumentException("The PsiXmlParser needs a non null Reader");
        }
        this.originalReader = reader;
        this.reader = new PushbackReader(reader, PsiXmlUtils.XML_BUFFER_SIZE);
    }

    @Override
    public T parseNextInteraction() throws PsiXmlParserException {
        // did not parse the entry set yet
        if (this.entrySet == null){
            try {
                initialiseEntryContext(XmlEntryContext.getInstance());
            } catch (JAXBException e) {
                createPsiXmlExceptionFrom("Impossible to read the input source", e);
            }

            this.entrySet = parseEntrySet();
            this.entryIterator = this.entrySet.getEntries().iterator();
        }
        // parsed entry set but did not initialise the interaction iterator
        if (this.interactionIterator == null){
            // create next interaction iterator from next entry
            if (this.entryIterator.hasNext()){
                this.interactionIterator = this.entryIterator.next().getInteractions().iterator();
            }
            // error, should find at least one entry
            else if (this.listener != null){
                listener.onInvalidSyntax(new DefaultFileSourceContext(), new PsiXmlParserException("Empty EntrySet root node. Needs at least one Entry"));
            }
            else{
                throw createPsiXmlExceptionFrom("Empty EntrySet root node. Needs at least one Entry", null);
            }
        }

        // is parsing interaction of an entry
        if (this.interactionIterator.hasNext()){
            return this.interactionIterator.next();
        }
        // parse interactions of the next entry
        else if (this.entryIterator.hasNext()){
            this.interactionIterator = this.entryIterator.next().getInteractions().iterator();
            if (this.interactionIterator.hasNext()){
                return this.interactionIterator.next();
            }
            // error, should find at least one entry
            else if (this.listener != null){
                listener.onInvalidSyntax(new DefaultFileSourceContext(), new PsiXmlParserException("Empty Entry node. Needs at least one interactionList/interaction"));
            }
            else{
                throw createPsiXmlExceptionFrom("Empty Entry node. Needs at least one interactionList/interaction", null);
            }
        }
        return null;
    }

    @Override
    public void close() throws MIIOException {

        if (this.reader != null){
            try {
                this.reader.close();
            } catch (IOException e) {
                throw new MIIOException("Impossible to close the reader", e);
            }
            finally {
                this.originalFile = null;
                this.originalURL = null;
                this.originalStream = null;
                this.originalReader = null;
                this.reader = null;
            }
        }
        else{
            this.originalFile = null;
            this.originalURL = null;
            this.originalStream = null;
            this.originalReader = null;
            this.reader = null;
        }

        this.interactionIterator = null;
        this.entryIterator = null;
        this.unmarshaller = null;
        this.entrySet = null;
        if (this.indexOfObjects != null){
            this.indexOfObjects.close();
        }
        this.indexOfObjects = null;
        this.version = null;
        this.interactorFactory = null;

        // release the thread local
        XmlEntryContext.getInstance().clear();
        XmlEntryContext.remove();
    }

    public XmlInteractorFactory getInteractorFactory() {
        if (interactorFactory == null){
            interactorFactory = new XmlInteractorFactory();
        }
        return interactorFactory;
    }

    public void setInteractorFactory(XmlInteractorFactory interactorFactory) {
        this.interactorFactory = interactorFactory;
    }

    @Override
    public boolean hasFinished() throws PsiXmlParserException {
        if (this.entryIterator != null && !this.entryIterator.hasNext()){
            if (this.interactionIterator != null && !this.interactionIterator.hasNext()){
                return true;
            }
        }
        return false;
    }

    @Override
    public void reInit() throws MIIOException {
        this.interactionIterator = null;
        this.entryIterator = null;
        this.entrySet = null;
        this.version = null;
        if (this.indexOfObjects != null){
            this.indexOfObjects.clear();
        }
        // release the thread local
        XmlEntryContext.getInstance().clear();
        XmlEntryContext.remove();
        if (this.originalReader != null){
            // reinit line parser if reader can be reset
            if (this.originalReader.markSupported()){
                try {
                    this.originalReader.reset();
                    this.reader = new PushbackReader(this.originalReader, PsiXmlUtils.XML_BUFFER_SIZE);
                } catch (IOException e) {
                    throw new MIIOException("We cannot open the reader  ", e);
                }
            }
            else {
                throw new MIIOException("The reader has been consumed and cannot be reset");
            }
        }
        else if (this.originalStream != null){
            // reinit parser if inputStream can be reset
            if (this.originalStream.markSupported()){
                try {
                    this.originalStream.reset();
                    this.reader = new PushbackReader(new InputStreamReader(this.originalStream), PsiXmlUtils.XML_BUFFER_SIZE);
                } catch (IOException e) {
                    throw new MIIOException("We cannot read the inputStream  ", e);
                }
            }
            else {
                throw new MIIOException("The inputStream has been consumed and cannot be reset");
            }
        }
        else if (this.originalURL != null){
            try {
                this.reader.close();
                this.reader = new PushbackReader(new InputStreamReader(this.originalURL.openStream()), PsiXmlUtils.XML_BUFFER_SIZE);
            } catch (IOException e) {
                throw new MIIOException("We cannot read the URL  ", e);
            }
        }
        else if (this.originalFile != null){
            try {
                this.reader.close();
                this.reader = new PushbackReader(new FileReader(this.originalFile), PsiXmlUtils.XML_BUFFER_SIZE);
            } catch (IOException e) {
                throw new MIIOException("We cannot read the URL  ", e);
            }
        }
    }

    @Override
    public PsiXmlParserListener getListener() {
        return this.listener;
    }

    @Override
    public void setListener(PsiXmlParserListener listener) {
        this.listener = listener;
    }

    public void setCacheOfObjects(PsiXmlIdCache indexOfObjects) {
        this.indexOfObjects = indexOfObjects;
    }

    public AbstractEntrySet<AbstractEntry<T>> getEntrySet() throws PsiXmlParserException {
        if (this.entrySet == null){
            this.entrySet = parseEntrySet();
            this.entryIterator = this.entrySet.getEntries().iterator();
        }
        return entrySet;
    }

    protected abstract Unmarshaller createJAXBUnmarshaller() throws JAXBException;

    protected AbstractEntrySet<AbstractEntry<T>> parseEntrySet() throws PsiXmlParserException {
        if (this.reader != null){
            try {
                initialiseEntryContext(XmlEntryContext.getInstance());

                return (AbstractEntrySet<AbstractEntry<T>>) this.unmarshaller.unmarshal(this.reader);
            } catch (JAXBException e) {
                throw createPsiXmlExceptionFrom("Error parsing entrySet from a file.", e);
            }
        }
        else{
            throw createPsiXmlExceptionFrom("Cannot parse entry set because does not have a file, URL, reader or inputstream to parse", null);
        }
    }

    protected PsiXmlParserException createPsiXmlExceptionFrom(String message, Exception e) {
        return new PsiXmlParserException(null, message, e);
    }

    public PsiXmlVersion getVersion() {
        return version;
    }

    private void initialiseEntryContext(XmlEntryContext entryContext) throws PsiXmlParserException, JAXBException {
        entryContext.initialiseInferredInteractionList();
        entryContext.initialiseReferencesList();
        // set interactor factory
        entryContext.setInteractorFactory(getInteractorFactory());

        initialiseVersion(this.reader);
        // create unmarshaller knowing the version
        this.unmarshaller = createJAXBUnmarshaller();

        entryContext.clear();
        entryContext.setListener(this.listener);

        initialiseDefaultCache();
        entryContext.setElementCache(this.indexOfObjects);
    }

    private void initialiseDefaultCache() {
        if (this.indexOfObjects == null){
            if (this.originalFile != null){
                try {
                    this.indexOfObjects = new PsiXmlFileIndexCache(this.originalFile, this.unmarshaller, this.version);
                } catch (IOException e) {
                    logger.log(Level.SEVERE, "cannot instantiate file index cache so will instantiate memory cache", e);
                    this.indexOfObjects = new InMemoryPsiXmlCache();
                }
            }
            else{
                this.indexOfObjects = new InMemoryPsiXmlCache();
            }
        }
    }

    private void initialiseVersion(PushbackReader reader) throws PsiXmlParserException {

        char[] buffer = new char[PsiXmlUtils.XML_BUFFER_SIZE];

        // read BUFFER_SIZE into the buffer
        int c = 0;
        try {
            c = reader.read( buffer, 0, PsiXmlUtils.XML_BUFFER_SIZE );
            // build a string representation for it
            final String sb = String.valueOf( buffer );

            String levelStr = readElementValue(sb, PsiXmlUtils.LEVEL_ATTRIBUTE);
            String versionStr = readElementValue(sb, PsiXmlUtils.VERSION_ATTRIBUTE);
            String minorVersionStr = readElementValue(sb, PsiXmlUtils.MINOR_VERSION_ATTRIBUTE);

            reader.unread( buffer, 0, c );

            if ("2".equals(levelStr) && "5".equals(versionStr)) {

                if ( "3".equals( minorVersionStr ) ) {
                    this.version = PsiXmlVersion.v2_5_3;
                } else if ( "4".equals( minorVersionStr ) ) {
                    this.version = PsiXmlVersion.v2_5_4;
                } else {
                    throw new PsiXmlParserException("Version not supported: Level="+levelStr+" Version="+versionStr+" MinorVersion="+minorVersionStr);
                }
            }else if ("3".equals(levelStr)){
               this.version = PsiXmlVersion.v3_0_0;
            }
            else {
                throw new PsiXmlParserException("Version not supported: Level="+levelStr+" Version="+versionStr+" MinorVersion="+minorVersionStr);
            }
        } catch (IOException e) {
            throw createPsiXmlExceptionFrom("Impossible to read Xml file.", e);
        }
    }

    private String readElementValue(String sb, String elementName) {
        String value = null;

        if ( sb.indexOf( elementName ) > -1 ) {
            int verindex = sb.lastIndexOf( elementName+"=" ) + ( elementName + "=\"" ).length();
            String textFromElement = sb.substring( verindex );
            value = textFromElement.substring(0, textFromElement.indexOf("\""));
        }
        return value;
    }
}
