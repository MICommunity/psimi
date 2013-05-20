package psidev.psi.mi.jami.utils.factory;

import psidev.psi.mi.jami.model.InteractionEvidence;
import psidev.psi.mi.jami.model.impl.DefaultInteractionEvidence;

/**
 * Factory for experimental interactions
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>11/02/13</pre>
 */

public class ExperimentalInteractionFactory {
    
    public static InteractionEvidence createEmptyBasicExperimentalInteraction(){

        return new DefaultInteractionEvidence(ExperimentFactory.createUnknownBasicExperiment());
    }
}
