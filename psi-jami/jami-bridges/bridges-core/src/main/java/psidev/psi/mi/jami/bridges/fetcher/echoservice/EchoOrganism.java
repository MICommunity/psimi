package psidev.psi.mi.jami.bridges.fetcher.echoservice;

import psidev.psi.mi.jami.bridges.exception.BridgeFailedException;
import psidev.psi.mi.jami.bridges.exception.EntryNotFoundException;
import psidev.psi.mi.jami.bridges.exception.FetcherException;
import psidev.psi.mi.jami.bridges.fetcher.OrganismFetcher;
import psidev.psi.mi.jami.model.Organism;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 22/05/13
 * Time: 10:22
 */
public class EchoOrganism
        implements OrganismFetcher {

    Organism echo = null;

    public EchoOrganism(){

    }

    public EchoOrganism(Organism echo){
        this.echo = echo;
    }

    public Organism getOrganismByTaxID(int taxID)
            throws FetcherException {

        if(echo == null) throw new BridgeFailedException("No Organism to Echo");
        else if(echo.getTaxId() != taxID) throw new EntryNotFoundException("Echo does not contain the requested Organism.");
        else return echo;
    }
}
