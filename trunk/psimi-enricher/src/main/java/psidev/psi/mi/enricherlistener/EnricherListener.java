package psidev.psi.mi.enricherlistener;


import psidev.psi.mi.enricherlistener.event.EnricherEvent;

/**
 * The methods which must be implemented by an EnricherListener.
 *
 * @author: Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 13/05/13
 * Time: 11:11
 */
public interface EnricherListener {

    /**
     * Fires at the end of an enrichment and contains a log of all events
     * @param e
     */
    public void enricherEvent(EnricherEvent e);


}
