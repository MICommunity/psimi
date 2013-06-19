package psidev.psi.mi.jami.binary.expansion;

import psidev.psi.mi.jami.binary.BinaryInteraction;
import psidev.psi.mi.jami.binary.impl.DefaultBinaryInteraction;
import psidev.psi.mi.jami.model.Interaction;
import psidev.psi.mi.jami.model.Participant;
import psidev.psi.mi.jami.model.impl.DefaultParticipant;
import psidev.psi.mi.jami.utils.CvTermUtils;
import psidev.psi.mi.jami.utils.clone.InteractionCloner;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Abstract class for Matrix expansion
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>19/06/13</pre>
 */

public class AbstractMatrixExpansion<T extends Interaction> extends AbstractComplexExpansionMethod<T> {

    public AbstractMatrixExpansion(){
        super(CvTermUtils.createMICvTerm(ComplexExpansionMethod.MATRIX_EXPANSION, ComplexExpansionMethod.MATRIX_EXPANSION_MI));
    }

    @Override
    protected Collection<? extends BinaryInteraction> collectBinaryInteractionsFrom(T interaction){
        Participant[] participants = createParticipantsArray(interaction);

        Collection<BinaryInteraction> binaryInteractions = new ArrayList<BinaryInteraction>((interaction.getParticipants().size() - 1)*(interaction.getParticipants().size() - 1));
        for ( int i = 0; i < interaction.getParticipants().size(); i++ ) {
            Participant c1 = participants[i];
            for ( int j = ( i + 1 ); j < participants.length; j++ ) {
                Participant c2 = participants[j];
                // build a new interaction
                BinaryInteraction binary = createBinaryInteraction(interaction, c1, c2);

                binaryInteractions.add(binary);
            }
        }

        return binaryInteractions;
    }

    protected BinaryInteraction createBinaryInteraction(T interaction, Participant c1, Participant c2) {
        BinaryInteraction binary = new DefaultBinaryInteraction(getMethod());
        InteractionCloner.copyAndOverrideBasicInteractionProperties(interaction, binary, false, true);
        binary.setParticipantA(c1);
        binary.setParticipantB(c2);
        return binary;
    }

    protected Participant[] createParticipantsArray(T interaction) {
        return interaction.getParticipants().toArray(new DefaultParticipant[]{});
    }
}
