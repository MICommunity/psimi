package psidev.psi.mi.jami.bridges.fetcher.mock;

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
    public Collection<OntologyTerm> fetchRootTerms(String databaseName) {
        if (databaseName == null || !getDelegateFetchers().containsKey(databaseName)){
            Collection<OntologyTerm> firstTermRetrieved = Collections.EMPTY_LIST;
            Iterator<OntologyTermFetcher> fetcherIterator = getDelegateFetchers().values().iterator();
            while(firstTermRetrieved.isEmpty() && fetcherIterator.hasNext()){
                firstTermRetrieved = fetcherIterator.next().fetchRootTerms(databaseName);
            }

            return firstTermRetrieved;
        }
        OntologyTermFetcher fetcher = getDelegateFetchers().get(databaseName);
        if (fetcher == null){
            return Collections.EMPTY_LIST;
        }
        else {
            return fetcher.fetchRootTerms(databaseName);
        }
    }

    public Collection<OntologyTerm> fetchRootTerms(CvTerm database) {
        if (database == null || !getDelegateFetchers().containsKey(database.getShortName())){
            Collection<OntologyTerm> firstTermRetrieved = Collections.EMPTY_LIST;
            Iterator<OntologyTermFetcher> fetcherIterator = getDelegateFetchers().values().iterator();
            while(firstTermRetrieved.isEmpty() && fetcherIterator.hasNext()){
                firstTermRetrieved = fetcherIterator.next().fetchRootTerms(database);
            }

            return firstTermRetrieved;
        }
        OntologyTermFetcher fetcher = getDelegateFetchers().get(database.getShortName());
        if (fetcher == null){
            return Collections.EMPTY_LIST;
        }
        else {
            return fetcher.fetchRootTerms(database);
        }
    }
}
