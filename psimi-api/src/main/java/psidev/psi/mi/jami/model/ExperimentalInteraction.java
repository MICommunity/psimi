package psidev.psi.mi.jami.model;

import java.util.Set;

/**
 * Interaction involving one to several molecules supported by experiments.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>11/12/12</pre>
 */

public interface ExperimentalInteraction extends Interaction<ExperimentalParticipant>{

    /**
     * IMEx identifier if the interaction has been curated following IMEx curation rules.
     * It can be null if the interaction is not registered in IMEx central or does not follow the IMEx curation rules.
     * Ex: IM-123-2
     * @return the IMEx identifier
     */
    public String getImexId();

    /**
     * Assign an IMEx id to an interaction.
     * @param identifier : the IMEx id from IMEx central
     * @throws IllegalArgumentException if
     * - the identifier is null, empty or not a valid IMEx identifier for interaction
     * - the interaction already has an IMEx identifier
     */
    public void assignImexId(String identifier);

    /**
     * The experiment which determined the interaction.
     * It cannot be null.
     * @return the experiment
     */
    public Experiment getExperiment();

    /**
     * Sets the experiment for this interaction.
     * @param experiment : experiment
     * @throws IllegalArgumentException if the experiment is null
     */
    public void setExperiment(Experiment experiment);

    /**
     * The interaction type is a controlled vocabulary term.
     * It can be null.
     * Ex: direct interaction, association, ...
     * @return the interaction type
     */
    public CvTerm getType();

    /**
     * Sets the interaction type.
     * @param term : interaction type
     */
    public void setType(CvTerm term);

    /**
     * The availability for this interaction. By default it is null because freely available.
     * Ex: copyrights, ...
     * @return the availability
     */
    public String getAvailability();

    /**
     * Sets the availability.
     * @param availability: availability
     */
    public void setAvailability(String availability);

    /**
     * Set of numerical parameters for this interaction.
     * The set cannot be null. If the interaction does not have any parameters, the method should return an empty set.
     * Ex: IC50, ...
     * @return the parameters
     */
    public Set<Parameter> getParameters();

    /**
     * Boolean value to know if the interaction is inferred from multiple experiments which on their own would not support the interaction.
     * By default is false.
     * @return true if the interaction is inferred from multiple experiments which on their own would not support the interaction
     */
    public boolean isInferred();

    /**
     * Sets the inferred boolean value
     * @param inferred : inferred boolean value
     */
    public void setInferred(boolean inferred);
}
