package psidev.psi.mi.jami.bridges.fetcher;

import java.io.File;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 08/07/13
 */
public interface CachedFetcher {

    public void initialiseCache();

    public void initialiseCache(File settingsFile);

    public void initialiseCache(String settingsFile);

    public void clearCache();

    public void shutDownCache();
}
