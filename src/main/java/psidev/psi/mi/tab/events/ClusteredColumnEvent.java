package psidev.psi.mi.tab.events;

import psidev.psi.mi.jami.datasource.DefaultFileSourceContext;
import psidev.psi.mi.jami.datasource.FileParsingErrorType;

import java.io.Serializable;
import java.util.Set;

/**
 * This event is fired when we have a clustered column in MITAB
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>01/03/13</pre>
 */

public class ClusteredColumnEvent extends DefaultFileSourceContext implements Serializable {

    private FileParsingErrorType errorType;
    private Set<String> values;
    private String message;

    public ClusteredColumnEvent(Set<String> values, FileParsingErrorType errorType, String message){
        this.values = values;
        this.message = message;
    }

    public Set<String> getValues() {
        return values;
    }

    public String getMessage() {
        return message;
    }

    public FileParsingErrorType getErrorType() {
        return errorType;
    }
}
