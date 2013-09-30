package psidev.psi.mi.jami.bridges.fetcher.mock;

import psidev.psi.mi.jami.bridges.exception.BridgeFailedException;
import psidev.psi.mi.jami.bridges.fetcher.OntologyTermFetcher;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.OntologyTerm;

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
public class MockOntologyTermFetcher
        implements OntologyTermFetcher{

    private Map<String,OntologyTerm> localOntologyTerms;

    public MockOntologyTermFetcher(){
        localOntologyTerms = new HashMap<String, OntologyTerm>();
    }

    public void addOntologyTerm(String identifier , OntologyTerm ontologyTerm){
        if(ontologyTerm == null || identifier == null) return;
        this.localOntologyTerms.put(identifier , ontologyTerm);
    }

    public void clear(){
        localOntologyTerms.clear();
    }

    private Collection<OntologyTerm> getMockTermCollection(Collection<String> termIdentifiers){
        Collection<OntologyTerm> value = new ArrayList<OntologyTerm>();
        for(String term : termIdentifiers){
            value.add(localOntologyTerms.get(term));
        }
        return value;
    }

    public OntologyTerm fetchByIdentifier(String termIdentifier, String ontologyDatabaseName)
            throws BridgeFailedException {
        return localOntologyTerms.get(termIdentifier);
    }

    public OntologyTerm fetchByIdentifier(String termIdentifier, CvTerm ontologyDatabase)
            throws BridgeFailedException {
        return localOntologyTerms.get(termIdentifier);
    }

    public OntologyTerm fetchByName(String searchName, String ontologyDatabaseName)
            throws BridgeFailedException {
        return localOntologyTerms.get(searchName);
    }

    public Collection<OntologyTerm> fetchByName(String searchName) throws BridgeFailedException {
        return Arrays.asList(localOntologyTerms.get(searchName));
    }

    public Collection<OntologyTerm> fetchByIdentifiers(Collection<String> identifiers, String ontologyDatabaseName)
            throws BridgeFailedException {
        return getMockTermCollection(identifiers);
    }

    public Collection<OntologyTerm> fetchByIdentifiers(Collection<String> identifiers, CvTerm ontologyDatabase)
            throws BridgeFailedException {
        return getMockTermCollection(identifiers);
    }

    public Collection<OntologyTerm> fetchByNames(Collection<String> searchNames, String ontologyDatabaseName)
            throws BridgeFailedException {
        return getMockTermCollection(searchNames);
    }

    public Collection<OntologyTerm> fetchByNames(Collection<String> searchNames)
            throws BridgeFailedException {
        return getMockTermCollection(searchNames);
    }
}
