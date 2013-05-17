package psidev.psi.mi.fetcher.ols;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 17/05/13
 * Time: 11:04
 */
public class OlsUtil {

    /**
     * Returns the ontology identifier.
     * Assumes that it is the first set of characters before  a semicolon.
     * Returns null if there is no semicolon or nothing before it.
     *
     * @param identifier
     * @return
     */
    public static String ontologyGetter(String identifier){
        if(identifier == null)return null;
        String[] dbsplit = identifier.split(":");
        if(dbsplit.length != 2) return null;
        if(dbsplit[0].length() == 0) return null;
        return dbsplit[0];
    }
}
