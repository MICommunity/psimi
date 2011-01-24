package psidev.psi.mi.validator.extension.rules.mimix;

import psidev.psi.mi.validator.extension.Mi25Context;
import psidev.psi.mi.xml.model.Participant;
import psidev.psi.tools.ontology_manager.OntologyManager;
import psidev.psi.tools.validator.MessageLevel;
import psidev.psi.tools.validator.ValidatorException;
import psidev.psi.tools.validator.ValidatorMessage;
import psidev.psi.tools.validator.rules.codedrule.ObjectRule;

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

public class ExperimentalRoleRule  extends ObjectRule<Participant> {

    public ExperimentalRoleRule( OntologyManager ontologyManager ) {
        super( ontologyManager );

        // describe the rule.
        setName( "Experimental Role Check" );

        setDescription( "Check that each interaction's participant has at least one experimental role." );

        addTip( "Experimental role terms can be found in the PSI-MI ontology under term MI:0495" );
    }

    @Override
    public boolean canCheck(Object t) {
        if (t instanceof Participant){
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
    public Collection<ValidatorMessage> check( Participant participant ) throws ValidatorException {

        // list of messages to return
        List<ValidatorMessage> messages = new ArrayList<ValidatorMessage>();

        // write the rule here ...
        int participantId = participant.getId();
        if ( !participant.hasExperimentalRoles() ) {
            final Mi25Context context = buildContext( participantId );
            messages.add( new ValidatorMessage( "Interaction's participants should have at least one " +
                    "experimental role; found 0.",
                    MessageLevel.ERROR,
                    context,
                    this ) );
        }

        return messages;
    }

    private Mi25Context buildContext( int participantId ) {
        Mi25Context context;
        context = new Mi25Context();
        context.setParticipantId( participantId );
        return context;
    }
}
