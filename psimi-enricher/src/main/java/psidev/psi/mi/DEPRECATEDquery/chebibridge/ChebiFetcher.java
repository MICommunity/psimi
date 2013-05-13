package psidev.psi.mi.DEPRECATEDquery.chebibridge;

import psidev.psi.mi.exception.BridgeFailedException;
import uk.ac.ebi.chebi.webapps.chebiWS.client.ChebiWebServiceClient;
import uk.ac.ebi.chebi.webapps.chebiWS.model.ChebiWebServiceFault_Exception;
import uk.ac.ebi.chebi.webapps.chebiWS.model.*;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 07/05/13
 * Time: 14:59
 */
public class ChebiFetcher {

    private ChebiWebServiceClient client;

    public ChebiFetcher(){
        client = new ChebiWebServiceClient();
    }


    public List<Entity> getEntityListByIDList (List<String> chebiID)
            throws BridgeFailedException{

        try {
            List<Entity> resultList = client.getCompleteEntityByList(chebiID);//getLiteEntity(chebiID, SearchCategory.CHEBI_ID, 10, StarsCategory.ALL);

            return resultList;
            /*for ( Entity entity : resultList ) {
                System.out.println("CHEBI ID: " + entity.getChebiId());
            }*/

        } catch ( ChebiWebServiceFault_Exception e ) {
            throw new BridgeFailedException(e);
        }
    }
}

