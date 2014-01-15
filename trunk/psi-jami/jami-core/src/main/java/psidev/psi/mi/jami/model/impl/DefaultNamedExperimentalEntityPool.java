package psidev.psi.mi.jami.model.impl;

import psidev.psi.mi.jami.model.*;

/**
 * Default implementation for named experimental entity pool
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>13/11/13</pre>
 */

public class DefaultNamedExperimentalEntityPool extends DefaultExperimentalEntityPool implements NamedEntity<FeatureEvidence>{
    private String shortName;
    private String fullName;

    public DefaultNamedExperimentalEntityPool(InteractionEvidence interaction, String interactorSetName, CvTerm participantIdentificationMethod) {
        super(interaction, interactorSetName, participantIdentificationMethod);
    }

    public DefaultNamedExperimentalEntityPool(InteractionEvidence interaction, String interactorSetName, CvTerm bioRole, CvTerm participantIdentificationMethod) {
        super(interaction, interactorSetName, bioRole, participantIdentificationMethod);
    }

    public DefaultNamedExperimentalEntityPool(InteractionEvidence interaction, String interactorSetName, Stoichiometry stoichiometry, CvTerm participantIdentificationMethod) {
        super(interaction, interactorSetName, stoichiometry, participantIdentificationMethod);
    }

    public DefaultNamedExperimentalEntityPool(String interactorSetName, Stoichiometry stoichiometry) {
        super(interactorSetName, stoichiometry);
    }

    public DefaultNamedExperimentalEntityPool(String interactorSetName) {
        super(interactorSetName);
    }

    public DefaultNamedExperimentalEntityPool(String interactorSetName, CvTerm bioRole, CvTerm expRole, Stoichiometry stoichiometry, Organism expressedIn, CvTerm participantIdentificationMethod) {
        super(interactorSetName, bioRole, expRole, stoichiometry, expressedIn, participantIdentificationMethod);
    }

    public DefaultNamedExperimentalEntityPool(String interactorSetName, CvTerm bioRole, CvTerm expRole, Stoichiometry stoichiometry, CvTerm participantIdentificationMethod) {
        super(interactorSetName, bioRole, expRole, stoichiometry, participantIdentificationMethod);
    }

    public DefaultNamedExperimentalEntityPool(String interactorSetName, CvTerm bioRole, CvTerm expRole, Organism expressedIn, CvTerm participantIdentificationMethod) {
        super(interactorSetName, bioRole, expRole, expressedIn, participantIdentificationMethod);
    }

    public DefaultNamedExperimentalEntityPool(InteractionEvidence interaction, String interactorSetName, CvTerm bioRole, Stoichiometry stoichiometry, CvTerm participantIdentificationMethod) {
        super(interaction, interactorSetName, bioRole, stoichiometry, participantIdentificationMethod);
    }

    public DefaultNamedExperimentalEntityPool(InteractionEvidence interaction, String interactorSetName, CvTerm bioRole, CvTerm expRole, CvTerm participantIdentificationMethod) {
        super(interaction, interactorSetName, bioRole, expRole, participantIdentificationMethod);
    }

    public DefaultNamedExperimentalEntityPool(InteractionEvidence interaction, String interactorSetName, CvTerm bioRole, CvTerm expRole, Stoichiometry stoichiometry, CvTerm participantIdentificationMethod) {
        super(interaction, interactorSetName, bioRole, expRole, stoichiometry, participantIdentificationMethod);
    }

    public DefaultNamedExperimentalEntityPool(InteractionEvidence interaction, String interactorSetName, CvTerm bioRole, CvTerm expRole, Organism expressedIn, CvTerm participantIdentificationMethod) {
        super(interaction, interactorSetName, bioRole, expRole, expressedIn, participantIdentificationMethod);
    }

    public DefaultNamedExperimentalEntityPool(String interactorSetName, CvTerm bioRole, CvTerm expRole, CvTerm participantIdentificationMethod) {
        super(interactorSetName, bioRole, expRole, participantIdentificationMethod);
    }

    public DefaultNamedExperimentalEntityPool(String interactorSetName, Stoichiometry stoichiometry, CvTerm participantIdentificationMethod) {
        super(interactorSetName, stoichiometry, participantIdentificationMethod);
    }

    public DefaultNamedExperimentalEntityPool(String interactorSetName, CvTerm bioRole, CvTerm participantIdentificationMethod) {
        super(interactorSetName, bioRole, participantIdentificationMethod);
    }

    public DefaultNamedExperimentalEntityPool(String interactorSetName, CvTerm participantIdentificationMethod) {
        super(interactorSetName, participantIdentificationMethod);
    }

    public DefaultNamedExperimentalEntityPool(InteractionEvidence interaction, String interactorSetName, CvTerm bioRole, CvTerm expRole, Stoichiometry stoichiometry, Organism expressedIn, CvTerm participantIdentificationMethod) {
        super(interaction, interactorSetName, bioRole, expRole, stoichiometry, expressedIn, participantIdentificationMethod);
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
