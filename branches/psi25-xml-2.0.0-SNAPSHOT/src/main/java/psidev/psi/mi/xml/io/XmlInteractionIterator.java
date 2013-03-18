package psidev.psi.mi.xml.io;

import psidev.psi.mi.xml.events.*;
import psidev.psi.mi.xml.listeners.PsiXml25ParserListener;
import psidev.psi.mi.xml.model.*;

import java.util.*;

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

        // we have more than one experiment
        if (this.nextInteraction.getExperimentDescriptions().size() > 1){
            MultipleExperimentsPerInteractionEvent evt = new MultipleExperimentsPerInteractionEvent(desc, new HashSet<ExperimentDescription>(desc.getExperimentDescriptions()), "Interaction " +desc.getId() + " contains more than one experiments.");
            evt.setColumnNumber(0);
            evt.setLineNumber(0);
            for (PsiXml25ParserListener l : listeners){
                l.fireOnMultipleExperimentsPerInteractionEvent(evt);
            }
        }

        // we have more than one interaction types
        if (this.nextInteraction.getInteractionTypes().size() > 1){
            MultipleInteractionTypesEvent evt = new MultipleInteractionTypesEvent(desc, new HashSet<InteractionType>(desc.getInteractionTypes()), "Interaction " +desc.getId() + " contains more than one interaction types.");
            evt.setColumnNumber(0);
            evt.setLineNumber(0);
            for (PsiXml25ParserListener l : listeners){
                l.fireOnMultipleInteractionTypesEvent(evt);
            }
        }

        for (Participant p : this.nextInteraction.getParticipants()){
            // we have more than one experimental role
            if (p.getExperimentalRoles().size() > 1){
                MultipleExperimentalRolesEvent evt = new MultipleExperimentalRolesEvent(desc, p, new HashSet<ExperimentalRole>(p.getExperimentalRoles()), "Participant "+p.getId()+" from the interaction " +desc.getId() + " contains more than one experimental roles.");
                evt.setColumnNumber(0);
                evt.setLineNumber(0);
                for (PsiXml25ParserListener l : listeners){
                    l.fireOnMultipleExperimentalRolesEvent(evt);
                }
            }

            // we have more than one expressed in
            if (p.getHostOrganisms().size() > 1){
                MultipleExpressedInOrganisms evt = new MultipleExpressedInOrganisms(desc, p, new HashSet<HostOrganism>(p.getHostOrganisms()), "Participant "+p.getId()+" from the interaction " +desc.getId() + " contains more than one host organisms.");
                evt.setColumnNumber(0);
                evt.setLineNumber(0);
                for (PsiXml25ParserListener l : listeners){
                    l.fireOnMultipleExpressedInOrganismsEvent(evt);
                }
            }

            // we have more than one identification methods
            if (p.getParticipantIdentificationMethods().size() > 1){
                MultipleParticipantIdentificationMethodsPerParticipant evt = new MultipleParticipantIdentificationMethodsPerParticipant(desc, p, new HashSet<ParticipantIdentificationMethod>(p.getParticipantIdentificationMethods()), "Participant "+p.getId()+" from the interaction " +desc.getId() + " contains more than one participant identification methods.");
                evt.setColumnNumber(0);
                evt.setLineNumber(0);
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
