package psidev.psi.mi.jami.enricher.listener.impl;

import psidev.psi.mi.jami.enricher.listener.CvTermEnricherListener;
import psidev.psi.mi.jami.enricher.listener.SourceEnricherListener;
import psidev.psi.mi.jami.model.Publication;
import psidev.psi.mi.jami.model.Source;

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
public class SourceEnricherListenerManager
    extends CvTermEnricherListenerManager<Source>
    implements SourceEnricherListener{

    /**
     * A constructor to create a listener manager with no listeners.
     */
    public SourceEnricherListenerManager(){}

    /**
     * A constructor to initiate a listener manager with as many listeners as required.
     * @param listeners     The listeners to add.
     */
    public SourceEnricherListenerManager(SourceEnricherListener... listeners){
        super(listeners);
    }

    //=============================================================================================================

    public void onUrlUpdate(Source cv, String oldUrl) {
        for(CvTermEnricherListener listener : getListenersList()){
            if (listener instanceof SourceEnricherListener){
                ((SourceEnricherListener)listener).onUrlUpdate(cv, oldUrl);
            }
        }
    }

    public void onPostalAddressUpdate(Source cv, String oldPostalAddress) {
        for(CvTermEnricherListener listener : getListenersList()){
            if (listener instanceof SourceEnricherListener){
                ((SourceEnricherListener)listener).onPostalAddressUpdate(cv, oldPostalAddress);
            }
        }
    }

    public void onPublicationUpdate(Source cv, Publication oldPublication) {
        for(CvTermEnricherListener listener : getListenersList()){
            if (listener instanceof SourceEnricherListener){
                ((SourceEnricherListener)listener).onPublicationUpdate(cv, oldPublication);
            }
        }
    }
}
