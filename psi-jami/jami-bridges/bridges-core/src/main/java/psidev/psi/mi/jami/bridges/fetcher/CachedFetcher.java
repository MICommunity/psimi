package psidev.psi.mi.jami.bridges.fetcher;


/**
 * An interface defining the basic interaction with the cache.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 08/07/13
 */
public interface CachedFetcher {

    /**
     * Initialises the cache with default settings.
     */
    public void initialiseCache();

    /**
     * Initialises the cache with the settings in the file at the location.
     *
     * @param settingsFile  The path of a file with the settings for an EHCache
     */
    public void initialiseCache(String settingsFile);

    /**
     * Clears the contents of the cache.
     */
    public void clearCache();

    /**
     * Shuts the cache down.
     */
    public void shutDownCache();
}
