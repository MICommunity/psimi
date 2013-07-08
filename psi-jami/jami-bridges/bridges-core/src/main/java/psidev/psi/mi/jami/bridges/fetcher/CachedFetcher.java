package psidev.psi.mi.jami.bridges.fetcher;

import net.sf.ehcache.Element;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 08/07/13
 */
public interface CachedFetcher {

    public void startCache();

    public void closeCache();

    public Object getFromCache( String key );

    public void storeInCache( String key, Object data );
}
