package psidev.psi.mi.validator.extension.rules;

import psidev.psi.mi.validator.extension.Mi25InteractionRule;
import psidev.psi.mi.xml.model.Interaction;
import psidev.psi.tools.ontology_manager.OntologyManager;
import psidev.psi.tools.validator.ValidatorException;
import psidev.psi.tools.validator.ValidatorMessage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * <b>When a raw sequence is not specified in the participant/sequence element this rule checks there is a valid
 * sequence xref.</b>
 * <p/>
 *
 * @author Samuel Kerrien, Luisa Montecchi
 * @version $Id$
 * @since 10-Apr-2006
 */
public class ParticipantSeqXrefRule extends Mi25InteractionRule {

    public ParticipantSeqXrefRule( OntologyManager ontologyMaganer ) {
        super( ontologyMaganer );

        // describe the rule.
        setName( "Participant sequence cross reference rule" );
        setDescription( "When a raw sequence is not specified in the participant/sequence element this rule checks there is a valid sequence XREF" );
        addTip( "Enter a raw sequence in the element interactorElementType/sequence or a cross reference to a sequence database in interactorElementType/xref" );
    }

    /**
     * TODO comment.
     *
     * @param interaction an interaction to check on.
     * @return a collection of validator messages.
     *          if we fail to retreive the MI Ontology.
     */
    public Collection<ValidatorMessage> check( Interaction interaction ) throws ValidatorException {

        int interactionId = interaction.getId();

        // list of messages to return
        List<ValidatorMessage> messages = new ArrayList<ValidatorMessage>();

//        // write the rule here ...
//        for ( Participant participant : interaction.getParticipants() ) {
//
//            int participantId = participant.getId();
//            int interactorId = participant.getInteractor().getId();
//
//            // TODO not very good as for instance small molecule would not have a sequence !!
//
//            // NOTE both xref and seq are optional in the schema
//            if ( participant.getInteractor().getSequence() == null &&
//                 participant.getInteractor().getXref() == null ) {
//
//                Mi25Context context = new Mi25Context();
//                context.setInteractionId( interactionId );
//                context.setParticipantId( participantId );
//                context.setInteractorId( interactorId );
//
//                messages.add( new ValidatorMessage( "Interactor does not have a sequence or a sequence primary ref ",
//                                                    MessageLevel.ERROR,
//                                                    context,
//                                                    this ) );
//            } else if ( participant.getInteractor().getSequence() != null &&
//                        participant.getInteractor().getXref() == null ) {
//
//                Mi25Context context = new Mi25Context();
//                context.setInteractionId( interactionId );
//                context.setParticipantId( participantId );
//                context.setInteractorId( interactorId );
//
//                messages.add( new ValidatorMessage( "Interactor has a raw sequence an no sequence primary ref ",
//                                                    MessageLevel.INFO,
//                                                    context,
//                                                    this ) );
//
//            } else if ( participant.getInteractor().getXref() != null ) {
//
//                DbReference xref = participant.getInteractor().getXref().getPrimaryRef();
//                String db = xref.getDb();
//                String id = xref.getId();
//                if ( db.equals( "" ) ) {
//                    Mi25Context context = new Mi25Context();
//                    context.setInteractionId( interactionId );
//                    context.setParticipantId( participantId );
//                    context.setInteractorId( interactorId );
//
//                    messages.add( new ValidatorMessage( "The attribute db in primary ref of the  participant is an empty string",
//                                                        MessageLevel.WARN,
//                                                        context,
//                                                        this ) );
//
//                } else if ( id.equals( "" ) ) {
//                    Mi25Context context = new Mi25Context();
//                    context.setInteractionId( interactionId );
//                    context.setParticipantId( participantId );
//                    context.setInteractorId( interactorId );
//
//                    messages.add( new ValidatorMessage( "The attribute id in primary ref of the participant is an empty string",
//                                                        MessageLevel.WARN,
//                                                        context,
//                                                        this ) );
//
//                } else if ( !id.equals( "" ) && !db.equals( "" ) ) {
//
//                    Mi25Ontology ont = super.getMiOntology();     //  db is name not MI and dcAc MI of db not mandatory
//
//                    String mi_db = ont.shortlabel2mi.get( db );
//                    if( mi_db == null ) {
//                        throw new IllegalArgumentException( "Failed to find database MI identifier by shortlabel: " + db );
//                    }
//                    OntologyTermI t = ont.search( mi_db );
//
//                    if ( t == null ) {
//
//                        Mi25Context context = new Mi25Context();
//                        context.setInteractionId( interactionId );
//                        context.setParticipantId( participantId );
//                        context.setInteractorId( interactorId );
//
//                        messages.add( new ValidatorMessage( "Not appropriate db name in participant primary xref (" + db + ")",
//                                                            MessageLevel.WARN,
//                                                            context,
//                                                            this ) );
//
//                    } else if ( !mi_db.equals( Mi25Ontology.UNIPROT_MI ) ) {
//                        Mi25Context context = new Mi25Context();
//                        context.setInteractionId( interactionId );
//                        context.setParticipantId( participantId );
//                        context.setInteractorId( interactorId );
//
//                        messages.add( new ValidatorMessage( "Participant primary xref different from UniProtKB (" + mi_db + ")",
//                                                            MessageLevel.INFO,
//                                                            context,
//                                                            this ) );
//                    } else {
//
//                        OntologyTermI t2 = ont.getDatabaseCitationRoot(); // add extra condition going down to sequence database once term created
//                        if ( !ont.isChildOf( t2, t ) ) {
//
//                            Mi25Context context = new Mi25Context();
//                            context.setInteractionId( interactionId );
//                            context.setParticipantId( participantId );
//                            context.setInteractorId( interactorId );
//
//                            messages.add( new ValidatorMessage( "Term does not belong to CV database citation (" + t.getTermAccession() + ")",
//                                                                MessageLevel.ERROR,
//                                                                context,
//                                                                this ) );
//                        }
//                    }
//                } // if db and id are not empty
//            }   //if  xref not null
//        }// for participant
        return messages;
    }

    public String getId() {
        return null;
    }
}