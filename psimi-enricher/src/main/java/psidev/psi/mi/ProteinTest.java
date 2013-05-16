package psidev.psi.mi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import psidev.psi.mi.enricher.proteinenricher.MinimumProteinEnricher;
import psidev.psi.mi.enricher.proteinenricher.ProteinEnricher;
import psidev.psi.mi.enricherlistener.EnricherListenerLog;
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

    public ProteinTest(){
        try{
            proteinEnricher = new MinimumProteinEnricher();
            proteinEnricher.addEnricherListener(new EnricherListenerLog());

        } catch (Exception e){
            log.debug("the protein enricher did not initialise");
            e.printStackTrace();
        }
    }

    public void testProteins(){

        String[] tests = {"P42694","Q9Y2H6"};

        for(String s : tests){
            Protein a = new DefaultProtein(s);
            a.setUniprotkb(s);

            try{
                proteinEnricher.enrichProtein(a);
            } catch (Exception e){
                log.debug("The protein enricher did not return protein a");
                e.printStackTrace();
            }
        }
    }

}
