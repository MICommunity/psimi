package psidev.psi.mi.jami.tab.extension;

import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Interactor;
import psidev.psi.mi.jami.model.Stoichiometry;
import psidev.psi.mi.jami.model.impl.DefaultParticipant;

/**
 * Mitab extension for Participant.
 * It contains a FileSourceLocator
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>11/06/13</pre>
 */

public class MitabParticipant<T extends Interactor> extends DefaultParticipant<T> implements FileSourceContext{

    private MitabSourceLocator sourceLocator;

    public MitabParticipant(T interactor) {
        super(interactor);
    }

    public MitabParticipant(T interactor, CvTerm bioRole) {
        super(interactor, bioRole);
    }

    public MitabParticipant(T interactor, Stoichiometry stoichiometry) {
        super(interactor, stoichiometry);
    }

    public MitabParticipant(T interactor, CvTerm bioRole, Stoichiometry stoichiometry) {
        super(interactor, bioRole, stoichiometry);
    }

    public MitabSourceLocator getSourceLocator() {
        return sourceLocator;
    }

    public void setSourceLocator(MitabSourceLocator sourceLocator) {
        this.sourceLocator = sourceLocator;
    }
}
