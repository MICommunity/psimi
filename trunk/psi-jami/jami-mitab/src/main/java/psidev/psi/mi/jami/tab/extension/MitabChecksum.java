package psidev.psi.mi.jami.tab.extension;

import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.datasource.FileSourceLocator;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.impl.DefaultChecksum;
import psidev.psi.mi.jami.tab.utils.MitabUtils;

/**
 * Mitab extension for Checksum.
 * It contains a file sourceLocator
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>11/06/13</pre>
 */

public class MitabChecksum extends DefaultChecksum implements FileSourceContext{

    private FileSourceLocator sourceLocator;

    public MitabChecksum(CvTerm method, String value) {
        super(method, value);
    }

    public MitabChecksum(String method, String value) {
        super(new MitabCvTerm(method != null ? method: MitabUtils.UNKNOWN_TYPE), value!= null ? value : MitabUtils.UNKNOWN_ID);
    }

    public MitabChecksum(CvTerm method, String value, FileSourceLocator locator) {
        super(method, value);
        this.sourceLocator = locator;
    }

    public FileSourceLocator getSourceLocator() {
        return this.sourceLocator;
    }

    public void setSourceLocator(FileSourceLocator sourceLocator) {
        this.sourceLocator = sourceLocator;
    }

    @Override
    public String toString() {
        return "Mitab Checksum: "+sourceLocator != null ? sourceLocator.toString():super.toString();
    }
}
