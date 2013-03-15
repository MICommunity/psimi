package psidev.psi.mi.xml.io;

import org.apache.commons.lang.exception.ExceptionUtils;
import psidev.psi.mi.xml.PsimiXmlReaderException;
import psidev.psi.mi.xml.events.InvalidXmlEvent;
import psidev.psi.mi.xml.listeners.PsiXml25ParserListener;
import psidev.psi.mi.xml.model.Interaction;
import psidev.psi.mi.xml.xmlindex.IndexedEntry;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Iterator for interaction XML elements
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>15/03/13</pre>
 */

public class XmlInteractionIterator implements Iterator<Interaction> {

    private Iterator<IndexedEntry> indexedEntriesIterator;
    private Iterator<Interaction> interactionIterator;
    private Interaction nextInteraction;
    private List<PsiXml25ParserListener> listeners;

    public XmlInteractionIterator(List<IndexedEntry> indexedEntries, List<PsiXml25ParserListener> listeners){
        if (indexedEntries == null){
            throw new IllegalArgumentException("The indexed entries is mandatory and cannot be null");
        }
        this.indexedEntriesIterator = indexedEntries.iterator();
        if (listeners == null){
            this.listeners = Collections.EMPTY_LIST;
        }

        processNextInteraction();
    }

    private void processNextInteraction(){
        Interaction desc = null;
        while (this.interactionIterator.hasNext() && desc == null){
            desc = this.interactionIterator.next();
        }
        while (this.indexedEntriesIterator.hasNext() && desc == null){
            IndexedEntry entry = indexedEntriesIterator.next();
            try {
                interactionIterator = entry.unmarshallInteractionIterator();

                while (interactionIterator.hasNext() && desc == null){
                    desc = interactionIterator.next();
                }

            } catch (PsimiXmlReaderException e) {
                InvalidXmlEvent evt = new InvalidXmlEvent("Error while reading the next entry. " + ExceptionUtils.getFullStackTrace(e));
                evt.setColumnNumber(0);
                evt.setLineNumber((int) entry.getEntryIndexElement().getLineNumber());
                for (PsiXml25ParserListener l : listeners){
                    l.fireOnInvalidXmlSyntax(evt);
                }
            }
        }

        this.nextInteraction = desc;
    }

    public boolean hasNext() {
        return nextInteraction != null;
    }

    public Interaction next() {
        if (!hasNext()){
            throw new NoSuchElementException();
        }

        Interaction current = this.nextInteraction;
        processNextInteraction();
        return current;
    }

    public void remove() {
        throw new UnsupportedOperationException("Cannot remove experiment description");
    }
}