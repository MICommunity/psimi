package psidev.psi.mi.enricherlistener;

import psidev.psi.mi.enricherlistener.event.AdditionEvent;
import psidev.psi.mi.enricherlistener.event.EnricherEvent;
import psidev.psi.mi.enricherlistener.event.MismatchEvent;
import psidev.psi.mi.enricherlistener.event.OverwriteEvent;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 13/05/13
 * Time: 11:12
 */
public interface EnricherEventProcessor {
    public void addEnricherListener(EnricherListener listener);
    public void removeEnricherListener(EnricherListener listener);

    public void fireEnricherEvent(EnricherEvent enricherEvent);

    public void addOverwriteEvent(OverwriteEvent e);
    public void addAdditionEvent(AdditionEvent e) ;
    public void addMismatchEvent(MismatchEvent e);
}

