package psidev.psi.mi;

import psidev.psi.mi.exception.BridgeFailedException;
import psidev.psi.mi.exception.UnrecognizedDatabaseException;
import psidev.psi.mi.exception.UnrecognizedTermException;
import psidev.psi.mi.query.*;
/**
 * Created with IntelliJ IDEA.
 *
 * @author: Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 15/04/13
 * Time: 12:15
 */
public class tester {

    public void log(String DB,String termID){
        String x;

        try{
            queryInterface q = new queryInterface();
            x = q.passQuery(DB, termID);
            System.out.println(x) ;
        }catch(UnrecognizedTermException e) {
            System.out.println("oops - couldn't find " + termID);

        } catch (BridgeFailedException e) {
            System.out.println("oops - couldn't access " + termID);
            //e.printStackTrace();
        }catch (UnrecognizedDatabaseException e){
            System.out.println("oops - couldn't find a database "+DB+" for " + termID);
            //e.printStackTrace();
        }
    }



}
