package psidev.psi.mi.jami.tab.extension;

import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.datasource.FileSourceLocator;
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

    private FileSourceLocator sourceLocator;

    public MitabInteraction() {
        super();
    }

    public MitabInteraction(String shortName) {
        super(shortName);
    }

    public MitabInteraction(String shortName, CvTerm type) {
        super(shortName, type);
    }

    public FileSourceLocator getSourceLocator() {
        return sourceLocator;
    }

    public void setSourceLocator(FileSourceLocator sourceLocator) {
        this.sourceLocator = sourceLocator;
    }
}
