package psidev.psi.mi.jami.xml.io.parser;

import psidev.psi.mi.jami.binary.BinaryInteraction;
import psidev.psi.mi.jami.binary.expansion.ComplexExpansionMethod;
import psidev.psi.mi.jami.exception.MIIOException;
import psidev.psi.mi.jami.model.Interaction;
import psidev.psi.mi.jami.model.Participant;
import psidev.psi.mi.jami.xml.extension.factory.XmlBinaryInteractionFactory;

import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLStreamException;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * Abstract class for a binary interaction parser
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>17/10/13</pre>
 */

public abstract class AbstractPsixml25BinaryParser<T extends Interaction<? extends Participant>, B extends BinaryInteraction> extends AbstractPsiXml25Parser<T> {

    private ComplexExpansionMethod<T,B> expansionMethod;
    private Collection<B> binaryInteractions;
    private Iterator<B> binaryInteractionIterator;

    public AbstractPsixml25BinaryParser(File file, ComplexExpansionMethod<T, B> expansionMethod) throws FileNotFoundException, XMLStreamException, JAXBException {
        super(file);
        if (expansionMethod == null){
            throw new IllegalArgumentException("The complex expansion method is required.");
        }
        this.expansionMethod = expansionMethod;
        this.expansionMethod.setBinaryInteractionFactory(new XmlBinaryInteractionFactory());
        this.binaryInteractions = new ArrayList<B>();
    }

    public AbstractPsixml25BinaryParser(InputStream inputStream, ComplexExpansionMethod<T, B> expansionMethod) throws XMLStreamException, JAXBException {
        super(inputStream);
        if (expansionMethod == null){
            throw new IllegalArgumentException("The complex expansion method is required.");
        }
        this.expansionMethod = expansionMethod;
        this.expansionMethod.setBinaryInteractionFactory(new XmlBinaryInteractionFactory());
        this.binaryInteractions = new ArrayList<B>();
    }

    public AbstractPsixml25BinaryParser(URL url, ComplexExpansionMethod<T, B> expansionMethod) throws IOException, XMLStreamException, JAXBException {
        super(url);
        if (expansionMethod == null){
            throw new IllegalArgumentException("The complex expansion method is required.");
        }
        this.expansionMethod = expansionMethod;
        this.expansionMethod.setBinaryInteractionFactory(new XmlBinaryInteractionFactory());
        this.binaryInteractions = new ArrayList<B>();
    }

    public AbstractPsixml25BinaryParser(Reader reader, ComplexExpansionMethod<T, B> expansionMethod) throws XMLStreamException, JAXBException {
        super(reader);
        if (expansionMethod == null){
            throw new IllegalArgumentException("The complex expansion method is required.");
        }
        this.expansionMethod = expansionMethod;
        this.expansionMethod.setBinaryInteractionFactory(new XmlBinaryInteractionFactory());
        this.binaryInteractions = new ArrayList<B>();
    }

    @Override
    public T parseNextInteraction() throws IOException, XMLStreamException, JAXBException {
        // first look at loaded interaction
        if (this.binaryInteractionIterator != null && this.binaryInteractionIterator.hasNext()){
            return processAndRemoveNextBinary();
        }

        // parse normal interaction
        T interaction = super.parseNextInteraction();
        if (interaction == null || !this.expansionMethod.isInteractionExpandable(interaction)){
            return null;
        }

        // expand
        this.binaryInteractions.addAll(this.expansionMethod.expand(interaction));
        this.binaryInteractionIterator = this.binaryInteractions.iterator();
        if (this.binaryInteractionIterator.hasNext()){
            return processAndRemoveNextBinary();
        }
        else{
            return null;
        }
    }

    @Override
    public boolean hasFinished() {
        if (this.binaryInteractionIterator != null && this.binaryInteractionIterator.hasNext()){
            return true;
        }
        return super.hasFinished();
    }

    @Override
    public void reInit() throws MIIOException {
        this.binaryInteractionIterator = null;
        this.binaryInteractions.clear();
        super.reInit();
    }

    @Override
    public void close() {
        this.binaryInteractionIterator = null;
        this.binaryInteractions.clear();
        super.close();
    }

    private T processAndRemoveNextBinary() {
        B binary = this.binaryInteractionIterator.next();
        this.binaryInteractionIterator.remove();
        return (T)binary;
    }
}
