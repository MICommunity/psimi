package psidev.psi.mi.jami.model.impl;

import psidev.psi.mi.jami.model.*;

/**
 * Default implementation of Named modelled entity.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>13/11/13</pre>
 */

public class DefaultNamedModelledEntity extends DefaultModelledEntity implements NamedEntity<ModelledFeature>{
    private String shortName;
    private String fullName;

    public DefaultNamedModelledEntity(Interactor interactor) {
        super(interactor);
    }

    public DefaultNamedModelledEntity(Interactor interactor, CvTerm bioRole) {
        super(interactor, bioRole);
    }

    public DefaultNamedModelledEntity(Interactor interactor, Stoichiometry stoichiometry) {
        super(interactor, stoichiometry);
    }

    public DefaultNamedModelledEntity(Interactor interactor, CvTerm bioRole, Stoichiometry stoichiometry) {
        super(interactor, bioRole, stoichiometry);
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
