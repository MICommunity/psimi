package psidev.psi.mi.jami.bridges.fetcher;

import psidev.psi.mi.jami.bridges.exception.BridgeFailedException;
import psidev.psi.mi.jami.model.BioactiveEntity;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 07/08/13
 */
public interface BioactiveEntityFetcher {

    public BioactiveEntity getBioactiveEntityByIdentifier (String identifier)
            throws BridgeFailedException;


}
