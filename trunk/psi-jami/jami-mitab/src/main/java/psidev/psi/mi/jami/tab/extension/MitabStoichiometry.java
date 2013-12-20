package psidev.psi.mi.jami.tab.extension;

import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.datasource.FileSourceLocator;
import psidev.psi.mi.jami.model.impl.DefaultStoichiometry;

/**
 * Mitab extension for Stoichiometry.
 * It contains a FileSourceLocator
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>11/06/13</pre>
 */

public class MitabStoichiometry extends DefaultStoichiometry implements FileSourceContext{

    private FileSourceLocator sourceLocator;

    public MitabStoichiometry(int value) {
        super(value);
    }

    public MitabStoichiometry(int minValue, int maxValue) {
        super(minValue, maxValue);
    }

    public FileSourceLocator getSourceLocator() {
        return sourceLocator;
    }

    public void setSourceLocator(FileSourceLocator sourceLocator) {
        this.sourceLocator = sourceLocator;
    }

    @Override
    public String toString() {
        return "Mitab Stoichiometry: "+sourceLocator != null ? sourceLocator.toString():super.toString();
    }
}
