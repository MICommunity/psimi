package psidev.psi.mi.xml.io;

import org.apache.commons.lang.exception.ExceptionUtils;
import psidev.psi.mi.xml.PsimiXmlReaderException;
import psidev.psi.mi.xml.events.InvalidXmlEvent;
import psidev.psi.mi.xml.listeners.PsiXml25ParserListener;
import psidev.psi.mi.xml.model.Entry;
import psidev.psi.mi.xml.model.EntrySet;
import psidev.psi.mi.xml.model.ExperimentDescription;
import psidev.psi.mi.xml.xmlindex.IndexedEntry;

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

    public XmlExperimentIterator(EntrySet entrySet){
        if (entrySet == null){
            throw new IllegalArgumentException("The entrySet is mandatory and cannot be null");
        }
        this.entriesIterator = entrySet.getEntries().iterator();

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