package psidev.psi.mi.enricherlistener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import psidev.psi.mi.enricherlistener.event.AdditionEvent;
import psidev.psi.mi.enricherlistener.event.EnricherEvent;
import psidev.psi.mi.enricherlistener.event.MismatchEvent;
import psidev.psi.mi.enricherlistener.event.OverwriteEvent;

/**
 * Created with IntelliJ IDEA.
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
            log.info("Addition on field ["+a.getField()+"] was added the value: ["+a.getNewValue()+"]");
        }

        for(MismatchEvent a :e.getMismatches()) {
            log.info("Mismatch on field ["+a.getField()+"] has the value ["+a.getOldValue()+"] which mismatches ["+a.getNewValue()+"]");
        }

        for(OverwriteEvent a :e.getOverwrites()) {
            log.info("Overwrite on field ["+a.getField()+"] had the value ["+a.getOldValue()+"] overwritten with ["+a.getNewValue()+"]");
        }

    }
}
