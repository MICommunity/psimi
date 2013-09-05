package psidev.psi.mi.jami.bridges.ols;

import org.junit.Before;
import org.junit.Test;
import psidev.psi.mi.jami.bridges.exception.BridgeFailedException;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.utils.CvTermUtils;

import java.util.Collection;

import static org.junit.Assert.*;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 24/06/13
 */
public class OlsFetcherTest {

    private OlsCvTermFetcher fetcher;

    @Before
    public void initialiseFetcher() throws BridgeFailedException {
        fetcher = new OlsCvTermFetcher();
    }

    @Test
    public void test_getCvTermByIdentifier_using_MI_Identifier_and_databaseName() throws BridgeFailedException {
        String identifier = "MI:0580";
        String ontologyName = "psi-mi";
        CvTerm cvTermFetched =  fetcher.fetchCvTermByIdentifier(identifier, ontologyName);

        assertNotNull(cvTermFetched);
        assertEquals("electron acceptor" , cvTermFetched.getShortName());
        assertEquals(identifier , cvTermFetched.getMIIdentifier());
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_getCvTermByIdentifier_using_MI_Identifier_and_null_databaseName() throws BridgeFailedException {
        String identifier = "MI:0580";
        String ontologyName = null;
        CvTerm cvTermFetched =  fetcher.fetchCvTermByIdentifier(identifier, ontologyName);
    }

    @Test
    public void test_getCvTermByIdentifier_using_MI_Identifier_and_databaseName_with_ShortName() throws BridgeFailedException {
        String identifier = "MI:0473";
        String ontologyName = "psi-mi";
        CvTerm cvTermFetched =  fetcher.fetchCvTermByIdentifier(identifier, ontologyName);

        assertNotNull(cvTermFetched);
        assertEquals("participant xref" , cvTermFetched.getShortName());
        assertEquals("participant database" , cvTermFetched.getFullName());
        assertEquals(identifier , cvTermFetched.getMIIdentifier());
    }

    @Test
    public void test_getCvTermByIdentifier_using_MI_Identifier_and_databaseName_with_Synonym() throws BridgeFailedException {
        String identifier = "MI:1064";
        String ontologyName = "psi-mi";
        CvTerm cvTermFetched =  fetcher.fetchCvTermByIdentifier(identifier, ontologyName);

        assertNotNull(cvTermFetched);
        assertEquals("confidence" , cvTermFetched.getShortName());
        assertEquals("interaction confidence" , cvTermFetched.getFullName());
        assertEquals(1 , cvTermFetched.getSynonyms().size());
        assertEquals("scoring system" , cvTermFetched.getSynonyms().iterator().next().getName());

        assertEquals(identifier , cvTermFetched.getMIIdentifier());
    }


    @Test
    public void test_getCvTermByIdentifier_using_MI_Identifier_and_databaseName_with_failing_identifier() throws BridgeFailedException {
        String identifier = "Foo";
        String ontologyName = "psi-mi";
        CvTerm cvTermFetched =  fetcher.fetchCvTermByIdentifier(identifier, ontologyName);

        assertNull(cvTermFetched);
    }

    @Test
    public void test_getCvTermByIdentifier_using_GO_Identifier_and_databaseName() throws BridgeFailedException {
        String identifier = "GO:0009055";
        String ontologyName = "go";
        CvTerm cvTermFetched =  fetcher.fetchCvTermByIdentifier(identifier, ontologyName);

        assertNotNull(cvTermFetched);
        assertEquals("electron carrier activity" , cvTermFetched.getShortName());
    }


    @Test
    public void test_getCvTermByIdentifier_using_MI_Identifier_and_databaseCvTerm() throws BridgeFailedException {
        String identifier = "MI:0580";
        CvTerm ontology = CvTermUtils.createPsiMiDatabase();
        CvTerm cvTermFetched =  fetcher.fetchCvTermByIdentifier(identifier, ontology);

        assertNotNull(cvTermFetched);
        assertEquals("electron acceptor" , cvTermFetched.getShortName());
        assertEquals(identifier , cvTermFetched.getMIIdentifier());
    }

    @Test
    public void test_getCvTermByExactName_using_MI_TermName_and_databaseName() throws BridgeFailedException {
        String term = "electron acceptor";
        String databaseName = "psi-mi";
        CvTerm cvTermFetched =  fetcher.fetchCvTermByName(term, databaseName);

        assertNotNull(cvTermFetched);
        assertEquals(term, cvTermFetched.getShortName());
        assertEquals("MI:0580" , cvTermFetched.getMIIdentifier());
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_getCvTermByExactName_using_MI_TermName_and_null_databaseName() throws BridgeFailedException {
        String term = "electron acceptor";
        String databaseName = null;
        CvTerm cvTermFetched =  fetcher.fetchCvTermByName(term, databaseName);

    }

    @Test
    public void test_getCvTermByExactName_using_MI_TermName() throws BridgeFailedException {
        String term = "allosteric change in dynamics";
        //String databaseName = null;
        Collection<CvTerm> cvTermFetched =  fetcher.fetchCvTermByName(term);

        assertEquals(1, cvTermFetched.size());
        assertEquals(term, cvTermFetched.iterator().next().getShortName());
        assertEquals("MI:1166" , cvTermFetched.iterator().next().getMIIdentifier());
    }

    @Test
    public void test_getCvTermByExactName_using_GO_TermName() throws BridgeFailedException {
        String term = "actin cortical patch localization";
        //String databaseName = null;
        Collection<CvTerm> cvTermFetched =  fetcher.fetchCvTermByName(term);

        assertEquals(1, cvTermFetched.size());
        assertEquals(term, cvTermFetched.iterator().next().getShortName());
        assertEquals("GO:0051666" , cvTermFetched.iterator().next().getIdentifiers().iterator().next().getId());
    }
}
