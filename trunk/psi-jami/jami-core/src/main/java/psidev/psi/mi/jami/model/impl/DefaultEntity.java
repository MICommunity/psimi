package psidev.psi.mi.jami.model.impl;

import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Feature;
import psidev.psi.mi.jami.model.Interactor;
import psidev.psi.mi.jami.model.Stoichiometry;

/**
 * Default implementation for component
 *
 * Notes: The equals and hashcode methods have NOT been overridden because the component object is a complex object.
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>04/10/13</pre>
 */

public class DefaultEntity extends AbstractEntity<Feature> {

    public DefaultEntity(Interactor interactor) {
        super(interactor);
    }

    public DefaultEntity(Interactor interactor, CvTerm bioRole) {
        super(interactor, bioRole);
    }

    public DefaultEntity(Interactor interactor, Stoichiometry stoichiometry) {
        super(interactor, stoichiometry);
    }

    public DefaultEntity(Interactor interactor, CvTerm bioRole, Stoichiometry stoichiometry) {
        super(interactor, bioRole, stoichiometry);
    }
}
