package psidev.psi.mi.jami.xml.io.writer;

import javanet.staxutils.IndentingXMLStreamWriter;
import org.codehaus.stax2.XMLOutputFactory2;
import org.codehaus.stax2.XMLStreamWriter2;
import psidev.psi.mi.jami.datasource.InteractionWriter;
import psidev.psi.mi.jami.exception.MIIOException;
import psidev.psi.mi.jami.factory.InteractionWriterFactory;
import psidev.psi.mi.jami.model.Interaction;
import psidev.psi.mi.jami.model.ModelledInteraction;
import psidev.psi.mi.jami.model.Source;
import psidev.psi.mi.jami.model.impl.DefaultSource;
import psidev.psi.mi.jami.xml.PsiXml25ObjectCache;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXml25InteractionWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXml25SourceWriter;
import psidev.psi.mi.jami.xml.utils.PsiXml25Utils;

import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.io.*;
import java.util.*;

/**
 * Abstract class for XML 2.5 writer of interactions
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>11/11/13</pre>
 */

public abstract class AbstractXml25Writer<T extends Interaction> implements InteractionWriter<T>{

    private XMLStreamWriter streamWriter;
    private boolean isInitialised = false;
    private PsiXml25InteractionWriter<T> interactionWriter;
    private PsiXml25InteractionWriter<ModelledInteraction> complexWriter;
    private PsiXml25SourceWriter sourceWriter;
    private PsiXml25ObjectCache elementCache;
    private List<T> interactionsToWrite;
    private Iterator<? extends T> interactionsIterator;
    private boolean started = false;
    private Source currentSource;
    private T currentInteraction;
    private boolean writeComplexesAsInteractors=false;
    private Set<T> processedInteractions;

    private Source defaultSource;
    private XMLGregorianCalendar defaultReleaseDate;

    public AbstractXml25Writer(){
        this.interactionsToWrite = new ArrayList<T>();
    }

    public AbstractXml25Writer(File file) throws IOException, XMLStreamException {

        initialiseFile(file);
        isInitialised = true;
        this.interactionsToWrite = new ArrayList<T>();
    }

    public AbstractXml25Writer(OutputStream output) throws XMLStreamException {

        initialiseOutputStream(output);
        isInitialised = true;
        this.interactionsToWrite = new ArrayList<T>();
    }

    public AbstractXml25Writer(Writer writer) throws XMLStreamException {

        initialiseWriter(writer);
        isInitialised = true;
        this.interactionsToWrite = new ArrayList<T>();
    }

    protected AbstractXml25Writer(XMLStreamWriter streamWriter, PsiXml25ObjectCache elementCache) {
        if (streamWriter == null){
            throw new IllegalArgumentException("The stream writer cannot be null.");
        }

        this.elementCache = elementCache;
        initialiseStreamWriter(streamWriter);
        isInitialised = true;
        this.interactionsToWrite = new ArrayList<T>();
    }

