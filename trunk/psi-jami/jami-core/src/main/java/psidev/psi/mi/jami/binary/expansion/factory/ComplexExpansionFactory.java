package psidev.psi.mi.jami.binary.expansion.factory;

import psidev.psi.mi.jami.binary.BinaryInteraction;
import psidev.psi.mi.jami.binary.expansion.ComplexExpansionMethod;
import psidev.psi.mi.jami.model.Interaction;
import psidev.psi.mi.jami.model.InteractionCategory;

/**
 * An interface for a factory of complex expansion methods
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>16/10/13</pre>
 */

public interface ComplexExpansionFactory {

    /**
     * Create an instance of complex expansion that suits the interactionCategory and the ComplexExpansionType
     * @param category : the type of interactions to deal with (evidence, modelled, mixed,...)
     * @param type : the type of complex expansiom (spoke, matrix or bipartite)
     * @return the complexExpansion instance
     */
    public ComplexExpansionMethod createComplexExpansionMethod(InteractionCategory category, ComplexExpansionType type);

    /**
     * Create an instance of complex expansion that suits the ComplexExpansionType and can deal with any kind of interactions
     * (modelled, evidence)
     * @param type : the type of complex expansiom (spoke, matrix or bipartite)
     * @return the complexExpansion instance that will deal with any kind of interactions (modelled, evidence, etc.)
     */
    public ComplexExpansionMethod<Interaction,BinaryInteraction> createDefaultComplexExpansion(ComplexExpansionType type);

    /**
     * Create an instance of spoke complex expansion that suits the interactionCategory
     * @param category : the type of interactions to deal with (evidence, modelled, mixed,...)
     * @return the spoke complexExpansion instance
     */
    public ComplexExpansionMethod createSpokeComplexExpansion(InteractionCategory category);

    /**
     * Create an instance of matrix complex expansion that suits the interactionCategory
     * @param category : the type of interactions to deal with (evidence, modelled, mixed,...)
     * @return the matrix complexExpansion instance
     */

    public ComplexExpansionMethod createMatrixComplexExpansion(InteractionCategory category);

    /**
     * Create an instance of bipartite complex expansion that suits the interactionCategory
     * @param category : the type of interactions to deal with (evidence, modelled, mixed,...)
     * @return the bipartite complexExpansion instance
     */
    public ComplexExpansionMethod createBipartiteComplexExpansion(InteractionCategory category);
}
