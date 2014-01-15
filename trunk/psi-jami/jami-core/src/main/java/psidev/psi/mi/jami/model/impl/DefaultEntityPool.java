package psidev.psi.mi.jami.model.impl;

import psidev.psi.mi.jami.model.*;

/**
 * Default implementation for Entity pool
 * Notes: The equals and hashcode methods have NOT been overridden because the DefaultEntityPool object is a complex object.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>04/10/13</pre>
 */

public class DefaultEntityPool extends AbstractEntityPool<Interaction,Feature,Entity> {

    public DefaultEntityPool(String interactorSetName) {
        super(new DefaultInteractorPool(interactorSetName));
    }

    public DefaultEntityPool(String interactorSetName, CvTerm bioRole) {
        super(new DefaultInteractorPool(interactorSetName), bioRole);
    }

    public DefaultEntityPool(String interactorSetName, Stoichiometry stoichiometry) {
        super(new DefaultInteractorPool(interactorSetName), stoichiometry);
    }

    public DefaultEntityPool(String interactorSetName, CvTerm bioRole, Stoichiometry stoichiometry) {
        super(new DefaultInteractorPool(interactorSetName), bioRole, stoichiometry);
    }
}
