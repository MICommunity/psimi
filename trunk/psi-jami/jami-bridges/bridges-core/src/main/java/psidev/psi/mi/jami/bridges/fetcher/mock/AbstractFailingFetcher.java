package psidev.psi.mi.jami.bridges.fetcher.mock;

import psidev.psi.mi.jami.bridges.exception.BridgeFailedException;

/**
 * A mock fetcher for testing behaviour of bridgeFailedExceptions.
 * It can be set to throw exceptions for a given number of queries or to continuously throw the exception.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 07/08/13
 */
public abstract class AbstractFailingFetcher<T>
        extends AbstractMockFetcher<T>{

    private String lastQuery = null;
    private int count = 0;
    private int maxQuery = 0;

    /**
     * Sets up the mock fetcher with a number of times to throw exceptions before returning the solution to the query.
     * If the maxQuery is positive, a query must be made that many times before a solution is given.
     * If a new query is made, the count resets.
     * If the maxQuery is -1, it will always throw an exception.
     * @param maxQuery  The number of time to throw an exception, or -1 if exception should always be thrown.
     */
    protected AbstractFailingFetcher(int maxQuery){
        super();
        this.maxQuery = maxQuery;
    }

    /**
     * Used to retrieve an entry from the internal list.
     * will throw exceptions if the required number of queries has not been made.
     *
     * @param identifier    The identifier to search for.
     * @return              The entry matching the identifier if the required number of exceptions have been thrown.
     * @throws BridgeFailedException        Thrown if the required number of queries has not been made.
     */
    protected T getEntry(String identifier) throws BridgeFailedException {
        if(identifier == null) throw new IllegalArgumentException(
                "Attempted to query mock protein fetcher for null identifier.");

        if(! localMap.containsKey(identifier))  return null;
        else {
            if(! identifier.equals(lastQuery)){
                lastQuery = identifier;
                count = 0;
            }

            if(maxQuery != -1 && count >= maxQuery)
                return localMap.get(identifier);
            else {
                count++;
                throw new BridgeFailedException(
                        "Mock fetcher throws because this is the "+(count-1)+" attempt of "+maxQuery);
            }
        }
    }

}
