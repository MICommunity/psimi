package psidev.psi.mi.jami.xml.io.parser;

import org.codehaus.stax2.XMLInputFactory2;
import org.codehaus.stax2.XMLStreamReader2;
import psidev.psi.mi.jami.datasource.DefaultFileSourceContext;
import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.datasource.FileSourceLocator;
import psidev.psi.mi.jami.exception.MIIOException;
import psidev.psi.mi.jami.model.Annotation;
import psidev.psi.mi.jami.model.Interaction;
import psidev.psi.mi.jami.xml.*;
import psidev.psi.mi.jami.xml.exception.PsiXmlParserException;
import psidev.psi.mi.jami.xml.extension.*;
import psidev.psi.mi.jami.xml.extension.factory.XmlInteractorFactory;
import psidev.psi.mi.jami.xml.listener.PsiXmlParserListener;
import psidev.psi.mi.jami.xml.utils.PsiXml25Utils;

import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.Location;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Parser for PSI-XML 2.5
 *
 * Returns an Iterator of interactions.
 *
 * // Needs to parse each entry. Between each entry, clear the Xml25EntryContext and resolve references
 * // for each entry, read all experiments and interactors and stop when reading interactions
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>14/10/13</pre>
 */

public abstract class AbstractPsiXml25Parser<T extends Interaction> implements PsiXml25Parser<T>{

    private static final Logger logger = Logger.getLogger("AbstractPsiXml25Parser");
    private XMLStreamReader streamReader;

    private URL originalURL;
    private File originalFile;
    private InputStream originalStream;
    private Reader originalReader;
    private Collection<T> loadedInteractions;
    private Unmarshaller unmarshaller;
    private Iterator<T> interactionIterator;
    private XmlInteractorFactory interactorFactory;
    private PsiXmlParserListener listener;
    private boolean hasReadEntrySet = false;
    private boolean hasReadEntry = false;
    private PsiXml25IdCache indexOfObjects=null;
    private boolean useDefaultCache = true;
    private String currentElement;

    private PsiXmlVersion version = null;

    public AbstractPsiXml25Parser(File file) {
        if (file == null){
            throw new IllegalArgumentException("The PsiXmlParser needs a non null File");
        }
        this.originalFile = file;
        loadedInteractions = new ArrayList<T>();
        this.interactorFactory = new XmlInteractorFactory();
    }

    public AbstractPsiXml25Parser(InputStream inputStream) {
        if (inputStream == null){
            throw new IllegalArgumentException("The PsiXmlParser needs a non null InputStream");
        }
        this.originalStream = inputStream;
        loadedInteractions = new ArrayList<T>();
        this.interactorFactory = new XmlInteractorFactory();

    }

    public AbstractPsiXml25Parser(URL url) {
        if (url == null){
            throw new IllegalArgumentException("The PsiXmlParser needs a non null URL");
        }
        this.originalURL = url;
        loadedInteractions = new ArrayList<T>();
        this.interactorFactory = new XmlInteractorFactory();

    }

    public AbstractPsiXml25Parser(Reader reader) {
        if (reader == null){
            throw new IllegalArgumentException("The PsiXmlParser needs a non null Reader");
        }
        this.originalReader = reader;
        loadedInteractions = new ArrayList<T>();
        this.interactorFactory = new XmlInteractorFactory();
    }

