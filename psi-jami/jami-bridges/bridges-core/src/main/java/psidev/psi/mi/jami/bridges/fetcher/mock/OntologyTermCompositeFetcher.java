package psidev.psi.mi.jami.bridges.fetcher.mock;

import psidev.psi.mi.jami.bridges.exception.BridgeFailedException;
import psidev.psi.mi.jami.bridges.fetcher.OntologyTermFetcher;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.OntologyTerm;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

/**
 * Ontology fetcher that delegates to different ontologyTermFetchers
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>18/07/13</pre>
 */

public class OntologyTermCompositeFetcher extends CvTermCompositeFetcherTemplate<OntologyTerm, OntologyTermFetcher> implements OntologyTermFetcher{


    public OntologyTerm getCvTermByIdentifier(String termIdentifier, String ontologyDatabaseName, int childrenDepth, int parentDepth) throws BridgeFailedException {
        if (ontologyDatabaseName == null || !getDelegateFetchers().containsKey(ontologyDatabaseName)){
            OntologyTerm firstTermRetrieved = null;
            Iterator<OntologyTermFetcher> fetcherIterator = getDelegateFetchers().values().iterator();
            while(firstTermRetrieved == null && fetcherIterator.hasNext()){
                firstTermRetrieved = fetcherIterator.next().getCvTermByIdentifier(termIdentifier, ontologyDatabaseName, childrenDepth, parentDepth);
            }

            return firstTermRetrieved;
        }

        OntologyTermFetcher fetcher = getDelegateFetchers().get(ontologyDatabaseName);
        if (fetcher == null){
            return null;
        }
        else {
            return fetcher.getCvTermByIdentifier(termIdentifier, ontologyDatabaseName, childrenDepth, parentDepth);
        }
    }

    public OntologyTerm getCvTermByIdentifier(String termIdentifier, CvTerm ontologyDatabase, int childrenDepth, int parentDepth) throws BridgeFailedException {
        if (ontologyDatabase == null || !getDelegateFetchers().containsKey(ontologyDatabase.getShortName())){
            OntologyTerm firstTermRetrieved = null;
            Iterator<OntologyTermFetcher> fetcherIterator = getDelegateFetchers().values().iterator();
            while(firstTermRetrieved == null && fetcherIterator.hasNext()){
                firstTermRetrieved = fetcherIterator.next().getCvTermByIdentifier(termIdentifier, ontologyDatabase, childrenDepth, parentDepth);
            }

            return firstTermRetrieved;
        }

        OntologyTermFetcher fetcher = getDelegateFetchers().get(ontologyDatabase.getShortName());
        if (fetcher == null){
            return null;
        }
        else {
            return fetcher.getCvTermByIdentifier(termIdentifier, ontologyDatabase, childrenDepth, parentDepth);
        }
    }

    public OntologyTerm getCvTermByExactName(String searchName, String ontologyDatabaseName, int childrenDepth, int parentDepth) throws BridgeFailedException {
        if (ontologyDatabaseName == null || !getDelegateFetchers().containsKey(ontologyDatabaseName)){
            OntologyTerm firstTermRetrieved = null;
            Iterator<OntologyTermFetcher> fetcherIterator = getDelegateFetchers().values().iterator();
            while(firstTermRetrieved == null && fetcherIterator.hasNext()){
                firstTermRetrieved = fetcherIterator.next().getCvTermByExactName(searchName, ontologyDatabaseName, childrenDepth, parentDepth);
            }

            return firstTermRetrieved;
        }

        OntologyTermFetcher fetcher = getDelegateFetchers().get(ontologyDatabaseName);
        if (fetcher == null){
            return null;
        }
        else {
            return fetcher.getCvTermByExactName(searchName, ontologyDatabaseName, childrenDepth, parentDepth);
        }
    }

    public OntologyTerm getCvTermByExactName(String searchName, int childrenDepth, int parentDepth) throws BridgeFailedException {
        OntologyTerm firstTermRetrieved = null;
        Iterator<OntologyTermFetcher> fetcherIterator = getDelegateFetchers().values().iterator();
        while(firstTermRetrieved == null && fetcherIterator.hasNext()){
            firstTermRetrieved = fetcherIterator.next().getCvTermByExactName(searchName, childrenDepth, parentDepth);
        }

        return firstTermRetrieved;
    }

