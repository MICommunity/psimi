package psidev.psi.mi.jami.enricher.mock.protein;

import psidev.psi.mi.jami.bridges.exception.FetcherException;
import psidev.psi.mi.jami.bridges.fetcher.ProteinFetcher;
import psidev.psi.mi.jami.model.Protein;

import java.util.HashMap;
import java.util.Map;

/**
 * TODO comment this
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>23/05/13</pre>
 */

public class MockProteinFetcher implements ProteinFetcher {

    private Map<String, Protein> localProteins;

    public MockProteinFetcher(){
        localProteins = new HashMap<String, Protein>();
    }

    public Protein getProteinByID(String identifier) throws FetcherException {

        return localProteins.get(identifier);
    }

    public void addNewProtein(String uniprot, Protein protein){
        localProteins.put(uniprot, protein);
    }

    public void clearProteins(){
        localProteins.clear();
    }
}