    public T parseNextInteraction() throws PsiXmlParserException{
        // Parse into typed objects
        if (this.streamReader == null){
            try {
                initialiseStreamReader();
                Xml25EntryContext.getInstance().initialiseInferredInteractionList();
                Xml25EntryContext.getInstance().initialiseReferencesList();
            } catch (XMLStreamException e) {
                createPsiXmlExceptionFrom("Cannot create a XMLStreamReader to parse the MI source", e);
            } catch (IOException e) {
                createPsiXmlExceptionFrom("Cannot create a XMLStreamReader to parse the MI source", e);
            }
        }

        // we have loaded interactions before because of references. We can use the cache and return next one until the cache is clear
        if (this.interactionIterator != null && this.interactionIterator.hasNext()){
            return parseNextPreLoadedInteraction();
        }

        // get next event from PSI-MI 2.5 schema without parsing it
        if (currentElement == null){
            currentElement = getNextPsiXml25StartElement();
        }

        // the eventreader does not have any new events
        if (currentElement == null){
            if (!hasReadEntrySet && listener != null){
                listener.onInvalidSyntax(new DefaultFileSourceContext(new PsiXmLocator(1,1,null)), new PsiXmlParserException("Xml254ExperimentalEntrySet root term not found. PSI-XML is not valid."));
            }
            return null;
        }

        // get xml entry context
        Xml25EntryContext entryContext = Xml25EntryContext.getInstance();
        // the next tag is an interaction, we parse the interaction.
        if (PsiXml25Utils.INTERACTION_TAG.equals(currentElement) && hasReadEntry){
            T interaction = parseInteractionTag(entryContext);
            // check if last interaction and need to flush entry
            flushEntryIfNecessary(entryContext);
            return interaction;
        }
        // we start a new entry
        else if (PsiXml25Utils.ENTRY_TAG.equals(currentElement) && hasReadEntrySet) {
            T interaction = processEntry(entryContext);
            // check if last interaction and need to flush entry
            flushEntryIfNecessary(entryContext);
            return interaction;
        }
        // entry set
        else if (PsiXml25Utils.ENTRYSET_TAG.equals(currentElement)){

            // read the entrySet
            try {
                // check the version
                if (this.streamReader.hasNext()){
                    streamReader.next();
                }
                initialiseEntryContext(entryContext);
            } catch (XMLStreamException e) {
                throw createPsiXmlExceptionFrom("Impossible to parse entry set.", e);
            } catch (JAXBException e) {
                throw createPsiXmlExceptionFrom("Impossible to create JAXB unmarshaller.", e);
            }
            // get next elements
            this.currentElement = getNextPsiXml25StartElement();
            if (this.currentElement != null && PsiXml25Utils.ENTRY_TAG.equals(currentElement)){
                hasReadEntry = true;
                // parse first interaction
                T interaction = processEntry(entryContext);
                // check if last interaction and need to flush entry
                flushEntryIfNecessary(entryContext);
                return interaction;
            }
            // we expected an entry but we don't have one
            else{
                processUnexpectedNode();
            }
        }
        // node not recognized.
        else{
            processUnexpectedNode();
        }
        return null;
    }

    public void close() throws MIIOException{
        if (this.streamReader != null){
            try {
                this.streamReader.close();
            } catch (XMLStreamException e) {
                closeOriginalInputSources();
                throw new MIIOException("Cannot close event reader",e);
            }
        }
        closeOriginalInputSources();
    }

    public boolean hasFinished() throws PsiXmlParserException{
        if (this.streamReader == null){
            return false;
        }
        if (this.interactionIterator != null && this.interactionIterator.hasNext()){
            return false;
        }
        try {
            return !this.streamReader.hasNext();
        } catch (XMLStreamException e) {
            logger.log(Level.SEVERE, "Impossible to parse next XML tag", e);
            throw createPsiXmlExceptionFrom("Impossible to parse next XML tag", e);
        }
    }

