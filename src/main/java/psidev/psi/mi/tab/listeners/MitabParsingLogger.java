package psidev.psi.mi.tab.listeners;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import psidev.psi.mi.jami.datasource.FileSourceLocator;
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
    private StringBuffer stringBuffer = new StringBuffer();

    public void fireOnInvalidFormat(InvalidFormatEvent event) {

        if (event != null){
            stringBuffer.setLength(0);
            stringBuffer.append("There is a syntax error: ");
            if (event.getSourceLocator() != null){
                FileSourceLocator locator = event.getSourceLocator();
                stringBuffer.append(locator.getLocationDescription());
            }
            stringBuffer.append(event.getMessage());
            log.error(stringBuffer.toString());
        }
    }

    public void fireOnClusteredColumnEvent(ClusteredColumnEvent event) {
        if (event != null){
            stringBuffer.setLength(0);
            stringBuffer.append("The parser detected some clustered content: ");
            if (event.getSourceLocator() != null){
                FileSourceLocator locator = event.getSourceLocator();
                stringBuffer.append(locator.getLocationDescription());
            }
            stringBuffer.append(event.getMessage());
            log.warn(stringBuffer.toString());
        }
    }

    public void fireOnMissingCvEvent(MissingCvEvent event) {
        if (event != null && event.getLevel() != null && event.getLevel().length() > 0){
            stringBuffer.setLength(0);
            stringBuffer.append("The parser did not find any controlled vocabulary terms for: ");
            if (event.getSourceLocator() != null){
                FileSourceLocator locator = event.getSourceLocator();
                stringBuffer.append(locator.getLocationDescription());
            }
            stringBuffer.append(event.getMessage());
            if (event.getLevel().equalsIgnoreCase("info")){
                log.info(stringBuffer.toString());
            }
            else if (event.getLevel().equalsIgnoreCase("warn")){
                log.warn(stringBuffer.toString());
            }
            else if (event.getLevel().equalsIgnoreCase("error")){
                log.error(stringBuffer.toString());
            }
            else if (event.getLevel().equalsIgnoreCase("fatal")){
                log.fatal(stringBuffer.toString());
            }
        }
    }


}
