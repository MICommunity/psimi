package psidev.psi.mi.jami.utils.comparator.cooperativity;

import psidev.psi.mi.jami.model.CooperativeEffect;
import psidev.psi.mi.jami.model.CooperativityEvidence;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.ModelledInteraction;
import psidev.psi.mi.jami.utils.comparator.cv.UnambiguousCvTermComparator;
import psidev.psi.mi.jami.utils.comparator.interaction.UnambiguousExactInteractionBaseComparator;
import psidev.psi.mi.jami.utils.comparator.interaction.UnambiguousExactModelledInteractionComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Unambiguous exact comparator for CooperativeEffect
 *
 * It will first compare the outcome using UnambiguousCvTermComparator. Then it will compare the response using UnambiguousCvTermComparator.
 * Then it will compare the CooperativityEvidences using UnambiguousCooperativityEvidenceComparator.
 *
 * Finally it will compare the affected interactions using UnambiguousExactModelledInteractionComparator
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>23/05/13</pre>
 */

public class UnambiguousExactCooperativeEffectComparator extends CooperativeEffectComparator {

    private static UnambiguousExactCooperativeEffectComparator unambiguousExactCooperativeEffectComparator;

    public UnambiguousExactCooperativeEffectComparator() {
        super(new UnambiguousCvTermComparator(), new UnambiguousCooperativityEvidenceComparator(), new UnambiguousExactModelledInteractionComparator());
    }

    @Override
    public UnambiguousCvTermComparator getCvTermComparator() {
        return (UnambiguousCvTermComparator) super.getCvTermComparator();
    }

    /**
     * It will first compare the outcome using UnambiguousCvTermComparator. Then it will compare the response using UnambiguousCvTermComparator.
     * Then it will compare the CooperativityEvidences using UnambiguousCooperativityEvidenceComparator.
     *
     * Finally it will compare the affected interactions using UnambiguousExactModelledInteractionComparator
     */
    public int compare(CooperativeEffect effect1, CooperativeEffect effect2) {
        return super.compare(effect1, effect2);
    }

    /**
     * Use UnambiguousExactCooperativeEffectComparator to know if two CooperativeEffects are equals.
     * @param effect1
     * @param effect2
     * @return true if the two CooperativeEffects are equal
     */
    public static boolean areEquals(CooperativeEffect effect1, CooperativeEffect effect2){
        if (unambiguousExactCooperativeEffectComparator == null){
            unambiguousExactCooperativeEffectComparator = new UnambiguousExactCooperativeEffectComparator();
        }

        return unambiguousExactCooperativeEffectComparator.compare(effect1, effect2) == 0;
    }

    /**
     *
     * @param effect
     * @return the hashcode consistent with the equals method for this comparator
     */
    public static int hashCode(CooperativeEffect effect){
        if (unambiguousExactCooperativeEffectComparator == null){
            unambiguousExactCooperativeEffectComparator = new UnambiguousExactCooperativeEffectComparator();
        }

        if (effect == null){
            return 0;
        }

        int hashcode = 31;
        CvTerm outcome = effect.getOutCome();
        hashcode = 31*hashcode + UnambiguousCvTermComparator.hashCode(outcome);
        CvTerm response = effect.getResponse();
        hashcode = 31*hashcode + UnambiguousCvTermComparator.hashCode(response);

        List<CooperativityEvidence> list1 = new ArrayList<CooperativityEvidence>(effect.getCooperativityEvidences());
        Collections.sort(list1, unambiguousExactCooperativeEffectComparator.getCooperativityEvidenceCollectionComparator().getObjectComparator());
        for (CooperativityEvidence evidence : list1){
            hashcode = 31*hashcode + UnambiguousCooperativityEvidenceComparator.hashCode(evidence);
        }

        List<ModelledInteraction> list2 = new ArrayList<ModelledInteraction>(effect.getAffectedInteractions());
        Collections.sort(list2, unambiguousExactCooperativeEffectComparator.getModelledInteractionCollectionComparator().getObjectComparator());
        for (ModelledInteraction interaction : list2){
            hashcode = 31*hashcode + UnambiguousExactInteractionBaseComparator.hashCode(interaction);
        }

        return hashcode;
    }
}
