package psidev.psi.mi.jami.bridges.ols;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import psidev.psi.mi.jami.bridges.exception.BridgeFailedException;
import psidev.psi.mi.jami.model.OntologyTerm;
import psidev.psi.mi.jami.model.Xref;

import static junit.framework.Assert.assertNotNull;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 03/07/13
 */
public class OntologyOLSFetcherTest {

    protected final Logger log = LoggerFactory.getLogger(OntologyOLSFetcherTest.class.getName());

    OntologyOLSFetcher ontologyOLSFetcher;

    @Before
    public void setup() throws BridgeFailedException {
        ontologyOLSFetcher = new OntologyOLSFetcher();
    }

    @Test
    public void baseLineReadOut() throws BridgeFailedException {
        String[] tests = {"MI:0100" , "MI:0077" , "MI:0113"};//nuclear magnetic resonance

        for(String test : tests){
            OntologyTerm ontologyTerm = ontologyOLSFetcher.getCvTermByIdentifier(test,"psi-mi",true,true);

            assertNotNull(ontologyTerm);

            log.info("First term: "+ontologyTerm.toString());

            listChildren(ontologyTerm  , "");
            listParents(ontologyTerm , "");
        }

    }

    public void listChildren(OntologyTerm ontologyTerm , String path){

        if(ontologyTerm.getChildren().isEmpty())
            log.info(path+ontologyTerm.getFullName());

        for(OntologyTerm child : ontologyTerm.getChildren()){
            listChildren(child , path+ontologyTerm.getFullName()+" <- ");
        }
    }

    public void listParents(OntologyTerm ontologyTerm, String path){
        if(ontologyTerm.getParents().isEmpty())
            log.info(path+ontologyTerm.getFullName());

        for(OntologyTerm parent : ontologyTerm.getParents()){
            listParents(parent , path+ontologyTerm.getFullName()+" -> ");
        }
    }
}