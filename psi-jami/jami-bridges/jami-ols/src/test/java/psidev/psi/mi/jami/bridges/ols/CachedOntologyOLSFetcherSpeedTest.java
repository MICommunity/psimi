package psidev.psi.mi.jami.bridges.ols;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import psidev.psi.mi.jami.bridges.exception.BridgeFailedException;
import psidev.psi.mi.jami.model.OntologyTerm;

import java.util.Collection;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 15/07/13
 */
public class CachedOntologyOLSFetcherSpeedTest {

    protected static  final Logger log = LoggerFactory.getLogger(CachedOntologyOLSFetcherSpeedTest.class.getName());

    private CachedOlsOntologyTermFetcher ontologyOLSFetcher;


    public CachedOntologyOLSFetcherSpeedTest() throws BridgeFailedException {
        ontologyOLSFetcher = new CachedOlsOntologyTermFetcher();
    }


    /**
     * Test that an ontology term is correctly retrieved with no parents or children.
     * @throws BridgeFailedException
     */

    public void test_speed() throws BridgeFailedException {

        log.warn("TEST");

        String[] featureTests = {"binding-associated region","sufficient binding region",
                //"mutation disrupting interaction rate","necessary binding region","sufficient binding region",
                "polyprotein fragment","amino-acid variant"};
        //ArrayList<Long> times = new ArrayList<Long>();

        //ArrayList<OntologyTerm> results = new ArrayList<OntologyTerm>();

        //times.add(System.currentTimeMillis());
        //log.info("Start time "+times.get(0));


        if(ontologyOLSFetcher == null) log.warn("null OLS!");
        else{
            long start =  System.currentTimeMillis();
            OntologyTerm term = ontologyOLSFetcher.fetchCvTermByName("biological feature", "psi-mi");
            long end = System.currentTimeMillis() ;
            log.info((end-start) + " was time for feature type");
            listChildren(term , "");
        }
        for(String name : featureTests){
            long start =  System.currentTimeMillis();
            OntologyTerm result = ontologyOLSFetcher.fetchCvTermByName(name, "psi-mi");
            long end = System.currentTimeMillis() ;
            log.info((end-start) + " was time for "+name);
        }
        for(String name : featureTests){
            long start =  System.currentTimeMillis();
            Collection<OntologyTerm> result = ontologyOLSFetcher.fetchCvTermByName(name );
            long end = System.currentTimeMillis() ;
            log.info((end-start) + " was time for "+name);
        }

        String[] additionalTests = {"cytolysis by host of symbiont cells" , "actin cortical patch localization" };

        for(String name : additionalTests){
            long start =  System.currentTimeMillis();
            Collection<OntologyTerm> result = ontologyOLSFetcher.fetchCvTermByName(name );
            long end = System.currentTimeMillis() ;
            log.info((end-start) + " was time for "+name);
        }

         /*
        for(int i = 0 ; i<times.size()-1 ; i++){
            int attempt = 0;
            if(i >= featureTests.length){
               attempt = 1;
            }
            Long time = times.get(i+1)-times.get(i);
            log.info(time + " for " + featureTests[i-(featureTests.length*attempt)]
                    + " on attempt " + attempt);

            listParents( results.get(i), "" );
            listChildren( results.get(i), "" );
        }  */


        //"feature type";


        ontologyOLSFetcher.shutDownCache();
    }


    public static  void listChildren(OntologyTerm ontologyTerm , String path){

        if(ontologyTerm.getChildren().isEmpty())
            log.info(path+ontologyTerm.toString());

        for(OntologyTerm child : ontologyTerm.getChildren()){
            listChildren(child , path+ontologyTerm.toString()+" <- ");
        }
    }

    public static void listParents(OntologyTerm ontologyTerm, String path){
        if(ontologyTerm.getParents().isEmpty())
            log.info(path+ontologyTerm.toString());

        for(OntologyTerm parent : ontologyTerm.getParents()){
            listParents(parent , path+ontologyTerm.toString()+" -> ");
        }
    }

    public static void main(String[] args)  {
        try {
            CachedOntologyOLSFetcherSpeedTest test = new CachedOntologyOLSFetcherSpeedTest();
            test.test_speed();
        } catch (BridgeFailedException e) {
            e.printStackTrace();
        }
    }
}
