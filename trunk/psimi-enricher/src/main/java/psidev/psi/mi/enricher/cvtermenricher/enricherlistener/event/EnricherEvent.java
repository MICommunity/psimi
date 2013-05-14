package psidev.psi.mi.enricher.cvtermenricher.enricherlistener.event;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 13/05/13
 * Time: 13:11
 */
public class EnricherEvent {

    private EnricherEvent e;
    private String queryID = null;
    private String queryIDType = null;


    public EnricherEvent(){
    }

    public EnricherEvent(EnricherEvent e){
        this.e=e;
    }

    public EnricherEvent(String queryID, String queryIDType){
        this.queryID = queryID;
        this.queryIDType = queryIDType;
    }


    public void setQueryID(String queryID) {
        this.queryID = queryID;
    }

    public void setQueryIDType(String queryIDType) {
        this.queryIDType = queryIDType;
    }


    public String getQueryID(){
        if(queryID == null) {
            return e.getQueryID();
        } else{
            return queryID;
        }
    }
    public String getQueryIDType(){
        if(queryIDType == null) {
            return e.getQueryIDType();
        } else{
            return queryIDType;
        }
    }
}
