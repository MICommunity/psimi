package psidev.psi.mi.jami.bridges.chebi;

import org.junit.Test;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 07/08/13
 */
public class ChebiFetcherTest {

    @Test
    public void test(){
        ChebiFetcher chebiFetcher = new ChebiFetcher();

        chebiFetcher.getBioactiveEntityByIdentifier("");
    }
}
