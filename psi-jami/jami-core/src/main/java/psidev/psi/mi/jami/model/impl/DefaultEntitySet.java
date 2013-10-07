package psidev.psi.mi.jami.model.impl;

import psidev.psi.mi.jami.model.*;

/**
 * Default implementation for Entity set
 * Notes: The equals and hashcode methods have NOT been overridden because the DefaultEntitySet object is a complex object.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>04/10/13</pre>
 */

public class DefaultEntitySet extends AbstractEntitySet<Interaction,Feature,Entity> {

    public DefaultEntitySet(String interactorSetName) {
        super(new DefaultInteractorSet(interactorSetName));
    }

    public DefaultEntitySet(String interactorSetName, CvTerm bioRole) {
        super(new DefaultInteractorSet(interactorSetName), bioRole);
    }

    public DefaultEntitySet(String interactorSetName, Stoichiometry stoichiometry) {
        super(new DefaultInteractorSet(interactorSetName), stoichiometry);
    }

    public DefaultEntitySet(String interactorSetName, CvTerm bioRole, Stoichiometry stoichiometry) {
        super(new DefaultInteractorSet(interactorSetName), bioRole, stoichiometry);
    }
}
