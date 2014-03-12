package psidev.psi.mi.jami.tab.extension;

import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.datasource.FileSourceLocator;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Stoichiometry;
import psidev.psi.mi.jami.model.impl.DefaultParticipantPool;

/**
 * Mitab implementation of a ParticipantPool
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>07/10/13</pre>
 */

public class MitabParticipantPool extends DefaultParticipantPool implements FileSourceContext {
    private FileSourceLocator sourceLocator;
    public MitabParticipantPool(String interactorSetName) {
        super(interactorSetName);
    }

    public MitabParticipantPool(String interactorSetName, CvTerm bioRole) {
        super(interactorSetName, bioRole);
    }

    public MitabParticipantPool(String interactorSetName, Stoichiometry stoichiometry) {
        super(interactorSetName, stoichiometry);
    }

    public MitabParticipantPool(String interactorSetName, CvTerm bioRole, Stoichiometry stoichiometry) {
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
