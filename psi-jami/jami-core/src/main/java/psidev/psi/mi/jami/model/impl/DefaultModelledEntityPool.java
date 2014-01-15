package psidev.psi.mi.jami.model.impl;

import psidev.psi.mi.jami.model.*;

/**
 * A pool of ModelledEntity that form a single modelled participant
 * Notes: The equals and hashcode methods have NOT been overridden because the DefaultModelledEntityPool object is a complex object.
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>04/10/13</pre>
 */
public class DefaultModelledEntityPool extends AbstractEntityPool<ModelledInteraction, ModelledFeature, ModelledEntity> implements ModelledEntityPool {
    public DefaultModelledEntityPool(String interactorSetName) {
        super(new DefaultInteractorPool(interactorSetName));
    }

    public DefaultModelledEntityPool(String interactorSetName, CvTerm bioRole) {
        super(new DefaultInteractorPool(interactorSetName), bioRole);
    }

    public DefaultModelledEntityPool(String interactorSetName, Stoichiometry stoichiometry) {
        super(new DefaultInteractorPool(interactorSetName), stoichiometry);
    }

    public DefaultModelledEntityPool(String interactorSetName, CvTerm bioRole, Stoichiometry stoichiometry) {
        super(new DefaultInteractorPool(interactorSetName), bioRole, stoichiometry);
    }
}
