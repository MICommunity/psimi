package psidev.psi.mi.jami.utils.comparator.cooperativity;

import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.utils.comparator.cv.AbstractCvTermComparator;
import psidev.psi.mi.jami.utils.comparator.interaction.ModelledInteractionCollectionComparator;
import psidev.psi.mi.jami.utils.comparator.interaction.ModelledInteractionComparator;

import java.util.Collection;
import java.util.Comparator;

/**
 * Basic comparator for CooperativeEffect
 *
 * It will first compare the outcome using AbstractCvTermComparator. Then it will compare the response using AbstractCvTermComparator.
 * Then it will compare the CooperativityEvidences using CooperativityEvidenceComparator.
 *
 * Finally it will compare the affected interactions using ModelledInteractionComparator
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>22/05/13</pre>
 */

public class CooperativeEffectBaseComparator implements Comparator<CooperativeEffect>{

    private AbstractCvTermComparator cvTermComparator;
    private CooperativityEvidenceCollectionComparator cooperativityEvidenceCollectionComparator;
    private ModelledInteractionCollectionComparator modelledInteractionCollectionComparator;

    public CooperativeEffectBaseComparator(AbstractCvTermComparator cvTermComparator, CooperativityEvidenceComparator cooperativityEvidenceComparator, ModelledInteractionComparator modelledInteractionComparator){
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

    /**
     * It will first compare the outcome using AbstractCvTermComparator. Then it will compare the response using AbstractCvTermComparator.
     * Then it will compare the CooperativityEvidences using CooperativityEvidenceComparator.
     *
     * Finally it will compare the affected interactions using ModelledInteractionComparator
     * @param cooperativeEffect1
     * @param cooperativeEffect2
     * @return
     */
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
            // first compare outcome
            CvTerm outcome1 = cooperativeEffect1.getOutCome();
            CvTerm outcome2 = cooperativeEffect2.getOutCome();

            int comp = cvTermComparator.compare(outcome1, outcome2);
            if (comp != 0){
                return comp;
            }

            // then compare response
            CvTerm response1 = cooperativeEffect1.getResponse();
            CvTerm response2 = cooperativeEffect2.getResponse();

            comp = cvTermComparator.compare(response1, response2);
            if (comp != 0){
                return comp;
            }

            // then compare cooperativity evidences
            Collection<CooperativityEvidence> evidenceMethods1 = cooperativeEffect1.getCooperativityEvidences();
            Collection<CooperativityEvidence> evidenceMethods2 = cooperativeEffect2.getCooperativityEvidences();

            comp = cooperativityEvidenceCollectionComparator.compare(evidenceMethods1, evidenceMethods2);
            if (comp != 0){
                return comp;
            }

            // then compare affected Interactions
            Collection<ModelledInteraction> modelledInteractions1 = cooperativeEffect1.getAffectedInteractions();
            Collection<ModelledInteraction> modelledInteractions2 = cooperativeEffect2.getAffectedInteractions();

            return modelledInteractionCollectionComparator.compare(modelledInteractions1, modelledInteractions2);
        }
    }
}
