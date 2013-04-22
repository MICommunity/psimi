package psidev.psi.mi.query;

/**
 * An enum identifier to couple the names of database queries with the identifiers.
 * Some databases (i.e. OLS) have multiple identifiers that might be used (i.e. "MI","GO",etc).
 *
 * Allowing the database to contain a variety of options allows for customisation that can be implemented later
 *
 * A database and its terms must be implemented in both the 'Database' Enums
 * and passQuery in the QueryInterface
 *
 * @author: Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 22/04/13
 * Time: 10:14
 */
public enum Database {
    OLS ("MI","psi-mi","GO"),
    UNIPROT ("uniprotkb","uniprot");

    String[] nameOptions;

    Database(String... nameOptions){
        this.nameOptions = nameOptions;
    }

    public boolean compareDB(String name){
        for(int i = 0; i<nameOptions.length; i++){
            if(name.equalsIgnoreCase(nameOptions[i])){
                return true;
            }
        }
        return false;
    }
}
