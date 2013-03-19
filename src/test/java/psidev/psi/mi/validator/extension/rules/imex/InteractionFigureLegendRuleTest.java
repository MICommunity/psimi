package psidev.psi.mi.validator.extension.rules.imex;

import junit.framework.Assert;
import org.junit.Test;
import psidev.psi.mi.validator.extension.rules.AbstractRuleTest;
import psidev.psi.mi.validator.extension.rules.RuleUtils;
import psidev.psi.mi.xml.model.*;
import psidev.psi.tools.validator.ValidatorException;
import psidev.psi.tools.validator.ValidatorMessage;

import java.util.Collection;

/**
 * Tester of InteractionFigureLegendRule
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>26/01/11</pre>
 */

public class InteractionFigureLegendRuleTest extends AbstractRuleTest {

    @Test
    public void test_interaction_one_figure_legend() throws ValidatorException {
        Interaction interaction = buildInteractionDeterministic();

        Attribute attribute = new Attribute(RuleUtils.FIGURE_LEGEND_MI_REF, RuleUtils.FIGURE_LEGEND, "Fig 1");
        interaction.getAttributes().add(attribute);

        InteractionFigureLegendRule rule =  new InteractionFigureLegendRule(ontologyMaganer);

        Collection<ValidatorMessage> messages = rule.check(interaction);

        Assert.assertNotNull(messages);
        Assert.assertEquals( 0, messages.size() );
    }

    @Test
    public void test_interaction_one_figure_legend_no_ac() throws ValidatorException {
        Interaction interaction = buildInteractionDeterministic();

        Attribute attribute = new Attribute(RuleUtils.FIGURE_LEGEND, "Fig 1");
        interaction.getAttributes().add(attribute);

        InteractionFigureLegendRule rule =  new InteractionFigureLegendRule(ontologyMaganer);

        Collection<ValidatorMessage> messages = rule.check(interaction);

        Assert.assertNotNull(messages);
        Assert.assertEquals( 0, messages.size() );
    }

    @Test
    public void test_interaction_attributes_no_legend() throws ValidatorException {
        Interaction interaction = buildInteractionDeterministic();

        Attribute attribute = new Attribute(RuleUtils.AUTHOR_CONFIDENCE, RuleUtils.AUTHOR_CONFIDENCE_MI_REF, "antibody 1");
        interaction.getAttributes().add(attribute);

        InteractionFigureLegendRule rule =  new InteractionFigureLegendRule(ontologyMaganer);

        Collection<ValidatorMessage> messages = rule.check(interaction);

        Assert.assertNotNull(messages);
        Assert.assertEquals( 1, messages.size() );
    }

    @Test
    public void test_interaction_no_attributes() throws ValidatorException {
        Interaction interaction = buildInteractionDeterministic();

        InteractionFigureLegendRule rule =  new InteractionFigureLegendRule(ontologyMaganer);

        Collection<ValidatorMessage> messages = rule.check(interaction);

        Assert.assertNotNull(messages);
        Assert.assertEquals( 1, messages.size() );
    }
}
