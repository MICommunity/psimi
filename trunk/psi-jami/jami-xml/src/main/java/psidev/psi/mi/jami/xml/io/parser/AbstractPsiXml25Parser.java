package psidev.psi.mi.jami.xml.io.parser;

import psidev.psi.mi.jami.exception.MIIOException;
import psidev.psi.mi.jami.model.Annotation;
import psidev.psi.mi.jami.model.Interaction;
import psidev.psi.mi.jami.model.Source;
import psidev.psi.mi.jami.xml.XmlEntry;
import psidev.psi.mi.jami.xml.XmlEntryContext;
import psidev.psi.mi.jami.xml.XmlIdReference;
import psidev.psi.mi.jami.xml.extension.*;
import psidev.psi.mi.jami.xml.utils.PsiXmlUtils;

import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.EventFilter;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
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

public abstract class AbstractPsiXml25Parser<T extends Interaction> {

    private static final Logger logger = Logger.getLogger("AbstractPsiXml25Parser");
    private XMLEventReader eventReader;

    private URL originalURL;
    private File originalFile;
    private InputStream originalStream;
    private Reader originalReader;
    private Collection<T> loadedInteractions;
    private Unmarshaller unmarshaller;
    private Iterator<T> interactionIterator;
    private XmlInteractorFactory interactorFactory;

