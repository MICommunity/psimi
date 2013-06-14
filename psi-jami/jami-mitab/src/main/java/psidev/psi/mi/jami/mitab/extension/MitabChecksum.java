package psidev.psi.mi.jami.mitab.extension;

import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.impl.DefaultChecksum;

/**
 * Mitab extension for Checksum.
 * It contains a file sourceLocator
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>11/06/13</pre>
 */

public class MitabChecksum extends DefaultChecksum implements FileSourceContext{

    private MitabSourceLocator sourceLocator;

    public MitabChecksum(CvTerm method, String value) {
        super(method, value);
    }

    public MitabChecksum(String method, String value) {
        super(new MitabCvTerm(method), value);
    }

    public MitabChecksum(CvTerm method, String value, MitabSourceLocator locator) {
        super(method, value);
        this.sourceLocator = locator;
    }

    public MitabSourceLocator getSourceLocator() {
        return this.sourceLocator;
    }

    public void setSourceLocator(MitabSourceLocator sourceLocator) {
        this.sourceLocator = sourceLocator;
    }
}
