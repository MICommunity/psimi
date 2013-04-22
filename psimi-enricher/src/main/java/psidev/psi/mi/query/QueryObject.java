package psidev.psi.mi.query;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 22/04/13
 * Time: 10:01
 */
public interface QueryObject {

    public void initialiseQueryObject(String database, String searchTerm, String searchCriteria);

    public String getDatabase();
    public void setDatabase(String database);
    public String getSearchTerm();
    public void setSearchTerm(String searchTerm);

    public String getSearchCriteria();
    public void setSearchCriteria(String searchCriteria);

    public String setResult(String result);
    public String getResult();

}
