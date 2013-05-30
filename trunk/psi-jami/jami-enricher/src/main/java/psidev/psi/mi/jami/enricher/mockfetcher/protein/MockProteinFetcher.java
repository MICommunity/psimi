package psidev.psi.mi.jami.enricher.mockfetcher.protein;

import psidev.psi.mi.jami.bridges.exception.EntryNotFoundException;
import psidev.psi.mi.jami.bridges.exception.FetcherException;
import psidev.psi.mi.jami.bridges.fetcher.ProteinFetcher;
import psidev.psi.mi.jami.model.Protein;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * TODO comment this
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>23/05/13</pre>
 */

public class MockProteinFetcher
        implements ProteinFetcher {

    private Map<String, Protein> localProteins;

    public MockProteinFetcher(){
        localProteins = new HashMap<String, Protein>();
    }

    public Collection<Protein> getProteinsByID(String identifier) throws FetcherException {
        if(! localProteins.containsKey(identifier)) {
            throw new EntryNotFoundException(
                    "No protein to repeat with ID ["+identifier+"]");
        }

        else {
            ArrayList<Protein> proteins = new ArrayList<Protein>();
            proteins.add(localProteins.get(identifier));
            return proteins;
        }
    }

    public void addNewProtein(String uniprot, Protein protein){
        if(protein == null) return;
        localProteins.put(uniprot, protein);
    }

    public void clearProteins(){
        localProteins.clear();
    }

    public String getService() {
        return "Mock Protein Fetcher";
    }
}
