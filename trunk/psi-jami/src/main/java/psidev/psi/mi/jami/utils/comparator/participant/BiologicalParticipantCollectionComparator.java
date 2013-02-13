package psidev.psi.mi.jami.utils.comparator.participant;

import psidev.psi.mi.jami.model.BiologicalParticipant;
import psidev.psi.mi.jami.utils.comparator.CollectionComparator;

/**
 * Comparator for collection of biological participants
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>13/02/13</pre>
 */

public class BiologicalParticipantCollectionComparator extends CollectionComparator<BiologicalParticipant> {

    /**
     * Creates a new component CollectionComparator. It requires a Comparator for the biological participants in the Collection
     *
     * @param biologicalParticipantComparator
     */
    public BiologicalParticipantCollectionComparator(BiologicalParticipantComparator biologicalParticipantComparator) {
        super(biologicalParticipantComparator);
    }

    @Override
    public BiologicalParticipantComparator getObjectComparator() {
        return (BiologicalParticipantComparator) objectComparator;
    }
}
