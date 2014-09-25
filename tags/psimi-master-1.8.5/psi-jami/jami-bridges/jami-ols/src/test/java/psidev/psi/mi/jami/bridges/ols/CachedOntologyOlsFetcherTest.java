package psidev.psi.mi.jami.bridges.ols;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import psidev.psi.mi.jami.bridges.exception.BridgeFailedException;
import psidev.psi.mi.jami.model.OntologyTerm;

import static junit.framework.Assert.*;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 03/07/13
 */
public class CachedOntologyOlsFetcherTest {

    protected final Logger log = LoggerFactory.getLogger(CachedOntologyOlsFetcherTest.class.getName());

    private CachedOlsOntologyTermFetcher ontologyOLSFetcher;

    public static final String TEST_TERM_A_IDENTIFIER = "MI:0113";
    public static final String TEST_TERM_A_DBNAME = "psi-mi";
    public static final String TEST_TERM_A_SHORTNAME = "western blot";
    public static final String TEST_TERM_B_IDENTIFIER = "MI:0661";
    public static final String TEST_TERM_B_DBNAME = "psi-mi";
    public static final String TEST_TERM_B_SHORTNAME = "experimental particp";
    public static final String TEST_TERM_B_FULLNAME = "experimental participant identification";

    @Before
    public void setup() throws BridgeFailedException {
        ontologyOLSFetcher = new CachedOlsOntologyTermFetcher();
    }

    @After
    public void tearDown(){
        ontologyOLSFetcher.shutDownCache();
    }


    /**
     * Test that an ontology term is correctly retrieved with no parents or children.
     * @throws BridgeFailedException
     */
    @Test
    public void test_getCvTermByIdentifier_without_relations() throws BridgeFailedException {
        OntologyTerm result = ontologyOLSFetcher.fetchByIdentifier(
                TEST_TERM_A_IDENTIFIER , TEST_TERM_A_DBNAME);

        assertNotNull(result);
        assertEquals(TEST_TERM_A_SHORTNAME , result.getShortName());
        assertTrue(result.getIdentifiers().size() == 1);
        assertTrue(result.getIdentifiers().iterator().hasNext());
        assertEquals(TEST_TERM_A_IDENTIFIER , result.getIdentifiers().iterator().next().getId());

        assertFalse(result.getChildren().isEmpty());
        assertFalse(result.getParents().isEmpty());
    }


    /**
     * Confirm that the Ontology term is correctly retrieved
     * @throws BridgeFailedException
     */
    @Test
    public void test_getCvTermByExactName_without_relations() throws BridgeFailedException {
        OntologyTerm result = ontologyOLSFetcher.fetchByName(TEST_TERM_A_SHORTNAME , TEST_TERM_A_DBNAME);

        assertNotNull(result);
        assertEquals(TEST_TERM_A_SHORTNAME , result.getShortName());
        assertTrue(result.getIdentifiers().size() == 1);
        assertTrue(result.getIdentifiers().iterator().hasNext());
        assertEquals(TEST_TERM_A_IDENTIFIER , result.getIdentifiers().iterator().next().getId());

        assertFalse(result.getChildren().isEmpty());
        assertFalse(result.getParents().isEmpty());
    }

    /**
    */
    public void listChildren(OntologyTerm ontologyTerm , String path){

        if(ontologyTerm.getChildren().isEmpty())
            log.info(path+ontologyTerm.toString());

        for(OntologyTerm child : ontologyTerm.getChildren()){
            listChildren(child , path+ontologyTerm.toString()+" <- ");
        }
    }

    public void listParents(OntologyTerm ontologyTerm, String path){
        if(ontologyTerm.getParents().isEmpty())
            log.info(path+ontologyTerm.toString());

        for(OntologyTerm parent : ontologyTerm.getParents()){
            listParents(parent , path+ontologyTerm.toString()+" -> ");
        }
    }
}
