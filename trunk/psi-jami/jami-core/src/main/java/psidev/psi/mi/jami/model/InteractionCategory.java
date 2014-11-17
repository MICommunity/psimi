package psidev.psi.mi.jami.model;

/**
 * Enum to recognize the object category of an interaction :
 * - evidence : for InteractionEvidence implementations.
 * - modelled: for ModelledInteraction implementations.
 * - basic: for basic implementations of Interaction
 * - mixed: for a mix of any kind of interactions
 * - complex: for biological complexes. Not all datasources support this kind of interactions
 *
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>03/07/13</pre>
 */

public enum InteractionCategory {

    evidence, modelled, basic, complex, mixed
}
