package psidev.psi.mi.validator.extension.rules;

import psidev.psi.mi.validator.extension.Mi25Context;
import psidev.psi.mi.validator.extension.Mi25InteractionRule;
import psidev.psi.mi.xml.model.*;
import psidev.psi.tools.ontology_manager.OntologyManager;
import psidev.psi.tools.validator.ValidatorException;
import psidev.psi.tools.validator.ValidatorMessage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * This rule is checking that each feature range is valid
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>25-Aug-2010</pre>
 */

public class FeatureRangeRule extends Mi25InteractionRule {

    public FeatureRangeRule( OntologyManager ontologyMaganer ) {
        super( ontologyMaganer );

        // describe the rule.
        setName( "Feature range Check" );
        setDescription( "Checks that the each interactor's feature range is valid : not out of bound (inferior to 1 or superior to the sequence length), not overlapping and compliant with the feature range status." );
        addTip( "When the feature range start is 'n-terminal', 'c-terminal' or 'undetermined', the feature range end status should be the same as the feature range start status (and vice versa)." );
        addTip( "When the feature range start is 'n-terminal', 'c-terminal' or 'undetermined', the range values should always be 0." );
        addTip( "When the feature range start is 'certain', 'range', 'greater than' or 'less than', the range values should always be superior or equal to 1." );
        addTip( "The ranges should not be out of the total protein sequence." );
        addTip( "The start and end intervals of a range should not overlap." );
    }

    @Override
    public Collection<ValidatorMessage> check(Interaction interaction) throws ValidatorException {
        int interactionId = interaction.getId();

        // list of messages to return
        List<ValidatorMessage> messages = new ArrayList<ValidatorMessage>();

        Collection<Participant> participants = interaction.getParticipants();

        for (Participant participant : participants){
            int participantId = participant.getId();
            Collection<Feature> features = participant.getFeatures();

            Interactor interactor = participant.getInteractor();

            if (interactor != null){
                String sequence = interactor.getSequence();

                for (Feature feature : features){
                    int featureId = feature.getId();
                    Collection<Range> ranges = feature .getRanges();

                    Mi25Context context = new Mi25Context();
                    context.setInteractionId( interactionId );
                    context.setParticipantId( participantId );
                    context.setFeatureId( featureId );

                    for (Range range : ranges){
                        FeatureUtils.checkBadRange(range, sequence, context, messages, this);
                    }
                }
            }
        }

        return messages;
    }
}
