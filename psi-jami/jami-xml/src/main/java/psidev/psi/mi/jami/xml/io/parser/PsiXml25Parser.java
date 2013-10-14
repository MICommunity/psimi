package psidev.psi.mi.jami.xml.io.parser;

import psidev.psi.mi.jami.exception.MIIOException;
import psidev.psi.mi.jami.model.Interaction;
import psidev.psi.mi.jami.model.Source;
import psidev.psi.mi.jami.xml.XmlEntryContext;
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

public abstract class PsiXml25Parser<T extends Interaction> {

    private XMLEventReader eventReader;

    private URL originalURL;
    private File originalFile;
    private InputStream originalStream;
    private Reader originalReader;
    private Collection<T> loadedInteractions;
    private Unmarshaller unmarshaller;
    private Iterator<T> interactionIterator;

    public PsiXml25Parser(File file) throws FileNotFoundException, XMLStreamException {
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
    }

    public PsiXml25Parser(InputStream inputStream) throws XMLStreamException {
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
    }

    public PsiXml25Parser(URL url) throws IOException, XMLStreamException {
        if (url == null){
            throw new IllegalArgumentException("The PsiXmlParser needs a non null URL");
        }
        this.originalURL = url;
        // Parse the data, filtering out the start elements
        XMLInputFactory xmlif = XMLInputFactory.newInstance();
        InputStreamReader fr = new InputStreamReader(url.openStream());
        XMLEventReader xmler = xmlif.createXMLEventReader(fr);
        initializeXmlEventReader(xmlif, xmler);
        loadedInteractions = new ArrayList<T>();
        this.unmarshaller = createJAXBUnmarshaller();
    }

