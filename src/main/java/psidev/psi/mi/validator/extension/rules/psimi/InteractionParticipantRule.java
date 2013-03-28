package psidev.psi.mi.validator.extension.rules.psimi;

import psidev.psi.mi.jami.model.InteractionEvidence;
import psidev.psi.mi.validator.extension.Mi25Context;
import psidev.psi.mi.validator.extension.Mi25InteractionRule;
import psidev.psi.mi.validator.extension.rules.RuleUtils;
import psidev.psi.tools.ontology_manager.OntologyManager;
import psidev.psi.tools.validator.MessageLevel;
import psidev.psi.tools.validator.ValidatorException;
import psidev.psi.tools.validator.ValidatorMessage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Checks that an interaction contains at least one participant
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>06/04/11</pre>
 */

public class InteractionParticipantRule extends Mi25InteractionRule {

    public InteractionParticipantRule(OntologyManager ontologyManager) {
        super(ontologyManager);

        // describe the rule.
        setName( "Interaction participant(s) check" );
        setDescription( "Checks that each interaction has a at least one participant." );
    }

    @Override
    public Collection<ValidatorMessage> check(InteractionEvidence interaction) throws ValidatorException {

        // list of messages to return
        List<ValidatorMessage> messages = new ArrayList<ValidatorMessage>();

        Mi25Context context = RuleUtils.buildContext(interaction);

        // Check participants
        if (interaction.getParticipantEvidences().isEmpty()){
            messages.add( new ValidatorMessage( "The interaction does not have any participants and it is required by IMEx.",
                        MessageLevel.ERROR,
                        context,
                        this ) );

        }

        return messages;
    }

    public String getId() {
        return "R26";
    }
}
