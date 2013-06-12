package psidev.psi.mi.jami.enricher.integrationtest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import psidev.psi.mi.jami.bridges.fetcher.ProteinFetcher;
import psidev.psi.mi.jami.bridges.uniprot.UniprotFetcher;
import psidev.psi.mi.jami.enricher.ProteinEnricher;
import psidev.psi.mi.jami.enricher.enricherimplementation.protein.MaximumProteinUpdater;
import psidev.psi.mi.jami.enricher.enricherimplementation.protein.MinimumProteinEnricher;
import psidev.psi.mi.jami.enricher.enricherimplementation.protein.listener.ProteinEnricherListener;
import psidev.psi.mi.jami.enricher.enricherimplementation.protein.listener.ProteinEnricherLogger;
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

    ProteinEnricherListener listener = new ProteinEnricherLogger();


    public ProteinTest(){
        fetcher = new UniprotFetcher();
    }


    public void min(){
        proteinEnricher = new MinimumProteinEnricher(fetcher);
        proteinEnricher.setProteinEnricherListener(listener);
    }

    public void max(){
        proteinEnricher = new MaximumProteinUpdater(fetcher);
        proteinEnricher.setProteinEnricherListener(listener);
    }

    //String[] tests = {"P77681"};

    String[] tests = {"P12345", "P77681", "P11163",
            "P17671-2","Q6ZRI6-3", "P13055-2",
            "P42694","Q9Y2H6","PRO_0000015868","FOOBAR"};

    public void testProteins(){
        for(String s : tests){
            Protein a = new DefaultProtein(s);
            a.setUniprotkb(s);
            //a.setOrganism(new DefaultOrganism(168927));

            try{
                proteinEnricher.enrichProtein(a);
            } catch (Exception e){
                log.debug("The protein enricher did not return a protein");
                log.debug("msg reads: "+e.getMessage());
                //log.debug("log reads: "); e.printStackTrace();
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
