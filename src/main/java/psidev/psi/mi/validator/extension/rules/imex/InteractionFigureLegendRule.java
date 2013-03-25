package psidev.psi.mi.validator.extension.rules.imex;

import psidev.psi.mi.jami.model.Annotation;
import psidev.psi.mi.jami.model.InteractionEvidence;
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
 * This rule checks that each interaction has a figure legend attached to it
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>25/01/11</pre>
 */

public class InteractionFigureLegendRule extends Mi25InteractionRule{
    public InteractionFigureLegendRule(OntologyManager ontologyManager) {
        super(ontologyManager);

        // describe the rule.
        setName("Interaction Figure Legend Check");
        setDescription("Checks that each interaction has at least one figure-legend attached to it.");
        addTip( "figure-legend accession in the PSI-MI ontology is " + RuleUtils.FIGURE_LEGEND );    }

    @Override
    public Collection<ValidatorMessage> check(InteractionEvidence interaction) throws ValidatorException {
        List<ValidatorMessage> messages = new ArrayList<ValidatorMessage>();

        Mi25Context context = RuleUtils.buildContext(interaction, "interaction");

        if (!interaction.getAnnotations().isEmpty()){
            Collection<Annotation> attributes = interaction.getAnnotations();

            boolean hasFigureLegend = false;

            for (Annotation attribute : attributes){
                if (attribute.getTopic().getIdentifiers() != null){
                    if (RuleUtils.FIGURE_LEGEND_MI_REF.equals(attribute.getTopic().getMIIdentifier())){
                        hasFigureLegend = true;
                    }
                }
                else if (attribute.getTopic().getShortName() != null){
                    if (RuleUtils.FIGURE_LEGEND.equalsIgnoreCase(attribute.getTopic().getShortName())){
                        hasFigureLegend = true;
                    }
                }
            }

            if (!hasFigureLegend){
                messages.add( new ValidatorMessage( "Interaction with "+attributes.size()+" annotations but without a figure legend. A figure legend is recommended by IMEx.'",
                        MessageLevel.WARN,
                        context,
                        this ) );
            }
        }
        else {
            messages.add( new ValidatorMessage( "Interaction without a figure legend. A figure legend is recommended by IMEx.'",
                    MessageLevel.WARN,
                    context,
                    this ) );
        }

        return messages;
    }

    public String getId() {
        return "R34";
    }
}
