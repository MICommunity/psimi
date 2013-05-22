package psidev.psi.mi.jami.utils.comparator.participant;

import psidev.psi.mi.jami.model.CausalRelationship;
import psidev.psi.mi.jami.utils.comparator.cv.UnambiguousCvTermComparator;

/**
 * Unambiguous comparator for CausalRelationship
 *
 * It will first compare the relationType using UnambiguousCvTermComparator. If both relationTypes are identical, it will compare the
 * target using UnambiguousParticipantBaseComparator
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>22/05/13</pre>
 */

public class UnambiguousCausalRelationshipComparator extends CausalRelationshipComparator {

    private static UnambiguousCausalRelationshipComparator unambiguousCausalRelationshipComparator;

    /**
     * Creates a new UnambiguousCausalRelationshipComparator with DefaultCvTermComparator and DefaultExactParticipantBaseComparator
     *
     */
    public UnambiguousCausalRelationshipComparator() {
        super(new UnambiguousCvTermComparator(), new UnambiguousParticipantBaseComparator());
    }

    @Override
    public UnambiguousCvTermComparator getCvTermComparator() {
        return (UnambiguousCvTermComparator) super.getCvTermComparator();
    }

    @Override
    public UnambiguousParticipantBaseComparator getParticipantComparator() {
        return (UnambiguousParticipantBaseComparator) super.getParticipantComparator();
    }

    @Override
    /**
     * It will first compare the relationType using UnambiguousCvTermComparator. If both relationTypes are identical, it will compare the
     * target using UnambiguousParticipantBaseComparator
     */
    public int compare(CausalRelationship rel1, CausalRelationship rel2) {
        return super.compare(rel1, rel2);
    }

    /**
     * Use UnambiguousCausalRelationshipComparator to know if two causalRelationShip are equals.
     * @param rel1
     * @param rel2
     * @return true if the two causalRelationShip are equal
     */
    public static boolean areEquals(CausalRelationship rel1, CausalRelationship rel2){
        if (unambiguousCausalRelationshipComparator == null){
            unambiguousCausalRelationshipComparator = new UnambiguousCausalRelationshipComparator();
        }

        return unambiguousCausalRelationshipComparator.compare(rel1, rel2) == 0;
    }

    /**
     *
     * @param rel
     * @return the hashcode consistent with the equals method for this comparator
     */
    public static int hashCode(CausalRelationship rel){
        if (unambiguousCausalRelationshipComparator == null){
            unambiguousCausalRelationshipComparator = new UnambiguousCausalRelationshipComparator();
        }

        if (rel == null){
            return 0;
        }

        int hashcode = 31;
        hashcode = 31 * hashcode + UnambiguousCvTermComparator.hashCode(rel.getRelationType());
        hashcode = 31*hashcode + UnambiguousParticipantBaseComparator.hashCode(rel.getTarget());

        return hashcode;
    }
}
