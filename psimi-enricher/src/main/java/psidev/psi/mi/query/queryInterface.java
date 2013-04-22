package psidev.psi.mi.query;

import psidev.psi.mi.exception.*;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 16/04/13
 * Time: 11:16
 */
public class queryInterface{
    //String source;
    //String term;

    public String passQuery(String source, String term)
    throws UnrecognizedTermException, BridgeFailedException, UnrecognizedDatabaseException {
        //Get a query
        //choose which database it is for
        //check cache

        if(source.equals("MI")){
            queryOLS ols = new queryOLS();
            return ols.queryTermID(term);
        }else if (source.equals("UP")){
            queryUniProt up = new queryUniProt();
            return up.queryTermID(term);
        }
        throw new UnrecognizedDatabaseException();
    }
}