package psidev.psi.mi.enricher.cvtermenricher;

import psidev.psi.mi.fetcher.exception.BridgeFailedException;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.fetcher.ols.OlsFetcher;

import java.util.HashMap;

/**
 * A manager for redirecting queries to alternative CvTermFetchers.
 * Allows for users who would rather use their own fetcher for some or all ontologies.
 *
 * @author: Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 08/05/13
 * Time: 16:36
 */
public class CvTermFetcherManager implements CvTermFetcher {
    private final String DEFAULTKEY = "default";

    private HashMap<String,CvTermFetcher> ontologyFetchers;

    public CvTermFetcherManager() {
        ontologyFetchers = new HashMap<String, CvTermFetcher>();
    }

    public void addDefaultFetcher(CvTermFetcher fetcher) throws BridgeFailedException {
        //ontologyFetchers.put("MI", new LocalFetcher());
        ontologyFetchers.put(DEFAULTKEY, fetcher);
    }

    public void addDefaultFetcher() throws BridgeFailedException {
        addDefaultFetcher(new OlsFetcher());
    }

    public void addCvTermFetcher(String key, CvTermFetcher fetcher){
        ontologyFetchers.put(key, fetcher);
    }

    public CvTerm getCVTermByID(String identifier, String dbname) throws BridgeFailedException {
        if(ontologyFetchers.containsKey(dbname)){
            return ontologyFetchers.get(dbname).getCVTermByID(identifier, dbname);
        }else{
            return ontologyFetchers.get(DEFAULTKEY).getCVTermByID(identifier, dbname);
        }
    }

    public CvTerm getCVTermByName(String name, String dbname) throws BridgeFailedException {
        if(ontologyFetchers.containsKey(dbname)){
            return ontologyFetchers.get(dbname).getCVTermByName(name, dbname);
        }else{
            return ontologyFetchers.get(DEFAULTKEY).getCVTermByName(name, dbname);
        }
    }
}
