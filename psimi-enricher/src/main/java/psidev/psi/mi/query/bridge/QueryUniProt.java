package psidev.psi.mi.query.bridge;

/**
 * Created with IntelliJ IDEA.
 *
 * author: Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 12/04/13
 * Time: 14:14
 */
@Deprecated
public class QueryUniProt {

   /* public QueryObject queryOnObject(QueryObject queryObject)
            throws UnrecognizedTermException, UnrecognizedCriteriaException, BridgeFailedException{

        EntryRetrievalService entryRetrievalService = UniProtJAPI.factory.getEntryRetrievalService();

        try{
            UniProtEntry entry = (UniProtEntry) entryRetrievalService.getUniProtEntry(queryObject.getSearchTerm());

            if(entry == null) {
                throw new UnrecognizedTermException();
            }

            UniProtUtil u = new UniProtUtil();
            u.uniProtToJami(entry) ;

            switch(queryObject.getCriteria()){

                case SCIENTIFICNAME:     queryObject.setResult(entry.getOrganism().getScientificName().getValue());
                    return queryObject;

                case COMMONNAME:         queryObject.setResult(entry.getOrganism().getCommonName().getValue());
                    return queryObject;


                default:     throw new UnrecognizedCriteriaException();

            }
        }catch (RemoteDataAccessException e){
            throw new BridgeFailedException(e);
        }
    }  */
}
