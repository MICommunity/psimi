package psidev.psi.mi.jami.model.impl;

import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.utils.comparator.participant.UnambiguousExactModelledParticipantComparator;

/**
 * Default implementation for ModelledParticipant
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>13/02/13</pre>
 */

public class DefaultModelledParticipant extends DefaultParticipant<Interactor, ModelledFeature> implements ModelledParticipant {

    private ModelledInteraction modelledInteraction;

    public DefaultModelledParticipant(ModelledInteraction interaction, Interactor interactor) {
        super(interactor);
        this.modelledInteraction = interaction;
    }

    public DefaultModelledParticipant(ModelledInteraction interaction, Interactor interactor, CvTerm bioRole) {
        super(interactor, bioRole);
        this.modelledInteraction = interaction;
    }

    public DefaultModelledParticipant(ModelledInteraction interaction, Interactor interactor, Integer stoichiometry) {
        super(interactor, stoichiometry);
        this.modelledInteraction = interaction;
    }

    public DefaultModelledParticipant(ModelledInteraction interaction, Interactor interactor, CvTerm bioRole, Integer stoichiometry) {
        super(interactor, bioRole, stoichiometry);
        this.modelledInteraction = interaction;
    }

    public DefaultModelledParticipant(Interactor interactor) {
        super(interactor);
    }

    public DefaultModelledParticipant(Interactor interactor, CvTerm bioRole) {
        super(interactor, bioRole);
    }

    public DefaultModelledParticipant(Interactor interactor, Integer stoichiometry) {
        super(interactor, stoichiometry);
    }

    public DefaultModelledParticipant(Interactor interactor, CvTerm bioRole, Integer stoichiometry) {
        super(interactor, bioRole, stoichiometry);
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

        if (interaction != null){
            modelledInteraction.addParticipant(this);
        }
        else {
            this.modelledInteraction =null;
        }
    }

    public ModelledInteraction getModelledInteraction() {
        return this.modelledInteraction;
    }

    public void setModelledInteraction(ModelledInteraction interaction) {
        this.modelledInteraction = interaction;
    }

    @Override
    public boolean addFeature(ModelledFeature feature) {

        if (super.addFeature(feature)){
            feature.setModelledParticipant(this);
            return true;
        }
        return false;
    }

    @Override
    public boolean removeFeature(ModelledFeature feature) {

        if (super.removeFeature(feature)){
            feature.setModelledParticipant(null);
            return true;
        }
        return false;
    }
}
