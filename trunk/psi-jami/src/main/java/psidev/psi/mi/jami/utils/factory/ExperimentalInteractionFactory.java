package psidev.psi.mi.jami.utils.factory;

import psidev.psi.mi.jami.model.ExperimentalInteraction;
import psidev.psi.mi.jami.model.impl.DefaultExperimentalInteraction;

/**
 * Factory for experimental interactions
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>11/02/13</pre>
 */

public class ExperimentalInteractionFactory {
    
    public static ExperimentalInteraction createEmptyBasicExperimentalInteraction(){

        return new DefaultExperimentalInteraction(ExperimentFactory.createUnknownBasicExperiment());
    }
}
