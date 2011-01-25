package psidev.psi.mi.validator.extension.rules.imex;

import psidev.psi.mi.validator.extension.Mi25Context;
import psidev.psi.mi.validator.extension.Mi25InteractionRule;
import psidev.psi.mi.validator.extension.rules.RuleUtils;
import psidev.psi.mi.xml.model.Attribute;
import psidev.psi.mi.xml.model.Interaction;
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
    public Collection<ValidatorMessage> check(Interaction interaction) throws ValidatorException {
        List<ValidatorMessage> messages = new ArrayList<ValidatorMessage>();

        Mi25Context context = new Mi25Context();
        context.setInteractionId(interaction.getId());

        if (interaction.hasAttributes()){
            Collection<Attribute> attributes = interaction.getAttributes();

            boolean hasFigureLegend = false;

            for (Attribute attribute : attributes){
                if (attribute.getNameAc() != null){
                    if (RuleUtils.FIGURE_LEGEND_MI_REF.equals(attribute.getNameAc())){
                        hasFigureLegend = true;
                    }
                }
                else if (attribute.getName() != null){
                    if (RuleUtils.FIGURE_LEGEND.equalsIgnoreCase(attribute.getName())){
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
}
