package psidev.psi.mi.jami.enricher.listener;

import psidev.psi.mi.jami.enricher.event.*;
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
     * @param r The report of all events.
     */
    public void fireEnricherEvent(EnricherEvent r);

    /**
     * Adds a subset of events which took place in a separate enricher.
     * @param e
     */
    public void addSubEnricherEvent(EnricherEvent e);

    /**
     * Adds information to the enricherEvent when something has been overwritten,
     * @param r A descritpion of what has been overwritten
     */
    public void addOverwriteReport(OverwriteReport r);

    /**
     * Adds information to the enricherEvent when something has been added.
     * @param r A description of what has been added
     */
    public void addAdditionReport(AdditionReport r) ;

    /**
     * Adds information to the enricherEvent when two fields do not match.
     * @param r A description of what mismatches
     */
    public void addMismatchReport(MismatchReport r);
}

