package psidev.psi.mi.jami.utils.clone;

import psidev.psi.mi.jami.model.CooperativityEvidence;

/**
 * Utility class for cloning cooperativity evidences
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>13/02/13</pre>
 */

public class CooperativityEvidenceCloner {

    /***
     * This method will copy properties of CooperativityEvidence source in CooperativityEvidence target and will override all the other properties of Target CooperativityEvidence.
     * @param source : the cooperativity evidence source to copy from
     * @param target : the cooperativity evidence target to copy to
     */
    public static void copyAndOverrideCooperativityEvidenceProperties(CooperativityEvidence source, CooperativityEvidence target){
        if (source != null && target != null){
            target.setPublication(source.getPublication());

            // copy collections
            target.getEvidenceMethods().clear();
            target.getEvidenceMethods().addAll(source.getEvidenceMethods());
        }
    }
}
