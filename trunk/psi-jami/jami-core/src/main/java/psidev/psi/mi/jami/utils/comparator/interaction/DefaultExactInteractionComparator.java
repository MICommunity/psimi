package psidev.psi.mi.jami.utils.comparator.interaction;

import psidev.psi.mi.jami.model.Interaction;

/**
 * Default exact Generic interaction comparator.
 * Modelled interactions come first, then experimental interactions
 * - It uses DefaultExactInteractionEvidenceComparator to compare experimental interactions
 * - It uses DefaultExactModelledInteractionComparator to compare modelled interactions
 * - It uses DefaultExactInteractionBaseComparator to compare basic interaction properties
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>05/02/13</pre>
 */

public class DefaultExactInteractionComparator extends InteractionComparator {

    private static DefaultExactInteractionComparator defaultExactInteractionComparator;

    /**
     * Creates a new DefaultExactInteractionComparator.
     */
    public DefaultExactInteractionComparator() {
        super(new DefaultInteractionBaseComparator(), new DefaultExactModelledInteractionComparator(), new DefaultExactInteractionEvidenceComparator());
    }

    @Override
    public DefaultInteractionBaseComparator getInteractionBaseComparator() {
        return (DefaultInteractionBaseComparator) this.interactionBaseComparator;
    }

    @Override
    public DefaultExactInteractionEvidenceComparator getExperimentalInteractionComparator() {
        return (DefaultExactInteractionEvidenceComparator) this.experimentalInteractionComparator;
    }

    @Override
    public DefaultExactModelledInteractionComparator getModelledInteractionComparator() {
        return (DefaultExactModelledInteractionComparator) super.getModelledInteractionComparator();
    }

    @Override
    /**
     * Modelled interactions come first, then experimental interactions
     * - It uses DefaultExactInteractionEvidenceComparator to compare experimental interactions
     * - It uses DefaultExactModelledInteractionComparator to compare modelled interactions
     * - It uses DefaultExactInteractionBaseComparator to compare basic interaction properties
     */
    public int compare(Interaction interaction1, Interaction interaction2) {
        return super.compare(interaction1, interaction2);
    }

    /**
     * Use DefaultExactInteractionComparator to know if two interactions are equals.
     * @param interaction1
     * @param interaction2
     * @return true if the two interactions are equal
     */
    public static boolean areEquals(Interaction interaction1, Interaction interaction2){
        if (defaultExactInteractionComparator == null){
            defaultExactInteractionComparator = new DefaultExactInteractionComparator();
        }

        return defaultExactInteractionComparator.compare(interaction1, interaction2) == 0;
    }
}
