package psidev.psi.mi.jami.bridges.fetcher.mockfetcher.publication;

import psidev.psi.mi.jami.bridges.exception.BridgeFailedException;
import psidev.psi.mi.jami.bridges.fetcher.PublicationFetcher;
import psidev.psi.mi.jami.bridges.fetcher.mockfetcher.AbstractExceptionThrowingMockFetcher;
import psidev.psi.mi.jami.bridges.fetcher.mockfetcher.AbstractMockFetcher;
import psidev.psi.mi.jami.model.Publication;

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

    public Publication getPublicationByPubmedID(String pubmedID) throws BridgeFailedException {
        return getEntry(pubmedID);
    }
}
