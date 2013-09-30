package psidev.psi.mi.jami.bridges.ols;

import psidev.psi.mi.jami.bridges.exception.BridgeFailedException;
import psidev.psi.mi.jami.bridges.fetcher.AbstractCachedFetcher;
import psidev.psi.mi.jami.bridges.fetcher.CachedFetcher;
import psidev.psi.mi.jami.bridges.fetcher.CvTermFetcher;
import psidev.psi.mi.jami.model.CvTerm;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Generic implementation for CachedOlsService
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>05/09/13</pre>
 */

public class CachedOlsFetcher<T extends CvTerm> extends AbstractCachedFetcher implements CvTermFetcher<T>, CachedFetcher {

    private CvTermFetcher<T> delegateFetcher;


    public CachedOlsFetcher(String cacheName, CvTermFetcher<T> delegateFetcher) throws BridgeFailedException {
        super(cacheName);
        this.delegateFetcher = delegateFetcher;
    }

    /**
     * Uses the identifier and the name of the database to search for a complete form of the cvTerm.
     * @param termIdentifier    The identifier for the CvTerm to fetch.
     * @param miOntologyName  The name of the ontology to search for. Eg, psi-mi, psi-mod, go. Must not be Null.
     * @return  A full cvTerm which matches the search term or null if one cannot be found.
     * @throws BridgeFailedException
     */
    public T fetchByIdentifier(String termIdentifier, String miOntologyName)
            throws BridgeFailedException{
        if(termIdentifier == null) throw new IllegalArgumentException("Provided OntologyTerm has no identifier.");
        if(miOntologyName == null) throw new IllegalArgumentException("Provided OntologyTerm has no ontology name.");

        final String key = "GET_BY_IDENTIFIER_"+termIdentifier+"_"+miOntologyName;

        Object data = getFromCache( key );
        if( data == null) {
            data = delegateFetcher.fetchByIdentifier(termIdentifier, miOntologyName);
            storeInCache(key, data);
        }
        return (T)data;
    }

    /**
     * Uses the identifier and a cvTerm denoting the database to search to fetch a complete from of the term.
     * @param termIdentifier     The identifier for the CvTerm to fetch
     * @param ontologyDatabase  The cvTerm of the ontology to search for.
     * @return  A fully enriched cvTerm which matches the search term or null if one cannot be found.
     * @throws BridgeFailedException
     */
    public T fetchByIdentifier(String termIdentifier, CvTerm ontologyDatabase)
            throws BridgeFailedException{
        if(termIdentifier == null) throw new IllegalArgumentException("Provided OntologyTerm has no identifier.");
        if(ontologyDatabase == null) throw new IllegalArgumentException("Provided OntologyTerm has no ontology name.");

        final String key = "GET_BY_IDENTIFIER_"+termIdentifier+"_"+ontologyDatabase.getShortName();

        Object data = getFromCache( key );
        if( data == null) {
            data = delegateFetcher.fetchByIdentifier(termIdentifier, ontologyDatabase);
            storeInCache(key, data);
        }
        return (T)data;
    }

    /**
     * Uses the name of the term and the name of the database to search for a complete form of the term.
     * @param searchName    A full or short name for the term to be searched for.
     * @param miOntologyName  The ontology to search for the term in.
     * @return  A fully enriched cvTerm which matches the search term or null if one cannot be found.
     * @throws BridgeFailedException
     */
    public T fetchByName(String searchName, String miOntologyName)
            throws BridgeFailedException{
        if(searchName == null) throw new IllegalArgumentException("Provided OntologyTerm has no identifier.");
        if(miOntologyName == null) throw new IllegalArgumentException("Provided OntologyTerm has no ontology name.");

        final String key = "GET_BY_NAME_"+searchName+"_"+miOntologyName;

        Object data = getFromCache( key );
        if( data == null) {
            data = delegateFetcher.fetchByName(searchName, miOntologyName);
            storeInCache(key, data);
        }
        return (T)data;
    }

