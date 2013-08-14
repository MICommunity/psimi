package psidev.psi.mi.jami.model.impl;

import psidev.psi.mi.jami.model.*;

/**
 * Default implementation for ModelledParticipant
 *
 * Notes: The equals and hashcode methods have NOT been overridden because the ModelledParticipant object is a complex object.
 * To compare ModelledParticipant objects, you can use some comparators provided by default:
 * - DefaultModelledParticipantComparator
 * - UnambiguousModelledParticipantComparator
 * - DefaultExactModelledParticipantComparator
 * - UnambiguousExactModelledParticipantComparator
 * - ModelledParticipantComparator
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>13/02/13</pre>
 */

public class DefaultModelledParticipant extends AbstractParticipant<ModelledInteraction, ModelledFeature> implements ModelledParticipant {

    public DefaultModelledParticipant(Interactor interactor) {
        super(interactor);
    }

    public DefaultModelledParticipant(Interactor interactor, CvTerm bioRole) {
        super(interactor, bioRole);
    }

    public DefaultModelledParticipant(Interactor interactor, Stoichiometry stoichiometry) {
        super(interactor, stoichiometry);
    }

    public DefaultModelledParticipant(Interactor interactor, CvTerm bioRole, Stoichiometry stoichiometry) {
        super(interactor, bioRole, stoichiometry);
    }
}
