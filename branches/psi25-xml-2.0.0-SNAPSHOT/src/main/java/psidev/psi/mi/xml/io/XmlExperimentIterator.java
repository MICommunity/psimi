package psidev.psi.mi.xml.io;

import psidev.psi.mi.xml.listeners.PsiXml25ParserListener;
import psidev.psi.mi.xml.model.Entry;
import psidev.psi.mi.xml.model.EntrySet;
import psidev.psi.mi.xml.model.ExperimentDescription;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Xml experiment iterator
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>18/03/13</pre>
 */

public class XmlExperimentIterator implements Iterator<ExperimentDescription> {

    private Iterator<Entry> entriesIterator;
    private Iterator<ExperimentDescription> experimentIterator;
    private ExperimentDescription nextExperiment;
    private List<PsiXml25ParserListener> listeners;

    public XmlExperimentIterator(EntrySet entrySet, List<PsiXml25ParserListener> listeners){
        if (entrySet == null){
            throw new IllegalArgumentException("The entrySet is mandatory and cannot be null");
        }
        this.entriesIterator = entrySet.getEntries().iterator();
        if (listeners == null){
            this.listeners = Collections.EMPTY_LIST;
        }
        processNextExperiment();
    }

    private void processNextExperiment(){
        ExperimentDescription desc = null;
        while (this.experimentIterator.hasNext() && desc == null){
            desc = this.experimentIterator.next();
        }
        while (this.entriesIterator.hasNext() && desc == null){
            Entry entry = entriesIterator.next();
            experimentIterator = entry.getExperiments().iterator();

            while (experimentIterator.hasNext() && desc == null){
                desc = experimentIterator.next();
            }
        }

        this.nextExperiment = desc;
    }

    public boolean hasNext() {
        return nextExperiment != null;
    }

    public ExperimentDescription next() {
        if (!hasNext()){
            throw new NoSuchElementException();
        }

        ExperimentDescription current = this.nextExperiment;
        processNextExperiment();
        return current;
    }

    public void remove() {
        throw new UnsupportedOperationException("Cannot remove experiment description");
    }
}