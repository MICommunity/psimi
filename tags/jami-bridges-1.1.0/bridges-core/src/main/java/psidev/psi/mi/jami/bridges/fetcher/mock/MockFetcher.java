package psidev.psi.mi.jami.bridges.fetcher.mock;


/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 07/08/13
 */
public interface MockFetcher <T> {

    /**
     * Adds an entry to the mock fetcher's internal fetching list with a given identifier as the key.
     * @param identifier    Identifier of the entry to add.
     * @param entry         Entry to add.
     */
    public void addEntry(String identifier, T entry);

    /**
     * Removes an entry from the
     * @param identifier    Identifier for the entry to be removed.
     */
    public void removeEntry(String identifier);

    /**
     * Clears all entries from the mock fetcher's internal fetching list.
     */
    public void clearEntries();
}
