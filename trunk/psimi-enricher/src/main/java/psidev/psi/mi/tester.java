package psidev.psi.mi;

import psidev.psi.mi.exception.BridgeFailedException;
import psidev.psi.mi.exception.UnrecognizedCriteriaException;
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



    public void newQuery(String DB,String term, Criteria criteria){
        QueryObject o = new DefaultQueryObject(DB, term, criteria);
        newQuery(o);
    }

    public void newQuery(QueryObject o){
        System.out.println("SEARCH: " + o.getSearchTerm()+", IN: "+o.getDatabase()+", ON: "+o.getCriteria());

        try{
            DefaultQueryInterface q = new DefaultQueryInterface();
            o = q.passQuery(o);
            System.out.println("RESULT: "+o.getResult()) ;

        }catch(UnrecognizedTermException e) {
            System.out.println("oops - couldn't find " + o.getSearchTerm());

        } catch (BridgeFailedException e) {
            System.out.println("oops - couldn't access " + o.getSearchTerm());
            //e.printStackTrace();
        }catch (UnrecognizedDatabaseException e){
            System.out.println("oops - couldn't find a database "+o.getDatabase()+" for " + o.getSearchTerm());
            //e.printStackTrace();
        } catch (UnrecognizedCriteriaException e){
            System.out.println("oops - couldn't search on "+o.getCriteria()+" in " + o.getDatabase());
        }
    }
}
