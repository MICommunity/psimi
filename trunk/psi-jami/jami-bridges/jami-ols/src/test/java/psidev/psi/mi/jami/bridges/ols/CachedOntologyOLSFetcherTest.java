package psidev.psi.mi.jami.bridges.ols;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import psidev.psi.mi.jami.bridges.exception.BridgeFailedException;
import psidev.psi.mi.jami.model.OntologyTerm;

import java.util.Collection;

import static junit.framework.Assert.assertNotNull;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 03/07/13
 */
public class CachedOntologyOLSFetcherTest {

    protected final Logger log = LoggerFactory.getLogger(CachedOntologyOLSFetcherTest.class.getName());

    CachedOntologyOLSFetcher ontologyOLSFetcher;// = new CachedOntologyOLSFetcher();

    @Before
    public void setup() throws BridgeFailedException {
        ontologyOLSFetcher = new CachedOntologyOLSFetcher();
    }

    @Test
    public void ParentsOfLeafChildren() throws BridgeFailedException {


        OntologyTerm result = ontologyOLSFetcher.getCvTermByIdentifier("MI:0113", "psi-mi");
        Collection<OntologyTerm> resultLeaves = ontologyOLSFetcher.findAllParentsOfDeepestChildren(result);
        log.info("First term: "+result.toString()+" has "+resultLeaves.size()+" leaves.");
        for(OntologyTerm leaf : resultLeaves){
            log.info("Leaf "+leaf.toString()+" :");
            listParents(leaf,"");
        }

        result = ontologyOLSFetcher.getCvTermByIdentifier("MI:0113", "psi-mi");
        resultLeaves = ontologyOLSFetcher.findAllParentsOfDeepestChildren(result);
        log.info("First term: "+result.toString()+" has "+resultLeaves.size()+" leaves.");
        for(OntologyTerm leaf : resultLeaves){
            log.info("Leaf "+leaf.toString()+" :");
            listParents(leaf,"");
        }


    //}
    /*
    @Test
    public void baseLineReadOut() throws BridgeFailedException { */
        String[] tests = {"MI:0100" , "MI:0077" , "MI:0113"};//nuclear magnetic resonance

        for(String test : tests){
            OntologyTerm ontologyTerm = ontologyOLSFetcher.getCvTermByIdentifier(test,"psi-mi",true,true);

            assertNotNull(ontologyTerm);

            log.info("First term: "+ontologyTerm.toString());

            listChildren(ontologyTerm  , "");
            listParents(ontologyTerm , "");
        }

        for(String test : tests){
            OntologyTerm ontologyTerm = ontologyOLSFetcher.getCvTermByIdentifier(test,"psi-mi",true,true);

            assertNotNull(ontologyTerm);

            log.info("First term: "+ontologyTerm.toString());

            listChildren(ontologyTerm  , "");
            listParents(ontologyTerm , "");
        }

        for(String test : tests){
            result = ontologyOLSFetcher.getCvTermByIdentifier(test , "psi-mi");

            resultLeaves = ontologyOLSFetcher.findAllParentsOfDeepestChildren(result);
            log.info("First term: "+result.toString()+" has "+resultLeaves.size()+" leaves.");
            for(OntologyTerm leaf : resultLeaves){
                log.info("Leaf "+leaf.toString()+" :");
                listParents(leaf,"");
            }
        }
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
