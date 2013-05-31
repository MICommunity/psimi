package psidev.psi.mi.jami.model.impl;

import psidev.psi.mi.jami.model.AllostericEffector;
import psidev.psi.mi.jami.model.Allostery;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.ModelledParticipant;

/**
 * Default implementation for Allostery
 *
 * Notes: The equals and hashcode methods have NOT been overridden because the Allostery object is a complex object.
 * To compare Allostery objects, you can use some comparators provided by default:
 * - DefaultAllosteryComparator
 * - UnambiguousAllosteryComparator
 * - AllosteryComparator
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>23/05/13</pre>
 */

public class DefaultAllostery<T extends AllostericEffector> extends DefaultCooperativeEffect implements Allostery<T> {

    private CvTerm allostericMechanism;
    private CvTerm allosteryType;
    private ModelledParticipant allostericMolecule;
    private T allostericEffector;

    public DefaultAllostery(CvTerm outcome, ModelledParticipant allostericMolecule, T allostericEffector) {
        super(outcome);
        if (allostericMolecule == null){
            throw new IllegalArgumentException("The allosteric molecule cannot be null");
        }
        this.allostericMolecule = allostericMolecule;
        if (allostericEffector == null){
            throw new IllegalArgumentException("The allosteric effector cannot be null");
        }
        this.allostericEffector = allostericEffector;
    }

    public DefaultAllostery(CvTerm outcome, CvTerm response, ModelledParticipant allostericMolecule, T allostericEffector) {
        super(outcome, response);
        if (allostericMolecule == null){
            throw new IllegalArgumentException("The allosteric molecule cannot be null");
        }
        this.allostericMolecule = allostericMolecule;
        if (allostericEffector == null){
            throw new IllegalArgumentException("The allosteric effector cannot be null");
        }
        this.allostericEffector = allostericEffector;
    }

    public CvTerm getAllostericMechanism() {
        return this.allostericMechanism;
    }

    public void setAllostericMechanism(CvTerm mechanism) {
        this.allostericMechanism = mechanism;
    }

    public CvTerm getAllosteryType() {
        return this.allosteryType;
    }

    public void setAllosteryType(CvTerm type) {
        this.allosteryType = type;
    }

    public ModelledParticipant getAllostericMolecule() {
        return this.allostericMolecule;
    }

    public void setAllostericMolecule(ModelledParticipant participant) {
        if (participant == null){
            throw new IllegalArgumentException("The allosteric molecule cannot be null");
        }
        this.allostericMolecule = participant;
    }

    public T getAllostericEffector() {
        return this.allostericEffector;
    }

    public void setAllostericEffector(T effector) {
        if (effector == null){
            throw new IllegalArgumentException("The allosteric effector cannot be null");
        }
        this.allostericEffector = effector;
    }

    @Override
    public String toString() {
        return "allostery: " + super.toString();
    }
}
