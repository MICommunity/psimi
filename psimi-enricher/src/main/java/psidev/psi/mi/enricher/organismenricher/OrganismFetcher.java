package psidev.psi.mi.enricher.organismenricher;

import psidev.psi.mi.fetcher.exception.BridgeFailedException;
import psidev.psi.mi.jami.model.Organism;
import psidev.psi.mi.jami.model.Protein;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 14/05/13
 * Time: 16:01
 */
public interface OrganismFetcher {
    public Organism getOrganismByID(String identifier)
            throws BridgeFailedException;

}
