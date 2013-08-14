package psidev.psi.mi.jami.utils.comparator.cooperativity;

import psidev.psi.mi.jami.model.CooperativityEvidence;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Publication;
import psidev.psi.mi.jami.utils.comparator.cv.UnambiguousCvTermComparator;
import psidev.psi.mi.jami.utils.comparator.publication.UnambiguousPublicationComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Unambiguous comparator for cooperativityEvidence
 *
 * It will first compare the publications using UnambiguousPublicationComparator and then the evidenceMethods using UnambiguousCvTermComparator
 *
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>22/05/13</pre>
 */

public class UnambiguousCooperativityEvidenceComparator extends CooperativityEvidenceComparator {

    private static UnambiguousCooperativityEvidenceComparator unambiguousCooperativityEvidenceComparator;

    public UnambiguousCooperativityEvidenceComparator() {
        super(new UnambiguousCvTermComparator(), new UnambiguousPublicationComparator());
    }

    @Override
    public UnambiguousPublicationComparator getPublicationComparator() {
        return (UnambiguousPublicationComparator) super.getPublicationComparator();
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
     * Use UnambiguousCooperativityEvidenceComparator to know if two cooperativityEvidences are equals.
     * @param evidence1
     * @param evidence2
     * @return true if the two cooperativityEvidences are equal
     */
    public static boolean areEquals(CooperativityEvidence evidence1, CooperativityEvidence evidence2){
        if (unambiguousCooperativityEvidenceComparator == null){
            unambiguousCooperativityEvidenceComparator = new UnambiguousCooperativityEvidenceComparator();
        }

        return unambiguousCooperativityEvidenceComparator.compare(evidence1, evidence2) == 0;
    }

    /**
     *
     * @param evidence
     * @return the hashcode consistent with the equals method for this comparator
     */
    public static int hashCode(CooperativityEvidence evidence){
        if (unambiguousCooperativityEvidenceComparator == null){
            unambiguousCooperativityEvidenceComparator = new UnambiguousCooperativityEvidenceComparator();
        }


        if (evidence == null){
            return 0;
        }

        int hashcode = 31;
        Publication pub = evidence.getPublication();
        hashcode = 31*hashcode + UnambiguousPublicationComparator.hashCode(pub);

        List<CvTerm> list1 = new ArrayList<CvTerm>(evidence.getEvidenceMethods());
        Collections.sort(list1, unambiguousCooperativityEvidenceComparator.getCvTermsCollectionComparator().getObjectComparator());
        for (CvTerm term : list1){
            hashcode = 31*hashcode + UnambiguousCvTermComparator.hashCode(term);
        }

        return hashcode;
    }
}
