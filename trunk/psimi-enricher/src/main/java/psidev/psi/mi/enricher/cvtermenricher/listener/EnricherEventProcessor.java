package psidev.psi.mi.enricher.cvtermenricher.listener;

import psidev.psi.mi.enricher.cvtermenricher.listener.event.AdditionEvent;
import psidev.psi.mi.enricher.cvtermenricher.listener.event.OverwriteEvent;

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
    public void fireOverwriteEvent(OverwriteEvent e);
    public void fireAdditionEvent(AdditionEvent e) ;
    public void fireIgnoredEvent(String identity,String reason);
}

