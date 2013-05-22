package psidev.psi.mi.jami.model;

import java.util.Collection;

/**
 * An interaction that is not directly supported by experimental evidence but is based on homology statements, modelling, etc...
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>11/12/12</pre>
 */

public interface ModelledInteraction extends Interaction{

    /**
     * Interaction evidences supporting this modelled interaction.
     * The collection cannot be null. If the modelled interaction does not have experimental interactions attached to it, the method should return an empty set
     * @return the collection of experimental evidences
     */
    public Collection<InteractionEvidence> getInteractionEvidences();

    /**
     * The source which reported this modelled interaction. It can be an organization, institute, ...
     * It can be null if the source is unknown or not relevant.
     * Ex: IntAct, MINT, DIP, ...
     * @return the source
     */
    public Source getSource();

    /**
     * Sets the source reporting the interaction.
     * @param source: source for this interaction
     */
    public void setSource(Source source);

    /**
     * The collection of participants involved in this interaction.
     * The collection cannot be null. If the interaction does not involve any participants, the method should return an empty set.
     * @return the particiants
     */
    public Collection<? extends ModelledParticipant> getModelledParticipants();

    /**
     * This method will add the participant and set the interaction of the new participant to this current interaction
     * @param part
     * @return true if participant is added to the list of participants
     */
    public boolean  addModelledParticipant(ModelledParticipant part);

    /**
     * This method will remove the participant and set the interaction of the new participant to null
     * @param part
     * @return true if participant is removed from the list of participants
     */
    public boolean removeModelledParticipant(ModelledParticipant part);

    /**
     * This method will add all the participant and set the interaction of the new participant to this current interaction
     * @param participants
     * @return true if participant are added to the list of participants
     */
    public boolean  addAllModelledParticipants(Collection<? extends ModelledParticipant> participants);

    /**
     * This method will remove the participant and set the interaction of the removed participant to null.
     * @param participants
     * @return true if participant are removed from the list of participants
     */
    public boolean removeAllModelledParticipants(Collection<? extends ModelledParticipant> participants);

    /**
     * The confidences in this interaction.
     * The Collection cannot be null. If the interaction does not have any confidences, the method should return an empty Collection.
     * Ex: author based scores, statistical confidences, ...
     * @return the confidences
     */
    public Collection<ModelledConfidence> getModelledConfidences();

    /**
     * Collection of numerical parameters for this interaction.
     * The set cannot be null. If the interaction does not have any parameters, the method should return an empty Collection.
     * Ex: IC50, ...
     * @return the parameters
     */
    public Collection<ModelledParameter> getModelledParameters();

    /**
     * The collection of cooperative effects associated with this modelledInteraction.
     * The collection cannot be null. If the ModelledInteraction does not have any cooperative effects, this method
     * should return an empty collection.
     * @return the collection of cooperative effects for this modelled interaction
     */
    public Collection<CooperativeEffect> getCooperativeEffects();
}
