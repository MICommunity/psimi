package psidev.psi.mi.jami.utils.factory;

import psidev.psi.mi.jami.model.Interactor;
import psidev.psi.mi.jami.model.impl.DefaultInteractor;

/**
 * Factory for interactors
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>11/02/13</pre>
 */

public class InteractorFactory {

    public static Interactor createUnknownBasicInteractor(){
        return new DefaultInteractor("unknown", CvTermFactory.createMICvTerm(Interactor.UNKNOWN_INTERACTOR, Interactor.UNKNOWN_INTERACTOR_MI));
    }
}
