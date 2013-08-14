package psidev.psi.mi.jami.bridges.fetcher.mock;

import psidev.psi.mi.jami.bridges.exception.BridgeFailedException;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 07/08/13
 */
public abstract class AbstractExceptionThrowingMockFetcher<T>
        extends AbstractMockFetcher<T>{


    private String lastQuery = null;
    private int count = 0;
    private int maxQuery = 0;

    protected AbstractExceptionThrowingMockFetcher(int maxQuery){
        super();
        this.maxQuery = maxQuery;
    }

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
