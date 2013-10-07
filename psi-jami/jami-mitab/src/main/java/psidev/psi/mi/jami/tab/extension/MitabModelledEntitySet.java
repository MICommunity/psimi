package psidev.psi.mi.jami.tab.extension;

import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.datasource.FileSourceLocator;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Stoichiometry;
import psidev.psi.mi.jami.model.impl.DefaultModelledEntitySet;

/**
 * Mitab implementation of ModelledEntitySet with a source locator
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>07/10/13</pre>
 */

public class MitabModelledEntitySet extends DefaultModelledEntitySet implements FileSourceContext {
    private FileSourceLocator sourceLocator;
    public MitabModelledEntitySet(String interactorSetName) {
        super(interactorSetName);
    }

    public MitabModelledEntitySet(String interactorSetName, CvTerm bioRole) {
        super(interactorSetName, bioRole);
    }

    public MitabModelledEntitySet(String interactorSetName, Stoichiometry stoichiometry) {
        super(interactorSetName, stoichiometry);
    }

    public MitabModelledEntitySet(String interactorSetName, CvTerm bioRole, Stoichiometry stoichiometry) {
        super(interactorSetName, bioRole, stoichiometry);
    }

    public FileSourceLocator getSourceLocator() {
        return sourceLocator;
    }

    public void setSourceLocator(FileSourceLocator sourceLocator) {
        this.sourceLocator = sourceLocator;
    }
}
