package psidev.psi.mi.jami.bridges.fetcher;

import psidev.psi.mi.jami.bridges.exception.BridgeFailedException;
import psidev.psi.mi.jami.model.BioactiveEntity;

import java.util.Collection;

/**
 * A fetcher for bioactiveEntities
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 07/08/13
 */
public interface BioactiveEntityFetcher {

    /**
     * Gets a bioactiveEntity by its identifier.
     * @param identifier    The identifier of the bioactiveEntity
     * @return  A complete bioactiveEntity record.
     * @throws BridgeFailedException
     */
    public BioactiveEntity fetchByIdentifier(String identifier)
            throws BridgeFailedException;


    public Collection<BioactiveEntity> fetchByIdentifiers(Collection<String> identifiers)
            throws BridgeFailedException;
}
