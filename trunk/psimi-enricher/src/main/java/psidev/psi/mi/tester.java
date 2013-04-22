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

    public void newQuery(String DB,String term){
        QueryObject o = new DefaultQueryObject();
        o.setDatabase(DB);
        o.setSearchTerm(term);

        try{


            DefaultQueryInterface q = new DefaultQueryInterface();
            o = q.passQuery(o);
            System.out.println(o.getResult()) ;

        }catch(UnrecognizedTermException e) {
            System.out.println("oops - couldn't find " + o.getSearchTerm());

        } catch (BridgeFailedException e) {
            System.out.println("oops - couldn't access " + o.getSearchTerm());
            //e.printStackTrace();
        }catch (UnrecognizedDatabaseException e){
            System.out.println("oops - couldn't find a database "+o.getDatabase()+" for " + o.getSearchTerm());
            //e.printStackTrace();
        }
    }



}
