package psidev.psi.mi.validator.extension.rules.mimix;

import psidev.psi.mi.validator.extension.Mi25Context;
import psidev.psi.mi.validator.extension.Mi25ExperimentRule;
import psidev.psi.mi.validator.extension.Mi25Ontology;
import psidev.psi.mi.validator.extension.rules.RuleUtils;
import psidev.psi.mi.xml.model.ExperimentDescription;
import psidev.psi.mi.xml.model.InteractionDetectionMethod;
import psidev.psi.tools.ontology_manager.OntologyManager;
import psidev.psi.tools.validator.MessageLevel;
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
        setDescription( "Checks that each experiment has at least one Interaction Detection Method (MI:0001) and that all Interaction Detection Methods are valid." );
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
    public Collection<ValidatorMessage> check( ExperimentDescription experiment ) throws ValidatorException {

        // list of messages to return
        List<ValidatorMessage> messages = new ArrayList<ValidatorMessage>();

        int experimentId = experiment.getId();

        Mi25Context context = new Mi25Context();
        context.setExperimentId( experimentId );

        if (experiment.getInteractionDetectionMethod() == null){
             messages.add( new ValidatorMessage( " The experiment does not have an Interaction Detection Method ( can be any child of MI:0001) and it is required for MIMIx",
                                                    MessageLevel.ERROR,
                                                    context,
                                                    this ) );
        }
        else {
            InteractionDetectionMethod intMethod = experiment.getInteractionDetectionMethod();
            final Mi25Ontology ontology = getMiOntology();
            RuleUtils.checkPsiMIXRef(intMethod, messages, context, this, ontology, "MI:0001");
        }

        return messages;
    }
}
