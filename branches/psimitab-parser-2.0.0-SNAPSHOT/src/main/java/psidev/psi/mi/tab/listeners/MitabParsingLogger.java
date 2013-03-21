package psidev.psi.mi.tab.listeners;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import psidev.psi.mi.tab.events.ClusteredColumnEvent;
import psidev.psi.mi.tab.events.InvalidFormatEvent;
import psidev.psi.mi.tab.events.MissingCvEvent;

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

    public void fireOnMissingCvEvent(MissingCvEvent event) {
        if (event != null && event.getLevel() != null && event.getLevel().length() > 0){
            if (event.getLevel().equalsIgnoreCase("info")){
                log.info("The parser did not find any controlled vocabulary term at line "+event.getLineNumber()+", column "+event.getColumnNumber() + ": " + event.getMessage());
            }
            else if (event.getLevel().equalsIgnoreCase("warn")){
                log.warn("The parser detected a missing controlled vocabulary term at line "+event.getLineNumber()+", column "+event.getColumnNumber() + ": " + event.getMessage());
            }
            else if (event.getLevel().equalsIgnoreCase("error")){
                log.error("The parser detected a missing controlled vocabulary term at line "+event.getLineNumber()+", column "+event.getColumnNumber() + ": " + event.getMessage());
            }
            else if (event.getLevel().equalsIgnoreCase("fatal")){
                log.fatal("The parser detected a missing controlled vocabulary term at line "+event.getLineNumber()+", column "+event.getColumnNumber() + ": " + event.getMessage());
            }
        }
    }


}
