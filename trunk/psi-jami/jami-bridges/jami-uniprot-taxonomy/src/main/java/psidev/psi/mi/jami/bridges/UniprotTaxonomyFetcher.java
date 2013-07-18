package psidev.psi.mi.jami.bridges;

import psidev.psi.mi.jami.bridges.exception.BridgeFailedException;
import psidev.psi.mi.jami.bridges.fetcher.OrganismFetcher;
import psidev.psi.mi.jami.model.Organism;

import java.util.Collection;
import java.util.Collections;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 28/06/13
 */
public class UniprotTaxonomyFetcher implements OrganismFetcher {


    public Organism getOrganismByTaxID(int taxID) throws BridgeFailedException {
        return null;
    }

    public Collection<Organism> getOrganismsByTaxIDs(Collection<Integer> taxIDs) throws BridgeFailedException {
        return Collections.EMPTY_LIST;
    }
}
