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

    /**
     * This method will add the feature and set the participant of the new feature to this current participant
     * @param feature
     * @return true if feature is added to the list of features
     */
    public boolean  addModelledFeature(ModelledFeature feature);

    /**
     * This method will remove the feature and set the participant of the removed feature to null.
     * @param feature
     * @return true if feature is removed from the list of features
     */
    public boolean removeModelledFeature(ModelledFeature feature);
}
