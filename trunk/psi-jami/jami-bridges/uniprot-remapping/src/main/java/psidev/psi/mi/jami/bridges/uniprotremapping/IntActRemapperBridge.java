package psidev.psi.mi.jami.bridges.uniprotremapping;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import psidev.psi.mi.jami.model.Organism;
import psidev.psi.mi.jami.model.Xref;
import uk.ac.ebi.intact.model.BioSource;
import uk.ac.ebi.intact.protein.mapping.model.contexts.UpdateContext;
import uk.ac.ebi.intact.protein.mapping.results.IdentificationResults;
import uk.ac.ebi.intact.protein.mapping.results.impl.DefaultIdentificationResults;
import uk.ac.ebi.intact.protein.mapping.strategies.StrategyWithIdentifier;
import uk.ac.ebi.intact.protein.mapping.strategies.StrategyWithSequence;
import uk.ac.ebi.intact.protein.mapping.strategies.exceptions.StrategyException;


/**
 * Created with IntelliJ IDEA.
 *
 * @author: Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 05/06/13
 * Time: 09:53
 */
public class IntActRemapperBridge implements RemapperBridge {


    public static final Log log = LogFactory.getLog(IntActRemapperBridge.class);

    private StrategyWithSequence sequenceStrategy;
    private StrategyWithIdentifier identifierStrategy;
    private UpdateContext context;

    public IntActRemapperBridge( ){

        sequenceStrategy = new StrategyWithSequence();
        sequenceStrategy.setBasicBlastRequired(false);
        sequenceStrategy.setEnableIntactSearch(false);
        sequenceStrategy.enableIsoforms(true);

        identifierStrategy = new StrategyWithIdentifier();
        identifierStrategy.enableIsoforms(true);

        context = new UpdateContext();
    }

    public void setSequence(String sequence){
        if(sequence != null && sequence.length() > 0) context.setSequence(sequence);
    }
    private void setOrganism(String taxId){
        //The biosource short label is never used in the remapping.
        context.setOrganism(new BioSource("shortLabel", taxId));
    }
    public void setOrganism(Organism organism){
        if(organism.getTaxId() > 0) setOrganism(""+organism.getTaxId());
    }

    public IdentificationResults getIdentifierResult(Xref identifier){
        String value =  identifier.getId();
        String database = null;

        if(identifier.getDatabase() != null){
            database = identifier.getDatabase().getMIIdentifier();
            if( database == null) database = identifier.getDatabase().getFullName();
            if( database == null) database = identifier.getDatabase().getShortName();
        }
        if(database != null && value != null) return getIdentifierResult(database, value);
        else return null;
    }


    private IdentificationResults getIdentifierResult(String database, String identifier){
        context.setDatabaseForIdentifier(database);
        context.setIdentifier(identifier);

        try{
            return identifierStrategy.identifyProtein(context);
        }catch(StrategyException e){
            return new DefaultIdentificationResults();

        }
    }

    public IdentificationResults getSequenceMapping(){
        if (context.getSequence() != null) {
            try{
                return sequenceStrategy.identifyProtein(context);
            }catch(StrategyException e){
                return new DefaultIdentificationResults();
            }
        }
        return new DefaultIdentificationResults();
    }


    public void clean(){
        context.clean();
    }

}
