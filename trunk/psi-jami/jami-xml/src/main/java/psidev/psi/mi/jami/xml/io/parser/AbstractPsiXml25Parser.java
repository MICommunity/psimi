package psidev.psi.mi.jami.xml.io.parser;

import psidev.psi.mi.jami.datasource.DefaultFileSourceContext;
import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.exception.MIIOException;
import psidev.psi.mi.jami.model.Annotation;
import psidev.psi.mi.jami.model.Interaction;
import psidev.psi.mi.jami.xml.XmlEntry;
import psidev.psi.mi.jami.xml.XmlEntryContext;
import psidev.psi.mi.jami.xml.XmlIdReference;
import psidev.psi.mi.jami.xml.exception.PsiXmlParserException;
import psidev.psi.mi.jami.xml.extension.*;
import psidev.psi.mi.jami.xml.extension.factory.XmlInteractorFactory;
import psidev.psi.mi.jami.xml.listener.PsiXmlParserListener;
import psidev.psi.mi.jami.xml.utils.PsiXmlUtils;

import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.*;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Parser for PSI-XML 2.5
 *
 * Returns an Iterator of interactions.
 *
 * // Needs to parse each entry. Between each entry, clear the XmlEntryContext and resolve references
 * // for each entry, read all experiments and interactors and stop when reading interactions
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>14/10/13</pre>
 */

public abstract class AbstractPsiXml25Parser<T extends Interaction> implements PsiXml25Parser<T>{

    private static final Logger logger = Logger.getLogger("AbstractPsiXml25Parser");
    private XMLEventReader eventReader;
    private XMLEventReader subEventReader;

    private URL originalURL;
    private File originalFile;
    private InputStream originalStream;
    private Reader originalReader;
    private Collection<T> loadedInteractions;
    private Unmarshaller unmarshaller;
    private Iterator<T> interactionIterator;
    private XmlInteractorFactory interactorFactory;
    private PsiXmlParserListener listener;
    private boolean started = false;

    public AbstractPsiXml25Parser(File file) throws FileNotFoundException, XMLStreamException, JAXBException {
        if (file == null){
            throw new IllegalArgumentException("The PsiXmlParser needs a non null File");
        }
        this.originalFile = file;
        // Parse the data, filtering out the start elements
        XMLInputFactory xmlif = XMLInputFactory.newInstance();
        FileReader fr = new FileReader(file);
        XMLEventReader xmler = xmlif.createXMLEventReader(fr);
        initializeXmlEventReader(xmlif, xmler);
        loadedInteractions = new ArrayList<T>();
        this.unmarshaller = createJAXBUnmarshaller();
        this.interactorFactory = new XmlInteractorFactory();
    }

    public AbstractPsiXml25Parser(InputStream inputStream) throws XMLStreamException, JAXBException {
        if (inputStream == null){
            throw new IllegalArgumentException("The PsiXmlParser needs a non null InputStream");
        }
        this.originalStream = inputStream;
        // Parse the data, filtering out the start elements
        XMLInputFactory xmlif = XMLInputFactory.newInstance();
        InputStreamReader fr = new InputStreamReader(inputStream);
        XMLEventReader xmler = xmlif.createXMLEventReader(fr);
        initializeXmlEventReader(xmlif, xmler);
        loadedInteractions = new ArrayList<T>();
        this.unmarshaller = createJAXBUnmarshaller();
        this.interactorFactory = new XmlInteractorFactory();

    }

    public AbstractPsiXml25Parser(URL url) throws IOException, XMLStreamException, JAXBException {
        if (url == null){
            throw new IllegalArgumentException("The PsiXmlParser needs a non null URL");
        }
        this.originalURL = url;
        this.originalStream = url.openStream();
        // Parse the data, filtering out the start elements
        XMLInputFactory xmlif = XMLInputFactory.newInstance();
        InputStreamReader fr = new InputStreamReader(this.originalStream);
        XMLEventReader xmler = xmlif.createXMLEventReader(fr);
        initializeXmlEventReader(xmlif, xmler);
        loadedInteractions = new ArrayList<T>();
        this.unmarshaller = createJAXBUnmarshaller();
        this.interactorFactory = new XmlInteractorFactory();

    }

