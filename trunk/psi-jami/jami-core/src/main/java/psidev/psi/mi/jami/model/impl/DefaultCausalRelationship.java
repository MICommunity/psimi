package psidev.psi.mi.jami.model.impl;

import psidev.psi.mi.jami.model.CausalRelationship;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Participant;

/**
 * Default implementation for CausalRelationship
 *
 * Notes: The equals and hashcode methods have NOT been overridden because the CausalRelationship object is a complex object.
 * To compare CausalRelationship objects, you can use some comparators provided by default:
 * - DefaultCausalRelationshipComparator
 * - UnambiguousCausalRelationshipComparator
 * - DefaultExactCausalRelationshipComparator
 * - UnambiguousExactCausalRelationshipComparator
 * - CausalRelationshipComparator
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>22/05/13</pre>
 */

public class DefaultCausalRelationship implements CausalRelationship {

    private CvTerm relationType;
    private Participant target;

    public DefaultCausalRelationship(CvTerm relationType, Participant target){
        if (relationType == null){
            throw new IllegalArgumentException("The relationType in a CausalRelationship cannot be null");
        }
        this.relationType = relationType;

        if (target == null){
            throw new IllegalArgumentException("The participat target in a CausalRelationship cannot be null");
        }
        this.target = target;
    }

    public CvTerm getRelationType() {
        return relationType;
    }

    public Participant getTarget() {
        return target;
    }

    @Override
    public String toString() {
        return relationType.toString() + ": " + target.toString();
    }
}
