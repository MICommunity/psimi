package psidev.psi.mi.jami.model;

import java.util.Collection;

/**
 * Participant identified in an interaction
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>23/11/12</pre>
 */

public interface Participant<T extends Interactor, F extends Feature> {

    public static String UNSPECIFIED_ROLE = "unspecified role";
    public static String UNSPECIFIED_ROLE_MI = "MI:0499";

    /**
     * The molecule/complex of molecules which interacts.
     * It cannot be null.
     * @return the interactor
     */
    public T getInteractor();

    /**
     * Sets the interactor
     * @param interactor : interactor
     * @throws IllegalArgumentException when interactor is null
     */
    public void setInteractor(T interactor);

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
     * Collection of cross references which give more information about the participant.
     * The set of xrefs cannot be null. If the participant does not have any xrefs, the method should return an empty Collection.
     * Ex: author identifiers, ...
     * @return the xrefs
     */
    public Collection<Xref> getXrefs();

    /**
     * Collection of annotations describing the participant.
     * The set cannot be null. If the participant does not have any annotations, the method should return an empty Collection.
     * @return the annotations
     */
    public Collection<Annotation> getAnnotations();

    /**
     * Collection of aliases which give more information about the participant.
     * The set of aliases cannot be null. If the participant does not have any aliases, the method should return an empty Collection.
     * Ex: author assigned name, ...
     * @return the xrefs
     */
    public Collection<Alias> getAliases();

    /**
     * Properties for this participant which are supported by experimental evidences.
     * The collection cannot be null. If the participant does not have any features, the method should return an empty collection.
     * @return the features
     */
    public Collection<? extends F> getFeatures();

    /**
     * The stoichiometry for this participant.
     * If the stoichiometry for this participant is unknown, the method should return null.
     * @return the stoichiometry
     */
    public Integer getStoichiometry();

    /**
     * Sets the stoichiometry for this participant.
     * @param stoichiometry : stoichiometry
     */
    public void setStoichiometry(Integer stoichiometry);

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
