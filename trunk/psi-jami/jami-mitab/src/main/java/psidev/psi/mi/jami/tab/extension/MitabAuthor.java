package psidev.psi.mi.jami.tab.extension;

import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.tab.utils.MitabUtils;

import java.text.ParseException;
import java.util.Date;

/**
 * This class wraps information about the first author found in mitab column 8
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>14/06/13</pre>
 */

public class MitabAuthor implements FileSourceContext{

    private String firstAuthor;
    private Date publicationDate;
    private MitabSourceLocator sourceLocator;

    public MitabAuthor(String firstAuthor){
        this.firstAuthor = firstAuthor;
        this.publicationDate = null;
    }

    public MitabAuthor(String firstAuthor, String publicationDate){
        this.firstAuthor = firstAuthor;
        try {
            this.publicationDate = MitabUtils.PUBLICATION_YEAR_FORMAT.parse(publicationDate);
        } catch (ParseException e) {
            e.printStackTrace();
            this.firstAuthor = firstAuthor + " " + publicationDate;
        }
    }

    public String getFirstAuthor() {
        return firstAuthor;
    }

    public Date getPublicationDate() {
        return publicationDate;
    }

    public MitabSourceLocator getSourceLocator() {
        return sourceLocator;
    }

    public void setSourceLocator(MitabSourceLocator sourceLocator) {
        this.sourceLocator = sourceLocator;
    }
}
