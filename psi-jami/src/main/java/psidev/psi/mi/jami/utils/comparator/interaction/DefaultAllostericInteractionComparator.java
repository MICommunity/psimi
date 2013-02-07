package psidev.psi.mi.jami.utils.comparator.interaction;

import psidev.psi.mi.jami.model.AllostericInteraction;
import psidev.psi.mi.jami.utils.comparator.cv.DefaultCvTermComparator;
import psidev.psi.mi.jami.utils.comparator.feature.DefaultBiologicalFeatureComparator;
import psidev.psi.mi.jami.utils.comparator.participant.DefaultComponentComparator;

/**
 * Default Allosteric interaction comparator.
 *
 * It will first compare the basic properties of a cooperative interaction using DefaultCooperativeInteractionComparator. It will then compare the allosteric mechanisms using DefaultCvTermComparator. If the mechanisms are the same, it will compare the allosteric types
 * using DefaultCvTermComparator. If the allosteric types are the same, it will compare the allosteric molecule using DefaultComponentComparator.
 * If the allosteric molecules are the same, it will compare the allosteric effectors using the DefaultComponentComparator.
 * If the allosteric effectors are the same, it will compare the allosteric PTMs using DefaultBiologicalFeatureComparator.
 *
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>21/01/13</pre>
 */

public class DefaultAllostericInteractionComparator extends AllostericInteractionComparator {

    private static DefaultAllostericInteractionComparator defaultAllostericInteractionComparator;

    /**
     * Creates a new DefaultAllostericInteractionComparator. It will use a DefaultCooperativeInteractionComparator to
     * compare basic cooperative interaction properties, DefaultCvTermComparator to compare responses, outcome effects and mechanisms,
     * DefaultComponentComparator to compare allosteric molecules and effectors and DefaultBiologicalFeatureComparator to compare allosteric PTMs
     */
    public DefaultAllostericInteractionComparator() {
        super(new DefaultCooperativeInteractionComparator(), new DefaultCvTermComparator(), new DefaultComponentComparator(), new DefaultBiologicalFeatureComparator());
    }

    @Override
    public DefaultCooperativeInteractionComparator getInteractionComparator() {
        return (DefaultCooperativeInteractionComparator) this.interactionComparator;
    }

    @Override
    public DefaultCvTermComparator getCvTermComparator() {
        return (DefaultCvTermComparator) this.cvTermComparator;
    }

    @Override
    public DefaultComponentComparator getComponentComparator() {
        return (DefaultComponentComparator) this.componentComparator;
    }

    @Override
    public DefaultBiologicalFeatureComparator getPtmComparator() {
        return (DefaultBiologicalFeatureComparator) ptmComparator;
    }

    @Override
    /**
     * It will first compare the basic properties of a cooperative interaction using DefaultCooperativeInteractionComparator. It will then compare the allosteric mechanisms using DefaultCvTermComparator. If the mechanisms are the same, it will compare the allosteric types
     * using DefaultCvTermComparator. If the allosteric types are the same, it will compare the allosteric molecule using DefaultComponentComparator.
     * If the allosteric molecules are the same, it will compare the allosteric effectors using the DefaultComponentComparator.
     * If the allosteric effectors are the same, it will compare the allosteric PTMs using DefaultBiologicalFeatureComparator.
     *
     *
     **/
    public int compare(AllostericInteraction interaction1, AllostericInteraction interaction2) {
        return super.compare(interaction1, interaction2);
    }

    /**
     * Use DefaultAllostericInteractionComparator to know if two allosteric interactions are equals.
     * @param interaction1
     * @param interaction2
     * @return true if the two allosteric interactions are equal
     */
    public static boolean areEquals(AllostericInteraction interaction1, AllostericInteraction interaction2){
        if (defaultAllostericInteractionComparator == null){
            defaultAllostericInteractionComparator = new DefaultAllostericInteractionComparator();
        }

        return defaultAllostericInteractionComparator.compare(interaction1, interaction2) == 0;
    }
}
