package psidev.psi.mi.jami.enricher.listener.impl;

import psidev.psi.mi.jami.enricher.listener.CvTermEnricherListener;
import psidev.psi.mi.jami.enricher.listener.OntologyTermEnricherListener;
import psidev.psi.mi.jami.model.OntologyTerm;

/**
 * A manager for listeners which holds a list of listeners.
 * Listener manager allows enrichers to send events to multiple listeners.
 * A listener itself, it implements all methods
 * which will then fire the corresponding method in each entry of the listener list.
 * No contract is given to the order in which the listeners are fired.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 08/07/13
 */
public class OntologyTermEnricherListenerManager
    extends CvTermEnricherListenerManager
    implements OntologyTermEnricherListener{

    /**
     * A constructor to create a listener manager with no listeners.
     */
    public OntologyTermEnricherListenerManager(){}

    /**
     * A constructor to initiate a listener manager with as many listeners as required.
     * @param listeners     The listeners to add.
     */
    public OntologyTermEnricherListenerManager(OntologyTermEnricherListener... listeners){
        super(listeners);
    }

    //=============================================================================================================

    public void onAddedParent(OntologyTerm o, OntologyTerm added) {
        for(CvTermEnricherListener listener : getListenersList()){
            if (listener instanceof OntologyTermEnricherListener){
                ((OntologyTermEnricherListener)listener).onAddedParent(o, added);
            }
        }
    }

    public void onRemovedParent(OntologyTerm o, OntologyTerm removed) {
        for(CvTermEnricherListener listener : getListenersList()){
            if (listener instanceof OntologyTermEnricherListener){
                ((OntologyTermEnricherListener)listener).onRemovedParent(o, removed);
            }
        }
    }

    public void onAddedChild(OntologyTerm o, OntologyTerm added) {
        for(CvTermEnricherListener listener : getListenersList()){
            if (listener instanceof OntologyTermEnricherListener){
                ((OntologyTermEnricherListener)listener).onAddedChild(o, added);
            }
        }
    }

    public void onRemovedChild(OntologyTerm o, OntologyTerm removed) {
        for(CvTermEnricherListener listener : getListenersList()){
            if (listener instanceof OntologyTermEnricherListener){
                ((OntologyTermEnricherListener)listener).onRemovedChild(o, removed);
            }
        }
    }
}
