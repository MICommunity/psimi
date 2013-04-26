package psidev.psi.mi;

import psidev.psi.mi.exception.BridgeFailedException;
import psidev.psi.mi.exception.UnrecognizedCriteriaException;
import psidev.psi.mi.exception.UnrecognizedDatabaseException;
import psidev.psi.mi.exception.UnrecognizedTermException;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.utils.factory.CvTermFactory;
import psidev.psi.mi.query.*;
import psidev.psi.mi.query.bridge.QueryOLS;
import uk.ac.ebi.ols.soap.Query;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 15/04/13
 * Time: 12:15
 */
public class tester {

    QueryOLS qol;
    public tester(){
        try {
            qol = new QueryOLS();
        } catch (BridgeFailedException e) {
            System.out.println(e.getMessage());
        }

    }

    public void testCVTerm(CvTerm cvTerm){

        try{
            System.out.println("----\nSEARCH by GEN: full ["+cvTerm.getFullName()+"] with short ["+cvTerm.getShortName()+"]"+
                    "\nwith mi id ["+cvTerm.getMIIdentifier()+"] mod id ["+cvTerm.getMODIdentifier()+"]");
            cvTerm = qol.queryOnCvTerm(cvTerm);
            System.out.println("\nRESULT: \nfullname: "+cvTerm.getFullName());
            System.out.println("mi id ["+cvTerm.getMIIdentifier()+"] mod id ["+cvTerm.getMODIdentifier()+"]");
            System.out.println("shortname: "+cvTerm.getShortName());


        }catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
