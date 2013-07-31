package psidev.psi.mi.jami.bridges.europubmedcentral;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import psidev.psi.mi.jami.bridges.exception.BridgeFailedException;

import static org.junit.Assert.fail;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 31/07/13
 */
public class EuroPubmedCentralFetcherTest {


    protected static final Logger log = LoggerFactory.getLogger(EuroPubmedCentralFetcherTest.class.getName());

    EuroPubmedCentralFetcher fetcher;

    @Before
    public void setup() throws BridgeFailedException {
        fetcher = new EuroPubmedCentralFetcher();
    }

    @Test
    public void test() throws BridgeFailedException {
        fetcher.getPublicationByPubmedID("23671334");

        log.info("------c-----");

        fetcher.getPublicationByPubmedID("13054692");


        log.info("----c-------");

        fetcher.getPublicationByPubmedID("10831611");
    }

}
