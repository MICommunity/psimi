package psidev.psi.mi.jami.binary.expansion;

import psidev.psi.mi.jami.binary.BinaryInteraction;
import psidev.psi.mi.jami.model.Interaction;
import psidev.psi.mi.jami.model.Participant;
import psidev.psi.mi.jami.utils.CvTermUtils;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Abstract class for SpokeExpansion
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>19/06/13</pre>
 */

public abstract class AbstractSpokeExpansion<T extends Interaction<? extends Participant>, B extends BinaryInteraction> extends AbstractComplexExpansionMethod<T,B> {

    public AbstractSpokeExpansion() {
        super(CvTermUtils.createMICvTerm(ComplexExpansionMethod.SPOKE_EXPANSION, ComplexExpansionMethod.SPOKE_EXPANSION_MI));
    }

    @Override
    protected Collection<B> collectBinaryInteractionsFrom(T interaction) {
        Collection<B> binaryInteractions = new ArrayList<B>(interaction.getParticipants().size()-1);

        Participant bait = collectBestBaitForSpokeExpansion(interaction);

        for ( Participant p : interaction.getParticipants() ) {
            if (p != bait){
                // build a new interaction
                B binary = createBinaryInteraction(interaction, bait, p);

                binaryInteractions.add(binary);
            }
        }

        return binaryInteractions;
    }

    protected abstract <P extends Participant> B createBinaryInteraction(T interaction, P c1, P c2);

    protected abstract <P extends Participant> P collectBestBaitForSpokeExpansion(T interaction);
}