    public Collection<OntologyTerm> getCvTermsByIdentifiers(Collection<String> termIdentifiers, String ontologyDatabaseName, int childrenDepth, int parentDepth) throws BridgeFailedException {
        if (ontologyDatabaseName == null || !getDelegateFetchers().containsKey(ontologyDatabaseName)){
            Collection<OntologyTerm> firstTermRetrieved = Collections.EMPTY_LIST;
            Iterator<OntologyTermFetcher> fetcherIterator = getDelegateFetchers().values().iterator();
            while(firstTermRetrieved.isEmpty() && fetcherIterator.hasNext()){
                firstTermRetrieved = fetcherIterator.next().getCvTermsByIdentifiers(termIdentifiers, ontologyDatabaseName, childrenDepth, parentDepth);
            }

            return firstTermRetrieved;
        }

        OntologyTermFetcher fetcher = getDelegateFetchers().get(ontologyDatabaseName);
        if (fetcher == null){
            return null;
        }
        else {
            return fetcher.getCvTermsByIdentifiers(termIdentifiers, ontologyDatabaseName, childrenDepth, parentDepth);
        }
    }

    public Collection<OntologyTerm> getCvTermsByIdentifiers(Collection<String> termIdentifiers, CvTerm ontologyDatabase, int childrenDepth, int parentDepth) throws BridgeFailedException {
        if (ontologyDatabase == null || !getDelegateFetchers().containsKey(ontologyDatabase.getShortName())){
            Collection<OntologyTerm> firstTermRetrieved = Collections.EMPTY_LIST;
            Iterator<OntologyTermFetcher> fetcherIterator = getDelegateFetchers().values().iterator();
            while(firstTermRetrieved.isEmpty() && fetcherIterator.hasNext()){
                firstTermRetrieved = fetcherIterator.next().getCvTermsByIdentifiers(termIdentifiers, ontologyDatabase, childrenDepth, parentDepth);
            }

            return firstTermRetrieved;
        }

        OntologyTermFetcher fetcher = getDelegateFetchers().get(ontologyDatabase.getShortName());
        if (fetcher == null){
            return null;
        }
        else {
            return fetcher.getCvTermsByIdentifiers(termIdentifiers, ontologyDatabase, childrenDepth, parentDepth);
        }
    }

    public Collection<OntologyTerm> getCvTermsByExactNames(Collection<String> searchNames, String ontologyDatabaseName, int childrenDepth, int parentDepth) throws BridgeFailedException {
        if (ontologyDatabaseName == null || !getDelegateFetchers().containsKey(ontologyDatabaseName)){
            Collection<OntologyTerm> firstTermRetrieved = Collections.EMPTY_LIST;
            Iterator<OntologyTermFetcher> fetcherIterator = getDelegateFetchers().values().iterator();
            while(firstTermRetrieved.isEmpty() && fetcherIterator.hasNext()){
                firstTermRetrieved = fetcherIterator.next().getCvTermsByExactNames(searchNames, ontologyDatabaseName, childrenDepth, parentDepth);
            }

            return firstTermRetrieved;
        }

        OntologyTermFetcher fetcher = getDelegateFetchers().get(ontologyDatabaseName);
        if (fetcher == null){
            return null;
        }
        else {
            return fetcher.getCvTermsByExactNames(searchNames, ontologyDatabaseName, childrenDepth, parentDepth);
        }
    }

    public Collection<OntologyTerm> getCvTermsByExactNames(Collection<String> searchNames, int childrenDepth, int parentDepth) throws BridgeFailedException {
        Collection<OntologyTerm> firstTermRetrieved = Collections.EMPTY_LIST;
        Iterator<OntologyTermFetcher> fetcherIterator = getDelegateFetchers().values().iterator();
        while(firstTermRetrieved.isEmpty() && fetcherIterator.hasNext()){
            firstTermRetrieved = fetcherIterator.next().getCvTermsByExactNames(searchNames, childrenDepth, parentDepth);
        }

        return firstTermRetrieved;
    }
}
