package psidev.psi.mi.jami.tab.extension;

import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.datasource.FileSourceLocator;
import psidev.psi.mi.jami.model.Publication;
import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.jami.model.impl.DefaultSource;
import psidev.psi.mi.jami.tab.utils.MitabUtils;
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
        super(shortName != null ? shortName : MitabUtils.UNKNOWN_NAME);
    }

    public MitabSource(String shortName, Xref ontologyId) {
        super(shortName != null ? shortName : MitabUtils.UNKNOWN_NAME, ontologyId);
    }

    public MitabSource(String shortName, String fullName, Xref ontologyId) {
        super(shortName != null ? shortName : MitabUtils.UNKNOWN_NAME, fullName, ontologyId);
    }

    public MitabSource(String shortName, String url, String address, Publication bibRef) {
        super(shortName != null ? shortName : MitabUtils.UNKNOWN_NAME, url, address, bibRef);
    }

    public MitabSource(String shortName, Xref ontologyId, String url, String address, Publication bibRef) {
        super(shortName != null ? shortName : MitabUtils.UNKNOWN_NAME, ontologyId, url, address, bibRef);
    }

    public MitabSource(String shortName, String fullName, Xref ontologyId, String url, String address, Publication bibRef) {
        super(shortName != null ? shortName : MitabUtils.UNKNOWN_NAME, fullName, ontologyId, url, address, bibRef);
    }

    public MitabSource(String shortName, String miId) {
        super(shortName != null ? shortName : MitabUtils.UNKNOWN_NAME, miId);
    }

    public MitabSource(String shortName, String fullName, String miId) {
        super(shortName != null ? shortName : MitabUtils.UNKNOWN_NAME, fullName, miId);
    }

    public MitabSource(String shortName, String miId, String url, String address, Publication bibRef) {
        super(shortName != null ? shortName : MitabUtils.UNKNOWN_NAME, miId, url, address, bibRef);
    }

    public MitabSource(String shortName, String fullName, String miId, String url, String address, Publication bibRef) {
        super(shortName != null ? shortName : MitabUtils.UNKNOWN_NAME, fullName, miId, url, address, bibRef);
    }

    public MitabSource(String shortName, String fullName, String db, String id) {
        super(shortName != null ? shortName : MitabUtils.UNKNOWN_NAME);
        if (db != null && id != null && id.length() > 0){
            getIdentifiers().add(XrefUtils.createIdentityXref(db, id));
        }
        else if (db == null && id != null && id.length() > 0){
            getIdentifiers().add(XrefUtils.createIdentityXref(MitabUtils.UNKNOWN_DATABASE, id));
        }
        else if (id == null || (id != null && id.length() == 0)){
            getIdentifiers().add(XrefUtils.createIdentityXref(db, MitabUtils.UNKNOWN_ID));
        }
        setFullName(fullName != null? fullName : shortName);
    }


    public FileSourceLocator getSourceLocator() {
        return sourceLocator;
    }

    public void setSourceLocator(FileSourceLocator sourceLocator) {
        this.sourceLocator = sourceLocator;
    }

    @Override
    public String toString() {
        return "Source: "+sourceLocator != null ? sourceLocator.toString():super.toString();
    }
}
