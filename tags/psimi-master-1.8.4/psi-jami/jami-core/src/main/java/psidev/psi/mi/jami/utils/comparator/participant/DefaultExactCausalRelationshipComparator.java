package psidev.psi.mi.jami.utils.comparator.participant;

import psidev.psi.mi.jami.model.CausalRelationship;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Participant;
import psidev.psi.mi.jami.utils.comparator.cv.DefaultCvTermComparator;

/**
 * Default exact comparator for CausalRelationship
 *
 * It will first compare the relationType using DefaultCvTermComparator. If both relationTypes are identical, it will compare the
 * target using DefaultExactParticipantBaseComparator
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>22/05/13</pre>
 */

public class DefaultExactCausalRelationshipComparator {

    /**
     * Use DefaultExactCausalRelationshipComparator to know if two causalRelationShip are equals.
     * @param rel1
     * @param rel2
     * @return true if the two causalRelationShip are equal
     */
    public static boolean areEquals(CausalRelationship rel1, CausalRelationship rel2){
        if (rel1 == null && rel2 == null){
            return true;
        }
        else if (rel1 == null || rel2 == null){
            return false;
        }
        else {
            CvTerm relationType1 = rel1.getRelationType();
            CvTerm relationType2 = rel2.getRelationType();

            if (!DefaultCvTermComparator.areEquals(relationType1, relationType2)){
                return false;
            }

            Participant p1 = rel1.getTarget();
            Participant p2 = rel2.getTarget();

            return DefaultExactParticipantComparator.areEquals(p1, p2);
        }
    }
}
