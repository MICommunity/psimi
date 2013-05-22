package psidev.psi.mi.jami.utils.comparator.participant;

import psidev.psi.mi.jami.model.CausalRelationship;
import psidev.psi.mi.jami.utils.comparator.cv.UnambiguousCvTermComparator;

/**
 * Unambiguous exact comparator for CausalRelationship
 *
 * It will first compare the relationType using UnambiguousCvTermComparator. If both relationTypes are identical, it will compare the
 * target using UnambiguousExactParticipantBaseComparator
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>22/05/13</pre>
 */

public class UnambiguousExactCausalRelationshipComparator extends CausalRelationshipComparator {

    private static UnambiguousExactCausalRelationshipComparator unambiguousExactCausalRelationshipComparator;

    /**
     * Creates a new UnambiguousExactCausalRelationshipComparator with DefaultCvTermComparator and DefaultExactParticipantBaseComparator
     *
     */
    public UnambiguousExactCausalRelationshipComparator() {
        super(new UnambiguousCvTermComparator(), new UnambiguousExactParticipantBaseComparator());
    }

    @Override
    public UnambiguousCvTermComparator getCvTermComparator() {
        return (UnambiguousCvTermComparator) super.getCvTermComparator();
    }

    @Override
    public UnambiguousExactParticipantBaseComparator getParticipantComparator() {
        return (UnambiguousExactParticipantBaseComparator) super.getParticipantComparator();
    }

    @Override
    /**
     * It will first compare the relationType using UnambiguousCvTermComparator. If both relationTypes are identical, it will compare the
     * target using UnambiguousExactParticipantBaseComparator
     */
    public int compare(CausalRelationship rel1, CausalRelationship rel2) {
        return super.compare(rel1, rel2);
    }

    /**
     * Use UnambiguousExactCausalRelationshipComparator to know if two causalRelationShip are equals.
     * @param rel1
     * @param rel2
     * @return true if the two causalRelationShip are equal
     */
    public static boolean areEquals(CausalRelationship rel1, CausalRelationship rel2){
        if (unambiguousExactCausalRelationshipComparator == null){
            unambiguousExactCausalRelationshipComparator = new UnambiguousExactCausalRelationshipComparator();
        }

        return unambiguousExactCausalRelationshipComparator.compare(rel1, rel2) == 0;
    }

    /**
     *
     * @param rel
     * @return the hashcode consistent with the equals method for this comparator
     */
    public static int hashCode(CausalRelationship rel){
        if (unambiguousExactCausalRelationshipComparator == null){
            unambiguousExactCausalRelationshipComparator = new UnambiguousExactCausalRelationshipComparator();
        }

        if (rel == null){
            return 0;
        }

        int hashcode = 31;
        hashcode = 31 * hashcode + UnambiguousCvTermComparator.hashCode(rel.getRelationType());
        hashcode = 31*hashcode + UnambiguousExactParticipantBaseComparator.hashCode(rel.getTarget());

        return hashcode;
    }
}
