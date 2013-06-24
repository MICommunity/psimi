package psidev.psi.mi.jami.utils.comparator.cooperativity;

import psidev.psi.mi.jami.model.CooperativityEvidence;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Publication;
import psidev.psi.mi.jami.utils.comparator.ComparatorUtils;
import psidev.psi.mi.jami.utils.comparator.publication.DefaultPublicationComparator;

import java.util.Collection;

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

public class DefaultCooperativityEvidenceComparator {

    /**
     * Use DefaultCooperativityEvidenceComparator to know if two cooperativityEvidences are equals.
     * @param cooperativityEvidence1
     * @param cooperativityEvidence2
     * @return true if the two cooperativityEvidences are equal
     */
    public static boolean areEquals(CooperativityEvidence cooperativityEvidence1, CooperativityEvidence cooperativityEvidence2){
        if (cooperativityEvidence1 == null && cooperativityEvidence2 == null){
            return true;
        }
        else if (cooperativityEvidence1 == null || cooperativityEvidence2 == null){
            return false;
        }
        else {

            Publication pub1 = cooperativityEvidence1.getPublication();
            Publication pub2 = cooperativityEvidence2.getPublication();

            if (!DefaultPublicationComparator.areEquals(pub1, pub2)){
               return false;
            }

            Collection<CvTerm> evidenceMethods1 = cooperativityEvidence1.getEvidenceMethods();
            Collection<CvTerm> evidenceMethods2 = cooperativityEvidence2.getEvidenceMethods();

            return ComparatorUtils.areCvTermsEqual(evidenceMethods1, evidenceMethods2);
        }
    }
}
