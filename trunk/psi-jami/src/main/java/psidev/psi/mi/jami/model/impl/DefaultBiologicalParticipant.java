package psidev.psi.mi.jami.model.impl;

import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.utils.comparator.participant.UnambiguousExactBiologicalParticipantComparator;

/**
 * TODO comment this
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>13/02/13</pre>
 */

public class DefaultBiologicalParticipant extends DefaultParticipant<ModelledInteraction, Interactor, BiologicalFeature> implements BiologicalParticipant {


    public DefaultBiologicalParticipant(ModelledInteraction interaction, Interactor interactor) {
        super(interaction, interactor);
    }

    public DefaultBiologicalParticipant(ModelledInteraction interaction, Interactor interactor, CvTerm bioRole) {
        super(interaction, interactor, bioRole);
    }

    public DefaultBiologicalParticipant(ModelledInteraction interaction, Interactor interactor, Integer stoichiometry) {
        super(interaction, interactor, stoichiometry);
    }

    public DefaultBiologicalParticipant(ModelledInteraction interaction, Interactor interactor, CvTerm bioRole, Integer stoichiometry) {
        super(interaction, interactor, bioRole, stoichiometry);
    }

    public DefaultBiologicalParticipant(Interactor interactor) {
        super(interactor);
    }

    public DefaultBiologicalParticipant(Interactor interactor, CvTerm bioRole) {
        super(interactor, bioRole);
    }

    public DefaultBiologicalParticipant(Interactor interactor, Integer stoichiometry) {
        super(interactor, stoichiometry);
    }

    public DefaultBiologicalParticipant(Interactor interactor, CvTerm bioRole, Integer stoichiometry) {
        super(interactor, bioRole, stoichiometry);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }

        if (!(o instanceof BiologicalParticipant)){
            return false;
        }

        // use UnambiguousExactBiologicalParticipant comparator for equals
        return UnambiguousExactBiologicalParticipantComparator.areEquals(this, (BiologicalParticipant) o);
    }
}
