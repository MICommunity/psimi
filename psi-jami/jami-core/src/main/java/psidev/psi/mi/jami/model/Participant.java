package psidev.psi.mi.jami.model;

import psidev.psi.mi.jami.listener.ParticipantInteractorChangeListener;

import java.util.Collection;

/**
 * Participant identified in an interaction
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>23/11/12</pre>
 */

public interface Participant<I extends Interaction, F extends Feature> {

    public static final String UNSPECIFIED_ROLE = "unspecified role";
    public static final String UNSPECIFIED_ROLE_MI = "MI:0499";
    public static final String PUTATIVE_SELF_ROLE = "putative self";
    public static final String PUTATIVE_SELF_ROLE_MI = "MI:0898";
    public static final String SELF_ROLE = "self";
    public static final String SELF_ROLE_MI = "MI:0503";
    public static final String BAIT_ROLE = "bait";
    public static final String BAIT_ROLE_MI = "MI:0496";
    public static final String FLUORESCENCE_DONOR_ROLE = "fluorescence donor";
    public static final String FLUORESCENCE_DONOR_ROLE_MI = "MI:0583";
    public static final String SUPPRESSOR_GENE_ROLE = "suppressor gene";
    public static final String SUPPRESSOR_GENE_ROLE_MI = "MI:0581";
    public static final String ENZYME_ROLE_MI = "MI:0501";
    public static final String ENZYME_ROLE = "enzyme";
    public static final String DONOR_ROLE_MI = "MI:0918";
    public static final String DONOR_ROLE = "donor";
    public static final String ELECTRON_DONOR_ROLE_MI = "MI:0579";
    public static final String ELECTRON_DONOR_ROLE = "electron donor";
    public static final String PHOSPHATE_DONOR_ROLE_MI = "MI:0842";
    public static final String PHOSPHATE_DONOR_ROLE = "phosphate donor";
    public static final String PHOTON_DONOR_ROLE_MI = "MI:1084";
    public static final String PHOTON_DONOR_ROLE = "photon donor";
    public static final String PREY_MI = "MI:0498";
    public static final String PREY = "prey";

    /**
     * Sets the Interaction and add the new Participant to its list of Participants.
     * If the give interaction is null, it will remove the Participant from the previous interaction it was attached to
     * @param interaction : interaction
     */
    public void setInteractionAndAddParticipant(I interaction);

    /**
     * The interaction in which the participant is involved.
     * It can be null if the participant is not attached to any interactions. It can happen if the participant has been removed from an interaction and is now invalid.
     * @return the interaction
     */
    public I getInteraction();

    /**
     * Sets the interaction.
     * @param interaction : interaction
     */
    public void setInteraction(I interaction);

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
