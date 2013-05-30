package psidev.psi.mi.jami.utils.comparator.participant;

import psidev.psi.mi.jami.model.ModelledParticipant;
import psidev.psi.mi.jami.utils.comparator.CollectionComparator;

import java.util.Comparator;

/**
 * Comparator for collection of biological participants
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>13/02/13</pre>
 */

public class ModelledParticipantCollectionComparator extends CollectionComparator<ModelledParticipant> {

    /**
     * Creates a new component CollectionComparator. It requires a Comparator for the biological participants in the Collection
     *
     * @param biologicalParticipantComparator
     */
    public ModelledParticipantCollectionComparator(Comparator<ModelledParticipant> biologicalParticipantComparator) {
        super(biologicalParticipantComparator);
    }

}
