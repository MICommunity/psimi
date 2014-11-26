package psidev.psi.mi.jami.crosslink.extension;

import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.datasource.FileSourceLocator;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.model.impl.DefaultExperimentalParticipantPool;

/**
 * Crosslink CSV extension of ParticipantEvidencePool.
 * It contains a FileSourceLocator
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>11/06/13</pre>
 */

public class CsvExperimentalParticipantPool extends DefaultExperimentalParticipantPool implements FileSourceContext{

    private FileSourceLocator sourceLocator;

    public CsvExperimentalParticipantPool(InteractionEvidence interaction, String poolName, CvTerm participantIdentificationMethod) {
        super(interaction, poolName, participantIdentificationMethod);
    }

    public CsvExperimentalParticipantPool(InteractionEvidence interaction, String poolName, CvTerm bioRole, CvTerm participantIdentificationMethod) {
        super(interaction, poolName, bioRole, participantIdentificationMethod);
    }

    public CsvExperimentalParticipantPool(InteractionEvidence interaction, String poolName, Stoichiometry stoichiometry, CvTerm participantIdentificationMethod) {
        super(interaction, poolName, stoichiometry, participantIdentificationMethod);
    }

    public CsvExperimentalParticipantPool(String poolName, Stoichiometry stoichiometry) {
        super(poolName, stoichiometry);
    }

    public CsvExperimentalParticipantPool(String poolName) {
        super(poolName);
    }

    public CsvExperimentalParticipantPool(String poolName, CvTerm bioRole, CvTerm expRole, Stoichiometry stoichiometry, Organism expressedIn, CvTerm participantIdentificationMethod) {
        super(poolName, bioRole, expRole, stoichiometry, expressedIn, participantIdentificationMethod);
    }

    public CsvExperimentalParticipantPool(String poolName, CvTerm bioRole, CvTerm expRole, Organism expressedIn, CvTerm participantIdentificationMethod) {
        super(poolName, bioRole, expRole, expressedIn, participantIdentificationMethod);
    }

    public CsvExperimentalParticipantPool(String poolName, CvTerm bioRole, CvTerm expRole, Stoichiometry stoichiometry, CvTerm participantIdentificationMethod) {
        super(poolName, bioRole, expRole, stoichiometry, participantIdentificationMethod);
    }

    public CsvExperimentalParticipantPool(String poolName, CvTerm bioRole, CvTerm expRole, CvTerm participantIdentificationMethod) {
        super(poolName, bioRole, expRole, participantIdentificationMethod);
    }

    public CsvExperimentalParticipantPool(String poolName, Stoichiometry stoichiometry, CvTerm participantIdentificationMethod) {
        super(poolName, stoichiometry, participantIdentificationMethod);
    }

    public CsvExperimentalParticipantPool(String poolName, CvTerm bioRole, CvTerm participantIdentificationMethod) {
        super(poolName, bioRole, participantIdentificationMethod);
    }

    public CsvExperimentalParticipantPool(String poolName, CvTerm participantIdentificationMethod) {
        super(poolName, participantIdentificationMethod);
    }

    public CsvExperimentalParticipantPool(InteractionEvidence interaction, String poolName, CvTerm bioRole, Stoichiometry stoichiometry, CvTerm participantIdentificationMethod) {
        super(interaction, poolName, bioRole, stoichiometry, participantIdentificationMethod);
    }

    public CsvExperimentalParticipantPool(InteractionEvidence interaction, String poolName, CvTerm bioRole, CvTerm expRole, CvTerm participantIdentificationMethod) {
        super(interaction, poolName, bioRole, expRole, participantIdentificationMethod);
    }

    public CsvExperimentalParticipantPool(InteractionEvidence interaction, String poolName, CvTerm bioRole, CvTerm expRole, Stoichiometry stoichiometry, CvTerm participantIdentificationMethod) {
        super(interaction, poolName, bioRole, expRole, stoichiometry, participantIdentificationMethod);
    }

    public CsvExperimentalParticipantPool(InteractionEvidence interaction, String poolName, CvTerm bioRole, CvTerm expRole, Organism expressedIn, CvTerm participantIdentificationMethod) {
        super(interaction, poolName, bioRole, expRole, expressedIn, participantIdentificationMethod);
    }

    public CsvExperimentalParticipantPool(InteractionEvidence interaction, String poolName, CvTerm bioRole, CvTerm expRole, Stoichiometry stoichiometry, Organism expressedIn, CvTerm participantIdentificationMethod) {
        super(interaction, poolName, bioRole, expRole, stoichiometry, expressedIn, participantIdentificationMethod);
    }

    public FileSourceLocator getSourceLocator() {
        return sourceLocator;
    }

    public void setSourceLocator(FileSourceLocator sourceLocator) {
        this.sourceLocator = sourceLocator;
    }

    @Override
    public String toString() {
        return "CSV Participant pool: "+sourceLocator != null ? sourceLocator.toString():super.toString();
    }
}
