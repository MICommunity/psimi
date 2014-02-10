package psidev.psi.mi.jami.utils.clone;

import psidev.psi.mi.jami.model.AllostericEffector;
import psidev.psi.mi.jami.model.Allostery;
import psidev.psi.mi.jami.model.CooperativeEffect;

/**
 * Utility class for cloning cooperativity evidences
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>13/02/13</pre>
 */

public class CooperativeEffectCloner {

    /***
     * This method will copy basic properties of CooperativeEffect source in CooperativeEffect target and will override all the other properties of Target CooperativeEffect.
     * @param source
     * @param target
     */
    public static void copyAndOverrideBasicCooperativeEffectProperties(CooperativeEffect source, CooperativeEffect target){
        if (source != null && target != null){
            target.setOutCome(source.getOutCome());
            target.setResponse(source.getResponse());
            target.setCooperativeEffectValue(source.getCooperativeEffectValue());

            // copy collections
            target.getAnnotations().clear();
            target.getAnnotations().addAll(source.getAnnotations());
            target.getCooperativityEvidences().clear();
            target.getCooperativityEvidences().addAll(source.getCooperativityEvidences());
            target.getAffectedInteractions().clear();
            target.getAffectedInteractions().addAll(source.getAffectedInteractions());
        }
    }

    public static <T extends AllostericEffector> void copyAndOverrideAllosteryProperties(Allostery<T> source, Allostery<T> target){
        if (source != null && target != null){
            copyAndOverrideBasicCooperativeEffectProperties(source, target);
            target.setAllostericMechanism(source.getAllostericMechanism());
            target.setAllostericMolecule(source.getAllostericMolecule());
            target.setAllosteryType(source.getAllosteryType());
            target.setAllostericEffector(source.getAllostericEffector());
        }
    }
}
