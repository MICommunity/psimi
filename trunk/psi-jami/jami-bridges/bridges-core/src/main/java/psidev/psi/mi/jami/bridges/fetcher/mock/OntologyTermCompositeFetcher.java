package psidev.psi.mi.jami.bridges.fetcher.mock;

import psidev.psi.mi.jami.bridges.fetcher.OntologyTermFetcher;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.OntologyTerm;

import java.util.Collections;
import java.util.Iterator;
import java.util.Set;

/**
 * Ontology fetcher that delegates to different ontologyTermFetchers
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>18/07/13</pre>
 */

public class OntologyTermCompositeFetcher extends CvTermCompositeFetcherTemplate<OntologyTerm, OntologyTermFetcher> implements OntologyTermFetcher{
    public Set<OntologyTerm> fetchRootTerms(String databaseName) {
        if (databaseName == null || !getDelegateFetchers().containsKey(databaseName)){
            Set<OntologyTerm> firstTermRetrieved = Collections.EMPTY_SET;
            Iterator<OntologyTermFetcher> fetcherIterator = getDelegateFetchers().values().iterator();
            while(firstTermRetrieved.isEmpty() && fetcherIterator.hasNext()){
                firstTermRetrieved = fetcherIterator.next().fetchRootTerms(databaseName);
            }

            return firstTermRetrieved;
        }
        OntologyTermFetcher fetcher = getDelegateFetchers().get(databaseName);
        if (fetcher == null){
            return Collections.EMPTY_SET;
        }
        else {
            return fetcher.fetchRootTerms(databaseName);
        }
    }

    public Set<OntologyTerm> fetchRootTerms(CvTerm database) {
        if (database == null || !getDelegateFetchers().containsKey(database.getShortName())){
            Set<OntologyTerm> firstTermRetrieved = Collections.EMPTY_SET;
            Iterator<OntologyTermFetcher> fetcherIterator = getDelegateFetchers().values().iterator();
            while(firstTermRetrieved.isEmpty() && fetcherIterator.hasNext()){
                firstTermRetrieved = fetcherIterator.next().fetchRootTerms(database);
            }

            return firstTermRetrieved;
        }
        OntologyTermFetcher fetcher = getDelegateFetchers().get(database.getShortName());
        if (fetcher == null){
            return Collections.EMPTY_SET;
        }
        else {
            return fetcher.fetchRootTerms(database);
        }
    }
}
