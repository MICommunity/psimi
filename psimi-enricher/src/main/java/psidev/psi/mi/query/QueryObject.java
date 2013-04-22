package psidev.psi.mi.query;

/**
 * A query object without a database or searchterm sill not work.
 */
public interface QueryObject {

   // public void initialiseQueryObject(String database, String searchTerm, String searchCriteria);

    public String getDatabase();
    public void setDatabase(String database);
    public String getSearchTerm();
    public void setSearchTerm(String searchTerm);

    public Criteria getSearchCriteria();
    public void setSearchCriteria(Criteria searchCriteria);

    public void setResult(String result);
    public String getResult();

}
