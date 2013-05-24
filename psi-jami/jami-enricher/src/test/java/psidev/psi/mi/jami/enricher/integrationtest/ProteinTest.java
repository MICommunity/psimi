package psidev.psi.mi.jami.enricher.integrationtest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import psidev.psi.mi.jami.bridges.exception.FetcherException;
import psidev.psi.mi.jami.bridges.fetcher.ProteinFetcher;
import psidev.psi.mi.jami.bridges.uniprot.UniprotFetcher;
import psidev.psi.mi.jami.enricher.ProteinEnricher;
import psidev.psi.mi.jami.enricher.exception.EnrichmentException;
import psidev.psi.mi.jami.enricher.listener.LoggingEnricherListener;
import psidev.psi.mi.jami.enricher.protein.MaximumProteinUpdater;
import psidev.psi.mi.jami.enricher.protein.MinimumProteinEnricher;
import psidev.psi.mi.jami.model.Protein;
import psidev.psi.mi.jami.model.impl.DefaultProtein;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 16/05/13
 * Time: 13:21
 */
public class ProteinTest {

    private final Logger log = LoggerFactory.getLogger(ProteinTest.class.getName());

    ProteinEnricher proteinEnricher;
    ProteinFetcher fetcher;


    public ProteinTest(){
        try {
            fetcher = new UniprotFetcher();
        } catch (FetcherException e) {
            log.debug("the protein fetcher did not initialise");
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }


    public void min(){
        //try{
        proteinEnricher = new MinimumProteinEnricher(fetcher);
        proteinEnricher.addEnricherListener(new LoggingEnricherListener());

        //} catch (Exception e){
            //log.debug("the protein enricher did not initialise");
            //e.printStackTrace();
       // }
    }

    public void max(){
        proteinEnricher = new MaximumProteinUpdater(fetcher);
        proteinEnricher.addEnricherListener(new LoggingEnricherListener());

    }


    String[] tests = {"P42694","Q9Y2H6","FOOBAR"};

    public void testProteins(){
        for(String s : tests){
            Protein a = new DefaultProtein(s);
            a.setUniprotkb(s);

            try{
                proteinEnricher.enrichProtein(a);
            } catch (EnrichmentException e){
                log.debug("The protein enricher did not return a protein");
                log.debug("msg reads: "+e.getMessage());
                log.debug("log reads: ");
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args){
        ProteinTest pt = new ProteinTest();
        //pt.min();
        //pt.testProteins();
        pt.max();
        pt.testProteins();
    }
}
