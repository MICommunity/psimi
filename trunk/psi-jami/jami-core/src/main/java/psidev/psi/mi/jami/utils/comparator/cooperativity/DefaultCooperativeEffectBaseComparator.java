package psidev.psi.mi.jami.utils.comparator.cooperativity;

import psidev.psi.mi.jami.model.CooperativeEffect;
import psidev.psi.mi.jami.utils.comparator.cv.DefaultCvTermComparator;
import psidev.psi.mi.jami.utils.comparator.interaction.DefaultModelledInteractionComparator;

/**
 * Default comparator for CooperativeEffect
 *
 * It will first compare the outcome using DefaultCvTermComparator. Then it will compare the response using DefaultCvTermComparator.
 * Then it will compare the CooperativityEvidences using DefaultCooperativityEvidenceComparator.
 *
 * Finally it will compare the affected interactions using DefaultModelledInteractionComparator
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>23/05/13</pre>
 */

public class DefaultCooperativeEffectBaseComparator extends CooperativeEffectBaseComparator {

    private static DefaultCooperativeEffectBaseComparator defaultCooperativeEffectComparator;

    public DefaultCooperativeEffectBaseComparator() {
        super(new DefaultCvTermComparator(), new DefaultCooperativityEvidenceComparator(), new DefaultModelledInteractionComparator());
    }

    @Override
    public DefaultCvTermComparator getCvTermComparator() {
        return (DefaultCvTermComparator) super.getCvTermComparator();
    }

    /**
     * It will first compare the outcome using DefaultCvTermComparator. Then it will compare the response using DefaultCvTermComparator.
     * Then it will compare the CooperativityEvidences using DefaultCooperativityEvidenceComparator.
     *
     * Finally it will compare the affected interactions using DefaultModelledInteractionComparator
     */
    public int compare(CooperativeEffect effect1, CooperativeEffect effect2) {
        return super.compare(effect1, effect2);
    }

    /**
     * Use DefaultCooperativeEffectBaseComparator to know if two CooperativeEffects are equals.
     * @param effect1
     * @param effect2
     * @return true if the two CooperativeEffects are equal
     */
    public static boolean areEquals(CooperativeEffect effect1, CooperativeEffect effect2){
        if (defaultCooperativeEffectComparator == null){
            defaultCooperativeEffectComparator = new DefaultCooperativeEffectBaseComparator();
        }

        return defaultCooperativeEffectComparator.compare(effect1, effect2) == 0;
    }
}
