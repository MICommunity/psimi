package psidev.psi.mi.jami.binary.expansion;

import psidev.psi.mi.jami.binary.BinaryInteraction;
import psidev.psi.mi.jami.binary.BinaryInteractionEvidence;
import psidev.psi.mi.jami.binary.ModelledBinaryInteraction;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Interaction;
import psidev.psi.mi.jami.model.InteractionEvidence;
import psidev.psi.mi.jami.model.ModelledInteraction;
import psidev.psi.mi.jami.utils.CvTermUtils;

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

public class MatrixExpansionMethod implements ComplexExpansionMethod {

    private CvTerm method;

    public MatrixExpansionMethod(){
        this.method = CvTermUtils.createMICvTerm(ComplexExpansionMethod.MATRIX_EXPANSION, ComplexExpansionMethod.MATRIX_EXPANSION_MI);
    }

    public CvTerm getMethod() {
        return this.method;
    }

    public boolean isExpandable(Interaction interaction) {
        if (interaction == null || interaction.getParticipants().isEmpty()){
            return false;
        }
        return true;
    }

    public boolean isExpandable(InteractionEvidence interaction) {
        if (interaction == null || interaction.getParticipants().isEmpty()){
            return false;
        }
        return true;
    }

    public boolean isExpandable(ModelledInteraction interaction) {
        if (interaction == null || interaction.getParticipants().isEmpty()){
            return false;
        }
        return true;
    }

    public Collection<BinaryInteraction> expand(Interaction interaction) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Collection<BinaryInteractionEvidence> expand(InteractionEvidence interaction) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Collection<ModelledBinaryInteraction> expand(ModelledInteraction interaction) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
