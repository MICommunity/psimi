package psidev.psi.mi.jami.model;

import java.util.Collection;

/**
 * A set of molecular binding events that influence each other either positively or negatively through allostery or pre-assembly.
 * In this context, covalent post-translational modifications are considered as binding events.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>26/11/12</pre>
 */

public interface CooperativeInteraction extends ModelledInteraction {

    /**
     * For an interaction that has a cooperative effect on a subsequent interaction, this term indicates the process that mediates this effect.
     * It is a controlled vocabulary term and cannot be null.
     * Ex: allostery, pre-assembly, ...
     * @return the cooperative mechanism
     */
    public CvTerm getCooperativeMechanism();

    /**
     * Sets the cooperative mechanism.
     * @param mechanism : cooperative mechanism
     * @throws IllegalArgumentException when mechanism is null
     */
    public void setMechanism(CvTerm mechanism);

    /**
     * For an interaction that has a cooperative effect on a subsequent interaction, this term indicates whether this effect is
     * positive or negative, i.e. whether the subsequent interaction is augmented or diminished.
     * It is a controlled vocabulary term and cannot be null.
     * @return cooperative effect outcome
     */
    public CvTerm getEffectOutCome();

    /**
     * Sets the cooperative effect outcome
     * @param effect : cooperative effect outcome
     * @throws IllegalArgumentException when effect is null
     */
    public void setEffectOutCome(CvTerm effect);

    /**
     * This term describes the cooperative effect on the interactions.
     * It could be a pre-assembly response or an allosteric response.
     * The pre-assembly response describes the way in which preformation of a molecular
     * complex has a non-allosteric cooperative effect on subsequent interactions of its components.
     * The allosteric response describes the effect of an allosteric binding event. It specifies which properties of
     * the allosteric molecule are altered, i.e. whether the interaction alters either (a) binding or (b) catalytic
     * properties of the allosteric molecule at a site distinct from the allosteric site.
     * The response is a controlled vocabulary term and cannot be null.
     * Ex: binding site hiding, allosteric k-type response, ...
     * @return the response
     */
    public CvTerm getResponse();

    /**
     * Sets the response.
     * @param response : the allosteric or pre-assembly response
     * @throws IllegalArgumentException when response is null.
     */
    public void setResponse(CvTerm response);

    /**
     * The subsequent interaction/complex affected by the cooperative effect.
     * The collection cannot be null. If the CooperativeInteraction does not have any affected interactions, the method should return an empty collection
     * @return the collection of affected interactions
     */
    public Collection<ModelledInteraction> getAffectedInteractions();
}
