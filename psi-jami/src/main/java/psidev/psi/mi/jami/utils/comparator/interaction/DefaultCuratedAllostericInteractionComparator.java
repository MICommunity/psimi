package psidev.psi.mi.jami.utils.comparator.interaction;

import psidev.psi.mi.jami.model.AllostericInteraction;
import psidev.psi.mi.jami.utils.comparator.cv.DefaultCvTermComparator;
import psidev.psi.mi.jami.utils.comparator.feature.DefaultModelledFeatureComparator;
import psidev.psi.mi.jami.utils.comparator.participant.DefaultComponentComparator;

/**
 * Default curated Allosteric interaction comparator.
 *
 * It will first compare the allosteric mechanisms using DefaultCvTermComparator. If the mechanisms are the same, it will compare the allosteric types
 * using DefaultCvTermComparator. If the allosteric types are the same, it will compare the allosteric molecule using DefaultComponentComparator.
 * If the allosteric molecules are the same, it will compare the allosteric effectors using the DefaultComponentComparator.
 * If the allosteric effectors are the same, it will compare the allosteric PTMs using DefaultModelledFeatureComparator. If the allosteric PTMs are the same,
 * it will compare the basic properties of a cooperative interaction using DefaultCuratedCooperativeInteractionComparator.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>21/01/13</pre>
 */

public class DefaultCuratedAllostericInteractionComparator extends AllostericInteractionComparator{

    private static DefaultCuratedAllostericInteractionComparator defaultCuratedAllostericInteractionComparator;

    /**
     * Creates a new DefaultCuratedAllostericInteractionComparator. It will use a DefaultCuratedCooperativeInteractionComparator to
     * compare basic cooperative interaction properties, DefaultCvTermComparator to compare responses, outcome effects and mechanisms,
     * DefaultComponentComparator to compare allosteric molecules and effectors and DefaultModelledFeatureComparator to compare allosteric PTMs
     */
    public DefaultCuratedAllostericInteractionComparator() {
        super(new DefaultCuratedCooperativeInteractionComparator(), new DefaultCvTermComparator(), new DefaultComponentComparator(), new DefaultModelledFeatureComparator());
    }

    @Override
    public DefaultCuratedCooperativeInteractionComparator getInteractionComparator() {
        return (DefaultCuratedCooperativeInteractionComparator) this.interactionComparator;
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
    public DefaultModelledFeatureComparator getPtmComparator() {
        return (DefaultModelledFeatureComparator) ptmComparator;
    }

    @Override
    /**
     * It will first compare the allosteric mechanisms using DefaultCvTermComparator. If the mechanisms are the same, it will compare the allosteric types
     * using DefaultCvTermComparator. If the allosteric types are the same, it will compare the allosteric molecule using DefaultComponentComparator.
     * If the allosteric molecules are the same, it will compare the allosteric effectors using the DefaultComponentComparator.
     * If the allosteric effectors are the same, it will compare the allosteric PTMs using DefaultModelledFeatureComparator. If the allosteric PTMs are the same,
     * it will compare the basic properties of a cooperative interaction using DefaultCuratedCooperativeInteractionComparator.
     *
     **/
    public int compare(AllostericInteraction interaction1, AllostericInteraction interaction2) {
        return super.compare(interaction1, interaction2);
    }

    /**
     * Use DefaultCuratedAllostericInteractionComparator to know if two allosteric interactions are equals.
     * @param interaction1
     * @param interaction2
     * @return true if the two allosteric interactions are equal
     */
    public static boolean areEquals(AllostericInteraction interaction1, AllostericInteraction interaction2){
        if (defaultCuratedAllostericInteractionComparator == null){
            defaultCuratedAllostericInteractionComparator = new DefaultCuratedAllostericInteractionComparator();
        }

        return defaultCuratedAllostericInteractionComparator.compare(interaction1, interaction2) == 0;
    }
}
