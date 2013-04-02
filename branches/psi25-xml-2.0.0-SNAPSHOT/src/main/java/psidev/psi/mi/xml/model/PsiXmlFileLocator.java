package psidev.psi.mi.xml.model;

import psidev.psi.mi.jami.datasource.FileSourceLocator;

/**
 * TODO comment this
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>22/03/13</pre>
 */

public class PsiXmlFileLocator extends FileSourceLocator {
    private Integer id;

    public PsiXmlFileLocator(int lineNumber, int charNumber, Integer id) {
        super(lineNumber, charNumber);
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    @Override
    public String getLocationDescription() {
        return super.getLocationDescription() + (id != null ? ", Id: "+id : "");
    }
}
