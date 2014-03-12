package psidev.psi.mi.jami.model.impl;

import psidev.psi.mi.jami.model.*;

/**
 * Default implementation of Named entity pool
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>13/11/13</pre>
 */

public class DefaultNamedParticipantPool extends DefaultParticipantPool implements NamedParticipant<Interaction, Feature> {

    private String shortName;
    private String fullName;

    public DefaultNamedParticipantPool(String interactorSetName) {
        super(interactorSetName);
    }

    public DefaultNamedParticipantPool(String interactorSetName, CvTerm bioRole) {
        super(interactorSetName, bioRole);
    }

    public DefaultNamedParticipantPool(String interactorSetName, Stoichiometry stoichiometry) {
        super(interactorSetName, stoichiometry);
    }

    public DefaultNamedParticipantPool(String interactorSetName, CvTerm bioRole, Stoichiometry stoichiometry) {
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
