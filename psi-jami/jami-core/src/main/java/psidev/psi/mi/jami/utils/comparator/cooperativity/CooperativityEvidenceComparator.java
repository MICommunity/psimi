package psidev.psi.mi.jami.utils.comparator.cooperativity;

import psidev.psi.mi.jami.model.CooperativityEvidence;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Publication;
import psidev.psi.mi.jami.utils.comparator.cv.CvTermsCollectionComparator;

import java.util.Collection;
import java.util.Comparator;

/**
 * Basic comparator for cooperativityEvidence
 *
 * It will first compare the publications using AbstractPublicationComparator and then the evidenceMethods using AbstractCvTermComparator
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>22/05/13</pre>
 */

public class CooperativityEvidenceComparator implements Comparator<CooperativityEvidence> {

    private CvTermsCollectionComparator cvTermsCollectionComparator;
    private Comparator<Publication> publicationComparator;

    public CooperativityEvidenceComparator(Comparator<CvTerm> cvTermComparator, Comparator<Publication> publicationComparator){

        if (cvTermComparator == null){
            throw new IllegalArgumentException("The cvTermComparator cannot be null as we need one to compare evidenceMethods of a cooperativityEvidence");
        }
        this.cvTermsCollectionComparator = new CvTermsCollectionComparator(cvTermComparator);
        if (publicationComparator == null){
            throw new IllegalArgumentException("The publicationComparator cannot be null as we need one to compare publications of a cooperativityEvidence");
        }
        this.publicationComparator = publicationComparator;
    }

    public CvTermsCollectionComparator getCvTermsCollectionComparator() {
        return cvTermsCollectionComparator;
    }

    public Comparator<Publication> getPublicationComparator() {
        return publicationComparator;
    }

    /**
     * It will first compare the publications using AbstractPublicationComparator and then the evidenceMethods using AbstractCvTermComparator
     * @param cooperativityEvidence1
     * @param cooperativityEvidence2
     * @return
     */
    public int compare(CooperativityEvidence cooperativityEvidence1, CooperativityEvidence cooperativityEvidence2) {
        int EQUAL = 0;
        int BEFORE = -1;
        int AFTER = 1;

        if (cooperativityEvidence1 == null && cooperativityEvidence2 == null){
            return EQUAL;
        }
        else if (cooperativityEvidence1 == null){
            return AFTER;
        }
        else if (cooperativityEvidence2 == null){
            return BEFORE;
        }
        else {

            Publication pub1 = cooperativityEvidence1.getPublication();
            Publication pub2 = cooperativityEvidence2.getPublication();
            
            int comp = publicationComparator.compare(pub1, pub2);
            if (comp != 0){
                return comp;
            }

            Collection<CvTerm> evidenceMethods1 = cooperativityEvidence1.getEvidenceMethods();
            Collection<CvTerm> evidenceMethods2 = cooperativityEvidence2.getEvidenceMethods();

            return cvTermsCollectionComparator.compare(evidenceMethods1, evidenceMethods2);
        }
    }
}
