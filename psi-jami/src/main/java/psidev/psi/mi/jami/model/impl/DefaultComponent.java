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

public class DefaultComponent extends DefaultParticipant<Complex, Interactor, ComponentFeature> implements Component {

    public DefaultComponent(Complex interaction, Interactor interactor) {
        super(interaction, interactor);
    }

    public DefaultComponent(Complex interaction, Interactor interactor, CvTerm bioRole) {
        super(interaction, interactor, bioRole);
    }

    public DefaultComponent(Complex interaction, Interactor interactor, Integer stoichiometry) {
        super(interaction, interactor, stoichiometry);
    }

    public DefaultComponent(Complex interaction, Interactor interactor, CvTerm bioRole, Integer stoichiometry) {
        super(interaction, interactor, bioRole, stoichiometry);
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
            getInteraction().addComponent(this);
        }
        else {
            setInteraction(null);
        }
    }
}
