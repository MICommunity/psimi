package psidev.psi.mi.jami.bridges.fetcher;

import psidev.psi.mi.jami.bridges.exception.BridgeFailedException;

/**
 * A fetcher that fetches a sequence from a specific version and/or a version from a specific sequence
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>30/09/13</pre>
 */

public interface SequenceVersionFetcher {

    public String fetchSequenceFromVersion(String id, int version) throws BridgeFailedException;

    public int fetchVersionFromSequence(String id, String sequence) throws BridgeFailedException;
}
