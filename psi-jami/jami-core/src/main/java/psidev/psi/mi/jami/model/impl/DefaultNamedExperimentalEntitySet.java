package psidev.psi.mi.jami.model.impl;

import psidev.psi.mi.jami.model.*;

/**
 * Default implementation for named experimental entity set
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>13/11/13</pre>
 */

public class DefaultNamedExperimentalEntitySet extends DefaultExperimentalEntitySet implements NamedEntity<FeatureEvidence>{
    private String shortName;
    private String fullName;

    public DefaultNamedExperimentalEntitySet(InteractionEvidence interaction, String interactorSetName, CvTerm participantIdentificationMethod) {
        super(interaction, interactorSetName, participantIdentificationMethod);
    }

    public DefaultNamedExperimentalEntitySet(InteractionEvidence interaction, String interactorSetName, CvTerm bioRole, CvTerm participantIdentificationMethod) {
        super(interaction, interactorSetName, bioRole, participantIdentificationMethod);
    }

    public DefaultNamedExperimentalEntitySet(InteractionEvidence interaction, String interactorSetName, Stoichiometry stoichiometry, CvTerm participantIdentificationMethod) {
        super(interaction, interactorSetName, stoichiometry, participantIdentificationMethod);
    }

    public DefaultNamedExperimentalEntitySet(String interactorSetName, Stoichiometry stoichiometry) {
        super(interactorSetName, stoichiometry);
    }

    public DefaultNamedExperimentalEntitySet(String interactorSetName) {
        super(interactorSetName);
    }

    public DefaultNamedExperimentalEntitySet(String interactorSetName, CvTerm bioRole, CvTerm expRole, Stoichiometry stoichiometry, Organism expressedIn, CvTerm participantIdentificationMethod) {
        super(interactorSetName, bioRole, expRole, stoichiometry, expressedIn, participantIdentificationMethod);
    }

    public DefaultNamedExperimentalEntitySet(String interactorSetName, CvTerm bioRole, CvTerm expRole, Stoichiometry stoichiometry, CvTerm participantIdentificationMethod) {
        super(interactorSetName, bioRole, expRole, stoichiometry, participantIdentificationMethod);
    }

    public DefaultNamedExperimentalEntitySet(String interactorSetName, CvTerm bioRole, CvTerm expRole, Organism expressedIn, CvTerm participantIdentificationMethod) {
        super(interactorSetName, bioRole, expRole, expressedIn, participantIdentificationMethod);
    }

    public DefaultNamedExperimentalEntitySet(InteractionEvidence interaction, String interactorSetName, CvTerm bioRole, Stoichiometry stoichiometry, CvTerm participantIdentificationMethod) {
        super(interaction, interactorSetName, bioRole, stoichiometry, participantIdentificationMethod);
    }

    public DefaultNamedExperimentalEntitySet(InteractionEvidence interaction, String interactorSetName, CvTerm bioRole, CvTerm expRole, CvTerm participantIdentificationMethod) {
        super(interaction, interactorSetName, bioRole, expRole, participantIdentificationMethod);
    }

    public DefaultNamedExperimentalEntitySet(InteractionEvidence interaction, String interactorSetName, CvTerm bioRole, CvTerm expRole, Stoichiometry stoichiometry, CvTerm participantIdentificationMethod) {
        super(interaction, interactorSetName, bioRole, expRole, stoichiometry, participantIdentificationMethod);
    }

    public DefaultNamedExperimentalEntitySet(InteractionEvidence interaction, String interactorSetName, CvTerm bioRole, CvTerm expRole, Organism expressedIn, CvTerm participantIdentificationMethod) {
        super(interaction, interactorSetName, bioRole, expRole, expressedIn, participantIdentificationMethod);
    }

    public DefaultNamedExperimentalEntitySet(String interactorSetName, CvTerm bioRole, CvTerm expRole, CvTerm participantIdentificationMethod) {
        super(interactorSetName, bioRole, expRole, participantIdentificationMethod);
    }

    public DefaultNamedExperimentalEntitySet(String interactorSetName, Stoichiometry stoichiometry, CvTerm participantIdentificationMethod) {
        super(interactorSetName, stoichiometry, participantIdentificationMethod);
    }

    public DefaultNamedExperimentalEntitySet(String interactorSetName, CvTerm bioRole, CvTerm participantIdentificationMethod) {
        super(interactorSetName, bioRole, participantIdentificationMethod);
    }

    public DefaultNamedExperimentalEntitySet(String interactorSetName, CvTerm participantIdentificationMethod) {
        super(interactorSetName, participantIdentificationMethod);
    }

    public DefaultNamedExperimentalEntitySet(InteractionEvidence interaction, String interactorSetName, CvTerm bioRole, CvTerm expRole, Stoichiometry stoichiometry, Organism expressedIn, CvTerm participantIdentificationMethod) {
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
