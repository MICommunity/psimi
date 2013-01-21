package psidev.psi.mi.jami.utils.comparator.interaction;

import psidev.psi.mi.jami.model.ExperimentalInteraction;
import psidev.psi.mi.jami.model.Interaction;
import psidev.psi.mi.jami.model.Source;

import java.util.Comparator;

/**
 * TODO comment this
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>18/01/13</pre>
 */

public class ExperimentalInteractionComparator implements Comparator<ExperimentalInteraction> {

    protected Comparator<Interaction> interactionComparator;

    public ExperimentalInteractionComparator(Comparator<Interaction> interactionComparator){
        if (interactionComparator == null){
            throw new IllegalArgumentException("The Interaction comparator is required to compare basic interaction properties. It cannot be null");
        }
        this.interactionComparator = interactionComparator;
    }

    public Comparator<Interaction> getInteractionComparator() {
        return interactionComparator;
    }

    public int compare(ExperimentalInteraction experimentalInteraction1, ExperimentalInteraction experimentalInteraction2) {
        int EQUAL = 0;
        int BEFORE = -1;
        int AFTER = 1;

        if (experimentalInteraction1 == null && experimentalInteraction2 == null){
            return EQUAL;
        }
        else if (experimentalInteraction1 == null){
            return AFTER;
        }
        else if (experimentalInteraction2 == null){
            return BEFORE;
        }
        else {
            // first compares the IMEx id
            String imex1 = experimentalInteraction1.getImexId();
            String imex2 = experimentalInteraction2.getImexId();

            if (imex1 != null && imex2 != null){
                return imex1.compareTo(imex2);
            }

            // compares
        }
    }
}
