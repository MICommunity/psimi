package psidev.psi.mi.query;

/**
 * A list of search types that could be applied to a database.
 * It is down to the individual bridge to implement the functions to pass to their database
 * If not recognized, a
 *
 * @author: Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 22/04/13
 * Time: 13:48
 */
@Deprecated
public enum Criteria {
    TERM, SCIENTIFICNAME, COMMONNAME, TAXID;
}
