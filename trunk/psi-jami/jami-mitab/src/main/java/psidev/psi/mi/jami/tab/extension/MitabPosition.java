package psidev.psi.mi.jami.tab.extension;

import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.impl.DefaultPosition;

/**
 * Mitab extension of Position.
 * It contains a FileSourceLocator
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>11/06/13</pre>
 */

public class MitabPosition extends DefaultPosition implements FileSourceContext{

    private MitabSourceLocator spurceLocator;

    public MitabPosition(long start, long end) {
        super(start, end);
    }

    public MitabPosition(CvTerm status, long start, long end) {
        super(status, start, end);
    }

    public MitabPosition(CvTerm status, long position) {
        super(status, position);
    }

    public MitabPosition(long position) {
        super(position);
    }

    public MitabSourceLocator getSourceLocator() {
        return spurceLocator;
    }

    public void setSourceLocator(MitabSourceLocator spurceLocator) {
        this.spurceLocator = spurceLocator;
    }
}
