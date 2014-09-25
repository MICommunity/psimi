package psidev.psi.mi.tab.model;

/**
 * Denotes an object that holds a cache on which this given set of operation is available.
 *
 * @author Samuel Kerrien
 * @version $Id$
 * @since 1.0-rc1
 */
public interface isCache {

    /**
     * Resets the cache.
     */
    public void resetCache();

    /**
     * answer the question: "is the cache enabled ?".
     *
     * @return true of the cache is enabled.
     */
    public boolean isCacheEnabled();

    /**
     * Enable or disable the cache according to the given parameter.
     * @param enabled
     */
    public void setCacheEnabled( boolean enabled );
}
