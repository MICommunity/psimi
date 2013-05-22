package psidev.psi.mi.jami.utils.comparator.cooperativity;

import psidev.psi.mi.jami.model.CooperativityEvidence;
import psidev.psi.mi.jami.utils.comparator.cv.DefaultCvTermComparator;
import psidev.psi.mi.jami.utils.comparator.publication.DefaultPublicationComparator;

/**
 * Default comparator for cooperativityEvidence
 *
 * It will first compare the publications using DefaultPublicationComparator and then the evidenceMethods using DefaultCvTermComparator
 *
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>22/05/13</pre>
 */

public class DefaultCooperativityEvidenceComparator extends CooperativityEvidenceComparator {

    private static DefaultCooperativityEvidenceComparator defaultCooperativityEvidenceComparator;

    public DefaultCooperativityEvidenceComparator() {
        super(new DefaultCvTermComparator(), new DefaultPublicationComparator());
    }

    @Override
    public DefaultPublicationComparator getPublicationComparator() {
        return (DefaultPublicationComparator) super.getPublicationComparator();
    }

    /**
     * It will first compare the publications using DefaultPublicationComparator and then the evidenceMethods using DefaultCvTermComparator
     * @param evidence1
     * @param evidence2
     * @return
     */
    public int compare(CooperativityEvidence evidence1, CooperativityEvidence evidence2) {
        return super.compare(evidence1, evidence2);
    }

    /**
     * Use DefaultCooperativityEvidenceComparator to know if two cooperativityEvidences are equals.
     * @param evidence1
     * @param evidence2
     * @return true if the two cooperativityEvidences are equal
     */
    public static boolean areEquals(CooperativityEvidence evidence1, CooperativityEvidence evidence2){
        if (defaultCooperativityEvidenceComparator == null){
            defaultCooperativityEvidenceComparator = new DefaultCooperativityEvidenceComparator();
        }

        return defaultCooperativityEvidenceComparator.compare(evidence1, evidence2) == 0;
    }
}
