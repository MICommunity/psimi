package psidev.psi.mi.jami.bridges.europubmedcentral;


import psidev.psi.mi.jami.bridges.exception.BridgeFailedException;
import psidev.psi.mi.jami.bridges.fetcher.AbstractCachedFetcher;
import psidev.psi.mi.jami.bridges.fetcher.PublicationFetcher;
import psidev.psi.mi.jami.model.Publication;

import java.util.*;

/**
 * A cached version of the EuroPubmedCentralFetcher.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 31/07/13
 */
public class CachedEuroPubmedCentralFetcher
        extends AbstractCachedFetcher
        implements PublicationFetcher {

    public static final String CACHE_NAME = "europubmedcentral-service-cache";

    private PublicationFetcher publicationFetcher;

    public CachedEuroPubmedCentralFetcher() throws BridgeFailedException {
        super(CACHE_NAME);
        initialiseCache();
        this.publicationFetcher = new EuroPubmedCentralFetcher();
    }


    public Publication fetchPublicationByIdentifier(String identifier, String source) throws BridgeFailedException {
        if (identifier != null){
            final String key = "GET_PUBLICATION_BY_IDENTIFIER_"+source+"_"+identifier;
            Object object = getFromCache(key);
            if (object != null){
                return (Publication)object;
            }
            else{
                Publication pub = publicationFetcher.fetchPublicationByIdentifier(identifier, source);
                storeInCache(key, pub);
                return pub;
            }
        }
        return publicationFetcher.fetchPublicationByIdentifier(identifier, source);
    }

    public Collection<Publication> fetchPublicationsByIdentifiers(Map<String, Collection<String>> identifiers) throws BridgeFailedException {
        if (identifiers != null){
            String key = "GET_ENTITIES_BY_IDENTIFIERS_";
            for (Map.Entry<String, Collection<String>> entry : identifiers.entrySet()){
                List<String> ids = new ArrayList<String>(entry.getValue());
                Collections.sort(ids);
                for (String id : ids){
                    key= key+"_"+entry.getKey()+"_"+id;
                }
            }

            Object object = getFromCache(key);
            if (object != null){
                return (Collection<Publication>)object;
            }
            else{
                Collection<Publication> pub = publicationFetcher.fetchPublicationsByIdentifiers(identifiers);
                storeInCache(key, pub);
                return pub;
            }
        }
        return publicationFetcher.fetchPublicationsByIdentifiers(identifiers);
    }
}
