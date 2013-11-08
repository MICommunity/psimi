package psidev.psi.mi.jami.xml.io.parser;

import psidev.psi.mi.jami.datasource.DefaultFileSourceContext;
import psidev.psi.mi.jami.exception.MIIOException;
import psidev.psi.mi.jami.model.Interaction;
import psidev.psi.mi.jami.xml.*;
import psidev.psi.mi.jami.xml.exception.PsiXmlParserException;
import psidev.psi.mi.jami.xml.listener.PsiXmlParserListener;

import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLStreamException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import java.util.Iterator;
import java.util.logging.Logger;

/**
 * This class if for parser that will load the all file in memory
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>07/11/13</pre>
 */

public abstract class AbstractFullPsiXml25Parser<T extends Interaction> implements PsiXml25Parser<T>{
    private static final Logger logger = Logger.getLogger("AbstractFullPsiXml25Parser");

    private URL originalURL;
    private File originalFile;
    private InputStream originalStream;
    private Reader originalReader;
    private Unmarshaller unmarshaller;
    private Iterator<T> interactionIterator;
    private Iterator<AbstractEntry<T>> entryIterator;
    private PsiXmlParserListener listener;
    private AbstractEntrySet<AbstractEntry<T>> entrySet;

    private PsiXml25IdIndex indexOfObjects=null;
    private PsiXml25IdIndex indexOfComplexes=null;

    public final static String NAMESPACE_URI = "http://psi.hupo.org/mi/mif";

    private String currentElement;

    public AbstractFullPsiXml25Parser(File file) throws XMLStreamException, JAXBException {
        if (file == null){
            throw new IllegalArgumentException("The PsiXmlParser needs a non null File");
        }
        this.originalFile = file;
        this.unmarshaller = createJAXBUnmarshaller();
    }

    public AbstractFullPsiXml25Parser(InputStream inputStream) throws XMLStreamException, JAXBException {
        if (inputStream == null){
            throw new IllegalArgumentException("The PsiXmlParser needs a non null InputStream");
        }
        this.originalStream = inputStream;
        this.unmarshaller = createJAXBUnmarshaller();

    }

    public AbstractFullPsiXml25Parser(URL url) throws IOException, XMLStreamException, JAXBException {
        if (url == null){
            throw new IllegalArgumentException("The PsiXmlParser needs a non null URL");
        }
        this.originalURL = url;
        this.originalStream = url.openStream();
        this.unmarshaller = createJAXBUnmarshaller();
    }

    public AbstractFullPsiXml25Parser(Reader reader) throws XMLStreamException, JAXBException {
        if (reader == null){
            throw new IllegalArgumentException("The PsiXmlParser needs a non null Reader");
        }
        this.originalReader = reader;
        this.unmarshaller = createJAXBUnmarshaller();
    }

    @Override
    public T parseNextInteraction() throws PsiXmlParserException {
        // did not parse the entry set yet
        if (this.entrySet == null){
            initialiseEntryContext(XmlEntryContext.getInstance());
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
            T interaction = this.interactionIterator.next();
            // remove parsed interaction
            this.interactionIterator.remove();
            return interaction;
        }
        // parse interactions of the next entry
        else if (this.entryIterator.hasNext()){
            this.interactionIterator = this.entryIterator.next().getInteractions().iterator();
            if (this.interactionIterator.hasNext()){
                T interaction = this.interactionIterator.next();
                // remove parsed interaction
                this.interactionIterator.remove();
                return interaction;
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

        this.interactionIterator = null;
        this.entryIterator = null;
        this.unmarshaller = null;
        this.entrySet = null;
        this.indexOfComplexes = null;
        this.indexOfObjects = null;

        // release the thread local
        XmlEntryContext.getInstance().clear();
        XmlEntryContext.remove();
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
        if (this.indexOfObjects != null){
            this.indexOfObjects.clear();
        }
        if (this.indexOfComplexes != null){
            this.indexOfComplexes.clear();
        }
        // release the thread local
        XmlEntryContext.getInstance().clear();
        XmlEntryContext.remove();
        if (this.originalReader != null){
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

    /**
     *
     * @return the unmarshaller with the class context
     */
    protected abstract Unmarshaller createJAXBUnmarshaller() throws JAXBException;

    protected AbstractEntrySet<AbstractEntry<T>> parseEntrySet() throws PsiXmlParserException {
        if (originalFile != null){
            try {
                return (AbstractEntrySet<AbstractEntry<T>>) this.unmarshaller.unmarshal(this.originalFile);
            } catch (JAXBException e) {
                throw createPsiXmlExceptionFrom("Error parsing entrySet from a file.", e);
            }
        }
        else if (originalURL != null){
            try {
                return (AbstractEntrySet<AbstractEntry<T>>) this.unmarshaller.unmarshal(this.originalURL);
            } catch (JAXBException e) {
                throw createPsiXmlExceptionFrom("Error parsing entrySet from a URL.", e);
            }
        }
        else if (originalStream != null){
            try {
                return (AbstractEntrySet<AbstractEntry<T>>) this.unmarshaller.unmarshal(this.originalStream);
            } catch (JAXBException e) {
                throw createPsiXmlExceptionFrom("Error parsing entrySet from an InputStream.", e);
            }
        }
        else if (originalReader != null){
            try {
                return (AbstractEntrySet<AbstractEntry<T>>) this.unmarshaller.unmarshal(this.originalReader);
            } catch (JAXBException e) {
                throw createPsiXmlExceptionFrom("Error parsing entrySet from a Reader.", e);
            }
        }
        else{
            throw createPsiXmlExceptionFrom("Cannot parse entry set because does not have a file, URL, reader or inputstream to parse", null);
        }
    }

    protected PsiXmlParserException createPsiXmlExceptionFrom(String message, Exception e) {
        return new PsiXmlParserException(null, message, e);
    }

    private void initialiseEntryContext(XmlEntryContext entryContext) {
        entryContext.clear();
        entryContext.setListener(this.listener);
        if (this.indexOfComplexes == null){
            this.indexOfComplexes = new InMemoryPsiXml25Index();
        }
        if (this.indexOfObjects == null){
            this.indexOfObjects = new InMemoryPsiXml25Index();
        }
        entryContext.setMapOfReferencedComplexes(this.indexOfComplexes);
        entryContext.setMapOfReferencedObjects(this.indexOfObjects);
    }
}
