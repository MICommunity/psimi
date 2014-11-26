package psidev.psi.mi.jami.enricher.listener.impl;


import psidev.psi.mi.jami.enricher.listener.EntityEnricherListener;
import psidev.psi.mi.jami.enricher.listener.ParticipantEvidenceEnricherListener;
import psidev.psi.mi.jami.model.*;

/**
 * A manager for listeners which holds a list of listeners.
 * Listener manager allows enrichers to send events to multiple listeners.
 * A listener itself, it implements all methods
 * which will then fire the corresponding method in each entry of the listener list.
 * No promise can be given to the order in which the listeners are fired.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 10/07/13
 */
public class ParticipantEvidenceEnricherListenerManager<P extends ParticipantEvidence>
        extends ParticipantEnricherListenerManager<P>
        implements ParticipantEvidenceEnricherListener<P>{

    /**
     * A constructor to create a listener manager with no listeners.
     */
    public ParticipantEvidenceEnricherListenerManager(){ }

    /**
     * A constructor to initiate a listener manager with as many listeners as required.
     * @param listeners     The listeners to add.
     */
    public ParticipantEvidenceEnricherListenerManager(ParticipantEvidenceEnricherListener<P>... listeners){
        super(listeners);
    }

    public void onExperimentalRoleUpdate(P participant, CvTerm oldType) {
        for(EntityEnricherListener listener : getListenersList()){
            if (listener instanceof ParticipantEvidenceEnricherListener){
                ((ParticipantEvidenceEnricherListener)listener).onExperimentalRoleUpdate(participant, oldType);
            }
        }
    }

    public void onExpressedInUpdate(P participant, Organism oldOrganism) {
        for(EntityEnricherListener listener : getListenersList()){
            if (listener instanceof ParticipantEvidenceEnricherListener){
                ((ParticipantEvidenceEnricherListener)listener).onExpressedInUpdate(participant, oldOrganism);
            }
        }
    }

    public void onAddedIdentificationMethod(P participant, CvTerm added) {
        for(EntityEnricherListener listener : getListenersList()){
            if (listener instanceof ParticipantEvidenceEnricherListener){
                ((ParticipantEvidenceEnricherListener)listener).onAddedIdentificationMethod(participant, added);
            }
        }
    }

    public void onRemovedIdentificationMethod(P participant, CvTerm removed) {
        for(EntityEnricherListener listener : getListenersList()){
            if (listener instanceof ParticipantEvidenceEnricherListener){
                ((ParticipantEvidenceEnricherListener)listener).onRemovedIdentificationMethod(participant, removed);
            }
        }
    }

    public void onAddedExperimentalPreparation(P participant, CvTerm added) {
        for(EntityEnricherListener listener : getListenersList()){
            if (listener instanceof ParticipantEvidenceEnricherListener){
                ((ParticipantEvidenceEnricherListener)listener).onAddedExperimentalPreparation(participant, added);
            }
        }
    }

    public void onRemovedExperimentalPreparation(P participant, CvTerm removed) {
        for(EntityEnricherListener listener : getListenersList()){
            if (listener instanceof ParticipantEvidenceEnricherListener){
                ((ParticipantEvidenceEnricherListener)listener).onRemovedExperimentalPreparation(participant, removed);
            }
        }
    }

    public void onAddedConfidence(P o, Confidence added) {
        for(EntityEnricherListener listener : getListenersList()){
            if (listener instanceof ParticipantEvidenceEnricherListener){
                ((ParticipantEvidenceEnricherListener)listener).onAddedConfidence(o, added);
            }
        }
    }

    public void onRemovedConfidence(P o, Confidence removed) {
        for(EntityEnricherListener listener : getListenersList()){
            if (listener instanceof ParticipantEvidenceEnricherListener){
                ((ParticipantEvidenceEnricherListener)listener).onRemovedConfidence(o, removed);
            }
        }
    }

    public void onAddedParameter(P o, Parameter added) {
        for(EntityEnricherListener listener : getListenersList()){
            if (listener instanceof ParticipantEvidenceEnricherListener){
                ((ParticipantEvidenceEnricherListener)listener).onAddedParameter(o, added);
            }
        }
    }

    public void onRemovedParameter(P o, Parameter removed) {
        for(EntityEnricherListener listener : getListenersList()){
            if (listener instanceof ParticipantEvidenceEnricherListener){
                ((ParticipantEvidenceEnricherListener)listener).onRemovedParameter(o, removed);
            }
        }
    }


    //============================================================================================
}
