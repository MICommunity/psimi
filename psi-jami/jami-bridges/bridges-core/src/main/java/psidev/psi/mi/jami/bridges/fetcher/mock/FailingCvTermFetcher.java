package psidev.psi.mi.jami.bridges.fetcher.mock;

import psidev.psi.mi.jami.bridges.exception.BridgeFailedException;
import psidev.psi.mi.jami.bridges.fetcher.CvTermFetcher;
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
public class FailingCvTermFetcher
        extends AbstractFailingFetcher<CvTerm>
        implements CvTermFetcher<CvTerm>{

    public FailingCvTermFetcher(int maxQuery) {
        super(maxQuery);
    }

    public CvTerm fetchCvTermByIdentifier(String termIdentifier, String ontologyDatabaseName) throws BridgeFailedException {
        return getEntry(termIdentifier);
    }

    public CvTerm fetchCvTermByIdentifier(String termIdentifier, CvTerm ontologyDatabase) throws BridgeFailedException {
        return getEntry(termIdentifier);
    }

    public CvTerm fetchCvTermByName(String searchName, String ontologyDatabaseName) throws BridgeFailedException {
        return getEntry(searchName);
    }

    public CvTerm fetchCvTermByName(String searchName) throws BridgeFailedException {
        return getEntry(searchName);
    }

    public Collection<CvTerm> fetchCvTermsByIdentifiers(Collection<String> termIdentifiers, String ontologyDatabaseName) throws BridgeFailedException {
        ArrayList<CvTerm> resultsList= new ArrayList<CvTerm>();
        for(String identifier : termIdentifiers){
            resultsList.add( getEntry(identifier) );
        }
        return resultsList;
    }

    public Collection<CvTerm> fetchCvTermsByIdentifiers(Collection<String> termIdentifiers, CvTerm ontologyDatabase) throws BridgeFailedException {
        ArrayList<CvTerm> resultsList= new ArrayList<CvTerm>();
        for(String identifier : termIdentifiers){
            resultsList.add( getEntry(identifier) );
        }
        return resultsList;
    }

    public Collection<CvTerm> fetchCvTermsByNames(Collection<String> searchNames, String ontologyDatabaseName) throws BridgeFailedException {
        ArrayList<CvTerm> resultsList= new ArrayList<CvTerm>();
        for(String identifier : searchNames){
            resultsList.add( getEntry(identifier) );
        }
        return resultsList;
    }

    public Collection<CvTerm> fetchCvTermsByNames(Collection<String> searchNames) throws BridgeFailedException {
        ArrayList<CvTerm> resultsList= new ArrayList<CvTerm>();
        for(String identifier : searchNames){
            resultsList.add( getEntry(identifier) );
        }
        return resultsList;
    }
}
