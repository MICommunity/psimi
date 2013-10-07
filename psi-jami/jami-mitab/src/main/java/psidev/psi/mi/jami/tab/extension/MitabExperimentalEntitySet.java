package psidev.psi.mi.jami.tab.extension;

import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.datasource.FileSourceLocator;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.InteractionEvidence;
import psidev.psi.mi.jami.model.Organism;
import psidev.psi.mi.jami.model.Stoichiometry;
import psidev.psi.mi.jami.model.impl.DefaultExperimentalEntitySet;

/**
 * Mitab implementation of an experimental entity set
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>07/10/13</pre>
 */

public class MitabExperimentalEntitySet extends DefaultExperimentalEntitySet implements FileSourceContext {
    private FileSourceLocator sourceLocator;
    public MitabExperimentalEntitySet(InteractionEvidence interaction, String interactorSetName, CvTerm participantIdentificationMethod) {
        super(interaction, interactorSetName, participantIdentificationMethod);
    }

    public MitabExperimentalEntitySet(InteractionEvidence interaction, String interactorSetName, CvTerm bioRole, CvTerm participantIdentificationMethod) {
        super(interaction, interactorSetName, bioRole, participantIdentificationMethod);
    }

    public MitabExperimentalEntitySet(InteractionEvidence interaction, String interactorSetName, Stoichiometry stoichiometry, CvTerm participantIdentificationMethod) {
        super(interaction, interactorSetName, stoichiometry, participantIdentificationMethod);
    }

    public MitabExperimentalEntitySet(InteractionEvidence interaction, String interactorSetName, CvTerm bioRole, Stoichiometry stoichiometry, CvTerm participantIdentificationMethod) {
        super(interaction, interactorSetName, bioRole, stoichiometry, participantIdentificationMethod);
    }

    public MitabExperimentalEntitySet(InteractionEvidence interaction, String interactorSetName, CvTerm bioRole, CvTerm expRole, CvTerm participantIdentificationMethod) {
        super(interaction, interactorSetName, bioRole, expRole, participantIdentificationMethod);
    }

    public MitabExperimentalEntitySet(InteractionEvidence interaction, String interactorSetName, CvTerm bioRole, CvTerm expRole, Stoichiometry stoichiometry, CvTerm participantIdentificationMethod) {
        super(interaction, interactorSetName, bioRole, expRole, stoichiometry, participantIdentificationMethod);
    }

    public MitabExperimentalEntitySet(InteractionEvidence interaction, String interactorSetName, CvTerm bioRole, CvTerm expRole, Organism expressedIn, CvTerm participantIdentificationMethod) {
        super(interaction, interactorSetName, bioRole, expRole, expressedIn, participantIdentificationMethod);
    }

    public MitabExperimentalEntitySet(InteractionEvidence interaction, String interactorSetName, CvTerm bioRole, CvTerm expRole, Stoichiometry stoichiometry, Organism expressedIn, CvTerm participantIdentificationMethod) {
        super(interaction, interactorSetName, bioRole, expRole, stoichiometry, expressedIn, participantIdentificationMethod);
    }

    public MitabExperimentalEntitySet(String interactorSetName, CvTerm participantIdentificationMethod) {
        super(interactorSetName, participantIdentificationMethod);
    }

    public MitabExperimentalEntitySet(String interactorSetName, CvTerm bioRole, CvTerm participantIdentificationMethod) {
        super(interactorSetName, bioRole, participantIdentificationMethod);
    }

    public MitabExperimentalEntitySet(String interactorSetName, Stoichiometry stoichiometry, CvTerm participantIdentificationMethod) {
        super(interactorSetName, stoichiometry, participantIdentificationMethod);
    }

    public MitabExperimentalEntitySet(String interactorSetName, CvTerm bioRole, CvTerm expRole, CvTerm participantIdentificationMethod) {
        super(interactorSetName, bioRole, expRole, participantIdentificationMethod);
    }

    public MitabExperimentalEntitySet(String interactorSetName, CvTerm bioRole, CvTerm expRole, Stoichiometry stoichiometry, CvTerm participantIdentificationMethod) {
        super(interactorSetName, bioRole, expRole, stoichiometry, participantIdentificationMethod);
    }

    public MitabExperimentalEntitySet(String interactorSetName, CvTerm bioRole, CvTerm expRole, Organism expressedIn, CvTerm participantIdentificationMethod) {
        super(interactorSetName, bioRole, expRole, expressedIn, participantIdentificationMethod);
    }

    public MitabExperimentalEntitySet(String interactorSetName, CvTerm bioRole, CvTerm expRole, Stoichiometry stoichiometry, Organism expressedIn, CvTerm participantIdentificationMethod) {
        super(interactorSetName, bioRole, expRole, stoichiometry, expressedIn, participantIdentificationMethod);
    }

    public MitabExperimentalEntitySet(String interactorSetName) {
        super(interactorSetName);
    }

    public MitabExperimentalEntitySet(String interactorSetName, Stoichiometry stoichiometry) {
        super(interactorSetName, stoichiometry);
    }

    public FileSourceLocator getSourceLocator() {
        return sourceLocator;
    }

    public void setSourceLocator(FileSourceLocator sourceLocator) {
        this.sourceLocator = sourceLocator;
    }
}
