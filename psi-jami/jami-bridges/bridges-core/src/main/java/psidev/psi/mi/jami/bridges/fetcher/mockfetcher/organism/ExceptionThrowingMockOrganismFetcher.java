package psidev.psi.mi.jami.bridges.fetcher.mockfetcher.organism;

import psidev.psi.mi.jami.bridges.exception.BridgeFailedException;
import psidev.psi.mi.jami.bridges.fetcher.CvTermFetcher;
import psidev.psi.mi.jami.bridges.fetcher.OrganismFetcher;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Organism;

import java.util.*;

/**
 * A mock fetcher for testing exceptions.
 * It extends the functionality of the mock fetcher but can also throw exceptions.
 * Upon initialisation, an integer is given which sets how many times a query is made before returning the result.
 * If the current query matches the last query and the counter of the number of times is less than the maxQuery
 * set at initialisation, then an exception will be thrown.
 * Additionally, if the maxQuery is set to -1, the fetcher will always throw an exception.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 01/07/13
 */
public class ExceptionThrowingMockOrganismFetcher
        extends MockOrganismFetcher
        implements OrganismFetcher {

    String lastQuery = null;
    int count = 0;
    int maxQuery = 0;

    /**
     * A constructor which sets the number of times before returning an answer to the query.
     * Will throw exceptions until the query has been made that number of times.
     * @param maxQuery  The number of times the fetcher must be queried before returning the entry.
     *                  If -1, will always throw exceptions.
     */
    public ExceptionThrowingMockOrganismFetcher(int maxQuery){
        super();
        this.maxQuery = maxQuery;
    }

    @Override
    public Organism getOrganismByTaxID(int identifier) throws BridgeFailedException {
        if(! localOrganisms.containsKey( Integer.toString(identifier) ))  return null;
        else {
            if(!  Integer.toString(identifier).equals( lastQuery )){
                lastQuery = Integer.toString(identifier) ;
                count = 0;

            }

            if(maxQuery != -1 && count >= maxQuery)
                return localOrganisms.get( Integer.toString(identifier) );
            else {
                count++;
                throw new BridgeFailedException("Mock fetcher throws because this is the "+(count-1)+" attempt of "+maxQuery);
            }
        }
    }
}
