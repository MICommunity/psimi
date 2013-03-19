package psidev.psi.mi.xml.io;

import org.apache.commons.lang.exception.ExceptionUtils;
import psidev.psi.mi.xml.PsimiXmlReaderException;
import psidev.psi.mi.xml.events.*;
import psidev.psi.mi.xml.listeners.PsiXml25ParserListener;
import psidev.psi.mi.xml.model.*;
import psidev.psi.mi.xml.xmlindex.IndexedEntry;

import java.util.*;

/**
 * Light weight Iterator for interaction XML elements
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>15/03/13</pre>
 */

public class LightWeightXmlInteractionIterator implements Iterator<Interaction> {

    private Iterator<IndexedEntry> indexedEntriesIterator;
    private Iterator<Interaction> interactionIterator;
    private Interaction nextInteraction;
    private List<PsiXml25ParserListener> listeners;
    private IndexedEntry entry;

    public LightWeightXmlInteractionIterator(List<IndexedEntry> indexedEntries, List<PsiXml25ParserListener> listeners){
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
            entry = indexedEntriesIterator.next();
            try {
                interactionIterator = entry.unmarshallInteractionIterator();

                while (interactionIterator.hasNext() && desc == null){
                    desc = interactionIterator.next();
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
                        interactionIterator = entry.unmarshallInteractionIterator();

                        while (interactionIterator.hasNext() && desc == null){
                            desc = interactionIterator.next();
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

        this.nextInteraction = desc;

        // we have more than one experiment
        if (this.nextInteraction.getExperiments().size() > 1){
            MultipleExperimentsPerInteractionEvent evt = new MultipleExperimentsPerInteractionEvent(desc, new HashSet<ExperimentDescription>(desc.getExperiments()), "Interaction " +desc.getId() + " contains more than one experiments.");
            evt.setColumnNumber(0);
            evt.setLineNumber((int) entry.getInteractionLineNumber(desc.getId()));
            for (PsiXml25ParserListener l : listeners){
                l.fireOnMultipleExperimentsPerInteractionEvent(evt);
            }
        }

        // we have more than one interaction types
        if (this.nextInteraction.getInteractionTypes().size() > 1){
            MultipleInteractionTypesEvent evt = new MultipleInteractionTypesEvent(desc, new HashSet<InteractionType>(desc.getInteractionTypes()), "Interaction " +desc.getId() + " contains more than one interaction types.");
            evt.setColumnNumber(0);
            evt.setLineNumber((int) entry.getInteractionLineNumber(desc.getId()));
            for (PsiXml25ParserListener l : listeners){
                l.fireOnMultipleInteractionTypesEvent(evt);
            }
        }

        for (Participant p : this.nextInteraction.getParticipants()){
            // we have more than one experimental role
            if (p.getExperimentalRoles().size() > 1){
                MultipleExperimentalRolesEvent evt = new MultipleExperimentalRolesEvent(desc, p, new HashSet<ExperimentalRole>(p.getExperimentalRoles()), "Participant "+p.getId()+" from the interaction " +desc.getId() + " contains more than one experimental roles.");
                evt.setColumnNumber(0);
                evt.setLineNumber((int) entry.getParticipantLineNumber(p.getId()));
                for (PsiXml25ParserListener l : listeners){
                    l.fireOnMultipleExperimentalRolesEvent(evt);
                }
            }

            // we have more than one expressed in
            if (p.getHostOrganisms().size() > 1){
                MultipleExpressedInOrganisms evt = new MultipleExpressedInOrganisms(desc, p, new HashSet<HostOrganism>(p.getHostOrganisms()), "Participant "+p.getId()+" from the interaction " +desc.getId() + " contains more than one host organisms.");
                evt.setColumnNumber(0);
                evt.setLineNumber((int) entry.getParticipantLineNumber(p.getId()));
                for (PsiXml25ParserListener l : listeners){
                    l.fireOnMultipleExpressedInOrganismsEvent(evt);
                }
            }

            // we have more than one identification methods
            if (p.getParticipantIdentificationMethods().size() > 1){
                MultipleParticipantIdentificationMethodsPerParticipant evt = new MultipleParticipantIdentificationMethodsPerParticipant(desc, p, new HashSet<ParticipantIdentificationMethod>(p.getParticipantIdentificationMethods()), "Participant "+p.getId()+" from the interaction " +desc.getId() + " contains more than one participant identification methods.");
                evt.setColumnNumber(0);
                evt.setLineNumber((int) entry.getParticipantLineNumber(p.getId()));
                for (PsiXml25ParserListener l : listeners){
                    l.fireOnMultipleParticipantIdentificationMethodsEvent(evt);
                }
            }
        }
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