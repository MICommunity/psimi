package psidev.psi.mi.jami.tab.extension;

import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.datasource.FileSourceLocator;
import psidev.psi.mi.jami.model.CurationDepth;
import psidev.psi.mi.jami.model.Source;
import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.jami.model.impl.DefaultPublication;

import java.util.Date;

/**
 * Mitab extension of Publication.
 * It contains a FileSourceLocator
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>11/06/13</pre>
 */

public class MitabPublication extends DefaultPublication implements FileSourceContext{

    private FileSourceLocator sourceLocator;

    public MitabPublication() {
        super();
    }

    public MitabPublication(Xref identifier) {
        super(identifier);
    }

    public MitabPublication(Xref identifier, CurationDepth curationDepth, Source source) {
        super(identifier, curationDepth, source);
    }

    public MitabPublication(Xref identifier, String imexId, Source source) {
        super(identifier, imexId, source);
    }

    public MitabPublication(String pubmed) {
        super(pubmed);
    }

    public MitabPublication(String pubmed, CurationDepth curationDepth, Source source) {
        super(pubmed, curationDepth, source);
    }

    public MitabPublication(String pubmed, String imexId, Source source) {
        super(pubmed, imexId, source);
    }

    public MitabPublication(String title, String journal, Date publicationDate) {
        super(title, journal, publicationDate);
    }

    public MitabPublication(String title, String journal, Date publicationDate, CurationDepth curationDepth, Source source) {
        super(title, journal, publicationDate, curationDepth, source);
    }

    public MitabPublication(String title, String journal, Date publicationDate, String imexId, Source source) {
        super(title, journal, publicationDate, imexId, source);
    }

    public FileSourceLocator getSourceLocator() {
        return sourceLocator;
    }

    public void setSourceLocator(FileSourceLocator sourceLocator) {
        this.sourceLocator = sourceLocator;
    }

    @Override
    public String toString() {
        return (getSourceLocator() != null ? "Publication: "+getSourceLocator().toString():super.toString());
    }
}
