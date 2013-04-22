package psidev.psi.mi.query;

import psidev.psi.mi.exception.*;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 16/04/13
 * Time: 11:16
 */
public class DefaultQueryInterface{
    //String source;
    //String term;

    public QueryObject passQuery(QueryObject queryObject)
    throws UnrecognizedTermException, BridgeFailedException, UnrecognizedDatabaseException {
        //Get a query
        //choose which database it is for
        //check cache
        Database dID = null;

        for (Database d : Database.values()){
            if(queryObject.getDatabase().equals(d)){
                dID = d;
                break;
            }
        }

        if(dID == null){
            throw new UnrecognizedDatabaseException();
        }

        switch(dID){
            case OLS:   queryOLS ols = new queryOLS();
                return ols.queryObject(queryObject);

            default:     throw new UnrecognizedDatabaseException();
        }

        /*
        }else if (source.equals("UP")){
            queryUniProt up = new queryUniProt();
            return up.queryTermID(term);
        }       */

    }

}