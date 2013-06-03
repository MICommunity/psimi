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
    private String fetcherType = null;
    private final String objectType;

    private List<EnricherEvent> subEnricherEvents = new ArrayList<EnricherEvent>();

    private List<MismatchReport> mismatches = new ArrayList<MismatchReport>();
    private List<AdditionReport> additions = new ArrayList<AdditionReport>();
    private List<OverwriteReport> overwrites = new ArrayList<OverwriteReport>();
    private RemapReport remap = null;


    public EnricherEvent(String objectType){
        this.objectType = objectType;
    }

    /*public void setQueryID(String queryID) {
        this.queryID = queryID;
    }

    public void setQueryIDType(String queryIDType) {
        this.queryIDType = queryIDType;
    } */

    public void setQueryDetails(String queryID, String queryIDType, String fetcherType) {
        this.queryID = queryID;
        this.queryIDType = queryIDType;
        this.fetcherType = fetcherType;
    }

    public String getQueryID(){return queryID;}
    public String getQueryIDType(){return queryIDType;}
    public String getFetcherType(){return fetcherType;}
    public String getObjectType(){return this.objectType;}


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

    public List<EnricherEvent> getSubEnricherEvents() {return subEnricherEvents;}
    public List<OverwriteReport> getOverwrites() {return overwrites;}
    public List<MismatchReport> getMismatches() {return mismatches;}
    public List<AdditionReport> getAdditions() {return additions;}
    public RemapReport getRemap(){return remap;}

    public void clear(){
        queryID = null;
        queryIDType = null;
        fetcherType = null;
        //objectType is final

        subEnricherEvents.clear();
        mismatches.clear();
        additions.clear();
        overwrites.clear();
        remap = null;
    }

    public void addRemapReport(RemapReport r) {
        this.remap = r;
    }
}
