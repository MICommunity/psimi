package psidev.psi.mi.jami.bridges.uniprot.remapping;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import psidev.psi.mi.jami.model.Organism;
import psidev.psi.mi.jami.model.Protein;
import psidev.psi.mi.jami.model.Xref;
import uk.ac.ebi.intact.model.BioSource;
import uk.ac.ebi.intact.protein.mapping.model.contexts.IdentificationContext;
import uk.ac.ebi.intact.protein.mapping.results.IdentificationResults;
import uk.ac.ebi.intact.protein.mapping.results.impl.DefaultIdentificationResults;
import uk.ac.ebi.intact.protein.mapping.strategies.StrategyWithIdentifier;
import uk.ac.ebi.intact.protein.mapping.strategies.StrategyWithSequence;
import uk.ac.ebi.intact.protein.mapping.strategies.exceptions.StrategyException;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 07/06/13
 * Time: 12:27
 */
public class IntactProteinRemapper
        extends AbstractProteinRemapper {


    public static final Log log = LogFactory.getLog(IntactProteinRemapper.class);

    private StrategyWithSequence sequenceStrategy;
    private StrategyWithIdentifier identifierStrategy;
    private IdentificationContext context;

    public IntactProteinRemapper( ){
        super();
        sequenceStrategy = new StrategyWithSequence();
        sequenceStrategy.setBasicBlastRequired(false);
        sequenceStrategy.setEnableIntactSearch(false);
        sequenceStrategy.enableIsoforms(true);

        identifierStrategy = new StrategyWithIdentifier();
        identifierStrategy.enableIsoforms(true);

        context = new IdentificationContext();
    }


    @Override
    public void remapProtein(Protein p) {
        context.clean();
        if(p.getSequence() != null) setSequence(p.getSequence());
        if(p.getOrganism() != null) setOrganism(p.getOrganism());

        super.remapProtein(p);
    }

    @Override
    protected IdentificationResults getMappingForXref(Protein p, Xref x){
        if(!identifierMappingResults.containsKey(x)){
            identifierMappingResults.put(x,getIdentifierResult(x));
        }
        return identifierMappingResults.get(x);
    }

    @Override
    protected IdentificationResults getMappingForSequence(Protein p){
        if(sequenceMappingResult == null){
            if (context.getSequence() != null) {
                try{
                    sequenceMappingResult = sequenceStrategy.identifyProtein(context);
                }catch(StrategyException e){
                    log.warn("Encountered a StrategyException " +
                            "when searching for sequence");
                    sequenceMappingResult = new DefaultIdentificationResults();
                }
            }
            else {
                log.warn("Sequence is not set in context.");
                sequenceMappingResult = new DefaultIdentificationResults();
            }
        }
        return sequenceMappingResult;
    }

    private IdentificationResults getIdentifierResult(Xref identifier){
        String value =  identifier.getId();
        String database = null;

        //Find a way to identify the database
        if(identifier.getDatabase() != null){
            database = identifier.getDatabase().getMIIdentifier();
            if( database == null) database = identifier.getDatabase().getShortName();
        }
        //If there's an identity, search else return an empty result.
        if(database != null && value != null) return getIdentifierResult(database, value);
        else return new DefaultIdentificationResults();
    }

    private IdentificationResults getIdentifierResult(String database, String identifier){
        context.setDatabaseForIdentifier(database);
        context.setIdentifier(identifier);
        IdentificationResults temp;
        try{
            temp = identifierStrategy.identifyProtein(context);
        }catch(StrategyException e){
            log.info("Encountered a StrategyException " +
                    "when searching for the AC ["+identifier+"] " +
                    "in the database ["+database+"]");
            temp = new DefaultIdentificationResults();
        }
        return temp;
    }

    private void setSequence(String sequence){
        if(sequence != null && sequence.length() > 0) context.setSequence(sequence);
    }
    private void setOrganism(String taxId){
        //The biosource short label is never used in the remapping.
        context.setOrganism(new BioSource("shortLabel", taxId));
    }
    private void setOrganism(Organism organism){
        if(organism.getTaxId() > 0) setOrganism(""+organism.getTaxId());
    }
}
