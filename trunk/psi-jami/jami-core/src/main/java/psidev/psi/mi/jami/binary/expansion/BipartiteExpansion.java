package psidev.psi.mi.jami.binary.expansion;

import psidev.psi.mi.jami.binary.BinaryInteraction;
import psidev.psi.mi.jami.model.Interaction;
import psidev.psi.mi.jami.model.InteractionEvidence;
import psidev.psi.mi.jami.model.ModelledInteraction;

import java.util.Collection;

/**
 * The bipartite expansion.
 *
 * Complex n-ary data has been expanded to binary using the bipartite model.
 * This assumes that all molecules in the complex interact with a single externally designated entity.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>05/06/13</pre>
 */

public class BipartiteExpansion extends AbstractBipartiteExpansion<Interaction> {

    private InteractionEvidenceBipartiteExpansion interactionEvidenceExpansion;
    private ModelledInteractionBipartiteExpansion modelledInteractionExpansion;

    public BipartiteExpansion(){
        super();
        this.interactionEvidenceExpansion = new InteractionEvidenceBipartiteExpansion();
        this.modelledInteractionExpansion = new ModelledInteractionBipartiteExpansion();
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