    public AbstractPsiXml25Parser(Reader reader) throws XMLStreamException, JAXBException {
        if (reader == null){
            throw new IllegalArgumentException("The PsiXmlParser needs a non null Reader");
        }
        this.originalReader = reader;
        // Parse the data, filtering out the start elements
        XMLInputFactory xmlif = XMLInputFactory.newInstance();
        XMLEventReader xmler = xmlif.createXMLEventReader(reader);
        initializeXmlEventReader(xmlif, xmler);
        loadedInteractions = new ArrayList<T>();
        this.unmarshaller = createJAXBUnmarshaller();
        this.interactorFactory = new XmlInteractorFactory();
    }

    public T parseNextInteraction() throws IOException, XMLStreamException, JAXBException {
        // Parse into typed objects

        // we have loaded interactions before because of references. We can use the cache and return next one until the cache is clear
        if (this.interactionIterator != null && this.interactionIterator.hasNext()){
            return parseNextPreLoadedInteraction();
        }

        // the eventreader does not have any new events
        if (!this.eventReader.hasNext()){
            if (!started && listener != null){
                listener.onInvalidSyntax(new DefaultFileSourceContext(new PsiXmLocator(1,1,null)), new PsiXmlParserException("EntrySet root term not found. PSI-XML is not valid."));
            }
            return null;
        }

        // get next event without parsing it
        StartElement start = (StartElement) eventReader.peek();
        // get xml entry context
        XmlEntryContext entryContext = XmlEntryContext.getInstance();
        // the next tag is an interaction, we parse the interaction.
        if (PsiXmlUtils.INTERACTION_TAG.equalsIgnoreCase(start.getName().getLocalPart())){
            return parseInteractionTag(entryContext);
        }
        // we start a new entry
        else if (PsiXmlUtils.ENTRY_TAG.equalsIgnoreCase(start.getName().getLocalPart())) {
            return processEntry(entryContext);
        }
        // entry set
        else if (PsiXmlUtils.ENTRYSET_TAG.equalsIgnoreCase(start.getName().getLocalPart())){
            started = true;
            XmlEntryContext.getInstance().setListener(this.listener);
            // read the entrySet
            eventReader.nextEvent();
            return processEntry(entryContext);
        }
        else{
            FileSourceContext context = null;
            if (start.getLocation() != null){
                Location loc = start.getLocation();
                context = new DefaultFileSourceContext(new PsiXmLocator(loc.getLineNumber(), loc.getColumnNumber(), null));
            }
            if(listener != null){
                listener.onInvalidSyntax(context, new PsiXmlParserException("We found a tag " + start.getName().getLocalPart() + ". We only expected " +
                        "interaction, entry or entrySet tag"));
            }

        }
        return null;
    }

    public void close() throws MIIOException{
        if (this.eventReader != null){
            this.subEventReader = null;
            try {
                this.eventReader.close();
            } catch (XMLStreamException e) {
                closeOriginalInputSources();
                throw new MIIOException("Cannot close event reader",e);
            }
        }
        else if (this.subEventReader != null){
            try {
                this.subEventReader.close();
            } catch (XMLStreamException e) {
                closeOriginalInputSources();
                throw new MIIOException("Cannot close event reader",e);
            }
        }
        closeOriginalInputSources();
    }

    public boolean hasFinished(){
        if (this.interactionIterator != null && this.interactionIterator.hasNext()){
            return false;
        }
        return !this.eventReader.hasNext();
    }

