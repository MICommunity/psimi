package psidev.psi.mi.jami.model.impl;

import psidev.psi.mi.jami.model.*;

/**
 * A set of ModelledEntity that form a single modelled participant
 * Notes: The equals and hashcode methods have NOT been overridden because the DefaultModelledEntitySet object is a complex object.
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>04/10/13</pre>
 */
public class DefaultModelledEntitySet extends AbstractEntitySet<ModelledInteraction, ModelledFeature, ModelledEntity> implements ModelledEntitySet {
    public DefaultModelledEntitySet(String interactorSetName) {
        super(new DefaultInteractorSet(interactorSetName));
    }

    public DefaultModelledEntitySet(String interactorSetName, CvTerm bioRole) {
        super(new DefaultInteractorSet(interactorSetName), bioRole);
    }

    public DefaultModelledEntitySet(String interactorSetName, Stoichiometry stoichiometry) {
        super(new DefaultInteractorSet(interactorSetName), stoichiometry);
    }

    public DefaultModelledEntitySet(String interactorSetName, CvTerm bioRole, Stoichiometry stoichiometry) {
        super(new DefaultInteractorSet(interactorSetName), bioRole, stoichiometry);
    }
}
