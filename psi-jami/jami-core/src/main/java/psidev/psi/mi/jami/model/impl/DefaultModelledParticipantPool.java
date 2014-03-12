package psidev.psi.mi.jami.model.impl;

import psidev.psi.mi.jami.model.*;

/**
 * A pool of ModelledParticipant that form a single modelled participant
 * Notes: The equals and hashcode methods have NOT been overridden because the DefaultModelledParticipantPool object is a complex object.
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>04/10/13</pre>
 */
public class DefaultModelledParticipantPool extends AbstractParticipantPool<ModelledInteraction, ModelledFeature, ModelledParticipant> implements ModelledParticipantPool {
    public DefaultModelledParticipantPool(String interactorSetName) {
        super(new DefaultInteractorPool(interactorSetName));
    }

    public DefaultModelledParticipantPool(String interactorSetName, CvTerm bioRole) {
        super(new DefaultInteractorPool(interactorSetName), bioRole);
    }

    public DefaultModelledParticipantPool(String interactorSetName, Stoichiometry stoichiometry) {
        super(new DefaultInteractorPool(interactorSetName), stoichiometry);
    }

    public DefaultModelledParticipantPool(String interactorSetName, CvTerm bioRole, Stoichiometry stoichiometry) {
        super(new DefaultInteractorPool(interactorSetName), bioRole, stoichiometry);
    }
}
