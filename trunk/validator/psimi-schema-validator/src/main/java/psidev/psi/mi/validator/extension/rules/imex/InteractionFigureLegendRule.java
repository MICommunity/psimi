package psidev.psi.mi.validator.extension.rules.imex;

import psidev.psi.mi.jami.model.Annotation;
import psidev.psi.mi.jami.model.InteractionEvidence;
import psidev.psi.mi.jami.utils.AnnotationUtils;
import psidev.psi.mi.validator.extension.Mi25Context;
import psidev.psi.mi.validator.extension.rules.AbstractMIRule;
import psidev.psi.mi.validator.extension.rules.RuleUtils;
import psidev.psi.tools.ontology_manager.OntologyManager;
import psidev.psi.tools.validator.MessageLevel;
import psidev.psi.tools.validator.ValidatorException;
import psidev.psi.tools.validator.ValidatorMessage;

import java.util.Collection;
import java.util.Collections;

/**
 * This rule checks that each interaction has a figure legend attached to it
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>25/01/11</pre>
 */

public class InteractionFigureLegendRule extends AbstractMIRule<InteractionEvidence> {
    public InteractionFigureLegendRule(OntologyManager ontologyManager) {
        super(ontologyManager, InteractionEvidence.class);

        // describe the rule.
        setName("Interaction Figure Legend Check");
        setDescription("Checks that each interaction has at least one figure-legend attached to it.");
        addTip( "figure-legend accession in the PSI-MI ontology is " + RuleUtils.FIGURE_LEGEND );    }

    @Override
    public Collection<ValidatorMessage> check(InteractionEvidence interaction) throws ValidatorException {
        Collection<ValidatorMessage> messages = Collections.EMPTY_LIST;

        if (!interaction.getAnnotations().isEmpty()){
            Collection<Annotation> attributes = interaction.getAnnotations();

            if (AnnotationUtils.collectAllAnnotationsHavingTopic(attributes, Annotation.FIGURE_LEGEND_MI, Annotation.FIGURE_LEGEND).isEmpty()){
                Mi25Context context = RuleUtils.buildContext(interaction, "interaction");

                messages=Collections.singleton( new ValidatorMessage( "Interaction with "+attributes.size()+" annotations but without a figure legend. A figure legend is recommended by IMEx.'",
                        MessageLevel.WARN,
                        context,
                        this ) );
            }
        }
        else {
            Mi25Context context = RuleUtils.buildContext(interaction, "interaction");

            messages=Collections.singleton(new ValidatorMessage("Interaction without a figure legend. A figure legend is recommended by IMEx.'",
                    MessageLevel.WARN,
                    context,
                    this));
        }

        return messages;
    }

    public String getId() {
        return "R38";
    }
}
