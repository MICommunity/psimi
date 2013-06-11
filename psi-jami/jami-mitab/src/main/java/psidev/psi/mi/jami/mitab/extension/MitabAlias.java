package psidev.psi.mi.jami.mitab.extension;

import psidev.psi.mi.jami.datasource.FileSourceContext;
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
    private MitabSourceLocator sourceLocator;

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

    public MitabAlias(String dbSource, CvTerm type, String name, MitabSourceLocator locator) {
        super(type, name);
        if (dbSource == null){
            throw new IllegalArgumentException("The dbsource of a MITAB alias is required");
        }
        this.dbSource = dbSource;
        this.sourceLocator = locator;
    }

    public MitabAlias(String dbSource, String name, MitabSourceLocator locator) {
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

    public MitabSourceLocator getSourceLocator() {
        return this.sourceLocator;
    }
}
