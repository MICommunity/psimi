package psidev.psi.mi.jami.bridges.fetcher.mockfetcher.organism;

import psidev.psi.mi.jami.bridges.exception.BridgeFailedException;
import psidev.psi.mi.jami.bridges.fetcher.CvTermFetcher;
import psidev.psi.mi.jami.bridges.fetcher.OrganismFetcher;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Organism;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 01/07/13
 */
public class ExceptionThrowingMockOrganismFetcher
        extends MockOrganismFetcher
        implements OrganismFetcher {

    String lastQuery = null;
    int count = 0;
    int maxQuery = 0;


    public ExceptionThrowingMockOrganismFetcher(int maxQuery){
        super();
        this.maxQuery = maxQuery;
    }

    @Override
    public Organism getOrganismByTaxID(int identifier) throws BridgeFailedException {
        if(! localOrganisms.containsKey( Integer.toString(identifier) ))  return null;
        else {
            if(! lastQuery.equals( Integer.toString(identifier) )){
                lastQuery = Integer.toString(identifier) ;
                count = 0;

            }

            if(maxQuery != -1 && count >= maxQuery)
                return localOrganisms.get( Integer.toString(identifier) );
            else {
                count++;
                throw new BridgeFailedException("Mock fetcher throws because this is the "+(count-1)+" attempt of "+maxQuery);
            }
        }
    }
}
