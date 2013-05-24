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
     * Sets the participant and add this feature to its list of features.
     * If participant is null, it will remove this feature from the previous participant attached to this feature
     * @param participant : participant
     */
    public void setModelledParticipantAndAddFeature(ModelledParticipant participant);

    /**
     * The other features that can bind to this feature.
     * The collection cannot be null. If the feature does not bind with any other features, the method should return an empty collection
     * @return the binding features
     */
    public Collection<ModelledFeature> getLinkedModelledFeatures();

}
