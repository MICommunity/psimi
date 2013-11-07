package psidev.psi.mi.jami.tab.extension;

import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.datasource.FileSourceLocator;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Interactor;
import psidev.psi.mi.jami.model.Stoichiometry;
import psidev.psi.mi.jami.model.impl.DefaultModelledEntity;

/**
 * Mitab implementation of ModelledEntity.
 *
 * It is a FileSourceContext
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>07/10/13</pre>
 */

public class MitabModelledEntity extends DefaultModelledEntity implements FileSourceContext {
    private FileSourceLocator sourceLocator;
    public MitabModelledEntity(Interactor interactor) {
        super(interactor);
    }

    public MitabModelledEntity(Interactor interactor, CvTerm bioRole) {
        super(interactor, bioRole);
    }

    public MitabModelledEntity(Interactor interactor, Stoichiometry stoichiometry) {
        super(interactor, stoichiometry);
    }

    public MitabModelledEntity(Interactor interactor, CvTerm bioRole, Stoichiometry stoichiometry) {
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
        return "Entity: "+sourceLocator != null ? sourceLocator.toString():super.toString();
    }
}
