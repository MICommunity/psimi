package psidev.psi.mi.jami.enricher.event;

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

    private String queryID = null;
    private String queryIDType = null;

    private List<EnricherEvent> subEnricherEvents = new ArrayList<EnricherEvent>();

    private List<MismatchReport> mismatches = new ArrayList<MismatchReport>();
    private List<AdditionReport> additions = new ArrayList<AdditionReport>();
    private List<OverwriteReport> overwrites = new ArrayList<OverwriteReport>();


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

    public void setQueryDetails(String queryID, String queryIDType) {
        this.queryID = queryID;
        this.queryIDType = queryIDType;
    }

    public String getQueryID(){
        return queryID;
    }
    public String getQueryIDType(){
        return queryIDType;
    }


    public void addSubEnricherEvent(EnricherEvent e){
        subEnricherEvents.add(e);
    }

    public void addMismatchReport(MismatchReport r) {
        mismatches.add(r);
    }

    public void addAdditionReport(AdditionReport r) {
        additions.add(r);
    }

    public void addOverwriteReport(OverwriteReport r) {
        overwrites.add(r);
    }



    public List<OverwriteReport> getOverwrites() {return overwrites;}
    public List<MismatchReport> getMismatches() {return mismatches;}
    public List<AdditionReport> getAdditions() {return additions;}

    public void clear(){
        queryID = null;
        queryIDType = null;

        subEnricherEvents.clear();
        mismatches.clear();
        additions.clear();
        overwrites.clear();
    }
}
