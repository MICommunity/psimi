package psidev.psi.mi.jami.model.impl;

import psidev.psi.mi.jami.model.*;

/**
 * Default implementation of Named Experimental Entity
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>13/11/13</pre>
 */

public class DefaultNamedExperimentalEntity extends DefaultExperimentalEntity implements NamedEntity<FeatureEvidence>{
    private String shortName;
    private String fullName;

    public DefaultNamedExperimentalEntity(Interactor interactor, CvTerm participantIdentificationMethod) {
        super(interactor, participantIdentificationMethod);
    }

    public DefaultNamedExperimentalEntity(Interactor interactor, CvTerm bioRole, CvTerm participantIdentificationMethod) {
        super(interactor, bioRole, participantIdentificationMethod);
    }

    public DefaultNamedExperimentalEntity(Interactor interactor, Stoichiometry stoichiometry, CvTerm participantIdentificationMethod) {
        super(interactor, stoichiometry, participantIdentificationMethod);
    }

    public DefaultNamedExperimentalEntity(Interactor interactor, CvTerm bioRole, CvTerm expRole, Stoichiometry stoichiometry, CvTerm participantIdentificationMethod) {
        super(interactor, bioRole, expRole, stoichiometry, participantIdentificationMethod);
    }

    public DefaultNamedExperimentalEntity(Interactor interactor, CvTerm bioRole, CvTerm expRole, Organism expressedIn, CvTerm participantIdentificationMethod) {
        super(interactor, bioRole, expRole, expressedIn, participantIdentificationMethod);
    }

    public DefaultNamedExperimentalEntity(Interactor interactor, CvTerm bioRole, CvTerm expRole, CvTerm participantIdentificationMethod) {
        super(interactor, bioRole, expRole, participantIdentificationMethod);
    }

    public DefaultNamedExperimentalEntity(Interactor interactor, CvTerm bioRole, CvTerm expRole, Stoichiometry stoichiometry, Organism expressedIn, CvTerm participantIdentificationMethod) {
        super(interactor, bioRole, expRole, stoichiometry, expressedIn, participantIdentificationMethod);
    }

    public DefaultNamedExperimentalEntity(Interactor interactor) {
        super(interactor);
    }

    public DefaultNamedExperimentalEntity(Interactor interactor, Stoichiometry stoichiometry) {
        super(interactor, stoichiometry);
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
