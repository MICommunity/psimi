package psidev.psi.mi.validator.extension.rules.psimi;

import psidev.psi.mi.jami.model.InteractionEvidence;
import psidev.psi.mi.validator.extension.MiContext;
import psidev.psi.mi.validator.extension.rules.AbstractMIRule;
import psidev.psi.mi.validator.extension.rules.RuleUtils;
import psidev.psi.tools.ontology_manager.OntologyManager;
import psidev.psi.tools.validator.MessageLevel;
import psidev.psi.tools.validator.ValidatorException;
import psidev.psi.tools.validator.ValidatorMessage;

import java.util.Collection;
import java.util.Collections;

/**
 * Rule to check that an interaction evidence has a non null experiment
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>27/03/13</pre>
 */

public class MissingInteractionExperimentRule extends AbstractMIRule<InteractionEvidence> {

    public MissingInteractionExperimentRule(OntologyManager ontologyManager) {
        super(ontologyManager, InteractionEvidence.class);
        setName( "Interaction's experiment check" );

        setDescription( "Check that each interaction evidence has an experiment." );
    }

    @Override
    public Collection<ValidatorMessage> check(InteractionEvidence interactionEvidence) throws ValidatorException {

        if (interactionEvidence.getExperiment() == null){
            // list of messages to return
            MiContext interactionContext = RuleUtils.buildContext(interactionEvidence, "interaction");
            return Collections.singletonList(new ValidatorMessage("Interaction evidences must have an experiment.'",
                    MessageLevel.ERROR,
                    interactionContext,
                    this));
        }
        return Collections.EMPTY_LIST;
    }

    public String getId() {
        return "R20";
    }
}
