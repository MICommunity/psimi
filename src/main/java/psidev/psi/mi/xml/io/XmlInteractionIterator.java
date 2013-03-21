package psidev.psi.mi.xml.io;

import psidev.psi.mi.xml.listeners.PsiXml25ParserListener;
import psidev.psi.mi.xml.model.Entry;
import psidev.psi.mi.xml.model.EntrySet;
import psidev.psi.mi.xml.model.Interaction;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Xml iterator for interactions
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>18/03/13</pre>
 */

public class XmlInteractionIterator implements Iterator<Interaction> {

    private Iterator<Entry> entriesIterator;
    private Iterator<Interaction> interactionIterator;
    private Interaction nextInteraction;
    private List<PsiXml25ParserListener> listeners;

    public XmlInteractionIterator(EntrySet entrySet, List<PsiXml25ParserListener> listeners){
        if (entrySet == null){
            throw new IllegalArgumentException("The entrySet is mandatory and cannot be null");
        }
        this.entriesIterator = entrySet.getEntries().iterator();
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
        while (this.entriesIterator.hasNext() && desc == null){
            Entry entry = entriesIterator.next();
            interactionIterator = entry.getInteractions().iterator();

            while (interactionIterator.hasNext() && desc == null){
                desc = interactionIterator.next();
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
