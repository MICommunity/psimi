package psidev.psi.mi.jami.utils.comparator.cooperativity;

import psidev.psi.mi.jami.model.CooperativeEffect;
import psidev.psi.mi.jami.model.CooperativityEvidence;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.ModelledInteraction;
import psidev.psi.mi.jami.utils.comparator.cv.DefaultCvTermComparator;
import psidev.psi.mi.jami.utils.comparator.interaction.DefaultExactModelledInteractionComparator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * Default exact comparator for CooperativeEffect
 *
 * It will first compare the outcome using DefaultCvTermComparator. Then it will compare the response using DefaultCvTermComparator.
 * Then it will compare the CooperativityEvidences using DefaultCooperativityEvidenceComparator.
 *
 * Finally it will compare the affected interactions using DefaultExactModelledInteractionComparator
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>23/05/13</pre>
 */

public class DefaultExactCooperativeEffectBaseComparator {

    /**
     * Use DefaultExactCooperativeEffectBaseComparator to know if two CooperativeEffects are equals.
     * @param cooperativeEffect1
     * @param cooperativeEffect2
     * @return true if the two CooperativeEffects are equal
     */
    public static boolean areEquals(CooperativeEffect cooperativeEffect1, CooperativeEffect cooperativeEffect2){
        if (cooperativeEffect1 == null && cooperativeEffect2 == null){
            return true;
        }
        else if (cooperativeEffect1 == null || cooperativeEffect2 == null){
            return false;
        }
        else {
            // first compare outcome
            CvTerm outcome1 = cooperativeEffect1.getOutCome();
            CvTerm outcome2 = cooperativeEffect2.getOutCome();

            if (!DefaultCvTermComparator.areEquals(outcome1, outcome2)){
                return false;
            }

            // then compare response
            CvTerm response1 = cooperativeEffect1.getResponse();
            CvTerm response2 = cooperativeEffect2.getResponse();

            if (!DefaultCvTermComparator.areEquals(response1, response2)){
                return false;
            }

            // then compare cooperativity evidences
            Collection<CooperativityEvidence> evidenceMethods1 = cooperativeEffect1.getCooperativityEvidences();
            Collection<CooperativityEvidence> evidenceMethods2 = cooperativeEffect2.getCooperativityEvidences();

            if (!areCooperativityEvidenceEqual(evidenceMethods1, evidenceMethods2)){
                return false;
            }

            // then compare affected Interactions
            Collection<ModelledInteraction> modelledInteractions1 = cooperativeEffect1.getAffectedInteractions();
            Collection<ModelledInteraction> modelledInteractions2 = cooperativeEffect2.getAffectedInteractions();

            return areModelledInteractionEqual(modelledInteractions1, modelledInteractions2);
        }
    }

    private static boolean areCooperativityEvidenceEqual(Collection<CooperativityEvidence> method1, Collection<CooperativityEvidence> method2) {
        if (method1.size() != method2.size()){
            return false;
        }
        else {
            Iterator<CooperativityEvidence> f1Iterator = new ArrayList<CooperativityEvidence>(method1).iterator();
            Collection<CooperativityEvidence> f2List = new ArrayList<CooperativityEvidence>(method2);

            while (f1Iterator.hasNext()){
                CooperativityEvidence f1 = f1Iterator.next();
                CooperativityEvidence f2ToRemove = null;
                for (CooperativityEvidence f2 : f2List){
                    if (DefaultCooperativityEvidenceComparator.areEquals(f1, f2)){
                        f2ToRemove = f2;
                        break;
                    }
                }
                if (f2ToRemove != null){
                    f2List.remove(f2ToRemove);
                    f1Iterator.remove();
                }
                else {
                    return false;
                }
            }

            if (f1Iterator.hasNext() || !f2List.isEmpty()){
                return false;
            }
            else{
                return true;
            }
        }
    }

    private static boolean areModelledInteractionEqual(Collection<ModelledInteraction> method1, Collection<ModelledInteraction> method2) {
        if (method1.size() != method2.size()){
            return false;
        }
        else {
            Iterator<ModelledInteraction> f1Iterator = new ArrayList<ModelledInteraction>(method1).iterator();
            Collection<ModelledInteraction> f2List = new ArrayList<ModelledInteraction>(method2);

            while (f1Iterator.hasNext()){
                ModelledInteraction f1 = f1Iterator.next();
                ModelledInteraction f2ToRemove = null;
                for (ModelledInteraction f2 : f2List){
                    if (DefaultExactModelledInteractionComparator.areEquals(f1, f2)){
                        f2ToRemove = f2;
                        break;
                    }
                }
                if (f2ToRemove != null){
                    f2List.remove(f2ToRemove);
                    f1Iterator.remove();
                }
                else {
                    return false;
                }
            }

            if (f1Iterator.hasNext() || !f2List.isEmpty()){
                return false;
            }
            else{
                return true;
            }
        }
    }
}
