package psidev.psi.mi.jami.enricher.listener.impl;

import psidev.psi.mi.jami.enricher.listener.ComplexEnricherListener;
import psidev.psi.mi.jami.enricher.listener.InteractionEnricherListener;
import psidev.psi.mi.jami.model.Alias;
import psidev.psi.mi.jami.model.Complex;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Organism;

/**
 * A manager for listeners which holds a list of listeners.
 * Listener manager allows enrichers to send events to multiple listeners.
 * A listener itself, it implements all methods
 * which will then fire the corresponding method in each entry of the listener list.
 * No promise can be given to the order in which the listeners are fired.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 09/07/13
 */
public class ComplexEnricherListenerManager
        extends ModelledInteractionEnricherListenerManager<Complex>
        implements ComplexEnricherListener{

    /**
     * A constructor to create a listener manager with no listeners.
     */
    public ComplexEnricherListenerManager(){ }

    /**
     * A constructor to initiate a listener manager with as many listeners as required.
     * @param listeners     The listeners to add.
     */
    public ComplexEnricherListenerManager(ComplexEnricherListener... listeners){
        super(listeners);
    }

    public void onFullNameUpdate(Complex interactor, String oldFullName) {
        for (InteractionEnricherListener listener : getListenersList()){
            if (listener instanceof ComplexEnricherListener){
                ((ComplexEnricherListener)listener).onFullNameUpdate(interactor, oldFullName);
            }
        }
    }

    public void onOrganismUpdate(Complex interactor, Organism oldOrganism) {
        for (InteractionEnricherListener listener : getListenersList()){
            if (listener instanceof ComplexEnricherListener){
                ((ComplexEnricherListener)listener).onOrganismUpdate(interactor, oldOrganism);
            }
        }
    }

    public void onInteractorTypeUpdate(Complex interactor, CvTerm oldType) {
        for (InteractionEnricherListener listener : getListenersList()){
            if (listener instanceof ComplexEnricherListener){
                ((ComplexEnricherListener)listener).onInteractorTypeUpdate(interactor, oldType);
            }
        }
    }

    public void onAddedAlias(Complex o, Alias added) {
        for (InteractionEnricherListener listener : getListenersList()){
            if (listener instanceof ComplexEnricherListener){
                ((ComplexEnricherListener)listener).onAddedAlias(o, added);
            }
        }
    }

    public void onRemovedAlias(Complex o, Alias removed) {
        for (InteractionEnricherListener listener : getListenersList()){
            if (listener instanceof ComplexEnricherListener){
                ((ComplexEnricherListener)listener).onRemovedAlias(o, removed);
            }
        }
    }

    //============================================================================================
}
