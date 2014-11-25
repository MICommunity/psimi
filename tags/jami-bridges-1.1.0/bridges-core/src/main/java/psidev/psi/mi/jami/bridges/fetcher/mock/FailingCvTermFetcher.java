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

    public CvTerm fetchByIdentifier(String termIdentifier, String ontologyDatabaseName) throws BridgeFailedException {
        return getEntry(termIdentifier);
    }

    public CvTerm fetchByIdentifier(String termIdentifier, CvTerm ontologyDatabase) throws BridgeFailedException {
        return getEntry(termIdentifier);
    }

    public CvTerm fetchByName(String searchName, String ontologyDatabaseName) throws BridgeFailedException {
        return getEntry(searchName);
    }

    public Collection<CvTerm> fetchByName(String searchName) throws BridgeFailedException {
        return Arrays.asList(getEntry(searchName));
    }

    public Collection<CvTerm> fetchByIdentifiers(Collection<String> termIdentifiers, String ontologyDatabaseName) throws BridgeFailedException {
        return getEntries(termIdentifiers);
    }

    public Collection<CvTerm> fetchByIdentifiers(Collection<String> termIdentifiers, CvTerm ontologyDatabase) throws BridgeFailedException {
        return getEntries(termIdentifiers);
    }

    public Collection<CvTerm> fetchByNames(Collection<String> searchNames, String ontologyDatabaseName) throws BridgeFailedException {
        return getEntries(searchNames);
    }

    public Collection<CvTerm> fetchByNames(Collection<String> searchNames) throws BridgeFailedException {
        return getEntries(searchNames);
    }
}
