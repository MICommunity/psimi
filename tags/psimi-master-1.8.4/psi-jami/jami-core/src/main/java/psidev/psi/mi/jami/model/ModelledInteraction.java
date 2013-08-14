package psidev.psi.mi.jami.model;

import java.util.Collection;

/**
 * An interaction that is not directly supported by experimental evidence but is based on homology statements, modelling, etc...
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>11/12/12</pre>
 */

public interface ModelledInteraction extends Interaction<ModelledParticipant>{

    /**
     * Interaction evidences supporting this modelled interaction.
     * The collection cannot be null. If the modelled interaction does not have experimental interactions attached to it, the method should return an empty set
     * @return the collection of experimental evidences
     */
    public <I extends InteractionEvidence> Collection<I> getInteractionEvidences();

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
     * The confidences in this interaction.
     * The Collection cannot be null. If the interaction does not have any confidences, the method should return an empty Collection.
     * Ex: author based scores, statistical confidences, ...
     * @return the confidences
     */
    public <C extends ModelledConfidence> Collection<C> getModelledConfidences();

    /**
     * Collection of numerical parameters for this interaction.
     * The set cannot be null. If the interaction does not have any parameters, the method should return an empty Collection.
     * Ex: IC50, ...
     * @return the parameters
     */
    public <P extends ModelledParameter> Collection<P> getModelledParameters();

    /**
     * The collection of cooperative effects associated with this modelledInteraction.
     * The collection cannot be null. If the ModelledInteraction does not have any cooperative effects, this method
     * should return an empty collection.
     * @return the collection of cooperative effects for this modelled interaction
     */
    public <C extends CooperativeEffect> Collection<C> getCooperativeEffects();
}
