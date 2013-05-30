package psidev.psi.mi.jami.utils.comparator.participant;

import psidev.psi.mi.jami.model.CausalRelationship;
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

public class DefaultCausalRelationshipComparator extends CausalRelationshipComparator {

    private static DefaultCausalRelationshipComparator defaultCausalRelationshipComparator;

    /**
     * Creates a new causalRelationshipComparator with DefaultCvTermComparator and DefaultParticipantBaseComparator
     *
     */
    public DefaultCausalRelationshipComparator() {
        super(new DefaultCvTermComparator(), new DefaultParticipantComparator());
    }

    @Override
    public DefaultCvTermComparator getCvTermComparator() {
        return (DefaultCvTermComparator) super.getCvTermComparator();
    }

    @Override
    public DefaultParticipantComparator getParticipantComparator() {
        return (DefaultParticipantComparator) super.getParticipantComparator();
    }

    @Override
    /**
     * It will first compare the relationType using DefaultCvTermComparator. If both relationTypes are identical, it will compare the
     * target using DefaultParticipantBaseComparator
     */
    public int compare(CausalRelationship rel1, CausalRelationship rel2) {
        return super.compare(rel1, rel2);
    }

    /**
     * Use DefaultCausalRelationshipComparator to know if two causalRelationShip are equals.
     * @param rel1
     * @param rel2
     * @return true if the two causalRelationShip are equal
     */
    public static boolean areEquals(CausalRelationship rel1, CausalRelationship rel2){
        if (defaultCausalRelationshipComparator == null){
            defaultCausalRelationshipComparator = new DefaultCausalRelationshipComparator();
        }

        return defaultCausalRelationshipComparator.compare(rel1, rel2) == 0;
    }
}
