package psidev.psi.mi.query.bridge;

import psidev.psi.mi.exception.*;
import psidev.psi.mi.query.QueryObject;
import uk.ac.ebi.ols.soap.QueryServiceLocator;

import javax.xml.rpc.ServiceException;
import java.rmi.RemoteException;

/**
 * Methods to query the Ontology Lookup Service.
 * Utilises the OLS soap service.
 *
 * @author: Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 12/04/13
 * Time: 14:07
 */
public class QueryOLS {

    /**
     *
     *
     * @param queryObject   carries all the parameters for the search and carries the result back.
     *                      The searchCriteria is ignored as there is only one OLS search.
     * @return
     * @throws UnrecognizedTermException
     * @throws BridgeFailedException
     */
    public QueryObject queryOnObject(QueryObject queryObject)
    throws UnrecognizedTermException, BridgeFailedException, UnrecognizedCriteriaException{
        String result = queryObject.getSearchTerm();

        try {
            //should the query service be disposable or reused?
            uk.ac.ebi.ols.soap.QueryService locator = new QueryServiceLocator();
            uk.ac.ebi.ols.soap.Query qs = null;

            qs = locator.getOntologyQuery();


            switch (queryObject.getSearchCriteria()){
                case TERM:
                    if(Character.isLetter(queryObject.getSearchTerm().charAt(0))){
                        //If begins with letter, the identifier is included
                        result = qs.getTermById(queryObject.getSearchTerm(),null);
                    }else{
                        //else begins with number, try including database
                        //Otherwise if just number, the search is run on NEWT not MI or GO
                        //This is not an adaptable solution - will not help if database was identified with e.g. psi-mi
                        result = qs.getTermById(queryObject.getDatabase()+":"+queryObject.getSearchTerm(),null);
                    }
                    break;

                default:   throw new UnrecognizedCriteriaException();
            }

        } catch (ServiceException e) {
            throw new BridgeFailedException(e);
        } catch (RemoteException e) {
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
