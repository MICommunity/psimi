package psidev.psi.mi.jami.model;

import java.util.Collection;

/**
 * A cooperative effect an interaction has on a subsequent interaction.
 *
 * A molecular binding event that influences each other either positively or negatively through allostery or pre-assembly.
 * In this context, covalent post-translational modifications are considered as binding events.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>21/05/13</pre>
 */

public interface CooperativeEffect {

    public static String ALLOSTERY = "allostery";
    public static String ALLOSTERY_ID = "MI:1157";

    /**
     * Collection of experimental methods and publications from which this cooperative effect has been inferred.
     * The collection cannot be null, if the CooperativeEffect does not have any cooperativityEvidences, this method should
     * return an empty collection.
     * @return Collection of experimental methods and publications
     */
    public Collection<CooperativityEvidence> getCooperativityEvidences();

    /**
     * Collection of model interactions affected by this model interaction.
     * The collection cannot be null, if the CooperativeEffect does not have any affectedInteractions, this method should
     * return an empty collection.
     * @return collection of model interactions affected by this model interaction
     */
    public Collection<ModelledInteraction> getAffectedInteractions();

    /**
     * The Collection of annotations describing the cooperativeEffect.
     * The Collection cannot be null. If the cooperativeEffect does not have any annotations, the method should return an empty Collection.
     * Ex: comments, cautions, ...
     * @return the annotations
     */
    public Collection<Annotation> getAnnotations();

    /**
     * For an interaction that has a cooperative effect on a subsequent interaction, this term indicates whether this effect is
     * positive or negative, i.e. whether the subsequent interaction is augmented or diminished.
     * It is a controlled vocabulary term and cannot be null.
     * @return cooperative effect outcome
     */
    public CvTerm getOutCome();

    /**
     * Sets the cooperative effect outcome
     * @param effect : cooperative effect outcome
     * @throws IllegalArgumentException when effect is null
     */
    public void setOutCome(CvTerm effect);

    /**
     * This term describes the cooperative effect on the interactions.
     * It could be a pre-assembly response or an allosteric response.
     * The pre-assembly response describes the way in which preformation of a molecular
     * complex has a non-allosteric cooperative effect on subsequent interactions of its components.
     * The allosteric response describes the effect of an allosteric binding event. It specifies which properties of
     * the allosteric molecule are altered, i.e. whether the interaction alters either (a) binding or (b) catalytic
     * properties of the allosteric molecule at a site distinct from the allosteric site.
     * The response is a controlled vocabulary term and can be null if it is not known.
     * Ex: binding site hiding, allosteric k-type response, ...
     * @return the response
     */
    public CvTerm getResponse();

    /**
     * Sets the response.
     * @param response : the allosteric or pre-assembly response
     */
    public void setResponse(CvTerm response);


}
