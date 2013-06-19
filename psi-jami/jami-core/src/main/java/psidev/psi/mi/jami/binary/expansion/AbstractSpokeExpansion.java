package psidev.psi.mi.jami.binary.expansion;

import psidev.psi.mi.jami.binary.BinaryInteraction;
import psidev.psi.mi.jami.binary.impl.DefaultBinaryInteraction;
import psidev.psi.mi.jami.model.Interaction;
import psidev.psi.mi.jami.model.Participant;
import psidev.psi.mi.jami.utils.CvTermUtils;
import psidev.psi.mi.jami.utils.ParticipantUtils;
import psidev.psi.mi.jami.utils.clone.InteractionCloner;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Abstract class for SpokeExpansion
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>19/06/13</pre>
 */

public class AbstractSpokeExpansion<T extends Interaction> extends AbstractComplexExpansionMethod<T> {

    public AbstractSpokeExpansion() {
        super(CvTermUtils.createMICvTerm(ComplexExpansionMethod.SPOKE_EXPANSION, ComplexExpansionMethod.SPOKE_EXPANSION_MI));
    }

    @Override
    protected Collection<? extends BinaryInteraction> collectBinaryInteractionsFrom(T interaction) {
        Collection<BinaryInteraction> binaryInteractions = new ArrayList<BinaryInteraction>(interaction.getParticipants().size()-1);

        Participant bait = collectBestBaitForSpokeExpansion(interaction);

        for ( Participant p : interaction.getParticipants() ) {
            if (p != bait){
                // build a new interaction
                BinaryInteraction binary = createBinaryInteraction(interaction, bait, p);

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

    protected Participant collectBestBaitForSpokeExpansion(T interaction) {
        return ParticipantUtils.collectBestBaitParticipantForSpokeExpansion(interaction.getParticipants());
    }
}
