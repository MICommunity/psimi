package psidev.psi.mi.jami.bridges.uniprot.remapping;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import psidev.psi.mi.jami.bridges.uniprot.remapping.listener.RemapListener;
import psidev.psi.mi.jami.model.Protein;
import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.jami.utils.comparator.xref.DefaultExternalIdentifierComparator;
import uk.ac.ebi.intact.protein.mapping.results.IdentificationResults;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 07/06/13
 * Time: 11:11
 */
public abstract class AbstractProteinRemapper
        implements ProteinRemapper{

    public static final Log log = LogFactory.getLog(AbstractProteinRemapper.class);
    private Collection<RemapListener> listeners = new ArrayList<RemapListener>();

    private boolean checkingEnabled = true;
    private boolean priorityIdentifiers = true;
    private boolean prioritySequence = true;

    protected RemapReport remapReport;


    protected TreeMap<Xref, IdentificationResults> identifierMappingResults;
    protected boolean identifierMappingResultsHasNoConflict = true;
    protected IdentificationResults sequenceMappingResult = null;


    public AbstractProteinRemapper(){
        identifierMappingResults = new TreeMap<Xref, IdentificationResults>(new DefaultExternalIdentifierComparator());
    }

    public void remapProtein(Protein p) {
        clean();

        String mapping = chooseMapping(p);

        if(mapping != null){
            p.setUniprotkb(mapping);
            remapReport.setRemapped(true);
        }else{
            remapReport.setRemapped(false);
        }
        fireRemapReport();
    }


    private String chooseMapping(Protein p){
        String mapping;

        if(priorityIdentifiers && prioritySequence){
            mapping = findIdentifierMapping(p);
            if (checkingEnabled && !identifierMappingResultsHasNoConflict) {
                remapReport.setMappingFromIdentifiers(false);
                remapReport.setMappingFromSequence(false);
                mapping = null;
            } else if (mapping != null) {
                if(getMappingForSequence(p).hasUniqueUniprotId()){
                    if(mapping.equalsIgnoreCase(
                            getMappingForSequence(p).getFinalUniprotId())){
                        remapReport.setMappingFromIdentifiers(true);
                        remapReport.setMappingFromSequence(true);
                    } else {
                        remapReport.setMappingFromIdentifiers(false);
                        remapReport.setMappingFromSequence(false);
                        remapReport.setConflictMessage("Conflict between identifiers and sequence: "+
                                "identifiers remapped to "+
                                "["+mapping+"], "+
                                "sequence remapped to "+
                                "["+getMappingForSequence(p).getFinalUniprotId()+"].");
                        mapping = null;
                    }
                }else {  // sequenceMapping == null // identifierMappping != null
                    remapReport.setMappingFromIdentifiers(false);
                    remapReport.setMappingFromSequence(false);
                    remapReport.setConflictMessage("Conflict between identifiers and sequence: "+
                            "identifiers remapped to "+
                            "["+mapping+"], "+
                            "sequence remapped to "+
                            "[null].");
                    mapping = null;
                }
            } else {// (identifierMapping == null)
                if(getMappingForSequence(p).hasUniqueUniprotId()){
                    remapReport.setMappingFromIdentifiers(false);
                    remapReport.setMappingFromSequence(false);
                    remapReport.setConflictMessage("Conflict between identifiers and sequence: "+
                            "identifiers remapped to "+
                            "[null], "+
                            "sequence remapped to "+
                            "["+getMappingForSequence(p).getFinalUniprotId()+"].");
                    mapping = null;
                }else {  // sequenceMapping == null
                    remapReport.setMappingFromIdentifiers(false);
                    remapReport.setMappingFromSequence(false);
                    mapping = null;
                }
            }
        }

        else if(priorityIdentifiers && !prioritySequence){
            mapping = findIdentifierMapping(p);
            if (checkingEnabled && !identifierMappingResultsHasNoConflict) {
                remapReport.setMappingFromIdentifiers(false);
                remapReport.setMappingFromSequence(false);
                mapping = null;
            }else if (mapping != null){
                remapReport.setMappingFromIdentifiers(true);
                remapReport.setMappingFromSequence(false);
            }else { // (identifierMapping == null)
                if(getMappingForSequence(p).hasUniqueUniprotId()){
                    remapReport.setMappingFromIdentifiers(false);
                    remapReport.setMappingFromSequence(true);
                    mapping = getMappingForSequence(p).getFinalUniprotId();
                }else { // sequenceMapping == null
                    remapReport.setMappingFromIdentifiers(false);
                    remapReport.setMappingFromSequence(false);
                    mapping = null;
                }
            }
        }

        else if(!priorityIdentifiers && prioritySequence){
            if(getMappingForSequence(p).hasUniqueUniprotId()){
                remapReport.setMappingFromIdentifiers(false);
                remapReport.setMappingFromSequence(true);
                mapping = getMappingForSequence(p).getFinalUniprotId();
            } else { //sequenceMapping == null
                mapping = findIdentifierMapping(p);
                if (checkingEnabled && !identifierMappingResultsHasNoConflict) {
                    remapReport.setMappingFromIdentifiers(false);
                    remapReport.setMappingFromSequence(false);
                    mapping = null;
                }else if (mapping != null){
                    remapReport.setMappingFromIdentifiers(true);
                    remapReport.setMappingFromSequence(false);
                } else {
                    remapReport.setMappingFromIdentifiers(false);
                    remapReport.setMappingFromSequence(false);
                    mapping = null;
                }
            }
        }

        else { //!priorityIdentifiers && !prioritySequence){

            mapping = findIdentifierMapping(p);

            if (checkingEnabled && !identifierMappingResultsHasNoConflict) {
                remapReport.setMappingFromIdentifiers(false);
                remapReport.setMappingFromSequence(false);
                mapping = null;
            } else if (mapping != null) {
                if(getMappingForSequence(p).hasUniqueUniprotId()){
                    if(mapping.equalsIgnoreCase(
                            getMappingForSequence(p).getFinalUniprotId())){
                        remapReport.setMappingFromIdentifiers(true);
                        remapReport.setMappingFromSequence(true);
                    } else {
                        remapReport.setMappingFromIdentifiers(false);
                        remapReport.setMappingFromSequence(false);
                        remapReport.setConflictMessage("Conflict between identifiers and sequence: "+
                                "identifiers remapped to "+
                                "["+mapping+"], "+
                                "sequence remapped to "+
                                "["+getMappingForSequence(p).getFinalUniprotId()+"].");
                        mapping = null;
                    }
                }else {  // sequenceMapping == null // identifierMappping != null
                    remapReport.setMappingFromIdentifiers(true);
                    remapReport.setMappingFromSequence(false);
                }
            } else {// (identifierMapping == null)
                if(getMappingForSequence(p).hasUniqueUniprotId()){
                    remapReport.setMappingFromIdentifiers(false);
                    remapReport.setMappingFromSequence(true);
                    mapping = getMappingForSequence(p).getFinalUniprotId();
                }else {  // sequenceMapping == null
                    remapReport.setMappingFromIdentifiers(false);
                    remapReport.setMappingFromSequence(false);
                    mapping = null;
                }
            }
        }
        return mapping;
    }


    private String findIdentifierMapping(Protein p){

        if(checkingEnabled){
            checkIdentifiersMappingConsistency(p);
            if(identifierMappingResultsHasNoConflict){
                IdentificationResults firstResult = getFirstMappedIdentifierMappingResult(p);
                if(firstResult != null) return firstResult.getFinalUniprotId();
                return null; // If no entry has a mapping
            }
            return null; //if there's a conflict

        } else {
            IdentificationResults firstResult = getFirstMappedIdentifierMappingResult(p);
            if(firstResult != null) return firstResult.getFinalUniprotId();
            return null; // If no entry has a mapping
        }
    }


    /**
     * Iterate of the identifierMappingResults, populating and comparing for conflicts.
     * @param p
     */
    private void checkIdentifiersMappingConsistency(Protein p){
        // Theoretically, these two loops could be merged into one.
        // If all gets on the TreeMap were run through wrapper
        // which chooses either to 'get' or 'query, put and get'

        //Populate the list
        for(Xref x : p.getXrefs()){
            if(!identifierMappingResults.containsKey(x)){
                identifierMappingResults.put(x,getMappingForXref(p, x));
            }
        }

        //check the list
        final Iterator<Xref> outerIterator = identifierMappingResults.keySet().iterator();
        /*String remappedUniprot = null;
        for (Map.Entry<Xref, IdentificationResults> entrye = identifierMappingResults.entrySet()){
            if (remappedUniprot != null){

            }
            else{
                remappedUniprot = entrye.getValue().getFinalUniprotId();
            }
        } */
        out:
        while (outerIterator.hasNext()) {
            final Xref outerKey = outerIterator.next();
            final Iterator<Xref> innerIterator = identifierMappingResults.keySet().iterator();
            while (innerIterator.hasNext()) {
                final Xref innerKey = innerIterator.next();
                in:
                if (DefaultExternalIdentifierComparator.areEquals(innerKey,outerKey)) {
                    break in;
                    // The iterators have met, further comparisons become redundant
                }else{
                    if (identifierMappingResults.get(innerKey).hasUniqueUniprotId() &&
                            identifierMappingResults.get(outerKey).hasUniqueUniprotId() &&
                            !identifierMappingResults.get(innerKey).getFinalUniprotId().equalsIgnoreCase(
                                    identifierMappingResults.get(outerKey).getFinalUniprotId())){
                        identifierMappingResultsHasNoConflict = false;
                        remapReport.setConflictMessage("Conflict in remapped identifiers: "+
                                "["+outerKey.getId()+"] was remapped to "+
                                "["+identifierMappingResults.get(outerKey).getFinalUniprotId()+"], "+
                                "["+innerKey.getId()+"] was remapped to "+
                                "["+identifierMappingResults.get(innerKey).getFinalUniprotId()+"].");
                        break out;
                        //All comparisons can now stop
                    }
                }
            }
        }
    }

    /**
     * Will return the IdentificationResults of the first identifier which has a uniprotID.
     * If the identifierMappingResults has not been populated, it will populate as it goes.
     * If there is no entry with a uniprotID, null will be returned.
     *
     * @param p
     * @return
     */
    private IdentificationResults getFirstMappedIdentifierMappingResult(Protein p){
        for(Xref x : p.getXrefs()){
            if(!identifierMappingResults.containsKey(x)){
                identifierMappingResults.put(x,getMappingForXref(p, x));
            }
            if(identifierMappingResults.get(x).hasUniqueUniprotId()){
                return identifierMappingResults.get(x);
            }
        }
        return null;
    }

    protected abstract IdentificationResults getMappingForXref(Protein p, Xref x);
        //return new DefaultIdentificationResults();

    protected abstract IdentificationResults getMappingForSequence(Protein p);
        //return new DefaultIdentificationResults();

    public boolean isCheckingEnabled() {
        return checkingEnabled;
    }
    public void setCheckingEnabled(boolean checkingEnabled) {
        this.checkingEnabled = checkingEnabled;
    }
    public boolean isPriorityIdentifiers() {
        return priorityIdentifiers;
    }
    public void setPriorityIdentifiers(boolean priorityIdentifiers) {
        this.priorityIdentifiers = priorityIdentifiers;

    }
    public boolean isPrioritySequence() {
        return prioritySequence;
    }
    public void setPrioritySequence(boolean prioritySequence) {
        this.prioritySequence = prioritySequence;
    }

    public void addRemapListener(RemapListener listener){
        listeners.add(listener);
    }
    public void removeRemapListener(RemapListener listener){
        listeners.remove(listener);
    }
    private void fireRemapReport(){
        for(RemapListener r: listeners){
            r.fireRemapReport(remapReport);
        }
    }

    private void clean(){
        remapReport = new RemapReport(checkingEnabled);
        identifierMappingResults.clear();
        identifierMappingResultsHasNoConflict = true;
        sequenceMappingResult = null;

    }
}
