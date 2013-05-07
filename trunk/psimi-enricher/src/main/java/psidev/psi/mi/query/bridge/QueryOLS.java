package psidev.psi.mi.query.bridge;

import uk.ac.ebi.ols.soap.Query;

/**
 * Methods to query the Ontology Lookup Service.
 * Utilises the OLS soap service.
 *
 *
 *
 * @author: Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 12/04/13
 * Time: 14:07
 */
@Deprecated
public class QueryOLS {
    boolean throwBadID = false;
    Query qs;

    /**
     * Opens the OLS query service. QS is null if it fails to initialise.
     *
     * @throws psidev.psi.mi.exception.BridgeFailedException    if the query service throws problems
     */
    /*public QueryOLS()
            throws BridgeFailedException {

        try{
            qs = new QueryServiceLocator().getOntologyQuery();

            /*HashMap map = qs.getOntologyNames();
            for(Object key : map.keySet()){
                System.out.println("DB "+map.get(key)+" for key " +key);
            } */
       /* }catch (ServiceException e) {
            qs = null;
            throw new BridgeFailedException("OLS QS bridge failed to initialise.",e);
        } catch(Exception e){}
    }

    /**
     * Opens the OLS query service. QS is null if it fails to initialise.
     * @param throwBadID      whether or not bad IDs are thrown as an error or ignored
     * @throws BridgeFailedException
     */
    /*public QueryOLS(boolean throwBadID)
            throws BridgeFailedException{

        this();
        this.throwBadID = throwBadID;
    }*/

    /*public CvTerm queryOnCvTerm(CvTerm cvTerm)
            throws UnrecognizedTermException, BridgeFailedException{

        EnrichOLSCvTerm enrich = new EnrichOLSCvTerm(qs);
        return enrich.queryOnCvTerm(cvTerm);
    } */
}