package psidev.psi.mi.jami.bridges.uniprot.mapping;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import psidev.psi.mi.jami.bridges.exception.BridgeFailedException;
import psidev.psi.mi.jami.bridges.mapper.ProteinMapper;
import psidev.psi.mi.jami.bridges.mapper.ProteinMapperListener;
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
 * Takes a protein and uses its Xrefs and sequence to find a Mapping to uniprot.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since  07/06/13
 */
public class UniprotProteinMapper
        implements ProteinMapper {

    public static final Log log = LogFactory.getLog(UniprotProteinMapper.class);

    private static ProteinMapperListener listener = null;

    private static boolean checkingEnabled = true;
    private static boolean priorityIdentifiers = true;
    private static boolean prioritySequence = true;


    private static  Stack<String> report = new Stack<String>();

    /**
     * Maps the provided protein to a uniprot identifier.
     * @param proteinToMap     the protein to be Mapped
     */
    public void mapProtein(Protein proteinToMap) throws BridgeFailedException {

        IdentificationContext context = new IdentificationContext();
        if( proteinToMap.getOrganism() != null )
            context.setOrganism(new BioSource("ShortName", ""+proteinToMap.getOrganism().getTaxId()));

        if(priorityIdentifiers && prioritySequence) priorityMapping(proteinToMap, context);
        else if(priorityIdentifiers) identifierPriorityMapping(proteinToMap, context);
        else if(prioritySequence) sequencePriorityMapping(proteinToMap, context);
        else noPriorityMapping(proteinToMap, context);
    }

    /**
     * Maps the protein, requiring a consistent, non null, mapping between the sequence and identifier.
     * If either does not return a Mapping, this will fail.
     * @param proteinToMap
     * @param context
     * @throws BridgeFailedException
     */
    private static void priorityMapping(Protein proteinToMap, IdentificationContext context)
            throws BridgeFailedException {

        report.push("Priority for identifiers and sequence.");

        IdentificationResults sequenceMapping = getMappingForSequence(proteinToMap , context);
        if (sequenceMapping == null || ! sequenceMapping.hasUniqueUniprotId()){
            report.push("No mapping found for sequence.");
            if(listener != null) listener.onFailedMapping(proteinToMap, report);
            return;
        } else {
            IdentificationResults identifierMapping;
            HashMap<Xref, IdentificationResults> identifierMappingResults = new HashMap<Xref, IdentificationResults>();

            if(checkingEnabled){
                if(! areAllIdentifierMappingsConsistent(proteinToMap, identifierMappingResults, context)){
                    if(listener != null)
                        listener.onFailedMapping(proteinToMap, report);
                    return;
                }
            } else{
                isThereAnIdentifierMapping(proteinToMap, identifierMappingResults, context);
            }

            identifierMapping = getFirstMappedIdentifierResult(identifierMappingResults);


            if( identifierMapping == null || ! identifierMapping.hasUniqueUniprotId()) {
                report.push("No mapping found for identifiers");
                if(listener != null)
                    listener.onFailedMapping(proteinToMap, report);
            } else {
                if(! identifierMapping.getFinalUniprotId().equalsIgnoreCase(sequenceMapping.getFinalUniprotId())){
                    report.push("Conflict between mappings for " +
                            "identifiers ["+identifierMapping.getFinalUniprotId()+"] and " +
                            "sequence ["+sequenceMapping.getFinalUniprotId()+"].");
                    if(listener != null)
                        listener.onFailedMapping(proteinToMap , report);
                } else {
                    proteinToMap.setUniprotkb(identifierMapping.getFinalUniprotId());
                    report.push("Mapping found for sequence and identifiers.");
                    if(listener != null){
                        listener.onSuccessfulMapping(proteinToMap ,report );
                    }
                }
            }
        }
    }

    /**
     * Maps the protein using the identifiers. If an identifier mapping cannot be found, the sequence will be used.
     * @param proteinToMap
     * @param context
     * @throws BridgeFailedException
     */
    private static void identifierPriorityMapping(Protein proteinToMap , IdentificationContext context)
            throws BridgeFailedException {

        report.push("Priority for identifiers.");

        HashMap<Xref, IdentificationResults> identifierMappingResults = new HashMap<Xref, IdentificationResults>();
        IdentificationResults identifierMapping;

        if(checkingEnabled){
            if(! areAllIdentifierMappingsConsistent(proteinToMap, identifierMappingResults, context)){
                if(listener != null)
                    listener.onFailedMapping(proteinToMap, report);
                return;
            }
        } else{
            isThereAnIdentifierMapping(proteinToMap, identifierMappingResults, context);
        }

        identifierMapping = getFirstMappedIdentifierResult(identifierMappingResults);

        if (identifierMapping != null){
            proteinToMap.setUniprotkb(identifierMapping.getFinalUniprotId());
            report.push("Mapping found for identifiers.");
            if(listener != null){
                listener.onSuccessfulMapping(proteinToMap, report);
            }
            return;
        }else {
            IdentificationResults sequenceMapping = getMappingForSequence(proteinToMap , context);
            if (sequenceMapping != null && sequenceMapping.hasUniqueUniprotId()){
                proteinToMap.setUniprotkb(sequenceMapping.getFinalUniprotId());
                report.push("Mapping found for sequence.");
                if(listener != null){
                    listener.onSuccessfulMapping(proteinToMap,report);
                }
                return;
            }else { // sequenceMapping == null
                report.push("No Mapping found for sequence and identifiers.");
                if(listener != null) listener.onFailedMapping(proteinToMap , report );
                return;
            }
        }
    }

    /**
     * Maps the protein using the sequence Mapping. If no mapping is found, the identifiers will be used.
     * @param proteinToMap
     * @param context
     * @throws BridgeFailedException
     */
    private static void sequencePriorityMapping(Protein proteinToMap , IdentificationContext context)
            throws BridgeFailedException {
        report.push("Priority for sequence.");
        IdentificationResults sequenceMapping = getMappingForSequence(proteinToMap , context);

        if( sequenceMapping != null && sequenceMapping.hasUniqueUniprotId() ) {
            proteinToMap.setUniprotkb(sequenceMapping.getFinalUniprotId());
            report.push("Mapping found for sequence.");
            if(listener != null){
                listener.onSuccessfulMapping(proteinToMap,report);
            }
            return ;
        } else {

            IdentificationResults identifierMapping;
            HashMap<Xref, IdentificationResults> identifierMappingResults = new HashMap<Xref, IdentificationResults>();

            if(checkingEnabled){
                if(! areAllIdentifierMappingsConsistent(proteinToMap, identifierMappingResults, context)){
                    report.push("Identifier mappings have conflicts.");
                    if(listener != null)
                        listener.onFailedMapping(proteinToMap, report );
                    return;
                }
            } else{
                isThereAnIdentifierMapping(proteinToMap, identifierMappingResults, context);
            }

            identifierMapping = getFirstMappedIdentifierResult(identifierMappingResults);

            if (identifierMapping != null && identifierMapping.hasUniqueUniprotId()){
                proteinToMap.setUniprotkb(identifierMapping.getFinalUniprotId());
                report.push("Identifiers have mapping.");
                if(listener != null){
                    listener.onSuccessfulMapping(proteinToMap,report);
                }
                return;
            } else {
                report.push("Neither sequence nor identifiers have mapping.");
                if(listener != null)
                    listener.onFailedMapping(proteinToMap, report);
                return;
            }
        }
    }


    /**
     * Maps the protein using the identifiers and sequence.
     * If the sequence or identifiers are null, but the other has a mapping, that mapping will be used.
     * @param proteinToMap
     * @param context
     * @throws BridgeFailedException
     */
    private static void noPriorityMapping(Protein proteinToMap, IdentificationContext context) throws BridgeFailedException {
        report.push("Priority for neither identifiers nor sequence.");

        HashMap<Xref, IdentificationResults> identifierMappingResults = new HashMap<Xref, IdentificationResults>();
        if(checkingEnabled){
            if( ! areAllIdentifierMappingsConsistent(proteinToMap, identifierMappingResults, context)){
                report.push("Identifier mappings have conflicts.");
                if(listener != null)
                    listener.onFailedMapping(proteinToMap,report);
                return;
            }
        } else{
            isThereAnIdentifierMapping(proteinToMap, identifierMappingResults, context);
        }

        IdentificationResults identifierMapping = getFirstMappedIdentifierResult(identifierMappingResults);
        IdentificationResults sequenceMapping = getMappingForSequence(proteinToMap , context);

        if( (identifierMapping != null && identifierMapping.hasUniqueUniprotId())
                && (sequenceMapping != null && sequenceMapping.hasUniqueUniprotId()) ){
            if(identifierMapping.getFinalUniprotId().equalsIgnoreCase(sequenceMapping.getFinalUniprotId())){
                proteinToMap.setUniprotkb(sequenceMapping.getFinalUniprotId());
                report.push("Mapping without conflicts found for sequence and identifiers.");
                if(listener != null) listener.onSuccessfulMapping(proteinToMap, report);
            } else {
                if(listener != null) {
                    report.push("Conflict between " +
                            "sequence mapping [" +sequenceMapping.getFinalUniprotId()+"] " +
                            "and identifier mapping ["+identifierMapping.getFinalUniprotId()+"].");
                    listener.onFailedMapping(proteinToMap,report);
                }
            }
        }else if ( (identifierMapping != null && identifierMapping.hasUniqueUniprotId()) ){
            proteinToMap.setUniprotkb(identifierMapping.getFinalUniprotId());
            report.push("Mapping found for identifiers.");
            if(listener != null) listener.onSuccessfulMapping(proteinToMap,report);
        }else if ( (sequenceMapping != null && sequenceMapping.hasUniqueUniprotId()) ){
            proteinToMap.setUniprotkb(sequenceMapping.getFinalUniprotId());
            report.push("Mapping found for sequence.");
            if(listener != null) listener.onSuccessfulMapping(proteinToMap,report);
        } else {
            report.push("No mapping for sequence or identifiers.");
            if(listener != null) listener.onFailedMapping(proteinToMap,report);
        }
    }




    /**
     * Queries and adds the results to the map, checks the identifier mappings for consistency
     * and returns false as soon as a conflict is found.
     * @param proteinToMap     the protein to Map
     */
    private static boolean areAllIdentifierMappingsConsistent(
            Protein proteinToMap,
            HashMap<Xref, IdentificationResults> identifierMappingResults,
            IdentificationContext context)
            throws BridgeFailedException {

        IdentificationResults MappedUniprot = null;
        //Populate the list
        for(Xref xref : proteinToMap.getXrefs()){
            IdentificationResults result = getMappingForXref( xref, context);
            identifierMappingResults.put(xref , result);

            if(result != null) log.info(xref+" ID="+result.getFinalUniprotId());
            else    log.info("Dead xref? "+xref);
            if(result != null && result.hasUniqueUniprotId()){

                if (MappedUniprot != null){
                    if(! MappedUniprot.getFinalUniprotId().equalsIgnoreCase(result.getFinalUniprotId())){
                        report.push("Identifier mappings have conflict: ["+ MappedUniprot.getFinalUniprotId()+"] and ["+result.getFinalUniprotId()+"].");
                        return false;
                    }
                }
                else{
                    MappedUniprot = result;
                }
            }
        }
        report.push("Identifier mappings have no conflicts.");
        return true;
    }

    /**
     * Finds the first identifier Mapping which has a uniprot id and adds it to the identifier mapping results.
     * @param proteinToMap
     * @param identifierMappingResults
     * @param context
     * @return
     * @throws BridgeFailedException
     */
    private static boolean isThereAnIdentifierMapping(
            Protein proteinToMap, HashMap<Xref, IdentificationResults> identifierMappingResults, IdentificationContext context)
            throws BridgeFailedException {

        for(Xref xref : proteinToMap.getXrefs()){
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
     * @return      the first Mapping which has a uniprot entry
     */
    private static IdentificationResults getFirstMappedIdentifierResult(
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
            Xref xrefToMap, IdentificationContext context)
            throws BridgeFailedException {

        String value =  xrefToMap.getId();
        String database = null;

        StrategyWithIdentifier identifierStrategy;
        identifierStrategy = new StrategyWithIdentifier();
        identifierStrategy.enableIsoforms(true);


        //Find a way to identify the database
        if(xrefToMap.getDatabase() != null){
            database = xrefToMap.getDatabase().getMIIdentifier();
            if(database == null) database = xrefToMap.getDatabase().getShortName();
        }
        //If there's an identity do a search - else return an empty result.
        if(database != null && value != null){
            context.setDatabaseForIdentifier(database);
            context.setIdentifier(value);

            try{
                return identifierStrategy.identifyProtein(context);
            }catch(StrategyException e){
                throw new BridgeFailedException("Failure while attempting to Map protein identifier.",e);
            }
        }

        return null;
    }

    /**
     * Finds a Mapping for the proteins sequence using the method which is implemented.
     * @param proteinToMap the protein to find a Mapping for
     * @return  The results of the query. Can be null.
     */
    protected static IdentificationResults getMappingForSequence(Protein proteinToMap, IdentificationContext context)
            throws BridgeFailedException {

        if(proteinToMap.getSequence() != null && proteinToMap.getSequence().length() > 0) {
            context.setSequence(proteinToMap.getSequence());
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
     * @return  whether checking is being applied to identifier Mappings
     */
    public boolean isCheckingEnabled() {
        return checkingEnabled;
    }

    /**
     * Sets the checking flag as to whether
     * If checking is true, identifiers are Mapped and compared for consistency.
     * If it is false use the first identifier which Maps.
     * @param checkingEnabled sets whether to check the Mappings of identifiers
     */
    public void setCheckingEnabled(boolean checkingEnabled) {
        this.checkingEnabled = checkingEnabled;
    }

    /**
     * Gives the priority of Mapping the identifiers.
     * @return  whether identifier Mappings have priority
     */
    public boolean isPriorityIdentifiers() {
        return priorityIdentifiers;
    }

    /**
     * If identifiers are the priority, they will be checked first.
     * If the identifiers have conflicts, then no Mapping will be made.
     * Sequence will only be used if the identifiers could not find a Mapping.
     * If sequence is also set to priority, both sequence and identifiers must return a consistent Mapping.
     * If neither is set to priority, then if either Maps without conflicting with the other, that mapping is used.
     * @param priorityIdentifiers   Should priority be given to Mapping identifiers.
     */
    public void setPriorityIdentifiers(boolean priorityIdentifiers) {
        this.priorityIdentifiers = priorityIdentifiers;

    }

    /**
     * Gives the priority of Mapping the sequence.
     * @return  is priority given to the sequence.
     */
    public boolean isPrioritySequence() {
        return prioritySequence;
    }

    /**
     * If sequence is the priority, it will be checked first.
     * If the sequence does not Map, then the identifiers will be tried.
     * If identifiers are also set to priority, both sequence and identifiers must return a consistent Mapping.
     * If neither is set to priority, then if either Maps without conflicting with the other, that mapping is used.
     * @param prioritySequence whether the sequence has priority
     */
    public void setPrioritySequence(boolean prioritySequence) {
        this.prioritySequence = prioritySequence;
    }

    /**
     * Sets a Map listener for this Mapping service.
     * @param listener the new Map listener
     */
    public void setListener(ProteinMapperListener listener){
        this.listener = listener;
    }

    /**
     * gives the Map listener that is listening to this Mapping service.
     * @return  the current Map listener
     */
    public ProteinMapperListener getListener( ){
        return listener;
    }
}
