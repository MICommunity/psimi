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

    private ProteinMapperListener listener = null;

    private boolean checkingEnabled = true;
    private boolean priorityIdentifiers = true;
    private boolean prioritySequence = true;

    private Stack<String> report;

    private StrategyWithIdentifier strategyWithIdentifier;
    private StrategyWithSequence strategyWithSequence;

    private Map<Xref, IdentificationResults> identifierMappingResults;

    public UniprotProteinMapper() {
        report = new Stack<String>();
        this.strategyWithIdentifier = new StrategyWithIdentifier();
        this.strategyWithSequence = new StrategyWithSequence();
        identifierMappingResults = new HashMap<Xref, IdentificationResults>();
    }

    /**
     * Maps the provided protein to a uniprot identifier.
     * @param proteinToMap     the protein to be Mapped
     */
    public void mapProtein(Protein proteinToMap) throws BridgeFailedException {
        if(proteinToMap == null){
            throw new IllegalArgumentException("Cannot remap a null protein");
        }
        clear();

        IdentificationContext context = new IdentificationContext();
        if( proteinToMap.getOrganism() != null )
            context.setOrganism(new BioSource(proteinToMap.getOrganism().getCommonName() != null ? proteinToMap.getOrganism().getCommonName() : "not specified", Integer.toString(proteinToMap.getOrganism().getTaxId())));

        if(priorityIdentifiers == prioritySequence) mapWithBothPriorities(proteinToMap, context);
        else if(priorityIdentifiers) mapWithPriorityToIdentifiers(proteinToMap, context);
        else if(prioritySequence) mapWithPriorityToSequence(proteinToMap, context);
    }

    private void clear() {
        report.clear();
        this.strategyWithSequence.getListOfActionReports().clear();
        this.strategyWithIdentifier.getListOfActionReports().clear();
        this.identifierMappingResults.clear();
    }

    /**
     * Maps the protein, requiring a consistent, non null, mapping between the sequence and identifier.
     * If either does not return a Mapping, this will fail.
     * @param proteinToMap
     * @param context
     * @throws BridgeFailedException
     */
    private void mapWithBothPriorities(Protein proteinToMap, IdentificationContext context)
            throws BridgeFailedException {

        log.info("Priority to identifiers and sequence.");
        IdentificationResults identifierResult = null;
        if(checkingEnabled){
            mapAllIdentifiersFor(proteinToMap, context);
            if(!areAllIdentifierMappingsConsistent()){
                if(listener != null)
                    listener.onToBeReviewedMapping(proteinToMap, report);
                return;
            }
            identifierResult = getFirstSuccessfulResult();
        }
        else{
            identifierResult = mapFirstValidIdentifierFor(proteinToMap, context);
        }

        IdentificationResults sequenceMapping = identifyProtein(proteinToMap.getSequence(), context);
        if (sequenceMapping == null || ! sequenceMapping.hasUniqueUniprotId()){
            report.push("Sequence mapping FAILED: No mapping found for sequence.");
        }
        // both sequence and identifiers have results
        if (sequenceMapping != null && identifierResult != null){
            if (sequenceMapping.getFinalUniprotId() != null && !sequenceMapping.getFinalUniprotId().equals(identifierResult.getFinalUniprotId())){
                report.push("Mapping consistency TO BE REVIEWED: The identifiers can map to a different uniprot protein from the sequence.");
                if(listener != null) listener.onToBeReviewedMapping(proteinToMap, report);
                return;
            }
            if(sequenceMapping.getFinalUniprotId() != null){
                proteinToMap.setUniprotkb(sequenceMapping.getFinalUniprotId());
            }
            else{
                proteinToMap.setUniprotkb(identifierResult.getFinalUniprotId());
            }
            if(listener != null){
                listener.onSuccessfulMapping(proteinToMap ,report );
            }
        }
        // only the sequence has results
        else if (sequenceMapping != null && sequenceMapping.hasUniqueUniprotId()){
            proteinToMap.setUniprotkb(sequenceMapping.getFinalUniprotId());
            if(listener != null){
                listener.onSuccessfulMapping(proteinToMap ,report );
            }
        }
        // only the identifiers have results
        else if (identifierResult != null){
            proteinToMap.setUniprotkb(identifierResult.getFinalUniprotId());
            if(listener != null){
                listener.onSuccessfulMapping(proteinToMap ,report );
            }
        }
        // failed mapping
        else{
            if(listener != null) listener.onFailedMapping(proteinToMap, report);
            return;
        }
    }

    private void mapAllIdentifiersFor(Protein prot, IdentificationContext context) throws BridgeFailedException {
        //Populate the list
        for(Xref xref : prot.getIdentifiers()){
            IdentificationResults result = identifyProtein(xref, context);
            identifierMappingResults.put(xref, result);
            if (result != null){
                for (Object obj : result.getListOfActions()){
                    report.push(obj.toString());
                }
            }
        }
    }

    private IdentificationResults mapFirstValidIdentifierFor(Protein prot, IdentificationContext context) throws BridgeFailedException {
        //Populate the list
        for(Xref xref : prot.getIdentifiers()){
            IdentificationResults result = identifyProtein(xref, context);
            if (result != null){
                for (Object obj : result.getListOfActions()){
                    report.push(obj.toString());
                }
                if (result.getFinalUniprotId() != null){
                    return result;
                }
            }
        }

        return null;
    }

    /**
     * Maps the protein using the identifiers. If an identifier mapping cannot be found, the sequence will be used.
     * @param proteinToMap
     * @param context
     * @throws BridgeFailedException
     */
    private void mapWithPriorityToIdentifiers(Protein proteinToMap, IdentificationContext context)
            throws BridgeFailedException {

        log.info("Priority to identifiers.");

        IdentificationResults identifierResult = null;
        if(checkingEnabled){
            mapAllIdentifiersFor(proteinToMap, context);
            if(!areAllIdentifierMappingsConsistent()){
                if(listener != null)
                    listener.onToBeReviewedMapping(proteinToMap, report);
                return;
            }
            identifierResult = getFirstSuccessfulResult();
        }
        else{
            identifierResult = mapFirstValidIdentifierFor(proteinToMap, context);
        }
        // we stop here if the identifiers could map to one protein
        if (identifierResult != null){
            proteinToMap.setUniprotkb(identifierResult.getFinalUniprotId());
            if(listener != null){
                listener.onSuccessfulMapping(proteinToMap ,report );
            }
            return;
        }

        IdentificationResults sequenceMapping = identifyProtein(proteinToMap.getSequence(), context);
        if (sequenceMapping == null || ! sequenceMapping.hasUniqueUniprotId()){
            report.push("Sequence mapping FAILED: No mapping found for sequence.");
        }
        // only the sequence has results
        if (sequenceMapping != null && sequenceMapping.getFinalUniprotId() != null){
            proteinToMap.setUniprotkb(sequenceMapping.getFinalUniprotId());
            if(listener != null){
                listener.onSuccessfulMapping(proteinToMap ,report );
            }
        }
        // failed mapping
        else{
            if(listener != null) listener.onFailedMapping(proteinToMap, report);
            return;
        }
    }

    /**
     * Maps the protein using the sequence Mapping. If no mapping is found, the identifiers will be used.
     * @param proteinToMap
     * @param context
     * @throws BridgeFailedException
     */
    private void mapWithPriorityToSequence(Protein proteinToMap, IdentificationContext context)
            throws BridgeFailedException {
        report.push("Priority to sequence.");
        IdentificationResults sequenceMapping = identifyProtein(proteinToMap.getSequence(), context);
        if (sequenceMapping == null || ! sequenceMapping.hasUniqueUniprotId()){
            report.push("Sequence mapping FAILED: No mapping found for sequence.");
        }
        // only the sequence has results
        if (sequenceMapping != null && sequenceMapping.hasUniqueUniprotId()){
            proteinToMap.setUniprotkb(sequenceMapping.getFinalUniprotId());
            if(listener != null){
                listener.onSuccessfulMapping(proteinToMap ,report );
            }
        }

        IdentificationResults identifierResult = null;
        if(checkingEnabled){
            mapAllIdentifiersFor(proteinToMap, context);
            if(!areAllIdentifierMappingsConsistent()){
                if(listener != null)
                    listener.onToBeReviewedMapping(proteinToMap, report);
                return;
            }
            identifierResult = getFirstSuccessfulResult();
        }
        else{
            identifierResult = mapFirstValidIdentifierFor(proteinToMap, context);
        }

        if (identifierResult != null){
            proteinToMap.setUniprotkb(identifierResult.getFinalUniprotId());
            if(listener != null){
                listener.onSuccessfulMapping(proteinToMap ,report );
            }
        }
        // failed mapping
        else{
            if(listener != null) listener.onFailedMapping(proteinToMap, report);
        }
    }

    /**
     * Queries and adds the results to the map, checks the identifier mappings for consistency
     * and returns false as soon as a conflict is found.
     */
    private boolean areAllIdentifierMappingsConsistent()
            throws BridgeFailedException {

        String mappedUniprot = null;
        //Populate the list
        for(Map.Entry<Xref, IdentificationResults> entry : identifierMappingResults.entrySet()){

            IdentificationResults result = entry.getValue();
            if(result != null && result.hasUniqueUniprotId()){

                if (mappedUniprot != null){
                    if(!mappedUniprot.equals(result.getFinalUniprotId())){
                        report.push("Identifier mapping FAILED: we could remap to "+mappedUniprot + " with a previous identifier and "+entry.getKey().getId()+" can remap to another entry "+result.getFinalUniprotId());
                        return false;
                    }
                }
                else{
                    mappedUniprot = result.getFinalUniprotId();
                }
            }
        }
        if (mappedUniprot != null){
            report.push("Identifier mapping SUCCESSFUL: we could remap to "+mappedUniprot);
        }
        return true;
    }

    /**
     * Will return the IdentificationResults of the first identifier which has a uniprotID.
     * If there is no entry with a uniprotID, null will be returned.
     *
     * @return      the first Mapping which has a uniprot entry
     */
    private IdentificationResults getFirstSuccessfulResult(){

        for(Map.Entry<Xref, IdentificationResults> entry : identifierMappingResults.entrySet()){
            if(entry.getValue() != null
                    && entry.getValue().hasUniqueUniprotId()){
                return entry.getValue();
            }
        }
        return null;
    }

    protected IdentificationResults identifyProtein(
            Xref xrefToMap, IdentificationContext context)
            throws BridgeFailedException {

        String value =  xrefToMap.getId();
        String database = null;
        String databaseName = null;

        //Find a way to identify the database
        if(xrefToMap.getDatabase() != null){
            database = xrefToMap.getDatabase().getMIIdentifier();
            databaseName = xrefToMap.getDatabase().getShortName();
        }
        //If there's an identity do a search - else return an empty result.
        if((database != null || databaseName != null) && value != null){
            context.setSequence(null);
            context.setDatabaseForIdentifier(database);
            context.setDatabaseName(databaseName);
            context.setIdentifier(value);

            try{
                return strategyWithIdentifier.identifyProtein(context);
            }catch(StrategyException e){
                throw new BridgeFailedException("Failure while attempting to Map protein identifier.",e);
            }
        }

        return null;
    }

    /**
     * Finds a Mapping for the proteins sequence using the method which is implemented.
     * @param sequence the protein sequence to find a Mapping for
     * @return  The results of the query. Can be null.
     */
    protected IdentificationResults identifyProtein(String sequence, IdentificationContext context)
            throws BridgeFailedException {

        if(sequence != null && sequence.length() > 0) {
            context.setSequence(sequence);
            context.setDatabaseForIdentifier(null);
            context.setDatabaseName(null);
            context.setIdentifier(null);
            try{
                return this.strategyWithSequence.identifyProtein(context);
            }catch(StrategyException e){
                throw new BridgeFailedException("Encountered a StrategyException " +
                        "when searching for sequence", e);
            }
        }
        else {
            report.push("Sequence mapping FAILED: Could not get mapping for sequence, no sequence was available.");
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
