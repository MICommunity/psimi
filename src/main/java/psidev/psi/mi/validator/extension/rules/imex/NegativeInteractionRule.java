package psidev.psi.mi.validator.extension.rules.imex;

import psidev.psi.mi.validator.extension.Mi25Context;
import psidev.psi.mi.validator.extension.Mi25InteractionRule;
import psidev.psi.mi.xml.model.Interaction;
import psidev.psi.tools.ontology_manager.OntologyManager;
import psidev.psi.tools.validator.MessageLevel;
import psidev.psi.tools.validator.ValidatorException;
import psidev.psi.tools.validator.ValidatorMessage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * This rule checks that the interaction is not negative
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>19/04/11</pre>
 */

public class NegativeInteractionRule extends Mi25InteractionRule{
    public NegativeInteractionRule(OntologyManager ontologyManager) {
        super(ontologyManager);

        // describe the rule.
        setName( "Negative interaction check" );

        setDescription( "Negative interactions are currently outside " +
                "of the remit of IMEx and should be removed from the file." );
    }

    @Override
    public Collection<ValidatorMessage> check(Interaction interaction) throws ValidatorException {

        // list of messages to return
        List<ValidatorMessage> messages = new ArrayList<ValidatorMessage>();

        int interactorId = interaction.getId();
        Mi25Context context = new Mi25Context();
        context.setId(interactorId);

        if (interaction.isNegative()){
            messages.add( new ValidatorMessage( "Negative interactions are currently outside of the remit of IMEx and " +
                            "should be removed from the record.",
                            MessageLevel.WARN,
                            context,
                            this ) );
        }

        return messages;
    }

    public String getId() {
        return "R39";
    }
}
