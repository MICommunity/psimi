package psidev.psi.mi.jami.bridges.fetcher.mockfetcher.bioactiveentity;

import psidev.psi.mi.jami.bridges.exception.BridgeFailedException;
import psidev.psi.mi.jami.bridges.fetcher.BioactiveEntityFetcher;
import psidev.psi.mi.jami.bridges.fetcher.mockfetcher.AbstractExceptionThrowingMockFetcher;
import psidev.psi.mi.jami.model.BioactiveEntity;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 09/08/13
 */
public class ExceptionThrowingMockBioactiveEntityFetcher
        extends AbstractExceptionThrowingMockFetcher <BioactiveEntity>
        implements BioactiveEntityFetcher {


    public ExceptionThrowingMockBioactiveEntityFetcher(int maxQuery) {
        super(maxQuery);
    }

    public BioactiveEntity getBioactiveEntityByIdentifier(String identifier) throws BridgeFailedException {
        return super.getEntry(identifier);
    }
}
