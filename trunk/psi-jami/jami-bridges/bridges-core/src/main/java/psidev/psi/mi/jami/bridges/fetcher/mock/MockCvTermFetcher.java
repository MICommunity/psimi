package psidev.psi.mi.jami.bridges.fetcher.mock;

import psidev.psi.mi.jami.bridges.exception.BridgeFailedException;
import psidev.psi.mi.jami.bridges.fetcher.CvTermFetcher;
import psidev.psi.mi.jami.model.CvTerm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

/**
 * A mock fetcher for testing.
 * It extends all the methods of the true fetcher, but does not access an external service.
 * Instead, it holds a map which can be loaded with objects and keys. which are then returned.
 * It attempts to replicate the responses of the fetcher in most scenarios.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 01/07/13
 */
public class MockCvTermFetcher
        extends AbstractMockFetcher<CvTerm>
        implements CvTermFetcher<CvTerm>{

    public CvTerm fetchCvTermByIdentifier(String termIdentifier, String ontologyDatabaseName) throws BridgeFailedException {
        return getEntry(termIdentifier);
    }

    public CvTerm fetchCvTermByIdentifier(String termIdentifier, CvTerm ontologyDatabase) throws BridgeFailedException {
        return getEntry(termIdentifier);
    }

    public CvTerm fetchCvTermByName(String searchName, String ontologyDatabaseName) throws BridgeFailedException {
        return getEntry(searchName);
    }

    public Collection<CvTerm> fetchCvTermByName(String searchName) throws BridgeFailedException {
        return Arrays.asList(getEntry(searchName));
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
