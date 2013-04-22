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
     * Uses OLS query search tools on the termID
     *
     *
     * @param termID
     * @return
     * @throws UnrecognizedTermException
     * @throws BridgeFailedException
     */
    protected String queryTermID(String termID) throws UnrecognizedTermException, BridgeFailedException{
        String term;
        try{
            //should the query service be disposable or reused?
            uk.ac.ebi.ols.soap.QueryService locator = new QueryServiceLocator();
            uk.ac.ebi.ols.soap.Query qs = locator.getOntologyQuery();

            term = qs.getTermById(termID,null);//Must be null or have the correct ontology id
        }catch (Exception e) {
            throw new BridgeFailedException(e);
        }
        //If no exceptions were caught, the query continues
        //Each API will show missing terms in separate ways - must check in each query
        if(term.equals(termID)){
            throw new UnrecognizedTermException();
        }
        else{
            return term;
        }
    }
}
