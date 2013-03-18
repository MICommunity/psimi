package psidev.psi.mi.xml.io;

import psidev.psi.mi.xml.model.Entry;
import psidev.psi.mi.xml.model.EntrySet;
import psidev.psi.mi.xml.model.Interaction;
import psidev.psi.mi.xml.model.Interactor;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Xml iterator for interactors
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>18/03/13</pre>
 */

public class XmlInteractorIterator implements Iterator<Interactor> {

    private Iterator<Entry> entriesIterator;
    private Iterator<Interactor> interactorIterator;
    private Interactor nextInteractor;

    public XmlInteractorIterator(EntrySet entrySet){
        if (entrySet == null){
            throw new IllegalArgumentException("The entrySet is mandatory and cannot be null");
        }
        this.entriesIterator = entrySet.getEntries().iterator();

        processNextInteractor();
    }

    private void processNextInteractor(){
        Interactor desc = null;
        while (this.interactorIterator.hasNext() && desc == null){
            desc = this.interactorIterator.next();
        }
        while (this.entriesIterator.hasNext() && desc == null){
            Entry entry = entriesIterator.next();
            interactorIterator = entry.getInteractors().iterator();

            while (interactorIterator.hasNext() && desc == null){
                desc = interactorIterator.next();
            }
        }

        this.nextInteractor = desc;
    }

    public boolean hasNext() {
        return nextInteractor != null;
    }

    public Interactor next() {
        if (!hasNext()){
            throw new NoSuchElementException();
        }

        Interactor current = this.nextInteractor;
        processNextInteractor();
        return current;
    }

    public void remove() {
        throw new UnsupportedOperationException("Cannot remove experiment description");
    }
}