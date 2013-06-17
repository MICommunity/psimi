package psidev.psi.mi.jami.tab.extension;

import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Source;
import psidev.psi.mi.jami.model.impl.DefaultModelledInteraction;

/**
 * Mitab extension for ModelledInteraction.
 * It contains a FileSourceLocator
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>11/06/13</pre>
 */

public class MitabModelledInteraction extends DefaultModelledInteraction implements FileSourceContext{

    private MitabSourceLocator sourceLocator;

    public MitabModelledInteraction() {
        super();
    }

    public MitabModelledInteraction(String shortName) {
        super(shortName);
    }

    public MitabModelledInteraction(String shortName, Source source) {
        super(shortName, source);
    }

    public MitabModelledInteraction(String shortName, CvTerm type) {
        super(shortName, type);
    }

    public MitabModelledInteraction(String shortName, Source source, CvTerm type) {
        super(shortName, source, type);
    }

    public MitabSourceLocator getSourceLocator() {
        return sourceLocator;
    }

    public void setSourceLocator(MitabSourceLocator sourceLocator) {
        this.sourceLocator = sourceLocator;
    }
}
