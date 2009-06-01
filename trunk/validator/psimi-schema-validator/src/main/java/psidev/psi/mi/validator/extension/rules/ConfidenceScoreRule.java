package psidev.psi.mi.validator.extension.rules;

import psidev.psi.mi.validator.extension.Mi25Context;
import psidev.psi.mi.validator.extension.Mi25InteractionRule;
import static psidev.psi.mi.validator.extension.rules.RuleUtils.*;
import psidev.psi.mi.xml.model.Interaction;
import psidev.psi.mi.xml.model.Attribute;
import psidev.psi.mi.xml.model.ExperimentDescription;
import psidev.psi.tools.ontology_manager.OntologyManager;
import psidev.psi.tools.validator.MessageLevel;
import psidev.psi.tools.validator.ValidatorException;
import psidev.psi.tools.validator.ValidatorMessage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * <b>Checks that the interaction defines its confidence score (if any) correctly.</b>
 * <p/>
 *
 * @author Samuel Kerrien
 * @version $Id$
 * @since 2.0
 */
public class ConfidenceScoreRule extends Mi25InteractionRule {

    public ConfidenceScoreRule( OntologyManager ontologyMaganer ) {
        super( ontologyMaganer );

        // describe the rule.
        setName( "Confidence Score Definition Check" );
        setDescription( "Checks that the interaction defines its confidence score (if any) correctly." );
        addTip( "The score(s) should be defined at the interaction level under attribute(s) having for name " +
                "'author-confidence' and nameAc 'MI:0621'." );
        addTip( "The scoring scheme should be defined at the experiment level in an attribute having for name " +
                "'confidence-mapping' and nameAc 'MI:0622'." );
    }

    /**
     * Checks that the interaction defines its confidence score (if any) correctly.
     *
     * @param interaction an interaction to check on.
     * @return a collection of validator messages.
     * @exception ValidatorException if we fail to retreive the MI Ontology.
     */
    public Collection<ValidatorMessage> check( Interaction interaction ) throws ValidatorException {

        int interactionId = interaction.getId();

        // list of messages to return
        List<ValidatorMessage> messages = new ArrayList<ValidatorMessage>();

        // write the rule here ...

        final Collection<Attribute> atts = searchAttributes( interaction.getAttributes(), 
                                                             AUTHOR_CONFIDENCE,
                                                             AUTHOR_CONFIDENCE_MI_REF );
        if ( ! atts.isEmpty() ) {

            // check that in the list of experiment attached to an interaction there should be
            // at least one with a confidence mapping.

            if( ! interaction.hasExperiments() ) {
                // error, we should have at least one exp !!

                Mi25Context context = new Mi25Context();
                context.setInteractionId( interactionId );
                context.setExperimentId( interactionId );

                messages.add( new ValidatorMessage( "No experiment defined for this interaction, furthermore, given " +
                                                    "that the interaction defines an author confidence, the experiment " +
                                                    "should have had a confidence mapping." ,
                                                    MessageLevel.ERROR,
                                                    context,
                                                    this ) );
            } else {

                boolean foundOne = false;
                for ( ExperimentDescription experiment : interaction.getExperiments() ) {
                    final Collection<Attribute> expAtts = searchAttributes( experiment.getAttributes(),
                                                                            CONFIDENCE_MAPPING,
                                                                            CONFIDENCE_MAPPING_MI_REF );
                    if( ! expAtts.isEmpty() ) {
                        foundOne = true;
                    }
                }

                if( ! foundOne ) {
                    Mi25Context context = new Mi25Context();
                    context.setInteractionId( interactionId );

                    final int expCount = interaction.getExperiments().size();

                    String msg = null;
                    if( expCount <= 1 ) {
                        msg = "Could not find a confidence mapping on the experiment attached to this interaction.";
                        context.setExperimentId( interaction.getExperiments().iterator().next().getId() );
                    } else {
                        msg = "Could not find a confidence mapping on any of the "+ expCount +
                              " experiments attached to this interaction.";
                    }

                    messages.add( new ValidatorMessage( msg ,
                                                        MessageLevel.ERROR,
                                                        context,
                                                        this ) );
                }
            }
        }

        return messages;
    }
}