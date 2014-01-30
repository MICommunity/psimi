package psidev.psi.mi.jami.bridges.chebi;

import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import psidev.psi.mi.jami.bridges.exception.BridgeFailedException;
import psidev.psi.mi.jami.model.BioactiveEntity;

import java.util.Collection;

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
        Collection<BioactiveEntity> fetched = chebiFetcher.fetchByIdentifier("CHEBI:15377");
        report(fetched);
    }

    @Test
    public void testB() throws BridgeFailedException {
        Collection<BioactiveEntity> fetched = chebiFetcher.fetchByIdentifier("CHEBI:53438");
        report(fetched);
    }

    @Test
    public void testC() throws BridgeFailedException {
        Collection<BioactiveEntity> fetched = chebiFetcher.fetchByIdentifier("CHEBI:17627");
        report(fetched);

    }

    public static void report(Collection<BioactiveEntity> fetched){
        Assert.assertTrue(fetched.size() == 1);

        BioactiveEntity entity = fetched.iterator().next();
        Assert.assertNotNull(entity.getShortName());
        Assert.assertNotNull(entity.getFullName());

        Assert.assertNotNull(entity.getSmile());

        Assert.assertNotNull(entity.getStandardInchi());
        Assert.assertNotNull(entity.getStandardInchiKey());
        Assert.assertNotNull(entity.getAliases().toString());

        Assert.assertTrue(entity.getAnnotations().isEmpty());
        Assert.assertFalse(entity.getIdentifiers().isEmpty());
    }
}
