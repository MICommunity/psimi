package psidev.psi.mi.jami.xml.io.parser;

import psidev.psi.mi.jami.binary.BinaryInteraction;
import psidev.psi.mi.jami.binary.expansion.ComplexExpansionMethod;
import psidev.psi.mi.jami.exception.MIIOException;
import psidev.psi.mi.jami.model.Interaction;
import psidev.psi.mi.jami.model.Participant;
import psidev.psi.mi.jami.xml.cache.PsiXml25IdCache;
import psidev.psi.mi.jami.xml.exception.PsiXmlParserException;
import psidev.psi.mi.jami.xml.extension.factory.XmlBinaryInteractionFactory;
import psidev.psi.mi.jami.xml.listener.PsiXmlParserListener;

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

public abstract class AbstractPsixml25BinaryParser<T extends Interaction<? extends Participant>, B extends BinaryInteraction> implements PsiXml25Parser<B>{

    private ComplexExpansionMethod<T,B> expansionMethod;
    private Collection<B> binaryInteractions;
    private Iterator<B> binaryInteractionIterator;
    private PsiXml25Parser<T> delegateParser;

    public AbstractPsixml25BinaryParser(PsiXml25Parser<T> delegateParser) {
        if (delegateParser == null){
           throw new IllegalArgumentException("An AbstractPsiXml25Parser is required to parse Xml interactions");
        }
        this.delegateParser = delegateParser;
        this.binaryInteractions = new ArrayList<B>();
    }

    @Override
    public B parseNextInteraction() throws PsiXmlParserException {
        // first look at loaded interaction
        if (this.binaryInteractionIterator != null && this.binaryInteractionIterator.hasNext()){
            return processAndRemoveNextBinary();
        }

        // parse normal interaction
        T interaction = this.delegateParser.parseNextInteraction();
        if (interaction == null || !getExpansionMethod().isInteractionExpandable(interaction)){
            return null;
        }

        // expand
        this.binaryInteractions.addAll(getExpansionMethod().expand(interaction));
        this.binaryInteractionIterator = this.binaryInteractions.iterator();
        if (this.binaryInteractionIterator.hasNext()){
            return processAndRemoveNextBinary();
        }
        else{
            return null;
        }
    }

    @Override
    public boolean hasFinished() throws PsiXmlParserException {
        if (this.binaryInteractionIterator != null && this.binaryInteractionIterator.hasNext()){
            return false;
        }
        return delegateParser.hasFinished();
    }

    @Override
    public void reInit() throws MIIOException {
        this.binaryInteractionIterator = null;
        this.binaryInteractions.clear();
        this.delegateParser.reInit();
    }

    @Override
    public void close() {
        this.binaryInteractionIterator = null;
        this.binaryInteractions.clear();
        this.delegateParser.close();
    }

    @Override
    public PsiXmlParserListener getListener() {
        return delegateParser.getListener();
    }

    @Override
    public void setListener(PsiXmlParserListener listener) {
        delegateParser.getListener();
    }

    public void setExpansionMethod(ComplexExpansionMethod<T, B> expansionMethod) {
        this.expansionMethod = expansionMethod;
    }

    public void setCacheOfObjects(PsiXml25IdCache indexOfObjects) {
        this.delegateParser.setCacheOfObjects(indexOfObjects);
    }

    protected ComplexExpansionMethod<T,B> getExpansionMethod(){
        if (expansionMethod == null){
            this.expansionMethod = initialiseDefaultExpansionMethod();
            this.expansionMethod.setBinaryInteractionFactory(new XmlBinaryInteractionFactory());
        }
        return this.expansionMethod;
    }

    protected abstract ComplexExpansionMethod<T,B> initialiseDefaultExpansionMethod();

    protected PsiXml25Parser<T> getDelegateParser() {
        return delegateParser;
    }

    private B processAndRemoveNextBinary() {
        B binary = this.binaryInteractionIterator.next();
        this.binaryInteractionIterator.remove();
        return binary;
    }
}
