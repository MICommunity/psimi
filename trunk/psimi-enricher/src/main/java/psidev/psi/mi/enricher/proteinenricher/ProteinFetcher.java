package psidev.psi.mi.enricher.proteinenricher;

import psidev.psi.mi.fetcher.exception.BridgeFailedException;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Protein;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 14/05/13
 * Time: 13:50
 */
public interface ProteinFetcher {
    public Protein getProteinByID(String identifier)
            throws BridgeFailedException;

}