    public void reInit() throws MIIOException{
        loadedInteractions.clear();
        this.version = null;
        if (this.indexOfObjects != null){
            this.indexOfObjects.clear();
        }
        this.interactionIterator = null;
        this.hasReadEntry = false;
        this.hasReadEntrySet = false;
        // release the thread local
        Xml25EntryContext.getInstance().clear();
        Xml25EntryContext.remove();
        this.streamReader = null;
        if (this.originalFile != null){
            XMLInputFactory xmlif = XMLInputFactory.newInstance();
            try {
                StreamSource source = new StreamSource(this.originalFile);
                this.streamReader = (XMLStreamReader2)xmlif.createXMLStreamReader(source);
            } catch (XMLStreamException e) {
                throw new MIIOException("We cannot open the file " + this.originalFile.getName(), e);
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
            // reinitialise the stream
            try {
                this.originalStream = originalURL.openStream();
                XMLInputFactory xmlif = XMLInputFactory.newInstance();
                StreamSource source = new StreamSource(this.originalStream);
                this.streamReader = (XMLStreamReader2)xmlif.createXMLStreamReader(source);
            }catch (XMLStreamException e) {
                throw new MIIOException("We cannot open the URL " + this.originalURL.toString(), e);
            } catch (IOException e) {
                throw new MIIOException("We cannot open the URL  " + this.originalURL.toString(), e);
            }
        }
        else if (this.originalReader != null){
            // reinit line parser if reader can be reset
            if (this.originalReader.markSupported()){
                try {
                    this.originalReader.reset();
                    StreamSource source = new StreamSource(this.originalReader);
                    XMLInputFactory xmlif = XMLInputFactory.newInstance();
                    this.streamReader = (XMLStreamReader2)xmlif.createXMLStreamReader(source);
                } catch (XMLStreamException e) {
                    throw new MIIOException("We cannot open the reader ", e);
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
                    XMLInputFactory xmlif = XMLInputFactory.newInstance();
                    StreamSource source = new StreamSource(this.originalStream);
                    this.streamReader = (XMLStreamReader2)xmlif.createXMLStreamReader(source);

                } catch (XMLStreamException e) {
                    throw new MIIOException("We cannot open the inputStream ", e);
                } catch (IOException e) {
                    throw new MIIOException("We cannot read the inputStream  ", e);
                }
            }
            else {
                throw new MIIOException("The inputStream has been consumed and cannot be reset");
            }
        }
    }

    public PsiXmlParserListener getListener() {
        return listener;
    }

    public void setListener(PsiXmlParserListener listener) {
        this.listener = listener;
    }

    public void setCacheOfObjects(PsiXml25IdCache indexOfObjects) {
        this.indexOfObjects = indexOfObjects;
        this.useDefaultCache = false;
    }

    protected String getNextPsiXml25StartElement() throws PsiXmlParserException{
        // Parse into typed objects
        try{
            // the eventreader does not have any new events
            if (!this.streamReader.hasNext()){
                return null;
            }
            String start = null;
            String namespaceURI = null;
            String currentNamespace = null;

            // if we don't have a version yet, find it
            if (this.version == null){
                do {
                    start = null;
                    namespaceURI = null;
                    // Skip all elements that are not from PSI-XML 2.5 schema and that are not start elements
                    while (this.streamReader.hasNext() && !this.streamReader.isStartElement()){
                        this.streamReader.next();
                    }

                    // get next event without parsing it
                    if (this.streamReader.isStartElement()){
                        start = this.streamReader.getLocalName();
                        namespaceURI = this.streamReader.getNamespaceURI();
                    }
                }
                while(start != null &&
                        (namespaceURI == null
                                || (!PsiXml25Utils.Xml254_NAMESPACE_URI.equals(namespaceURI.trim())
                        && !PsiXml25Utils.Xml253_NAMESPACE_URI.equals(namespaceURI.trim()))));

                if (namespaceURI != null && PsiXml25Utils.Xml254_NAMESPACE_URI.equals(namespaceURI.trim())){
                    this.version = PsiXmlVersion.v2_5_4;
                    return start;
                }
                else if (namespaceURI != null && PsiXml25Utils.Xml253_NAMESPACE_URI.equals(namespaceURI.trim())){
                    this.version = PsiXmlVersion.v2_5_3;
                    return start;
                }
                else{
                    return null;
                }
            }
            else if (PsiXmlVersion.v2_5_4.equals(this.version)){
                currentNamespace = PsiXml25Utils.Xml254_NAMESPACE_URI;
            }
            else if (PsiXmlVersion.v2_5_3.equals(this.version)){
                currentNamespace = PsiXml25Utils.Xml253_NAMESPACE_URI;
            }
            else{
                currentNamespace = PsiXml25Utils.Xml254_NAMESPACE_URI;
            }

            do {
                start = null;
                namespaceURI = null;
                // Skip all elements that are not from PSI-XML 2.5 schema and that are not start elements
                while (this.streamReader.hasNext() && !this.streamReader.isStartElement()){
                    this.streamReader.next();
                }

                // get next event without parsing it
                if (this.streamReader.isStartElement()){
                    start = this.streamReader.getLocalName();
                    namespaceURI = this.streamReader.getNamespaceURI();
                }
            }
            while(start != null &&
                    (namespaceURI == null || !currentNamespace.equals(namespaceURI.trim())));
            return start;
        }
        catch (XMLStreamException e){
            throw createPsiXmlExceptionFrom("Impossible to find next PSI-XML25 tag.", e);
        }
    }

    protected PsiXmlParserException createPsiXmlExceptionFrom(String message, Exception e) {
        Location loc = this.streamReader.getLocation();
        FileSourceLocator locator = null;
        if (loc != null){
            locator = new FileSourceLocator(loc.getLineNumber(), loc.getColumnNumber());
        }
        return new PsiXmlParserException(locator, message, e);
    }


    /**
     *
     * @return the unmarshaller with the class context
     */
    protected abstract Unmarshaller createXml254JAXBUnmarshaller() throws JAXBException;

    protected abstract Unmarshaller createXml253JAXBUnmarshaller() throws JAXBException;

    /**
     * Process an entry that is opened (source, experimentList, etc) and read the first interaction
     * @param entryContext
     * @return
     * @throws XMLStreamException
     * @throws JAXBException
     */
    protected T processEntryAndLoadNextInteraction(Xml25EntryContext entryContext, Location startEntry) throws PsiXmlParserException {
        T loadedInteraction = null;

        this.currentElement = getNextPsiXml25StartElement();

        // process syntax error
        if (this.currentElement == null){
            if (listener != null){
                FileSourceContext context = null;
                if (startEntry != null){
                    context = new DefaultFileSourceContext(new PsiXmLocator(startEntry.getLineNumber(), startEntry.getColumnNumber(), null));
                }
                listener.onInvalidSyntax(context, new PsiXmlParserException("ExperimentalEntry elements is empty. It should contains at least an interactionList. It can also contains a source, availabilityList, experimentList, interactorList and attributeList. PSI-XML is not valid."));
            }
        }
        else{
            // get next event without parsing it until we could read an interaction and solve all its references
            while(loadedInteraction == null && this.currentElement != null){
                // process source of entry
                if (PsiXml25Utils.SOURCE_TAG.equals(this.currentElement)){
                    parseSource(entryContext);
                }
                // process availability
                else if (PsiXml25Utils.AVAILABILITYLIST_TAG.equals(this.currentElement)){
                    parseAvailabilityList(entryContext);
                }
                // process experiments
                else if (PsiXml25Utils.EXPERIMENTLIST_TAG.equals(this.currentElement)){
                    parseExperimentList();

                }
                // process interactors. All interactors will be stored in entryContext so no need to do something else
                else if (PsiXml25Utils.INTERACTORLIST_TAG.equals(this.currentElement)){
                    parseInteractorList();

                }
                // process interaction
                else if (PsiXml25Utils.INTERACTIONLIST_TAG.equals(this.currentElement)){
                    loadedInteraction = parseInteractionList(entryContext, loadedInteraction);

                }
                // read attributeList
                else if (PsiXml25Utils.ATTRIBUTELIST_TAG.equals(this.currentElement)){
                    parseAttributeList(entryContext);

                }
                else{
                    if (listener != null){
                        FileSourceContext context = null;
                        if (this.streamReader.getLocation() != null){
                            Location loc = this.streamReader.getLocation();
                            context = new DefaultFileSourceContext(new PsiXmLocator(loc.getLineNumber(), loc.getColumnNumber(), null));
                        }
                        listener.onInvalidSyntax(context, new PsiXmlParserException("ExperimentalEntry contains a node "+this.currentElement+". In an entry, only a source, experimentList, interactorList, interactionList, attributeList and availabilityList are allowed. PSI-XML is not valid."));
                    }
                }
            }
        }

        return loadedInteraction;
    }

    protected void parseAttributeList(Xml25EntryContext entryContext) throws PsiXmlParserException {
        // read attributeList
        try{
            Location attributeList = this.streamReader.getLocation();
            if (this.streamReader.hasNext()){
                streamReader.next();
            }

            this.currentElement = getNextPsiXml25StartElement();
            // read attributes
            if (this.currentElement != null){
                Entry currentEntry = entryContext.getCurrentEntry();
                // load attribute
                while (this.currentElement != null && PsiXml25Utils.ATTRIBUTE_TAG.equals(this.currentElement)) {
                    Annotation attribute = (Annotation)this.unmarshaller.unmarshal(streamReader);
                    currentEntry.getAnnotations().add(attribute);
                    this.currentElement = getNextPsiXml25StartElement();
                }                    }
            else{
                if (listener != null){
                    FileSourceContext context = null;
                    if (attributeList != null){
                        context = new DefaultFileSourceContext(new PsiXmLocator(attributeList.getLineNumber(), attributeList.getColumnNumber(), null));
                    }
                    listener.onInvalidSyntax(context, new PsiXmlParserException("JAXBFeatureList elements does not contains any attribute node. PSI-XML is not valid."));
                }
            }
        }
        catch (XMLStreamException e){
            throw createPsiXmlExceptionFrom("Impossible to parse attribute list of the entry", e);
        } catch (JAXBException e) {
            throw createPsiXmlExceptionFrom("Impossible to parse attribute list of the entry", e);
        }
    }

    protected T parseInteractionList(Xml25EntryContext entryContext, T loadedInteraction) throws PsiXmlParserException {
        // read interaction list
        try{
            Location interactionList = this.streamReader.getLocation();
            if (this.streamReader.hasNext()){
                streamReader.next();
            }

            this.currentElement = getNextPsiXml25StartElement();
            // read interactions
            if (this.currentElement != null){
                loadedInteraction = parseInteractionTag(entryContext);
            }
            else{
                if (listener != null){
                    FileSourceContext context = null;
                    if (interactionList != null){
                        context = new DefaultFileSourceContext(new PsiXmLocator(interactionList.getLineNumber(), interactionList.getColumnNumber(), null));
                    }
                    listener.onInvalidSyntax(context, new PsiXmlParserException("InteractionList elements does not contains any interaction node. PSI-XML is not valid."));
                }
            }
            return loadedInteraction;
        }
        catch (XMLStreamException e){
            throw createPsiXmlExceptionFrom("Impossible to parse interaction list",e);
        }
    }

    protected void parseInteractorList() throws PsiXmlParserException {
        // read experiment list
        try{
            Location interactorList = this.streamReader.getLocation();
            if (this.streamReader.hasNext()){
                streamReader.next();
            }

            this.currentElement = getNextPsiXml25StartElement();
            // process interactors
            if (this.currentElement != null){
                // load experimentDescription
                while (this.currentElement != null && PsiXml25Utils.INTERACTOR_TAG.equals(this.currentElement)) {
                    AbstractXmlInteractor interactorElement = (AbstractXmlInteractor)unmarshaller.unmarshal(this.streamReader);
                    this.interactorFactory.
                            createInteractorFromXmlInteractorInstance(interactorElement);
                    this.currentElement = getNextPsiXml25StartElement();
                }
            }
            else{
                if (listener != null){
                    FileSourceContext context = null;
                    if (interactorList != null){
                        context = new DefaultFileSourceContext(new PsiXmLocator(interactorList.getLineNumber(), interactorList.getColumnNumber(), null));
                    }
                    listener.onInvalidSyntax(context, new PsiXmlParserException("InteractorList elements does not contains any interactor node. PSI-XML is not valid."));
                }
            }
        }
        catch (XMLStreamException e){
            throw new PsiXmlParserException("Impossible to parse interactor list",e);
        } catch (JAXBException e) {
            throw new PsiXmlParserException("Impossible to parse interactor list",e);
        }
    }

    protected void parseExperimentList() throws PsiXmlParserException {
        // read experiment list
        try{
            Location experimentList = this.streamReader.getLocation();
            if (this.streamReader.hasNext()){
                streamReader.next();
            }

            this.currentElement = getNextPsiXml25StartElement();
            // process experiments. Each experiment will be loaded in entryContext so no needs to do something else
            if (this.currentElement != null){
                // load experimentDescription
                while (this.currentElement != null && PsiXml25Utils.EXPERIMENT_TAG.equals(this.currentElement)) {
                    unmarshaller.unmarshal(this.streamReader, XmlExperiment.class);
                    this.currentElement = getNextPsiXml25StartElement();
                }
            }
            else{
                if (listener != null){
                    FileSourceContext context = null;
                    if (experimentList != null){
                        context = new DefaultFileSourceContext(new PsiXmLocator(experimentList.getLineNumber(), experimentList.getColumnNumber(), null));
                    }
                    listener.onInvalidSyntax(context, new PsiXmlParserException("ExperimentList elements does not contains any experimentDescription node. PSI-XML is not valid."));
                }
            }
        }
        catch (XMLStreamException e){
             throw createPsiXmlExceptionFrom("Impossible to parse the experiment list",e);
        } catch (JAXBException e) {
            throw createPsiXmlExceptionFrom("Impossible to parse the experiment list",e);
        }
    }

    protected void parseAvailabilityList(Xml25EntryContext entryContext) throws PsiXmlParserException {
        processAvailabilityList(entryContext);
        this.currentElement = getNextPsiXml25StartElement();
    }

    protected void parseSource(Xml25EntryContext entryContext) throws PsiXmlParserException {

        ExtendedPsi25Source sourceElement = null;
        try {
            sourceElement = (ExtendedPsi25Source)this.unmarshaller.unmarshal(this.streamReader);
            entryContext.getCurrentEntry().setSource(sourceElement);
            this.currentElement = getNextPsiXml25StartElement();
        } catch (JAXBException e) {
            throw createPsiXmlExceptionFrom("Cannot parse the source of the entry.",e);
        }
    }

    /**
     * Creates a new Entry, parses the entry and return the next available interaction
     * @param entryContext
     * @return
     * @throws JAXBException
     * @throws XMLStreamException
     */
    protected T processEntry(Xml25EntryContext entryContext) throws PsiXmlParserException {
        try{
            // reset new entry
            entryContext.setCurrentSource(new Entry());
            // read entry
            Location startEntry = this.streamReader.getLocation();
            if (this.streamReader.hasNext()){
                streamReader.next();
            }

            // get next interaction if possible
            return processEntryAndLoadNextInteraction(entryContext, startEntry);
        }
        catch (XMLStreamException e){
             throw createPsiXmlExceptionFrom("Cannot parse entry",e);
        }
    }

    /**
     * Some interactions contains references and we want to load the remaining interactions until the end of the entry
     * @param entryContext
     * @throws XMLStreamException
     * @throws JAXBException
     */
    protected void loadEntry(Xml25EntryContext entryContext, T currentInteraction) throws PsiXmlParserException {
        // load the all entry
        // we already are parsing interactions
        try{
            this.currentElement = getNextPsiXml25StartElement();
            boolean isReadingInteraction = this.currentElement != null && PsiXml25Utils.INTERACTION_TAG.equals(this.currentElement);
            while(isReadingInteraction && this.currentElement != null){
                this.loadedInteractions.add(unmarshallInteraction());

                this.currentElement = getNextPsiXml25StartElement();
                isReadingInteraction = this.currentElement != null && PsiXml25Utils.INTERACTION_TAG.equals(this.currentElement);
            }

            // get the current entry. It must exists
            Entry currentEntry = entryContext.getCurrentEntry();
            if (currentEntry == null){
                if (listener != null){
                    FileSourceContext context = null;
                    if (currentInteraction instanceof FileSourceContext){
                        context = (FileSourceContext)currentEntry;
                    }
                    listener.onInvalidSyntax(context, new PsiXmlParserException("An interaction node was found outside an entry elements. PSI-XML is not valid."));
                }
            }
            else{
                // check entry attributes
                if (this.currentElement != null && PsiXml25Utils.ATTRIBUTELIST_TAG.equals(this.currentElement)){
                    parseAttributeList(entryContext);
                }

                this.interactionIterator = this.loadedInteractions.iterator();
            }
        }
        catch (JAXBException e){
            throw createPsiXmlExceptionFrom("Impossible to parse the entry.", e);
        }
    }

    /**
     *
     * @return the next interaction preloaded in the interactionIterator. Deletes the returned interaction
     */
    protected T parseNextPreLoadedInteraction() {
        T interaction = this.interactionIterator.next();
        this.interactionIterator.remove();
        return interaction;
    }

    /**
     * The unmarshaller must be able to return the expected interaction type
     * @param entryContext
     * @return next interaction parsed in the interaction list. Will load the all entry if we have references to solve
     * @throws JAXBException
     * @throws XMLStreamException
     */
    protected T parseInteractionTag(Xml25EntryContext entryContext) throws PsiXmlParserException{
        T interaction = null;
        try {
            interaction = unmarshallInteraction();

            // no references, can return the interaction
            if (!entryContext.hasInferredInteractions() && !entryContext.hasUnresolvedReferences()){
                return interaction;
            }
            // we have references to resolve, loads the all entry and keep in cache
            else{
                loadEntry(entryContext,interaction);
            }

            return interaction;
        } catch (JAXBException e) {
            throw  createPsiXmlExceptionFrom("Impossible to parse the interaction", e);
        }
    }

    protected T unmarshallInteraction() throws JAXBException{
        return (T) this.unmarshaller.unmarshal(this.streamReader);
    }

    protected void processAvailabilityList(Xml25EntryContext entryContext) throws PsiXmlParserException {
        // read availability list
        try{
            Location startList = this.streamReader.getLocation();
            if (this.streamReader.hasNext()){
                streamReader.next();
            }

            this.currentElement = getNextPsiXml25StartElement();
            // load availability
            if (this.currentElement != null){
                Entry entry = entryContext.getCurrentEntry();

                // load availability
                while (this.currentElement != null && PsiXml25Utils.AVAILABILITY_TAG.equals(this.currentElement)) {
                    AbstractAvailability availabilityElement = (AbstractAvailability) unmarshaller.unmarshal(this.streamReader);
                    entry.getAvailabilities().add(availabilityElement);
                    this.currentElement = getNextPsiXml25StartElement();
                }
            }
            else{
                if (listener != null){
                    FileSourceContext context = null;
                    if (startList != null){
                        context = new DefaultFileSourceContext(new PsiXmLocator(startList.getLineNumber(), startList.getColumnNumber(), null));
                    }
                    listener.onInvalidSyntax(context, new PsiXmlParserException("AvailabilityList elements does not contains any availability node. PSI-XML is not valid."));
                }
            }
        }
        catch (XMLStreamException e){
            throw createPsiXmlExceptionFrom("Cannot parse availability list", e);
        } catch (JAXBException e) {
            throw createPsiXmlExceptionFrom("Cannot parse availability list", e);
        }
    }

    protected XMLStreamReader getStreamReader() {
        return streamReader;
    }

    protected String getCurrentElement() {
        return currentElement;
    }

    protected void setCurrentElement(String currentElement) {
        this.currentElement = currentElement;
    }

    protected void skipNextElement() throws PsiXmlParserException {
        try{
            String currentNamespace = null;
            switch (this.version){
                case v2_5_4:
                    currentNamespace = PsiXml25Utils.Xml254_NAMESPACE_URI;
                    break;
                case v2_5_3:
                    currentNamespace = PsiXml25Utils.Xml253_NAMESPACE_URI;
                    break;
                default:
                    currentNamespace = PsiXml25Utils.Xml254_NAMESPACE_URI;
                    break;
            }

            boolean isFromPsiNamespace = false;
            do{
                if (this.streamReader.hasNext()){
                    streamReader.next();
                }
                else{
                    isFromPsiNamespace = true;
                }
                // only look if we have a start or end element
                if (this.streamReader.isEndElement() || this.streamReader.isStartElement()){
                   isFromPsiNamespace = this.streamReader.getNamespaceURI() != null && currentNamespace.equals(this.streamReader.getNamespaceURI().trim());
                }
            }
            while (!isFromPsiNamespace);
        }
        catch (XMLStreamException e){
            throw createPsiXmlExceptionFrom("Cannot parse next start/end elements", e);
        }
    }

    protected void processUnexpectedNode() throws PsiXmlParserException {
        // skip nodes from other schema
        FileSourceContext context = null;
        if (this.streamReader.getLocation() != null){
            Location loc = this.streamReader.getLocation();
            context = new DefaultFileSourceContext(new PsiXmLocator(loc.getLineNumber(), loc.getColumnNumber(), null));
        }
        if(listener != null){
            listener.onInvalidSyntax(context, new PsiXmlParserException("We found a tag " + currentElement + ". We only expected " +
                    "interaction, entry or entrySet tag"));
        }
        // skip the node
        skipNextElement();
    }

    protected PsiXmlVersion getVersion() {
        return version;
    }

    private void initialiseStreamReader() throws XMLStreamException, IOException {
        XMLInputFactory xmlif = XMLInputFactory2.newInstance();
        if (this.originalFile != null){
            StreamSource source = new StreamSource(this.originalFile);
            this.streamReader = xmlif.createXMLStreamReader(source);
        }
        else if (this.originalURL != null){
            this.originalStream = this.originalURL.openStream();
            StreamSource source = new StreamSource(this.originalStream);
            this.streamReader = xmlif.createXMLStreamReader(source);
        }
        else if (this.originalReader != null){
            StreamSource source = new StreamSource(this.originalReader);
            this.streamReader = xmlif.createXMLStreamReader(source);
        }
        else if (this.originalStream != null){
            StreamSource source = new StreamSource(this.originalStream);
            this.streamReader = xmlif.createXMLStreamReader(source);
        }
    }

    private void flushEntryIfNecessary(Xml25EntryContext entryContext) throws PsiXmlParserException {
        this.currentElement = getNextPsiXml25StartElement();
        // end of file, flush last entry
        if (this.currentElement == null){
            flushEntry(entryContext);
        }
        // end of entry, parse attributes and flush the entry
        else if (PsiXml25Utils.ATTRIBUTELIST_TAG.equals(this.currentElement)){
            parseAttributeList(entryContext);
            flushEntry(entryContext);
        }
        // if this interaction is not followed by another interaction, we need to flush the entry
        else if (!PsiXml25Utils.INTERACTION_TAG.equals(this.currentElement)){
            flushEntry(entryContext);
        }
    }

    private void flushEntry(Xml25EntryContext context){
        if (context.getCurrentEntry() != null){
            context.getCurrentEntry().setHasLoadedFullEntry(true);
        }
        clearEntryReferences(context);
        this.hasReadEntry = false;
    }

    private void clearEntryReferences(Xml25EntryContext context){
        context.resolveInteractorAndExperimentRefs();
        context.resolveInferredInteractionRefs();
        context.clear();
    }

    private void closeOriginalInputSources() {
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
            }
        }
        else{
            this.originalFile = null;
            this.originalURL = null;
            this.originalStream = null;
            this.originalReader = null;
        }

        loadedInteractions.clear();
        this.interactionIterator = null;
        this.hasReadEntry = false;
        this.hasReadEntrySet = false;
        this.unmarshaller = null;
        this.indexOfObjects = null;
        this.useDefaultCache = true;
        this.version = null;

        // release the thread local
        Xml25EntryContext.getInstance().clear();
        Xml25EntryContext.remove();
    }

    private void initialiseEntryContext(Xml25EntryContext entryContext) throws JAXBException {
        // create unmarshaller knowing the version
        switch (this.version){
            case v2_5_4:
                this.unmarshaller = createXml254JAXBUnmarshaller();
                break;
            case v2_5_3:
                this.unmarshaller = createXml253JAXBUnmarshaller();
                break;
            default:
                this.unmarshaller = createXml254JAXBUnmarshaller();
                break;
        }

        entryContext.clear();
        this.hasReadEntrySet = true;
        entryContext.setListener(this.listener);
        if (useDefaultCache){
            initialiseDefaultCache();
        }
        entryContext.setElementCache(this.indexOfObjects);
    }

    private void initialiseDefaultCache() {
        if (this.indexOfObjects == null){
            this.indexOfObjects = new InMemoryPsiXml25Cache();
        }
    }
}
