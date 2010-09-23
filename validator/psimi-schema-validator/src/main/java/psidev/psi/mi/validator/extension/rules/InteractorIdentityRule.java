package psidev.psi.mi.validator.extension.rules;

import psidev.psi.mi.validator.extension.Mi25Context;
import psidev.psi.mi.validator.extension.Mi25InteractionRule;
import psidev.psi.mi.xml.model.DbReference;
import psidev.psi.mi.xml.model.Interaction;
import psidev.psi.mi.xml.model.Interactor;
import psidev.psi.mi.xml.model.Participant;
import psidev.psi.tools.ontology_manager.OntologyManager;
import psidev.psi.tools.ontology_manager.interfaces.OntologyAccess;
import psidev.psi.tools.ontology_manager.interfaces.OntologyTermI;
import psidev.psi.tools.validator.MessageLevel;
import psidev.psi.tools.validator.ValidatorException;
import psidev.psi.tools.validator.ValidatorMessage;

import java.util.*;

import static psidev.psi.mi.validator.extension.rules.RuleUtils.*;

/**
 * <b> Check that each interactor has a cross reference to an appropriate reference database.</b>
 * <p/>
 *
 * @author Samuel Kerrien, Luisa Montecchi
 * @version $Id$
 * @since 1.0
 */
public class InteractorIdentityRule extends Mi25InteractionRule {

    public InteractorIdentityRule( OntologyManager ontologyMaganer ) {
        super( ontologyMaganer );

        // describe the rule.
        setName( "Interactor database reference check" );
        setDescription( "Check that each interactor has a cross reference to an appropriate reference database" );
        addTip( "Sequence databases can be found in the PSI-MI ontology under term MI:0683" );
        addTip( "Bioactive entity databases can be found in the PSI-MI ontology under term MI:2054" );
        addTip( "The term " );
    }

    /**
     * check that each interactor has at least name or a short label.
     *
     * @param interaction an interaction to check on.
     * @return a collection of validator messages.
     * @exception psidev.psi.tools.validator.ValidatorException if we fail to retreive the MI Ontology.
     */
    public Collection<ValidatorMessage> check( Interaction interaction ) throws ValidatorException {

        int interactionId = interaction.getId();

        // list of messages to return
        List<ValidatorMessage> messages = new ArrayList<ValidatorMessage>();

        final OntologyAccess mi = getMiOntology();

        // write the rule here ...
        for ( Participant participant : interaction.getParticipants() ) {

            int participantId = participant.getId();
            final Interactor interactor = participant.getInteractor();

            if (interactor != null){
                int interactorId = interactor.getId();

                if( RuleUtils.isBiopolymer( ontologyManager, interactor )) {

                    // TODO cache these MI refs
                    final Set<OntologyTermI> dbs = mi.getValidTerms( SEQUENCE_DATABASE_MI_REF, true, false );

                    final Set<String> dbMiRefs = RuleUtils.collectAccessions( dbs );

                    final Collection<DbReference> identities;

                    if( interactor.hasXref() ) {
                        identities = RuleUtils.searchReferences( interactor.getXref().getAllDbReferences(),
                                Arrays.asList( IDENTITY_MI_REF ),
                                dbMiRefs,
                                null );
                    } else {
                        identities = Collections.EMPTY_LIST;
                    }

                    if( identities.isEmpty() ) {
                        Mi25Context context = buildContext( interactionId, participantId, interactorId );
                        messages.add( new ValidatorMessage( "Interactor should have an Xref to a sequence database with a ref type 'identity' ",
                                MessageLevel.WARN,
                                context,
                                this ) );
                    }

                } else if( RuleUtils.isSmallMolecule( ontologyManager, interactor )) {

                    // TODO cache these MI refs
                    final Set<OntologyTermI> dbs = mi.getValidTerms( BIOACTIVE_ENTITY_DATABASE_MI_REF, true, false );

                    final Set<String> dbMiRefs = RuleUtils.collectAccessions( dbs );

                    final Collection<DbReference> identities;
                    if( interactor.hasXref() ) {
                        identities = RuleUtils.searchReferences( interactor.getXref().getAllDbReferences(),
                                Arrays.asList( IDENTITY_MI_REF ), dbMiRefs,
                                null );
                    } else {
                        identities = Collections.EMPTY_LIST;
                    }

                    if( identities.isEmpty() ) {
                        Mi25Context context = buildContext( interactionId, participantId, interactorId );
                        messages.add( new ValidatorMessage( "Interactor should have an Xref to a bioactive entity database with a ref type 'identity' ",
                                MessageLevel.WARN,
                                context,
                                this ) );
                    }

                } else {
                    // until now (2009-01), that's all we can check on.
                }
            }

        } // for participants

        return messages;
    }

    private Mi25Context buildContext( int interactionId, int participantId, int interactorId ) {
        Mi25Context context;
        context = new Mi25Context();
        context.setInteractionId( interactionId );
        context.setParticipantId( participantId );
        context.setInteractorId( interactorId );
        return context;
    }
}