package psidev.psi.mi.jami.mitab.extension;

import psidev.psi.mi.jami.model.impl.DefaultStoichiometry;

/**
 * Mitab extension for Stoichiometry.
 * It contains a FileSourceLocator
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>11/06/13</pre>
 */

public class MitabStoichiometry extends DefaultStoichiometry {

    private MitabSourceLocator sourceLocator;

    public MitabStoichiometry(long value) {
        super(value);
    }

    public MitabStoichiometry(long minValue, long maxValue) {
        super(minValue, maxValue);
    }

    public MitabSourceLocator getSourceLocator() {
        return sourceLocator;
    }

    public void setSourceLocator(MitabSourceLocator sourceLocator) {
        this.sourceLocator = sourceLocator;
    }
}
