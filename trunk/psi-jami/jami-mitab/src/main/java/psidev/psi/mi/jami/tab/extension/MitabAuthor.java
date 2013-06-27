package psidev.psi.mi.jami.tab.extension;

import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.datasource.FileSourceLocator;
import psidev.psi.mi.jami.tab.utils.MitabUtils;

import java.text.ParseException;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    private FileSourceLocator sourceLocator;
    private Pattern year = Pattern.compile("[0-9]{4}");

    public MitabAuthor(String firstAuthor){
        if (firstAuthor != null){
            Matcher matcher = year.matcher(firstAuthor);
            int count = 0;
            while (matcher.find()){
                count++;
                // we don't recognize any publication date
                if (count > 1){
                    this.firstAuthor = firstAuthor;
                    this.publicationDate = null;
                    break;
                }
                else{
                    try {
                        String date = matcher.group();
                        this.publicationDate = MitabUtils.PUBLICATION_YEAR_FORMAT.parse(date);
                        this.firstAuthor = firstAuthor.replaceAll("\\(|\\)|et al.|date","");
                    } catch (ParseException e) {
                        e.printStackTrace();
                        this.firstAuthor = firstAuthor;
                        this.publicationDate = null;
                        break;
                    }
                }
            }
        }
        else{
            this.firstAuthor = null;
            publicationDate = null;
        }
    }

    public MitabAuthor(String firstAuthor, String publicationDate){
        this.firstAuthor = firstAuthor != null ? firstAuthor.replaceAll("et al.","") : null;
        try {
            this.publicationDate = MitabUtils.PUBLICATION_YEAR_FORMAT.parse(publicationDate);
        } catch (ParseException e) {
            e.printStackTrace();
            if (firstAuthor == null){
               this.firstAuthor = publicationDate;
            }
            else{
                this.firstAuthor = firstAuthor.replaceAll("et al.","") + " " + publicationDate;
            }
        }
    }

    public String getFirstAuthor() {
        return firstAuthor;
    }

    public Date getPublicationDate() {
        return publicationDate;
    }

    public FileSourceLocator getSourceLocator() {
        return sourceLocator;
    }

    public void setSourceLocator(FileSourceLocator sourceLocator) {
        this.sourceLocator = sourceLocator;
    }
}
