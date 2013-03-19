package psidev.psi.mi.validator.extension.rules;

import psidev.psi.mi.validator.extension.Mi25Context;
import psidev.psi.mi.validator.extension.FeatureUtils;
import psidev.psi.mi.xml.model.*;
import psidev.psi.tools.ontology_manager.OntologyManager;
import psidev.psi.tools.validator.MessageLevel;
import psidev.psi.tools.validator.ValidatorException;
import psidev.psi.tools.validator.ValidatorMessage;
import psidev.psi.tools.validator.rules.codedrule.ObjectRule;

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

public class FeatureRangeRule extends ObjectRule<Participant> {

    public FeatureRangeRule( OntologyManager ontologyMaganer ) {
        super( ontologyMaganer );

        // describe the rule.
        setName( "Feature range Check" );
        setDescription( "Checks that the each participant's feature range is valid : not out of bound (inferior to 1 or superior to the sequence length), not overlapping and compliant with the feature range status. " +
                "WARNING : the status 'c-terminal' and 'n-terminal' cannot be used anymore for n-terminal/c-terminal features where the exact positions are not known. It is recommended to use " +
                "the new terms 'c-terminal range' (MI:1039) and 'n-terminal range' (MI:1040) for such features" );
        addTip( "When the feature range status is 'undetermined', 'n-terminal range' or 'c-terminal range', the range position values should always be 0 or null." );
        addTip( "When the feature range status is 'n-terminal', the range position values should always be 1 because 'n-terminal' means the first amino acid of the participant." );
        addTip( "When the feature range status is 'c-terminal', the range position values should always be the length of the participant sequence because 'c-terminal' means the last amino " +
                "acid of the participant. A 'c-terminal' position equal to null or 0 can be accepted if the sequence of the interactor is not known." );
        addTip( "When the feature range status is 'greater-than', the range position values must be strictly inferior to the sequence length as 'greater-than' is exclusive." );
        addTip( "When the feature range status is 'less-than', the range position values must be strictly superior to 1 as 'less-than' is exclusive." );
        addTip( "When the feature range status is 'certain', 'range', 'greater than' or 'less than', the range values should never be inferior or equal to 0." );
        addTip( "The ranges should not be out of the total protein sequence." );
        addTip( "If the start position is not a range, we should not have a start interval and vice versa for the end position." );
        addTip( "The start and end intervals of a range should not overlap." );
    }

    @Override
    public boolean canCheck(Object t) {

        if (t instanceof Participant){
            return true;
        }

        return false;
    }

    @Override
    public Collection<ValidatorMessage> check(Participant participant) throws ValidatorException {

        // list of messages to return
        List<ValidatorMessage> messages = new ArrayList<ValidatorMessage>();

        int participantId = participant.getId();
        Collection<Feature> features = participant.getFeatures();

        Interactor interactor = participant.getInteractor();

        if (interactor != null){
            String sequence = interactor.getSequence();

            for (Feature feature : features){
                int featureId = feature.getId();
                Collection<Range> ranges = feature .getFeatureRanges();

                Mi25Context context = new Mi25Context();
                context.setParticipantId( participantId );
                context.setFeatureId( featureId );

                if (ranges.isEmpty()){
                    messages.add( new ValidatorMessage( "Feature must have at least one range.'",
                            MessageLevel.ERROR,
                            context,
                            this ) );
                }

                for (Range range : ranges){
                    FeatureUtils.checkBadRange(range, sequence, context, messages, this);
                }
            }
        }

        return messages;
    }

    public String getId() {
        return "R14";
    }
}
