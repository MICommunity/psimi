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
 * Created with IntelliJ IDEA.
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


    /**
     * Remaps the provided protein to a uniprot identifier.
     * @param proteinToRemap     the protein to be remapped
     */
    public void remapProtein(Protein proteinToRemap) throws BridgeFailedException {

        IdentificationContext context = new IdentificationContext();
        if(proteinToRemap.getOrganism() != null)
            context.setOrganism(new BioSource("Short", ""+proteinToRemap.getOrganism().getTaxId()));

        if(priorityIdentifiers && prioritySequence) priorityRemapping(proteinToRemap, context);
        else if(priorityIdentifiers && !prioritySequence) identifierPriorityRemapping(proteinToRemap, context);
        else if(!priorityIdentifiers && prioritySequence) sequencePriorityRemapping(proteinToRemap, context);
        else if(!priorityIdentifiers && !prioritySequence) noPriorityRemapping(proteinToRemap, context);
    }

    private void priorityRemapping(Protein proteinToRemap, IdentificationContext context)
            throws BridgeFailedException {

        IdentificationResults identifierMapping;

        if(checkingEnabled){
            HashMap<Xref, IdentificationResults> identifierMappingResults = new HashMap<Xref, IdentificationResults>();
            if(checkIdentifiersMappingConsistency(proteinToRemap , identifierMappingResults , context)){
                identifierMapping = getFirstRemappedIdentifierResultChecked(identifierMappingResults);
            }
            else{
                if(listener != null)
                    listener.onRemappingFailed(proteinToRemap, "Failed. Identifier mappings have conflicts.");
                return;
            }
        } else{
            identifierMapping = getFirstRemappedIdentifierResultUnchecked(proteinToRemap , context);
        }

        if( identifierMapping == null || ! identifierMapping.hasUniqueUniprotId()) {
            if(listener != null)
                listener.onRemappingFailed(proteinToRemap, "No remapping found for identifiers");
        } else {
            IdentificationResults sequenceMapping = getMappingForSequence(proteinToRemap , context);
            if (sequenceMapping == null || ! sequenceMapping.hasUniqueUniprotId()){
                if(listener != null) listener.onRemappingFailed(proteinToRemap, "No remapping for sequence");
                return;
            } else {
                if(identifierMapping.getFinalUniprotId().equalsIgnoreCase(sequenceMapping.getFinalUniprotId())){
                    proteinToRemap.setUniprotkb(identifierMapping.getFinalUniprotId());
                } else {
                    if(listener != null)
                        listener.onRemappingFailed(proteinToRemap , "Conflict between identifiers and sequence");
                }
            }
        }
    }

    private static void identifierPriorityRemapping(Protein proteinToRemap , IdentificationContext context)
            throws BridgeFailedException {

        IdentificationResults identifierMapping;

        if(checkingEnabled){
            HashMap<Xref, IdentificationResults> identifierMappingResults = new HashMap<Xref, IdentificationResults>();
            if(checkIdentifiersMappingConsistency(proteinToRemap , identifierMappingResults , context)){
                identifierMapping = getFirstRemappedIdentifierResultChecked(identifierMappingResults);
            }
            else{
                if(listener != null)
                    listener.onRemappingFailed(proteinToRemap, "Failed. Identifier mappings have conflicts.");
                return;
            }
        } else{
            identifierMapping = getFirstRemappedIdentifierResultUnchecked(proteinToRemap , context);
            return;
        }

        if (identifierMapping != null){
            proteinToRemap.setUniprotkb(identifierMapping.getFinalUniprotId());
            if(listener != null) listener.onGettingRemappingFromIdentifiers(proteinToRemap);
            if(listener != null) listener.onRemappingSuccessful(proteinToRemap,"Success. Identifiers have mapping.");
            return;
        }else {
            IdentificationResults sequenceMapping = getMappingForSequence(proteinToRemap , context);
            if (sequenceMapping != null && sequenceMapping.hasUniqueUniprotId()){
                proteinToRemap.setUniprotkb(sequenceMapping.getFinalUniprotId());
                if(listener != null) listener.onGettingRemappingFromSequence(proteinToRemap);
                if(listener != null) listener.onRemappingSuccessful(proteinToRemap,"Success. Sequence has mapping.");
                return;
            }else { // sequenceMapping == null
                if(listener != null) listener.onRemappingFailed(proteinToRemap ,"Failed. Neither identifier nor sequence have mappings.");
                return;
            }
        }
    }

    private static void sequencePriorityRemapping(Protein proteinToRemap , IdentificationContext context)
            throws BridgeFailedException {

        IdentificationResults sequenceMapping = getMappingForSequence(proteinToRemap , context);

        if( sequenceMapping != null && sequenceMapping.hasUniqueUniprotId() ) {
            proteinToRemap.setUniprotkb(sequenceMapping.getFinalUniprotId());
            if(listener != null) listener.onGettingRemappingFromSequence(proteinToRemap);
            if(listener != null) listener.onRemappingSuccessful(proteinToRemap,"Success. Sequence has mapping.");
            proteinToRemap.setUniprotkb(sequenceMapping.getFinalUniprotId());
            return ;
        } else {

            IdentificationResults identifierMapping;

            if(checkingEnabled){
                HashMap<Xref, IdentificationResults> identifierMappingResults = new HashMap<Xref, IdentificationResults>();
                if(checkIdentifiersMappingConsistency(proteinToRemap , identifierMappingResults , context)){
                    identifierMapping = getFirstRemappedIdentifierResultChecked(identifierMappingResults);
                }
                else{
                    if(listener != null)
                        listener.onRemappingFailed(proteinToRemap, "Failed. Identifier mappings have conflicts.");
                    return;
                }
            } else{
                identifierMapping = getFirstRemappedIdentifierResultUnchecked(proteinToRemap , context);
            }

            if (identifierMapping != null && identifierMapping.hasUniqueUniprotId()){
                proteinToRemap.setUniprotkb(identifierMapping.getFinalUniprotId());
                if(listener != null)
                    listener.onRemappingSuccessful(proteinToRemap , "");
                return;
            } else {
                if(listener != null)
                    listener.onRemappingFailed(proteinToRemap, "");
                return;
            }
        }
    }


    /**
     *
     * @param proteinToRemap
     * @param context
     * @throws BridgeFailedException
     */
    private static void noPriorityRemapping(Protein proteinToRemap, IdentificationContext context) throws BridgeFailedException {

        IdentificationResults identifierMapping;

        if(checkingEnabled){
            HashMap<Xref, IdentificationResults> identifierMappingResults = new HashMap<Xref, IdentificationResults>();
            if(checkIdentifiersMappingConsistency(proteinToRemap , identifierMappingResults , context)){
                identifierMapping = getFirstRemappedIdentifierResultChecked(identifierMappingResults);
            }
            else{
                if(listener != null)
                    listener.onRemappingFailed(proteinToRemap,"Failed. Identifier mappings have conflicts.");
                return;
            }
        } else{
            identifierMapping = getFirstRemappedIdentifierResultUnchecked(proteinToRemap , context);
            return;
        }

        IdentificationResults sequenceMapping = getMappingForSequence(proteinToRemap , context);

        if( (identifierMapping != null && identifierMapping.hasUniqueUniprotId())
                && (sequenceMapping != null && sequenceMapping.hasUniqueUniprotId()) ){
            if(identifierMapping.getFinalUniprotId().equalsIgnoreCase(sequenceMapping.getFinalUniprotId())){
                proteinToRemap.setUniprotkb(sequenceMapping.getFinalUniprotId());
                if(listener != null) listener.onRemappingSuccessful(proteinToRemap,
                        "Sequence and identifier mappings match.");
                return;
            } else {
                if(listener != null) listener.onRemappingFailed(proteinToRemap,"Neither a remapping for ");
                return;
            }


        }else if ( (identifierMapping != null && identifierMapping.hasUniqueUniprotId()) ){
            if(listener != null) listener.onRemappingSuccessful(proteinToRemap,"identifier mapping only");
            proteinToRemap.setUniprotkb(identifierMapping.getFinalUniprotId());

        }else if ( (sequenceMapping != null && sequenceMapping.hasUniqueUniprotId()) ){
            if(listener != null) listener.onRemappingSuccessful(proteinToRemap,"Sequence mapping only");
            proteinToRemap.setUniprotkb(sequenceMapping.getFinalUniprotId());
        } else {
            if(listener != null) listener.onRemappingFailed(proteinToRemap,"No mapping for sequence or identifier.");
            return;
        }
    }




    /**
     * Checks the identifier mappings for consistency and flags the results in the identifier mappings results boolean.
     * If any conflicts are found, this method sets identifierMappingResultsHasNoConflict to false.
     * @param proteinToRemap     the protein to remap
     */
    private static boolean checkIdentifiersMappingConsistency(
            Protein proteinToRemap , HashMap<Xref, IdentificationResults> identifierMappingResults ,
            IdentificationContext context)
            throws BridgeFailedException {

        String remappedUniprot = null;
        //Populate the list
        for(Xref xref : proteinToRemap.getXrefs()){
            IdentificationResults result = getMappingForXref( xref, context);
            identifierMappingResults.put(xref , result);

            if(result != null && result.hasUniqueUniprotId()){
                if (remappedUniprot != null){
                    if(! remappedUniprot.equalsIgnoreCase(result.getFinalUniprotId())){
                        //if(listener != null) listener.onIdentifierConflict(remappedUniprot, getEntry(p,x).getFinalUniprotId());
                        return false;
                    }
                }
                else{
                    remappedUniprot = result.getFinalUniprotId();
                }
            }
        }
        return true;
    }


    private static IdentificationResults getFirstRemappedIdentifierResultUnchecked(
            Protein proteinToRemap , IdentificationContext context)
            throws BridgeFailedException {

        String remappedUniprot = null;
        //Populate the list
        for(Xref xref : proteinToRemap.getXrefs()){
            IdentificationResults result = getMappingForXref(xref, context);
            if(result != null && result.hasUniqueUniprotId()){
                return result;
            }
        }
        return null;
    }

    /**
     * Will return the IdentificationResults of the first identifier which has a uniprotID.
     * If the identifierMappingResults has not been populated, it will populate as it goes.
     * If there is no entry with a uniprotID, null will be returned.
     *
     * @return      the first remapping which has a uniprot entry
     */
    private static IdentificationResults getFirstRemappedIdentifierResultChecked(
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
            //Protein proteinToRemap,
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
