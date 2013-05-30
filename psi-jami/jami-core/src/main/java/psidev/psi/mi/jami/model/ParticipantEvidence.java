package psidev.psi.mi.jami.model;

import java.util.Collection;

/**
 * Participant of an interaction which is supported by experimental evidences
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>07/12/12</pre>
 */

public interface ParticipantEvidence extends Participant<Interactor>{

     /**
     * The experimental role of the participant.
     * It is a controlled vocabulary term and cannot be null.
     * It the experimental role role of a participant is not known or not relevant, the method should return
     * unspecified role (MI:0499)
     * Ex: bait, prey, ...
     * @return the experimental role
     */
    public CvTerm getExperimentalRole();

    /**
     * Sets the experimental role.
     * If expRole is null, it should create a unspecified role (MI:0499)
     * @param expRole : experimental role
     */
    public void setExperimentalRole(CvTerm expRole);

    /**
     * The identification methods for this participant.
     * Each identification method is a controlled vocabulary term.
     * The collection cannot be null. If the participant does not have any identification methods, this method should return an empty collection
     * Ex: western blot, immunostaining, ...
     * @return the participant identification method
     */
    public Collection<CvTerm> getIdentificationMethods();

    /**
     * The experimental preparations for this participant.
     * Each experimental preparation is a controlled vocabulary term.
     * The Collection cannot be null. If the participant does not have any experimental preparations, the method should return an empty Collection.
     * Ex: engineered, cDNA library, ...
     * @return the experimental preparations.
     */
    public Collection<CvTerm> getExperimentalPreparations();

    /**
     * The organisms in which the participant has been expressed.
     * It can be null if not relevant or same as the original source organism of the interactor
     * Ex: human-hela cells, ...
     * @return the expressed in organism
     */
    public Organism getExpressedInOrganism();

    /**
     * Sets the expressed in organism
     * @param organism: expressed in organism
     */
    public void setExpressedInOrganism(Organism organism);

    /**
     * The confidences for this participant.
     * The Collection cannot be null. If the participant does not have any confidences, the method should return an empty Collection.
     * Ex: author based scores, statistical confidences, ...
     * @return the confidences
     */
    public Collection<Confidence> getConfidences();

    /**
     * Numerical parameters associated with this participant.
     * The Collection cannot be null. If the participant does not have any parameters, the method should return an empty Collection.
     * @return the parameters
     */
    public Collection<Parameter> getParameters();

    /**
     * Sets the interaction evidence and add the new participant to its list of participant evidences.
     * If the given interaction is null, it will remove this featureEvidence from the previous interaction it was attached to
     * @param interaction : interaction evidence
     */
    public void setInteractionEvidenceAndAddParticipantEvidence(InteractionEvidence interaction);

    /**
     * The interaction in which the participant is involved.
     * It can be null if the participant is not attached to any interactions. It can happen if the participant has been removed from an interaction and is now invalid.
     * @return the interaction
     */
    public InteractionEvidence getInteractionEvidence();

    /**
     * Sets the interaction.
     * @param interaction : interaction evidence
     */
    public void setInteractionEvidence(InteractionEvidence interaction);

    /**
     * This method will add the feature and set the participant of the new feature to this current participant
     * @param feature
     * @return true if feature is added to the list of features
     */
    public boolean  addFeatureEvidence(FeatureEvidence feature);

    /**
     * This method will remove the feature and set the participant of the removed feature to null.
     * @param feature
     * @return true if feature is removed from the list of features
     */
    public boolean removeFeatureEvidence(FeatureEvidence feature);

    /**
     * This method will add all features and set the participant of the new features to this current participant
     * @param features
     * @return true if features are added to the list of features
     */
    public boolean  addAllFeatureEvidences(Collection<? extends FeatureEvidence> features);

    /**
     * This method will remove all the features and set the participant of the removed features to null.
     * @param features
     * @return true if features are removed from the list of features
     */
    public boolean removeAllFeatureEvidences(Collection<? extends FeatureEvidence> features);

    /**
     * Properties for this participant which are supported by experimental evidences.
     * The collection cannot be null. If the participant does not have any features, the method should return an empty collection.
     * @return the features
     */
    public Collection<FeatureEvidence> getFeatureEvidences();
}
