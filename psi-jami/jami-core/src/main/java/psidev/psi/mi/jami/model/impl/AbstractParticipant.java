package psidev.psi.mi.jami.model.impl;

import psidev.psi.mi.jami.model.*;

/**
 * TAbstract class for Participant
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>09/07/13</pre>
 */

public abstract class AbstractParticipant<I extends Interaction, F extends Feature> extends AbstractEntity<F> implements Participant<I,F> {
    private I interaction;

    public AbstractParticipant(Interactor interactor){
        super(interactor);
    }

    public AbstractParticipant(Interactor interactor, CvTerm bioRole){
        super(interactor, bioRole);
    }

    public AbstractParticipant(Interactor interactor, Stoichiometry stoichiometry){
        super(interactor, stoichiometry);
    }

    public AbstractParticipant(Interactor interactor, CvTerm bioRole, Stoichiometry stoichiometry){
        super(interactor, bioRole, stoichiometry);
    }

    public void setInteractionAndAddParticipant(I interaction) {

        if (this.interaction != null){
            this.interaction.removeParticipant(this);
        }

        if (interaction != null){
            interaction.addParticipant(this);
        }
    }

    public I getInteraction() {
        return this.interaction;
    }

    public void setInteraction(I interaction) {
        this.interaction = interaction;
    }

    @Override
    public String toString() {
        return getInteractor().toString() + " ( " + getInteractor().toString() + ")" + (getStoichiometry() != null ? ", stoichiometry: " + getStoichiometry().toString() : "");
    }
}
