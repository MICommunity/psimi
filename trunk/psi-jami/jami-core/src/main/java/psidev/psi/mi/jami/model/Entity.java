package psidev.psi.mi.jami.model;

import psidev.psi.mi.jami.listener.EntityInteractorChangeListener;

import java.util.Collection;

/**
 * An entity which gives more information to a specific molecule (such as features, causal relationships)
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>23/11/12</pre>
 */

public interface Entity<F extends Feature> {

    /**
     * The molecule/complex of molecules which interacts.
     * It cannot be null.
     * @return the interactor
     */
    public Interactor getInteractor();

    /**
     * Sets the interactor
     * @param interactor : interactor
     * @throws IllegalArgumentException when interactor is null
     */
    public void setInteractor(Interactor interactor);

    /**
     * The causal relationships of this entity on other entities usually part of an interaction.
     * The set of causal relationship cannot be null. If the entity does not have any causal relationship, the method should return an empty Collection.
     * Ex: increasing, decreasing, disrupting, etc.
     * @return the collection of causal relationships attached to this entity
     */
    public Collection<CausalRelationship> getCausalRelationships();

    /**
     * Properties for this entity.
     * The collection cannot be null. If the entity does not have any features, the method should return an empty collection.
     * @return the features
     */
    public <F2 extends F> Collection<F2> getFeatures();

    /**
     * The entity change listener if set, null otherwise.
     * The entity change listener listen to changes in entity (interactor changes)
     * @return the participant change listener
     */
    public EntityInteractorChangeListener getChangeListener();

    /**
     * Sets the entity change listener
     * @param listener
     */
    public void setChangeListener(EntityInteractorChangeListener listener);

    /**
     * This method will add the feature and set the entity of the new feature to this current entity
     * @param feature
     * @return true if feature is added to the list of features
     */
    public boolean  addFeature(F feature);

    /**
     * This method will remove the feature and set the entity of the removed feature to null.
     * @param feature
     * @return true if feature is removed from the list of features
     */
    public boolean removeFeature(F feature);

    /**
     * This method will add all features and set the entity of the new features to this current entity
     * @param features
     * @return true if features are added to the list of features
     */
    public boolean  addAllFeatures(Collection<? extends F> features);

    /**
     * This method will remove all the features and set the entity of the removed features to null.
     * @param features
     * @return true if features are removed from the list of features
     */
    public boolean removeAllFeatures(Collection<? extends F> features);

    /**
     * The stoichiometry for this participant.
     * If the stoichiometry for this participant is unknown, the method should return null.
     * @return the stoichiometry
     */
    public Stoichiometry getStoichiometry();

    /**
     * Sets the mean stoichiometry for this participant.
     * @param stoichiometry : mean stoichiometry value
     */
    public void setStoichiometry(Integer stoichiometry);

    /**
     * Sets the stoichiometry for this participant.
     * @param stoichiometry : the stoichiometry
     */
    public void setStoichiometry(Stoichiometry stoichiometry);
}
