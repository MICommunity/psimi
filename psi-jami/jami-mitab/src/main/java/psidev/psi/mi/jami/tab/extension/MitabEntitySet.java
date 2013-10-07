package psidev.psi.mi.jami.tab.extension;

import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.datasource.FileSourceLocator;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Stoichiometry;
import psidev.psi.mi.jami.model.impl.DefaultEntitySet;

/**
 * Mitab implementation of an EntitySet
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>07/10/13</pre>
 */

public class MitabEntitySet extends DefaultEntitySet implements FileSourceContext {
    private FileSourceLocator sourceLocator;
    public MitabEntitySet(String interactorSetName) {
        super(interactorSetName);
    }

    public MitabEntitySet(String interactorSetName, CvTerm bioRole) {
        super(interactorSetName, bioRole);
    }

    public MitabEntitySet(String interactorSetName, Stoichiometry stoichiometry) {
        super(interactorSetName, stoichiometry);
    }

    public MitabEntitySet(String interactorSetName, CvTerm bioRole, Stoichiometry stoichiometry) {
        super(interactorSetName, bioRole, stoichiometry);
    }

    public FileSourceLocator getSourceLocator() {
        return sourceLocator;
    }

    public void setSourceLocator(FileSourceLocator sourceLocator) {
        this.sourceLocator = sourceLocator;
    }
}
