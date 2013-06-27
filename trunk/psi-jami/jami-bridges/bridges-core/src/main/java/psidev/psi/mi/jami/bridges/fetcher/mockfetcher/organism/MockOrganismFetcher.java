package psidev.psi.mi.jami.bridges.fetcher.mockfetcher.organism;


import psidev.psi.mi.jami.bridges.exception.BridgeFailedException;
import psidev.psi.mi.jami.bridges.fetcher.OrganismFetcher;
import psidev.psi.mi.jami.model.Organism;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * An organism fetcher which has no external service.
 *
 * Instead, organisms can be loaded into it with a key for retrieval.
 * The normal fetch methods then retrieve the loaded organism as if it had been an external service.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 24/05/13
 */
public class MockOrganismFetcher
        implements OrganismFetcher {

    private Map<String,Organism> localOrganisms;

    public MockOrganismFetcher(){
        localOrganisms = new HashMap<String,Organism>();
    }

    public Organism getOrganismByTaxID(int identifier){

        if(! localOrganisms.containsKey(""+identifier)) {
            return null;
        }

        else return localOrganisms.get(""+identifier);
    }

    public Collection<Organism> getOrganismsByTaxIDs(Collection<Integer> taxIDs) throws BridgeFailedException {
        return Collections.EMPTY_LIST;
    }


    public void addNewOrganism(String taxID, Organism organism){
        if(organism == null) return;
        this.localOrganisms.put(taxID, organism);
    }

    public void clearOrganisms(){
        localOrganisms.clear();
    }
}
