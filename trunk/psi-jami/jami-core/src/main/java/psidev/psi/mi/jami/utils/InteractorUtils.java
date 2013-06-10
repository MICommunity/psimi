package psidev.psi.mi.jami.utils;

import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Interactor;
import psidev.psi.mi.jami.model.impl.DefaultInteractor;

/**
 * Factory for interactors
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>11/02/13</pre>
 */

public class InteractorUtils {

    public static Interactor createUnknownBasicInteractor(){
        return new DefaultInteractor("unknown", CvTermUtils.createMICvTerm(Interactor.UNKNOWN_INTERACTOR, Interactor.UNKNOWN_INTERACTOR_MI));
    }

    /**
     * To know if an interactor have a specific interactor type.
     * @param interactor
     * @param typeId
     * @param typeName
     * @return true if the interactor has the type with given name/identifier
     */
    public static boolean doesInteractorHaveType(Interactor interactor, String typeId, String typeName){

        if (interactor == null || (typeName == null && typeId == null)){
            return false;
        }

        CvTerm type = interactor.getInteractorType();
        if (type == null){
            return false;
        }

        // we can compare identifiers
        if (typeId != null && type.getMIIdentifier() != null){
            // we have the same type id
            return type.getMIIdentifier().equals(typeId);
        }
        // we need to compare type names
        else if (typeName != null) {
            return typeName.toLowerCase().trim().equals(type.getShortName().toLowerCase().trim());
        }

        return false;
    }
}
