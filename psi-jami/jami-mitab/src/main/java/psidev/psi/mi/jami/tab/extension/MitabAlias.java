package psidev.psi.mi.jami.tab.extension;

import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.datasource.FileSourceLocator;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.impl.DefaultAlias;
import psidev.psi.mi.jami.tab.utils.MitabUtils;

/**
 * Extended alias in MITAB
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>07/06/13</pre>
 */

public class MitabAlias extends DefaultAlias implements FileSourceContext{

    private String dbSource;
    private FileSourceLocator sourceLocator;

    public MitabAlias(String dbSource, CvTerm type, String name) {
        super(type, name != null ? name : MitabUtils.UNKNOWN_NAME);
        this.dbSource = dbSource != null ? dbSource : MitabUtils.UNKNOWN_DATABASE;
    }

    public MitabAlias(String dbSource, String name) {
        super(name != null ? name : MitabUtils.UNKNOWN_NAME);
        this.dbSource = dbSource != null ? dbSource : MitabUtils.UNKNOWN_DATABASE;
    }

    public MitabAlias(String dbSource, String name, String type) {
        super(type != null ? new MitabCvTerm(type) : null, name != null ? name : MitabUtils.UNKNOWN_NAME);
        this.dbSource = dbSource != null ? dbSource : MitabUtils.UNKNOWN_DATABASE;
    }

    public MitabAlias(String dbSource, CvTerm type, String name, FileSourceLocator locator) {
        super(type, name != null ? name : MitabUtils.UNKNOWN_NAME);
        this.dbSource = dbSource != null ? dbSource : MitabUtils.UNKNOWN_DATABASE;
        this.sourceLocator = locator;
    }

    public MitabAlias(String dbSource, String name, FileSourceLocator locator) {
        super(name != null ? name : MitabUtils.UNKNOWN_NAME);
        this.dbSource = dbSource != null ? dbSource : MitabUtils.UNKNOWN_DATABASE;
        this.sourceLocator = locator;
    }

    public String getDbSource() {
        return dbSource;
    }

    public FileSourceLocator getSourceLocator() {
        return this.sourceLocator;
    }

    public void setSourceLocator(FileSourceLocator sourceLocator) {
        this.sourceLocator = sourceLocator;
    }

    @Override
    public String toString() {
        return "Mitab Alias: "+sourceLocator != null ? sourceLocator.toString():super.toString();
    }
}
