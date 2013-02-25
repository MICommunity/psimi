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

public class DefaultModelledParticipant extends DefaultParticipant<ModelledInteraction, Interactor, ModelledFeature> implements ModelledParticipant {


    public DefaultModelledParticipant(ModelledInteraction interaction, Interactor interactor) {
        super(interaction, interactor);
    }

    public DefaultModelledParticipant(ModelledInteraction interaction, Interactor interactor, CvTerm bioRole) {
        super(interaction, interactor, bioRole);
    }

    public DefaultModelledParticipant(ModelledInteraction interaction, Interactor interactor, Integer stoichiometry) {
        super(interaction, interactor, stoichiometry);
    }

    public DefaultModelledParticipant(ModelledInteraction interaction, Interactor interactor, CvTerm bioRole, Integer stoichiometry) {
        super(interaction, interactor, bioRole, stoichiometry);
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
        setInteraction(interaction);

        if (interaction != null){
            getInteraction().addParticipant(this);
        }
        else {
            setInteraction(null);
        }
    }
}
