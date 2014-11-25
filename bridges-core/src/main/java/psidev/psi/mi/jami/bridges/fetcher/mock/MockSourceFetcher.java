package psidev.psi.mi.jami.bridges.fetcher.mock;

import psidev.psi.mi.jami.bridges.exception.BridgeFailedException;
import psidev.psi.mi.jami.bridges.fetcher.SourceFetcher;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Source;

import java.util.*;

/**
 * A mock fetcher for testing.
 * It extends all the methods of the true fetcher, but does not access an external service.
 * Instead, it holds a map which can be loaded with objects and keys. which are then returned.
 * It attempts to replicate the responses of the fetcher in most scenarios.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 08/07/13
 */
public class MockSourceFetcher
        implements SourceFetcher{

    private Map<String,Source> localSource;

    public MockSourceFetcher(){
        localSource = new HashMap<String, Source>();
    }

    public void addOntologyTerm(String identifier , Source source){
        if(source == null || identifier == null) return;
        this.localSource.put(identifier, source);
    }

    public void clear(){
        localSource.clear();
    }

    private Collection<Source> getMockTermCollection(Collection<String> termIdentifiers){
        Collection<Source> value = new ArrayList<Source>();
        for(String term : termIdentifiers){
            value.add(localSource.get(term));
        }
        return value;
    }

    public Source fetchByIdentifier(String termIdentifier, String ontologyDatabaseName)
            throws BridgeFailedException {
        return localSource.get(termIdentifier);
    }

    public Source fetchByIdentifier(String termIdentifier, CvTerm ontologyDatabase)
            throws BridgeFailedException {
        return localSource.get(termIdentifier);
    }

    public Source fetchByName(String searchName, String ontologyDatabaseName)
            throws BridgeFailedException {
        return localSource.get(searchName);
    }

    public Collection<Source> fetchByName(String searchName) throws BridgeFailedException {
        return Arrays.asList(localSource.get(searchName));
    }

    public Collection<Source> fetchByIdentifiers(Collection<String> identifiers, String ontologyDatabaseName)
            throws BridgeFailedException {
        return getMockTermCollection(identifiers);
    }

    public Collection<Source> fetchByIdentifiers(Collection<String> identifiers, CvTerm ontologyDatabase)
            throws BridgeFailedException {
        return getMockTermCollection(identifiers);
    }

    public Collection<Source> fetchByNames(Collection<String> searchNames, String ontologyDatabaseName)
            throws BridgeFailedException {
        return getMockTermCollection(searchNames);
    }

    public Collection<Source> fetchByNames(Collection<String> searchNames)
            throws BridgeFailedException {
        return getMockTermCollection(searchNames);
    }
}
