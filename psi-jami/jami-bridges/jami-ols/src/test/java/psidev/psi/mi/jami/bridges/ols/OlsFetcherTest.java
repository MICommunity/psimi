package psidev.psi.mi.jami.bridges.ols;

import org.junit.Before;
import org.junit.Test;
import psidev.psi.mi.jami.bridges.exception.BridgeFailedException;
import psidev.psi.mi.jami.model.CvTerm;

import static org.junit.Assert.*;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 24/06/13
 */
public class OlsFetcherTest {

    private OlsFetcher fetcher;

    @Before
    public void initialiseFetcher() throws BridgeFailedException {
        fetcher = new OlsFetcher();
    }

    @Test
    public void test_CvTerm_byIdentifier() throws BridgeFailedException {
        String identifier = "MI:0580";
        String ontologyName = "psi-mi";
        CvTerm cvTermFetched =  fetcher.getCvTermByIdentifier(identifier, ontologyName);

        assertNotNull(cvTermFetched);
        assertEquals("electron acceptor" , cvTermFetched.getShortName());
        assertEquals(identifier , cvTermFetched.getMIIdentifier());
    }

    @Test
    public void test_CvTerm_by_Identifier_with_ShortName() throws BridgeFailedException {
        String identifier = "MI:0473";
        String ontologyName = "psi-mi";
        CvTerm cvTermFetched =  fetcher.getCvTermByIdentifier(identifier, ontologyName);

        assertNotNull(cvTermFetched);
        assertEquals("participant xref" , cvTermFetched.getShortName());
        assertEquals("participant database" , cvTermFetched.getFullName());
        assertEquals(identifier , cvTermFetched.getMIIdentifier());
    }

    @Test
    public void test_CvTerm_by_Identifier_with_Synonym() throws BridgeFailedException {
        String identifier = "MI:1064";
        String ontologyName = "psi-mi";
        CvTerm cvTermFetched =  fetcher.getCvTermByIdentifier(identifier, ontologyName);

        assertNotNull(cvTermFetched);
        assertEquals("confidence" , cvTermFetched.getShortName());
        assertEquals("interaction confidence" , cvTermFetched.getFullName());
        assertEquals(1 , cvTermFetched.getSynonyms().size());
        assertEquals("scoring system" , cvTermFetched.getSynonyms().iterator().next().getName());

        assertEquals(identifier , cvTermFetched.getMIIdentifier());
    }


    @Test
    public void test_CvTerm_by_Identifier_with_failing_identifier() throws BridgeFailedException {
        String identifier = "Foo";
        String ontologyName = "psi-mi";
        CvTerm cvTermFetched =  fetcher.getCvTermByIdentifier(identifier, ontologyName);

        assertNull(cvTermFetched);
    }


}
