package psidev.psi.mi.jami.bridges.fetcher.mock;

import psidev.psi.mi.jami.bridges.exception.BridgeFailedException;
import psidev.psi.mi.jami.bridges.fetcher.PublicationFetcher;
import psidev.psi.mi.jami.bridges.fetcher.PublicationIdentifierSource;
import psidev.psi.mi.jami.bridges.fetcher.mock.AbstractExceptionThrowingMockFetcher;
import psidev.psi.mi.jami.model.Publication;

import java.util.Collection;
import java.util.Collections;

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
public class ExceptionThrowingMockPublicationFetcher
        extends AbstractExceptionThrowingMockFetcher<Publication>
        implements PublicationFetcher {


    public ExceptionThrowingMockPublicationFetcher(int maxQuery) {
        super(maxQuery);
    }

    public Publication getPublicationByIdentifier(String pubmedID, PublicationIdentifierSource source) throws BridgeFailedException {
        return getEntry(pubmedID);
    }

    public Collection<Publication> getPublicationsByIdentifiers(Collection<String> identifiers , PublicationIdentifierSource source) throws BridgeFailedException {
        return Collections.EMPTY_LIST;
    }
}
