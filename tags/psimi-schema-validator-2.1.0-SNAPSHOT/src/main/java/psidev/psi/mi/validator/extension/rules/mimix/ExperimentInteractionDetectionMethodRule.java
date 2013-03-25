package psidev.psi.mi.validator.extension.rules.mimix;

import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Experiment;
import psidev.psi.mi.validator.extension.Mi25Context;
import psidev.psi.mi.validator.extension.Mi25ExperimentRule;
import psidev.psi.mi.validator.extension.rules.RuleUtils;
import psidev.psi.tools.ontology_manager.OntologyManager;
import psidev.psi.tools.validator.ValidatorException;
import psidev.psi.tools.validator.ValidatorMessage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Checks if an experiment have a at least one Interaction Detection Method and that all the interaction detection methods are valid.
 *
 * Rule Id = 5. See http://docs.google.com/Doc?docid=0AXS9Q1JQ2DygZGdzbnZ0Ym5fMHAyNnM3NnRj&hl=en_GB&pli=1
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id: ExperimentInteractionDetectionMethodRule.java 56 2010-01-22 15:37:09Z marine.dumousseau@wanadoo.fr $
 * @since 2.0
 */
public class ExperimentInteractionDetectionMethodRule extends Mi25ExperimentRule {

    public ExperimentInteractionDetectionMethodRule( OntologyManager ontologyMaganer ) {
        super( ontologyMaganer );

        // describe the rule.
        setName( "Experiment Interaction Detection Method check" );
        setDescription( "Checks that each experiment has a valid Interaction Detection Method (any children of MI:0001)." );
        addTip( "Your experiment should have an interaction detection method" );
        addTip( "Each interaction detection method should have a PSI MI cross reference with a reference type set to identical object (MI:0356)" );
        addTip( "Any child of MI:0001 is an interaction detection method. You can look at http://www.ebi.ac.uk/ontology-lookup/browse.do?ontName=MI." );
        addTip( "Each participant identification method should have a PSI MI cross reference with a reference type set to identical object (MI:0356)" );
        addTip( "The PSI-MI identifier for identical object is: MI:0356" );
    }

    /**
     * Make sure that an experiment has an Interaction Detection Method and that all Interaction Detection Methods are valid.
     *
     * @param experiment an experiment to check on.
     * @return a collection of validator messages.
     */
    public Collection<ValidatorMessage> check( Experiment experiment ) throws ValidatorException {

        // list of messages to return
        List<ValidatorMessage> messages = new ArrayList<ValidatorMessage>();

        Mi25Context context = RuleUtils.buildContext(experiment.getInteractionDetectionMethod(), "interaction detection method");
        context.addAssociatedContext(RuleUtils.buildContext(experiment, "experiment"));

        CvTerm intMethod = experiment.getInteractionDetectionMethod();
        RuleUtils.checkPsiMIXRef(intMethod, messages, context, this, "MI:0001");

        return messages;
    }

    public String getId() {
        return "R19";
    }
}
