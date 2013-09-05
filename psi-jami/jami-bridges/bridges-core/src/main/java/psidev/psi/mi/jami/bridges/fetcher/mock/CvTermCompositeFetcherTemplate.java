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

    public T fetchCvTermByIdentifier(String termIdentifier, String ontologyDatabaseName) throws BridgeFailedException {

        if (ontologyDatabaseName == null || !this.delegateFetchers.containsKey(ontologyDatabaseName)){
            T firstTermRetrieved = null;
            Iterator<F> fetcherIterator = delegateFetchers.values().iterator();
            while(firstTermRetrieved == null && fetcherIterator.hasNext()){
                 firstTermRetrieved = fetcherIterator.next().fetchCvTermByIdentifier(termIdentifier, ontologyDatabaseName);
            }

            return firstTermRetrieved;
        }

        F fetcher = this.delegateFetchers.get(ontologyDatabaseName);
        if (fetcher == null){
            return null;
        }
        else {
            return fetcher.fetchCvTermByIdentifier(termIdentifier, ontologyDatabaseName);
        }
    }

    public T fetchCvTermByIdentifier(String termIdentifier, CvTerm ontologyDatabase) throws BridgeFailedException {
        if (ontologyDatabase == null || !this.delegateFetchers.containsKey(ontologyDatabase.getShortName())){
            T firstTermRetrieved = null;
            Iterator<F> fetcherIterator = delegateFetchers.values().iterator();
            while(firstTermRetrieved == null && fetcherIterator.hasNext()){
                firstTermRetrieved = fetcherIterator.next().fetchCvTermByIdentifier(termIdentifier, ontologyDatabase);
            }

            return firstTermRetrieved;
        }

        F fetcher = this.delegateFetchers.get(ontologyDatabase.getShortName());
        if (fetcher == null){
            return null;
        }
        else {
            return fetcher.fetchCvTermByIdentifier(termIdentifier, ontologyDatabase);
        }
    }

    public T fetchCvTermByName(String searchName, String ontologyDatabaseName) throws BridgeFailedException {
        if (ontologyDatabaseName == null || !this.delegateFetchers.containsKey(ontologyDatabaseName)){
            T firstTermRetrieved = null;
            Iterator<F> fetcherIterator = delegateFetchers.values().iterator();
            while(firstTermRetrieved == null && fetcherIterator.hasNext()){
                firstTermRetrieved = fetcherIterator.next().fetchCvTermByName(searchName, ontologyDatabaseName);
            }

            return firstTermRetrieved;
        }

        F fetcher = this.delegateFetchers.get(ontologyDatabaseName);
        if (fetcher == null){
            return null;
        }
        else {
            return fetcher.fetchCvTermByName(searchName, ontologyDatabaseName);
        }
    }

    public T fetchCvTermByName(String searchName) throws BridgeFailedException {
        T firstTermRetrieved = null;
        Iterator<F> fetcherIterator = delegateFetchers.values().iterator();
        while(firstTermRetrieved == null && fetcherIterator.hasNext()){
            firstTermRetrieved = fetcherIterator.next().fetchCvTermByName(searchName);
        }

        return firstTermRetrieved;
    }

    public Collection<T> fetchCvTermsByIdentifiers(Collection<String> termIdentifiers, String ontologyDatabaseName) throws BridgeFailedException {
        if (ontologyDatabaseName == null || !this.delegateFetchers.containsKey(ontologyDatabaseName)){
            Collection<T> firstTermRetrieved = Collections.EMPTY_LIST;
            Iterator<F> fetcherIterator = delegateFetchers.values().iterator();
            while(firstTermRetrieved.isEmpty() && fetcherIterator.hasNext()){
                firstTermRetrieved = fetcherIterator.next().fetchCvTermsByIdentifiers(termIdentifiers, ontologyDatabaseName);
            }

            return firstTermRetrieved;
        }

        F fetcher = this.delegateFetchers.get(ontologyDatabaseName);
        if (fetcher == null){
            return null;
        }
        else {
            return fetcher.fetchCvTermsByIdentifiers(termIdentifiers, ontologyDatabaseName);
        }
    }

    public Collection<T> fetchCvTermsByIdentifiers(Collection<String> termIdentifiers, CvTerm ontologyDatabase) throws BridgeFailedException {
        if (ontologyDatabase == null || !this.delegateFetchers.containsKey(ontologyDatabase.getShortName())){
            Collection<T> firstTermRetrieved = Collections.EMPTY_LIST;
            Iterator<F> fetcherIterator = delegateFetchers.values().iterator();
            while(firstTermRetrieved.isEmpty() && fetcherIterator.hasNext()){
                firstTermRetrieved = fetcherIterator.next().fetchCvTermsByIdentifiers(termIdentifiers, ontologyDatabase);
            }

            return firstTermRetrieved;
        }

        F fetcher = this.delegateFetchers.get(ontologyDatabase.getShortName());
        if (fetcher == null){
            return null;
        }
        else {
            return fetcher.fetchCvTermsByIdentifiers(termIdentifiers, ontologyDatabase);
        }
    }

    public Collection<T> fetchCvTermsByNames(Collection<String> searchNames, String ontologyDatabaseName) throws BridgeFailedException {
        if (ontologyDatabaseName == null || !this.delegateFetchers.containsKey(ontologyDatabaseName)){
            Collection<T> firstTermRetrieved = Collections.EMPTY_LIST;
            Iterator<F> fetcherIterator = delegateFetchers.values().iterator();
            while(firstTermRetrieved.isEmpty() && fetcherIterator.hasNext()){
                firstTermRetrieved = fetcherIterator.next().fetchCvTermsByNames(searchNames, ontologyDatabaseName);
            }

            return firstTermRetrieved;
        }

        F fetcher = this.delegateFetchers.get(ontologyDatabaseName);
        if (fetcher == null){
            return null;
        }
        else {
            return fetcher.fetchCvTermsByNames(searchNames, ontologyDatabaseName);
        }
    }

    public Collection<T> fetchCvTermsByNames(Collection<String> searchNames) throws BridgeFailedException {
        Collection<T> firstTermRetrieved = Collections.EMPTY_LIST;
        Iterator<F> fetcherIterator = delegateFetchers.values().iterator();
        while(firstTermRetrieved.isEmpty() && fetcherIterator.hasNext()){
            firstTermRetrieved = fetcherIterator.next().fetchCvTermsByNames(searchNames);
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
