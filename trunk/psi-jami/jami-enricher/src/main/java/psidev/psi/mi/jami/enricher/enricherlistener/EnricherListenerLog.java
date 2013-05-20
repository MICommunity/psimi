package psidev.psi.mi.jami.enricher.enricherlistener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import psidev.psi.mi.enricherlistener.event.AdditionEvent;
import psidev.psi.mi.enricherlistener.event.EnricherEvent;
import psidev.psi.mi.enricherlistener.event.MismatchEvent;
import psidev.psi.mi.enricherlistener.event.OverwriteEvent;

/**
 * An implementation of the enricherListener which reports to the logger.
 * Upon receiving an enricherEvent,
 * it will report all additions, mismatches and overwrites which have taken place.
 *
 * @author: Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 16/05/13
 * Time: 13:09
 */
public class EnricherListenerLog implements EnricherListener {

    private final Logger log = LoggerFactory.getLogger(EnricherListenerLog.class.getName());

    public void enricherEvent(EnricherEvent e) {
        log.info("Logging for: ["+e.getQueryID()+"] (a query on ["+e.getQueryIDType()+"])");

        for(AdditionEvent a :e.getAdditions()) {
            log.info("Addition on ["+a.getField()+"] was added the value: ["+a.getNewValue()+"]");
        }

        for(MismatchEvent a :e.getMismatches()) {
            log.info("Mismatch on ["+a.getField()+"] has the current value ["+a.getOldValue()+"] which mismatches ["+a.getNewValue()+"]");
        }

        for(OverwriteEvent a :e.getOverwrites()) {
            log.info("Overwrite on ["+a.getField()+"] had the old value ["+a.getOldValue()+"] overwritten with ["+a.getNewValue()+"]");
        }

    }
}
