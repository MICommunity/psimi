package psidev.psi.mi.query;

import psidev.psi.mi.exception.UnrecognizedTermException;
import uk.ac.ebi.kraken.interfaces.uniprot.UniProtEntry;
import uk.ac.ebi.kraken.uuw.services.remoting.EntryRetrievalService;
import uk.ac.ebi.kraken.uuw.services.remoting.UniProtJAPI;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 12/04/13
 * Time: 14:14
 */
public class queryUniProt {

    public String queryTermID(String term) throws UnrecognizedTermException{
        EntryRetrievalService entryRetrievalService = UniProtJAPI.factory.getEntryRetrievalService();
        UniProtEntry entry = (UniProtEntry) entryRetrievalService.getUniProtEntry(term);

        if(entry == null) {
            throw new UnrecognizedTermException();
        }

        System.out.println("entry = " + entry);
        return entry.getUniProtId().getValue();
    }

    protected void test2() throws UnrecognizedTermException{

        String test = "Q15942" ;// "O95835"; //
        //Create entry retrieval service
        EntryRetrievalService entryRetrievalService = UniProtJAPI.factory.getEntryRetrievalService();

        //Retrieve UniProt entry by its accession number
        UniProtEntry entry = (UniProtEntry) entryRetrievalService.getUniProtEntry(test);

        System.out.println("entry = " + entry);

        //If entry with a given accession number is not found, entry will be equal null




        if (entry != null) {
            System.out.println("entry = " + entry.getUniProtId().getValue());
            System.out.println("entry recommended names = " + entry.getProteinDescription().getRecommendedName().getFields());
            System.out.println("entry ec numbers = " + entry.getProteinDescription().getEcNumbers());
        }
    }

}
