package psidev.psi.mi.jami.enricher.mockfetcher.protein;

import psidev.psi.mi.jami.bridges.exception.EntryNotFoundException;
import psidev.psi.mi.jami.bridges.exception.FetcherException;
import psidev.psi.mi.jami.bridges.fetcher.ProteinFetcher;
import psidev.psi.mi.jami.model.Protein;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * The mock protein fetcher mimics a normal protein fetcher
 * but can only fetch proteins which have been loaded into it.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>23/05/13</pre>
 */

public class MockProteinFetcher
        implements ProteinFetcher {

    private Map<String, ArrayList<Protein>> localProteins;

    public MockProteinFetcher(){
        localProteins = new HashMap<String, ArrayList<Protein>>();
    }

    /**
     * Will return a collection of proteins if one or more protein has been added with the identifier.
     * If no proteins can be found, it will throw a fetcherException (as a true fetcher would).
     * @param identifier
     * @return
     * @throws FetcherException
     */
    public Collection<Protein> getProteinsByID(String identifier) throws FetcherException {
        if(! localProteins.containsKey(identifier)) {
            throw new EntryNotFoundException(
                    "No protein to repeat with ID ["+identifier+"]");
        }

        else {
            return localProteins.get(identifier);
        }
    }

    /**
     * Adds the protein to the available return values.
     * If a protein of the given identifier already exists,
     * it will be appended and both proteins will be returned in a search for that identifier.
     * @param identifier
     * @param protein
     */
    public void addNewProtein(String identifier, Protein protein){
        if(protein == null) return;
        if(! localProteins.containsKey(identifier)){
            ArrayList<Protein> array = new ArrayList<Protein>();
            localProteins.put(identifier, array);
        }
        localProteins.get(identifier).add(protein);
    }

    public void clearProteins(){
        localProteins.clear();
    }

    public String getService() {
        return "Mock Protein Fetcher";
    }
}
