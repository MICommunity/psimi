package psidev.psi.mi.jami.binary.expansion;

import psidev.psi.mi.jami.binary.BinaryInteraction;
import psidev.psi.mi.jami.model.Interaction;
import psidev.psi.mi.jami.model.InteractionEvidence;
import psidev.psi.mi.jami.model.ModelledInteraction;

import java.util.Collection;

/**
 * The matrix Expansion method.
 * 	Complex n-ary data has been expanded to binary using the spoke model.
 * 	This assumes that all molecules in the complex interact with each other.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>04/06/13</pre>
 */

public class MatrixExpansion extends AbstractMatrixExpansion<Interaction> {

    private InteractionEvidenceMatrixExpansion interactionEvidenceExpansion;
    private ModelledInteractionMatrixExpansion modelledInteractionExpansion;

    public MatrixExpansion(){
        super();
        this.interactionEvidenceExpansion = new InteractionEvidenceMatrixExpansion();
        this.modelledInteractionExpansion = new ModelledInteractionMatrixExpansion();
    }

    @Override
    public Collection<? extends BinaryInteraction> expand(Interaction interaction) {

        if (interaction instanceof InteractionEvidence){
            return interactionEvidenceExpansion.expand((InteractionEvidence) interaction);
        }
        else if (interaction instanceof ModelledInteraction){
            return modelledInteractionExpansion.expand((ModelledInteraction) interaction);
        }
        else {
            return super.expand(interaction);
        }
    }
}
