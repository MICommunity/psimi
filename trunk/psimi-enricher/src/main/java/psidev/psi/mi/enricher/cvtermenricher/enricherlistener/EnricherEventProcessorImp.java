package psidev.psi.mi.enricher.cvtermenricher.enricherlistener;

import psidev.psi.mi.enricher.cvtermenricher.enricherlistener.event.AdditionEvent;
import psidev.psi.mi.enricher.cvtermenricher.enricherlistener.event.OverwriteEvent;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 13/05/13
 * Time: 11:12
 */
public class EnricherEventProcessorImp implements EnricherEventProcessor {

    ArrayList<EnricherListener> enricherListeners = new ArrayList<EnricherListener>();

    public void addEnricherListener(EnricherListener listener){
        enricherListeners.add(listener);
    }

    public void removeEnricherListener(EnricherListener listener){
        if(enricherListeners.contains(listener)){
            enricherListeners.remove(enricherListeners.indexOf(listener));
        }
    }

    public void fireOverwriteEvent(OverwriteEvent e) {
        for (EnricherListener il : enricherListeners) {
            il.overwriteEvent(e);
        }
    }

    public void fireAdditionEvent(AdditionEvent e) {
        for (EnricherListener il : enricherListeners) {
            il.additionEvent(e);
        }
    }

}
