package psidev.psi.mi.jami.bridges.chebi;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import psidev.psi.mi.jami.bridges.exception.BridgeFailedException;
import psidev.psi.mi.jami.model.BioactiveEntity;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 07/08/13
 */
public class ChebiFetcherTest {

    protected static final Logger log = LoggerFactory.getLogger(ChebiFetcherTest.class.getName());

    private ChebiFetcher chebiFetcher;

    @Before
    public void setUp(){
        chebiFetcher = new ChebiFetcher();
    }

    @Test
    public void test() throws BridgeFailedException {
        BioactiveEntity fetched = chebiFetcher.fetchByIdentifier("CHEBI:15377");
        report(fetched);
    }

    @Test
    public void testB() throws BridgeFailedException {
        BioactiveEntity fetched = chebiFetcher.fetchByIdentifier("CHEBI:53438");
        report(fetched);
    }

    @Test
    public void testC() throws BridgeFailedException {
        BioactiveEntity fetched = chebiFetcher.fetchByIdentifier("CHEBI:17627");
        report(fetched);

    }




    public static void report(BioactiveEntity fetched){

        log.info(fetched.getShortName());
        log.info(fetched.getFullName());

        log.info(fetched.getSmile());

        log.info(fetched.getStandardInchi());
        log.info(fetched.getStandardInchiKey());
        log.info(fetched.getAliases().toString());

        log.info(fetched.getAnnotations().toString());
        log.info(fetched.getIdentifiers().toString());
    }
}
