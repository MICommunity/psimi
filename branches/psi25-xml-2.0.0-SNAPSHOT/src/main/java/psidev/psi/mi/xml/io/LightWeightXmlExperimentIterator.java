package psidev.psi.mi.xml.io;

import org.apache.commons.lang.exception.ExceptionUtils;
import psidev.psi.mi.xml.PsimiXmlReaderException;
import psidev.psi.mi.xml.events.InvalidXmlEvent;
import psidev.psi.mi.xml.events.MultipleHostOrganismsPerExperiment;
import psidev.psi.mi.xml.listeners.PsiXml25ParserListener;
import psidev.psi.mi.xml.model.ExperimentDescription;
import psidev.psi.mi.xml.model.Organism;
import psidev.psi.mi.xml.xmlindex.IndexedEntry;

import java.util.*;

/**
 * Light weight Iterator for Experiments within the xml
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>15/03/13</pre>
 */

public class LightWeightXmlExperimentIterator implements Iterator<ExperimentDescription> {

    private Iterator<IndexedEntry> indexedEntriesIterator;
    private Iterator<ExperimentDescription> experimentIterator;
    private ExperimentDescription nextExperiment;
    private IndexedEntry entry;
    private List<PsiXml25ParserListener> listeners;

    public LightWeightXmlExperimentIterator(List<IndexedEntry> indexedEntries, List<PsiXml25ParserListener> listeners){
        if (indexedEntries == null){
            throw new IllegalArgumentException("The indexed entries is mandatory and cannot be null");
        }
        this.indexedEntriesIterator = indexedEntries.iterator();
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
        while (this.indexedEntriesIterator.hasNext() && desc == null){
            entry = indexedEntriesIterator.next();
            try {
                experimentIterator = entry.unmarshallExperimentIterator();

                while (experimentIterator.hasNext() && desc == null){
                   desc = experimentIterator.next();
                }

            } catch (PsimiXmlReaderException e) {

                boolean errorInLine = true;
                do {

                    try {
                        InvalidXmlEvent evt = new InvalidXmlEvent("Error while reading the next entry. " + ExceptionUtils.getFullStackTrace(e));
                        evt.setColumnNumber(0);
                        evt.setLineNumber((int) entry.getEntryIndexElement().getLineNumber());
                        for (PsiXml25ParserListener l : listeners){
                            l.fireOnInvalidXmlSyntax(evt);
                        }

                        entry = indexedEntriesIterator.next();
                        experimentIterator = entry.unmarshallExperimentIterator();

                        while (experimentIterator.hasNext() && desc == null){
                            desc = experimentIterator.next();
                        }

                        errorInLine = false;
                    } catch (PsimiXmlReaderException e1) {
                        // skip the error
                    }

                } while (desc == null && this.indexedEntriesIterator.hasNext() && errorInLine);

                if (errorInLine && desc == null){
                    InvalidXmlEvent evt = new InvalidXmlEvent("Error while reading the next entry. " + ExceptionUtils.getFullStackTrace(e));
                    evt.setColumnNumber(0);
                    evt.setLineNumber((int) entry.getEntryIndexElement().getLineNumber());
                    for (PsiXml25ParserListener l : listeners){
                        l.fireOnInvalidXmlSyntax(evt);
                    }
                }
            }
        }

        this.nextExperiment = desc;

        // we have more than one host organism
        if (this.nextExperiment.getHostOrganisms().size() > 1){
            MultipleHostOrganismsPerExperiment evt = new MultipleHostOrganismsPerExperiment(desc, new HashSet<Organism>(desc.getHostOrganisms()), "Experiment " +desc.getId() + " contains more than one host organism.");
            evt.setColumnNumber(0);
            evt.setLineNumber((int) entry.getExperimentLineNumber(desc.getId()));
            for (PsiXml25ParserListener l : listeners){
                l.fireOnMultipleHostOrganismsPerExperimentEvent(evt);
            }
        }
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
