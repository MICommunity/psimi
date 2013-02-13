package psidev.psi.mi.jami.utils.comparator.interaction;

import psidev.psi.mi.jami.model.AllostericInteraction;
import psidev.psi.mi.jami.utils.comparator.cv.DefaultCvTermComparator;
import psidev.psi.mi.jami.utils.comparator.feature.DefaultModelledFeatureComparator;
import psidev.psi.mi.jami.utils.comparator.participant.DefaultExactComponentComparator;

/**
 * Default exact curated Allosteric interaction comparator.
 *
 * It will first compare the allosteric mechanisms using DefaultCvTermComparator. If the mechanisms are the same, it will compare the allosteric types
 * using DefaultCvTermComparator. If the allosteric types are the same, it will compare the allosteric molecule using DefaultExactComponentComparator.
 * If the allosteric molecules are the same, it will compare the allosteric effectors using the DefaultExactComponentComparator.
 * If the allosteric effectors are the same, it will compare the allosteric PTMs using DefaultModelledFeatureComparator. If the allosteric PTMs are the same,
 * it will compare the basic properties of a cooperative interaction using DefaultExactCuratedCooperativeInteractionComparator.
 *
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>21/01/13</pre>
 */

public class DefaultExactCuratedAllostericInteractionComparator extends AllostericInteractionComparator{

    private static DefaultExactCuratedAllostericInteractionComparator defaultExactCuratedAllostericInteractionComparator;

    /**
     * Creates a new DefaultExactCuratedAllostericInteractionComparator. It will use a DefaultExactCuratedCooperativeInteractionComparator to
     * compare basic cooperative interaction properties, DefaultCvTermComparator to compare responses, outcome effects and mechanisms,
     * DefaultExactComponentComparator to compare allosteric molecules and effectors and DefaultModelledFeatureComparator to compare allosteric PTMs
     */
    public DefaultExactCuratedAllostericInteractionComparator() {
        super(new DefaultExactCuratedCooperativeInteractionComparator(), new DefaultCvTermComparator(), new DefaultExactComponentComparator(), new DefaultModelledFeatureComparator());
    }

    @Override
    public DefaultExactCuratedCooperativeInteractionComparator getInteractionComparator() {
        return (DefaultExactCuratedCooperativeInteractionComparator) this.interactionComparator;
    }

    @Override
    public DefaultCvTermComparator getCvTermComparator() {
        return (DefaultCvTermComparator) this.cvTermComparator;
    }

    @Override
    public DefaultExactComponentComparator getComponentComparator() {
        return (DefaultExactComponentComparator) this.componentComparator;
    }

    @Override
    public DefaultModelledFeatureComparator getPtmComparator() {
        return (DefaultModelledFeatureComparator) ptmComparator;
    }

    @Override
    /**
     * It will first compare the allosteric mechanisms using DefaultCvTermComparator. If the mechanisms are the same, it will compare the allosteric types
     * using DefaultCvTermComparator. If the allosteric types are the same, it will compare the allosteric molecule using DefaultExactComponentComparator.
     * If the allosteric molecules are the same, it will compare the allosteric effectors using the DefaultExactComponentComparator.
     * If the allosteric effectors are the same, it will compare the allosteric PTMs using DefaultModelledFeatureComparator. If the allosteric PTMs are the same,
     * it will compare the basic properties of a cooperative interaction using DefaultExactCuratedCooperativeInteractionComparator.
     *
     **/
    public int compare(AllostericInteraction interaction1, AllostericInteraction interaction2) {
        return super.compare(interaction1, interaction2);
    }

    /**
     * Use DefaultExactCuratedAllostericInteractionComparator to know if two allosteric interactions are equals.
     * @param interaction1
     * @param interaction2
     * @return true if the two allosteric interactions are equal
     */
    public static boolean areEquals(AllostericInteraction interaction1, AllostericInteraction interaction2){
        if (defaultExactCuratedAllostericInteractionComparator == null){
            defaultExactCuratedAllostericInteractionComparator = new DefaultExactCuratedAllostericInteractionComparator();
        }

        return defaultExactCuratedAllostericInteractionComparator.compare(interaction1, interaction2) == 0;
    }
}
