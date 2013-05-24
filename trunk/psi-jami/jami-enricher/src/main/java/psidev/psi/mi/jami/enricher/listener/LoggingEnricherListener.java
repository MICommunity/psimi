package psidev.psi.mi.jami.enricher.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import psidev.psi.mi.jami.enricher.event.AdditionReport;
import psidev.psi.mi.jami.enricher.event.EnricherEvent;
import psidev.psi.mi.jami.enricher.event.MismatchReport;
import psidev.psi.mi.jami.enricher.event.OverwriteReport;

/**
 * An implementation of the enricherListener which reports to the logger.
 * Upon receiving an enricherEvent,
 * it will report all additions, mismatches and overwrites which have taken place.
 *
 * @author: Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 16/05/13
 * Time: 13:09
 */
public class LoggingEnricherListener implements EnricherListener {

    private final Logger log = LoggerFactory.getLogger(LoggingEnricherListener.class.getName());

    public void onEnricherEvent(EnricherEvent e) {
        log.info(" ---- New Log ----");
        logAllReports(e);
    }

    public void logAllReports(EnricherEvent e) {
        log.info("Logging for: ["+e.getQueryID()+"] " +
                "Object type : " +e.getObjectType()+
                "(a query on ["+e.getQueryIDType()+"])");

        for(AdditionReport r :e.getAdditions()) {
            log.info("Addition on ["+r.getField()+"] " +
                    "was added the value: ["+r.getNewValue()+"]");
        }

        for(MismatchReport r :e.getMismatches()) {
            log.info("Mismatch on ["+r.getField()+"] " +
                    "has the current value ["+r.getOldValue()+"] " +
                    "which mismatches ["+r.getNewValue()+"]");
        }

        for(OverwriteReport r :e.getOverwrites()) {
            log.info("Overwrite on ["+r.getField()+"] " +
                    "had the old value ["+r.getOldValue()+"] " +
                    "overwritten with ["+r.getNewValue()+"]");
        }

        for(EnricherEvent s :e.getSubEnricherEvents()) {
            log.info("Sub enrichment begins");
            logAllReports(s);
        }
    }
}
