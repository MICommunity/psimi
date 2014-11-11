package psidev.psi.mi.jami.listener;

import psidev.psi.mi.jami.model.*;

/**
 * Listener for changes in an entity
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 19/07/13
 */
public interface EntityChangeListener<F extends Entity> extends EntityInteractorChangeListener {

    /**
     *
     * @param participant : the updated participant
     * @param oldStoichiometry : old stoichiometry
     */
    public void onStoichiometryUpdate(F participant, Stoichiometry oldStoichiometry);

    /**
     *
     * @param participant : the updated participant
     * @param added : old causal relationship
     */
    public void onAddedCausalRelationship(F participant, CausalRelationship added);

    /**
     *
     * @param participant : the updated participant
     * @param removed : removed causal relationship
     */
    public void onRemovedCausalRelationship(F participant, CausalRelationship removed);

    /**
     *
     * @param participant : the updated participant
     * @param added : added feature
     */
    public void onAddedFeature(F participant, Feature added);

    /**
     *
     * @param participant : the updated participant
     * @param removed : removed feature
     */
    public void onRemovedFeature(F participant, Feature removed);
}