    public void initialiseContext(Map<String, Object> options) {
        if (options != null && options.containsKey(PsiXml25Utils.ELEMENT_WITH_ID_CACHE_OPTION)){
            this.elementCache = (PsiXml25ObjectCache)options.get(PsiXml25Utils.ELEMENT_WITH_ID_CACHE_OPTION);
        }
        // use the default cache option
        else{
            initialiseDefaultElementCache();
        }

        if (options == null && !isInitialised){
            throw new IllegalArgumentException("The options for the PSI-XML 2.5 writer should contains at least "+ InteractionWriterFactory.OUTPUT_OPTION_KEY + " to know where to write the interactions.");
        }
        else if (options == null){
            return;
        }
        else if (options.containsKey(InteractionWriterFactory.OUTPUT_OPTION_KEY)){
            Object output = options.get(InteractionWriterFactory.OUTPUT_OPTION_KEY);

            if (output instanceof File){
                try {
                    initialiseFile((File) output);
                } catch (XMLStreamException e) {
                    throw new IllegalArgumentException("Impossible to open and write in output file " + ((File)output).getName(), e);
                } catch (IOException e) {
                    throw new IllegalArgumentException("Impossible to open and write in output file " + ((File)output).getName(), e);
                }
            }
            else if (output instanceof OutputStream){
                try {
                    initialiseOutputStream((OutputStream) output);
                } catch (XMLStreamException e) {
                    throw new IllegalArgumentException("Impossible to open and write in output stream ", e);
                }
            }
            else if (output instanceof Writer){
                try {
                    initialiseWriter((Writer) output);
                } catch (XMLStreamException e) {
                    throw new IllegalArgumentException("Impossible to open and write in output writer ", e);
                }
            }
            // suspect a file path
            else if (output instanceof String){
                try {
                    initialiseFile(new File((String)output));
                } catch (IOException e) {
                    throw new IllegalArgumentException("Impossible to open and write in output file " + output, e);
                }
                catch (XMLStreamException e) {
                    throw new IllegalArgumentException("Impossible to open and write in output file " + ((File)output).getName(), e);
                }
            }
            else if (output instanceof XMLStreamWriter){
                initialiseStreamWriter((XMLStreamWriter) output);
            }
            else {
                throw new IllegalArgumentException("Impossible to write in the provided output "+output.getClass().getName() + ", a File, OuputStream, Writer or file path was expected.");
            }
        }
        else if (!isInitialised){
            throw new IllegalArgumentException("The options for the PSI-XML 2.5 writer should contains at least "+ InteractionWriterFactory.OUTPUT_OPTION_KEY + " to know where to write the interactions.");
        }

        if (options.containsKey(PsiXml25Utils.WRITE_COMPLEX_AS_INTERACTOR_OPTION)){
            setWriteComplexesAsInteractors(this.writeComplexesAsInteractors = (Boolean)options.get(PsiXml25Utils.WRITE_COMPLEX_AS_INTERACTOR_OPTION));
        }

        if (options.containsKey(PsiXml25Utils.XML_INTERACTION_SET_OPTION)){
            setInteractionSet((Set<T>) options.get(PsiXml25Utils.XML_INTERACTION_SET_OPTION));
        }
        // use the default cache option
        else{
            initialiseDefaultInteractionSet();
        }
        // default source
        if (options.containsKey(PsiXml25Utils.DEFAULT_SOURCE_OPTION)){
            setDefaultSource((Source)options.get(PsiXml25Utils.DEFAULT_SOURCE_OPTION));
        }

        // default release date
        if (options.containsKey(PsiXml25Utils.DEFAULT_RELEASE_DATE_OPTION)){
            setDefaultReleaseDate((XMLGregorianCalendar)options.get(PsiXml25Utils.DEFAULT_RELEASE_DATE_OPTION));
        }

        isInitialised = true;
    }

    @Override
    public void end() throws MIIOException {
        if (!isInitialised){
            throw new IllegalStateException("The PSI-XML 2.5 writer was not initialised. The options for the PSI-XML 2.5 writer should contains at least "+ InteractionWriterFactory.OUTPUT_OPTION_KEY + " to know where to write the interactions.");
        }
        started = false;
        // write end of entrySet
        try {
            this.streamWriter.writeEndElement();
        } catch (XMLStreamException e) {
            throw new MIIOException("Cannot write the end of entrySet root node.", e);
        }
        // write end of document
        try {
            this.streamWriter.writeEndDocument();
        } catch (XMLStreamException e) {
            throw new MIIOException("Cannot write the end of XML document.", e);
        }
    }

    @Override
    public void start() throws MIIOException {
        if (!isInitialised){
            throw new IllegalStateException("The PSI-XML 2.5 writer was not initialised. The options for the PSI-XML 2.5 writer should contains at least "+ InteractionWriterFactory.OUTPUT_OPTION_KEY + " to know where to write the interactions.");
        }
        // write start of document (by default, version = 1 and encoding = UTL-8)
        try {
            this.streamWriter.writeStartDocument();
        } catch (XMLStreamException e) {
            throw new MIIOException("Cannot write the start document of this XML 2.5 output.", e);
        }
        // write start of entrySet
        try {
            this.streamWriter.writeStartElement(PsiXml25Utils.ENTRYSET_TAG);
            this.streamWriter.writeDefaultNamespace(PsiXml25Utils.NAMESPACE_URI);
            this.streamWriter.writeNamespace(PsiXml25Utils.XML_SCHEMA_PREFIX,PsiXml25Utils.XML_SCHEMA);
            this.streamWriter.writeAttribute(PsiXml25Utils.XML_SCHEMA, PsiXml25Utils.SCHEMA_LOCATION_ATTRIBUTE, PsiXml25Utils.PSI_SCHEMA_LOCATION);
            this.streamWriter.writeAttribute(PsiXml25Utils.LEVEL_ATTRIBUTE,"2");
            this.streamWriter.writeAttribute(PsiXml25Utils.VERSION_ATTRIBUTE,"5");
            this.streamWriter.writeAttribute(PsiXml25Utils.MINOR_VERSION_ATTRIBUTE,"4");
        } catch (XMLStreamException e) {
            throw new MIIOException("Cannot write the start of the entrySet root node.", e);
        }
    }

