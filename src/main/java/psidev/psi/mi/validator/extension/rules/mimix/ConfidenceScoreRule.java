package psidev.psi.mi.validator.extension.rules.mimix;

import psidev.psi.mi.jami.model.Annotation;
import psidev.psi.mi.jami.model.Confidence;
import psidev.psi.mi.jami.model.InteractionEvidence;
import psidev.psi.mi.jami.utils.AnnotationUtils;
import psidev.psi.mi.jami.utils.ConfidenceUtils;
import psidev.psi.mi.validator.extension.Mi25Context;
import psidev.psi.mi.validator.extension.Mi25InteractionRule;
import psidev.psi.mi.validator.extension.rules.RuleUtils;
import psidev.psi.tools.ontology_manager.OntologyManager;
import psidev.psi.tools.validator.MessageLevel;
import psidev.psi.tools.validator.ValidatorException;
import psidev.psi.tools.validator.ValidatorMessage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static psidev.psi.mi.validator.extension.rules.RuleUtils.*;

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
                "'author-confidence' and nameAc 'MI:0621' or under confidence(s) having for type 'author-based confidence' and typeAc = 'MI:1221'." );
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
    public Collection<ValidatorMessage> check( InteractionEvidence interaction ) throws ValidatorException {

        // list of messages to return
        List<ValidatorMessage> messages = new ArrayList<ValidatorMessage>();

        Mi25Context context = RuleUtils.buildContext(interaction, "interaction");

        // write the rule here ...

        final Collection<Annotation> atts = AnnotationUtils.collectAllAnnotationsHavingTopic( interaction.getAnnotations(),
                AUTHOR_CONFIDENCE_MI_REF,
                AUTHOR_CONFIDENCE);
        boolean hasAddedExperimentContext = false;

        if ( ! atts.isEmpty() ) {

            // check that in the list of experiment attached to an interaction there should be
            // at least one with a confidence mapping.

            if( interaction.getExperiment() == null ) {
                // error, we should have at least one exp !!
                messages.add( new ValidatorMessage( "No experiment defined for this interaction, furthermore, given " +
                        "that the interaction defines an author confidence, the experiment " +
                        "should have had a confidence mapping." ,
                        MessageLevel.ERROR,
                        context,
                        this ) );
            } else {
                context.addAssociatedContext(RuleUtils.buildContext(interaction.getExperiment(), "experiment"));
                hasAddedExperimentContext = true;
                final Collection<Annotation> expAtts = AnnotationUtils.collectAllAnnotationsHavingTopic( interaction.getExperiment().getAnnotations(),
                        CONFIDENCE_MAPPING_MI_REF,
                        CONFIDENCE_MAPPING);

                if( expAtts.isEmpty() ) {

                    context.addAssociatedContext(RuleUtils.buildContext(interaction.getExperiment()));
                    messages.add( new ValidatorMessage( "Could not find a confidence mapping on the experiment attached to this interaction." ,
                            MessageLevel.ERROR,
                            context,
                            this ) );
                }
            }
        }

        final Collection<Confidence> confs = ConfidenceUtils.collectAllConfidencesHavingType( interaction.getExperimentalConfidences(),
                AUTHOR_SCORE_MI_REF,
                AUTHOR_SCORE);

        if ( ! confs.isEmpty() ) {

            // check that in the list of experiment attached to an interaction there should be
            // at least one with a confidence mapping.

            if( interaction.getExperiment() == null ) {
                // error, we should have at least one exp !!
                messages.add( new ValidatorMessage( "No experiment defined for this interaction, furthermore, given " +
                        "that the interaction defines an author score, the experiment " +
                        "should have had a confidence mapping." ,
                        MessageLevel.ERROR,
                        context,
                        this ) );
            } else {
                if (!hasAddedExperimentContext){
                    context.addAssociatedContext(RuleUtils.buildContext(interaction.getExperiment(), "experiment"));
                }
                final Collection<Annotation> expAtts = AnnotationUtils.collectAllAnnotationsHavingTopic( interaction.getExperiment().getAnnotations(),
                        CONFIDENCE_MAPPING_MI_REF,
                        CONFIDENCE_MAPPING);

                if( expAtts.isEmpty() ) {

                    context.addAssociatedContext(RuleUtils.buildContext(interaction.getExperiment()));
                    messages.add( new ValidatorMessage( "Could not find a confidence mapping on the experiment attached to this interaction." ,
                            MessageLevel.ERROR,
                            context,
                            this ) );
                }
            }
        }

        return messages;
    }

    public String getId() {
        return "R50";
    }
}