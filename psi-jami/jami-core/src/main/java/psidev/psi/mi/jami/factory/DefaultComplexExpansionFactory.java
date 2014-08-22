package psidev.psi.mi.jami.factory;

import psidev.psi.mi.jami.binary.BinaryInteraction;
import psidev.psi.mi.jami.binary.expansion.*;
import psidev.psi.mi.jami.model.Interaction;
import psidev.psi.mi.jami.model.InteractionCategory;

/**
 * Default implementation of complex expansion
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>22/08/14</pre>
 */

public class DefaultComplexExpansionFactory implements ComplexExpansionFactory{
    public ComplexExpansionMethod createComplexExpansionMethod(InteractionCategory category, ComplexExpansionType type) {
        switch (type){
            case bipartite:
                return createBipartiteComplexExpansion(category);
            case matrix:
                return createMatrixComplexExpansion(category);
            case spoke:
                return createSpokeComplexExpansion(category);
            default:
                throw new IllegalArgumentException("Cannot create a complex expansion method for ComplexExpansionType "+type);
        }
    }

    public ComplexExpansionMethod<Interaction, BinaryInteraction> createDefaultComplexExpansion(ComplexExpansionType type) {
        return null;
    }

    public ComplexExpansionMethod createSpokeComplexExpansion(InteractionCategory category) {
        switch (category){
            case evidence:
                return new InteractionEvidenceSpokeExpansion();
            case modelled:
                return new ModelledInteractionSpokeExpansion();
            case mixed:
                return new SpokeExpansion();
            default:
                throw new IllegalArgumentException("Cannot create a spoke complex expansion method for category "+category);
        }
    }

    public ComplexExpansionMethod createMatrixComplexExpansion(InteractionCategory category) {
        switch (category){
            case evidence:
                return new InteractionEvidenceMatrixExpansion();
            case modelled:
                return new InteractionEvidenceMatrixExpansion();
            case mixed:
                return new MatrixExpansion();
            default:
                throw new IllegalArgumentException("Cannot create a matrix complex expansion method for category "+category);
        }
    }

    public ComplexExpansionMethod createBipartiteComplexExpansion(InteractionCategory category) {
        switch (category){
            case evidence:
                return new InteractionEvidenceBipartiteExpansion();
            case modelled:
                return new InteractionEvidenceBipartiteExpansion();
            case mixed:
                return new BipartiteExpansion();
            default:
                throw new IllegalArgumentException("Cannot create a bipartite complex expansion method for category "+category);
        }
    }
}
