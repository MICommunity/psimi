package psidev.psi.mi.jami.mitab.extension;

import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Interactor;
import psidev.psi.mi.jami.model.ModelledInteraction;
import psidev.psi.mi.jami.model.Stoichiometry;
import psidev.psi.mi.jami.model.impl.DefaultModelledParticipant;

/**
 * Mitab extension for ModelledParticipant
 * It contains a FileSourceLocator
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>11/06/13</pre>
 */

public class MitabModelledParticipant extends DefaultModelledParticipant implements FileSourceContext {

    private MitabSourceLocator sourceLocator;

    public MitabModelledParticipant(ModelledInteraction interaction, Interactor interactor) {
        super(interaction, interactor);
    }

    public MitabModelledParticipant(ModelledInteraction interaction, Interactor interactor, CvTerm bioRole) {
        super(interaction, interactor, bioRole);
    }

    public MitabModelledParticipant(ModelledInteraction interaction, Interactor interactor, Stoichiometry stoichiometry) {
        super(interaction, interactor, stoichiometry);
    }

    public MitabModelledParticipant(ModelledInteraction interaction, Interactor interactor, CvTerm bioRole, Stoichiometry stoichiometry) {
        super(interaction, interactor, bioRole, stoichiometry);
    }

    public MitabModelledParticipant(Interactor interactor) {
        super(interactor);
    }

    public MitabModelledParticipant(Interactor interactor, CvTerm bioRole) {
        super(interactor, bioRole);
    }

    public MitabModelledParticipant(Interactor interactor, Stoichiometry stoichiometry) {
        super(interactor, stoichiometry);
    }

    public MitabModelledParticipant(Interactor interactor, CvTerm bioRole, Stoichiometry stoichiometry) {
        super(interactor, bioRole, stoichiometry);
    }

    public MitabSourceLocator getSourceLocator() {
        return sourceLocator;
    }

    public void setSourceLocator(MitabSourceLocator sourceLocator) {
        this.sourceLocator = sourceLocator;
    }
}
