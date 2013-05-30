package psidev.psi.mi.jami.utils.comparator.participant;

import psidev.psi.mi.jami.model.CausalRelationship;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Participant;
import psidev.psi.mi.jami.utils.comparator.cv.AbstractCvTermComparator;

import java.util.Comparator;

/**
 * Basic comparator for CausalRelationship
 *
 * It will first compare the relationType using AbstractCvTermComparator. If both relationTypes are identical, it will compare the
 * target using ParticipantBaseComparator
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>22/05/13</pre>
 */

public class CausalRelationshipComparator implements Comparator<CausalRelationship>{

    private AbstractCvTermComparator cvTermComparator;
    private ParticipantComparator participantComparator;

    public CausalRelationshipComparator(AbstractCvTermComparator cvTermComparator, ParticipantComparator participantComparator){
        if (cvTermComparator == null){
            throw new IllegalArgumentException("The cvTermComparator cannot be null in a CausalRelationshipComparator");
        }
        this.cvTermComparator = cvTermComparator;

        if (participantComparator == null){
            throw new IllegalArgumentException("The participantComparator cannot be null in a CausalRelationshipComparator");
        }
        this.participantComparator = participantComparator;
    }

    public AbstractCvTermComparator getCvTermComparator() {
        return cvTermComparator;
    }

    public ParticipantComparator getParticipantComparator() {
        return participantComparator;
    }

    /**
     * It will first compare the relationType using AbstractCvTermComparator. If both relationTypes are identical, it will compare the
     * target using ParticipantBaseComparator
     * @param causalRelationship1
     * @param causalRelationship2
     * @return
     */
    public int compare(CausalRelationship causalRelationship1, CausalRelationship causalRelationship2) {
        int EQUAL = 0;
        int BEFORE = -1;
        int AFTER = 1;

        if (causalRelationship1 == null && causalRelationship2 == null){
            return EQUAL;
        }
        else if (causalRelationship1 == null){
            return AFTER;
        }
        else if (causalRelationship2 == null){
            return BEFORE;
        }
        else {
            CvTerm relationType1 = causalRelationship1.getRelationType();
            CvTerm relationType2 = causalRelationship2.getRelationType();

            int comp = cvTermComparator.compare(relationType1, relationType2);
            if (comp != 0){
                return comp;
            }

            Participant p1 = causalRelationship1.getTarget();
            Participant p2 = causalRelationship2.getTarget();

            return participantComparator.compare(p1, p2);
        }
    }
}
