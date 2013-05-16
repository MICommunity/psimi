package psidev.psi.mi.fetcher.uniprot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import psidev.psi.mi.enricher.organismenricher.OrganismFetcher;
import psidev.psi.mi.enricher.proteinenricher.ProteinFetcher;
import psidev.psi.mi.fetcher.exception.BridgeFailedException;
import psidev.psi.mi.fetcher.uniprot.uniprotutil.UniprotToJAMI;
import psidev.psi.mi.jami.model.Organism;
import psidev.psi.mi.jami.model.Protein;
import uk.ac.ebi.kraken.interfaces.uniprot.UniProtEntry;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 14/05/13
 * Time: 13:09
 */
public class UniprotFetcher
        implements ProteinFetcher { //, OrganismFetcher {

    private final Logger log = LoggerFactory.getLogger(UniprotFetcher.class.getName());

    UniprotBridge bridge;

    public UniprotFetcher() throws BridgeFailedException {
        log.debug("starting bridge");
        bridge = new UniprotBridge();
    }

    public Protein getProteinByID(String ID) throws BridgeFailedException {
        if(log.isDebugEnabled()) log.debug("Searching on id ["+ID+"]");
        UniProtEntry e = bridge.fetchEntryByID(ID);

        if(e == null){
            if(log.isDebugEnabled()) log.debug("Entry is null");
            return null;
        }

        Protein p = UniprotToJAMI.getProteinFromEntry(e);
        if(log.isDebugEnabled()) if(p == null) log.debug("Protein came back null");
        return p;
    }

    /*
    public Organism getOrganismByID(String ID) throws BridgeFailedException {
        UniProtEntry e = bridge.fetchEntryByID(ID);
        Organism o = UniprotToJAMI.getOrganismFromEntry(e);
        return o;
    }  */
}
