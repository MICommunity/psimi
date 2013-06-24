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

    private TreeMap<Xref, IdentificationResults> identifierMappingResults;
    private boolean identifierMappingResultsHasNoConflict = true;
    private IdentificationResults sequenceMappingResult = null;
    private boolean isSequenceMappingResultChecked = false;


    public AbstractProteinRemapper(){
        identifierMappingResults = new TreeMap<Xref, IdentificationResults>(new DefaultExternalIdentifierComparator());
    }

    /**
     * Remaps the provided protein to a uniprot identifier.
     * @param p     protein to be remapped
     */
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
                if(getSequenceResult(p) != null && getSequenceResult(p).hasUniqueUniprotId()){
                    if(mapping.equalsIgnoreCase( getSequenceResult(p).getFinalUniprotId())){
                        p.setUniprotkb(mapping);
                        if(listener != null) listener.onGettingRemappingFromIdentifiers(p);
                        if(listener != null) listener.onGettingRemappingFromSequence(p);
                        if(listener != null) listener.onRemappingComplete(p,"Success. Identifier mappings match the sequence mapping.");
                        return;
                    } else {
                        if(listener != null) listener.onSequenceToIdentifierConflict(getSequenceResult(p).getFinalUniprotId() , mapping);
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
                if(getSequenceResult(p) != null && getSequenceResult(p).hasUniqueUniprotId()){
                    p.setUniprotkb(getSequenceResult(p).getFinalUniprotId());
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
            if(getSequenceResult(p) != null && getSequenceResult(p).hasUniqueUniprotId()){
                p.setUniprotkb(getSequenceResult(p).getFinalUniprotId());
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

        else{// if(!priorityIdentifiers && !prioritySequence){
            mapping = findIdentifierMapping(p);

            if (checkingEnabled && !identifierMappingResultsHasNoConflict) {
                if(listener != null) listener.onRemappingComplete(p,"Failed. Identifier mappings have conflicts.");
                return;
            }
            if (mapping == null) {
                if(getSequenceResult(p) != null && getSequenceResult(p).hasUniqueUniprotId()){
                    p.setUniprotkb(getSequenceResult(p).getFinalUniprotId());
                    if(listener != null) listener.onGettingRemappingFromSequence(p);
                    if(listener != null) listener.onRemappingComplete(p,"Success. Sequence has mapping.");
                    return ;
                }else {  // sequenceMapping == null
                    if(listener != null) listener.onRemappingComplete(p,"Failed. No sequence nor identifier mappings.");
                    return;
                }
            }
            else{// if (mapping != null) {
                if(getSequenceResult(p) != null && getSequenceResult(p).hasUniqueUniprotId()){
                    if(mapping.equalsIgnoreCase(getSequenceResult(p).getFinalUniprotId())){
                        p.setUniprotkb(mapping);
                        if(listener != null) listener.onGettingRemappingFromIdentifiers(p);
                        if(listener != null) listener.onGettingRemappingFromSequence(p);
                        if(listener != null) listener.onRemappingComplete(p,"Success.");
                        return;
                    } else {
                        if(listener != null) listener.onSequenceToIdentifierConflict(getSequenceResult(p).getFinalUniprotId() , mapping);
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

        //if(listener != null) listener.onRemappingComplete(p,"Failed. Impossible exit case.");
        //`log.warn("Impossible exit case from the remapping.");
    }

    /**
     * Returns the remapping generated by remapping the identifiers.
     * If the checking is enabled, this is result that all identifiers remap to,
     * or null if there are conflicts.
     * If checking is not enabled, this is the first result which remaps to a uniprot identifier,
     * or null if no identifiers remap.
     * @param p     The protein with identifiers to remap to uniprot.
     * @return      The uniprot AC which matches given criteria or null if none match.
     */
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
     * Checks the identifier mappings for consistency and flags the results in the identifier mappings results boolean.
     * If any conflicts are found, this method sets identifierMappingResultsHasNoConflict to false.
     * @param p     the protein to remap
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
     * @param p     the protein to find a remapping for
     * @return      the first remapping which has a uniprot entry
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
     * A lazy get method for entries in the identification results.
     * If the entry has not yet been retrieved, this will query the service and then put the entry in the map.
     * Or return it if it has already been queried.
     * @param p     the protein to find a remapping for
     * @param xref  The xref to attempt to remap to a uniprot identifier.
     * @return  The results of the query on the given xref.
     */
    public IdentificationResults getEntry(Protein p, Xref xref){
        if(!identifierMappingResults.containsKey(xref)){
            identifierMappingResults.put(xref,getMappingForXref(p, xref));
        }
        return identifierMappingResults.get(xref);
    }

    /**
     * A lazy get method for results of the sequence search.
     * If the sequence mapping has not yet been retrieved,
     * this will query the service and then put the entry in its place,
     * also setting a flag to show that it has been queried.
     * Or return it if it has already been queried.
     * @param p the protein to remap
     * @return  the result of remapping the sequence
     */
    public IdentificationResults getSequenceResult(Protein p){
        if(sequenceMappingResult == null
                && !isSequenceMappingResultChecked){
            sequenceMappingResult = getMappingForSequence(p);
            isSequenceMappingResultChecked = true;
        }
        return sequenceMappingResult;
    }

    /**
     * Finds a remapping for an identifier cross reference using the method which is implemented.
     * @param p the protein to find a remapping for
     * @param x the cross-reference to find a mapping for.
     * @return  The results of the query. Can be null.
     */
    protected abstract IdentificationResults getMappingForXref(Protein p, Xref x);

    /**
     * Finds a remapping for the proteins sequence using the method which is implemented.
     * @param p the protein to find a remapping for
     * @return  The results of the query. Can be null.
     */
    protected abstract IdentificationResults getMappingForSequence(Protein p);

    /**
     * Gives the state of the checking flag.
     * @return  whether checking is being applied to identifier remappings
     */
    public boolean isCheckingEnabled() {
        return checkingEnabled;
    }

    /**
     * Sets the checking flag as to whether
     * If checking is true, identifiers are remapped and compared for consistency.
     * If it is false use the first identifier which remaps.
     * @param checkingEnabled sets whether to check the remappings of identifiers
     */
    public void setCheckingEnabled(boolean checkingEnabled) {
        this.checkingEnabled = checkingEnabled;
    }

    /**
     * Gives the priority of remapping the identifiers.
     * @return  whether identifier remappings have priority
     */
    public boolean isPriorityIdentifiers() {
        return priorityIdentifiers;
    }

    /**
     * If identifiers are the priority, they will be checked first.
     * If the identifiers have conflicts, then no remapping will be made.
     * Sequence will only be used if the identifiers could not find a remapping.
     * If sequence is also set to priority, both sequence and identifiers must return a consistent remapping.
     * If neither is set to priority, then if either remaps without conflicting with the other, that mapping is used.
     * @param priorityIdentifiers   Should priority be given to remapping identifiers.
     */
    public void setPriorityIdentifiers(boolean priorityIdentifiers) {
        this.priorityIdentifiers = priorityIdentifiers;

    }

    /**
     * Gives the priority of remapping the sequence.
     * @return  is priority given to the sequence.
     */
    public boolean isPrioritySequence() {
        return prioritySequence;
    }

    /**
     * If sequence is the priority, it will be checked first.
     * If the sequence does not remap, then the identifiers will be tried.
     * If identifiers are also set to priority, both sequence and identifiers must return a consistent remapping.
     * If neither is set to priority, then if either remaps without conflicting with the other, that mapping is used.
     * @param prioritySequence whether the sequence has priority
     */
    public void setPrioritySequence(boolean prioritySequence) {
        this.prioritySequence = prioritySequence;
    }

    /**
     * Sets a remap listener for this remapping service.
     * @param listener the new remap listener
     */
    public void setRemapListener(ProteinRemapperListener listener){
        this.listener = listener;
    }

    /**
     * gives the remap listener that is listening to this remapping service.
     * @return  the current remap listener
     */
    public ProteinRemapperListener getRemapListener( ){
        return listener;
    }

    /**
     * Cleans, rests and clears all variables ready for a new query.
     */
    private void clean(){
       // remapReport = new RemapReport(checkingEnabled);
        identifierMappingResults.clear();
        identifierMappingResultsHasNoConflict = true;
        sequenceMappingResult = null;
        isSequenceMappingResultChecked = false;
    }
}
