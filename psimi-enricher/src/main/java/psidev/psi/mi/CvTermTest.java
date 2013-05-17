package psidev.psi.mi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import psidev.psi.mi.enricher.cvtermenricher.CvTermEnricher;
import psidev.psi.mi.enricher.cvtermenricher.MinimumCvTermEnricher;
import psidev.psi.mi.enricher.proteinenricher.MinimumProteinEnricher;
import psidev.psi.mi.enricher.proteinenricher.ProteinEnricher;
import psidev.psi.mi.enricherlistener.EnricherListenerLog;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Protein;
import psidev.psi.mi.jami.model.impl.DefaultProtein;
import psidev.psi.mi.jami.utils.factory.CvTermFactory;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 16/05/13
 * Time: 13:32
 */
public class CvTermTest {




    private final Logger log = LoggerFactory.getLogger(ProteinTest.class.getName());

    CvTermEnricher cvTermEnricher;

    public CvTermTest(){
        try{
            cvTermEnricher = new MinimumCvTermEnricher();
            cvTermEnricher.addEnricherListener(new EnricherListenerLog());

        } catch (Exception e){
            log.debug("the cv enricher did not initialise");
            e.printStackTrace();
        }
    }

    public void testModCV(){

        String[][] tests = {
                {"bob","GO:0071840"},
                {"dehydromethionine","MOD:01906"},
                {"dehydromethionine",null},
                {"dehydromethion",null},
                {"stuff","MOD:00698"} };

        for(String[] s : tests){
            CvTerm a = CvTermFactory.createMODCvTerm(s[0],s[1]);

            try{
                cvTermEnricher.enrichCvTerm(a);
            } catch (Exception e){
                log.debug("The cv enricher did not return cv a");
                e.printStackTrace();
            }
        }
    }


    public void testMiCV(){

        String[][] tests = {
                {"allosteric change in dynamics","MOD:01906"},
                {"allosteric change in dynamics","MI:1166" } ,
                {"allosteric change in dynamics","MI:1166" } ,
                {"allosteric change in dynamics",null } ,
                {"allosteric change in dynam",null},
                {"osteric change in dynam",null},
                {"allosteric",null},
                {"0915", "MI:0915"},
                {"0915", null}};

        for(String[] s : tests){
            CvTerm a = CvTermFactory.createMICvTerm(s[0],s[1]);

            try{
                cvTermEnricher.enrichCvTerm(a);
            } catch (Exception e){
                log.debug("The cv enricher did not return cv a");
                log.debug(e.getMessage());
            }
        }
    }
}
