package psidev.psi.mi.enricher.cvtermenricher;

import psidev.psi.mi.exception.BridgeFailedException;
import psidev.psi.mi.jami.model.CvTerm;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 08/05/13
 * Time: 16:34
 */
public interface CvTermFetcher {

    public CvTerm getCVTermByID(String identifier, String dbname)
            throws BridgeFailedException;

    public CvTerm getCVTermByName(String name, String dbname)
            throws BridgeFailedException;

}
