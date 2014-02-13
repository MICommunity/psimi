package psidev.psi.mi.jami.enricher.listener.impl;

import psidev.psi.mi.jami.enricher.listener.GeneEnricherListener;
import psidev.psi.mi.jami.enricher.listener.impl.InteractorEnricherListenerManager;
import psidev.psi.mi.jami.model.Gene;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 03/09/13
 */
public class GeneEnricherListenerManager
        extends InteractorEnricherListenerManager<Gene>
        implements GeneEnricherListener{


    /**
     * A constructor to create a listener manager with no listeners.
     */
    public GeneEnricherListenerManager(){}

    /**
     * A constructor to initiate a listener manager with as many listeners as required.
     * @param listeners     The listeners to add.
     */
    public GeneEnricherListenerManager(GeneEnricherListener... listeners){
        super(listeners);
    }
}
