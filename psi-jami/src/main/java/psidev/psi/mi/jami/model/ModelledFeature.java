package psidev.psi.mi.jami.model;

import java.util.Collection;

/**
 * 	Biological property of a participant that may be involved with or interfere with the binding of a molecule.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>23/11/12</pre>
 */

public interface ModelledFeature extends Feature{

    /**
     * The participant to which the feature is attached.
     * It can be null if the feature is not attached to any participants.
     * @return the participant
     */
    public ModelledParticipant getModelledParticipant();

    /**
     * Sets the participant.
     * @param participant : participant
     */
    public void setModelledParticipant(ModelledParticipant participant);

    /**
     * Sets the participant and add this feature to its list of features
     * @param participant : participant
     */
    public void setModelledParticipantAndAddFeature(ModelledParticipant participant);

    /**
     * The other features that can bind to this feature.
     * The collection cannot be null. If the feature does not bind with any other features, the method should return an empty collection
     * @return the binding features
     */
    public Collection<? extends ModelledFeature> getModelledBindingSites();

    /**
     * This method will add the feature as a modelled binding site
     * @param feature
     * @return true if feature is added to the list of features
     */
    public boolean addModelledBindingSite(ModelledFeature feature);

    /**
     * This method will remove the feature from the modelled binding sites
     * @param feature
     * @return true if feature is removed from the list of features
     */
    public boolean removeModelledBindingSite(ModelledFeature feature);

    /**
     * This method will add all features as modelled binding site
     * @param features
     * @return true if features are added to the list of features
     */
    public boolean addAllModelledBindingSites(Collection<? extends ModelledFeature> features);

    /**
     * This method will remove all the features from modelled binding sites
     * @param features
     * @return true if features are removed from the list of features
     */
    public boolean removeAllModelledBindingSites(Collection<? extends ModelledFeature> features);

}
