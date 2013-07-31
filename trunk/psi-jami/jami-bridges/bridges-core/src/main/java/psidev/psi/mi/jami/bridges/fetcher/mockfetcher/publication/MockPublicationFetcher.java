package psidev.psi.mi.jami.bridges.fetcher.mockfetcher.publication;

import psidev.psi.mi.jami.bridges.exception.BridgeFailedException;
import psidev.psi.mi.jami.bridges.fetcher.PublicationFetcher;
import psidev.psi.mi.jami.model.Publication;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 31/07/13
 */
public class MockPublicationFetcher
        implements PublicationFetcher{

    private Map<String, Publication> localPublications;

    public MockPublicationFetcher(){
        localPublications = new HashMap<String , Publication>();
    }

    public void addNewPublication(String identifier, Publication publication){
        if(publication == null) return;

        localPublications.put(identifier, publication);
    }

    public void clearProteins(){
        localPublications.clear();
    }


    public Publication getPublicationByPubmedID(String pubmedID) throws BridgeFailedException {
        if(pubmedID == null) throw new IllegalArgumentException(
                "Attempted to query mock protein fetcher for null identifier.");
        if(! localPublications.containsKey(pubmedID)) {
            return null;
        } else {
            return localPublications.get(pubmedID);
        }
    }
}
