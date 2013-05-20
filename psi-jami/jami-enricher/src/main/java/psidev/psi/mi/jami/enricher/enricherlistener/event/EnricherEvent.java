package psidev.psi.mi.jami.enricher.enricherlistener.event;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 13/05/13
 * Time: 13:11
 */
public class EnricherEvent {

    private EnricherEvent e;    private String queryID = null;
    private String queryIDType = null;

    private List<MismatchEvent> mismatches = new ArrayList<MismatchEvent>();
    private List<AdditionEvent> additions = new ArrayList<AdditionEvent>();
    private List<OverwriteEvent> overwrites = new ArrayList<OverwriteEvent>();


    public EnricherEvent(){
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

    public void addMismatchEvent(MismatchEvent e) {
        mismatches.add(e);
    }

    public void addAdditionEvent(AdditionEvent e) {
        additions.add(e);
    }

    public void addOverwriteEvent(OverwriteEvent e) {
        overwrites.add(e);
    }



    public List<OverwriteEvent> getOverwrites() {return overwrites;}
    public List<MismatchEvent> getMismatches() {return mismatches;}
    public List<AdditionEvent> getAdditions() {return additions;}
}
