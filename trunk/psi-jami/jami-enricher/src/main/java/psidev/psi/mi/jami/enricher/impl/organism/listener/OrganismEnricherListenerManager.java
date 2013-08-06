package psidev.psi.mi.jami.enricher.impl.organism.listener;



import psidev.psi.mi.jami.enricher.listener.EnricherListenerManager;
import psidev.psi.mi.jami.enricher.listener.EnrichmentStatus;
import psidev.psi.mi.jami.model.Alias;
import psidev.psi.mi.jami.model.Organism;



/**
 * A manager for listeners which holds a list of listeners.
 * Listener manager allows enrichers to send events to multiple listeners.
 * A listener itself, it implements all methods
 * which will then fire the corresponding method in each entry of the listener list.
 * No promise can be given to the order in which the listeners are fired.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 08/07/13
 */
public class OrganismEnricherListenerManager
        extends EnricherListenerManager<OrganismEnricherListener>
        implements OrganismEnricherListener{

    /**
     * A constructor to create a listener manager with no listeners.
     */
    public OrganismEnricherListenerManager(){}

    /**
     * A constructor to initiate a listener manager with as many listeners as required.
     * @param listeners     The listeners to add.
     */
    public OrganismEnricherListenerManager(OrganismEnricherListener... listeners){
        super(listeners);
    }

    //===================================================================================================

    public void onEnrichmentComplete(Organism organism, EnrichmentStatus status, String message) {
        for(OrganismEnricherListener listener : listenersList){
            listener.onEnrichmentComplete(organism, status, message) ;
        }
    }

    public void onCommonNameUpdate(Organism organism, String oldCommonName) {
        for(OrganismEnricherListener listener : listenersList){
            listener.onCommonNameUpdate(organism, oldCommonName);
        }
    }

    public void onScientificNameUpdate(Organism organism, String oldScientificName) {
        for(OrganismEnricherListener listener : listenersList){
            listener.onScientificNameUpdate(organism, oldScientificName);
        }
    }

    public void onTaxidUpdate(Organism organism, String oldTaxid) {
        for(OrganismEnricherListener listener : listenersList){
            listener.onTaxidUpdate(organism, oldTaxid);
        }
    }

    public void onAddedAlias(Organism organism, Alias added) {
        for(OrganismEnricherListener listener : listenersList){
            listener.onAddedAlias(organism, added);
        }
    }

    public void onRemovedAlias(Organism organism, Alias removed) {
        for(OrganismEnricherListener listener : listenersList){
            listener.onRemovedAlias(organism, removed);
        }
    }
}
