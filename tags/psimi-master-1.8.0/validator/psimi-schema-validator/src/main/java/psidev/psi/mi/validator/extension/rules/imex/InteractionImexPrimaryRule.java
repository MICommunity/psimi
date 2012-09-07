package psidev.psi.mi.validator.extension.rules.imex;

import psidev.psi.mi.validator.extension.Mi25Context;
import psidev.psi.mi.validator.extension.Mi25InteractionRule;
import psidev.psi.mi.validator.extension.rules.PublicationRuleUtils;
import psidev.psi.mi.validator.extension.rules.RuleUtils;
import psidev.psi.mi.xml.model.DbReference;
import psidev.psi.mi.xml.model.Interaction;
import psidev.psi.tools.ontology_manager.OntologyManager;
import psidev.psi.tools.validator.MessageLevel;
import psidev.psi.tools.validator.ValidatorException;
import psidev.psi.tools.validator.ValidatorMessage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Checks that each interaction has a IMEx ID
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>12-Jan-2011</pre>
 */
public class InteractionImexPrimaryRule extends Mi25InteractionRule{

    public InteractionImexPrimaryRule( OntologyManager ontologyMaganer ) {
        super( ontologyMaganer );

        // describe the rule.
        setName( "Interaction Imex-primary cross reference check" );
        setDescription( "Checks that each interaction has a at least one cross reference 'imex-primary' and that all the imex" +
                "IDs are correct." );
        addTip( "All interactions should have an IMEx ID (IM-xxx-xxx) when there is a cross reference type: imex-primary" );
        addTip( "The PSI-MI identifier for imex-primary is: MI:0662" );
    }

    /**
     * Make sure that an interaction has a valid IMEX id in its xref.
     *
     * @param interaction an interaction to check on.
     * @return a collection of validator messages.
     */
    public Collection<ValidatorMessage> check( Interaction interaction ) throws ValidatorException {

        // list of messages to return
        List<ValidatorMessage> messages = new ArrayList<ValidatorMessage>();

        int interactionId = interaction.getId();

        Mi25Context context = new Mi25Context();
        context.setInteractionId( interactionId );

        // Check xRef
        if (interaction.hasXref()){
            Collection<DbReference> dbReferences = interaction.getXref().getAllDbReferences();

            // search for reference type: imex-primary (should not be empty)
            Collection<DbReference> imexReferences = RuleUtils.findByReferenceType( dbReferences, "MI:0662", "imex-primary", messages, context, this );

            // At least one cross reference type 'imex-primary' is required and the Imex ID must be valid.
            if (imexReferences.isEmpty()){
                messages.add( new ValidatorMessage( "The interaction has " + dbReferences.size() + " cross reference(s) but none of them has a reference type set to 'imex-primary'. Each interaction should have" +
                        "a cross reference 'imex-primary' " +
                        "which extends the cross reference 'imex-primary' of the experiment.",
                        MessageLevel.WARN,
                        context,
                        this ) );
            }
            else {
                PublicationRuleUtils.checkImexInteractionId(imexReferences, messages, context, this);
            }

        }
        else {
            messages.add( new ValidatorMessage( "The interaction does not have any cross references. At least one cross reference with a reference type set" +
                    " to 'imex-primary' (MI:0662) is recommended for IMEx.",
                    MessageLevel.WARN,
                    context,
                    this ) );
        }

        return messages;
    }
}
