package psidev.psi.mi.jami.enricher.integrationtest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import psidev.psi.mi.jami.bridges.fetcher.ProteinFetcher;
import psidev.psi.mi.jami.bridges.fetcher.mockfetcher.protein.MockProteinFetcher;
import psidev.psi.mi.jami.enricher.ParticipantEnricher;
import psidev.psi.mi.jami.enricher.impl.organism.listener.OrganismEnricherLogger;
import psidev.psi.mi.jami.enricher.impl.participant.ParticipantEnricherMaximum;
import psidev.psi.mi.jami.enricher.impl.participant.ParticipantEnricherMinimum;
import psidev.psi.mi.jami.enricher.impl.protein.listener.ProteinEnricherListener;
import psidev.psi.mi.jami.enricher.impl.protein.listener.ProteinEnricherLogger;
import psidev.psi.mi.jami.model.ParticipantEvidence;
import psidev.psi.mi.jami.model.Protein;
import psidev.psi.mi.jami.model.impl.DefaultParticipantEvidence;
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

    ParticipantEnricher participantEnricher;
    ProteinFetcher fetcher;
    //ProteinRemapper remapper = new UniprotProteinRemapper();

    ProteinEnricherListener listener = new ProteinEnricherLogger();


    public ProteinTest(){
        fetcher = new MockProteinFetcher();
    }


    public void min(){
        participantEnricher = new ParticipantEnricherMinimum();
        participantEnricher.getProteinEnricher().setProteinFetcher(fetcher);
        //participantEnricher.getProteinEnricher().setProteinRemapper(remapper);
        participantEnricher.getProteinEnricher().setProteinEnricherListener(listener);
        participantEnricher.getProteinEnricher().getOrganismEnricher().setOrganismEnricherListener(new OrganismEnricherLogger());
    }

    public void max(){
        participantEnricher = new ParticipantEnricherMaximum();
        participantEnricher.getProteinEnricher().setProteinFetcher(fetcher);
        //participantEnricher.getProteinEnricher().setProteinRemapper(remapper);
        participantEnricher.getProteinEnricher().setProteinEnricherListener(listener);
        participantEnricher.getProteinEnricher().getOrganismEnricher().setOrganismEnricherListener(new OrganismEnricherLogger());
    }

    //String[] tests = {"P77681"};

    String[] tests = {"P12345", "P77681", "P11163",
            "P17671-2","Q6ZRI6-3", "P13055-2",
            "P42694","Q9Y2H6","PRO_0000015868","FOOBAR"};

    public void testProteins(){
        for(String s : tests){
            log.info("---begin---");
            Protein protein = new DefaultProtein(s);
            protein.setUniprotkb(s);
            ParticipantEvidence participant = new DefaultParticipantEvidence(protein);


            //a.setOrganism(new DefaultOrganism(168927));


            try{
                participantEnricher.enrichParticipant(participant);
            } catch (Exception e){
                log.debug("The protein enricher threw an exception.");
                log.debug("msg reads: "+e.getMessage());
                e.printStackTrace();
                //log.debug("log reads: "); e.printStackTrace();
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("---END---");

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
