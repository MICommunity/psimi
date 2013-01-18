package psidev.psi.mi.jami.utils.comparator.participant;

import psidev.psi.mi.jami.model.Participant;
import psidev.psi.mi.jami.utils.comparator.CollectionComparator;

/**
 * Comparator for collection of participants
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>18/01/13</pre>
 */

public class ParticipantCollectionComparator  extends CollectionComparator<Participant> {

    /**
     * Creates a new participant CollectionComparator. It requires a Comparator for the participants in the Collection
     *
     * @param participantComparator
     */
    public ParticipantCollectionComparator(ParticipantInteractorComparator participantComparator) {
        super(participantComparator);
    }

    @Override
    public ParticipantInteractorComparator getObjectComparator() {
        return (ParticipantInteractorComparator) objectComparator;
    }
}
