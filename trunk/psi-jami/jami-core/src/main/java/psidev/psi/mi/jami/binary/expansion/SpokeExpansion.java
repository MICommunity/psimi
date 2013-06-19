package psidev.psi.mi.jami.binary.expansion;

import psidev.psi.mi.jami.binary.BinaryInteraction;
import psidev.psi.mi.jami.model.Interaction;
import psidev.psi.mi.jami.model.InteractionEvidence;
import psidev.psi.mi.jami.model.ModelledInteraction;

import java.util.Collection;

/**
 * The spoke expansion
 * Complex n-ary data has been expanded to binary using the spoke model. This assumes that all molecules in the complex interact with a single designated molecule, usually the bait.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>05/06/13</pre>
 */

public class SpokeExpansion extends AbstractSpokeExpansion<Interaction> {
    private InteractionEvidenceSpokeExpansion interactionEvidenceExpansion;
    private ModelledInteractionSpokeExpansion modelledInteractionExpansion;

    public SpokeExpansion(){
        super();
        this.interactionEvidenceExpansion = new InteractionEvidenceSpokeExpansion();
        this.modelledInteractionExpansion = new ModelledInteractionSpokeExpansion();
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
