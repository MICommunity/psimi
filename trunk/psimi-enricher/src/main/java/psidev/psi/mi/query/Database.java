package psidev.psi.mi.query;

/**
 * An enum identifier to couple the names of database queries with the identifiers.
 * Some databases (i.e. OLS) have multiple identifiers that might be used (i.e. "MI","GO",etc).
 * When the
 *
 * @author: Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 22/04/13
 * Time: 10:14
 */
public enum Database {
    OLS ("MI","psi-mi"),
    UNIPROT ("uniprotkb","uniprot");

    String[] nameOptions;

    Database(String... nameOptions){
        this.nameOptions = nameOptions;
    }

    public boolean equals(String name){
        for(int i = 0; i<name.length(); i++){
            if(name.equalsIgnoreCase(this.nameOptions[i])){
                return true;
            }
        }
        return false;
    }
}
