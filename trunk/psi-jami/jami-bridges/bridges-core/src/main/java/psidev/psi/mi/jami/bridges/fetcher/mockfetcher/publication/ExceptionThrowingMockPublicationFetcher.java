package psidev.psi.mi.jami.bridges.fetcher.mockfetcher.publication;

import psidev.psi.mi.jami.bridges.exception.BridgeFailedException;
import psidev.psi.mi.jami.model.Publication;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 05/08/13
 */
public class ExceptionThrowingMockPublicationFetcher
        extends MockPublicationFetcher{

    String lastQuery = null;
    int count = 0;
    int maxQuery = 0;


    public ExceptionThrowingMockPublicationFetcher(int maxQuery){
        super();
        this.maxQuery = maxQuery;
    }

    public Publication getPublicationByPubmedID(String pubmedID) throws BridgeFailedException {
        if(pubmedID == null) throw new IllegalArgumentException(
                "Attempted to query mock protein fetcher for null identifier.");

        if(! localPublications.containsKey(""+pubmedID))  return null;
        else {
            if(! lastQuery.equals(""+pubmedID)){
                lastQuery = ""+pubmedID;
                count = 0;

            }

            if(maxQuery != -1 && count >= maxQuery)
                return localPublications.get(""+pubmedID);
            else {
                count++;
                throw new BridgeFailedException("Mock fetcher throws because this is the "+(count-1)+" attempt of "+maxQuery);
            }
        }
    }
}
