package psidev.psi.mi.jami.model.impl;

import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.utils.comparator.participant.UnambiguousExactModelledParticipantComparator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * Default implementation for ModelledParticipant
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>13/02/13</pre>
 */

public class DefaultModelledParticipant extends DefaultParticipant<Interactor> implements ModelledParticipant {

    private ModelledInteraction modelledInteraction;
    private Collection<ModelledFeature> modelledFeatures;

    public DefaultModelledParticipant(ModelledInteraction interaction, Interactor interactor) {
        super(interactor);
        this.modelledInteraction = interaction;
    }

    public DefaultModelledParticipant(ModelledInteraction interaction, Interactor interactor, CvTerm bioRole) {
        super(interactor, bioRole);
        this.modelledInteraction = interaction;
    }

    public DefaultModelledParticipant(ModelledInteraction interaction, Interactor interactor, Stoichiometry stoichiometry) {
        super(interactor, stoichiometry);
        this.modelledInteraction = interaction;
    }

    public DefaultModelledParticipant(ModelledInteraction interaction, Interactor interactor, CvTerm bioRole, Stoichiometry stoichiometry) {
        super(interactor, bioRole, stoichiometry);
        this.modelledInteraction = interaction;
    }

    public DefaultModelledParticipant(Interactor interactor) {
        super(interactor);
    }

    public DefaultModelledParticipant(Interactor interactor, CvTerm bioRole) {
        super(interactor, bioRole);
    }

    public DefaultModelledParticipant(Interactor interactor, Stoichiometry stoichiometry) {
        super(interactor, stoichiometry);
    }

    public DefaultModelledParticipant(Interactor interactor, CvTerm bioRole, Stoichiometry stoichiometry) {
        super(interactor, bioRole, stoichiometry);
    }

    protected void initialiseModelledFeatures(){
        this.modelledFeatures = new ArrayList<ModelledFeature>();
    }

    protected void initialiseModelledFeaturesWith(Collection<ModelledFeature> features){
        if (features == null){
            this.modelledFeatures = Collections.EMPTY_LIST;
        }
        else {
            this.modelledFeatures = features;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }

        if (!(o instanceof ModelledParticipant)){
            return false;
        }

        // use UnambiguousExactBiologicalParticipant comparator for equals
        return UnambiguousExactModelledParticipantComparator.areEquals(this, (ModelledParticipant) o);
    }

    public void setModelledInteractionAndAddModelledParticipant(ModelledInteraction interaction) {

        if (this.modelledInteraction != null){
            this.modelledInteraction.removeModelledParticipant(this);
        }

        if (interaction != null){
            modelledInteraction.addModelledParticipant(this);
        }
    }

    public ModelledInteraction getModelledInteraction() {
        return this.modelledInteraction;
    }

    public void setModelledInteraction(ModelledInteraction interaction) {
        this.modelledInteraction = interaction;
    }

    public Collection<ModelledFeature> getModelledFeatures() {
        if (modelledFeatures == null){
            initialiseModelledFeatures();
        }
        return modelledFeatures;
    }

    public boolean addModelledFeature(ModelledFeature feature) {

        if (feature == null){
            return false;
        }
        if (modelledFeatures == null){
            initialiseModelledFeatures();
        }
        if (modelledFeatures.add(feature)){
            feature.setModelledParticipant(this);
            return true;
        }
        return false;
    }

    public boolean removeModelledFeature(ModelledFeature feature) {

        if (feature == null){
            return false;
        }
        if (modelledFeatures == null){
            initialiseModelledFeatures();
        }
        if (modelledFeatures.remove(feature)){
            feature.setModelledParticipant(null);
            return true;
        }
        return false;
    }

    public boolean addAllModelledFeatures(Collection<? extends ModelledFeature> features) {
        if (features == null){
            return false;
        }

        boolean added = false;
        for (ModelledFeature feature : features){
            if (addModelledFeature(feature)){
                added = true;
            }
        }
        return added;
    }

    public boolean removeAllModelledFeatures(Collection<? extends ModelledFeature> features) {
        if (features == null){
            return false;
        }

        boolean added = false;
        for (ModelledFeature feature : features){
            if (removeModelledFeature(feature)){
                added = true;
            }
        }
        return added;
    }
}
