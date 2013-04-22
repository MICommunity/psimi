package psidev.psi.mi.query;

import psidev.psi.mi.exception.*;
import uk.ac.ebi.ols.soap.QueryServiceLocator;

/**
 * Methods to query the Ontology Lookup Service.
 * Utilises the OLS soap service.
 *
 * @author: Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 12/04/13
 * Time: 14:07
 */
public class queryOLS {

    /**
     *
     *
     * @param queryObject   carries all the parameters for the search and carries the result back.
     *                      The searchCriteria is ignored as there is only one OLS search.
     * @return
     * @throws UnrecognizedTermException
     * @throws BridgeFailedException
     */
    protected QueryObject queryObject(QueryObject queryObject) throws UnrecognizedTermException, BridgeFailedException{
        String result = queryObject.getSearchTerm();
        try{
            //should the query service be disposable or reused?
            uk.ac.ebi.ols.soap.QueryService locator = new QueryServiceLocator();
            uk.ac.ebi.ols.soap.Query qs = locator.getOntologyQuery();

            result = qs.getTermById(queryObject.getSearchTerm(),null);//Must be null or have the correct ontology id
        }catch (Exception e) {
            throw new BridgeFailedException(e);
        }
        //If no exceptions were caught, the query continues
        //Each API will show missing terms in separate ways - must check in each query
        if(result.equals(queryObject.getSearchTerm())){
            throw new UnrecognizedTermException();
        }
        else{
            queryObject.setResult(result);
            return queryObject;
        }
    }
}
