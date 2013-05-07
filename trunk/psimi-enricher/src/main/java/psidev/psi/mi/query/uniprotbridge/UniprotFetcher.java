package psidev.psi.mi.query.uniprotbridge;

import psidev.psi.mi.exception.BridgeFailedException;
import psidev.psi.mi.exception.UnrecognizedCriteriaException;
import psidev.psi.mi.exception.UnrecognizedTermException;
import psidev.psi.mi.query.QueryObject;
import uk.ac.ebi.kraken.interfaces.uniprot.UniProtEntry;
import uk.ac.ebi.kraken.uuw.services.remoting.EntryRetrievalService;
import uk.ac.ebi.kraken.uuw.services.remoting.RemoteDataAccessException;
import uk.ac.ebi.kraken.uuw.services.remoting.UniProtJAPI;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 03/05/13
 * Time: 11:06
 */
public class UniprotFetcher {

    EntryRetrievalService entryRetrievalService;

    public UniprotFetcher(){
        entryRetrievalService = UniProtJAPI.factory.getEntryRetrievalService();
    }

    public UniProtEntry fetchEntryByID(String ID)
            throws UnrecognizedTermException, BridgeFailedException {

        try{
            UniProtEntry entry = (UniProtEntry) entryRetrievalService.getUniProtEntry(ID);

            if(entry == null) {
                throw new UnrecognizedTermException();
            }

            return entry;

            /*
            UniProtUtil u = new UniProtUtil();
            u.uniProtToJami(entry) ;

            switch(queryObject.getCriteria()){

                case SCIENTIFICNAME:     queryObject.setResult(entry.getOrganism().getScientificName().getValue());
                    return queryObject;

                case COMMONNAME:         queryObject.setResult(entry.getOrganism().getCommonName().getValue());
                    return queryObject;


                default:     throw new UnrecognizedCriteriaException();

            }   */
        }catch (RemoteDataAccessException e){
            throw new BridgeFailedException(e);
        }
    }
}
