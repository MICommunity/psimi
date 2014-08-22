package psidev.psi.mi.jami.extension;

import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.datasource.FileSourceLocator;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.impl.DefaultXref;

/**
 * CSV extension for Xref.
 * It contains a FileSourceLocator
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>14/06/13</pre>
 */

public class CsvXref extends DefaultXref implements FileSourceContext {

    private FileSourceLocator sourceLocator;

    public CsvXref(CvTerm database, String id, CvTerm qualifier){
        super(database, id, qualifier);
    }

    public CsvXref(CvTerm database, String id, String version, CvTerm qualifier){
        super(database, id, version, qualifier);
    }

    public CsvXref(CvTerm database, String id, String version){
        super(database, id, version);
    }

    public CsvXref(CvTerm database, String id){
        super(database, id);
    }

    public FileSourceLocator getSourceLocator() {
        return sourceLocator;
    }

    public void setSourceLocator(FileSourceLocator sourceLocator) {
        this.sourceLocator = sourceLocator;
    }

    @Override
    public String toString() {
        return "CSV Xref: "+sourceLocator != null ? sourceLocator.toString():super.toString();
    }
}
