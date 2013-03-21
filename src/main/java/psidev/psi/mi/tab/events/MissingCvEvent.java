package psidev.psi.mi.tab.events;

import psidev.psi.mi.jami.datasource.DefaultFileSourceContext;
import psidev.psi.mi.jami.datasource.FileSourceParsingError;

import java.io.Serializable;

/**
 * This event is fired when we have a missing CvTerm
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>21/03/13</pre>
 */

public class MissingCvEvent extends DefaultFileSourceContext implements Serializable {

    private String message;
    private String level;
    private FileSourceParsingError errorType;

    public MissingCvEvent(String level, String message, FileSourceParsingError type){
        this.message = message;
        this.level = level;
        this.errorType = type;
    }

    public String getMessage() {
        return message;
    }

    public String getLevel() {
        return level;
    }

    public FileSourceParsingError getErrorType() {
        return errorType;
    }
}
