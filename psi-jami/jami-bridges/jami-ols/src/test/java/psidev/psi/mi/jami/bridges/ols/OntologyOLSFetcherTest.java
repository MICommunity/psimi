package psidev.psi.mi.jami.bridges.ols;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import psidev.psi.mi.jami.bridges.exception.BridgeFailedException;
import psidev.psi.mi.jami.model.OntologyTerm;
import psidev.psi.mi.jami.model.Xref;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 03/07/13
 */
public class OntologyOLSFetcherTest {

    protected static final Logger log = LoggerFactory.getLogger(OntologyOLSFetcherTest.class.getName());

    OntologyOLSFetcher ontologyOLSFetcher;
    public static final String TEST_TERM_A_IDENTIFIER = "MI:0113";
    public static final String TEST_TERM_A_DBNAME = "psi-mi";
    public static final String TEST_TERM_A_SHORTNAME = "western blot";


    @Before
    public void setup() throws BridgeFailedException {
        ontologyOLSFetcher = new OntologyOLSFetcher();
    }


    @Test
    public void baseLineReadOut() throws BridgeFailedException {
        String[] tests = {"MI:0100" , "MI:0077" , "MI:0113"};//nuclear magnetic resonance
        for(String test : tests){
            OntologyTerm ontologyTerm = ontologyOLSFetcher.getCvTermByIdentifier(test,"psi-mi",0,0);
            assertNotNull(ontologyTerm);
            log.info("First term: "+ontologyTerm.toString());
            listChildren(ontologyTerm  , "");
            listParents(ontologyTerm , "");
        }
    }

    /**
     * Confirm that the Ontology term is correctly retrieved
     * @throws BridgeFailedException
     */
    @Test
    public void test_getCvTermByIdentifier_without_relations() throws BridgeFailedException {

        OntologyTerm result = ontologyOLSFetcher.getCvTermByIdentifier(
                TEST_TERM_A_IDENTIFIER , TEST_TERM_A_DBNAME);

        assertNotNull(result);
        assertEquals(TEST_TERM_A_SHORTNAME , result.getShortName());
        assertTrue(result.getIdentifiers().size() == 1);
        assertTrue(result.getIdentifiers().iterator().hasNext());
        assertEquals(TEST_TERM_A_IDENTIFIER , result.getIdentifiers().iterator().next().getId());

        assertTrue(result.getChildren().isEmpty());
        assertTrue(result.getParents().isEmpty());
    }

    /**
     * Confirm that the Ontology term is correctly retrieved
     * @throws BridgeFailedException
     */
    @Test
    public void test_getCvTermByIdentifier_with_children() throws BridgeFailedException {

        OntologyTerm result = ontologyOLSFetcher.getCvTermByIdentifier(
                TEST_TERM_A_IDENTIFIER , TEST_TERM_A_DBNAME , 0 , 0);

        assertNotNull(result);
        assertEquals(TEST_TERM_A_SHORTNAME , result.getShortName());
        assertTrue(result.getIdentifiers().size() == 1);
        assertTrue(result.getIdentifiers().iterator().hasNext());
        assertEquals(TEST_TERM_A_IDENTIFIER , result.getIdentifiers().iterator().next().getId());

        assertTrue(result.getChildren().size() > 0);
        assertTrue(result.getParents().isEmpty());
    }


    /**
     * Confirm that the Ontology term is correctly retrieved
     * @throws BridgeFailedException
     */
    //@Test
    public void test_getCvTermByIdentifier_with_parents() throws BridgeFailedException {

        OntologyTerm result = ontologyOLSFetcher.getCvTermByIdentifier(
                TEST_TERM_A_IDENTIFIER , TEST_TERM_A_DBNAME , 0 , 0);

        assertNotNull(result);
        assertEquals(TEST_TERM_A_SHORTNAME , result.getShortName());
        assertTrue(result.getIdentifiers().size() == 1);
        assertTrue(result.getIdentifiers().iterator().hasNext());
        assertEquals(TEST_TERM_A_IDENTIFIER , result.getIdentifiers().iterator().next().getId());

        assertTrue(result.getChildren().isEmpty());
        assertTrue(result.getParents().size() > 0);
    }



    public static void listChildren(OntologyTerm ontologyTerm , String path){
        if(ontologyTerm.getChildren().isEmpty())
            log.info(path+ontologyTerm);

        for(OntologyTerm child : ontologyTerm.getChildren()){
            listChildren(child , path+ontologyTerm+" <- ");
        }
    }

    public static void listParents(OntologyTerm ontologyTerm, String path){
        if(ontologyTerm.getParents().isEmpty())
            log.info(path+ontologyTerm);

        for(OntologyTerm parent : ontologyTerm.getParents()){
            listParents(parent , path+ontologyTerm+" -> ");
        }
    }
}