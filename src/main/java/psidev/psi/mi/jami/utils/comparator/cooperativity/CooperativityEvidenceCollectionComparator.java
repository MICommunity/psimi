package psidev.psi.mi.jami.utils.comparator.cooperativity;

import psidev.psi.mi.jami.model.CooperativityEvidence;
import psidev.psi.mi.jami.utils.comparator.CollectionComparator;

/**
 * Comparator for a collection of CooperativityEvidences
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>22/05/13</pre>
 */

public class CooperativityEvidenceCollectionComparator extends CollectionComparator<CooperativityEvidence> {
    /**
     * Creates a new CollectionComparator. It requires a Comparator for the obejcts in the Collection
     *
     * @param objectComparator
     */
    public CooperativityEvidenceCollectionComparator(CooperativityEvidenceComparator objectComparator) {
        super(objectComparator);
    }

    @Override
    public CooperativityEvidenceComparator getObjectComparator() {
        return (CooperativityEvidenceComparator) super.getObjectComparator();
    }
}
