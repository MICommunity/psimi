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

    public MitabStoichiometry(long value) {
        super(value);
    }

    public MitabStoichiometry(long minValue, long maxValue) {
        super(minValue, maxValue);
    }

    public FileSourceLocator getSourceLocator() {
        return sourceLocator;
    }

    public void setSourceLocator(FileSourceLocator sourceLocator) {
        this.sourceLocator = sourceLocator;
    }
}
