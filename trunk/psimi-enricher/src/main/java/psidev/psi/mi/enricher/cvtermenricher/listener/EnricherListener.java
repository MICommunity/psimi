package psidev.psi.mi.enricher.cvtermenricher.listener;

import psidev.psi.mi.enricher.cvtermenricher.listener.event.AdditionEvent;
import psidev.psi.mi.enricher.cvtermenricher.listener.event.OverwriteEvent;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 13/05/13
 * Time: 11:11
 */
public interface EnricherListener {

    public void overwriteEvent(OverwriteEvent e);

    public void additionEvent(AdditionEvent e);

    /**
     * If an object can not be updated for any reason.
     * @param identity
     */
    public void ignoredEvent(String identity, String reason);
}