    public AbstractPsiXml25Parser(File file) throws FileNotFoundException, XMLStreamException {
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

    public AbstractPsiXml25Parser(InputStream inputStream) throws XMLStreamException {
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

    public AbstractPsiXml25Parser(URL url) throws IOException, XMLStreamException {
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

    public AbstractPsiXml25Parser(Reader reader) throws XMLStreamException {
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

    public T parseInteraction() throws IOException, XMLStreamException, JAXBException {
        // Parse into typed objects
        //JAXBContext ctx = JAXBContext.newInstance("psidev.psi.mi.jami.xml.extension");
        //Unmarshaller um = ctx.createUnmarshaller();

        // we have loaded interactions before because of references. We can use the cache and return next one until the cache is clear
        if (this.interactionIterator != null && this.interactionIterator.hasNext()){
            return parseNextPreLoadedInteraction();
        }

        // the eventreader does not have any new events
        if (!this.eventReader.hasNext()){
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
            // read the entrySet
            eventReader.nextEvent();
            return processEntry(entryContext);
        }
        else{
            // TODO element not recognized, syntax mistake

        }
        return null;
    }

    public void close(){
        if (this.eventReader != null){
            try {
                this.eventReader.close();
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

    /**
     *
     * @return the unmarshaller with the class context
     */
    protected abstract Unmarshaller createJAXBUnmarshaller();

    /**
     * Process an entry that is opened (source, experimentList, etc) and read the first interaction
     * @param entryContext
     * @return
     * @throws XMLStreamException
     * @throws JAXBException
     */
    protected T processEntryAndLoadNextInteraction(XmlEntryContext entryContext) throws XMLStreamException, JAXBException {
        T loadedInteraction = null;

        // process syntax error
        if (!this.eventReader.hasNext()){
            //TODO process syntax error
        }
        else{
            // get next event without parsing it until we could read an interaction and solve all its references
            while(loadedInteraction == null && this.eventReader.hasNext()){
                StartElement nextEvt = (StartElement) this.eventReader.peek();
                // process source of entry
                if (PsiXmlUtils.SOURCE_TAG.equalsIgnoreCase(nextEvt.getName().getLocalPart())){
                    entryContext.getCurrentEntry().setSource((Source) this.unmarshaller.unmarshal(eventReader));
                }
                // process availability
                else if (PsiXmlUtils.AVAILABILITYLIST_TAG.equalsIgnoreCase(nextEvt.getName().getLocalPart())){
                    // read availability list
                    this.eventReader.nextEvent();
                    // load availability
                    if (this.eventReader.hasNext()){
                        XmlEntry entry = entryContext.getCurrentEntry();

                        nextEvt = (StartElement)this.eventReader.peek();
                        // load availability
                        while (nextEvt != null && PsiXmlUtils.AVAILABILITY_TAG.equalsIgnoreCase(nextEvt.getName().getLocalPart())) {
                            entry.getAvailabilities().add((Availability)unmarshaller.unmarshal(this.eventReader));
                            if (this.eventReader.hasNext()){
                                nextEvt = (StartElement)this.eventReader.peek();
                            }
                            else{
                                nextEvt= null;
                            }
                        }
                    }
                    // TODO deal with wrong syntax
                    else{

                    }
                }
                // process experiments
                else if (PsiXmlUtils.EXPERIMENTLIST_TAG.equalsIgnoreCase(nextEvt.getName().getLocalPart())){
                    // read experiment list
                    this.eventReader.nextEvent();
                    // process experiments. Each experiment will be loaded in entryContext so no needs to do something else
                    if (this.eventReader.hasNext()){
                        nextEvt = (StartElement)this.eventReader.peek();
                        // load experimentDescription
                        while (nextEvt != null && PsiXmlUtils.EXPERIMENT_TAG.equalsIgnoreCase(nextEvt.getName().getLocalPart())) {
                            unmarshaller.unmarshal(this.eventReader);
                            if (this.eventReader.hasNext()){
                                nextEvt = (StartElement)this.eventReader.peek();
                            }
                            else{
                                nextEvt= null;
                            }
                        }
                    }
                    // TODO deal with wrong syntax
                    else{

                    }
                }
                // process interactors. All interactors will be stored in entryContext so no need to do something else
                else if (PsiXmlUtils.INTERACTORLIST_TAG.equalsIgnoreCase(nextEvt.getName().getLocalPart())){
                    // read experiment list
                    this.eventReader.nextEvent();
                    // process interactors
                    if (this.eventReader.hasNext()){
                        nextEvt = (StartElement)this.eventReader.peek();
                        // load experimentDescription
                        while (nextEvt != null && PsiXmlUtils.INTERACTOR_TAG.equalsIgnoreCase(nextEvt.getName().getLocalPart())) {
                            this.interactorFactory.
                                    createInteractorFromXmlInteractorInstance((XmlInteractor)unmarshaller.unmarshal(this.eventReader));
                            if (this.eventReader.hasNext()){
                                nextEvt = (StartElement)this.eventReader.peek();
                            }
                            else{
                                nextEvt= null;
                            }
                        }
                    }
                    // TODO deal with wrong syntax
                    else{

                    }
                }
                // process interaction
                else if (PsiXmlUtils.INTERACTIONLIST_TAG.equalsIgnoreCase(nextEvt.getName().getLocalPart())){
                    // read experiment list
                    this.eventReader.nextEvent();
                    // read interactions
                    if (this.eventReader.hasNext()){
                        loadedInteraction = parseInteractionTag(entryContext);
                    }
                    // TODO deal with wrong syntax
                    else{

                    }
                }
                // TODO deal with wrong syntax and attributList
                else{

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
        this.eventReader.next();
        // get next interaction if possible
        if (this.eventReader.hasNext()){
            // next element should be an entry
            return processEntryAndLoadNextInteraction(entryContext);
        }
        // element is expected here
        else{
            //TODO syntax error
        }
        return null;
    }

    /**
     * Some interactions contains references and we want to load the remaining interactions until the end of the entry
     * @param entryContext
     * @throws XMLStreamException
     * @throws JAXBException
     */
    protected void loadEntry(XmlEntryContext entryContext) throws XMLStreamException, JAXBException {
        // load the all entry
        // we already are parsing interactions
        StartElement evt = (StartElement) eventReader.peek();
        boolean isReadingInteraction = PsiXmlUtils.INTERACTION_TAG.equalsIgnoreCase(evt.getName().getLocalPart());
        while(isReadingInteraction && this.eventReader.hasNext()){
            this.loadedInteractions.add((T)this.unmarshaller.unmarshal(eventReader));

            evt = (StartElement) eventReader.peek();
            isReadingInteraction = evt != null && PsiXmlUtils.INTERACTION_TAG.equalsIgnoreCase(evt.getName().getLocalPart());
        }

        // get the current entry. It must exists
        XmlEntry currentEntry = entryContext.getCurrentEntry();
        if (currentEntry == null){
            // TODO deals with syntax error
            throw new IllegalStateException("Each interaction should be coming from an Entry");
        }

        // check entry attributes
        if (evt != null && PsiXmlUtils.ATTRIBUTELIST_TAG.equalsIgnoreCase(evt.getName().getLocalPart())){
            // read attributeList
            eventReader.nextEvent();

            evt = (StartElement) eventReader.peek();
            if (evt == null){
                // TODO deals with syntax error
            }
            else{
                boolean isReadingAttribute = PsiXmlUtils.ATTRIBUTE_TAG.equalsIgnoreCase(evt.getName().getLocalPart());

                while(isReadingAttribute && this.eventReader.hasNext()){
                    currentEntry.getAnnotations().add((Annotation)this.unmarshaller.unmarshal(eventReader));

                    evt = (StartElement) eventReader.peek();
                    isReadingAttribute = evt != null && PsiXmlUtils.ATTRIBUTE_TAG.equalsIgnoreCase(evt.getName().getLocalPart());
                }
            }
        }

        this.interactionIterator = this.loadedInteractions.iterator();
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
        T interaction = (T)this.unmarshaller.unmarshal(eventReader);
        // no references, can return the interaction
        if (entryContext.getReferences().isEmpty() && entryContext.getInferredInteractions().isEmpty()){
            return interaction;
        }
        // we have references to resolve, loads the all entry and keep in cache
        else{
            loadEntry(entryContext);
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
            if (!refIterator.next().resolve(context.getMapOfReferencedObjects())){
                // TODO syntax error
            }
            refIterator.remove();
        }
    }

    private void resolveInferredInteractionRefs(XmlEntryContext context){
        Iterator<InferredInteraction> inferredIterator = context.getInferredInteractions().iterator();
        while(inferredIterator.hasNext()){
            InferredInteraction inferred = inferredIterator.next();
            if (!inferred.getJAXBParticipants().isEmpty()){
                Iterator<InferredInteractionParticipant> partIterator = inferred.getJAXBParticipants().iterator();
                List<InferredInteractionParticipant> partIterator2 = new ArrayList<InferredInteractionParticipant>(inferred.getJAXBParticipants());
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
                // TODO process syntax error
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

        // release the thread local
        XmlEntryContext.getInstance().clear();
        XmlEntryContext.remove();
    }

    private void initializeXmlEventReader(XMLInputFactory xmlif, XMLEventReader xmler) throws XMLStreamException {
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
