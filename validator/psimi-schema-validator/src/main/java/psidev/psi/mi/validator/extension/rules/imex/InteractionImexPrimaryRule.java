package psidev.psi.mi.validator.extension.rules.imex;

import psidev.psi.mi.jami.model.InteractionEvidence;
import psidev.psi.mi.validator.extension.rules.AbstractMIRule;
import psidev.psi.mi.validator.extension.rules.PublicationRuleUtils;
import psidev.psi.tools.ontology_manager.OntologyManager;
import psidev.psi.tools.validator.ValidatorException;
import psidev.psi.tools.validator.ValidatorMessage;

import java.util.Collection;

/**
 * Checks that each interaction has a IMEx ID
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>12-Jan-2011</pre>
 */
public class InteractionImexPrimaryRule extends AbstractMIRule<InteractionEvidence>{

    public InteractionImexPrimaryRule( OntologyManager ontologyMaganer ) {
        super( ontologyMaganer, InteractionEvidence.class );

        // describe the rule.
        setName( "Interaction Imex-primary cross reference check" );
        setDescription( "Checks that each interaction imex" +
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
    public Collection<ValidatorMessage> check( InteractionEvidence interaction ) throws ValidatorException {

        // Check xRef
        return PublicationRuleUtils.checkImexInteractionId(interaction.getImexId(), interaction, this);
    }

    public String getId() {
        return "R39";
    }
}
