package psidev.psi.mi.jami.model;

import java.util.Collection;

/**
 * Participant of a biological complex.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>23/11/12</pre>
 */
public interface Component extends Participant<Interactor>{

    /**
     * Sets the complex and add the new component to its list of components
     * @param interaction : complex
     */
    public void setComplexAndAddComponent(Complex interaction);

    /**
     * The interaction in which the participant is involved.
     * It can be null if the participant is not attached to any interactions. It can happen if the participant has been removed from an interaction and is now invalid.
     * @return the interaction
     */
    public Complex getComplex();

    /**
     * Sets the interaction.
     * @param interaction : complex
     */
    public void setComplex(Complex interaction);

    /**
     * Properties for this participant.
     * The collection cannot be null. If the participant does not have any features, the method should return an empty collection.
     * @return the features
     */
    public Collection<? extends ComponentFeature> getComponentFeatures();

    /**
     * This method will add the feature and set the participant of the new feature to this current participant
     * @param feature
     * @return true if feature is added to the list of features
     */
    public boolean  addComponentFeature(ComponentFeature feature);

    /**
     * This method will remove the feature and set the participant of the removed feature to null.
     * @param feature
     * @return true if feature is removed from the list of features
     */
    public boolean removeComponentFeature(ComponentFeature feature);

    /**
     * This method will add all features and set the participant of the new features to this current participant
     * @param features
     * @return true if features are added to the list of features
     */
    public boolean  addAllComponentFeatures(Collection<? extends ComponentFeature> features);

    /**
     * This method will remove all the features and set the participant of the removed features to null.
     * @param features
     * @return true if features are removed from the list of features
     */
    public boolean removeAllComponentFeatures(Collection<? extends ComponentFeature> features);
}
