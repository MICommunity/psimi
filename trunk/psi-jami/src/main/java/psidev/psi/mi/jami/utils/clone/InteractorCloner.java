package psidev.psi.mi.jami.utils.clone;

import psidev.psi.mi.jami.model.Complex;
import psidev.psi.mi.jami.model.Interactor;

/**
 * Cloner for interactors
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>13/02/13</pre>
 */

public class InteractorCloner {

    /***
     * This method will copy properties of interactor source in interactor target and will override all the other properties of Target interactor.
     * @param source
     * @param target
     */
    public static void copyAndOverrideInteractorProperties(Interactor source, Interactor target){
        if (source != null && target != null){
            target.setShortName(source.getShortName());
            target.setFullName(source.getFullName());
            target.setType(source.getType());
            target.setOrganism(source.getOrganism());

            // copy collections
            target.getAnnotations().clear();
            target.getAnnotations().addAll(source.getAnnotations());
            target.getIdentifiers().clear();
            target.getIdentifiers().addAll(source.getIdentifiers());
            target.getAliases().clear();
            target.getAliases().addAll(source.getAliases());
            target.getXrefs().clear();
            target.getXrefs().addAll(source.getXrefs());
            target.getChecksums().clear();
            target.getChecksums().addAll(source.getChecksums());
        }
    }

    /***
     * This method will copy properties of complex source in complex target and will override all the other properties of Target complex.
     * @param source
     * @param target
     */
    public static void copyAndOverrideComplexProperties(Complex source, Complex target){
        if (source != null && target != null){
            copyAndOverrideInteractorProperties(source, target);

            target.setPhysicalProperties(source.getPhysicalProperties());

            // copy collections
            target.getExperiments().clear();
            target.getExperiments().addAll(source.getExperiments());
            target.getComponents().clear();
            target.addAllComponents(source.getComponents());
        }
    }
}
