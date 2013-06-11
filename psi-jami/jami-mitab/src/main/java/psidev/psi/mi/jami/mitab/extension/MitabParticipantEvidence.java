package psidev.psi.mi.jami.mitab.extension;

import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.model.impl.DefaultParticipantEvidence;

/**
 * Mitab extension of ParticipantEvidence.
 * It contains a FileSourceLocator
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>11/06/13</pre>
 */

public class MitabParticipantEvidence extends DefaultParticipantEvidence implements FileSourceContext{

    private MitabSourceLocator sourceLocator;

    public MitabParticipantEvidence(InteractionEvidence interaction, Interactor interactor, CvTerm participantIdentificationMethod) {
        super(interaction, interactor, participantIdentificationMethod);
    }

    public MitabParticipantEvidence(InteractionEvidence interaction, Interactor interactor, CvTerm bioRole, CvTerm participantIdentificationMethod) {
        super(interaction, interactor, bioRole, participantIdentificationMethod);
    }

    public MitabParticipantEvidence(InteractionEvidence interaction, Interactor interactor, Stoichiometry stoichiometry, CvTerm participantIdentificationMethod) {
        super(interaction, interactor, stoichiometry, participantIdentificationMethod);
    }

    public MitabParticipantEvidence(InteractionEvidence interaction, Interactor interactor, CvTerm bioRole, Stoichiometry stoichiometry, CvTerm participantIdentificationMethod) {
        super(interaction, interactor, bioRole, stoichiometry, participantIdentificationMethod);
    }

    public MitabParticipantEvidence(InteractionEvidence interaction, Interactor interactor, CvTerm bioRole, CvTerm expRole, CvTerm participantIdentificationMethod) {
        super(interaction, interactor, bioRole, expRole, participantIdentificationMethod);
    }

    public MitabParticipantEvidence(InteractionEvidence interaction, Interactor interactor, CvTerm bioRole, CvTerm expRole, Stoichiometry stoichiometry, CvTerm participantIdentificationMethod) {
        super(interaction, interactor, bioRole, expRole, stoichiometry, participantIdentificationMethod);
    }

    public MitabParticipantEvidence(InteractionEvidence interaction, Interactor interactor, CvTerm bioRole, CvTerm expRole, Organism expressedIn, CvTerm participantIdentificationMethod) {
        super(interaction, interactor, bioRole, expRole, expressedIn, participantIdentificationMethod);
    }

    public MitabParticipantEvidence(InteractionEvidence interaction, Interactor interactor, CvTerm bioRole, CvTerm expRole, Stoichiometry stoichiometry, Organism expressedIn, CvTerm participantIdentificationMethod) {
        super(interaction, interactor, bioRole, expRole, stoichiometry, expressedIn, participantIdentificationMethod);
    }

    public MitabParticipantEvidence(Interactor interactor, CvTerm participantIdentificationMethod) {
        super(interactor, participantIdentificationMethod);
    }

    public MitabParticipantEvidence(Interactor interactor, CvTerm bioRole, CvTerm participantIdentificationMethod) {
        super(interactor, bioRole, participantIdentificationMethod);
    }

    public MitabParticipantEvidence(Interactor interactor, Stoichiometry stoichiometry, CvTerm participantIdentificationMethod) {
        super(interactor, stoichiometry, participantIdentificationMethod);
    }

    public MitabParticipantEvidence(Interactor interactor, CvTerm bioRole, CvTerm expRole, Stoichiometry stoichiometry, CvTerm participantIdentificationMethod) {
        super(interactor, bioRole, expRole, stoichiometry, participantIdentificationMethod);
    }

    public MitabParticipantEvidence(Interactor interactor, CvTerm bioRole, CvTerm expRole, CvTerm participantIdentificationMethod) {
        super(interactor, bioRole, expRole, participantIdentificationMethod);
    }

    public MitabParticipantEvidence(Interactor interactor, CvTerm bioRole, CvTerm expRole, Organism expressedIn, CvTerm participantIdentificationMethod) {
        super(interactor, bioRole, expRole, expressedIn, participantIdentificationMethod);
    }

    public MitabParticipantEvidence(Interactor interactor, CvTerm bioRole, CvTerm expRole, Stoichiometry stoichiometry, Organism expressedIn, CvTerm participantIdentificationMethod) {
        super(interactor, bioRole, expRole, stoichiometry, expressedIn, participantIdentificationMethod);
    }

    public MitabParticipantEvidence(Interactor interactor) {
        super(interactor);
    }

    public MitabParticipantEvidence(Interactor interactor, Stoichiometry stoichiometry) {
        super(interactor, stoichiometry);
    }

    public MitabSourceLocator getSourceLocator() {
        return sourceLocator;
    }

    public void setSourceLocator(MitabSourceLocator sourceLocator) {
        this.sourceLocator = sourceLocator;
    }
}
