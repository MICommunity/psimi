package psidev.psi.mi.validator.extension.rules.imex;

import psidev.psi.mi.validator.extension.Mi25Context;
import psidev.psi.mi.validator.extension.Mi25InteractionRule;
import psidev.psi.mi.xml.model.Interaction;
import psidev.psi.mi.xml.model.Participant;
import psidev.psi.tools.ontology_manager.OntologyManager;
import psidev.psi.tools.validator.MessageLevel;
import psidev.psi.tools.validator.ValidatorException;
import psidev.psi.tools.validator.ValidatorMessage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * <b> Checks that participants have a single experimental role.</b>
 * <p/>
 *
 * @author Samuel Kerrien
 * @version $Id$
 * @since 2.0
 */
public class SingleExperimentRoleRule extends Mi25InteractionRule {

    public SingleExperimentRoleRule( OntologyManager ontologyMaganer ) {
        super( ontologyMaganer );

        // describe the rule.
        setName( "Single Experimental BiologicalRole Check" );

        setDescription( "Check that each interaction's participant has a single experimental role." );

        addTip( "Experimental role terms can be found in the PSI-MI ontology under term MI:0495" );
    }

    /**
     * Checks that participants have a single experimental role.
     *
     * @param interaction an interaction to check on.
     * @return a collection of validator messages.
     * @throws psidev.psi.tools.validator.ValidatorException
     *          if we fail to retrieve the MI Ontology.
     */
    public Collection<ValidatorMessage> check( Interaction interaction ) throws ValidatorException {

        int interactionId = interaction.getId();

        // list of messages to return
        List<ValidatorMessage> messages = new ArrayList<ValidatorMessage>();

        // write the rule here ...
        for ( Participant participant : interaction.getParticipants() ) {

            int participantId = participant.getId();
            if ( participant.hasExperimentalRoles() ) {
                final int count = participant.getExperimentalRoles().size();
                if ( count > 1 ) {
                    final Mi25Context context = buildContext( interactionId, participantId );
                    messages.add( new ValidatorMessage( "Interaction's participants should have a single " +
                                                        "experimental role; found " + count + ".",
                                                        MessageLevel.WARN,
                                                        context,
                                                        this ) );
                }
            } else {
                final Mi25Context context = buildContext( interactionId, participantId );
                messages.add( new ValidatorMessage( "Interaction's participants should have a single " +
                                                    "experimental role; found 0.",
                                                    MessageLevel.WARN,
                                                    context,
                                                    this ) );
            }
        } // for participants

        return messages;
    }

    private Mi25Context buildContext( int interactionId, int participantId ) {
        Mi25Context context;
        context = new Mi25Context();
        context.setInteractionId( interactionId );
        context.setParticipantId( participantId );
        return context;
    }
}