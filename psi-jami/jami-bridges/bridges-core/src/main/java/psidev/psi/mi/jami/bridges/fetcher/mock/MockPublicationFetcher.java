package psidev.psi.mi.jami.bridges.fetcher.mock;

import psidev.psi.mi.jami.bridges.exception.BridgeFailedException;
import psidev.psi.mi.jami.bridges.fetcher.PublicationFetcher;
import psidev.psi.mi.jami.model.Publication;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

/**
 * A mock fetcher for testing.
 * It extends all the methods of the true fetcher, but does not access an external service.
 * Instead, it holds a map which can be loaded with objects and keys. which are then returned.
 * It attempts to replicate the responses of the fetcher in most scenarios.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 31/07/13
 */
public class MockPublicationFetcher
        extends AbstractMockFetcher<Publication>
        implements PublicationFetcher{

    public MockPublicationFetcher(){
        super();
    }

    public Publication fetchPublicationByIdentifier(String pubmedID , String source) throws BridgeFailedException {
        return getEntry(pubmedID);
    }

    public Collection<Publication> fetchPublicationsByIdentifiers(Map<String, Collection<String>> identifiers)
            throws BridgeFailedException {
        Collection<Publication> results = new ArrayList<Publication>();
        for(Map.Entry<String, Collection<String>> id : identifiers.entrySet()){
            for (String id2 : id.getValue()){
                results.add(fetchPublicationByIdentifier(id.getKey(), id2));
            }
        }
        return results;
    }
}
