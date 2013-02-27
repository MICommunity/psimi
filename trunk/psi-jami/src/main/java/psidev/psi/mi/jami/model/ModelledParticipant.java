package psidev.psi.mi.jami.model;

import java.util.Collection;

/**
 *  Participant of a modelled interaction.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>13/02/13</pre>
 */

public interface ModelledParticipant extends Participant<Interactor>{

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
     * Properties for this participant coming from multiple experimental evidences and that are now modelled.
     * The collection cannot be null. If the participant does not have any features, the method should return an empty collection.
     * @return the features
     */
    public Collection<ModelledFeature> getModelledFeatures();

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

    /**
     * This method will add all features and set the participant of the new features to this current participant
     * @param features
     * @return true if features are added to the list of features
     */
    public boolean  addAllModelledFeatures(Collection<? extends ModelledFeature> features);

    /**
     * This method will remove all the features and set the participant of the removed features to null.
     * @param features
     * @return true if features are removed from the list of features
     */
    public boolean removeAllModelledFeatures(Collection<? extends ModelledFeature> features);
}
