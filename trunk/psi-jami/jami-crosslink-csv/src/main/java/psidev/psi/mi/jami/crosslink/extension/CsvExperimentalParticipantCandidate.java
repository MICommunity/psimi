package psidev.psi.mi.jami.crosslink.extension;

import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.datasource.FileSourceLocator;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Interactor;
import psidev.psi.mi.jami.model.Stoichiometry;
import psidev.psi.mi.jami.model.impl.DefaultExperimentalParticipantCandidate;

/**
 * Crosslink CSV extension of ExperimentalParticipantCandidate.
 * It contains a FileSourceLocator
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>11/06/13</pre>
 */

public class CsvExperimentalParticipantCandidate extends DefaultExperimentalParticipantCandidate implements FileSourceContext{

    private FileSourceLocator sourceLocator;

    public CsvExperimentalParticipantCandidate(Interactor interactor) {
        super(interactor);
    }

    public CsvExperimentalParticipantCandidate(Interactor interactor, CvTerm bioRole) {
        super(interactor, bioRole);
    }

    public CsvExperimentalParticipantCandidate(Interactor interactor, Stoichiometry stoichiometry) {
        super(interactor, stoichiometry);
    }

    public CsvExperimentalParticipantCandidate(Interactor interactor, CvTerm bioRole, Stoichiometry stoichiometry) {
        super(interactor, bioRole, stoichiometry);
    }

    public FileSourceLocator getSourceLocator() {
        return sourceLocator;
    }

    public void setSourceLocator(FileSourceLocator sourceLocator) {
        this.sourceLocator = sourceLocator;
    }

    @Override
    public String toString() {
        return "CSV Participant: "+sourceLocator != null ? sourceLocator.toString():super.toString();
    }
}
