package psidev.psi.mi.jami.bridges.fetcher.mockfetcher.cvterm;

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

    public T getCvTermByIdentifier(String termIdentifier, String ontologyDatabaseName) throws BridgeFailedException {

        if (ontologyDatabaseName == null || !this.delegateFetchers.containsKey(ontologyDatabaseName)){
            T firstTermRetrieved = null;
            Iterator<F> fetcherIterator = delegateFetchers.values().iterator();
            while(firstTermRetrieved == null && fetcherIterator.hasNext()){
                 firstTermRetrieved = fetcherIterator.next().getCvTermByIdentifier(termIdentifier, ontologyDatabaseName);
            }

            return firstTermRetrieved;
        }

        F fetcher = this.delegateFetchers.get(ontologyDatabaseName);
        if (fetcher == null){
            return null;
        }
        else {
            return fetcher.getCvTermByIdentifier(termIdentifier, ontologyDatabaseName);
        }
    }

    public T getCvTermByIdentifier(String termIdentifier, CvTerm ontologyDatabase) throws BridgeFailedException {
        if (ontologyDatabase == null || !this.delegateFetchers.containsKey(ontologyDatabase.getShortName())){
            T firstTermRetrieved = null;
            Iterator<F> fetcherIterator = delegateFetchers.values().iterator();
            while(firstTermRetrieved == null && fetcherIterator.hasNext()){
                firstTermRetrieved = fetcherIterator.next().getCvTermByIdentifier(termIdentifier, ontologyDatabase);
            }

            return firstTermRetrieved;
        }

        F fetcher = this.delegateFetchers.get(ontologyDatabase.getShortName());
        if (fetcher == null){
            return null;
        }
        else {
            return fetcher.getCvTermByIdentifier(termIdentifier, ontologyDatabase);
        }
    }

    public T getCvTermByExactName(String searchName, String ontologyDatabaseName) throws BridgeFailedException {
        if (ontologyDatabaseName == null || !this.delegateFetchers.containsKey(ontologyDatabaseName)){
            T firstTermRetrieved = null;
            Iterator<F> fetcherIterator = delegateFetchers.values().iterator();
            while(firstTermRetrieved == null && fetcherIterator.hasNext()){
                firstTermRetrieved = fetcherIterator.next().getCvTermByExactName(searchName, ontologyDatabaseName);
            }

            return firstTermRetrieved;
        }

        F fetcher = this.delegateFetchers.get(ontologyDatabaseName);
        if (fetcher == null){
            return null;
        }
        else {
            return fetcher.getCvTermByExactName(searchName, ontologyDatabaseName);
        }
    }

    public T getCvTermByExactName(String searchName) throws BridgeFailedException {
        T firstTermRetrieved = null;
        Iterator<F> fetcherIterator = delegateFetchers.values().iterator();
        while(firstTermRetrieved == null && fetcherIterator.hasNext()){
            firstTermRetrieved = fetcherIterator.next().getCvTermByExactName(searchName);
        }

        return firstTermRetrieved;
    }

    public Collection<T> getCvTermByInexactName(String searchName, String databaseName) throws BridgeFailedException {
        if (databaseName == null || !this.delegateFetchers.containsKey(databaseName)){
            Collection<T> firstTermRetrieved = Collections.EMPTY_LIST;
            Iterator<F> fetcherIterator = delegateFetchers.values().iterator();
            while(firstTermRetrieved.isEmpty() && fetcherIterator.hasNext()){
                firstTermRetrieved = fetcherIterator.next().getCvTermByInexactName(searchName, databaseName);
            }

            return firstTermRetrieved;
        }

        F fetcher = this.delegateFetchers.get(databaseName);
        if (fetcher == null){
            return null;
        }
        else {
            return fetcher.getCvTermByInexactName(searchName, databaseName);
        }
    }

    public Collection<T> getCvTermByInexactName(String searchName, CvTerm database) throws BridgeFailedException {
        if (database == null || !this.delegateFetchers.containsKey(database.getShortName())){
            Collection<T> firstTermRetrieved = Collections.EMPTY_LIST;
            Iterator<F> fetcherIterator = delegateFetchers.values().iterator();
            while(firstTermRetrieved.isEmpty() && fetcherIterator.hasNext()){
                firstTermRetrieved = fetcherIterator.next().getCvTermByInexactName(searchName, database);
            }

            return firstTermRetrieved;
        }

        F fetcher = this.delegateFetchers.get(database.getShortName());
        if (fetcher == null){
            return null;
        }
        else {
            return fetcher.getCvTermByInexactName(searchName, database);
        }
    }

    public Collection<T> getCvTermsByIdentifiers(Collection<String> termIdentifiers, String ontologyDatabaseName) throws BridgeFailedException {
        if (ontologyDatabaseName == null || !this.delegateFetchers.containsKey(ontologyDatabaseName)){
            Collection<T> firstTermRetrieved = Collections.EMPTY_LIST;
            Iterator<F> fetcherIterator = delegateFetchers.values().iterator();
            while(firstTermRetrieved.isEmpty() && fetcherIterator.hasNext()){
                firstTermRetrieved = fetcherIterator.next().getCvTermsByIdentifiers(termIdentifiers, ontologyDatabaseName);
            }

            return firstTermRetrieved;
        }

        F fetcher = this.delegateFetchers.get(ontologyDatabaseName);
        if (fetcher == null){
            return null;
        }
        else {
            return fetcher.getCvTermsByIdentifiers(termIdentifiers, ontologyDatabaseName);
        }
    }

    public Collection<T> getCvTermsByIdentifiers(Collection<String> termIdentifiers, CvTerm ontologyDatabase) throws BridgeFailedException {
        if (ontologyDatabase == null || !this.delegateFetchers.containsKey(ontologyDatabase.getShortName())){
            Collection<T> firstTermRetrieved = Collections.EMPTY_LIST;
            Iterator<F> fetcherIterator = delegateFetchers.values().iterator();
            while(firstTermRetrieved.isEmpty() && fetcherIterator.hasNext()){
                firstTermRetrieved = fetcherIterator.next().getCvTermsByIdentifiers(termIdentifiers, ontologyDatabase);
            }

            return firstTermRetrieved;
        }

        F fetcher = this.delegateFetchers.get(ontologyDatabase.getShortName());
        if (fetcher == null){
            return null;
        }
        else {
            return fetcher.getCvTermsByIdentifiers(termIdentifiers, ontologyDatabase);
        }
    }

    public Collection<T> getCvTermsByExactNames(Collection<String> searchNames, String ontologyDatabaseName) throws BridgeFailedException {
        if (ontologyDatabaseName == null || !this.delegateFetchers.containsKey(ontologyDatabaseName)){
            Collection<T> firstTermRetrieved = Collections.EMPTY_LIST;
            Iterator<F> fetcherIterator = delegateFetchers.values().iterator();
            while(firstTermRetrieved.isEmpty() && fetcherIterator.hasNext()){
                firstTermRetrieved = fetcherIterator.next().getCvTermsByExactNames(searchNames, ontologyDatabaseName);
            }

            return firstTermRetrieved;
        }

        F fetcher = this.delegateFetchers.get(ontologyDatabaseName);
        if (fetcher == null){
            return null;
        }
        else {
            return fetcher.getCvTermsByExactNames(searchNames, ontologyDatabaseName);
        }
    }

    public Collection<T> getCvTermsByExactNames(Collection<String> searchNames) throws BridgeFailedException {
        Collection<T> firstTermRetrieved = Collections.EMPTY_LIST;
        Iterator<F> fetcherIterator = delegateFetchers.values().iterator();
        while(firstTermRetrieved.isEmpty() && fetcherIterator.hasNext()){
            firstTermRetrieved = fetcherIterator.next().getCvTermsByExactNames(searchNames);
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
