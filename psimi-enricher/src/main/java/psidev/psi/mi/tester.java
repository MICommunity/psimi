package psidev.psi.mi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import psidev.psi.mi.enrichment.EnrichOLS;
import psidev.psi.mi.exception.BridgeFailedException;
import psidev.psi.mi.jami.model.CvTerm;

import psidev.psi.mi.query.bridge.QueryOLS;


/**
 * Created with IntelliJ IDEA.
 *
 * @author: Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 15/04/13
 * Time: 12:15
 */
public class tester {
    private final Logger log = LoggerFactory.getLogger(EnrichOLS.class.getName());
    EnrichOLS eols;

    public tester(){
        eols = new EnrichOLS();
    }

    public void testCVTerm(CvTerm cvTerm){

        //try{
            if(log.isDebugEnabled()){log.debug("----\nSEARCH by GEN: full ["+cvTerm.getFullName()+"] with short ["+cvTerm.getShortName()+"]"+
                    "\nwith mi id ["+cvTerm.getMIIdentifier()+"] mod id ["+cvTerm.getMODIdentifier()+"]");}

            cvTerm = eols.queryOnCvTerm(cvTerm);

            if(log.isDebugEnabled()){log.debug("\nRESULT: \nfullname: "+cvTerm.getFullName());}
            if(log.isDebugEnabled()){log.debug("mi id ["+cvTerm.getMIIdentifier()+"] mod id ["+cvTerm.getMODIdentifier()+"]");}
            if(log.isDebugEnabled()){log.debug("shortname: "+cvTerm.getShortName());}

        //}/*catch(Exception e) {
           // System.out.println("foobar"+e.getMessage());
       // } */
    }
}
