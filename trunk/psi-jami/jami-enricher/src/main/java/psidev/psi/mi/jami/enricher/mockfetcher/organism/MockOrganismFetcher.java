package psidev.psi.mi.jami.enricher.mockfetcher.organism;

import psidev.psi.mi.jami.bridges.exception.EntryNotFoundException;
import psidev.psi.mi.jami.bridges.exception.FetcherException;
import psidev.psi.mi.jami.bridges.fetcher.OrganismFetcher;
import psidev.psi.mi.jami.model.Organism;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 24/05/13
 * Time: 11:15
 */
public class MockOrganismFetcher
        implements OrganismFetcher {

    private Map<String,Organism> localOrganisms;

    public MockOrganismFetcher(){
        localOrganisms = new HashMap<String,Organism>();
    }

    public Organism getOrganismByTaxID(int identifier)
            throws FetcherException {

        if(! localOrganisms.containsKey(""+identifier)) {
            throw new EntryNotFoundException(
                    "No Organism to repeat with ID ["+identifier+"]");
        }

        else return localOrganisms.get(""+identifier);
    }


    public void addNewOrganism(String taxID, Organism organism){
        if(organism == null) return;
        this.localOrganisms.put(taxID, organism);
    }

    public void clearOrganisms(){
        localOrganisms.clear();
    }
}
