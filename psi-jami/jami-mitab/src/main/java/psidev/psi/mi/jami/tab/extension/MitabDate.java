package psidev.psi.mi.jami.tab.extension;

import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.datasource.FileSourceLocator;
import psidev.psi.mi.jami.tab.utils.MitabUtils;

import java.text.ParseException;
import java.util.Date;

/**
 * This class wraps a date and a fileSourceLocator
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>14/06/13</pre>
 */

public class MitabDate implements FileSourceContext{

    private FileSourceLocator sourceLocator;
    private Date date;

    public MitabDate(String date) throws ParseException {
        this.date = MitabUtils.DATE_FORMAT.parse(date);
    }

    public FileSourceLocator getSourceLocator() {
        return sourceLocator;
    }

    public Date getDate() {
        return date;
    }

    public void setSourceLocator(FileSourceLocator sourceLocator) {
        this.sourceLocator = sourceLocator;
    }

    @Override
    public String toString() {
        return "Mitab date: "+sourceLocator != null ? sourceLocator.toString():super.toString();
    }
}
