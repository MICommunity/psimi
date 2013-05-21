package psidev.psi.mi.jami.bridges.uniprot.uniprot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import psidev.psi.mi.jami.bridges.exception.BridgeFailedException;
import psidev.psi.mi.jami.bridges.exception.EntryNotFoundException;
import psidev.psi.mi.jami.bridges.exception.FetcherException;
import psidev.psi.mi.jami.bridges.exception.NullSearchException;
import psidev.psi.mi.jami.bridges.fetcher.ProteinFetcher;
import psidev.psi.mi.jami.bridges.uniprot.uniprot.uniprotutil.UniprotToJAMI;
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
        implements ProteinFetcher {

    private final Logger log = LoggerFactory.getLogger(UniprotFetcher.class.getName());

    UniprotBridge bridge;

    public UniprotFetcher() throws FetcherException {
        log.trace("Starting uniprot bridge");
        bridge = new UniprotBridge();
    }

    public Protein getProteinByID(String identifier)
            throws FetcherException {

        if(identifier == null){
            throw new NullSearchException("The provided searchName was null.");
        }
        UniProtEntry e = bridge.fetchEntryByID(identifier);
        if(e == null){
            throw new EntryNotFoundException("Identifier "+identifier+" returned no uniprotKB entry.");
        }

        Protein p = UniprotToJAMI.getProteinFromEntry(e);

        return p;
    }

    /*
    public Organism getOrganismByID(String ID) throws BridgeFailedException {
        UniProtEntry e = bridge.fetchEntryByID(ID);
        Organism o = UniprotToJAMI.getOrganismFromEntry(e);
        return o;
    }  */
}
