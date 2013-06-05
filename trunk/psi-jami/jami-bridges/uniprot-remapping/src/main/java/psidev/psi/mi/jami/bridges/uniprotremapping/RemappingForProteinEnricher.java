package psidev.psi.mi.jami.bridges.uniprotremapping;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import uk.ac.ebi.intact.protein.mapping.actions.ActionName;
import uk.ac.ebi.intact.protein.mapping.actions.FeatureRangeCheckingProcess;
import uk.ac.ebi.intact.protein.mapping.actions.exception.ActionProcessingException;
import uk.ac.ebi.intact.protein.mapping.actions.status.Status;
import uk.ac.ebi.intact.protein.mapping.actions.status.StatusLabel;
import uk.ac.ebi.intact.protein.mapping.model.actionReport.BlastReport;
import uk.ac.ebi.intact.protein.mapping.model.actionReport.MappingReport;
import uk.ac.ebi.intact.protein.mapping.model.contexts.FeatureRangeCheckingContext;
import uk.ac.ebi.intact.protein.mapping.model.contexts.IdentificationContext;
import uk.ac.ebi.intact.protein.mapping.model.contexts.UpdateContext;
import uk.ac.ebi.intact.protein.mapping.results.IdentificationResults;
import uk.ac.ebi.intact.protein.mapping.strategies.StrategyForProteinUpdate;
import uk.ac.ebi.intact.protein.mapping.strategies.StrategyWithIdentifier;
import uk.ac.ebi.intact.protein.mapping.strategies.StrategyWithSequence;
import uk.ac.ebi.intact.protein.mapping.strategies.exceptions.StrategyException;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 04/06/13
 * Time: 10:53
 */
public class RemappingForProteinEnricher{

   /* public static final Log log = LogFactory.getLog(RemappingForProteinEnricher.class);

    StrategyWithSequence sequenceStrategy;
    StrategyWithIdentifier identifierStrategy;
    UpdateContext context;
    RemapReportImpl report = new RemapReportImpl();


    public RemappingForProteinEnricher(){

        sequenceStrategy = new StrategyWithSequence();
        sequenceStrategy.setBasicBlastRequired(false);
        sequenceStrategy.setEnableIntactSearch(false);
        sequenceStrategy.enableIsoforms(true);

        identifierStrategy = new StrategyWithIdentifier();
        identifierStrategy.enableIsoforms(true);

        context = new UpdateContext();
    }


    public RemapReportImpl identifyProtein(){//} throws StrategyException {
        report.clean();

            // Neither a sequence nor an identifier for this protein
            if (context.getSequence() == null && context.getIdentifiers().isEmpty()){
                log.info("No sequence - no Identifiers");
                report.setStatus(false, "No sequence or identifier.");

            }
            // the protein has a sequence
            else {
                if (context.getSequence() != null) {
                    log.info("There's a sequence");


                    RemapReportStep step = new RemapReportStep();
                    step.setKeyDetails(context.getSequence(), "Sequence", "Sequence");
                    try{
                        IdentificationResults results = sequenceStrategy.identifyProtein(context);
                        //logWriter(results);  //****
                        step.setUniprotId(results.getFinalUniprotId());
                        step.setStatus(results.getLastAction().getStatus().getLabel().toString());
                        step.setStatusMessage(results.getLastAction().getStatusDescription());
                        if(results.getLastAction().getWarnings() != null
                                && results.getLastAction().getWarnings().size() > 0){
                            step.getWarnings().addAll(results.getLastAction().getWarnings());
                        }
                    }catch(StrategyException e){
                        step.setStatus(RemapReportStep.StatusLabel.FAILED);
                        step.setStatusMessage(e.getMessage() + e.toString());
                    }
                    report.getRemapSteps().add(step);

                }

                if (context.getIdentifiers() != null
                        && context.getIdentifiers().size() > 0){

                    log.info("There's identifiers");
                    for (Map.Entry<String, String> entry : context.getIdentifiers().entrySet()){
                        context.setDatabaseForIdentifier(entry.getKey());
                        context.setIdentifier(entry.getValue());

                        RemapReportStep step = new RemapReportStep();
                        step.setKeyDetails(entry.getValue(), entry.getKey(), "Identifier");
                        try{
                            IdentificationResults results = identifierStrategy.identifyProtein(context);
                            //logWriter(results);  //****
                            step.setUniprotId(results.getFinalUniprotId());
                            step.setStatus(results.getLastAction().getStatus().getLabel().toString());
                            step.setStatusMessage(results.getLastAction().getStatusDescription());
                            if(results.getLastAction().getWarnings() != null
                                    && results.getLastAction().getWarnings().size() > 0){
                                step.getWarnings().addAll(results.getLastAction().getWarnings());
                            }
                        }catch(StrategyException e){
                            step.setStatus(RemapReportStep.StatusLabel.FAILED);
                            step.setStatusMessage(e.getMessage() + e.toString());
                        }

                        report.getRemapSteps().add(step);

                    }
                }
            }

        return report;
    }


    private void logWriter(IdentificationResults results){
        log.info("the final result is "+results.getFinalUniprotId());
        log.info("has unique "+results.hasUniqueUniprotId());
        log.info("There are "+results.getListOfActions().size()+" reports");
        for(Object a :results.getListOfActions()){
            log.info("-----------");
            MappingReport r = (MappingReport)a;

            log.info("status: "+r.getStatus());
            log.info("description:"+r.getStatusDescription());
            log.info("label:"+r.getStatusLabel());
            log.info("name: "+r.getName());
            log.info("Swissprot?"+r.isASwissprotEntry());

            log.info("There are this many acs :"+r.getPossibleAccessions().size());
            for(String s : r.getPossibleAccessions()){
                log.info("Possible acs:"+s);
            }
            for(String s: r.getWarnings()){
                log.info("warn: "+s);
            }
        }
    } */

}
