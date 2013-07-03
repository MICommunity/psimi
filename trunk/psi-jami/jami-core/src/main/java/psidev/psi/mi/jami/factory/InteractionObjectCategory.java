package psidev.psi.mi.jami.factory;

/**
 * Enum to recognize the object category of an interaction :
 * - evidence : for InteractionEvidence implementations
 * - modelled: for ModelledInteraction implementations
 * - mixed: for a mix between ModelledInteraction implementations and InteractionEvidence implementations
 * - basic: for basic implementations of Interaction
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>03/07/13</pre>
 */

public enum InteractionObjectCategory {

    evidence, modelled, mixed, basic, binary_evidence, modelled_binary, mixed_binary, basic_binary
}
