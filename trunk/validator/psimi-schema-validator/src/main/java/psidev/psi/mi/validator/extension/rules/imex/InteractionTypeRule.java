package psidev.psi.mi.validator.extension.rules.imex;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import psidev.psi.mi.validator.extension.Mi25Context;
import psidev.psi.mi.validator.extension.Mi25InteractionRule;
import psidev.psi.mi.validator.extension.rules.RuleUtils;
import psidev.psi.mi.xml.model.Interaction;
import psidev.psi.mi.xml.model.InteractionType;
import psidev.psi.tools.ontology_manager.OntologyManager;
import psidev.psi.tools.validator.MessageLevel;
import psidev.psi.tools.validator.ValidatorException;
import psidev.psi.tools.validator.ValidatorMessage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * This rule checks that each interaction has at least one interaction type and all the interaction types have valid psi-MI cross references
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>25/01/11</pre>
 */

public class InteractionTypeRule extends Mi25InteractionRule{

    private static final Log log = LogFactory.getLog(InteractionTypeRule.class);

    public InteractionTypeRule(OntologyManager ontologyManager) {
        super(ontologyManager);

        // describe the rule.
        setName("Interaction Type Check");
        setDescription("Checks that each interaction has at least one interaction type and all the interactions types should have " +
                "a valid PSI MI cross reference.");
        addTip( "See http://www.ebi.ac.uk/ontology-lookup/browse.do?ontName=MI&termId=MI%3A0190&termName=interaction%20type for the existing interaction types" );
    }

    @Override
    public Collection<ValidatorMessage> check(Interaction interaction) throws ValidatorException {

        List<ValidatorMessage> messages = new ArrayList<ValidatorMessage>();

        Mi25Context context = new Mi25Context();
        context.setInteractionId(interaction.getId());

        if (interaction.hasInteractionTypes()){
            Collection<InteractionType> interactionTypes = interaction.getInteractionTypes();

            for (InteractionType type : interactionTypes){
                RuleUtils.checkPsiMIXRef(type, messages, context, this, RuleUtils.INTERACTION_TYPE);
            }
        }
        else {
            messages.add( new ValidatorMessage( "The interaction does not have any interaction types. At least one interaction type is required by IMEx.'",
                    MessageLevel.ERROR,
                    context,
                    this ) );
        }

        return messages;
    }

    public String getId() {
        return "R40";
    }
}
