package psidev.psi.mi.jami.tab.extension;

import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.datasource.FileSourceLocator;
import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.jami.model.impl.DefaultCvTerm;
import psidev.psi.mi.jami.tab.utils.MitabUtils;
import psidev.psi.mi.jami.utils.XrefUtils;

/**
 * Mitab extension for CvTerm.
 * It contains a FileSourceLocator
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>11/06/13</pre>
 */

public class MitabCvTerm extends DefaultCvTerm implements FileSourceContext{

    private FileSourceLocator sourceLocator;

    public MitabCvTerm(String shortName) {
        super(shortName != null ? shortName : MitabUtils.UNKNOWN_NAME);
    }

    public MitabCvTerm(String shortName, String miIdentifier) {
        super(shortName != null ? shortName : MitabUtils.UNKNOWN_NAME, miIdentifier);
    }

    public MitabCvTerm(String shortName, String fullName, String miIdentifier) {
        super(shortName != null ? shortName : MitabUtils.UNKNOWN_NAME, fullName, miIdentifier);
    }

    public MitabCvTerm(String shortName, Xref ontologyId) {
        super(shortName != null ? shortName : MitabUtils.UNKNOWN_NAME, ontologyId);
    }

    public MitabCvTerm(String shortName, String fullName, Xref ontologyId) {
        super(shortName != null ? shortName : MitabUtils.UNKNOWN_NAME, fullName, ontologyId);
    }

    public MitabCvTerm(String shortName, String fullName, String db, String id) {
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
        return "Mitab CV Term: "+sourceLocator != null ? sourceLocator.toString():super.toString();
    }
}
