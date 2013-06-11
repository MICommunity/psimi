package psidev.psi.mi.jami.mitab.extension;

import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.jami.model.impl.DefaultCvTerm;

/**
 * Mitab extension for CvTerm.
 * It contains a FileSourceLocator
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>11/06/13</pre>
 */

public class MitabCvTerm extends DefaultCvTerm implements FileSourceContext{

    private MitabSourceLocator sourceLocator;

    public MitabCvTerm(String shortName) {
        super(shortName);
    }

    public MitabCvTerm(String shortName, String miIdentifier) {
        super(shortName, miIdentifier);
    }

    public MitabCvTerm(String shortName, String fullName, String miIdentifier) {
        super(shortName, fullName, miIdentifier);
    }

    public MitabCvTerm(String shortName, Xref ontologyId) {
        super(shortName, ontologyId);
    }

    public MitabCvTerm(String shortName, String fullName, Xref ontologyId) {
        super(shortName, fullName, ontologyId);
    }

    public MitabSourceLocator getSourceLocator() {
        return sourceLocator;
    }

    public void setSourceLocator(MitabSourceLocator sourceLocator) {
        this.sourceLocator = sourceLocator;
    }
}
