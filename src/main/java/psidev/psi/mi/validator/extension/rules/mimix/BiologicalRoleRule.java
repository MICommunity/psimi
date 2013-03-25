package psidev.psi.mi.validator.extension.rules.mimix;

import psidev.psi.mi.jami.model.ParticipantEvidence;
import psidev.psi.mi.validator.extension.Mi25Context;
import psidev.psi.mi.validator.extension.rules.RuleUtils;
import psidev.psi.tools.ontology_manager.OntologyManager;
import psidev.psi.tools.validator.ValidatorException;
import psidev.psi.tools.validator.ValidatorMessage;
import psidev.psi.tools.validator.rules.codedrule.ObjectRule;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * <b> Checks that participants have a single biological role with a valid psi mi cross reference.</b>
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>24/01/11</pre>
 */

public class BiologicalRoleRule extends ObjectRule<ParticipantEvidence> {

    public BiologicalRoleRule( OntologyManager ontologyManager ) {
        super( ontologyManager );

        // describe the rule.
        setName( "Participant's Biological Role Check" );

        setDescription( "Check that each interaction's participant has a valid biological role (with one cross reference to PSI-MI ontology)." );

        addTip( "Biological role terms can be found in the PSI-MI ontology under term MI:0500" );
    }

    @Override
    public boolean canCheck(Object t) {
        if (t instanceof ParticipantEvidence){
            return true;
        }

        return false;
    }

    /**
     * Checks that participants have a single experimental role.
     *
     * @param participant to check on.
     * @return a collection of validator messages.
     * @throws psidev.psi.tools.validator.ValidatorException
     *          if we fail to retrieve the MI Ontology.
     */
    public Collection<ValidatorMessage> check( ParticipantEvidence participant ) throws ValidatorException {

        // list of messages to return
        List<ValidatorMessage> messages = new ArrayList<ValidatorMessage>();

        // write the rule here ...
        final Mi25Context context = RuleUtils.buildContext( participant );

        RuleUtils.checkPsiMIXRef(participant.getBiologicalRole(), messages, context, this, RuleUtils.BIOLOGICAL_ROLE);

        return messages;
    }

    public String getId() {
        return "R15";
    }
}
