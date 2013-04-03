package psidev.psi.mi.validator.extension.rules.mimix;

import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.ParticipantEvidence;
import psidev.psi.mi.validator.extension.Mi25Context;
import psidev.psi.mi.validator.extension.MiParticipantRule;
import psidev.psi.mi.validator.extension.rules.RuleUtils;
import psidev.psi.tools.ontology_manager.OntologyManager;
import psidev.psi.tools.validator.ValidatorException;
import psidev.psi.tools.validator.ValidatorMessage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * <b> Checks that participants have at least one experimental role.</b>
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>24/01/11</pre>
 */

public class ExperimentalRoleRule  extends MiParticipantRule {

    public ExperimentalRoleRule( OntologyManager ontologyManager ) {
        super( ontologyManager );

        // describe the rule.
        setName( "Participant's experimental Role Check" );

        setDescription( "Check that each interaction's participant has at least one experimental role. Each experimental role must refer to a valid term in the PSI-MI ontology." );

        addTip( "Experimental role terms can be found in the PSI-MI ontology under term MI:0495" );
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
        CvTerm experimentalRole = participant.getExperimentalRole();

        final Mi25Context context = RuleUtils.buildContext(experimentalRole, "participant's experimental role");
        context.addAssociatedContext(RuleUtils.buildContext(participant, "participant"));

        RuleUtils.checkUniquePsiMIXRef(experimentalRole, messages, context, this);

        return messages;
    }

    public String getId() {
        return "R55";
    }
}
