package psidev.psi.mi.jami.utils.comparator.cooperativity;

import psidev.psi.mi.jami.model.CooperativeEffect;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Publication;
import psidev.psi.mi.jami.utils.comparator.cv.AbstractCvTermComparator;
import psidev.psi.mi.jami.utils.comparator.interaction.ModelledInteractionCollectionComparator;
import psidev.psi.mi.jami.utils.comparator.interaction.ModelledInteractionComparator;

import java.util.Collection;
import java.util.Comparator;

/**
 * Basic comparator for CooperativeEffect
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>22/05/13</pre>
 */

public class CooperativeEffectComparator implements Comparator<CooperativeEffect>{

    private AbstractCvTermComparator cvTermComparator;
    private CooperativityEvidenceCollectionComparator cooperativityEvidenceCollectionComparator;
    private ModelledInteractionCollectionComparator modelledInteractionCollectionComparator;

    private CooperativeEffectComparator(AbstractCvTermComparator cvTermComparator, CooperativityEvidenceComparator cooperativityEvidenceComparator, ModelledInteractionComparator modelledInteractionComparator){
        if (cvTermComparator == null){
            throw new IllegalArgumentException("The cvTermComparator cannot be null and is required for comparing outcome and response.");
        }
        this.cvTermComparator = cvTermComparator;

        if (cooperativityEvidenceComparator == null){
            throw new IllegalArgumentException("The cooperativityEvidenceComparator cannot be null and is required for comparing cooperativity evidences.");
        }
        this.cooperativityEvidenceCollectionComparator = new CooperativityEvidenceCollectionComparator(cooperativityEvidenceComparator);

        if (modelledInteractionComparator == null){
            throw new IllegalArgumentException("The modelledInteractionComparator cannot be null and is required for comparing affected interactions.");
        }
        this.modelledInteractionCollectionComparator = new ModelledInteractionCollectionComparator(modelledInteractionComparator);
    }

    public AbstractCvTermComparator getCvTermComparator() {
        return cvTermComparator;
    }

    public CooperativityEvidenceCollectionComparator getCooperativityEvidenceCollectionComparator() {
        return cooperativityEvidenceCollectionComparator;
    }

    public ModelledInteractionCollectionComparator getModelledInteractionCollectionComparator() {
        return modelledInteractionCollectionComparator;
    }

    public int compare(CooperativeEffect cooperativeEffect1, CooperativeEffect cooperativeEffect2) {
        int EQUAL = 0;
        int BEFORE = -1;
        int AFTER = 1;

        if (cooperativeEffect1 == null && cooperativeEffect2 == null){
            return EQUAL;
        }
        else if (cooperativeEffect1 == null){
            return AFTER;
        }
        else if (cooperativeEffect2 == null){
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
