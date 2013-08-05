package psidev.psi.mi.jami.bridges.fetcher.mockfetcher.protein;

import psidev.psi.mi.jami.bridges.exception.BridgeFailedException;
import psidev.psi.mi.jami.bridges.fetcher.ProteinFetcher;
import psidev.psi.mi.jami.model.Protein;

import java.util.*;

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

    protected Map<String, ArrayList<Protein>> localProteins;

    public MockProteinFetcher(){
        localProteins = new HashMap<String, ArrayList<Protein>>();
    }

    /**
     * Will return a collection of proteins if one or more protein has been added with the identifier.
     * If no proteins can be found, it will throw a fetcherException (as a true fetcher would).
     * @param identifier
     * @return
     */
    public Collection<Protein> getProteinsByIdentifier(String identifier) throws BridgeFailedException {
        if(identifier == null) throw new IllegalArgumentException(
                "Attempted to query mock protein fetcher for null identifier.");

        if(! localProteins.containsKey(identifier)) {
            return Collections.EMPTY_LIST;
        }

        else {
            return localProteins.get(identifier);
        }
    }

    public Collection<Protein> getProteinsByIdentifiers(Collection<String> identifiers)  {
        return Collections.EMPTY_LIST;
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
}
