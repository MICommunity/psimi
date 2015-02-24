package psidev.psi.mi.validator.extension.rules.imex;

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
 * This rule checks that the interaction is not negative
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>19/04/11</pre>
 */

public class NegativeInteractionRule extends AbstractMIRule<InteractionEvidence>{
    public NegativeInteractionRule(OntologyManager ontologyManager) {
        super(ontologyManager, InteractionEvidence.class);

        // describe the rule.
        setName( "Negative interaction check" );

        setDescription( "Negative interactions are currently outside " +
                "of the remit of IMEx and should be removed from the file." );
    }

    @Override
    public Collection<ValidatorMessage> check(InteractionEvidence interaction) throws ValidatorException {

        if (interaction.isNegative()){
            MiContext context = RuleUtils.buildContext(interaction, "interaction");

            return Collections.singletonList( new ValidatorMessage( "Negative interactions are currently outside of the remit of IMEx and " +
                    "should be removed from the record.",
                    MessageLevel.WARN,
                    context,
                    this ) );
        }

        return Collections.EMPTY_LIST;
    }

    public String getId() {
        return "R43";
    }
}
