package psidev.psi.mi.jami.xml.io.writer;

import org.codehaus.stax2.XMLOutputFactory2;
import org.codehaus.stax2.XMLStreamWriter2;
import psidev.psi.mi.jami.datasource.InteractionWriter;
import psidev.psi.mi.jami.exception.MIIOException;
import psidev.psi.mi.jami.factory.InteractionWriterFactory;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.xml.InMemoryIdentityObjectIndex;
import psidev.psi.mi.jami.xml.PsiXml25ObjectIndex;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXml25ElementWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.Xml25SourceWriter;
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
    private PsiXml25ElementWriter<Source> sourceWriter;
    private PsiXml25ElementWriter<String> availabilityWriter;
    private PsiXml25ElementWriter<Experiment> experimentWriter;
    private PsiXml25ElementWriter<Interactor> interactorWriter;
    private PsiXml25ElementWriter<Interaction> interactionWriter;
    private PsiXml25ElementWriter<Annotation> attributeWriter;
    private PsiXml25ElementWriter<ModelledInteraction> complexWriter;
    private PsiXml25ObjectIndex elementCache;

    public AbstractXml25Writer(){

    }

    public AbstractXml25Writer(File file) throws IOException, XMLStreamException {

        initialiseFile(file);
        isInitialised = true;
    }

    public AbstractXml25Writer(OutputStream output) throws XMLStreamException {

        initialiseOutputStream(output);
        isInitialised = true;
    }

    public AbstractXml25Writer(Writer writer) throws XMLStreamException {

        initialiseWriter(writer);
        isInitialised = true;
    }

    public void initialiseContext(Map<String, Object> options) {

        if (options == null && !isInitialised){
            throw new IllegalArgumentException("The options for the PSI-XML 2.5 writer should contain at least "+ InteractionWriterFactory.OUTPUT_OPTION_KEY + " to know where to write the interactions.");
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
            throw new IllegalArgumentException("The options for the PSI-XML 2.5 writer should contain at least "+ InteractionWriterFactory.OUTPUT_OPTION_KEY + " to know where to write the interactions.");
        }

        if (options.containsKey(PsiXml25Utils.ELEMENT_WITH_ID_CACHE_OPTION)){
            this.elementCache = (PsiXml25ObjectIndex)options.get(PsiXml25Utils.ELEMENT_WITH_ID_CACHE_OPTION);
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
            throw new IllegalStateException("The PSI-XML 2.5 writer was not initialised. The options for the PSI-XML 2.5 writer should contain at least "+ InteractionWriterFactory.OUTPUT_OPTION_KEY + " to know where to write the interactions.");
        }

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
            throw new IllegalStateException("The PSI-XML 2.5 writer was not initialised. The options for the PSI-XML 2.5 writer should contain at least "+ InteractionWriterFactory.OUTPUT_OPTION_KEY + " to know where to write the interactions.");
        }

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
            throw new IllegalStateException("The PSI-XML 2.5 writer was not initialised. The options for the PSI-XML 2.5 writer should contain at least "+ InteractionWriterFactory.OUTPUT_OPTION_KEY + " to know where to write the interactions.");
        }
        try {
            // write start entry
            writeStartEntry();
            // write interaction in teh entry
            writeInteractionInEntry(Collections.singleton(interaction).iterator());
            // write end entry
            writeEndEntry();
        } catch (XMLStreamException e) {
            throw new MIIOException("Cannot write interaction "+interaction.toString(), e);
        }
    }

    @Override
    public void write(Collection<? extends T> interactions) throws MIIOException {
        if (!isInitialised){
            throw new IllegalStateException("The PSI-XML 2.5 writer was not initialised. The options for the PSI-XML 2.5 writer should contain at least "+ InteractionWriterFactory.OUTPUT_OPTION_KEY + " to know where to write the interactions.");
        }
        try {
            // write start entry
            writeStartEntry();
            // write interaction in teh entry
            writeInteractionInEntry(interactions.iterator());
            // write end entry
            writeEndEntry();
        } catch (XMLStreamException e) {
            throw new MIIOException("Cannot write interactions ", e);
        }
    }

    @Override
    public void write(Iterator<? extends T> interactions) throws MIIOException {
        if (!isInitialised){
            throw new IllegalStateException("The PSI-XML 2.5 writer was not initialised. The options for the PSI-XML 2.5 writer should contain at least "+ InteractionWriterFactory.OUTPUT_OPTION_KEY + " to know where to write the interactions.");
        }
        try {
            // write start entry
            writeStartEntry();
            // write interaction in the entry
            writeInteractionInEntry(interactions);
            // write end entry
            writeEndEntry();
        } catch (XMLStreamException e) {
            throw new MIIOException("Cannot write interactions ", e);
        }
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
            }
        }
    }

    protected void writeInteractionInEntry(Iterator<? extends T> interaction) throws XMLStreamException {
        // write elements before
        writeTopEntryProperties(interaction);
        // write interaction list
        writeInteractionList(interaction);
        // write elements after
        writeFinalEntryProperties(interaction);
    }

    protected void writeInteractionList(Iterator<? extends T> interaction) throws XMLStreamException {
        // write start interaction list
        this.streamWriter.writeStartElement(PsiXml25Utils.INTERACTIONLIST_TAG);
        this.streamWriter.writeCharacters(PsiXml25Utils.LINE_BREAK);

        // write interaction
        while(interaction.hasNext()){
            this.interactionWriter.write(interaction.next());
            this.streamWriter.writeCharacters(PsiXml25Utils.LINE_BREAK);
        }

        // write sub complexes or eother modelled interactions used as interactor/reference
        while (this.elementCache.hasRegisteredSubComplexes()){
            Set<ModelledInteraction> subComplexes = this.elementCache.clearRegisteredComplexes();
            writeSubComplexInEntry(subComplexes.iterator());
        }

        // write end interaction list
        this.streamWriter.writeEndElement();
        this.streamWriter.writeCharacters(PsiXml25Utils.LINE_BREAK);
    }

    protected void writeSubComplexInEntry(Iterator<ModelledInteraction> interaction) throws XMLStreamException {
         while (interaction.hasNext()){
             this.complexWriter.write(interaction.next());
             this.streamWriter.writeCharacters(PsiXml25Utils.LINE_BREAK);
         }
    }

    protected abstract void writeFinalEntryProperties(Iterator<? extends T> interaction);

    protected abstract void writeTopEntryProperties(Iterator<? extends T> interaction);

    protected void writeStartEntry() throws XMLStreamException {
        this.streamWriter.writeStartElement(PsiXml25Utils.ENTRY_TAG);
        this.streamWriter.writeCharacters(PsiXml25Utils.LINE_BREAK);
    }

    protected void writeEndEntry() throws XMLStreamException {
        this.streamWriter.writeEndElement();
        this.streamWriter.writeCharacters(PsiXml25Utils.LINE_BREAK);
    }

    protected void initialiseSubWriters(){
        this.sourceWriter = new Xml25SourceWriter(this.streamWriter);
    }

    protected void initialiseDefaultElementCache() {
        this.elementCache = new InMemoryIdentityObjectIndex();
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
