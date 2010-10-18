package psidev.psi.mi.validator.extension.rules.imex;

import psidev.psi.mi.validator.extension.Mi25Context;
import psidev.psi.mi.validator.extension.Mi25InteractionRule;
import psidev.psi.mi.validator.extension.rules.RuleUtils;
import psidev.psi.mi.xml.model.DbReference;
import psidev.psi.mi.xml.model.Interaction;
import psidev.psi.mi.xml.model.Interactor;
import psidev.psi.mi.xml.model.Participant;
import psidev.psi.tools.ontology_manager.OntologyManager;
import psidev.psi.tools.ontology_manager.interfaces.OntologyAccess;
import psidev.psi.tools.validator.MessageLevel;
import psidev.psi.tools.validator.ValidatorException;
import psidev.psi.tools.validator.ValidatorMessage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static psidev.psi.mi.validator.extension.rules.RuleUtils.*;

/**
 * <b> Checks that proteins have UNIPROT and/or REFSEQ identity.</b>
 * <p/>
 *
 * @author Samuel Kerrien
 * @version $Id$
 * @since 2.0
 */
public class ProteinIdentityRule extends Mi25InteractionRule {

    public ProteinIdentityRule( OntologyManager ontologyMaganer ) {
        super( ontologyMaganer );

        // describe the rule.
        setName( "Protein identity check" );

        setDescription( "Check that each protein has an identity cross reference to the sequence database: UniProtKB and/or RefSeq" );

        addTip( "UniProtKb accession in the PSI-MI ontology is " + UNIPROTKB_MI_REF );
        addTip( "RefSeq accession in the PSI-MI ontology is " + REFSEQ_MI_REF );
        addTip( "Identity accession in the PSI-MI ontology is " + IDENTITY_MI_REF );
    }

    /**
     * check that each interactor has at least name or a short label.
     *
     * @param interaction an interaction to check on.
     * @return a collection of validator messages.
     * @exception psidev.psi.tools.validator.ValidatorException if we fail to retrieve the MI Ontology.
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

                if( RuleUtils.isProtein( ontologyManager, interactor )) {

                    final Collection<DbReference> identities =
                            RuleUtils.searchReferences( interactor.getXref().getAllDbReferences(),
                                    Arrays.asList( IDENTITY_MI_REF ),
                                    Arrays.asList( UNIPROTKB_MI_REF, REFSEQ_MI_REF ),
                                    null);

                    if( identities.isEmpty() ) {
                        Mi25Context context = buildContext( interactionId, participantId, interactorId );
                        messages.add( new ValidatorMessage( "Proteins should have an Xref to UniProtKB and/or RefSeq with a ref type 'identity' ",
                                MessageLevel.WARN,
                                context,
                                this ) );
                    }
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