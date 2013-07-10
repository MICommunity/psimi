package psidev.psi.mi.jami.bridges.uniprot.remapping;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import psidev.psi.mi.jami.bridges.exception.BridgeFailedException;
import psidev.psi.mi.jami.bridges.remapper.ProteinRemapperListener;
import psidev.psi.mi.jami.bridges.remapper.ProteinRemapper;
import psidev.psi.mi.jami.bridges.uniprot.remapping.listener.LoggingRemapListener;
import psidev.psi.mi.jami.model.Protein;
import psidev.psi.mi.jami.model.Xref;
import uk.ac.ebi.intact.model.BioSource;
import uk.ac.ebi.intact.protein.mapping.model.contexts.IdentificationContext;
import uk.ac.ebi.intact.protein.mapping.results.IdentificationResults;
import uk.ac.ebi.intact.protein.mapping.strategies.StrategyWithIdentifier;
import uk.ac.ebi.intact.protein.mapping.strategies.StrategyWithSequence;
import uk.ac.ebi.intact.protein.mapping.strategies.exceptions.StrategyException;

import java.util.*;

/**
 * Takes a protein and uses its Xrefs and sequence to find a remapping to uniprot.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since  07/06/13
 */
public class UniprotProteinRemapper
        implements ProteinRemapper {

    public static final Log log = LogFactory.getLog(UniprotProteinRemapper.class);

    private static ProteinRemapperListener listener = new LoggingRemapListener();

    private static boolean checkingEnabled = true;
    private static boolean priorityIdentifiers = true;
    private static boolean prioritySequence = true;


    private static  Stack<String> report = new Stack<String>();

    /**
     * Remaps the provided protein to a uniprot identifier.
     * @param proteinToRemap     the protein to be remapped
     */
    public void remapProtein(Protein proteinToRemap) throws BridgeFailedException {

        IdentificationContext context = new IdentificationContext();
        if(proteinToRemap.getOrganism() != null)
            context.setOrganism(new BioSource("ShortName", ""+proteinToRemap.getOrganism().getTaxId()));

        if(priorityIdentifiers && prioritySequence) priorityRemapping(proteinToRemap, context);
        else if(priorityIdentifiers) identifierPriorityRemapping(proteinToRemap, context);
        else if(prioritySequence) sequencePriorityRemapping(proteinToRemap, context);
        else noPriorityRemapping(proteinToRemap, context);
    }

    /**
     * Remaps the protein, requiring a consistent, non null, mapping between the sequence and identifier.
     * If either does not return a remapping, this will fail.
     * @param proteinToRemap
     * @param context
     * @throws BridgeFailedException
     */
    private static void priorityRemapping(Protein proteinToRemap, IdentificationContext context)
            throws BridgeFailedException {

        report.push("Priority for identifiers and sequence.");

        IdentificationResults sequenceMapping = getMappingForSequence(proteinToRemap , context);
        if (sequenceMapping == null || ! sequenceMapping.hasUniqueUniprotId()){
            report.push("No mapping found for sequence.");
            if(listener != null) listener.onRemappingFailed(proteinToRemap, report);
            return;
        } else {
            IdentificationResults identifierMapping;
            HashMap<Xref, IdentificationResults> identifierMappingResults = new HashMap<Xref, IdentificationResults>();

            if(checkingEnabled){
                if(! areAllIdentifierMappingsConsistent(proteinToRemap, identifierMappingResults, context)){
                    if(listener != null)
                        listener.onRemappingFailed(proteinToRemap, report);
                    return;
                }
            } else{
                isThereAnIdentifierMapping(proteinToRemap, identifierMappingResults, context);
            }

            identifierMapping = getFirstRemappedIdentifierResult(identifierMappingResults);


            if( identifierMapping == null || ! identifierMapping.hasUniqueUniprotId()) {
                report.push("No mapping found for identifiers");
                if(listener != null)
                    listener.onRemappingFailed(proteinToRemap, report);
            } else {
                if(! identifierMapping.getFinalUniprotId().equalsIgnoreCase(sequenceMapping.getFinalUniprotId())){
                    report.push("Conflict between mappings for " +
                            "identifiers ["+identifierMapping.getFinalUniprotId()+"] and " +
                            "sequence ["+sequenceMapping.getFinalUniprotId()+"].");
                    if(listener != null)
                        listener.onRemappingFailed(proteinToRemap , report);
                } else {
                    proteinToRemap.setUniprotkb(identifierMapping.getFinalUniprotId());
                    report.push("Mapping found for sequence and identifiers.");
                    if(listener != null){
                        listener.onRemappingSuccessful(proteinToRemap ,report );
                    }
                }
            }
        }
    }

    /**
     * Remaps the protein using the identifiers. If an identifier mapping cannot be found, the sequence will be used.
     * @param proteinToRemap
     * @param context
     * @throws BridgeFailedException
     */
    private static void identifierPriorityRemapping(Protein proteinToRemap , IdentificationContext context)
            throws BridgeFailedException {

        report.push("Priority for identifiers.");

        HashMap<Xref, IdentificationResults> identifierMappingResults = new HashMap<Xref, IdentificationResults>();
        IdentificationResults identifierMapping;

        if(checkingEnabled){
            if(! areAllIdentifierMappingsConsistent(proteinToRemap, identifierMappingResults, context)){
                if(listener != null)
                    listener.onRemappingFailed(proteinToRemap, report);
                return;
            }
        } else{
            isThereAnIdentifierMapping(proteinToRemap, identifierMappingResults, context);
        }

        identifierMapping = getFirstRemappedIdentifierResult(identifierMappingResults);

        if (identifierMapping != null){
            proteinToRemap.setUniprotkb(identifierMapping.getFinalUniprotId());
            report.push("Mapping found for identifiers.");
            if(listener != null){
                listener.onRemappingSuccessful(proteinToRemap, report);
            }
            return;
        }else {
            IdentificationResults sequenceMapping = getMappingForSequence(proteinToRemap , context);
            if (sequenceMapping != null && sequenceMapping.hasUniqueUniprotId()){
                proteinToRemap.setUniprotkb(sequenceMapping.getFinalUniprotId());
                report.push("Mapping found for sequence.");
                if(listener != null){
                    listener.onRemappingSuccessful(proteinToRemap,report);
                }
                return;
            }else { // sequenceMapping == null
                report.push("No remapping found for sequence and identifiers.");
                if(listener != null) listener.onRemappingFailed(proteinToRemap , report );
                return;
            }
        }
    }

    /**
     * Remaps the protein using the sequence remapping. If no mapping is found, the identifiers will be used.
     * @param proteinToRemap
     * @param context
     * @throws BridgeFailedException
     */
    private static void sequencePriorityRemapping(Protein proteinToRemap , IdentificationContext context)
            throws BridgeFailedException {
        report.push("Priority for sequence.");
        IdentificationResults sequenceMapping = getMappingForSequence(proteinToRemap , context);

        if( sequenceMapping != null && sequenceMapping.hasUniqueUniprotId() ) {
            proteinToRemap.setUniprotkb(sequenceMapping.getFinalUniprotId());
            report.push("Remapping found for sequence.");
            if(listener != null){
                listener.onRemappingSuccessful(proteinToRemap,report);
            }
            return ;
        } else {

            IdentificationResults identifierMapping;
            HashMap<Xref, IdentificationResults> identifierMappingResults = new HashMap<Xref, IdentificationResults>();

            if(checkingEnabled){
                if(! areAllIdentifierMappingsConsistent(proteinToRemap, identifierMappingResults, context)){
                    report.push("Identifier mappings have conflicts.");
                    if(listener != null)
                        listener.onRemappingFailed(proteinToRemap, report );
                    return;
                }
            } else{
                isThereAnIdentifierMapping(proteinToRemap, identifierMappingResults, context);
            }

            identifierMapping = getFirstRemappedIdentifierResult(identifierMappingResults);

            if (identifierMapping != null && identifierMapping.hasUniqueUniprotId()){
                proteinToRemap.setUniprotkb(identifierMapping.getFinalUniprotId());
                report.push("Identifiers have mapping.");
                if(listener != null){
                    listener.onRemappingSuccessful(proteinToRemap,report);
                }
                return;
            } else {
                report.push("Neither sequence nor identifiers have mapping.");
                if(listener != null)
                    listener.onRemappingFailed(proteinToRemap, report);
                return;
            }
        }
    }


    /**
     * Remaps the protein using the identifiers and sequence.
     * If the sequence or identifiers are null, but the other has a mapping, that mapping will be used.
     * @param proteinToRemap
     * @param context
     * @throws BridgeFailedException
     */
    private static void noPriorityRemapping(Protein proteinToRemap, IdentificationContext context) throws BridgeFailedException {
        report.push("Priority for neither identifiers nor sequence.");

        HashMap<Xref, IdentificationResults> identifierMappingResults = new HashMap<Xref, IdentificationResults>();
        if(checkingEnabled){
            if( ! areAllIdentifierMappingsConsistent(proteinToRemap, identifierMappingResults, context)){
                report.push("Identifier mappings have conflicts.");
                if(listener != null)
                    listener.onRemappingFailed(proteinToRemap,report);
                return;
            }
        } else{
            isThereAnIdentifierMapping(proteinToRemap, identifierMappingResults, context);
        }

        IdentificationResults identifierMapping = getFirstRemappedIdentifierResult(identifierMappingResults);
        IdentificationResults sequenceMapping = getMappingForSequence(proteinToRemap , context);

        if( (identifierMapping != null && identifierMapping.hasUniqueUniprotId())
                && (sequenceMapping != null && sequenceMapping.hasUniqueUniprotId()) ){
            if(identifierMapping.getFinalUniprotId().equalsIgnoreCase(sequenceMapping.getFinalUniprotId())){
                proteinToRemap.setUniprotkb(sequenceMapping.getFinalUniprotId());
                report.push("Mapping without conflicts found for sequence and identifiers.");
                if(listener != null) listener.onRemappingSuccessful(proteinToRemap, report);
            } else {
                if(listener != null) {
                    report.push("Conflict between " +
                            "sequence mapping [" +sequenceMapping.getFinalUniprotId()+"] " +
                            "and identifier mapping ["+identifierMapping.getFinalUniprotId()+"].");
                    listener.onRemappingFailed(proteinToRemap,report);
                }
            }
        }else if ( (identifierMapping != null && identifierMapping.hasUniqueUniprotId()) ){
            proteinToRemap.setUniprotkb(identifierMapping.getFinalUniprotId());
            report.push("Mapping found for identifiers.");
            if(listener != null) listener.onRemappingSuccessful(proteinToRemap,report);
        }else if ( (sequenceMapping != null && sequenceMapping.hasUniqueUniprotId()) ){
            proteinToRemap.setUniprotkb(sequenceMapping.getFinalUniprotId());
            report.push("Mapping found for sequence.");
            if(listener != null) listener.onRemappingSuccessful(proteinToRemap,report);
        } else {
            report.push("No mapping for sequence or identifiers.");
            if(listener != null) listener.onRemappingFailed(proteinToRemap,report);
        }
    }




    /**
     * Queries and adds the results to the map, checks the identifier mappings for consistency
     * and returns false as soon as a conflict is found.
     * @param proteinToRemap     the protein to remap
     */
    private static boolean areAllIdentifierMappingsConsistent(
            Protein proteinToRemap,
            HashMap<Xref, IdentificationResults> identifierMappingResults,
            IdentificationContext context)
            throws BridgeFailedException {

        IdentificationResults remappedUniprot = null;
        //Populate the list
        for(Xref xref : proteinToRemap.getXrefs()){
            IdentificationResults result = getMappingForXref( xref, context);
            identifierMappingResults.put(xref , result);

            if(result != null && result.hasUniqueUniprotId()){
                if (remappedUniprot != null){
                    if(! remappedUniprot.getFinalUniprotId().equalsIgnoreCase(result.getFinalUniprotId())){
                        report.push("Identifier mappings have conflict: ["+ remappedUniprot.getFinalUniprotId()+"] and ["+result.getFinalUniprotId()+"].");
                        return false;
                    }
                }
                else{
                    remappedUniprot = result;
                }
            }
        }
        report.push("Identifier mappings have no conflicts.");
        return true;
    }

    /**
     * Finds the first identifier remapping which has a uniprot id and adds it to the identifier mapping results.
     * @param proteinToRemap
     * @param identifierMappingResults
     * @param context
     * @return
     * @throws BridgeFailedException
     */
    private static boolean isThereAnIdentifierMapping(
            Protein proteinToRemap, HashMap<Xref, IdentificationResults> identifierMappingResults, IdentificationContext context)
            throws BridgeFailedException {

        for(Xref xref : proteinToRemap.getXrefs()){
            IdentificationResults result = getMappingForXref(xref, context);
            if(result != null && result.hasUniqueUniprotId()){
                identifierMappingResults.put(xref , result);
                return true;
            }
        }
        return false;
    }

    /**
     * Will return the IdentificationResults of the first identifier which has a uniprotID.
     * If there is no entry with a uniprotID, null will be returned.
     *
     * @return      the first remapping which has a uniprot entry
     */
    private static IdentificationResults getFirstRemappedIdentifierResult(
            HashMap<Xref, IdentificationResults> identifierMappingResults){

        for(Map.Entry<Xref, IdentificationResults> entry : identifierMappingResults.entrySet()){
            if(entry.getValue() != null
                    && entry.getValue().hasUniqueUniprotId()){
                return entry.getValue();
            }
        }
        return null;
    }



    private static IdentificationResults getMappingForXref(
            Xref xrefToRemap, IdentificationContext context)
            throws BridgeFailedException {

        String value =  xrefToRemap.getId();
        String database = null;

        StrategyWithIdentifier identifierStrategy;
        identifierStrategy = new StrategyWithIdentifier();
        identifierStrategy.enableIsoforms(true);


        //Find a way to identify the database
        if(xrefToRemap.getDatabase() != null){
            database = xrefToRemap.getDatabase().getMIIdentifier();
            if(database == null) database = xrefToRemap.getDatabase().getShortName();
        }
        //If there's an identity do a search - else return an empty result.
        if(database != null && value != null){
            context.setDatabaseForIdentifier(database);
            context.setIdentifier(value);

            try{
                return identifierStrategy.identifyProtein(context);
            }catch(StrategyException e){
                throw new BridgeFailedException("Failure while attempting to remap protein identifier.",e);
            }
        }

        return null;
    }

    /**
     * Finds a remapping for the proteins sequence using the method which is implemented.
     * @param proteinToRemap the protein to find a remapping for
     * @return  The results of the query. Can be null.
     */
    protected static IdentificationResults getMappingForSequence(Protein proteinToRemap, IdentificationContext context)
            throws BridgeFailedException {

        if(proteinToRemap.getSequence() != null && proteinToRemap.getSequence().length() > 0) {
            context.setSequence(proteinToRemap.getSequence());
            try{
                StrategyWithSequence sequenceStrategy = new StrategyWithSequence();
                sequenceStrategy.setBasicBlastRequired(false);
                sequenceStrategy.setEnableIntactSearch(false);
                sequenceStrategy.enableIsoforms(true);

                return sequenceStrategy.identifyProtein(context);
            }catch(StrategyException e){
                throw new BridgeFailedException("Encountered a StrategyException " +
                        "when searching for sequence", e);
            }
        }
        else {
            report.push("Could not get mapping for sequence, no sequence was available.");
            log.warn("Sequence is not set in context.");
            return null;
        }
    }



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
}
