package psidev.psi.mi.jami.bridges.mapper.listener;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import psidev.psi.mi.jami.bridges.mapper.ProteinMapperListener;
import psidev.psi.mi.jami.model.Protein;

import java.util.Collection;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 23/07/13
 */
public class ProteinMappingLogger implements ProteinMapperListener {

    protected static final Logger log = LoggerFactory.getLogger(ProteinMappingLogger.class.getName());


    public void onSuccessfulMapping(Protein p, Collection<String> report) {
        log.info("Remapping succeeded: ");
        for(String string : report){
            log.info(string);
        }
    }

    public void onFailedMapping(Protein p, Collection<String> report) {
        log.info("Remapping failed: ");
        for(String string : report){
            log.info(string);
        }
    }
}
