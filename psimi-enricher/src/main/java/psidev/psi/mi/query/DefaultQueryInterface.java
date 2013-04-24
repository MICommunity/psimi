package psidev.psi.mi.query;

import psidev.psi.mi.exception.*;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.impl.DefaultCvTerm;
import psidev.psi.mi.query.bridge.QueryOLS;
import psidev.psi.mi.query.bridge.QueryUniProt;

/**
 * An implementation of the QueryInterface. Takes the query object and passes it through to a relevant query.
 * Although it can accept an arbitrary QueryObject, it can only be searched on if the terms have been defined.
 *
 *
 * @author: Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 16/04/13
 * Time: 11:16
 */
public class DefaultQueryInterface implements QueryInterface{

    /**
     * A database and its terms must be implemented in both the 'Service' Enums
     * and passQuery in the QueryInterface
     *
     * @param queryObject
     * @return The original queryObject, updated with a result.
     * @throws UnrecognizedTermException
     * @throws BridgeFailedException
     * @throws UnrecognizedDatabaseException
     */
    public QueryObject passQuery(QueryObject queryObject)
    throws UnrecognizedTermException, BridgeFailedException, UnrecognizedDatabaseException, UnrecognizedCriteriaException {
        Service dID = null;

        for (Service d : Service.values()){
            if(d.compareDB(queryObject.getDatabase())){
                dID = d;
                break;
            }
        }

        if(dID == null){
            throw new UnrecognizedDatabaseException();
        }

        switch(dID){
            case OLS:       QueryOLS ols = new QueryOLS();
                    return ols.queryOnObject(queryObject);

            case UNIPROT:   QueryUniProt unp = new QueryUniProt();
                    return unp.queryOnObject(queryObject);

            default:     throw new UnrecognizedDatabaseException();
        }
    }


    public void foo(){
        String shortName = "foo";
        String miIdentifier = "bar";
        CvTerm bar = new DefaultCvTerm(shortName, miIdentifier);
    }

}