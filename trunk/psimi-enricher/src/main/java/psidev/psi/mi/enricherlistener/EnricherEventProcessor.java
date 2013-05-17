package psidev.psi.mi.enricherlistener;

import psidev.psi.mi.enricherlistener.event.AdditionEvent;
import psidev.psi.mi.enricherlistener.event.EnricherEvent;
import psidev.psi.mi.enricherlistener.event.MismatchEvent;
import psidev.psi.mi.enricherlistener.event.OverwriteEvent;

/**
 * An extension to allow the listening and firing of enrichment events.
 * Using this interface allows other methods to listen and receive reports.
 *
 * @author: Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 13/05/13
 * Time: 11:12
 */
public interface EnricherEventProcessor {
    /**
     * Adds a new EnricherListener to report to.
     * @param listener  A listener to report to.
     */
    public void addEnricherListener(EnricherListener listener);

    /**
     * Removes an EnricherListener
     * @param listener  A listener not to report to.
     */
    public void removeEnricherListener(EnricherListener listener);

    /**
     * Fires the enricherEvent with details of all changes made.
     * @param e The report of all events.
     */
    public void fireEnricherEvent(EnricherEvent e);

    /**
     * Adds information to the enricherEvent when something has been overwritten,
     * @param e A descritpion of what has been overwritten
     */
    public void addOverwriteEvent(OverwriteEvent e);

    /**
     * Adds information to the enricherEvent when something has been added.
     * @param e A description of what has been added
     */
    public void addAdditionEvent(AdditionEvent e) ;

    /**
     * Adds information to the enricherEvent when two fields do not match.
     * @param e A description of what mismatches
     */
    public void addMismatchEvent(MismatchEvent e);
}

