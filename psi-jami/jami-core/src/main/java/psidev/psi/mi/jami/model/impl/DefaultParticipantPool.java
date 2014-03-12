package psidev.psi.mi.jami.model.impl;

import psidev.psi.mi.jami.model.*;

/**
 * Default implementation for Participant pool
 * Notes: The equals and hashcode methods have NOT been overridden because the DefaultParticipantPool object is a complex object.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>04/10/13</pre>
 */

public class DefaultParticipantPool extends AbstractParticipantPool<Interaction,Feature,Participant> {

    public DefaultParticipantPool(String interactorSetName) {
        super(new DefaultInteractorPool(interactorSetName));
    }

    public DefaultParticipantPool(String interactorSetName, CvTerm bioRole) {
        super(new DefaultInteractorPool(interactorSetName), bioRole);
    }

    public DefaultParticipantPool(String interactorSetName, Stoichiometry stoichiometry) {
        super(new DefaultInteractorPool(interactorSetName), stoichiometry);
    }

    public DefaultParticipantPool(String interactorSetName, CvTerm bioRole, Stoichiometry stoichiometry) {
        super(new DefaultInteractorPool(interactorSetName), bioRole, stoichiometry);
    }
}