    @Override
    public void write(T interaction) throws MIIOException {
        if (!isInitialised){
            throw new IllegalStateException("The PSI-XML 2.5 writer was not initialised. The options for the PSI-XML 2.5 writer should contains at least "+ InteractionWriterFactory.OUTPUT_OPTION_KEY + " to know where to write the interactions.");
        }
        registerInteractionForEntry(interaction);
        writeInteractionListContent();
    }

    @Override
    public void write(Collection<? extends T> interactions) throws MIIOException {
        if (!isInitialised){
            throw new IllegalStateException("The PSI-XML 2.5 writer was not initialised. The options for the PSI-XML 2.5 writer should contains at least "+ InteractionWriterFactory.OUTPUT_OPTION_KEY + " to know where to write the interactions.");
        }
        registerInteractionsForEntry(interactions);
        writeInteractionListContent();
    }

    @Override
    public void write(Iterator<? extends T> interactions) throws MIIOException {
        if (!isInitialised){
            throw new IllegalStateException("The PSI-XML 2.5 writer was not initialised. The options for the PSI-XML 2.5 writer should contains at least "+ InteractionWriterFactory.OUTPUT_OPTION_KEY + " to know where to write the interactions.");
        }
        registerInteractionsForEntry(interactions);
        writeInteractionListContent();
    }

    @Override
    public void flush() throws MIIOException{
        if (isInitialised){
            try {
                this.streamWriter.flush();
            } catch (XMLStreamException e) {
                throw new MIIOException("Impossible to flush the PSI-XML 2.5 writer", e);
            }
        }
    }

    public void close() throws MIIOException{
        if (isInitialised){
            try {
                this.streamWriter.flush();
            } catch (XMLStreamException e) {
                throw new MIIOException("Impossible to flush the PSI-XML 2.5 writer", e);
            } finally {
                this.isInitialised = false;
                this.streamWriter = null;
                this.elementCache = null;
                this.interactionsToWrite.clear();
                this.interactionsIterator = null;
                this.processedInteractions = null;
            }
        }
    }
    public void reset() throws MIIOException{
        if (isInitialised){
            try {
                this.streamWriter.flush();
            } catch (XMLStreamException e) {
                throw new MIIOException("Impossible to flush the PSI-XML 2.5 writer", e);
            } finally {
                isInitialised = false;
                this.streamWriter = null;
                this.elementCache = null;
                this.interactionsToWrite.clear();
                this.interactionsIterator = null;
                this.processedInteractions = null;
            }
        }
    }

    public void setWriteComplexesAsInteractors(boolean writeComplexesAsInteractors) {
        this.writeComplexesAsInteractors = writeComplexesAsInteractors;
        if (this.interactionWriter != null){
            this.interactionWriter.setComplexAsInteractor(writeComplexesAsInteractors);
        }
        if (this.complexWriter != null){
            this.complexWriter.setComplexAsInteractor(writeComplexesAsInteractors);
        }
    }

    public void setInteractionSet(Set<T> processedInteractions) {
        this.processedInteractions = processedInteractions;
    }

