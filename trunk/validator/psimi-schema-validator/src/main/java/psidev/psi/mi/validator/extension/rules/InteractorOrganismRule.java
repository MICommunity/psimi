package psidev.psi.mi.validator.extension.rules;

import psidev.psi.mi.validator.extension.Mi25Context;
import psidev.psi.mi.validator.extension.Mi25InteractionRule;
import psidev.psi.mi.xml.model.Interaction;
import psidev.psi.mi.xml.model.Interactor;
import psidev.psi.mi.xml.model.Organism;
import psidev.psi.mi.xml.model.Participant;
import psidev.psi.tools.ontology_manager.OntologyManager;
import psidev.psi.tools.validator.MessageLevel;
import psidev.psi.tools.validator.ValidatorException;
import psidev.psi.tools.validator.ValidatorMessage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * <b> check every experiment host organism has an attribute taxid and that is is defined in NEWT. </b>.
 * <p/>
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version $Id$
 * @since 1.0
 */
public class InteractorOrganismRule extends Mi25InteractionRule {

    public InteractorOrganismRule( OntologyManager ontologyMaganer ) {
        super( ontologyMaganer );

        // describe the rule.
        setName( "Interactor Host Organism Check" );
        setDescription( "Checks that each Interactor has an host organisms element with a valid tax id as attribute" );
        addTip( "Search http://www.ebi.ac.uk/newt/display with an organism name to retrieve its taxid." );
        addTip( "By convention, the taxid for 'in vitro' is -1" );
        addTip( "By convention, the taxid for 'chemical synthesis' is -2" );
        addTip( "By convention, the taxid for 'unknown' is -3" );
        addTip( "By convention, the taxid for 'in vivo' is -4" );
        addTip( "By convention, the taxid for 'in silico' is -5" );
    }

    /**
     * Checks that each experiment has an host organisms element with a valid tax id as attribute.
     * Tax id must be a positive integer or -1
     *
     * @param interaction an interaction to check on.
     * @return a collection of validator messages.
     */
    public Collection<ValidatorMessage> check( Interaction interaction ) throws ValidatorException {

        List<ValidatorMessage> messages = new ArrayList<ValidatorMessage>();

        int interactionId = interaction.getId();

        for ( Participant participant : interaction.getParticipants() ) {

            final Interactor interactor = participant.getInteractor();
            final Organism organism = interactor.getOrganism();

            Mi25Context context = new Mi25Context();
            context.setInteractionId( interactionId );
            context.setParticipantId( participant.getId() );
            context.setInteractorId( interactor.getId() );

            if( organism == null ) {
                messages.add( new ValidatorMessage( "Interactor without an organism defined.",
                                                    MessageLevel.WARN,
                                                    context,
                                                    this ) );
            } else {
                RuleUtils.checkOrganism( ontologyManager, organism, context, messages, this,
                                         "Interactor", "organism");
            }
        }

        return messages;
    }
}