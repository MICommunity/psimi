package psidev.psi.mi.jami.model.impl;

import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.utils.CvTermUtils;
import psidev.psi.mi.jami.utils.comparator.cv.DefaultCvTermComparator;
import psidev.psi.mi.jami.utils.comparator.interaction.UnambiguousExactAllostericInteractionComparator;
import psidev.psi.mi.jami.utils.factory.CvTermFactory;

/**
 * Default implementation for AllostericInteraction
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>05/02/13</pre>
 */

public class DefaultAllostericInteraction extends DefaultCooperativeInteraction implements AllostericInteraction {

    private CvTerm allostericMechanism;
    private CvTerm allosteryType;
    private ModelledParticipant allostericMolecule;
    private ModelledParticipant allostericEffector;
    private ModelledFeature allostericPtm;

    public DefaultAllostericInteraction(CvTerm effectOutcome, CvTerm response, CvTerm allostericMechanism,
                                        CvTerm allosteryType, ModelledParticipant allostericMolecule) {
        super(CvTermFactory.createAllosteryCooperativeMechanism(), effectOutcome, response);
        if (allostericMechanism == null){
            throw new IllegalArgumentException("The allosteric mechanism cannot be null.");
        }
        this.allostericMechanism = allostericMechanism;

        if (allosteryType == null){
            throw new IllegalArgumentException("The allostery type cannot be null.");
        }
        this.allosteryType = allosteryType;

        if (allostericMolecule == null){
            throw new IllegalArgumentException("The allosteric molecule cannot be null.");
        }
        this.allostericMolecule = allostericMolecule;
    }

    public DefaultAllostericInteraction(String shortName, CvTerm effectOutcome, CvTerm response, CvTerm allostericMechanism,
                                        CvTerm allosteryType, ModelledParticipant allostericMolecule) {
        super(shortName, CvTermFactory.createAllosteryCooperativeMechanism(), effectOutcome, response);
        if (allostericMechanism == null){
            throw new IllegalArgumentException("The allosteric mechanism cannot be null.");
        }
        this.allostericMechanism = allostericMechanism;

        if (allosteryType == null){
            throw new IllegalArgumentException("The allostery type cannot be null.");
        }
        this.allosteryType = allosteryType;

        if (allostericMolecule == null){
            throw new IllegalArgumentException("The allosteric molecule cannot be null.");
        }
        this.allostericMolecule = allostericMolecule;
    }

    public DefaultAllostericInteraction(String shortName, Source source, CvTerm effectOutcome, CvTerm response, CvTerm allostericMechanism,
                                        CvTerm allosteryType, ModelledParticipant allostericMolecule) {
        super(shortName, source, CvTermFactory.createAllosteryCooperativeMechanism(), effectOutcome, response);
        if (allostericMechanism == null){
            throw new IllegalArgumentException("The allosteric mechanism cannot be null.");
        }
        this.allostericMechanism = allostericMechanism;

        if (allosteryType == null){
            throw new IllegalArgumentException("The allostery type cannot be null.");
        }
        this.allosteryType = allosteryType;

        if (allostericMolecule == null){
            throw new IllegalArgumentException("The allosteric molecule cannot be null.");
        }
        this.allostericMolecule = allostericMolecule;
    }

    public DefaultAllostericInteraction(String shortName, CvTerm type, CvTerm effectOutcome, CvTerm response, CvTerm allostericMechanism,
                                        CvTerm allosteryType, ModelledParticipant allostericMolecule) {
        super(shortName, type, CvTermFactory.createAllosteryCooperativeMechanism(), effectOutcome, response);
        if (allostericMechanism == null){
            throw new IllegalArgumentException("The allosteric mechanism cannot be null.");
        }
        this.allostericMechanism = allostericMechanism;

        if (allosteryType == null){
            throw new IllegalArgumentException("The allostery type cannot be null.");
        }
        this.allosteryType = allosteryType;

        if (allostericMolecule == null){
            throw new IllegalArgumentException("The allosteric molecule cannot be null.");
        }
        this.allostericMolecule = allostericMolecule;
    }

    public DefaultAllostericInteraction(String shortName, Source source, CvTerm type, CvTerm effectOutcome, CvTerm response, CvTerm allostericMechanism,
                                        CvTerm allosteryType, ModelledParticipant allostericMolecule) {
        super(shortName, source, type, CvTermFactory.createAllosteryCooperativeMechanism(), effectOutcome, response);
        if (allostericMechanism == null){
            throw new IllegalArgumentException("The allosteric mechanism cannot be null.");
        }
        this.allostericMechanism = allostericMechanism;

        if (allosteryType == null){
            throw new IllegalArgumentException("The allostery type cannot be null.");
        }
        this.allosteryType = allosteryType;

        if (allostericMolecule == null){
            throw new IllegalArgumentException("The allosteric molecule cannot be null.");
        }
        this.allostericMolecule = allostericMolecule;
    }

    public CvTerm getAllostericMechanism() {
        return this.allostericMechanism;
    }

    public void setAllostericMechanism(CvTerm mechanism) {
        if (allostericMechanism == null){
            throw new IllegalArgumentException("The allosteric mechanism cannot be null.");
        }
        this.allostericMechanism = mechanism;
    }

    public CvTerm getAllosteryType() {
        return this.allosteryType;
    }

    public void setAllosteryType(CvTerm type) {
        if (allosteryType == null){
            throw new IllegalArgumentException("The allostery type cannot be null.");
        }
        this.allosteryType = type;
    }

    public ModelledParticipant getAllostericMolecule() {
        return this.allostericMolecule;
    }

    public void setAllostericMolecule(ModelledParticipant participant) {
        if (allostericMolecule == null){
            throw new IllegalArgumentException("The allosteric molecule cannot be null.");
        }
        this.allostericMolecule = participant;
    }

    public ModelledParticipant getAllostericEffector() {
        return this.allostericEffector;
    }

    public void setAllostericEffector(ModelledParticipant effector) {
        this.allostericEffector = effector;
    }

    public ModelledFeature getAllostericPtm() {
        return this.allostericPtm;
    }

    public void setAllostericPtm(ModelledFeature feature) {
        this.allostericPtm = feature;
    }

    @Override
    public void setCooperativeMechanism(CvTerm mechanism) {
        if (mechanism == null){
            throw new IllegalArgumentException("The cooperative mechanism cannot be null.");
        }
        else if (!DefaultCvTermComparator.areEquals(mechanism, CvTermUtils.getAllosteryMechanism())){
            throw new IllegalArgumentException("This interaction is an allosteric interaction and the cooperative mechanism can only by allostery (MI:1157)");
        }
        super.setCooperativeMechanism(mechanism);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }

        if (!(o instanceof AllostericInteraction)){
            return false;
        }

        // use UnambiguousExactAllostericInteraction comparator for equals
        return UnambiguousExactAllostericInteractionComparator.areEquals(this, (AllostericInteraction) o);
    }

    @Override
    public String toString() {
        return super.toString() + ", allosteric mechanism: " + allostericMechanism.toString() + ", allostery type: " + allosteryType.toString() + ", allosteric molecule: " + allostericMolecule.toString();
    }
}
