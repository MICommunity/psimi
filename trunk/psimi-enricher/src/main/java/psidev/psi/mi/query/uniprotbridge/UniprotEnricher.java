package psidev.psi.mi.query.uniprotbridge;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import psidev.psi.mi.exception.BridgeFailedException;
import uk.ac.ebi.kraken.interfaces.uniprot.UniProtEntry;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 07/05/13
 * Time: 10:09
 */
public class UniprotEnricher {
    UniprotFetcher fetcher;
    private final Logger log = LoggerFactory.getLogger(UniprotEnricher.class.getName());

    public UniprotEnricher(){
        fetcher = new UniprotFetcher();
    }

    public UniProtEntry getEntryByID(String ID){
        try{
            UniProtEntry e = fetcher.fetchEntryByID(ID);
            return e;

        }catch(BridgeFailedException e){
            log.error("BridgeFailedException");
            return null;
        }
    }
}
