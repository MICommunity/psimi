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

public class ExceptionThrowingMockProteinFetcher
        extends MockProteinFetcher
        implements ProteinFetcher {

    String lastQuery = null;
    int count = 0;
    int maxquery = 0;


    public ExceptionThrowingMockProteinFetcher(int maxquery){
        super();
        this.maxquery = maxquery;
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

        if(! localProteins.containsKey(identifier)) return Collections.EMPTY_LIST;
        else {
            if(! identifier.equals(lastQuery)){
                lastQuery = identifier;
                count = 0;
            }

            if(maxquery != -1 && count >= maxquery)
                return localProteins.get(identifier);
            else {
                count++;
                throw new BridgeFailedException("Mock fetcher throws because this is the "+(count-1)+" attempt of "+maxquery);
            }
        }
    }
}
