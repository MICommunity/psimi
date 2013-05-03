package psidev.psi.mi.query.bridge;

import psidev.psi.mi.exception.BridgeFailedException;
import psidev.psi.mi.exception.UnrecognizedCriteriaException;
import psidev.psi.mi.exception.UnrecognizedTermException;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.query.QueryObject;
import psidev.psi.mi.query.bridge.ols.EnrichOLSCvTerm;
import uk.ac.ebi.ols.soap.Query;
import uk.ac.ebi.ols.soap.QueryServiceLocator;

import javax.xml.rpc.ServiceException;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

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