    public void setSourceWriter(PsiXml25SourceWriter sourceWriter) {
        if (sourceWriter == null){
            throw new IllegalArgumentException("The source writer cannot be null");
        }
        this.sourceWriter = sourceWriter;
        this.sourceWriter.setDefaultReleaseDate(this.defaultReleaseDate);
    }
    public void setComplexWriter(PsiXml25InteractionWriter<ModelledInteraction> complexWriter) {
        if (complexWriter == null){
            throw new IllegalArgumentException("The Complex writer cannot be null");
        }
        this.complexWriter = complexWriter;
    }

    public void setElementCache(PsiXml25ObjectCache elementCache) {
        if (elementCache == null){
            initialiseDefaultElementCache();
        }
        else{
            this.elementCache = elementCache;
        }
        // reinit subwriters
        initialiseSubWriters();
    }

    public void setStarted(boolean started) {
        this.started = started;
    }

    public void setDefaultSource(Source defaultSource) {
        this.defaultSource = defaultSource;
    }

    public void setDefaultReleaseDate(XMLGregorianCalendar defaultReleaseDate) {
        this.defaultReleaseDate = defaultReleaseDate;
        if (this.sourceWriter != null){
            this.sourceWriter.setDefaultReleaseDate(this.defaultReleaseDate);
        }
    }

    protected Set<T> getProcessedInteractions() {
        if (processedInteractions == null){
            initialiseDefaultInteractionSet();
        }
        return processedInteractions;
    }

    protected void writeEndEntryContent() throws XMLStreamException {
        // write subComplexes
        writeSubComplexInEntry();
        // write end interactionsList
        writeEndInteractionList();
        // write end previous entry
        writeEndEntry();
    }

    protected void writeInteractionListContent() {
        started = true;
        try {
            while (this.interactionsIterator.hasNext()){
                this.currentInteraction = this.interactionsIterator.next();
                Source source = extractSourceFromInteraction();
                // write first entry
                if (started){
                    setStarted(false);
                    this.currentSource = source;
                    getProcessedInteractions().clear();
                    writeStartEntryContent();

                }
                // write next entry after closing first one
                else if (this.currentSource != source){
                    // write subComplexes
                    writeEndEntryContent();
                    // change current source
                    this.currentSource = source;
                    getProcessedInteractions().clear();
                    // write start entry
                    writeStartEntryContent();
                }

                // write interaction
                if (getProcessedInteractions().add(this.currentInteraction)){
                    writeInteraction();
                }
            }

            // write final end entry
            writeEndEntryContent();
            setStarted(true);
        } catch (XMLStreamException e) {
            throw new MIIOException("Cannot write interactions ", e);
        }
    }

    protected abstract void writeStartEntryContent() throws XMLStreamException;

    protected void writeStartInteractionList() throws XMLStreamException {
        // write start interaction list
        this.streamWriter.writeStartElement(PsiXml25Utils.INTERACTIONLIST_TAG);
    }

    protected void writeEndInteractionList() throws XMLStreamException {
        // write end interaction list
        this.streamWriter.writeEndElement();
    }

    protected void writeInteraction() throws XMLStreamException {
        // write interaction
        this.interactionWriter.write(this.currentInteraction);
    }

    protected void writeSubComplexInEntry() throws XMLStreamException {
         while (this.elementCache.hasRegisteredSubComplexes()){
             Set<ModelledInteraction> registeredComplexes = this.elementCache.clearRegisteredSubComplexes();
             for (ModelledInteraction modelled : registeredComplexes){
                 this.complexWriter.write(modelled);
             }
         }
    }

    protected Source extractSourceFromInteraction(){
        if (this.defaultSource == null && this.defaultReleaseDate != null){
           initialiseDefaultSource();
        }
        return this.defaultSource;
    }

    protected void initialiseDefaultSource() {
        this.defaultSource = new DefaultSource("Unknown source");
    }

    protected void writeSource() throws XMLStreamException {
        if (this.currentSource != null){
            this.sourceWriter.write(this.currentSource);
        }
    }

    protected void writeStartEntry() throws XMLStreamException {
        this.elementCache.clear();
        this.streamWriter.writeStartElement(PsiXml25Utils.ENTRY_TAG);
    }

    protected void writeEndEntry() throws XMLStreamException {
        this.streamWriter.writeEndElement();
        this.elementCache.clear();
    }

    protected abstract void initialiseSubWriters();

    protected abstract void initialiseDefaultElementCache();

