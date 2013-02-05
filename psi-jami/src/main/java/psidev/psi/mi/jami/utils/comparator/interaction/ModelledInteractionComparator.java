package psidev.psi.mi.jami.utils.comparator.interaction;

import psidev.psi.mi.jami.model.Component;
import psidev.psi.mi.jami.model.ModelledInteraction;

import java.util.Comparator;

/**
 * Basic ModelledInteraction comparator.
 *
 * It will use a InteractionBaseComparator<Component> to compare basic interaction properties.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>18/01/13</pre>
 */

public class ModelledInteractionComparator implements Comparator<ModelledInteraction> {

    protected InteractionBaseComparator<Component> interactionComparator;

    public ModelledInteractionComparator(InteractionBaseComparator<Component> interactionComparator){
        if (interactionComparator == null){
            throw new IllegalArgumentException("The Interaction comparator is required to compare basic interaction properties. It cannot be null");
        }
        this.interactionComparator = interactionComparator;
    }

    public InteractionBaseComparator<Component> getInteractionComparator() {
        return interactionComparator;
    }

    /**
     * It will use a InteractionBaseComparator<Component> to compare basic interaction properties.
     * @param modelledInteraction1
     * @param modelledInteraction2
     * @return
     */
    public int compare(ModelledInteraction modelledInteraction1, ModelledInteraction modelledInteraction2) {
        return interactionComparator.compare(modelledInteraction1, modelledInteraction2);
    }
}
