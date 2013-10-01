package psidev.psi.mi.jami.bridges.unisave;

import psidev.psi.mi.jami.bridges.exception.BridgeFailedException;
import psidev.psi.mi.jami.bridges.fetcher.AbstractCachedFetcher;
import psidev.psi.mi.jami.bridges.fetcher.SequenceVersionFetcher;

/**
 * Cached unisave client
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>01/10/13</pre>
 */

public class CachedUnisaveClient extends AbstractCachedFetcher implements SequenceVersionFetcher {

    public static final String CACHE_NAME = "unisave-service-cache";
    private SequenceVersionFetcher sequenceFetcher;

    public CachedUnisaveClient() throws BridgeFailedException {
        super(CACHE_NAME);
        this.sequenceFetcher = new UnisaveClient();
    }

    public String fetchSequenceFromVersion(String id, int version) throws BridgeFailedException {
        final String key = "GET_SEQUENCE_"+id+"_VERSION_"+version;
        Object data = getFromCache( key );
        if( data == null) {
            data = sequenceFetcher.fetchSequenceFromVersion(id, version);
            storeInCache(key , data);
        }
        return (String)data;
    }

    public int fetchVersionFromSequence(String id, String sequence) throws BridgeFailedException {
        final String key = "GET_VERSION_"+id+"_SEQUENCE_"+sequence;
        Object data = getFromCache( key );
        if( data == null) {
            data = sequenceFetcher.fetchVersionFromSequence(id, sequence);
            storeInCache(key , data);
        }
        return (Integer)data;
    }
}
