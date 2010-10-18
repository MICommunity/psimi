package psidev.psi.mi.validator.extension.rules.mimix;

import psidev.psi.mi.validator.extension.Mi25Context;
import psidev.psi.mi.validator.extension.Mi25InteractionRule;
import psidev.psi.mi.validator.extension.Mi25Ontology;
import psidev.psi.mi.validator.extension.rules.RuleUtils;
import psidev.psi.mi.xml.model.ExperimentDescription;
import psidev.psi.mi.xml.model.Interaction;
import psidev.psi.mi.xml.model.Participant;
import psidev.psi.mi.xml.model.ParticipantIdentificationMethod;
import psidev.psi.tools.ontology_manager.OntologyManager;
import psidev.psi.tools.validator.MessageLevel;
import psidev.psi.tools.validator.ValidatorException;
import psidev.psi.tools.validator.ValidatorMessage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Checks if an experiment have a Participant identification Method and that all the participant identification methods are valid.
 *
 * Rule Id = 6. See http://docs.google.com/Doc?docid=0AXS9Q1JQ2DygZGdzbnZ0Ym5fMHAyNnM3NnRj&hl=en_GB&pli=1
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id: ExperimentParticipantIdentificationMethodRule.java 56 2010-01-22 15:37:09Z marine.dumousseau@wanadoo.fr $
 * @since 2.0
 */
public class ExperimentParticipantIdentificationMethodRule extends Mi25InteractionRule {

    public ExperimentParticipantIdentificationMethodRule( OntologyManager ontologyMaganer ) {
        super( ontologyMaganer );

        setName( "Experiment Participant Identification Method check" );
        setDescription( "Checks that each experiment has at least one Participant Identification Method (MI:0002) and that all Participant Identification Methods are valid." );
        addTip( "Your experiment should have a Participant Identification method" );
        addTip( "Each participant identification method should have a PSI MI cross reference with a reference type set to identical object (MI:0356)" );
        addTip( "Any child of MI:0002 is a Participant Identification method. You can look at http://www.ebi.ac.uk/ontology-lookup/browse.do?ontName=MI." );
        addTip( "The PSI-MI identifier for identical object is: MI:0356" );
    }

    /**
     * Make sure that an experiment has a valid Participant Identification Method that all the participant identification methods are valid.
     *
     * @param interaction an interaction to check on.
     * @return a collection of validator messages.
     */
    public Collection<ValidatorMessage> check( Interaction interaction ) throws ValidatorException {

        // list of messages to return
        List<ValidatorMessage> messages = new ArrayList<ValidatorMessage>();

        int interactionId = interaction.getId();

        Collection<ExperimentDescription> experiments = interaction.getExperiments();
        Collection<Participant> participants = interaction.getParticipants();

        for (ExperimentDescription experiment : experiments){
            int experimentId = experiment.getId();

            boolean hasToCheckPartDetMetInExperiment = false;

            for (Participant participant : participants){
                int participantId = participant.getId();

                Mi25Context context = new Mi25Context();
                context.setInteractionId( interactionId );
                context.setExperimentId(experimentId);
                context.setParticipantId(participantId);

                if (experiment.getParticipantIdentificationMethod() == null && !participant.hasParticipantIdentificationMethods()){
                    messages.add( new ValidatorMessage( " In the experiment " + experimentId + ", the participant "+participantId+" of the interaction "+interactionId+" does not have a participant identification method and it is required for MIMIx",
                            MessageLevel.ERROR,
                            context,
                            this ) );
                }
                else if (participant.hasParticipantIdentificationMethods()){
                    Collection<ParticipantIdentificationMethod> participantIdentifications = participant.getParticipantIdentificationMethods();

                    for (ParticipantIdentificationMethod method : participantIdentifications){

                        final Mi25Ontology ontology = getMi25Ontology();
                        RuleUtils.checkPsiMIXRef(method, messages, context, this, ontology, "MI:0002");
                    }
                }
                else {
                    hasToCheckPartDetMetInExperiment = true;
                }
            }

            if (hasToCheckPartDetMetInExperiment){
                Mi25Context context = new Mi25Context();
                context.setInteractionId( interactionId );
                context.setExperimentId(experimentId);

                final Mi25Ontology ontology = getMi25Ontology();
                RuleUtils.checkPsiMIXRef(experiment.getParticipantIdentificationMethod(), messages, context, this, ontology, "MI:0002");
            }
        }

        return messages;
    }
}