    protected PsiXml25InteractionWriter<T> getInteractionWriter() {
        return interactionWriter;
    }

    protected PsiXml25InteractionWriter<ModelledInteraction> getComplexWriter() {
        return complexWriter;
    }

    protected PsiXml25ObjectCache getElementCache() {
        if (elementCache == null){
           initialiseDefaultElementCache();
        }
        return elementCache;
    }

    protected void setCurrentSource(Source currentSource) {
        this.currentSource = currentSource;
    }

    protected Source getCurrentSource() {
        return currentSource;
    }

    protected Iterator<? extends T> getInteractionsIterator() {
        return interactionsIterator;
    }

    protected void setInteractionsIterator(Iterator<? extends T> interactionsIterator) {
        this.interactionsIterator = interactionsIterator;
    }

    protected void setInteractionWriter(PsiXml25InteractionWriter<T> interactionWriter) {
        if (interactionWriter == null){
            throw new IllegalArgumentException("The interaction writer cannot be null");
        }
        this.interactionWriter = interactionWriter;
    }

    protected XMLStreamWriter getStreamWriter() {
        return streamWriter;
    }

    protected T getCurrentInteraction() {
        return currentInteraction;
    }

    protected void setCurrentInteraction(T currentInteraction) {
        this.currentInteraction = currentInteraction;
    }

    protected boolean isStarted() {
        return started;
    }

    protected boolean writeComplexesAsInteractors() {
        return writeComplexesAsInteractors;
    }

    protected void initialiseDefaultInteractionSet() {
        this.processedInteractions = Collections.newSetFromMap(new IdentityHashMap<T, Boolean>());
    }

    private void initialiseStreamWriter(XMLStreamWriter writer) {
        if (writer == null){
            throw new IllegalArgumentException("The writer cannot be null.");
        }
        this.streamWriter = writer;
        initialiseSubWriters();
    }

    private void initialiseWriter(Writer writer) throws XMLStreamException {
        if (writer == null){
            throw new IllegalArgumentException("The writer cannot be null.");
        }
        XMLOutputFactory outputFactory = XMLOutputFactory2.newInstance();
        XMLStreamWriter2 streamWriter2 = (XMLStreamWriter2)outputFactory.createXMLStreamWriter(writer);
        this.streamWriter = new IndentingXMLStreamWriter(streamWriter2);
        initialiseSubWriters();
    }

    private void initialiseOutputStream(OutputStream output) throws XMLStreamException {
        if (output == null){
            throw new IllegalArgumentException("The output stream cannot be null.");
        }

        XMLOutputFactory outputFactory = XMLOutputFactory2.newInstance();
        XMLStreamWriter2 streamWriter2 = (XMLStreamWriter2)outputFactory.createXMLStreamWriter(output, "UTF-8");
        this.streamWriter = new IndentingXMLStreamWriter(streamWriter2);
        initialiseSubWriters();
    }

    private void initialiseFile(File file) throws IOException, XMLStreamException {
        if (file == null){
            throw new IllegalArgumentException("The file cannot be null.");
        }
        else if (!file.canWrite()){
            throw new IllegalArgumentException("Does not have the permissions to write in file "+file.getAbsolutePath());
        }

        XMLOutputFactory outputFactory = XMLOutputFactory2.newInstance();
        this.streamWriter = new IndentingXMLStreamWriter(outputFactory.createXMLStreamWriter(new FileOutputStream(file), "UTF-8"));
        initialiseSubWriters();
    }

    private void registerInteractionForEntry(T interaction) {
        this.interactionsToWrite.clear();
        this.interactionsToWrite.add(interaction);
        this.interactionsIterator = this.interactionsToWrite.iterator();
        this.currentInteraction = null;
    }

    private void registerInteractionsForEntry(Collection<? extends T> interactions) {
        this.interactionsToWrite.clear();
        this.interactionsToWrite.addAll(interactions);
        this.interactionsIterator = this.interactionsToWrite.iterator();
        this.currentInteraction = null;
    }

    private void registerInteractionsForEntry(Iterator<? extends T> interactions) {
        this.interactionsToWrite.clear();
        this.interactionsIterator = interactions;
        this.currentInteraction = null;
    }
}