    public void reInit() throws MIIOException{
        loadedInteractions.clear();
        this.interactionIterator = null;
        this.started = false;
        // release the thread local
        XmlEntryContext.getInstance().clear();
        XmlEntryContext.remove();
        if (this.eventReader != null){
            try {
                this.eventReader.close();
            } catch (XMLStreamException e) {
                logger.log(Level.SEVERE, "Could not close the eventReader.", e);
            }
        }
        if (this.originalFile != null){
            XMLInputFactory xmlif = XMLInputFactory.newInstance();
            FileReader fr = null;
            try {
                fr = new FileReader(this.originalFile);
                XMLEventReader xmler = xmlif.createXMLEventReader(fr);
                initializeXmlEventReader(xmlif, xmler);
            } catch (FileNotFoundException e) {
                throw new MIIOException("File not found  " + this.originalFile.getName(), e);
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
                InputStreamReader fr = new InputStreamReader(this.originalStream);
                XMLEventReader xmler = xmlif.createXMLEventReader(fr);
                initializeXmlEventReader(xmlif, xmler);
            }catch (XMLStreamException e) {
                throw new MIIOException("We cannot open the URL " + this.originalURL.toString(), e);
            } catch (IOException e) {
                throw new MIIOException("We cannot open the URL  " + this.originalURL.toString(), e);
            }
        }
        else if (this.originalStream != null){
            // reinit parser if inputStream can be reset
            if (this.originalStream.markSupported()){
                try {
                    this.originalStream.reset();
                    XMLInputFactory xmlif = XMLInputFactory.newInstance();
                    InputStreamReader fr = new InputStreamReader(this.originalStream);
                    XMLEventReader xmler = xmlif.createXMLEventReader(fr);
                    initializeXmlEventReader(xmlif, xmler);

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
        else if (this.originalReader != null){
            // reinit line parser if reader can be reset
            if (this.originalReader.markSupported()){
                try {
                    this.originalReader.reset();
                    XMLInputFactory xmlif = XMLInputFactory.newInstance();
                    XMLEventReader xmler = xmlif.createXMLEventReader(this.originalReader);
                    initializeXmlEventReader(xmlif, xmler);
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
    }

    public PsiXmlParserListener getListener() {
        return listener;
    }

    public void setListener(PsiXmlParserListener listener) {
        this.listener = listener;
    }

    /**
     *
     * @return the unmarshaller with the class context
     */
    protected abstract Unmarshaller createJAXBUnmarshaller() throws JAXBException;

    /**
     * Process an entry that is opened (source, experimentList, etc) and read the first interaction
     * @param entryContext
     * @return
     * @throws XMLStreamException
     * @throws JAXBException
     */
    protected T processEntryAndLoadNextInteraction(XmlEntryContext entryContext, StartElement startEntry) throws XMLStreamException, JAXBException {
        T loadedInteraction = null;

        // process syntax error
        if (!this.eventReader.hasNext()){
            if (listener != null){
                FileSourceContext context = null;
                if (startEntry.getLocation() != null){
                   Location loc = startEntry.getLocation();
                    context = new DefaultFileSourceContext(new PsiXmLocator(loc.getLineNumber(), loc.getColumnNumber(), null));
                }
                listener.onInvalidSyntax(context, new PsiXmlParserException("Entry element is empty. It should contain at least an interactionList. It can also contain a source, availabilityList, experimentList, interactorList and attributeList. PSI-XML is not valid."));
            }
        }
        else{
            // get next event without parsing it until we could read an interaction and solve all its references
            while(loadedInteraction == null && this.eventReader.hasNext()){
                StartElement nextEvt = (StartElement) this.eventReader.peek();
                // process source of entry
                if (PsiXmlUtils.SOURCE_TAG.equalsIgnoreCase(nextEvt.getName().getLocalPart())){
                    entryContext.getCurrentEntry().setSource((XmlSource) this.unmarshaller.unmarshal(subEventReader));
                }
                // process availability
                else if (PsiXmlUtils.AVAILABILITYLIST_TAG.equalsIgnoreCase(nextEvt.getName().getLocalPart())){
                    // read availability list
                    StartElement startList = (StartElement)this.eventReader.nextEvent();
                    // load availability
                    if (this.eventReader.hasNext()){
                        XmlEntry entry = entryContext.getCurrentEntry();

                        nextEvt = (StartElement)this.eventReader.peek();
                        // load availability
                        while (nextEvt != null && PsiXmlUtils.AVAILABILITY_TAG.equalsIgnoreCase(nextEvt.getName().getLocalPart())) {
                            entry.getAvailabilities().add((Availability)unmarshaller.unmarshal(this.subEventReader));
                            if (this.eventReader.hasNext()){
                                nextEvt = (StartElement)this.eventReader.peek();
                            }
                            else{
                                nextEvt= null;
                            }
                        }
                    }
                    else{
                        if (listener != null){
                            FileSourceContext context = null;
                            if (startList.getLocation() != null){
                                Location loc = startList.getLocation();
                                context = new DefaultFileSourceContext(new PsiXmLocator(loc.getLineNumber(), loc.getColumnNumber(), null));
                            }
                            listener.onInvalidSyntax(context, new PsiXmlParserException("AvailabilityList element does not contain any availability node. PSI-XML is not valid."));
                        }
                    }
                }
                // process experiments
                else if (PsiXmlUtils.EXPERIMENTLIST_TAG.equalsIgnoreCase(nextEvt.getName().getLocalPart())){
                    // read experiment list
                    StartElement experimentList = (StartElement)this.eventReader.nextEvent();
                    // process experiments. Each experiment will be loaded in entryContext so no needs to do something else
                    if (this.eventReader.hasNext()){
                        nextEvt = (StartElement)this.eventReader.peek();
                        // load experimentDescription
                        while (nextEvt != null && PsiXmlUtils.EXPERIMENT_TAG.equalsIgnoreCase(nextEvt.getName().getLocalPart())) {
                            unmarshaller.unmarshal(this.subEventReader);
                            if (this.eventReader.hasNext()){
                                nextEvt = (StartElement)this.eventReader.peek();
                            }
                            else{
                                nextEvt= null;
                            }
                        }
                    }
                    else{
                        if (listener != null){
                            FileSourceContext context = null;
                            if (experimentList.getLocation() != null){
                                Location loc = experimentList.getLocation();
                                context = new DefaultFileSourceContext(new PsiXmLocator(loc.getLineNumber(), loc.getColumnNumber(), null));
                            }
                            listener.onInvalidSyntax(context, new PsiXmlParserException("ExperimentList element does not contain any experimentDescription node. PSI-XML is not valid."));
                        }
                    }
                }
                // process interactors. All interactors will be stored in entryContext so no need to do something else
                else if (PsiXmlUtils.INTERACTORLIST_TAG.equalsIgnoreCase(nextEvt.getName().getLocalPart())){
                    // read experiment list
                    StartElement interactorList = (StartElement)this.eventReader.nextEvent();
                    // process interactors
                    if (this.eventReader.hasNext()){
                        nextEvt = (StartElement)this.eventReader.peek();
                        // load experimentDescription
                        while (nextEvt != null && PsiXmlUtils.INTERACTOR_TAG.equalsIgnoreCase(nextEvt.getName().getLocalPart())) {
                            this.interactorFactory.
                                    createInteractorFromXmlInteractorInstance((XmlInteractor)unmarshaller.unmarshal(this.subEventReader));
                            if (this.eventReader.hasNext()){
                                nextEvt = (StartElement)this.eventReader.peek();
                            }
                            else{
                                nextEvt= null;
                            }
                        }
                    }
                    else{
                        if (listener != null){
                            FileSourceContext context = null;
                            if (interactorList.getLocation() != null){
                                Location loc = interactorList.getLocation();
                                context = new DefaultFileSourceContext(new PsiXmLocator(loc.getLineNumber(), loc.getColumnNumber(), null));
                            }
                            listener.onInvalidSyntax(context, new PsiXmlParserException("InteractorList element does not contain any interactor node. PSI-XML is not valid."));
                        }
                    }
                }
                // process interaction
                else if (PsiXmlUtils.INTERACTIONLIST_TAG.equalsIgnoreCase(nextEvt.getName().getLocalPart())){
                    // read experiment list
                    StartElement interactionList = (StartElement)this.eventReader.nextEvent();
                    // read interactions
                    if (this.eventReader.hasNext()){
                        loadedInteraction = parseInteractionTag(entryContext);
                    }
                    else{
                        if (listener != null){
                            FileSourceContext context = null;
                            if (interactionList.getLocation() != null){
                                Location loc = interactionList.getLocation();
                                context = new DefaultFileSourceContext(new PsiXmLocator(loc.getLineNumber(), loc.getColumnNumber(), null));
                            }
                            listener.onInvalidSyntax(context, new PsiXmlParserException("InteractionList element does not contain any interaction node. PSI-XML is not valid."));
                        }
                    }
                }
                // read attributeList
                else if (PsiXmlUtils.ATTRIBUTELIST_TAG.equalsIgnoreCase(nextEvt.getName().getLocalPart())){
                    // read attributeList
                    StartElement attributeList = (StartElement)eventReader.nextEvent();
                    // read attributes
                    if (this.eventReader.hasNext()){
                        XmlEntry currentEntry = entryContext.getCurrentEntry();
                        nextEvt = (StartElement)this.eventReader.peek();
                        // load attribute
                        while (nextEvt != null && PsiXmlUtils.ATTRIBUTE_TAG.equalsIgnoreCase(nextEvt.getName().getLocalPart())) {
                            currentEntry.getAnnotations().add((Annotation) this.unmarshaller.unmarshal(subEventReader));

                            if (this.eventReader.hasNext()){
                                nextEvt = (StartElement)this.eventReader.peek();
                            }
                            else{
                                nextEvt= null;
                            }
                        }                    }
                    else{
                        if (listener != null){
                            FileSourceContext context = null;
                            if (attributeList.getLocation() != null){
                                Location loc = attributeList.getLocation();
                                context = new DefaultFileSourceContext(new PsiXmLocator(loc.getLineNumber(), loc.getColumnNumber(), null));
                            }
                            listener.onInvalidSyntax(context, new PsiXmlParserException("JAXBFeatureList element does not contain any attribute node. PSI-XML is not valid."));
                        }
                    }
                }
                else{
                    if (listener != null){
                        FileSourceContext context = null;
                        if (nextEvt.getLocation() != null){
                            Location loc = nextEvt.getLocation();
                            context = new DefaultFileSourceContext(new PsiXmLocator(loc.getLineNumber(), loc.getColumnNumber(), null));
                        }
                        listener.onInvalidSyntax(context, new PsiXmlParserException("Entry contains a node "+nextEvt.getName().getLocalPart()+". In an entry, only a source, experimentList, interactorList, interactionList, attributeList and availabilityList are allowed. PSI-XML is not valid."));
                    }
                }
            }
        }

        return loadedInteraction;
    }

    /**
     * Creates a new XmlEntry, parses the entry and return the next available interaction
     * @param entryContext
     * @return
     * @throws JAXBException
     * @throws XMLStreamException
     */
    protected T processEntry(XmlEntryContext entryContext) throws JAXBException, XMLStreamException {
        // reset new entry
        entryContext.setCurrentSource(new XmlEntry());
        // read entry
        StartElement startEntry = (StartElement)this.eventReader.next();
        // get next interaction if possible
        return processEntryAndLoadNextInteraction(entryContext, startEntry);
    }

    /**
     * Some interactions contains references and we want to load the remaining interactions until the end of the entry
     * @param entryContext
     * @throws XMLStreamException
     * @throws JAXBException
     */
    protected void loadEntry(XmlEntryContext entryContext, T currentInteraction) throws XMLStreamException, JAXBException {
        // load the all entry
        // we already are parsing interactions
        StartElement evt = (StartElement) eventReader.peek();
        boolean isReadingInteraction = evt != null && PsiXmlUtils.INTERACTION_TAG.equalsIgnoreCase(evt.getName().getLocalPart());
        while(isReadingInteraction && this.eventReader.hasNext()){
            this.loadedInteractions.add((T)this.unmarshaller.unmarshal(subEventReader));

            evt = (StartElement) eventReader.peek();
            isReadingInteraction = evt != null && PsiXmlUtils.INTERACTION_TAG.equalsIgnoreCase(evt.getName().getLocalPart());
        }

        // get the current entry. It must exists
        XmlEntry currentEntry = entryContext.getCurrentEntry();
        if (currentEntry == null){
            if (listener != null){
                FileSourceContext context = null;
                if (currentInteraction instanceof FileSourceContext){
                    context = (FileSourceContext)currentEntry;
                }
                listener.onInvalidSyntax(context, new PsiXmlParserException("An interaction node was found outside an entry element. PSI-XML is not valid."));
            }
        }
        else{
            // check entry attributes
            if (evt != null && PsiXmlUtils.ATTRIBUTELIST_TAG.equalsIgnoreCase(evt.getName().getLocalPart())){
                // read attributeList
                StartElement attributeList = (StartElement)eventReader.nextEvent();

                evt = (StartElement) eventReader.peek();
                if (evt == null){
                    if (listener != null){
                        FileSourceContext context = null;
                        if (attributeList.getLocation() != null){
                            Location loc = attributeList.getLocation();
                            context = new DefaultFileSourceContext(new PsiXmLocator(loc.getLineNumber(), loc.getColumnNumber(), null));
                        }
                        listener.onInvalidSyntax(context, new PsiXmlParserException("The attributeList node did not contain any availability node. PSI-XML is not valid."));
                    }
                }
                else{
                    boolean isReadingAttribute = PsiXmlUtils.ATTRIBUTE_TAG.equalsIgnoreCase(evt.getName().getLocalPart());

                    while(isReadingAttribute && this.eventReader.hasNext()){
                        currentEntry.getAnnotations().add((Annotation)this.unmarshaller.unmarshal(subEventReader));

                        evt = (StartElement) eventReader.peek();
                        isReadingAttribute = evt != null && PsiXmlUtils.ATTRIBUTE_TAG.equalsIgnoreCase(evt.getName().getLocalPart());
                    }
                }
            }

            this.interactionIterator = this.loadedInteractions.iterator();
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
    protected T parseInteractionTag(XmlEntryContext entryContext) throws JAXBException, XMLStreamException {
        T interaction = (T)this.unmarshaller.unmarshal(subEventReader);
        // no references, can return the interaction
        if (entryContext.getReferences().isEmpty() && entryContext.getInferredInteractions().isEmpty()){
            return interaction;
        }
        // we have references to resolve, loads the all entry and keep in cache
        else{
            loadEntry(entryContext,interaction);
        }

        return interaction;
    }

    private void clearEntryReferences(XmlEntryContext context){
        resolveInteractorAndExperimentRefs(context);
        resolveInferredInteractionRefs(context);
        XmlEntryContext.getInstance().clear();
    }

    private void resolveInteractorAndExperimentRefs(XmlEntryContext context){

        Iterator<XmlIdReference> refIterator = context.getReferences().iterator();
        while(refIterator.hasNext()){
            XmlIdReference ref = refIterator.next();
            if (!ref.resolve(context.getMapOfReferencedObjects())){
                if (listener != null){
                   listener.onUnresolvedReference(ref, "Cannot resolve a reference in the xml file");
                }
            }
            refIterator.remove();
        }
    }

    private void resolveInferredInteractionRefs(XmlEntryContext context){
        Iterator<InferredInteraction> inferredIterator = context.getInferredInteractions().iterator();
        while(inferredIterator.hasNext()){
            InferredInteraction inferred = inferredIterator.next();
            if (!inferred.getParticipants().isEmpty()){
                Iterator<InferredInteractionParticipant> partIterator = inferred.getParticipants().iterator();
                List<InferredInteractionParticipant> partIterator2 = new ArrayList<InferredInteractionParticipant>(inferred.getParticipants());
                int currentIndex = 0;

                while (partIterator.hasNext()){
                    currentIndex++;
                    InferredInteractionParticipant p1 = partIterator.next();
                    for (int i = currentIndex; i < partIterator2.size();i++){
                        InferredInteractionParticipant p2 = partIterator2.get(i);

                        if (p1.getFeature() != null && p2.getFeature() != null){
                            p1.getFeature().getLinkedFeatures().add(p2.getFeature());
                            if (p1.getFeature() != p2.getFeature()){
                                p2.getFeature().getLinkedFeatures().add(p1.getFeature());
                            }
                        }
                    }
                }
            }
            else{
                if (listener != null){
                    listener.onInvalidSyntax(inferred, new PsiXmlParserException("InferredInteraction must have at least one inferredInteractionParticipant."));
                }
            }

            inferredIterator.remove();
        }
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
        this.started = false;

        // release the thread local
        XmlEntryContext.getInstance().clear();
        XmlEntryContext.remove();
    }

    private void initializeXmlEventReader(XMLInputFactory xmlif, XMLEventReader xmler) throws XMLStreamException {
        this.subEventReader = xmler;
        EventFilter filter = new EventFilter() {
            public boolean accept(XMLEvent event) {
                // clear entry when reach end of entry
                if(event.isEndElement()){
                    EndElement end = (EndElement)event;
                    if (PsiXmlUtils.ENTRY_TAG.equalsIgnoreCase(end.getName().getLocalPart())){
                        XmlEntryContext context = XmlEntryContext.getInstance();
                        if (context.getCurrentEntry() != null){
                            context.getCurrentEntry().setHasLoadedFullEntry(true);
                        }
                        clearEntryReferences(context);
                    }
                }
                return event.isStartElement();
            }
        };
        this.eventReader = xmlif.createFilteredReader(xmler, filter);
    }
}
