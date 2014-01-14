package psidev.psi.mi.jami.model;

import psidev.psi.mi.jami.listener.ParticipantInteractorChangeListener;

import java.util.Collection;

/**
 * An entity is an interactor having several features/annotations/xrefs in a special context/environment (example an interactor within an interactor set or interaction or complex)
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>03/10/13</pre>
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
     * The biological role of the participant.
     * It is a controlled vocabulary term and cannot be null.
     * It the biological role of a participant is not known or not relevant, the method should return
     * unspecified role (MI:0499)
     * Ex: enzyme, enzyme target, ...
     * @return the biological role
     */
    public CvTerm getBiologicalRole();

    /**
     * Sets the biological role.
     * If the bioRole is null, should create a bioRole
     * @param bioRole : biological role unspecified role (MI:0499)
     */
    public void setBiologicalRole(CvTerm bioRole);

    /**
     * The causal relationships of this participant on other participants of the interaction.
     * The set of causal relationship cannot be null. If the participant does not have any causal relationship, the method should return an empty Collection.
     * Ex: increasing, decreasing, disrupting, etc.
     * @return the effect of this participant on the interaction
     */
    public Collection<CausalRelationship> getCausalRelationships();

    /**
     * Collection of cross references which give more information about the participant.
     * The set of xrefs cannot be null. If the participant does not have any xrefs, the method should return an empty Collection.
     * Ex: author identifiers, ...
     * @return the xrefs
     */
    public <X extends Xref> Collection<X> getXrefs();

    /**
     * Collection of annotations describing the participant.
     * The set cannot be null. If the participant does not have any annotations, the method should return an empty Collection.
     * @return the annotations
     */
    public <A extends Annotation> Collection<A> getAnnotations();

    /**
     * Collection of aliases which give more information about the participant.
     * The set of aliases cannot be null. If the participant does not have any aliases, the method should return an empty Collection.
     * Ex: author assigned name, ...
     * @return the xrefs
     */
    public <A extends Alias> Collection<A> getAliases();

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

    /**
     * Properties for this participant.
     * The collection cannot be null. If the participant does not have any features, the method should return an empty collection.
     * @return the features
     */
    public <F2 extends F> Collection<F2> getFeatures();

    /**
     * The participant change listener if set, null otherwise.
     * The participant change listener listen to changes in participant (interactor changes)
     * @return the participant change listener
     */
    public ParticipantInteractorChangeListener getChangeListener();

    /**
     * Sets the participant change listener
     * @param listener
     */
    public void setChangeListener(ParticipantInteractorChangeListener listener);

    /**
     * This method will add the feature and set the participant of the new feature to this current participant
     * @param feature
     * @return true if feature is added to the list of features
     */
    public boolean  addFeature(F feature);

    /**
     * This method will remove the feature and set the participant of the removed feature to null.
     * @param feature
     * @return true if feature is removed from the list of features
     */
    public boolean removeFeature(F feature);

    /**
     * This method will add all features and set the participant of the new features to this current participant
     * @param features
     * @return true if features are added to the list of features
     */
    public boolean  addAllFeatures(Collection<? extends F> features);

    /**
     * This method will remove all the features and set the participant of the removed features to null.
     * @param features
     * @return true if features are removed from the list of features
     */
    public boolean removeAllFeatures(Collection<? extends F> features);
}
