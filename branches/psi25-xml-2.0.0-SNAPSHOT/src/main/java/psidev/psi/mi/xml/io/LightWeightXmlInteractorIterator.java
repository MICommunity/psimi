package psidev.psi.mi.xml.io;

import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.xml.PsimiXmlReaderException;
import psidev.psi.mi.xml.events.InvalidXmlEvent;
import psidev.psi.mi.xml.listeners.PsiXml25ParserListener;
import psidev.psi.mi.xml.model.Interactor;
import psidev.psi.mi.xml.xmlindex.IndexedEntry;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Light weight Iterator for interactor within the xml
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>15/03/13</pre>
 */

public class LightWeightXmlInteractorIterator implements Iterator<Interactor> {

    private Iterator<IndexedEntry> indexedEntriesIterator;
    private Iterator<Interactor> interactorIterator;
    private Interactor nextInteractor;
    private List<PsiXml25ParserListener> listeners;

    public LightWeightXmlInteractorIterator(List<IndexedEntry> indexedEntries, List<PsiXml25ParserListener> listeners){
        if (indexedEntries == null){
            throw new IllegalArgumentException("The indexed entries is mandatory and cannot be null");
        }
        this.indexedEntriesIterator = indexedEntries.iterator();
        if (listeners == null){
            this.listeners = Collections.EMPTY_LIST;
        }

        processNextInteractor();
    }

    private void processNextInteractor(){
        Interactor desc = null;
        while (this.interactorIterator.hasNext() && desc == null){
            desc = this.interactorIterator.next();
        }
        while (this.indexedEntriesIterator.hasNext() && desc == null){
            IndexedEntry entry = indexedEntriesIterator.next();
            try {
                interactorIterator = entry.unmarshallInteractorIterator();

                while (interactorIterator.hasNext() && desc == null){
                    desc = interactorIterator.next();
                }

            } catch (PsimiXmlReaderException e) {
                boolean errorInLine = true;
                do {

                    try {
                        InvalidXmlEvent evt = new InvalidXmlEvent("Error while reading the next entry. ", e);
                        if (e.getCurrentObject() instanceof FileSourceContext){
                            FileSourceContext context = (FileSourceContext) e.getCurrentObject();
                            evt.setSourceLocator(context.getSourceLocator());
                        }
                        for (PsiXml25ParserListener l : listeners){
                            l.fireOnInvalidXmlSyntax(evt);
                        }

                        entry = indexedEntriesIterator.next();
                        interactorIterator = entry.unmarshallInteractorIterator();

                        while (interactorIterator.hasNext() && desc == null){
                            desc = interactorIterator.next();
                        }

                        errorInLine = false;
                    } catch (PsimiXmlReaderException e1) {
                        // skip the error
                    }

                } while (desc == null && this.indexedEntriesIterator.hasNext() && errorInLine);

                if (errorInLine && desc == null){
                    InvalidXmlEvent evt = new InvalidXmlEvent("Error while reading the next entry. ", e);
                    if (e.getCurrentObject() instanceof FileSourceContext){
                        FileSourceContext context = (FileSourceContext) e.getCurrentObject();
                        evt.setSourceLocator(context.getSourceLocator());
                    }
                    for (PsiXml25ParserListener l : listeners){
                        l.fireOnInvalidXmlSyntax(evt);
                    }
                }
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
