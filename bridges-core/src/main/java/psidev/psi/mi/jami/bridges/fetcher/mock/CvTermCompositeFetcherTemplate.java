package psidev.psi.mi.jami.bridges.fetcher.mock;

import psidev.psi.mi.jami.bridges.exception.BridgeFailedException;
import psidev.psi.mi.jami.bridges.fetcher.CvTermFetcher;
import psidev.psi.mi.jami.model.CvTerm;

import java.util.*;

/**
 * Template for a CvTermCompositeFetcher which delegates fetching to other fetcher
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>18/07/13</pre>
 */

public class CvTermCompositeFetcherTemplate<T extends CvTerm, F extends CvTermFetcher<T>> implements CvTermFetcher<T>{

    private Map<String, F> delegateFetchers;

    public CvTermCompositeFetcherTemplate(){
        this.delegateFetchers = new HashMap<String, F>();
    }

    public T fetchByIdentifier(String termIdentifier, String ontologyDatabaseName) throws BridgeFailedException {

        if (ontologyDatabaseName == null || !this.delegateFetchers.containsKey(ontologyDatabaseName)){
            T firstTermRetrieved = null;
            Iterator<F> fetcherIterator = delegateFetchers.values().iterator();
            while(firstTermRetrieved == null && fetcherIterator.hasNext()){
                 firstTermRetrieved = fetcherIterator.next().fetchByIdentifier(termIdentifier, ontologyDatabaseName);
            }

            return firstTermRetrieved;
        }

        F fetcher = this.delegateFetchers.get(ontologyDatabaseName);
        if (fetcher == null){
            return null;
        }
        else {
            return fetcher.fetchByIdentifier(termIdentifier, ontologyDatabaseName);
        }
    }

    public T fetchByIdentifier(String termIdentifier, CvTerm ontologyDatabase) throws BridgeFailedException {
        if (ontologyDatabase == null || !this.delegateFetchers.containsKey(ontologyDatabase.getShortName())){
            T firstTermRetrieved = null;
            Iterator<F> fetcherIterator = delegateFetchers.values().iterator();
            while(firstTermRetrieved == null && fetcherIterator.hasNext()){
                firstTermRetrieved = fetcherIterator.next().fetchByIdentifier(termIdentifier, ontologyDatabase);
            }

            return firstTermRetrieved;
        }

        F fetcher = this.delegateFetchers.get(ontologyDatabase.getShortName());
        if (fetcher == null){
            return null;
        }
        else {
            return fetcher.fetchByIdentifier(termIdentifier, ontologyDatabase);
        }
    }

    public T fetchByName(String searchName, String ontologyDatabaseName) throws BridgeFailedException {
        if (ontologyDatabaseName == null || !this.delegateFetchers.containsKey(ontologyDatabaseName)){
            T firstTermRetrieved = null;
            Iterator<F> fetcherIterator = delegateFetchers.values().iterator();
            while(firstTermRetrieved == null && fetcherIterator.hasNext()){
                firstTermRetrieved = fetcherIterator.next().fetchByName(searchName, ontologyDatabaseName);
            }

            return firstTermRetrieved;
        }

        F fetcher = this.delegateFetchers.get(ontologyDatabaseName);
        if (fetcher == null){
            return null;
        }
        else {
            return fetcher.fetchByName(searchName, ontologyDatabaseName);
        }
    }

    public Collection<T> fetchByName(String searchName) throws BridgeFailedException {
        Collection<T> firstTermRetrieved = null;
        Iterator<F> fetcherIterator = delegateFetchers.values().iterator();
        while(firstTermRetrieved == null && fetcherIterator.hasNext()){
            firstTermRetrieved = fetcherIterator.next().fetchByName(searchName);
        }

        return firstTermRetrieved;
    }

    public Collection<T> fetchByIdentifiers(Collection<String> termIdentifiers, String ontologyDatabaseName) throws BridgeFailedException {
        if (ontologyDatabaseName == null || !this.delegateFetchers.containsKey(ontologyDatabaseName)){
            Collection<T> firstTermRetrieved = Collections.EMPTY_LIST;
            Iterator<F> fetcherIterator = delegateFetchers.values().iterator();
            while(firstTermRetrieved.isEmpty() && fetcherIterator.hasNext()){
                firstTermRetrieved = fetcherIterator.next().fetchByIdentifiers(termIdentifiers, ontologyDatabaseName);
            }

            return firstTermRetrieved;
        }

        F fetcher = this.delegateFetchers.get(ontologyDatabaseName);
        if (fetcher == null){
            return null;
        }
        else {
            return fetcher.fetchByIdentifiers(termIdentifiers, ontologyDatabaseName);
        }
    }

    public Collection<T> fetchByIdentifiers(Collection<String> termIdentifiers, CvTerm ontologyDatabase) throws BridgeFailedException {
        if (ontologyDatabase == null || !this.delegateFetchers.containsKey(ontologyDatabase.getShortName())){
            Collection<T> firstTermRetrieved = Collections.EMPTY_LIST;
            Iterator<F> fetcherIterator = delegateFetchers.values().iterator();
            while(firstTermRetrieved.isEmpty() && fetcherIterator.hasNext()){
                firstTermRetrieved = fetcherIterator.next().fetchByIdentifiers(termIdentifiers, ontologyDatabase);
            }

            return firstTermRetrieved;
        }

        F fetcher = this.delegateFetchers.get(ontologyDatabase.getShortName());
        if (fetcher == null){
            return null;
        }
        else {
            return fetcher.fetchByIdentifiers(termIdentifiers, ontologyDatabase);
        }
    }

    public Collection<T> fetchByNames(Collection<String> searchNames, String ontologyDatabaseName) throws BridgeFailedException {
        if (ontologyDatabaseName == null || !this.delegateFetchers.containsKey(ontologyDatabaseName)){
            Collection<T> firstTermRetrieved = Collections.EMPTY_LIST;
            Iterator<F> fetcherIterator = delegateFetchers.values().iterator();
            while(firstTermRetrieved.isEmpty() && fetcherIterator.hasNext()){
                firstTermRetrieved = fetcherIterator.next().fetchByNames(searchNames, ontologyDatabaseName);
            }

            return firstTermRetrieved;
        }

        F fetcher = this.delegateFetchers.get(ontologyDatabaseName);
        if (fetcher == null){
            return null;
        }
        else {
            return fetcher.fetchByNames(searchNames, ontologyDatabaseName);
        }
    }

    public Collection<T> fetchByNames(Collection<String> searchNames) throws BridgeFailedException {
        Collection<T> firstTermRetrieved = Collections.EMPTY_LIST;
        Iterator<F> fetcherIterator = delegateFetchers.values().iterator();
        while(firstTermRetrieved.isEmpty() && fetcherIterator.hasNext()){
            firstTermRetrieved = fetcherIterator.next().fetchByNames(searchNames);
        }

        return firstTermRetrieved;
    }

    public void addCvTermFetcher(String ontologyDatabase, F fetcher){
        this.delegateFetchers.put(ontologyDatabase, fetcher);
    }

    public void removeCvTermFetcher(String ontologyDatabase){
        this.delegateFetchers.remove(ontologyDatabase);
    }

    protected Map<String, F> getDelegateFetchers() {
        return delegateFetchers;
    }
}
