package psidev.psi.mi.jami.utils.comparator.participant;

import psidev.psi.mi.jami.model.Participant;
import psidev.psi.mi.jami.utils.comparator.CollectionComparator;

import java.util.Comparator;

/**
 * Comparator for collection of participants
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>18/01/13</pre>
 */

public class ParticipantCollectionComparator<T extends Participant>  extends CollectionComparator<T> {

    /**
     * Creates a new participant CollectionComparator. It requires a Comparator for the participants in the Collection
     *
     * @param participantComparator
     */
    public ParticipantCollectionComparator(Comparator<T> participantComparator) {
        super(participantComparator);
    }

    @Override
    public Comparator<T> getObjectComparator() {
        return objectComparator;
    }
}
