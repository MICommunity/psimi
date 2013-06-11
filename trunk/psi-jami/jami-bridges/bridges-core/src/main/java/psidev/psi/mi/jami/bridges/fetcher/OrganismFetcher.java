package psidev.psi.mi.jami.bridges.fetcher;

import psidev.psi.mi.jami.bridges.exception.FetcherException;
import psidev.psi.mi.jami.model.Organism;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 22/05/13
 * Time: 10:20
 */
public interface OrganismFetcher{
    public Organism getOrganismByTaxID(int taxID)
            throws FetcherException;

    public String getService();
}
