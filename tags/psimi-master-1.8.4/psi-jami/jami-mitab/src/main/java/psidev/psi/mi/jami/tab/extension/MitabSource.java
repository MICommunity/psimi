package psidev.psi.mi.jami.tab.extension;

import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.datasource.FileSourceLocator;
import psidev.psi.mi.jami.model.Publication;
import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.jami.model.impl.DefaultSource;
import psidev.psi.mi.jami.utils.XrefUtils;

/**
 * Mitab extension of MitabSource
 * It contains a FileSourceLocator
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>11/06/13</pre>
 */

public class MitabSource extends DefaultSource implements FileSourceContext{

    private FileSourceLocator sourceLocator;

    public MitabSource(String shortName) {
        super(shortName);
    }

    public MitabSource(String shortName, Xref ontologyId) {
        super(shortName, ontologyId);
    }

    public MitabSource(String shortName, String fullName, Xref ontologyId) {
        super(shortName, fullName, ontologyId);
    }

    public MitabSource(String shortName, String url, String address, Publication bibRef) {
        super(shortName, url, address, bibRef);
    }

    public MitabSource(String shortName, Xref ontologyId, String url, String address, Publication bibRef) {
        super(shortName, ontologyId, url, address, bibRef);
    }

    public MitabSource(String shortName, String fullName, Xref ontologyId, String url, String address, Publication bibRef) {
        super(shortName, fullName, ontologyId, url, address, bibRef);
    }

    public MitabSource(String shortName, String miId) {
        super(shortName, miId);
    }

    public MitabSource(String shortName, String fullName, String miId) {
        super(shortName, fullName, miId);
    }

    public MitabSource(String shortName, String miId, String url, String address, Publication bibRef) {
        super(shortName, miId, url, address, bibRef);
    }

    public MitabSource(String shortName, String fullName, String miId, String url, String address, Publication bibRef) {
        super(shortName, fullName, miId, url, address, bibRef);
    }

    public MitabSource(String shortName, String fullName, String db, String id) {
        super(shortName, XrefUtils.createIdentityXref(db, id));
        setFullName(fullName != null? fullName : shortName);
    }


    public FileSourceLocator getSourceLocator() {
        return sourceLocator;
    }

    public void setSourceLocator(FileSourceLocator sourceLocator) {
        this.sourceLocator = sourceLocator;
    }
}
