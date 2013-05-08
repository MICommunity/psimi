package psidev.psi.mi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import psidev.psi.mi.query.psiolsbridge.PsiOlsEnricher;
import psidev.psi.mi.jami.model.CvTerm;



/**
 * Created with IntelliJ IDEA.
 *
 * @author: Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 15/04/13
 * Time: 12:15
 */
public class tester {
    /*private final Logger log = LoggerFactory.getLogger(PsiOlsEnricher.class.getName());
    PsiOlsEnricher eols;

    public tester(){
        eols = new PsiOlsEnricher();
    }

    public void testCVTerm(CvTerm cvTerm){

        //try{
            if(log.isDebugEnabled()){

                log.debug("----Entry begins----");
                log.debug("SEARCH by CVTERM: full ["+cvTerm.getFullName()+"] with short ["+cvTerm.getShortName()+"]");
                log.debug("SEARCH by CVTERM: with mi id ["+cvTerm.getMIIdentifier()+"] mod id ["+cvTerm.getMODIdentifier()+"]");}

            cvTerm = eols.queryOnCvTerm(cvTerm);

            if(log.isDebugEnabled()){
                log.debug("RESULT: fullname: "+cvTerm.getFullName());
                log.debug("RESULT: mi id ["+cvTerm.getMIIdentifier()+"] mod id ["+cvTerm.getMODIdentifier()+"]");
                log.debug("RESULT: shortname: "+cvTerm.getShortName());
            }

        //}/*catch(Exception e) {
           // System.out.println("foobar"+e.getMessage());
       // } */
    //}
}
