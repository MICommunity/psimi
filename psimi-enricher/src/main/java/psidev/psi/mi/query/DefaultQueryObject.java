package psidev.psi.mi.query;

/**
 *
 *
 * @author: Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 22/04/13
 * Time: 10:02
 */
public class DefaultQueryObject implements QueryObject{
    private String database;
    private String searchTerm;
    private Criteria searchCriteria;

    private String result;



    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public String getSearchTerm() {
        return searchTerm;
    }

    public void setSearchTerm(String searchTerm) {
        this.searchTerm = searchTerm;
    }

    public Criteria getSearchCriteria() {
        return searchCriteria;
    }

    public void setSearchCriteria(Criteria searchCriteria) {
        this.searchCriteria = searchCriteria;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }



}