    public PsiXml25Parser(Reader reader) throws XMLStreamException {
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
        XmlEntryContext entryContext = XmlEntryContext.getInstance();

        // reads the next interaction
        if (PsiXmlUtils.INTERACTION_TAG.equalsIgnoreCase(start.getName().getLocalPart())){
            return parseInteractionTag(entryContext);
        }
        // end of Entry because the Event filter only accepts all StartElements and the EndElement of entry
        else if (PsiXmlUtils.ENTRY_TAG.equalsIgnoreCase(start.getName().getLocalPart())) {
            // read entry
            this.eventReader.next();
            // get next interaction if possible
            if (this.eventReader.hasNext()){
                // next element should be an entry
                return processEntryAndLoadNextInteraction(entryContext);
            }
        }
        // entry set
        else if (PsiXmlUtils.ENTRYSET_TAG.equalsIgnoreCase(start.getName().getLocalPart())){
            // read the entrySet
            eventReader.nextEvent();
            if (this.eventReader.hasNext()){
                // next element should be an entry
                return processEntryAndLoadNextInteraction(entryContext);
            }
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
        return !this.eventReader.hasNext();
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
    }

    protected abstract Unmarshaller createJAXBUnmarshaller();

    protected T processEntryAndLoadNextInteraction(XmlEntryContext entryContext) throws XMLStreamException, JAXBException {
        T loadedInteraction = null;

        // get next event without parsing it until we could read an interaction and solve all its references
        while(loadedInteraction == null && this.eventReader.hasNext()){
            StartElement nextEvt = (StartElement) this.eventReader.peek();
            // process source of entry
            if (PsiXmlUtils.SOURCE_TAG.equalsIgnoreCase(nextEvt.getName().getLocalPart())){
                entryContext.setCurrentSource((Source)this.unmarshaller.unmarshal(eventReader));
            }
            // process availability
            else if (PsiXmlUtils.AVAILABILITYLIST_TAG.equalsIgnoreCase(nextEvt.getName().getLocalPart())){
                // read availability list
                this.eventReader.nextEvent();
                if (this.eventReader.hasNext()){
                    nextEvt = (StartElement)this.eventReader.peek();
                    // load availability
                    while (nextEvt != null && PsiXmlUtils.AVAILABILITY_TAG.equalsIgnoreCase(nextEvt.getName().getLocalPart())) {
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
            // process experiments
            else if (PsiXmlUtils.EXPERIMENTLIST_TAG.equalsIgnoreCase(nextEvt.getName().getLocalPart())){
                // read experiment list
                this.eventReader.nextEvent();
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
            // process interactors
            else if (PsiXmlUtils.INTERACTORLIST_TAG.equalsIgnoreCase(nextEvt.getName().getLocalPart())){
                // read experiment list
                this.eventReader.nextEvent();
                if (this.eventReader.hasNext()){
                    nextEvt = (StartElement)this.eventReader.peek();
                    // load experimentDescription
                    while (nextEvt != null && PsiXmlUtils.INTERACTOR_TAG.equalsIgnoreCase(nextEvt.getName().getLocalPart())) {
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
            // process interaction
            else if (PsiXmlUtils.INTERACTORLIST_TAG.equalsIgnoreCase(nextEvt.getName().getLocalPart())){
                // read experiment list
                this.eventReader.nextEvent();
                if (this.eventReader.hasNext()){
                    return parseInteractionTag(entryContext);
                }
                // TODO deal with wrong syntax
                else{

                }
            }
            // TODO deal with wrong syntax and attributList
            else{

            }
        }

        return loadedInteraction;
    }

    private void initializeXmlEventReader(XMLInputFactory xmlif, XMLEventReader xmler) throws XMLStreamException {
        EventFilter filter = new EventFilter() {
            public boolean accept(XMLEvent event) {
                // clear entry when reach end of entry
                if(event.isEndElement()){
                    EndElement end = (EndElement)event;
                    if (PsiXmlUtils.ENTRY_TAG.equalsIgnoreCase(end.getName().getLocalPart())){
                        clearEntryReferences();
                    }
                }
                return event.isStartElement();
            }
        };
        this.eventReader = xmlif.createFilteredReader(xmler, filter);
    }

    private void resolveInteractorAndExperimentRefs(){
        // resolve experiment and interactor ref
        // load participant identification method
        // load feature detection method
        // solve source pb

    }

    private void resolveInferredInteractionRefs(){
        // resolve binding sites

    }

    private void loadEntry(XmlEntryContext entryContext) throws XMLStreamException, JAXBException {
        // load the all entry
        // we already are parsing interactions
        StartElement evt = (StartElement) eventReader.peek();
        boolean isReading = PsiXmlUtils.INTERACTION_TAG.equalsIgnoreCase(evt.getName().getLocalPart());
        while(isReading && this.eventReader.hasNext()){
            T interaction = (T)this.unmarshaller.unmarshal(eventReader);
            this.loadedInteractions.add(interaction);

            evt = (StartElement) eventReader.peek();
            isReading = evt != null && PsiXmlUtils.INTERACTION_TAG.equalsIgnoreCase(evt.getName().getLocalPart());
        }

        // resolve references
        clearEntryReferences();
        this.interactionIterator = this.loadedInteractions.iterator();
    }

    private void clearEntryReferences(){
        resolveInteractorAndExperimentRefs();
        resolveInferredInteractionRefs();
        XmlEntryContext.getInstance().clear();
    }

    /**
     *
     * @return the next interaction preloaded in the interactionIterator. Deletes the returned interaction
     */
    private T parseNextPreLoadedInteraction() {
        T interaction = this.interactionIterator.next();
        this.interactionIterator.remove();
        return interaction;
    }

    /**
     *
     * @param entryContext
     * @return next interaction parsed in the interaction list. Will load the all entry if we have references to solve
     * @throws JAXBException
     * @throws XMLStreamException
     */
    private T parseInteractionTag(XmlEntryContext entryContext) throws JAXBException, XMLStreamException {
        T interaction = (T)this.unmarshaller.unmarshal(eventReader);
        // no references, can return the interaction
        if (entryContext.getReferences().isEmpty()){
            return interaction;
        }
        // we have references to resolve, loads the all entry and keep in cache
        else{
            loadEntry(entryContext);
        }

        return interaction;
    }
}
