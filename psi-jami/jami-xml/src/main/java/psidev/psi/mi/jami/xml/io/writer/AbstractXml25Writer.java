package psidev.psi.mi.jami.xml.io.writer;

import org.codehaus.stax2.XMLOutputFactory2;
import org.codehaus.stax2.XMLStreamWriter2;
import psidev.psi.mi.jami.datasource.InteractionWriter;
import psidev.psi.mi.jami.exception.MIIOException;
import psidev.psi.mi.jami.factory.InteractionWriterFactory;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.xml.InMemoryIdentityObjectCache;
import psidev.psi.mi.jami.xml.PsiXml25ObjectCache;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXml25ElementWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXml25InteractionWriter;
import psidev.psi.mi.jami.xml.utils.PsiXml25Utils;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
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

    private XMLStreamWriter2 streamWriter;
    private boolean isInitialised = false;
    private PsiXml25InteractionWriter<T> interactionWriter;
    private PsiXml25InteractionWriter<ModelledInteraction> complexWriter;
    private PsiXml25ElementWriter<Source> sourceWriter;
    private PsiXml25ObjectCache elementCache;
    private List<T> interactionsToWrite;
    private Iterator<? extends T> interactionsIterator;
    private boolean started = false;
    private Source currentSource;
    private T currentInteraction;

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

    public void initialiseContext(Map<String, Object> options) {

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
            else {
                throw new IllegalArgumentException("Impossible to write in the provided output "+output.getClass().getName() + ", a File, OuputStream, Writer or file path was expected.");
            }
        }
        else if (!isInitialised){
            throw new IllegalArgumentException("The options for the PSI-XML 2.5 writer should contains at least "+ InteractionWriterFactory.OUTPUT_OPTION_KEY + " to know where to write the interactions.");
        }

        if (options.containsKey(PsiXml25Utils.ELEMENT_WITH_ID_CACHE_OPTION)){
            this.elementCache = (PsiXml25ObjectCache)options.get(PsiXml25Utils.ELEMENT_WITH_ID_CACHE_OPTION);
        }
        // use the default cache option
        else{
            initialiseDefaultElementCache();
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
            this.streamWriter.writeCharacters(PsiXml25Utils.LINE_BREAK);
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
        started = true;
        // write start of document (by default, version = 1 and encoding = UTL-8)
        try {
            this.streamWriter.writeStartDocument();
            this.streamWriter.writeCharacters(PsiXml25Utils.LINE_BREAK);
        } catch (XMLStreamException e) {
            throw new MIIOException("Cannot write the start document of this XML 2.5 output.", e);
        }
        // write start of entrySet
        try {
            this.streamWriter.writeStartElement(PsiXml25Utils.ENTRYSET_TAG);
            this.streamWriter.writeDefaultNamespace(PsiXml25Utils.NAMESPACE_URI);
            this.streamWriter.writeNamespace(PsiXml25Utils.XML_SCHEMA_PREFIX,PsiXml25Utils.XML_SCHEMA);
            this.streamWriter.writeAttribute(PsiXml25Utils.XML_SCHEMA, PsiXml25Utils.SCHEMA_LOCATION_ATTRIBUTE, PsiXml25Utils.PSI_SCHEMA_LOCATION);
            this.streamWriter.writeAttribute(PsiXml25Utils.VERSION_ATTRIBUTE,"5");
            this.streamWriter.writeAttribute(PsiXml25Utils.MINOR_VERSION_ATTRIBUTE,"4");
            this.streamWriter.writeAttribute(PsiXml25Utils.LEVEL_ATTRIBUTE,"2");
            this.streamWriter.writeCharacters(PsiXml25Utils.LINE_BREAK);
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
        try {
            this.currentInteraction = interaction;
            this.currentSource = extractSourceFromInteraction();
            // write first entry
            if (started){
                started = false;
            }

            // write start entry content
            writeStartEntryContent();
            // write interaction
            writeInteraction();
            // write end entry content
            writeEndEntryContent();

        } catch (XMLStreamException e) {
            throw new MIIOException("Cannot write interaction "+interaction.toString(), e);
        }
    }

    protected void writeEndEntryContent() throws XMLStreamException {
        // write subComplexes
        writeSubComplexInEntry();
        // write end interactionsList
        writeEndInteractionList();
        // write end previous entry
        writeEndEntry();
    }

    @Override
    public void write(Collection<? extends T> interactions) throws MIIOException {
        if (!isInitialised){
            throw new IllegalStateException("The PSI-XML 2.5 writer was not initialised. The options for the PSI-XML 2.5 writer should contains at least "+ InteractionWriterFactory.OUTPUT_OPTION_KEY + " to know where to write the interactions.");
        }
        registerInteractionsForEntry(interactions);
        writeInteractionListContent();
    }

    protected void writeInteractionListContent() {
        try {
            while (this.interactionsIterator.hasNext()){
                this.currentInteraction = this.interactionsIterator.next();
                Source source = extractSourceFromInteraction();
                // write first entry
                if (started){
                    started = false;
                    this.currentSource = source;
                    writeStartEntryContent();

                }
                // write next entry after closing first one
                else if (this.currentSource != source){
                    // write subComplexes
                    writeEndEntryContent();
                    // change current source
                    this.currentSource = source;
                    // write start entry
                    writeStartEntryContent();
                }

                // write interaction
                writeInteraction();
            }

            // write final end entry
            writeEndEntryContent();
        } catch (XMLStreamException e) {
            throw new MIIOException("Cannot write interactions ", e);
        }
    }

    protected void writeStartEntryContent() throws XMLStreamException {
        // write start entry
        writeStartEntry();
        // write source
        writeSource();
        // write start interactionList
        writeStartInteractionList();
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
            }
        }
    }

    protected void registerInteractionForEntry(T interaction) {
        this.interactionsToWrite.clear();
        this.interactionsIterator = null;
    }

    protected void registerInteractionsForEntry(Collection<? extends T> interactions) {
        this.interactionsToWrite.clear();
        this.interactionsToWrite.addAll(interactions);
        this.interactionsIterator = this.interactionsToWrite.iterator();
        this.currentInteraction = null;
    }

    protected void registerInteractionsForEntry(Iterator<? extends T> interactions) {
        this.interactionsToWrite.clear();
        this.interactionsIterator = interactions;
        this.currentInteraction = null;
    }

    protected void writeStartInteractionList() throws XMLStreamException {
        // write start interaction list
        this.streamWriter.writeStartElement(PsiXml25Utils.INTERACTIONLIST_TAG);
        this.streamWriter.writeCharacters(PsiXml25Utils.LINE_BREAK);
    }

    protected void writeEndInteractionList() throws XMLStreamException {
        // write end interaction list
        this.streamWriter.writeEndElement();
        this.streamWriter.writeCharacters(PsiXml25Utils.LINE_BREAK);
    }

    protected void writeInteraction() throws XMLStreamException {
        // write interaction
        this.interactionWriter.write(this.currentInteraction);
        this.streamWriter.writeCharacters(PsiXml25Utils.LINE_BREAK);
    }

    protected void writeSubComplexInEntry() throws XMLStreamException {
         while (this.elementCache.hasRegisteredSubComplexes()){
             Set<ModelledInteraction> registeredComplexes = this.elementCache.clearRegisteredSubComplexes();
             for (ModelledInteraction modelled : registeredComplexes){
                 this.complexWriter.write(modelled);
                 this.streamWriter.writeCharacters(PsiXml25Utils.LINE_BREAK);
             }
         }
    }

    protected abstract Source extractSourceFromInteraction();

    protected void writeSource() throws XMLStreamException {
        this.sourceWriter.write(this.currentSource);
        this.streamWriter.writeCharacters(PsiXml25Utils.LINE_BREAK);
    }

    protected void writeStartEntry() throws XMLStreamException {
        this.elementCache.clear();
        this.streamWriter.writeStartElement(PsiXml25Utils.ENTRY_TAG);
        this.streamWriter.writeCharacters(PsiXml25Utils.LINE_BREAK);
    }

    protected void writeEndEntry() throws XMLStreamException {
        this.streamWriter.writeEndElement();
        this.streamWriter.writeCharacters(PsiXml25Utils.LINE_BREAK);
        this.elementCache.clear();
    }

    protected abstract void initialiseSubWriters();

    protected void initialiseDefaultElementCache() {
        this.elementCache = new InMemoryIdentityObjectCache();
    }

    protected PsiXml25InteractionWriter<T> getInteractionWriter() {
        return interactionWriter;
    }

    protected PsiXml25InteractionWriter<ModelledInteraction> getComplexWriter() {
        return complexWriter;
    }

    protected PsiXml25ElementWriter<Source> getSourceWriter() {
        return sourceWriter;
    }

    protected PsiXml25ObjectCache getElementCache() {
        return elementCache;
    }

    protected void setSourceWriter(PsiXml25ElementWriter<Source> sourceWriter) {
        if (sourceWriter == null){
            throw new IllegalArgumentException("The source writer cannot be null");
        }
        this.sourceWriter = sourceWriter;
    }

    protected void setCurrentSource(Source currentSource) {
        this.currentSource = currentSource;
    }

    protected void setComplexWriter(PsiXml25InteractionWriter<ModelledInteraction> complexWriter) {
        if (complexWriter == null){
            throw new IllegalArgumentException("The Complex writer cannot be null");
        }
        this.complexWriter = complexWriter;
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

    protected List<T> getInteractionsToWrite() {
        return interactionsToWrite;
    }

    protected void setInteractionsToWrite(List<T> interactionsToWrite) {
        this.interactionsToWrite = interactionsToWrite;
    }

    protected void setElementCache(PsiXml25ObjectCache elementCache) {
        this.elementCache = elementCache;
    }

    protected void setInteractionWriter(PsiXml25InteractionWriter<T> interactionWriter) {
        if (interactionWriter == null){
            throw new IllegalArgumentException("The interaction writer cannot be null");
        }
        this.interactionWriter = interactionWriter;
    }

    protected XMLStreamWriter2 getStreamWriter() {
        return streamWriter;
    }

    protected void setStreamWriter(XMLStreamWriter2 streamWriter) {
        this.streamWriter = streamWriter;
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

    protected void setStarted(boolean started) {
        this.started = started;
    }

    private void initialiseWriter(Writer writer) throws XMLStreamException {
        if (writer == null){
            throw new IllegalArgumentException("The writer cannot be null.");
        }
        XMLOutputFactory outputFactory = XMLOutputFactory2.newInstance();
        this.streamWriter = (XMLStreamWriter2)outputFactory.createXMLStreamWriter(writer);
        initialiseSubWriters();
    }

    private void initialiseOutputStream(OutputStream output) throws XMLStreamException {
        if (output == null){
            throw new IllegalArgumentException("The output stream cannot be null.");
        }

        XMLOutputFactory outputFactory = XMLOutputFactory2.newInstance();
        this.streamWriter = (XMLStreamWriter2)outputFactory.createXMLStreamWriter(output, "UTF-8");
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
        this.streamWriter = (XMLStreamWriter2)outputFactory.createXMLStreamWriter(new FileOutputStream(file), "UTF-8");
        initialiseSubWriters();
    }
}
