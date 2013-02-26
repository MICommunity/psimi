package psidev.psi.mi.jami.model;

/**
 *  Participant of a modelled interaction.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>13/02/13</pre>
 */

public interface ModelledParticipant extends Participant<Interactor, ModelledFeature>{

    /**
     * Sets the complex and add the new component to its list of components
     * @param interaction : modelled interaction
     */
    public void setModelledInteractionAndAddModelledParticipant(ModelledInteraction interaction);

    /**
     * The interaction in which the participant is involved.
     * It can be null if the participant is not attached to any interactions. It can happen if the participant has been removed from an interaction and is now invalid.
     * @return the interaction
     */
    public ModelledInteraction getModelledInteraction();

    /**
     * Sets the interaction.
     * @param interaction : modelled interaction
     */
    public void setModelledInteraction(ModelledInteraction interaction);
}
