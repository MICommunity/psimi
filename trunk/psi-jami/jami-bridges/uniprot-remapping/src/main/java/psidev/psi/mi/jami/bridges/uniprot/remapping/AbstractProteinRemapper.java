package psidev.psi.mi.jami.bridges.uniprot.remapping;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import psidev.psi.mi.jami.bridges.remapper.ProteinRemapperListener;
import psidev.psi.mi.jami.bridges.remapper.ProteinRemapper;
import psidev.psi.mi.jami.bridges.uniprot.remapping.listener.LoggingRemapListener;
import psidev.psi.mi.jami.model.Protein;
import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.jami.utils.comparator.xref.DefaultExternalIdentifierComparator;
import uk.ac.ebi.intact.protein.mapping.results.IdentificationResults;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 07/06/13
 */
public abstract class AbstractProteinRemapper
        implements ProteinRemapper {

    public static final Log log = LogFactory.getLog(AbstractProteinRemapper.class);

    private ProteinRemapperListener listener = new LoggingRemapListener();

    private boolean checkingEnabled = true;
    private boolean priorityIdentifiers = true;
    private boolean prioritySequence = true;

    //protected RemapReport remapReport;

    protected TreeMap<Xref, IdentificationResults> identifierMappingResults;
    protected boolean identifierMappingResultsHasNoConflict = true;
    protected IdentificationResults sequenceMappingResult = null;
    protected boolean isSequenceMappingResultChecked = false;


    public AbstractProteinRemapper(){
        identifierMappingResults = new TreeMap<Xref, IdentificationResults>(new DefaultExternalIdentifierComparator());
    }

    public void remapProtein(Protein p) {
        clean();

        String mapping;

        if(priorityIdentifiers && prioritySequence){
            mapping = findIdentifierMapping(p);
            if (checkingEnabled && !identifierMappingResultsHasNoConflict) {
                if(listener != null) listener.onRemappingComplete(p,"Failed. Identifier mappings have conflicts.");
                return;
            }
            if(mapping == null){
                if(listener != null) listener.onRemappingComplete(p,"Failed. Identifier mapping cannot be found.");
                return;
            }
            else { //mapping != null
                if(getMappingForSequence(p) != null && getMappingForSequence(p).hasUniqueUniprotId()){
                    if(mapping.equalsIgnoreCase( getMappingForSequence(p).getFinalUniprotId())){
                        p.setUniprotkb(mapping);
                        if(listener != null) listener.onGettingRemappingFromIdentifiers(p);
                        if(listener != null) listener.onGettingRemappingFromSequence(p);
                        if(listener != null) listener.onRemappingComplete(p,"Success. Identifier mappings match the sequence mapping.");
                        return;
                    } else {
                        if(listener != null) listener.onSequenceToIdentifierConflict(getMappingForSequence(p).getFinalUniprotId() , mapping);
                        if(listener != null) listener.onRemappingComplete(p,"Failed. Conflict between sequence and identifiers.");
                        return;
                    }
                }else {  // sequenceMapping == null // identifierMappping != null
                    if(listener != null) listener.onRemappingComplete(p,"Failed. Sequence mapping cannot be resolved.");
                    return;
                }
            }
        }

        else if(priorityIdentifiers && !prioritySequence){
            mapping = findIdentifierMapping(p);
            if (checkingEnabled && !identifierMappingResultsHasNoConflict) {
                if(listener != null) listener.onRemappingComplete(p,"Failed. Identifier mappings have conflicts.");
                return ;
            }else if (mapping != null){
                p.setUniprotkb(mapping);
                if(listener != null) listener.onGettingRemappingFromIdentifiers(p);
                if(listener != null) listener.onRemappingComplete(p,"Success. Identifiers have mapping.");
                return;
            }else { // (identifierMapping == null)
                if(getMappingForSequence(p) != null && getMappingForSequence(p).hasUniqueUniprotId()){
                    p.setUniprotkb(getMappingForSequence(p).getFinalUniprotId());
                    if(listener != null) listener.onGettingRemappingFromSequence(p);
                    if(listener != null) listener.onRemappingComplete(p,"Success. Sequence has mapping.");
                    return;
                }else { // sequenceMapping == null
                    if(listener != null) listener.onRemappingComplete(p,"Failed. Neither identifier nor sequence have mappings.");
                    return;
                }
            }
        }

        else if(!priorityIdentifiers && prioritySequence){
            if(getMappingForSequence(p) != null && getMappingForSequence(p).hasUniqueUniprotId()){
                p.setUniprotkb(getMappingForSequence(p).getFinalUniprotId());
                if(listener != null) listener.onGettingRemappingFromSequence(p);
                if(listener != null) listener.onRemappingComplete(p,"Success. Sequence has mapping.");
                return ;
            } else { //sequenceMapping == null
                mapping = findIdentifierMapping(p);
                if (checkingEnabled && !identifierMappingResultsHasNoConflict) {
                    if(listener != null) listener.onRemappingComplete(p,"Failed. No sequence remapping and identifier mappings have conflicts.");
                    return;
                }
                if (mapping != null){
                    p.setUniprotkb(mapping);
                    if(listener != null) listener.onGettingRemappingFromIdentifiers(p);
                    if(listener != null) listener.onRemappingComplete(p,"Success. Identifiers have mapping.");
                    return;
                } else {
                    listener.onRemappingComplete(p,"Failed. No sequence nor identifier mappings.");
                    return;
                }
            }
        }

        else if(!priorityIdentifiers && !prioritySequence){
            mapping = findIdentifierMapping(p);

            if (checkingEnabled && !identifierMappingResultsHasNoConflict) {
                if(listener != null) listener.onRemappingComplete(p,"Failed. Identifier mappings have conflicts.");
                return;
            }
            if (mapping == null) {
                if(getMappingForSequence(p) != null && getMappingForSequence(p).hasUniqueUniprotId()){
                    p.setUniprotkb(getMappingForSequence(p).getFinalUniprotId());
                    if(listener != null) listener.onGettingRemappingFromSequence(p);
                    if(listener != null) listener.onRemappingComplete(p,"Success. Sequence has mapping.");
                    return ;
                }else {  // sequenceMapping == null
                    if(listener != null) listener.onRemappingComplete(p,"Failed. No sequence nor identifier mappings.");
                    return;
                }
            }
            else if (mapping != null) {
                if(getMappingForSequence(p) != null && getMappingForSequence(p).hasUniqueUniprotId()){
                    if(mapping.equalsIgnoreCase(getMappingForSequence(p).getFinalUniprotId())){
                        p.setUniprotkb(mapping);
                        if(listener != null) listener.onGettingRemappingFromIdentifiers(p);
                        if(listener != null) listener.onGettingRemappingFromSequence(p);
                        if(listener != null) listener.onRemappingComplete(p,"Success.");
                        return;
                    } else {
                        if(listener != null) listener.onSequenceToIdentifierConflict(getMappingForSequence(p).getFinalUniprotId() , mapping);
                        if(listener != null) listener.onRemappingComplete(p,"Failed. Conflict between sequence and identifier mappings.");
                        return ;
                    }
                }else {  // sequenceMapping == null // identifierMappping != null
                    p.setUniprotkb(mapping);
                    if(listener != null) listener.onGettingRemappingFromIdentifiers(p);
                    if(listener != null) listener.onRemappingComplete(p,"Success.");
                    return ;
                }
            }
        }

        if(listener != null) listener.onRemappingComplete(p,"Failed. Impossible exit case");
        log.warn("Impossible exit case from the remapping.");
        return ;
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
        String remappedUniprot = null;
        //Populate the list using 'getEntry'
        for(Xref x : p.getXrefs()){
            if(getEntry(p,x) != null && getEntry(p,x).hasUniqueUniprotId()){
                if (remappedUniprot != null){
                    if(! remappedUniprot.equalsIgnoreCase(getEntry(p,x).getFinalUniprotId())){
                        identifierMappingResultsHasNoConflict = false;
                        if(listener != null) listener.onIdentifierConflict(remappedUniprot, getEntry(p,x).getFinalUniprotId());
                    }
                }
                else{
                    remappedUniprot = getEntry(p,x).getFinalUniprotId();
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
            if(getEntry(p,x) != null
                    && getEntry(p,x).hasUniqueUniprotId()){
                return getEntry(p,x);
            }
        }
        return null;
    }

    /**
     * A wrapper for the identifierMappingResults map to ensure that before get is applied, the entry has been put.
     * @param p
     * @param xref
     * @return
     */
    public IdentificationResults getEntry(Protein p, Xref xref){
        if(!identifierMappingResults.containsKey(xref)){
            identifierMappingResults.put(xref,getMappingForXref(p, xref));
        }
        return identifierMappingResults.get(xref);
    }


    protected abstract IdentificationResults getMappingForXref(Protein p, Xref x);
    protected abstract IdentificationResults getMappingForSequence(Protein p);

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

    public void setRemapListener(ProteinRemapperListener listener){
        this.listener = listener;
    }
    public ProteinRemapperListener getRemapListener( ){
        return listener;
    }

    private void clean(){
       // remapReport = new RemapReport(checkingEnabled);
        identifierMappingResults.clear();
        identifierMappingResultsHasNoConflict = true;
        sequenceMappingResult = null;
        isSequenceMappingResultChecked = false;
    }
}
