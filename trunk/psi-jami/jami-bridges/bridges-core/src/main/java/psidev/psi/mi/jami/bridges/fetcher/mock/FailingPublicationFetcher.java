package psidev.psi.mi.jami.bridges.fetcher.mock;

import psidev.psi.mi.jami.bridges.exception.BridgeFailedException;
import psidev.psi.mi.jami.bridges.fetcher.PublicationFetcher;
import psidev.psi.mi.jami.model.Publication;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

/**
 * A mock fetcher for testing exceptions.
 * It extends the functionality of the mock fetcher but can also throw exceptions.
 * Upon initialisation, an integer is given which sets how many times a query is made before returning the result.
 * If the current query matches the last query and the counter of the number of times is less than the maxQuery
 * set at initialisation, then an exception will be thrown.
 * Additionally, if the maxQuery is set to -1, the fetcher will always throw an exception.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 05/08/13
 */
public class FailingPublicationFetcher
        extends AbstractFailingFetcher<Publication>
        implements PublicationFetcher {


    public FailingPublicationFetcher(int maxQuery) {
        super(maxQuery);
    }

    public Publication fetchPublicationByIdentifier(String pubmedID, String source) throws BridgeFailedException {
        return getEntry(pubmedID);
    }

    public Collection<Publication> fetchPublicationsByIdentifiers(Map<String,Collection<String>> identifiers) throws BridgeFailedException {
        return Collections.EMPTY_LIST;
    }
}
