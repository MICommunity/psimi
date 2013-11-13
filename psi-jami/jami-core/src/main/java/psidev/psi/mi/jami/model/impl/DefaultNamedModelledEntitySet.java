package psidev.psi.mi.jami.model.impl;

import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.ModelledFeature;
import psidev.psi.mi.jami.model.NamedEntity;
import psidev.psi.mi.jami.model.Stoichiometry;

/**
 * Default implementation of Named modelled entity set
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>13/11/13</pre>
 */

public class DefaultNamedModelledEntitySet extends DefaultModelledEntitySet implements NamedEntity<ModelledFeature>{

    private String shortName;
    private String fullName;

    public DefaultNamedModelledEntitySet(String interactorSetName) {
        super(interactorSetName);
    }

    public DefaultNamedModelledEntitySet(String interactorSetName, CvTerm bioRole) {
        super(interactorSetName, bioRole);
    }

    public DefaultNamedModelledEntitySet(String interactorSetName, Stoichiometry stoichiometry) {
        super(interactorSetName, stoichiometry);
    }

    public DefaultNamedModelledEntitySet(String interactorSetName, CvTerm bioRole, Stoichiometry stoichiometry) {
        super(interactorSetName, bioRole, stoichiometry);
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
