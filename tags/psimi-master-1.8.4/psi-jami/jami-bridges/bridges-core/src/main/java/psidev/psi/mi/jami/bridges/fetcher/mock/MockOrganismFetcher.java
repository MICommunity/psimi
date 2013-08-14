package psidev.psi.mi.jami.bridges.fetcher.mock;


import psidev.psi.mi.jami.bridges.exception.BridgeFailedException;
import psidev.psi.mi.jami.bridges.fetcher.OrganismFetcher;
import psidev.psi.mi.jami.bridges.fetcher.mock.AbstractMockFetcher;
import psidev.psi.mi.jami.model.Organism;

import java.util.*;

/**
 * A mock fetcher for testing.
 * It extends all the methods of the true fetcher, but does not access an external service.
 * Instead, it holds a map which can be loaded with objects and keys. which are then returned.
 * It attempts to replicate the responses of the fetcher in most scenarios.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since  24/05/13
 */
public class MockOrganismFetcher
        extends AbstractMockFetcher<Organism>
        implements OrganismFetcher {

    public Organism getOrganismByTaxID(int taxID) throws BridgeFailedException {
        return getEntry( Integer.toString(taxID) );
    }

    public Collection<Organism> getOrganismsByTaxIDs(Collection<Integer> taxIDs) throws BridgeFailedException {
        ArrayList<Organism> resultsList= new ArrayList<Organism>();
        for(Integer identifier : taxIDs){
            resultsList.add( getEntry(Integer.toString(identifier)) );
        }
        return resultsList;
    }
}
