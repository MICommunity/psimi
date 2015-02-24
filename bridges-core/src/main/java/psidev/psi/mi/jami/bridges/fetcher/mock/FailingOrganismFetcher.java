package psidev.psi.mi.jami.bridges.fetcher.mock;

import psidev.psi.mi.jami.bridges.exception.BridgeFailedException;
import psidev.psi.mi.jami.bridges.fetcher.OrganismFetcher;
import psidev.psi.mi.jami.model.Organism;

import java.util.*;

/**
 * A mock fetcher for testing exceptions.
 * It extends the functionality of the mock fetcher but can also throw exceptions.
 * Upon initialisation, an integer is given which sets how many times a query is made before returning the result.
 * If the current query matches the last query and the counter of the number of times is less than the maxQuery
 * set at initialisation, then an exception will be thrown.
 * Additionally, if the maxQuery is set to -1, the fetcher will always throw an exception.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 01/07/13
 */
public class FailingOrganismFetcher
        extends AbstractFailingFetcher<Organism>
        implements OrganismFetcher {

    public FailingOrganismFetcher(int maxQuery) {
        super(maxQuery);
    }

    public Organism fetchByTaxID(int taxID) throws BridgeFailedException {
        return getEntry( Integer.toString(taxID) );
    }

    public Collection<Organism> fetchByTaxIDs(Collection<Integer> taxIDs) throws BridgeFailedException {
        Collection<Organism> resultsList= new ArrayList<Organism>();
        for(Integer identifier : taxIDs){
            resultsList.add( getEntry(Integer.toString(identifier)) );
        }
        return resultsList;
    }
}
