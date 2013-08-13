package psidev.psi.mi.jami.bridges.fetcher.mock;


/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 07/08/13
 */
public interface MockFetcher <T> {

    public void addEntry(String identifier, T entry);

    public void removeEntry(String identifier);

    public void clearEntries();
}
