package psidev.psi.mi.jami.mitab.extension;

import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.impl.DefaultAlias;

/**
 * Extended alias in MITAB
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>07/06/13</pre>
 */

public class MitabAlias extends DefaultAlias{

    private String dbSource;

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

    public String getDbSource() {
        return dbSource;
    }
}
