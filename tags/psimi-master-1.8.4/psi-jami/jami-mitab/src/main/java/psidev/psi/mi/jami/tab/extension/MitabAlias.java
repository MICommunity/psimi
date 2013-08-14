package psidev.psi.mi.jami.tab.extension;

import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.datasource.FileSourceLocator;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.impl.DefaultAlias;

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
        super(type, name);
        if (dbSource == null){
            throw new IllegalArgumentException("The dbsource of a MITAB alias is required");
        }
        this.dbSource = dbSource;
    }

    public MitabAlias(String dbSource, String name) {
        super(name);
        if (dbSource == null){
            throw new IllegalArgumentException("The dbsource of a MITAB alias is required");
        }
        this.dbSource = dbSource;
    }

    public MitabAlias(String dbSource, String name, String type) {
        super(type != null ? new MitabCvTerm(type) : null, name);
        if (dbSource == null){
            throw new IllegalArgumentException("The dbsource of a MITAB alias is required");
        }
        this.dbSource = dbSource;
    }

    public MitabAlias(String dbSource, CvTerm type, String name, FileSourceLocator locator) {
        super(type, name);
        if (dbSource == null){
            throw new IllegalArgumentException("The dbsource of a MITAB alias is required");
        }
        this.dbSource = dbSource;
        this.sourceLocator = locator;
    }

    public MitabAlias(String dbSource, String name, FileSourceLocator locator) {
        super(name);
        if (dbSource == null){
            throw new IllegalArgumentException("The dbsource of a MITAB alias is required");
        }
        this.dbSource = dbSource;
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
}