    /**
     * Uses the name of the term and the name of the database to search for a complete form of the term.
     * <p>
     * If the term can not be resolved to a database, then this method may return null.
     *
     * @param searchName    A full or short name for the term to be searched for.
     * @return  A fully enriched cvTerm which matches the search term or null if one cannot be found.
     * @throws BridgeFailedException
     */
    public Collection<T> fetchByName(String searchName)
            throws BridgeFailedException{
        if(searchName == null) throw new IllegalArgumentException("Provided OntologyTerm is null.");

        final String key = "GET_BY_NAME_"+searchName;

        Object data = getFromCache( key );
        if( data == null) {
            data = delegateFetcher.fetchByName(searchName);
            storeInCache(key, data);
        }
        return (Collection<T>)data;
    }

    public Collection<T> fetchByIdentifiers(Collection<String> termIdentifiers, String miOntologyName) throws BridgeFailedException {
        if(termIdentifiers == null) throw new IllegalArgumentException("Provided identifiers are null.");
        if(miOntologyName == null) throw new IllegalArgumentException("Provided OntologyTerm has no ontology names.");

        String key = "GET_BY_IDENTIFIERS_"+miOntologyName;

        List<String> sortedIds = new ArrayList<String>(termIdentifiers);
        Collections.sort(sortedIds);
        for (String id: sortedIds){
           key=key+"_"+id;
        }

        Object data = getFromCache( key );
        if( data == null) {
            data = delegateFetcher.fetchByIdentifiers(termIdentifiers, miOntologyName);
            storeInCache(key, data);
        }
        return (Collection<T>)data;
    }

    public Collection<T> fetchByIdentifiers(Collection<String> termIdentifiers, CvTerm ontologyDatabase) throws BridgeFailedException {
        if(termIdentifiers == null) throw new IllegalArgumentException("Provided identifiers are null.");
        if(ontologyDatabase == null) throw new IllegalArgumentException("Provided OntologyTerm has no ontology names.");

        String key = "GET_BY_IDENTIFIERS_"+ontologyDatabase.getShortName();

        List<String> sortedIds = new ArrayList<String>(termIdentifiers);
        Collections.sort(sortedIds);
        for (String id: sortedIds){
            key=key+"_"+id;
        }

        Object data = getFromCache( key );
        if( data == null) {
            data = delegateFetcher.fetchByIdentifiers(termIdentifiers, ontologyDatabase);
            storeInCache(key, data);
        }
        return (Collection<T>)data;
    }

    public Collection<T> fetchByNames(Collection<String> searchNames, String miOntologyName) throws BridgeFailedException {
        if(searchNames == null) throw new IllegalArgumentException("Provided names are null.");
        if(miOntologyName == null) throw new IllegalArgumentException("Provided OntologyTerm has no ontology names.");

        String key = "GET_BY_NAMES_"+miOntologyName;

        List<String> sortedIds = new ArrayList<String>(searchNames);
        Collections.sort(sortedIds);
        for (String id: sortedIds){
            key=key+"_"+id;
        }

        Object data = getFromCache( key );
        if( data == null) {
            data = delegateFetcher.fetchByNames(searchNames, miOntologyName);
            storeInCache(key, data);
        }
        return (Collection<T>)data;
    }

    public Collection<T> fetchByNames(Collection<String> searchNames) throws BridgeFailedException {
        if(searchNames == null) throw new IllegalArgumentException("Provided names are null.");

        String key = "GET_BY_NAMES";

        List<String> sortedIds = new ArrayList<String>(searchNames);
        Collections.sort(sortedIds);
        for (String id: sortedIds){
            key=key+"_"+id;
        }

        Object data = getFromCache( key );
        if( data == null) {
            data = delegateFetcher.fetchByNames(searchNames);
            storeInCache(key, data);
        }
        return (Collection<T>)data;
    }
}
