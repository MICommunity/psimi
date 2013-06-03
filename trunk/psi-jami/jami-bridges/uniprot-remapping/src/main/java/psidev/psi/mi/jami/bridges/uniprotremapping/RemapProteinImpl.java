package psidev.psi.mi.jami.bridges.uniprotremapping;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import uk.ac.ebi.intact.model.BioSource;
import uk.ac.ebi.intact.protein.mapping.actions.ActionName;
import uk.ac.ebi.intact.protein.mapping.model.actionReport.MappingReport;
import uk.ac.ebi.intact.protein.mapping.model.contexts.IdentificationContext;
import uk.ac.ebi.intact.protein.mapping.model.contexts.UpdateContext;
import uk.ac.ebi.intact.protein.mapping.results.IdentificationResults;
import uk.ac.ebi.intact.protein.mapping.strategies.StrategyForProteinUpdate;
import uk.ac.ebi.intact.protein.mapping.strategies.exceptions.StrategyException;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 03/06/13
 * Time: 13:50
 */
public class RemapProteinImpl {

    public static final Log log = LogFactory.getLog(RemapProteinImpl.class);

    UpdateContext context;
    StrategyForProteinUpdate strategy;

    public RemapProteinImpl(){
        context = new UpdateContext();
        this.strategy = new StrategyForProteinUpdate();
        this.strategy.enableIsoforms(false);
        this.strategy.setBasicBlastProcessRequired(false);
    }


    public RemapReportImpl runRemap(){
        try {
            IdentificationResults results = strategy.identifyProtein(context);

            return generateReport(results);
        } catch (StrategyException e) {
            log.info("didn't get a return because");
            e.printStackTrace();
        }
        return null;
    }


    public void setIdentifier(String database, String identifier){
        context.addIdentifier(database, identifier);
    }

    public void setSequence(String sequence){
        context.setSequence(sequence);
    }

    public void setOrganism(String taxId){
        //The biosource short label is never used in the remapping.
        context.setOrganism(new BioSource("shortLabel", taxId));
    }

    public void clean(){
        context.clean();
    }


    private RemapReportImpl generateReport(IdentificationResults results){
        RemapReportImpl report = new RemapReportImpl();

        log.info("status "+results.getLastAction().getStatusLabel());

        for(Object a :results.getListOfActions()){
            MappingReport r = (MappingReport)a;
            log.info("Object has status: "+r.getStatus());
            log.info(r.getName());

            for(String s : r.getPossibleAccessions()){
                log.info("Possible acs:"+s);
            }
            for(String s: r.getWarnings()){
                log.info("warn: "+s);
            }
        }


        log.info(results.getLastAction().getName());
        for(String s : results.getLastAction().getPossibleAccessions()){
            log.info(s);
        }

        log.info("getFinalUniprotId "+results.getFinalUniprotId());
        log.info("hasUniqueUniprotId "+results.hasUniqueUniprotId());


        report.setFinalUniprotId(results.getFinalUniprotId());
        //hasUniqueUniprotId();
        //List<T> getListOfActions();
        //boolean addActionReport(T report);
        //boolean removeActionReport(T report);
        //T getLastAction();
        //List<T> getActionsByName(ActionName name);

        return report;
    }

}
