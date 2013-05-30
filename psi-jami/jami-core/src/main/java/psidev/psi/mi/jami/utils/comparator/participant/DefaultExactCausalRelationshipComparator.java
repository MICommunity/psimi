package psidev.psi.mi.jami.utils.comparator.participant;

import psidev.psi.mi.jami.model.CausalRelationship;
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

public class DefaultExactCausalRelationshipComparator extends CausalRelationshipComparator {

    private static DefaultExactCausalRelationshipComparator defaultExactCausalRelationshipComparator;

    /**
     * Creates a new causalRelationshipComparator with DefaultCvTermComparator and DefaultExactParticipantBaseComparator
     *
     */
    public DefaultExactCausalRelationshipComparator() {
        super(new DefaultCvTermComparator(), new DefaultExactParticipantComparator());
    }

    @Override
    public DefaultCvTermComparator getCvTermComparator() {
        return (DefaultCvTermComparator) super.getCvTermComparator();
    }

    @Override
    public DefaultExactParticipantComparator getParticipantComparator() {
        return (DefaultExactParticipantComparator) super.getParticipantComparator();
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
     * Use DefaultExactCausalRelationshipComparator to know if two causalRelationShip are equals.
     * @param rel1
     * @param rel2
     * @return true if the two causalRelationShip are equal
     */
    public static boolean areEquals(CausalRelationship rel1, CausalRelationship rel2){
        if (defaultExactCausalRelationshipComparator == null){
            defaultExactCausalRelationshipComparator = new DefaultExactCausalRelationshipComparator();
        }

        return defaultExactCausalRelationshipComparator.compare(rel1, rel2) == 0;
    }
}
