package psidev.psi.mi.validator.extension.rules.imex;

import psidev.psi.mi.jami.model.Annotation;
import psidev.psi.mi.jami.model.Experiment;
import psidev.psi.mi.jami.model.InteractionEvidence;
import psidev.psi.mi.jami.model.Publication;
import psidev.psi.mi.jami.utils.AnnotationUtils;
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

/**
 * Checks if an interaction or its experiment have an attribute name set to 'full coverage'.
 *
 * Rule id = 4. See http://docs.google.com/Doc?docid=0AXS9Q1JQ2DygZGdzbnZ0Ym5fMHAyNnM3NnRj&hl=en_GB&pli=1
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id: FullCoverageRule.java 52 2010-01-22 11:24:24Z marine.dumousseau@wanadoo.fr $
 * @since 2.0
 */
public class FullCoverageRule extends Mi25InteractionRule {

    public FullCoverageRule(OntologyManager ontologyMaganer) {
        super( ontologyMaganer );

        // describe the rule.
        setName( "Full coverage attribute check" );
        setDescription( "Checks that each interaction (or the experiment or the publication) has at least one attribute 'full coverage' (MI:0957)." );
        addTip( "Your interaction, or experiment or publication must have an attribute with a name 'full coverage'" );
        addTip( "The PSI-MI identifier for full coverage is: MI:0957" );
    }

    /**
     * Make sure that an experiment has at least one attribute name set to 'full coverage'.
     *
     * @param interaction an interaction to check on.
     * @return a collection of validator messages.
     */
    public Collection<ValidatorMessage> check( InteractionEvidence interaction ) throws ValidatorException {

        // list of messages to return
        List<ValidatorMessage> messages = new ArrayList<ValidatorMessage>();

        boolean hasAnnotation = false;
        // An experiment must have at least one attribute 'imex-curation' and one attribute 'full coverage'
        if (!interaction.getAnnotations().isEmpty()){
            // The attributes with a name/MI attName/attMI
            Collection<Annotation> attributeName = AnnotationUtils.collectAllAnnotationsHavingTopic(interaction.getAnnotations(), "MI:0957", "full coverage");

            if (!attributeName.isEmpty()){
                hasAnnotation = true;
            }
        }

        if (!hasAnnotation && interaction.getExperiment() != null){
            Experiment exp = interaction.getExperiment();

            if (!exp.getAnnotations().isEmpty()){
                // The attributes with a name/MI attName/attMI
                Collection<Annotation> attributeName = AnnotationUtils.collectAllAnnotationsHavingTopic(exp.getAnnotations(), "MI:0957", "full coverage");

                if (!attributeName.isEmpty()){
                    hasAnnotation = true;
                }
            }

            if (!hasAnnotation && exp.getPublication() != null){
                Publication pub = exp.getPublication();

                if (!pub.getAnnotations().isEmpty()){
                    // The attributes with a name/MI attName/attMI
                    Collection<Annotation> attributeName = AnnotationUtils.collectAllAnnotationsHavingTopic(pub.getAnnotations(), "MI:0957", "full coverage");

                    if (!attributeName.isEmpty()){
                        hasAnnotation = true;
                    }
                }
            }
        }

        if (!hasAnnotation){
            // The attributes with a name/MI attName/attMI
            Collection<Annotation> attributeName = AnnotationUtils.collectAllAnnotationsHavingTopic(interaction.getAnnotations(), "MI:0957", "full coverage");

            if (attributeName.isEmpty()){
                Mi25Context context = RuleUtils.buildContext(interaction, "interaction");
                messages.add( new ValidatorMessage( "The annotation 'full coverage' (MI:0957) is missing (can be at the interaction, experiment or publication level) and it is required for IMEx. ",
                        MessageLevel.ERROR,
                        context,
                        this ) );
            }
        }

        return messages;
    }

    public String getId() {
        return "R64";
    }
}
