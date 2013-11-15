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
    public static String PREASSEMBLY = "pre-assembly";
    public static String PREASSEMBLY_ID = "MI:1158";
    public static String AFFECTED_INTERACTION = "affected interaction";
    public static String AFFECTED_INTERACTION_ID = "MI:1150";
    public static String NEGATIVE_EFFECT = "negative cooperative effect";
    public static String NEGATIVE_EFFECT_ID = "MI:1155";
    public static String POSITIVE_EFFECT = "positive cooperative effect";
    public static String POSITIVE_EFFECT_ID = "MI:1154";
    public static String ALLOSTERIC_EFFECTOR = "allosteric effector";
    public static String ALLOSTERIC_EFFECTOR_ID = "MI:1160";
    public static String ALLOSTERIC_MOLECULE = "allosteric molecule";
    public static String ALLOSTERIC_MOLECULE_ID = "MI:1159";
    public static String ALLOSTERIC_K_RESPONSE = "allosteric k-type response";
    public static String ALLOSTERIC_K_RESPONSE_ID = "MI:1162";
    public static String ALLOSTERIC_V_RESPONSE = "allosteric v-type response";
    public static String ALLOSTERIC_V_RESPONSE_ID = "MI:1163";
    public static String HETEROTROPIC_ALLOSTERY = "heterotropic allostery";
    public static String HETEROTROPIC_ALLOSTERY_ID = "MI:1168";
    public static String HOMOTROPIC_ALLOSTERY = "homotropic allostery";
    public static String HOMOTROPIC_ALLOSTERY_ID = "MI:1169";
    public static String COOPERATIVE_EFFECT_VALUE = "cooperative effect value";
    public static String COOPERATIVE_EFFECT_VALUE_ID = "MI:1152";
    public static String ALLOSTERIC_DYNAMIC_CHANGE = "allosteric change in dynamics";
    public static String ALLOSTERIC_DYNAMIC_CHANGE_ID = "MI:1166";
    public static String ALTERED_PHYSICO_COMPATIBILITY = "altered physicochemical compatibility";
    public static String ALTERED_PHYSICO_COMPATIBILITY_ID = "MI:1172";
    public static String BINDING_HIDING = "binding site hiding";
    public static String BINDING_HIDING_ID = "MI:1173";
    public static String COMPOSITE_BINDING = "composite binding site formation";
    public static String COMPOSITE_BINDING_ID = "MI:1171";
    public static String ALLOSTERIC_STRUCTURE_CHANE = "allosteric change in structure";
    public static String ALLOSTERIC_STRUCTURE_CHANE_ID = "MI:1165";
    public static String PRE_ORGANIZATION = "configurational pre-organization";
    public static String PRE_ORGANIZATION_ID = "MI:1174";
    public static String ALLOSTERIC_PTM = "allosteric ptm";
    public static String ALLOSTERIC_PTM_ID = "MI:1175";
    public static String PARTICIPANT_REF = "participant-ref";
    public static String PARTICIPANT_REF_ID = "MI:1151";


    /**
     * Collection of experimental methods and publications from which this cooperative effect has been inferred.
     * The collection cannot be null, if the CooperativeEffect does not have any cooperativityEvidences, this method should
     * return an empty collection.
     * @return Collection of experimental methods and publications
     */
    public <T extends CooperativityEvidence> Collection<T> getCooperativityEvidences();

    /**
     * Collection of model interactions affected by this model interaction.
     * The collection cannot be null, if the CooperativeEffect does not have any affectedInteractions, this method should
     * return an empty collection.
     * @return collection of model interactions affected by this model interaction
     */
    public <T extends ModelledInteraction> Collection<T> getAffectedInteractions();

    /**
     * The Collection of annotations describing the cooperativeEffect.
     * The Collection cannot be null. If the cooperativeEffect does not have any annotations, the method should return an empty Collection.
     * Ex: comments, cautions, ...
     * @return the annotations
     */
    public <T extends Annotation> Collection<T> getAnnotations();

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
