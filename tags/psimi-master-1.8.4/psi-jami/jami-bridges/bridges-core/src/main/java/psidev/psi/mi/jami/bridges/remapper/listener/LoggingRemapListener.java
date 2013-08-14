package psidev.psi.mi.jami.bridges.remapper.listener;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import psidev.psi.mi.jami.bridges.remapper.ProteinRemapperListener;
import psidev.psi.mi.jami.model.Protein;

import java.util.Collection;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 23/07/13
 */
public class LoggingRemapListener implements ProteinRemapperListener {

    protected static final Logger log = LoggerFactory.getLogger(LoggingRemapListener.class.getName());


    public void onRemappingSuccessful(Protein p, Collection<String> report) {
        log.info("Remapping succeeded: ");
        for(String string : report){
            log.info(string);
        }
    }

    public void onRemappingFailed(Protein p, Collection<String> report) {
        log.info("Remapping failed: ");
        for(String string : report){
            log.info(string);
        }
    }
}
