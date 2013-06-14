package psidev.psi.mi.jami.mitab.extension;

import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.mitab.utils.MitabWriterUtils;

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

    private MitabSourceLocator sourceLocator;
    private Date date;

    public MitabDate(String date) throws ParseException {
        this.date = MitabWriterUtils.DATE_FORMAT.parse(date);
    }

    public MitabSourceLocator getSourceLocator() {
        return sourceLocator;
    }

    public Date getDate() {
        return date;
    }

    public void setSourceLocator(MitabSourceLocator sourceLocator) {
        this.sourceLocator = sourceLocator;
    }
}
