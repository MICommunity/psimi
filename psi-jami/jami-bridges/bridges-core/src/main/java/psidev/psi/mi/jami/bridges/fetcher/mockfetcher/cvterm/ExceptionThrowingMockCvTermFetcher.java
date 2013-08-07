package psidev.psi.mi.jami.bridges.fetcher.mockfetcher.cvterm;

import psidev.psi.mi.jami.bridges.exception.BridgeFailedException;
import psidev.psi.mi.jami.bridges.fetcher.CvTermFetcher;
import psidev.psi.mi.jami.bridges.fetcher.mockfetcher.AbstractExceptionThrowingMockFetcher;
import psidev.psi.mi.jami.bridges.fetcher.mockfetcher.AbstractMockFetcher;
import psidev.psi.mi.jami.model.CvTerm;

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
public class ExceptionThrowingMockCvTermFetcher
        extends AbstractExceptionThrowingMockFetcher<CvTerm>
        implements CvTermFetcher<CvTerm>{

    public ExceptionThrowingMockCvTermFetcher(int maxQuery) {
        super(maxQuery);
    }

    public CvTerm getCvTermByIdentifier(String termIdentifier, String ontologyDatabaseName) throws BridgeFailedException {
        return getEntry(termIdentifier);
    }

    public CvTerm getCvTermByIdentifier(String termIdentifier, CvTerm ontologyDatabase) throws BridgeFailedException {
        return getEntry(termIdentifier);
    }

    public CvTerm getCvTermByExactName(String searchName, String ontologyDatabaseName) throws BridgeFailedException {
        return getEntry(searchName);
    }

    public CvTerm getCvTermByExactName(String searchName) throws BridgeFailedException {
        return getEntry(searchName);
    }

    public Collection<CvTerm> getCvTermByInexactName(String searchName, String databaseName) throws BridgeFailedException {
        ArrayList<CvTerm> resultsList= new ArrayList<CvTerm>();
        resultsList.add( getEntry(searchName) );
        return resultsList;
    }

    public Collection<CvTerm> getCvTermByInexactName(String searchName, CvTerm database) throws BridgeFailedException {
        ArrayList<CvTerm> resultsList= new ArrayList<CvTerm>();
        resultsList.add( getEntry(searchName) );
        return resultsList;
    }

    public Collection<CvTerm> getCvTermsByIdentifiers(Collection<String> termIdentifiers, String ontologyDatabaseName) throws BridgeFailedException {
        ArrayList<CvTerm> resultsList= new ArrayList<CvTerm>();
        for(String identifier : termIdentifiers){
            resultsList.add( getEntry(identifier) );
        }
        return resultsList;
    }

    public Collection<CvTerm> getCvTermsByIdentifiers(Collection<String> termIdentifiers, CvTerm ontologyDatabase) throws BridgeFailedException {
        ArrayList<CvTerm> resultsList= new ArrayList<CvTerm>();
        for(String identifier : termIdentifiers){
            resultsList.add( getEntry(identifier) );
        }
        return resultsList;
    }

    public Collection<CvTerm> getCvTermsByExactNames(Collection<String> searchNames, String ontologyDatabaseName) throws BridgeFailedException {
        ArrayList<CvTerm> resultsList= new ArrayList<CvTerm>();
        for(String identifier : searchNames){
            resultsList.add( getEntry(identifier) );
        }
        return resultsList;
    }

    public Collection<CvTerm> getCvTermsByExactNames(Collection<String> searchNames) throws BridgeFailedException {
        ArrayList<CvTerm> resultsList= new ArrayList<CvTerm>();
        for(String identifier : searchNames){
            resultsList.add( getEntry(identifier) );
        }
        return resultsList;
    }
}
