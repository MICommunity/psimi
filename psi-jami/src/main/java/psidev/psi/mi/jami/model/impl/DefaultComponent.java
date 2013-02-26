package psidev.psi.mi.jami.model.impl;

import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.utils.comparator.participant.UnambiguousExactComponentComparator;

/**
 * Default implementation for Component
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>04/02/13</pre>
 */

public class DefaultComponent extends DefaultParticipant<Interactor, ComponentFeature> implements Component {

    private Complex complex;

    public DefaultComponent(Complex interaction, Interactor interactor) {
        super(interactor);
        this.complex = interaction;
    }

    public DefaultComponent(Complex interaction, Interactor interactor, CvTerm bioRole) {
        super(interactor, bioRole);
        this.complex = interaction;
    }

    public DefaultComponent(Complex interaction, Interactor interactor, Integer stoichiometry) {
        super(interactor, stoichiometry);
        this.complex = interaction;
    }

    public DefaultComponent(Complex interaction, Interactor interactor, CvTerm bioRole, Integer stoichiometry) {
        super(interactor, bioRole, stoichiometry);
        this.complex = interaction;
    }

    public DefaultComponent(Interactor interactor) {
        super(interactor);
    }

    public DefaultComponent(Interactor interactor, CvTerm bioRole) {
        super(interactor, bioRole);
    }

    public DefaultComponent(Interactor interactor, Integer stoichiometry) {
        super(interactor, stoichiometry);
    }

    public DefaultComponent(Interactor interactor, CvTerm bioRole, Integer stoichiometry) {
        super(interactor, bioRole, stoichiometry);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }

        if (!(o instanceof Component)){
            return false;
        }

        // use UnambiguousExactComponent comparator for equals
        return UnambiguousExactComponentComparator.areEquals(this, (Component) o);
    }

    public void setComplexAndAddComponent(Complex interaction) {

        if (interaction != null){
            complex.addComponent(this);
        }
        else {
            complex = null;
        }
    }

    public Complex getComplex() {
        return this.complex;
    }

    public void setComplex(Complex interaction) {
        this.complex = interaction;
    }

    @Override
    public boolean addFeature(ComponentFeature feature) {

        if (super.addFeature(feature)){
            feature.setComponent(this);
            return true;
        }
        return false;
    }

    @Override
    public boolean removeFeature(ComponentFeature feature) {

        if (super.removeFeature(feature)){
            feature.setComponent(null);
            return true;
        }
        return false;
    }
}
