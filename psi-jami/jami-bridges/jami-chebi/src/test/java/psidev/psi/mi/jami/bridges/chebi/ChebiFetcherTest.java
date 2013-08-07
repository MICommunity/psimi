package psidev.psi.mi.jami.bridges.chebi;

import org.junit.Test;
import psidev.psi.mi.jami.bridges.exception.BridgeFailedException;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 07/08/13
 */
public class ChebiFetcherTest {

    @Test
    public void test() throws BridgeFailedException {
        ChebiFetcher chebiFetcher = new ChebiFetcher();

        chebiFetcher.getBioactiveEntityByIdentifier("CHEBI:15377");
    }
}
