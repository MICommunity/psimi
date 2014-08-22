package psidev.psi.mi.jami.binary.expansion;

import psidev.psi.mi.jami.binary.BinaryInteraction;
import psidev.psi.mi.jami.model.Interaction;
import psidev.psi.mi.jami.model.Participant;
import psidev.psi.mi.jami.utils.CvTermUtils;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Abstract class for Matrix expansion
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>19/06/13</pre>
 */

public abstract class AbstractMatrixExpansion<T extends Interaction, B extends BinaryInteraction>
        extends AbstractComplexExpansionMethod<T,B> {

    public AbstractMatrixExpansion(){
        super(CvTermUtils.createMICvTerm(ComplexExpansionMethod.MATRIX_EXPANSION, ComplexExpansionMethod.MATRIX_EXPANSION_MI));
    }

    @Override
    protected Collection<B> collectBinaryInteractionsFromNary(T interaction){
        Participant[] participants = createParticipantsArray(interaction);

        Collection<B> binaryInteractions = new ArrayList<B>((interaction.getParticipants().size() - 1)*(interaction.getParticipants().size() - 1));
        for ( int i = 0; i < interaction.getParticipants().size(); i++ ) {
            Participant c1 = participants[i];
            for ( int j = ( i + 1 ); j < participants.length; j++ ) {
                Participant c2 = participants[j];
                // build a new interaction
                B binary = createBinaryInteraction(interaction, c1, c2);

                binaryInteractions.add(binary);
            }
        }

        return binaryInteractions;
    }

    /**
     *
     * @param interaction : the interaction to expand
     * @param c1 : participant A
     * @param c2 : participant B
     * @param <P> : participant type
     * @return  the binary interaction
     */
    protected abstract <P extends Participant> B createBinaryInteraction(T interaction, P c1, P c2);

    /**
     *
     * @param interaction : the interaction to expand
     * @param <P> : participant type
     * @return the array of participants involved in this interaction
     */
    protected abstract <P extends Participant> P[] createParticipantsArray(T interaction);
}
