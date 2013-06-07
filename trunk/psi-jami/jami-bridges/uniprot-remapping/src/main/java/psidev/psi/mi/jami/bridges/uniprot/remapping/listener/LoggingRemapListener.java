package psidev.psi.mi.jami.bridges.uniprot.remapping.listener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import psidev.psi.mi.jami.bridges.uniprot.remapping.RemapReport;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 07/06/13
 * Time: 16:04
 */
public class LoggingRemapListener implements RemapListener {

    public static final Log log = LogFactory.getLog(LoggingRemapListener.class);

    public void fireRemapReport(RemapReport report) {
        log.info("Was remapped? "+report.isRemapped());
        log.info("Conflict message: "+report.getConflictMessage());
    }
}
