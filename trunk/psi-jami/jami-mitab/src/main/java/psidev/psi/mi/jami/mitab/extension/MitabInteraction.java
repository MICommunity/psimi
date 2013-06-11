package psidev.psi.mi.jami.mitab.extension;

import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.impl.DefaultInteraction;

/**
 * Mitab extension for Interaction
 * It contains a SourceLocator
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>11/06/13</pre>
 */

public class MitabInteraction extends DefaultInteraction implements FileSourceContext{

    private MitabSourceLocator sourceLocator;

    public MitabInteraction() {
        super();
    }

    public MitabInteraction(String shortName) {
        super(shortName);
    }

    public MitabInteraction(String shortName, CvTerm type) {
        super(shortName, type);
    }

    public MitabSourceLocator getSourceLocator() {
        return sourceLocator;
    }

    public void setSourceLocator(MitabSourceLocator sourceLocator) {
        this.sourceLocator = sourceLocator;
    }
}
