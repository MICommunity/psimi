package psidev.psi.mi.jami.tab.extension;

import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.datasource.FileSourceLocator;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Stoichiometry;
import psidev.psi.mi.jami.model.impl.DefaultModelledParticipantPool;

/**
 * Mitab implementation of modelled participant pool with a source locator
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>07/10/13</pre>
 */

public class MitabModelledParticipantPool extends DefaultModelledParticipantPool implements FileSourceContext {
    private FileSourceLocator sourceLocator;
    public MitabModelledParticipantPool(String interactorSetName) {
        super(interactorSetName);
    }

    public MitabModelledParticipantPool(String interactorSetName, CvTerm bioRole) {
        super(interactorSetName, bioRole);
    }

    public MitabModelledParticipantPool(String interactorSetName, Stoichiometry stoichiometry) {
        super(interactorSetName, stoichiometry);
    }

    public MitabModelledParticipantPool(String interactorSetName, CvTerm bioRole, Stoichiometry stoichiometry) {
        super(interactorSetName, bioRole, stoichiometry);
    }

    public FileSourceLocator getSourceLocator() {
        return sourceLocator;
    }

    public void setSourceLocator(FileSourceLocator sourceLocator) {
        this.sourceLocator = sourceLocator;
    }

    @Override
    public String toString() {
        return "Entity set: "+sourceLocator != null ? sourceLocator.toString():super.toString();
    }
}
