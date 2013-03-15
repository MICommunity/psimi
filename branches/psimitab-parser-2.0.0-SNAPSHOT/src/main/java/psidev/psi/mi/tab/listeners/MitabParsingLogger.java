package psidev.psi.mi.tab.listeners;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import psidev.psi.mi.tab.events.ClusteredColumnEvent;
import psidev.psi.mi.tab.events.InvalidFormatEvent;

/**
 * This listener will just log events
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>15/03/13</pre>
 */

public class MitabParsingLogger implements MitabParserListener {

    private Log log = LogFactory.getLog(MitabParsingLogger.class);

    public void fireOnInvalidFormat(InvalidFormatEvent event) {

        if (event != null){
           log.error("There is a syntax error in line "+event.getLineNumber()+", column "+event.getColumnNumber() + ": " + event.getMessage());
        }
    }

    public void fireOnClusteredColumnEvent(ClusteredColumnEvent event) {
        if (event != null){
            log.warn("The parser detected some clustered content at line "+event.getLineNumber()+", column "+event.getColumnNumber() + ": " + event.getMessage());
        }
    }
}
