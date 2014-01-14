package psidev.psi.mi.jami.utils.comparator.participant;

import psidev.psi.mi.jami.model.CausalRelationship;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Entity;
import psidev.psi.mi.jami.utils.comparator.cv.DefaultCvTermComparator;

/**
 * Default comparator for CausalRelationship
 *
 * It will first compare the relationType using DefaultCvTermComparator. If both relationTypes are identical, it will compare the
 * target using DefaultParticipantBaseComparator
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>22/05/13</pre>
 */

public class DefaultCausalRelationshipComparator {

    /**
     * Use DefaultCausalRelationshipComparator to know if two causalRelationShip are equals.
     * @param causalRelationship1
     * @param causalRelationship2
     * @return true if the two causalRelationShip are equal
     */
    public static boolean areEquals(CausalRelationship causalRelationship1, CausalRelationship causalRelationship2){

        if (causalRelationship1 == null && causalRelationship2 == null){
            return true;
        }
        else if (causalRelationship1 == null || causalRelationship2 == null){
            return false;
        }
        else {
            CvTerm relationType1 = causalRelationship1.getRelationType();
            CvTerm relationType2 = causalRelationship2.getRelationType();

            if (!DefaultCvTermComparator.areEquals(relationType1, relationType2)){
                return false;
            }

            Entity p1 = causalRelationship1.getTarget();
            Entity p2 = causalRelationship2.getTarget();

            return DefaultParticipantComparator.areEquals(p1, p2);
        }
    }
}
