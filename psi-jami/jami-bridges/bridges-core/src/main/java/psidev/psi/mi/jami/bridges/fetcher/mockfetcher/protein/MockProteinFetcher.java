package psidev.psi.mi.jami.bridges.fetcher.mockfetcher.protein;

import psidev.psi.mi.jami.bridges.exception.BridgeFailedException;
import psidev.psi.mi.jami.bridges.fetcher.ProteinFetcher;
import psidev.psi.mi.jami.bridges.fetcher.mockfetcher.AbstractMockFetcher;
import psidev.psi.mi.jami.model.Protein;

import java.util.*;

/**
 * A mock fetcher for testing.
 * It extends all the methods of the true fetcher, but does not access an external service.
 * Instead, it holds a map which can be loaded with objects and keys. which are then returned.
 * It attempts to replicate the responses of the fetcher in most scenarios.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>23/05/13</pre>
 */

public class MockProteinFetcher
        extends AbstractMockFetcher<ArrayList<Protein>>
        implements ProteinFetcher {

    protected ArrayList<Protein> getEntry(String identifier) throws BridgeFailedException {
        if(identifier == null) throw new IllegalArgumentException(
                "Attempted to query mock protein fetcher for null identifier.");

        if(! localMap.containsKey(identifier)) {
            return new ArrayList<Protein>();
        }else {
            return super.getEntry(identifier);
        }
    }

    protected void addEntry(String identifier, Protein protein){
        if(protein == null) return;
        if(! localMap.containsKey(identifier)){
            ArrayList<Protein> array = new ArrayList<Protein>();
            localMap.put(identifier, array);
        }
        localMap.get(identifier).add(protein);
    }


    public Collection<Protein> getProteinsByIdentifier(String identifier) throws BridgeFailedException {
        return getEntry(identifier);
    }

    public Collection<Protein> getProteinsByIdentifiers(Collection<String> identifiers) throws BridgeFailedException {
        ArrayList<Protein> resultsList= new ArrayList<Protein>();
        for(String identifier : identifiers){
            resultsList.addAll( getEntry(identifier) );
        }
        return resultsList;
    }

}
